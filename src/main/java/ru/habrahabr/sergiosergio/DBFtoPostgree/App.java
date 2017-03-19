package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import ru.smartflex.tools.dbf.DbfColumn;
import ru.smartflex.tools.dbf.DbfEngine;
import ru.smartflex.tools.dbf.DbfHeader;

/**
 * DbfToPostgre
 *
 */
public class App {

	public static void main(String[] args) {

		BlockingQueue<String> buf;
		String bdServerAdress = "127.0.0.1";
		String bdServerPort = "5432";
		String bdName = null;
		String tableName = null;
		String bdUserName = "postgres";
		String bdPassword = "";
		String filePath = null;
		// Long readCounter = 0L;
		// Long writeCounter = 0L;
		Connection connection = null;

		Option bdServerAdressKey = new Option("h", true, "Адрес сервера (по умолчанию 127.0.0.1)");
		Option bdServerPortKey = new Option("p", true, "Порт сервера (по умолчанию 5432)");
		Option bdNameKey = new Option("d", true, "Имя БД (Обязательный параметр)");
		Option tableNameKey = new Option("t", true, "Имя таблицы в БД (Обязательный параметр)");
		Option bdUserNameKey = new Option("u", true, "Имя пользователя (по умолчанию \"postgres\")");
		Option bdPasswordKey = new Option("w", true, "Адрес сервера (по умолчанию 127.0.0.1)");
		Option filePathKey = new Option("f", true, "Путь к файлу .dbf (Обязательный параметр)");

		bdServerAdressKey.setArgs(1);
		bdServerPortKey.setArgs(1);
		bdNameKey.setArgs(1);
		tableNameKey.setArgs(1);
		bdUserNameKey.setArgs(1);
		bdPasswordKey.setArgs(1);
		filePathKey.setArgs(1);

		bdServerAdressKey.setOptionalArg(true);
		bdServerPortKey.setOptionalArg(true);
		bdNameKey.setOptionalArg(false);
		tableNameKey.setOptionalArg(false);
		bdUserNameKey.setOptionalArg(true);
		bdPasswordKey.setOptionalArg(true);
		filePathKey.setOptionalArg(false);

		bdServerAdressKey.setArgName("Адрес сервера (по умолчанию 127.0.0.1)");
		bdServerPortKey.setArgName("Порт сервера (по умолчанию 5432)");
		bdNameKey.setArgName("Имя БД (Обязательный параметр)");
		tableNameKey.setArgName("Имя таблицы в БД (Обязательный параметр)");
		bdUserNameKey.setArgName("Имя пользователя (по умолчанию \"postgres\"");
		bdPasswordKey.setArgName("Адрес сервера (по умолчанию 127.0.0.1)");
		filePathKey.setArgName("Путь к файлу .dbf (Обязательный параметр)");

		Options options = new Options();

		options.addOption(bdServerAdressKey);
		options.addOption(bdServerPortKey);
		options.addOption(bdNameKey);
		options.addOption(tableNameKey);
		options.addOption(bdUserNameKey);
		options.addOption(bdPasswordKey);
		options.addOption(filePathKey);

		buf = new ArrayBlockingQueue<String>(1000, false);
		DbfHeader dbfHeader;
		Iterator<DbfColumn> nameColumnIterator;

		dbfHeader = DbfEngine.getHeader(filePath, null);
		nameColumnIterator = dbfHeader.getColumnIterator();
		int columnCounter = dbfHeader.getCountColumns();
		// String[] columnsNames = new String[columnCounter];
		StringBuilder nameVariables = new StringBuilder();
		String sqlVariables;

		CopyManager copyManager = null;

		CommandLineParser parser = new DefaultParser();

		CommandLine line = null;

		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {

			System.err.println("Невозможно прочитать параметры командной строки");
			System.err.println("Работа программы завершена");
			e.printStackTrace();
			System.exit(1);
		}

		if (!line.hasOption("d") || !line.hasOption("t") || !line.hasOption("f")) {

			System.err.println("Отсутствует один или более обязательный параметр");
			System.err.println("Работа программы завершена");
			System.exit(1);

		} else {

			bdName = line.getOptionValue("d");
			tableName = line.getOptionValue("t");
			filePath = line.getOptionValue("f");
		}

		if (bdName == null || tableName == null || filePath == null) {

			System.err.println("Отсутствует один или более обязательный параметр");
			System.err.println("Работа программы завершена");
			System.exit(1);

		}

		if (line.hasOption("-h")) {
			bdServerAdress = line.getOptionValue("h");
		}
		if (line.hasOption("-p")) {
			bdServerPort = line.getOptionValue("p");
		}
		if (line.hasOption("-u")) {
			bdUserName = line.getOptionValue("u");
		}
		if (line.hasOption("-w")) {
			bdPassword = line.getOptionValue("w");
		}

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e2) {
			System.err.println("Не найден драйвер БД");
			e2.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://" + bdServerAdress + ":" + bdServerPort + "/" + bdName, bdUserName, bdPassword);
		} catch (SQLException e2) {
			System.err.println("Невозможно создать подключение");
			e2.printStackTrace();
		}

		StringsInputStream inputStream = new StringsInputStream(buf);

		try {
			copyManager = new CopyManager((BaseConnection) connection);
		} catch (SQLException e2) {
			System.err.println("Не удалось создать copyManager");
			e2.printStackTrace();
		}

		/*
		 * читаем имена колонок в строку
		 */
		for (int i = 0; i < columnCounter; i++) {

			nameVariables.append("\"");
			nameVariables.append(nameColumnIterator.next().getColumnName());
			nameVariables.append("\"");
			if (i < columnCounter - 1) {

				nameVariables.append(", ");

			}

		}

		sqlVariables = nameVariables.toString();
		DBFReader dbfReader = new DBFReader(buf, dbfHeader);
		DBWriter dbWriter = new DBWriter(inputStream, copyManager, sqlVariables, tableName);
		// TestWrite testWrite = new TestWrite(buf);
		dbfReader.start();
		dbWriter.start();
		// testWrite.start();

		try {
			dbfReader.join();
		} catch (InterruptedException e) {
			System.err.println("Ошибка выполнения программы в модуле чтения");
			e.printStackTrace();
			dbfReader.interrupt();
			dbWriter.interrupt();
			System.exit(1);
		}

		while (!buf.isEmpty()) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {

			try {
				connection.close(); // Закрываем подключение к базе.
			} catch (SQLException e) {
				System.err.println("Не удалось завершить подключение к базе");
				e.printStackTrace();
			}
			System.out.println("Подключение к базе закрыто");
			// testWrite.interrupt();
			dbWriter.interrupt();
			System.out.println("Копирование завершено");
			System.exit(0);

		}

	}
}
