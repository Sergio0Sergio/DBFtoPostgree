package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.concurrent.BlockingQueue;

public class DBWriter implements Runnable {

	private BlockingQueue<String> buf;
	private String bdServerAdress;
	private String bdServerPort;
	private String bdName;
	private String tableName;
	private String bdUserName;
	private String bdPassword;

	public DBWriter(BlockingQueue<String> buf, String bdServerAdress, String bdServerPort, String bdName, String tableName, String bdUserName, String bdPassword) {

		this.buf = buf;
		this.bdServerAdress = bdServerAdress;
		this.bdServerPort = bdServerPort;
		this.bdName = bdName;
		this.tableName = tableName;
		this.bdUserName = bdUserName;
		this.bdPassword = bdPassword;

	}

	public void run() {
		// TODO Auto-generated method stub

	}

}
