package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;

public class DBWriter extends Thread {

    private BlockingQueue<String> buf;
    private Connection connection;

    public DBWriter(BlockingQueue<String> buf, Connection connection) {

	this.buf = buf;
	this.connection = connection;

    }

    @Override
    public void run() {

    }

}
