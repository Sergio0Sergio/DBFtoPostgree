package ru.smartflex.tools.dbf.test;

import java.math.BigDecimal;
import java.util.Date;

import ru.smartflex.tools.dbf.DbfEngine;
import ru.smartflex.tools.dbf.DbfIterator;
import ru.smartflex.tools.dbf.DbfRecord;

/**
 * Sample class for dbf reading.
 * @author galisha
 * @since 1.00
 */
public class Fp26Reader {

	private static void testRead() {
		DbfIterator dbfIterator = DbfEngine.getReader(
				Fp26Reader.class.getResourceAsStream("FP_26_SAMPLE.DBF"), null);
		
		while (dbfIterator.hasMoreRecords()) {
			DbfRecord dbfRecord = dbfIterator.nextRecord();
			String string = dbfRecord.getString("string");
			float sumFloat = dbfRecord.getFloat("sum_f");
			BigDecimal sumNumeric = dbfRecord.getBigDecimal("sum_n");
			boolean bool = dbfRecord.getBoolean("bool_val");
			Date date = dbfRecord.getDate("date_val");
			
			System.out.println(string + " " + sumFloat + " " + sumNumeric + " "
					+ bool + " " + date);
		}
	}

	public static void main(String[] args) {
		Fp26Reader.testRead();
	}

}
