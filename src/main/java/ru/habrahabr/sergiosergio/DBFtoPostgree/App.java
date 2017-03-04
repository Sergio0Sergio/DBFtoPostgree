package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
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

		Option bdServerAdressKey = new Option("-h", true, "Адрес сервера (по умолчанию 127.0.0.1)");
		Option bdServerPortKey = new Option("-p", true, "Порт сервера (по умолчанию 5432)");
		Option bdNameKey = new Option("-d", true, "Имя БД (Обязательный параметр)");
		Option tableNameKey = new Option("-t", true, "Имя таблицы в БД (Обязательный параметр)");
		Option bdUserNameKey = new Option("-u", true, "Имя пользователя (по умолчанию \"postgres\")");
		Option bdPasswordKey = new Option("-w", true, "Адрес сервера (по умолчанию 127.0.0.1)");
		Option filePathKey = new Option("-f", true, "Путь к файлу .dbf (Обязательный параметр)");

		Options options = new Options();

		options.addOption(bdServerAdressKey);
		options.addOption(bdServerPortKey);
		options.addOption(bdNameKey);
		options.addOption(tableNameKey);
		options.addOption(bdUserNameKey);
		options.addOption(bdPasswordKey);
		options.addOption(filePathKey);

		buf = new ArrayBlockingQueue<String>(1000, false);

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

		if (!line.hasOption("-d") || !line.hasOption("-t") || !line.hasOption("-f")) {

			System.err.println("Отсутствует один или более обязательный параметр");
			System.err.println("Работа программы завершена");
			System.exit(1);

		} else {

			bdName = line.getOptionValue("-d");
			tableName = line.getOptionValue("-t");
			filePath = line.getOptionValue("-f");
		}

		if (bdName == null || tableName == null || filePath == null) {

			System.err.println("Отсутствует один или более обязательный параметр");
			System.err.println("Работа программы завершена");
			System.exit(1);

		}

		if (line.hasOption("-h")) {
			bdServerAdress = line.getOptionValue("-h");
		}
		if (line.hasOption("-p")) {
			bdServerPort = line.getOptionValue("-p");
		}
		if (line.hasOption("-u")) {
			bdUserName = line.getOptionValue("-u");
		}
		if (line.hasOption("-w")) {
			bdPassword = line.getOptionValue("-w");
		}

		DBFReader dbfReader = new DBFReader(buf, filePath);
		DBWriter dbWriter = new DBWriter(buf, bdServerAdress, bdServerPort, bdName, tableName, bdUserName, bdPassword);
		dbfReader.start();
		dbWriter.start();

	}
}
