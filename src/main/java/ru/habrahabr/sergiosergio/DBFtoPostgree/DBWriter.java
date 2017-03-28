package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;

public class DBWriter extends Thread {

    private StringsInputStream inputStream;
    private CopyManager copyManager;
    private String sqlVariables;
    private String tableName;
    private Connection connection;

    public DBWriter(StringsInputStream inputStream, CopyManager copyManager, String sqlVariables, String tableName,
	    Connection connection) {

	this.inputStream = inputStream;
	this.copyManager = copyManager;
	this.sqlVariables = sqlVariables;
	this.tableName = tableName;
	this.connection = connection;

    }

    @Override
    public void run() {

	try {
	    System.out.println("Начало записи в базу.");
	    copyManager.copyIn(
		    "COPY \"" + tableName + "\"(" + sqlVariables
			    + ")FROM STDIN	WITH (FORMAT CSV, DELIMITER ',', HEADER FALSE, QUOTE '\"', ESCAPE '\"', ENCODING 'UTF-8');",
		    inputStream);
	} catch (SQLException e) {
	    System.err.println("Ошибка записи в базу.");
	    e.printStackTrace();
	} catch (IOException e) {
	    System.err.println("Ошибка записи в базу.");
	    e.printStackTrace();
	}

	try {
	    connection.close();
	} catch (SQLException e) {
	    System.err.println("Ошибка закрытия базы.");
	    e.printStackTrace();
	}

	System.out.println("Запись завершена.");
	System.out.println("Работа программы завершена успешно.");

    }

}
