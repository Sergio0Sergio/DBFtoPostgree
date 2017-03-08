package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;

public class StringsInputStream extends InputStream {

	private ByteArrayInputStream bais = null;
	private BlockingQueue<String> buf;
	ByteArrayOutputStream outputStream;

	public StringsInputStream(BlockingQueue<String> buf) {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		this.buf = buf;
		while(true)

		{

			try {
				outputStream.write(buf.take().getBytes());
			} catch (IOException e) {
				System.err.println("Не удается прочитать данные из буфера");
				e.printStackTrace();
			} catch (InterruptedException e) {
				System.err.println("Не удается прочитать данные из буфера");
				e.printStackTrace();
			}
	}

	

	}

	bais=new ByteArrayInputStream(outputStream.toByteArray());

	@Override
	public int read() throws IOException {

		return bais.read();
	}

}
