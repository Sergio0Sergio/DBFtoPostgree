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
			copyManager.copyIn(
					"COPY \"HOUSE\"(\n"
							+ "AOGUID\",\"BUILDNUM\",\"ENDDATE\",\"ESTSTATUS\",\"HOUSEGUID\",\"HOUSEID\",\"HOUSENUM\",\"STATSTATUS\",\"IFNSFL\",\"IFNSUL\",\"OKATO\",\"OKTMO\",\"POSTALCODE\",\"STARTDATE\",\"STRUCNUM\",\"STRSTATUS\",\"TERRIFNSFL\",\"TERRIFNSUL\",\"UPDATEDATE\",\"NORMDOC\",\"COUNTER\",\"CADNUM\",\"DIVTYPE\"\n"
							+ ")\n" + "FROM STDIN\n" + "WITH (\n" + "FORMAT CSV,\n" + "DELIMITER ';',\n" + "HEADER TRUE,\n" + "QUOTE '\"',\n" + "ESCAPE '\"',\n" + "ENCODING 'WIN866'\n" + ");",
					inputStream);
		} catch (SQLException e) {
			System.out.println("Ошибка записи в базу");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Ошибка записи в базу");
			e.printStackTrace();
		}

	}

}
