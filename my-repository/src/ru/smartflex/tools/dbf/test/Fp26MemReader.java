package ru.smartflex.tools.dbf.test;

import ru.smartflex.tools.dbf.mem.MemBag;
import ru.smartflex.tools.dbf.mem.MemEngine;

/**
 * Sample class for reading (test_mem.mem) file. Foxpro program file that
 * created this example file is named: TEST_MEM.PRG
 * 
 * @author galisha
 * @since 1.05
 */
public class Fp26MemReader {

	private static void testRead() {
		MemBag memBag = MemEngine.getMemBag(
				Fp26MemReader.class.getResourceAsStream("TEST_MEM.MEM"),
				"Cp866");
		// String (character)
		System.out.println("Read one symbol from variable: tstr_1: "+memBag.getString("tstr_1"));
		System.out.println("Read english message from variable: tstr_eng: "+memBag.getString("tstr_eng"));
		System.out.println("Read russian message (Cp866) from variable: tstr_cp866: "+memBag.getString("tstr_cp866"));
		// Double (numeric)
		System.out.println("\nRead 0 from variable: tnum_0: "+memBag.getDouble("tnum_0"));
		System.out.println("Read 1 from variable: tnum_1: "+memBag.getDouble("tnum_1"));
		System.out.println("Read -1 from variable: tnum_1neg: "+memBag.getDouble("tnum_1neg"));
		System.out.println("Read pi from variable: tnum_pi: "+memBag.getDouble("tnum_pi"));
		System.out.println("Read 201401.18 from variable: tnum_rur: "+memBag.getDouble("tnum_rur"));
		System.out.println("Read -919.19 from variable: tnum_neg: "+memBag.getDouble("tnum_neg"));
		// Boolean(logical)
		System.out.println("\nRead TRUE from variable: ttrue: "+memBag.getBoolean("ttrue"));
		System.out.println("Read FALSE from variable: tfalse: "+memBag.getBoolean("tfalse"));
		// Date
		System.out.println("\nRead 20.01.2014 from variable: tdt_140120: "+memBag.getDate("tdt_140120"));
		System.out.println("Read 01.01.1970 from variable: tdt_700101: "+memBag.getDate("tdt_700101"));
		// Array
		System.out.println("\nRead 'A4' from array el.: tarr_one(4): "+memBag.getArrayElement("tarr_one", 4));
		System.out.println("Read 31 from array el.: tarr_two(3,1): "+memBag.getArrayElement("tarr_two", 3, 1));
		System.out.println("Read 'A 3x2' from array el.: tarr_two(3,2): "+memBag.getArrayElement("tarr_two", 3, 2));
		
	}

	public static void main(String[] args) {
		Fp26MemReader.testRead();
	}

}
