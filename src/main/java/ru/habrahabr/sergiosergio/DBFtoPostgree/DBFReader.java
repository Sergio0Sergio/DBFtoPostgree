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

	private DbfHeader dbfHeader;
	private DbfIterator dbfIterator;
	private DbfRecord dbfRecord;
	private StringBuilder string;
	private int columnCounter;

	private Iterator<DbfColumn> columnIterator;
	private Iterator<DbfColumn> nameColumnIterator;

	public DBFReader(BlockingQueue<String> buf, String filename) {

		this.buf = buf;
		this.filename = filename;

	}

	@Override
	public void run() {

		string = new StringBuilder();
		dbfHeader = DbfEngine.getHeader(filename, null);
		dbfIterator = dbfHeader.getDbfIterator();
		columnCounter = dbfHeader.getCountColumns();
		columnIterator = dbfHeader.getColumnIterator();
		String[] columnsNames = new String[columnCounter];

		String str1 = null;
		String str2 = null;
		boolean hasNextRecord = true;// TODO исправить проверку на отсутствие
										// записей

		for (int i = 0; i < columnCounter; i++) {

			columnsNames[i] = nameColumnIterator.next().getColumnName();
		}

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

				if (i != columnCounter - 1) {
					string.append(",");
				}

			}
			columnIterator = dbfHeader.getColumnIterator();

			try {
				buf.put(string.toString());
			} catch (InterruptedException e) {
				System.err.println("Ошибка записи в буфер");
				e.printStackTrace();
			}
			string.delete(0, string.length());

		}

		System.out.println("Чтение файла завершено");
		System.exit(0);

	}

}
