package ru.habrahabr.sergiosergio.DBFtoPostgree;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import ru.smartflex.tools.dbf.DbfColumn;
import ru.smartflex.tools.dbf.DbfHeader;
import ru.smartflex.tools.dbf.DbfIterator;
import ru.smartflex.tools.dbf.DbfRecord;

public class DBFReader extends Thread {

	private BlockingQueue<String> buf;
	// private String filename;

	private DbfHeader dbfHeader;
	private DbfIterator dbfIterator;
	private DbfRecord dbfRecord;
	private StringBuilder string;
	private int columnCounter;
	private SuperFlag superFlag;

	private Iterator<DbfColumn> columnIterator;

	public DBFReader(BlockingQueue<String> buf, DbfHeader dbfHeader, SuperFlag superFlag) {

		this.buf = buf;
		this.dbfHeader = dbfHeader;
		this.superFlag = superFlag;

	}

	@Override
	public void run() {

		string = new StringBuilder();

		dbfIterator = dbfHeader.getDbfIterator();
		columnCounter = dbfHeader.getCountColumns();
		columnIterator = dbfHeader.getColumnIterator();

		String str1 = null;
		String str2 = null;
		boolean hasNextRecord = dbfIterator.hasMoreRecords();

		while (hasNextRecord) {

			dbfRecord = dbfIterator.nextRecord();
			hasNextRecord = dbfIterator.hasMoreRecords();

			for (int i = 0; i < columnCounter; i++) {

				str1 = dbfRecord.getAsString(columnIterator.next());

				if (str1 == null) {
					str1 = "";
				}

				str2 = str1.replaceAll("\"", "\"\"");
				string.append("\"");
				string.append(str2);
				string.append("\"");
				// System.out.print(str1);
				// string.append(",");

				if (i != columnCounter - 1) {
					string.append(",");
				}

			}
			string.append("\n");

			columnIterator = dbfHeader.getColumnIterator();
			// System.out.print(string.toString());

			try {
				buf.put(string.toString());
			} catch (InterruptedException e) {
				System.err.println("Ошибка записи в буфер");
				e.printStackTrace();
			}
			string.delete(0, string.length());

		}
		dbfHeader.closeDbfHeader();
		superFlag.endingFlag = true;
		System.out.println("Чтение файла завершено");

	}

}
