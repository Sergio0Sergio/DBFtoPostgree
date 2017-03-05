package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class DBWriter extends Thread {

	private BlockingQueue<String> buf;
	private String bdServerAdress;
	private String bdServerPort;
	private String bdName;
	private String tableName;
	private String bdUserName;
	private String bdPassword;
	private Long counter;

	Connection connection = null;

	public DBWriter(BlockingQueue<String> buf, String bdServerAdress, String bdServerPort, String bdName, String tableName, String bdUserName, String bdPassword, Long counter) {

		this.buf = buf;
		this.bdServerAdress = bdServerAdress;
		this.bdServerPort = bdServerPort;
		this.bdName = bdName;
		this.tableName = tableName;
		this.bdUserName = bdUserName;
		this.bdPassword = bdPassword;
		this.counter = counter;

	}

	@Override
	public void run() {

		try {
			connection = DriverManager.getConnection("jdbc:postgresql://" + bdServerAdress + ":" + bdServerPort + "/" + bdName, bdUserName, bdPassword);
		} catch (SQLException e) {
			System.err.println("Невозможно подключиться к БД");
			System.exit(1);
		}

		if (connection != null) {
			System.out.println("Подключение Установлено");
		} else {
			System.err.println("Подключение не установлено");
			System.exit(1);
		}

	}

}
