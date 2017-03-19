package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.io.IOException;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;

public class DBWriter extends Thread {

	private StringsInputStream inputStream;
	private CopyManager copyManager;
	private String sqlVariables;
	private String tableName;

	public DBWriter(StringsInputStream inputStream, CopyManager copyManager, String sqlVariables, String tableName) {

		this.inputStream = inputStream;
		this.copyManager = copyManager;
		this.sqlVariables = sqlVariables;
		this.tableName = tableName;

	}

	@Override
	public void run() {

		try {
			copyManager.copyIn("COPY \"" + tableName + "\"(" + sqlVariables + ")FROM STDIN	WITH (FORMAT CSV, DELIMITER ',', HEADER TRUE, QUOTE '\"', ESCAPE '\"', ENCODING 'WIN866');", inputStream);
		} catch (SQLException e) {
			System.out.println("Ошибка записи в базу");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Ошибка записи в базу");
			e.printStackTrace();
		}

	}

}
