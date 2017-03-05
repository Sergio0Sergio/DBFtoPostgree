package ru.smartflex.tools.dbf.test;

import java.math.BigDecimal;
import java.util.Date;

import ru.smartflex.tools.dbf.DbfAppender;
import ru.smartflex.tools.dbf.DbfCodePages;
import ru.smartflex.tools.dbf.DbfColumn;
import ru.smartflex.tools.dbf.DbfColumnTypes;
import ru.smartflex.tools.dbf.DbfEngine;
import ru.smartflex.tools.dbf.DbfStatement;

/**
 * Sample class for writing filled dbf file.
 * @author galisha
 * @since 1.00
 */
public class Fp26Writer {

	private static void testWrite() {
		DbfAppender dbfAppender = DbfEngine.getWriter("WRT_PERSON.DBF", DbfCodePages.Cp866);
		DbfColumn dc01 = new DbfColumn("magic", DbfColumnTypes.Logical, 0, 0);
		DbfColumn dc02 = new DbfColumn("actor", DbfColumnTypes.Character, 60, 0);
		DbfColumn dc03 = new DbfColumn("currdate", DbfColumnTypes.Date, 0, 0);
		DbfColumn dc04 = new DbfColumn("hit", DbfColumnTypes.Numeric, 10, 2);
		DbfColumn dc05 = new DbfColumn("forever", DbfColumnTypes.Logical, 0, 0);
		dbfAppender.defineColumns(dc01,dc02, dc03, dc04, dc05);
		
		DbfStatement statement = dbfAppender.getStatement();
		statement.setString("actor", "Chuck Norris");
		statement.setDate("currdate", new Date());
		statement.setBigDecimal("hit", new BigDecimal("500.5"));
		statement.insertStatement();

		statement.setBoolean("magic", Boolean.TRUE);
		statement.setString("actor", "Bruce Lee");
		statement.setBigDecimal("hit", new BigDecimal("1000.10"));
		statement.setBoolean("forever", Boolean.TRUE);
		statement.insertStatement();
		
		dbfAppender.writeDbfAndClose();
	}
	public static void main(String[] args) {
		Fp26Writer.testWrite();
	}

}
