package ru.smartflex.tools.dbf.mem;

/**
 * Contains literals and special constants.
 * @author galisha
 * @since 1.05
 */
public interface MemConstants {

	public static final int LENGTH_NUMERIC = 8;
	public static final int LENGTH_DATE = 8;
	public static final int LENGTH_LOGICAL = 1;
	public static final int LENGTH_ARRAY_DIM = 4;
	
	public static final byte MEM_FILE_EOF_BYTE = 0x1A;
	
	public static final String EXCP_IO_ERROR = "IO error with mem";
	public static final String EXCP_EOF = "Unexpected end of data";
	public static final String EXCP_LN_POS = "Length must be positive";
	public static final String EXCP_FILE_PROBLEM = "Some file error";
	public static final String EXCP_INDEX_OUT_OF_BOUND = "Index array request is more than array size: ";
	public static final String EXCP_MISS_MEM_NAME = "Memory name is missed";
	public static final String EXCP_NF_VALUE_MEM_NAME = "Not found value for that memory name: ";
	public static final String EXCP_STRING_CREATE = "Some error with string creation";
	public static final String EXCP_ARRAY_OVER_ROW = "Row index exceeded the maximum allowable value: ";
	public static final String EXCP_ARRAY_OVER_COL = "Col index exceeded the maximum allowable value: ";
	public static final String EXCP_ARRAY_ROW_BAD = "Row index must be positive";
	public static final String EXCP_ARRAY_COL_BAD = "Col index must be positive";
}
