package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.concurrent.BlockingQueue;

public class TestWrite extends Thread {

	private BlockingQueue<String> buf;
	private Long counter;

	public TestWrite(BlockingQueue<String> buf, Long counter) {

		this.buf = buf;
		this.counter = counter;
	}

	@Override
	public void run() {

		while (true) {
			try {
				System.out.println(buf.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
		}
	}

}
