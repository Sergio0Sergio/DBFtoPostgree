package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.concurrent.BlockingQueue;

public class DBFReader extends Thread {

	private BlockingQueue<String> buf;
	private String filename;

	public DBFReader(BlockingQueue<String> buf, String filename) {

		this.buf = buf;
		this.filename = filename;

	}

	@Override
	public void run() {

	}

}
