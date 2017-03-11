package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.io.IOException;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;

public class DBWriter extends Thread {

	private StringsInputStream inputStream;
	private CopyManager copyManager;

	public DBWriter(StringsInputStream inputStream, CopyManager copyManager) {

		this.inputStream = inputStream;
		this.copyManager = copyManager;

	}

	@Override
	public void run() {

		try {
			copyManager.copyIn("COPY t FROM STDIN", inputStream);
		} catch (SQLException e) {
			System.out.println("Ошибка записи в базу");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Ошибка записи в базу");
			e.printStackTrace();
		}

	}

}
