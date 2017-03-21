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
	private boolean endingFlag;
	private boolean emptyBuf = false;

	public StringsInputStream(BlockingQueue<String> buf, boolean endingFlag) {

		this.endingFlag = endingFlag;
		this.buf = buf;

	}

	@Override
	public int read() {

		if (i == bytearray.length) {

			while (emptyBuf && !endingFlag) {
				emptyBuf = getNextString();
			}

		}

		if (endingFlag) {
			return -1;
		}
		i++;
		return bytearray[i - 1];

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

			while (emptyBuf && !endingFlag) {
				emptyBuf = getNextString();
			}

		}
		if (endingFlag) {
			return -1;

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

	private boolean getNextString() {

		bytearray = null;
		if (buf.isEmpty()) {
			return true;
		}
		try {
			bytearray = buf.take().getBytes();
		} catch (InterruptedException e) {
			System.err.println("Не удалось прочитать данные из буфера");
			e.printStackTrace();
		}
		i = 0;
		return false;

	}

}
