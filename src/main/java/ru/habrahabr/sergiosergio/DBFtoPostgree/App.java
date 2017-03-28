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
import org.apache.commons.cli.HelpFormatter;
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
	Connection connection = null;

	Option bdServerAdressKey = new Option("h", "help", true, "host");
	Option bdServerPortKey = new Option("p", "port", true, "port");
	Option bdNameKey = new Option("d", "database", true, "database");
	Option tableNameKey = new Option("t", "table", true, "table");
	Option bdUserNameKey = new Option("u", "user", true, "user");
	Option bdPasswordKey = new Option("w", "password", true, "password");
	Option filePathKey = new Option("f", "filename", true, "file");
	Option help = new Option("h", "help", true, "help");

	bdServerAdressKey.setArgs(1);
	bdServerPortKey.setArgs(1);
	bdNameKey.setArgs(1);
	tableNameKey.setArgs(1);
	bdUserNameKey.setArgs(1);
	bdPasswordKey.setArgs(1);
	filePathKey.setArgs(1);
	help.setArgs(0);

	bdServerAdressKey.setOptionalArg(true);
	bdServerPortKey.setOptionalArg(true);
	bdNameKey.setOptionalArg(false);
	tableNameKey.setOptionalArg(false);
	bdUserNameKey.setOptionalArg(true);
	bdPasswordKey.setOptionalArg(true);
	filePathKey.setOptionalArg(false);
	help.setOptionalArg(true);

	bdServerAdressKey.setArgName("db server adress");
	bdServerPortKey.setArgName("server port");
	bdNameKey.setArgName("db name");
	tableNameKey.setArgName("table name");
	bdUserNameKey.setArgName("user name");
	bdPasswordKey.setArgName("bd password");
	filePathKey.setArgName("path to .dbf file");
	help.setArgName("this help");

	Options options = new Options();

	options.addOption(bdServerAdressKey);
	options.addOption(bdServerPortKey);
	options.addOption(bdNameKey);
	options.addOption(tableNameKey);
	options.addOption(bdUserNameKey);
	options.addOption(bdPasswordKey);
	options.addOption(filePathKey);
	options.addOption(help);

	buf = new ArrayBlockingQueue<String>(1000, false);
	DbfHeader dbfHeader;
	Iterator<DbfColumn> nameColumnIterator;
	CopyManager copyManager = null;
	CommandLine line = null;
	CommandLineParser parser = new DefaultParser();

	try {
	    line = parser.parse(options, args);
	} catch (ParseException e) {

	    System.err.println("Невозможно прочитать параметры командной строки.");
	    System.err.println("Работа программы завершена.");
	    e.printStackTrace();
	    System.exit(1);
	}

	if (args.length == 0 || line.hasOption("h")) {

	    HelpFormatter formatter = new HelpFormatter();
	    formatter.printHelp("DBFtoPostgre", options, true);
	    System.exit(1);

	}

	if (!line.hasOption("d") || !line.hasOption("t") || !line.hasOption("f")) {

	    System.err.println("Отсутствует один или более обязательный параметр.");
	    System.err.println("Работа программы завершена.");
	    System.exit(1);

	} else {

	    bdName = line.getOptionValue("d");
	    tableName = line.getOptionValue("t");
	    filePath = line.getOptionValue("f");
	    System.out.println(filePath);
	}

	if (bdName == null || tableName == null || filePath == null) {

	    System.err.println("Отсутствует один или более обязательный параметр.");
	    System.err.println("Работа программы завершена.");
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
	dbfHeader = DbfEngine.getHeader(filePath, null);
	nameColumnIterator = dbfHeader.getColumnIterator();
	int columnCounter = dbfHeader.getCountColumns();
	SuperFlag superFlag = new SuperFlag();

	try {
	    Class.forName("org.postgresql.Driver");
	} catch (ClassNotFoundException e2) {
	    System.err.println("Не найден драйвер БД.");
	    e2.printStackTrace();
	}
	try {
	    connection = DriverManager.getConnection(
		    "jdbc:postgresql://" + bdServerAdress + ":" + bdServerPort + "/" + bdName, bdUserName, bdPassword);
	} catch (SQLException e2) {
	    System.err.println("Невозможно создать подключение.");
	    e2.printStackTrace();
	}

	StringsInputStream inputStream = new StringsInputStream(buf, superFlag);

	try {
	    copyManager = new CopyManager((BaseConnection) connection);
	} catch (SQLException e2) {
	    System.err.println("Не удалось создать copyManager.");
	    e2.printStackTrace();
	}

	StringBuilder nameVariables = new StringBuilder();
	String sqlVariables;

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
	System.out.println(sqlVariables);
	DBFReader dbfReader = new DBFReader(buf, dbfHeader, superFlag);
	System.out.println("Создан модуль чтения.");

	DBWriter dbWriter = new DBWriter(inputStream, copyManager, sqlVariables, tableName, connection);
	System.out.println("Создан модуль записи.");
	dbfReader.start();
	dbWriter.start();

    }

}
