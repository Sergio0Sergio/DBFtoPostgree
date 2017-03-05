package ru.smartflex.tools.dbf.test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import ru.smartflex.tools.dbf.DbfEngine;
import ru.smartflex.tools.dbf.DbfIterator;
import ru.smartflex.tools.dbf.DbfRecord;

public class Fp26FlexOldReader {

	private static void testRead() {
		DbfIterator dbfIterator = DbfEngine.getReader(new File("F:\\t7_del\\KV.DBF"), null);
		
		while (dbfIterator.hasMoreRecords()) {
			DbfRecord dbfRecord = dbfIterator.nextRecord();
			short floor = dbfRecord.getShort("etaj");
			short entrance = dbfRecord.getShort("podj");
			BigDecimal bd = dbfRecord.getBigDecimal("etaj");
			float fl = dbfRecord.getFloat("etaj");
			Date kvv = dbfRecord.getDate("datekvv");
			String prim = dbfRecord.getString("prim");
			
			System.out.println(floor+" "+entrance+" "+bd+" "+fl+" "+kvv+" "+prim);
		}
	}
	

	public static void main(String[] args) {
		
		Fp26FlexOldReader.testRead();
	}

}
