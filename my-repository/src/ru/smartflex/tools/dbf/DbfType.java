package ru.smartflex.tools.dbf;

/**
 * The class <code>DbfType</code> describes supported dbf formats. 
 * @author galisha
 * @since 1.00
 */
public class DbfType {

//	public final static int FoxBase = 0x02;
	public final static int FoxBASE_dBASE_III_PLUS_without_memo = 0x03;
/*	public final static int Visual_FoxPro = 0x30;
	public final static int dBASE_IV_SQL_without_memo = 0x43;
	public final static int dBASE_IV_SQL_system_without_memo = 0x33;
	public final static int FoxBASE_PLUS_dBASE_III_PLUS_with_memo = 0x83;
	public final static int dBASE_IV_with_memo = 0x8B;
	public final static int dBASE_IV_SQL_with_memo = 0xCB;
	public final static int FoxPro_2_x_with_memo = 0xF5;
	public final static int FoxBASE = 0xFB;
*/
	private DbfType(){
	}
	/**
	 * Returns format name.
	 * @param type code xBase file
	 * @return format name
	 */
	public static String getTypeDescription(int type) {
		String ret = "Unknow type";
		switch (type) {
/*		case FoxBase:
			ret = "FoxBase";
			break;
*/		case FoxBASE_dBASE_III_PLUS_without_memo:
			ret = "FoxBASE_dBASE_III_PLUS_without_memo";
			break;
/*		case Visual_FoxPro:
			ret = "Visual_FoxPro";
			break;
		case dBASE_IV_SQL_without_memo:
			ret = "dBASE_IV_SQL_without_memo";
			break;
		case dBASE_IV_SQL_system_without_memo:
			ret = "dBASE_IV_SQL_system_without_memo";
			break;
		case FoxBASE_PLUS_dBASE_III_PLUS_with_memo:
			ret = "FoxBASE_PLUS_dBASE_III_PLUS_with_memo";
			break;
		case dBASE_IV_with_memo:
			ret = "dBASE_IV_with_memo";
			break;
		case dBASE_IV_SQL_with_memo:
			ret = "dBASE_IV_SQL_with_memo";
			break;
		case FoxPro_2_x_with_memo:
			ret = "FoxPro_2_x_with_memo";
			break;
		case FoxBASE:
			ret = "FoxBASE";
			break;
*/		}
		return ret;
	}

}
