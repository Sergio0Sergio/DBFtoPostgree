package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import ru.smartflex.tools.dbf.DbfColumn;
import ru.smartflex.tools.dbf.DbfEngine;
import ru.smartflex.tools.dbf.DbfHeader;
import ru.smartflex.tools.dbf.DbfIterator;
import ru.smartflex.tools.dbf.DbfRecord;

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

		string = new StringBuilder();
		dbfHeader = DbfEngine.getHeader(filename, null);
		dbfIterator = dbfHeader.getDbfIterator();
		columnCounter = dbfHeader.getCountColumns();
		columnIterator = dbfHeader.getColumnIterator();
		String modString = null;
		boolean hasNextRecord = true;

		while (hasNextRecord) {

			dbfRecord = dbfIterator.nextRecord();
			hasNextRecord = dbfIterator.hasMoreRecords();

			for (int i = 0; i < columnCounter; i++) {

				modString = dbfRecord.getAsString(columnIterator.next());
				modString.replaceAll(""", replacement)

				if (i != columnCounter - 1) {
					string.append(",");
				}

			}
			columnIterator = dbfHeader.getColumnIterator();
			buf.add(string.toString());
			string.delete(0, string.length());
			counter++;

		}

	}

}
