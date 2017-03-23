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
	private Integer endingFlag;
	private boolean emptyBuf = false;

	public StringsInputStream(BlockingQueue<String> buf, Integer endingFlag) {

		this.endingFlag = endingFlag;
		this.buf = buf;

	}

	@Override
	public int read() {

		if (bytearray == null) {

			emptyBuf = getNextString();

		}

		if (i == bytearray.length) {

			while (emptyBuf && endingFlag == 0) {
				emptyBuf = getNextString();
			}

		}

		if (endingFlag != 0) {
			return -1;
		}
		i++;
		return bytearray[i - 1];

	}

	@Override
	public int read(byte[] b) throws IOException {

		if (bytearray == null) {

			emptyBuf = getNextString();

		}
		k = 0;
		if (b.length >= bytearray.length - i) {
			while (i < bytearray.length) {

				b[k] = bytearray[i];
				i++;
				k++;
			}

			emptyBuf = getNextString();
			return k;
		}

		if (emptyBuf) {
			return -1;

		}
		int j = 0;

		while (j < b.length) {

			b[j] = bytearray[i];
			i++;
			j++;
		}
		return j + 1;

	}

	public int aviable() {
		if (bytearray == null) {

			emptyBuf = getNextString();

		}

		return bytearray.length - i;
	}

	private boolean getNextString() {

		bytearray = null;
		while (buf.isEmpty() && endingFlag == 1) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("ошибка при выходе из ожидания");
				e.printStackTrace();
			}
		}
		if (buf.isEmpty() && endingFlag == 1) {

			return true;
		} else {

			try {
				bytearray = buf.take().getBytes();
			} catch (InterruptedException e) {
				System.err.println("Не удалось прочитать данные из буфера");
				e.printStackTrace();

			}
			i = 0;
			return false;
		}
		// System.err.println("Непредвиденный путь выполнения чтения из
		// буфера");
		// return true;

	}

}
