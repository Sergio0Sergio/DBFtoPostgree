package ru.smartflex.tools.dbf.test;

import java.util.Iterator;

import ru.smartflex.tools.dbf.DbfColumn;
import ru.smartflex.tools.dbf.DbfEngine;
import ru.smartflex.tools.dbf.DbfHeader;

/**
 * Sample class for reading dbf header.
 * @author galisha
 * @since 1.05
 */
public class Fp26ReaderHeader {

	
	private static void testRead() {
		DbfHeader dbfHeader = DbfEngine.getHeader (
				Fp26Reader.class.getResourceAsStream("FP_26_SAMPLE.DBF"), null);
		
		System.out.println("DBF header info: "+dbfHeader.toString());
		
		Iterator<DbfColumn> iter = dbfHeader.getColumnIterator();
		while (iter.hasNext()) {
			DbfColumn column = iter.next();
			System.out.println(column.getColumnName() + " "+column.getDbfColumnType());
		}
		
/*		DbfIterator dbfIterator = dbfHeader.getDbfIterator();
		dbfIterator.closeIterator();
*/	
		dbfHeader.closeDbfHeader();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Fp26ReaderHeader.testRead();
	}

}
