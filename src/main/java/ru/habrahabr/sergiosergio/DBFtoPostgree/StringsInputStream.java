package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

public class StringsInputStream extends InputStream {

	private BlockingQueue<String> buf;
	private byte[] bytearray;

	public String string;
	private int i;
	private int k;
	private int temp;

	public StringsInputStream(BlockingQueue<String> buf) {

		this.buf = buf;
		try {
			bytearray = buf.take().getBytes();
		} catch (InterruptedException e) {
			System.err.println("Не удалось прочитать данные из буфера");
			e.printStackTrace();
		}
		i = 0;
	}

	@Override
	public int read() {

		temp = i;
		i++;

		if (i == bytearray.length) {

			getNextString();
		}
		return bytearray[temp];

	}

	@Override
	public int read(byte[] b) throws IOException {

		k = 0;
		if (b.length >= bytearray.length - i) {
			while (i < bytearray.length) {

				b[k] = bytearray[i];
				i++;
				k++;
			}

			getNextString();

		} else {

			for (int j = 0; j < b.length; j++) {

				b[j] = bytearray[i];
				i++;
			}
		}

		return b.length;

	}

	public int aviable() {

		return bytearray.length - i;
	}

	private void getNextString() {

		bytearray = null;
		try {
			bytearray = buf.take().getBytes();
		} catch (InterruptedException e) {
			System.err.println("Не удалось прочитать данные из буфера");
			e.printStackTrace();
		}
		i = 0;

	}

}
