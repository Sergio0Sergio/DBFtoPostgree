package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import ru.smartflex.tools.dbf.DbfColumn;
import ru.smartflex.tools.dbf.DbfEngine;
import ru.smartflex.tools.dbf.DbfHeader;
import ru.smartflex.tools.dbf.DbfIterator;
import ru.smartflex.tools.dbf.DbfRecord;
import ru.smartflex.tools.dbf.test.Fp26Reader;

public class DBFReader extends Thread {

	private BlockingQueue<String> buf;
	private String filename;
	private Long counter;
	private DbfHeader dbfHeader;

	private DbfIterator dbfIterator;

	private DbfRecord dbfRecord;

	private StringBuilder string;
	private int columnCounter;

	// private String[] columns = new String[columnCounter];
	private Iterator<DbfColumn> columnIterator;

	public DBFReader(BlockingQueue<String> buf, String filename, Long counter) {

		this.buf = buf;
		this.filename = filename;
		this.counter = counter;

	}

	@Override
	public void run() {

		dbfHeader = DbfEngine.getHeader(Fp26Reader.class.getResourceAsStream(filename), null);
		dbfIterator = dbfHeader.getDbfIterator();
		columnCounter = dbfHeader.getCountColumns();
		columnIterator = dbfHeader.getColumnIterator();
		while (dbfIterator.hasMoreRecords()) {

			string.delete(0, string.length());
			dbfRecord = dbfIterator.nextRecord();
			for (int i = 0; i < columnCounter; i++) {

				string.append(columnIterator.next());
				if (i != columnCounter - 1) {
					string.append(",");
				}

			}
			buf.add(string.toString());
			counter++;

		}

	}

}
