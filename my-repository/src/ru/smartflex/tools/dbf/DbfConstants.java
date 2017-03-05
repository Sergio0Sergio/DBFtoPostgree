package ru.smartflex.tools.dbf;

/**
 * Contains literals and special constants.
 * @author galisha
 * @since 1.00 
 */
public interface DbfConstants {

	public static final String DBF_ENGINE_VERSION = "1.07";
	
	public static final String DEFAULT_CODE_PAGE = "Cp866";
	
	public static final String EXCP_CODE_PAGE = "Code page is unknown";
	public static final String EXCP_IO_ERROR = "IO error with dbf";
	public static final String EXCP_HEADER_INF = "No information about dbf file header";
	public static final String EXCP_HEADER_END = "Unexpected end of file by header";
	public static final String EXCP_COLUMN_NOEXISTS = "Field is not existed: ";
	public static final String EXCP_COLUMN_COL_TYPE = "There type mismatch for field: ";
	public static final String EXCP_COLUMN_CP = "Error with supported code page";
	public static final String EXCP_COLUMN_NUM = "Error with numeric format";
	public static final String EXCP_HEADER_LEN = "DBF length record is not defined";
	public static final String EXCP_REC_UNEXP = "Unexpected end of file by content. Was read records: ";
	public static final String EXCP_COLUMN_DT = "Error with date format";
	public static final String EXCP_DBF_EXISTS = "File is existed already";
	public static final String EXCP_DBF_ERR_CREATE = "Error with file creating";
	public static final String EXCP_CURR_REC_INFO = " on record: ";
	
	public static final String EXCP_COLUMN_ADD = "There were no column added";
	public static final String EXCP_COLUMN_EMPTY = "Column has to be named";
	public static final String EXCP_COLUMN_EXISTED = "Column already existed: ";
	public static final String EXCP_COLUMN_NAMELEN = "Column length exceed 10 symbols";
	public static final String EXCP_COLUMN_NOTYPE = "Column must have type";
	public static final String EXCP_COLUMN_NODEC = "Column must not have dec value";
	public static final String EXCP_COLUMN_CHARLEN = "Column must be between 1 and 254 symbols";
	public static final String EXCP_COLUMN_NUMLEN = "Column must be between 1 and 20 digit";
	public static final String EXCP_COLUMN_NUMDECLEN = "Column must have normal dec value";
	public static final String EXCP_COLUMN_VAL_TOO_BIG = "Value too big for column: ";
	public static final String EXCP_CP_MISSED = "There must be setup code page info";
	public static final String EXCP_CP_ARITHERR = "There is ArithmeticException";
	
	public static final int DBF_HEADER_LENGTH = 32;
	public static final int DBF_COLUMN_LENGTH = 32;
	public static final int DBF_COLUMN_NAME_LENGTH = 10;
	public static final int DBF_COLUMN_CHAR_MAX_LEN = 254;
	public static final int DBF_COLUMN_NUM_MAX_LEN = 20;
	public static final int DBF_END_HEADER = 0x0D;
	public static final int DBF_REC_FILLSYMB = 0x20;
	public static final int DBF_END_OF_FILE = 0x1A;
	
	public static final byte LOGICAL_TRUE = 'T';
	public static final byte LOGICAL_FALSE = 'F';
	public static final byte DELETED_MARKER = '*';

}
