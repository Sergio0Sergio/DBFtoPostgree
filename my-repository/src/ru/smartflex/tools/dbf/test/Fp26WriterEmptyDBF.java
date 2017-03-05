package ru.smartflex.tools.dbf.test;

import ru.smartflex.tools.dbf.DbfAppender;
import ru.smartflex.tools.dbf.DbfCodePages;
import ru.smartflex.tools.dbf.DbfColumn;
import ru.smartflex.tools.dbf.DbfColumnTypes;
import ru.smartflex.tools.dbf.DbfEngine;

/**
 * Sample class for writing empty dbf file (no record).
 * @author galisha
 * @since 1.00
 */
public class Fp26WriterEmptyDBF {

	private static void testWrite() {
		DbfAppender dbfAppender = DbfEngine.getWriter("WRT_EMPTY.DBF", DbfCodePages.Cp866);
		DbfColumn dc01 = new DbfColumn("fio", DbfColumnTypes.Character, 20, 0);
		DbfColumn dc02 = new DbfColumn("birthday", DbfColumnTypes.Date, 0, 0);
		dbfAppender.defineColumns(dc01,dc02);
		dbfAppender.writeDbfAndClose();
	}

	public static void main(String[] args) {
		Fp26WriterEmptyDBF.testWrite();

	}

}
