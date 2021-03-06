package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;

public class StringsInputStream extends InputStream {

	private BlockingQueue<String> buf;
	private byte[] bytearray;

	public String string;
	private int i;
	private int k;
	private SuperFlag superFlag;
	private boolean emptyBuf = false;

	public StringsInputStream(BlockingQueue<String> buf, SuperFlag superFlag) {

		this.superFlag = superFlag;
		this.buf = buf;

	}

	@Override
	public int read() {

		if (bytearray == null) {

			emptyBuf = getNextString();

		}
		if (emptyBuf) {
			return -1;
		}

		if (i == bytearray.length) {

			while (emptyBuf && !superFlag.endingFlag) {

			}
			emptyBuf = getNextString();

		}

		if (emptyBuf) {
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
		if (emptyBuf) {
			return -1;
		}
		k = 0;
		if (b.length >= bytearray.length - i) {
			while (i < bytearray.length) {

				b[k] = bytearray[i];
				i++;
				k++;
			}

			emptyBuf = getNextString();
			if (emptyBuf) {
				bytearray = null;
			}

			return k;
		}

		int j = 0;

		while (j < b.length) {

			b[j] = bytearray[i];
			i++;
			j++;
		}
		return j;

	}

	public int aviable() {
		if (bytearray == null) {

			emptyBuf = getNextString();

		}

		return bytearray.length - i;
	}

	private boolean getNextString() {

		bytearray = null;
		while (buf.isEmpty() && !superFlag.endingFlag) {

		}
		if (buf.isEmpty() && superFlag.endingFlag) {

			return true;
		} else {

			try {
				bytearray = buf.take().getBytes(StandardCharsets.UTF_8);
			} catch (InterruptedException e) {
				System.err.println("Не удалось прочитать данные из буфера.");
				e.printStackTrace();

			}
			i = 0;
			return false;
		}

	}

}
