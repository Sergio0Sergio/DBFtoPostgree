package ru.smartflex.tools.dbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Contains dbf header definition.
 * 
 * @author galisha
 * @since 1.00
 */
public class DbfHeader {

	private int currentOffset = 1;
	private int firstRecordPosition = 0;
	private int countRecords = 0;
	private int typeDbf = -1;
	private int lengthRecord = 0;

	private Map<String, DbfColumn> columns = new HashMap<String, DbfColumn>();
	private String codePage = null;
	private String defaultCodePage = DbfConstants.DEFAULT_CODE_PAGE;
	private InputStream dbfStream = null;
	private List<DbfColumn> orderedColumns = new ArrayList<DbfColumn>();

	private DbfIterator dbfIterator = null;

	protected DbfHeader(File dbfFile, String enc, DbfIterator dbfIterator) {
		// Open for reading
		codePageHandling(enc);
		parseDbfHeader(dbfFile);
		this.dbfIterator = dbfIterator;
	}

	protected DbfHeader(InputStream is, String enc, DbfIterator dbfIterator) {
		// Open for reading
		dbfStream = is;
		codePageHandling(enc);
		parseDbfHeader();
		this.dbfIterator = dbfIterator;
	}

	/**
	 * Returns iterator.
	 * 
	 * @return iterator.
	 * @since 1.05
	 */
	public DbfIterator getDbfIterator() {
		return this.dbfIterator;
	}

	/**
	 * Closes dbf stream (and also iterator).
	 * 
	 * @see DbfIterator.closeIterator()
	 * @since 1.05
	 */
	public void closeDbfHeader() {
		this.dbfIterator.closeIterator();
	}

	/**
	 * Checks column name in dbf header.
	 * 
	 * @param columnName
	 * @return true if column is existed
	 * @since 1.05
	 */
	public boolean isColumnExisted(String columnName) {
		boolean fok = false;

		if (columnName != null) {
			String cn = columnName.toUpperCase();
			if (columns.get(cn) != null) {
				fok = true;
			}
		}

		return fok;
	}

	/**
	 * Checks entirely array of column names in dbf header.
	 * 
	 * @param columnNames
	 * @return
	 * @since 1.06
	 */
	public boolean isColumnExisted(String... columnNames) {
		boolean fok = false;

		if (columnNames != null && columnNames.length > 0) {
			fok = true;
			for (String name : columnNames) {
				if (columns.get(name.toUpperCase()) == null) {
					fok = false;
					break;
				}
			}
		}
		return fok;
	}

	protected DbfHeader() {
		// Create for append
	}

	private void codePageHandling(String enc) {
		if (enc != null) {
			defaultCodePage = enc;
		}
	}

	protected List<DbfColumn> getOrderedColumnList() {
		return orderedColumns;
	}

	/**
	 * Returns dbf column iterator.
	 * 
	 * @return dbf column iterator
	 * @since 1.05
	 */
	public Iterator<DbfColumn> getColumnIterator() {
		return orderedColumns.iterator();
	}

	protected void addColumn(DbfColumn dbfColumn) {
		if (columns.get(dbfColumn.getColumnName()) != null) {
			throw new DbfEngineException(DbfConstants.EXCP_COLUMN_EXISTED
					+ dbfColumn.getColumnName());
		}
		columns.put(dbfColumn.getColumnName(), dbfColumn);
		orderedColumns.add(dbfColumn);
	}

	protected DbfColumn getColumn(String name) {
		return columns.get(name);
	}

	private void parseDbfHeader(File dbf) {
		try {
			dbfStream = new FileInputStream(dbf);
			parseDbfHeader();
		} catch (FileNotFoundException e) {
			throw new DbfEngineException(DbfConstants.EXCP_IO_ERROR, e);
		}
	}

	private void parseDbfHeader() {
		try {
			byte[] hdr = new byte[32];
			int cnt = dbfStream.read(hdr);
			if (cnt != DbfConstants.DBF_HEADER_LENGTH) {
				dbfStream.close();
				throw new DbfEngineException(DbfConstants.EXCP_HEADER_INF);
			}
			typeDbf = hdr[0] & 0xff;
			// Skip Last update (YYMMDD)
			// Read count of records
			countRecords = hdr[4] & 0xff;
			countRecords += (hdr[5] & 0xff) << 8;
			countRecords += (hdr[6] & 0xff) << 16;
			countRecords += (hdr[7] & 0xff) << 24;
			// Read first record position
			firstRecordPosition = hdr[8] & 0xff;
			firstRecordPosition += (hdr[9] & 0xff) << 8;
			// Read length
			lengthRecord = hdr[10] & 0xff;
			lengthRecord += (hdr[11] & 0xff) << 8;
			// Code page mark
			int cp = hdr[29] & 0xff;
			// System.out.println("cp "+cp);
			if (cp == 0) {
				codePage = defaultCodePage;
			} else {
				DbfCodePages dcp = DbfCodePages.Cp1251.getByDbfCode(cp);
				if (dcp == null) {
					throw new DbfEngineException(DbfConstants.EXCP_CODE_PAGE);
				} else {
					codePage = dcp.getCharsetName();
				}
			}
			int currentOffset = 32;
			
			// Fields
			byte[] fld = new byte[32];
			boolean next = true;
			do {
				// cnt = dbfStream.read(fld);
				int flagByte = dbfStream.read();
				if (flagByte == -1) {
					throw new DbfEngineException(DbfConstants.EXCP_HEADER_END);
				}
				// if (cnt < 32)
				// next = false;
				// if (fld[0] == 0x0D)
				if (flagByte == DbfConstants.DBF_END_HEADER)
					next = false;
				currentOffset++;
				if (next) {
					fld[0] = (byte) flagByte;
					dbfStream.read(fld, 1, 31);
					DbfColumn dbfColumn = new DbfColumn(this); 
					dbfColumn.parse(fld);
					// some validation
					if (dbfColumn.getColumnName() == null) {
						throw new DbfEngineException(DbfConstants.EXCP_COLUMN_EMPTY);
					}
					if (dbfColumn.getDbfColumnType() == null) {
						throw new DbfEngineException(DbfConstants.EXCP_COLUMN_NOTYPE);
					}
					columns.put(dbfColumn.getColumnName(), dbfColumn);
					orderedColumns.add(dbfColumn);
					
					currentOffset += 31;
				}
			} while (next);
			
			if (currentOffset < firstRecordPosition) {
				do {
					int flagByte = dbfStream.read();
					// without any zero analyze yet
					if (flagByte == 0x00) {
						// it is OK
					} else {
						// has to do something
					}
					currentOffset++;
				} while (currentOffset < firstRecordPosition);
			}
		} catch (Exception e) {
			throw new DbfEngineException(DbfConstants.EXCP_IO_ERROR, e);
		}
	}

	protected int getCurrentOffset() {
		return currentOffset;
	}

	protected void setCurrentOffset(int currentOffset) {
		this.currentOffset = currentOffset;
	}

	protected int getFirstRecordPosition() {
		return firstRecordPosition;
	}

	protected void setFirstRecordPosition(int firstRecordPosition) {
		this.firstRecordPosition = firstRecordPosition;
	}

	/**
	 * Returns all amount of records with deleted rows.
	 * 
	 * @return amount of records
	 * @since 1.05
	 */
	public int getCountRecords() {
		return countRecords;
	}

	protected void setCountRecords(int countRecords) {
		this.countRecords = countRecords;
	}

	protected String getCodePage() {
		return codePage;
	}

	protected int getTypeDbf() {
		if (typeDbf == -1) {
			// TODO check for memo field
			/*
			 * for (DbfColumn dc : orderedColumns){ }
			 */
			typeDbf = DbfType.FoxBASE_dBASE_III_PLUS_without_memo;
		}
		return typeDbf;
	}

	protected void setTypeDbf(int typeDbf) {
		this.typeDbf = typeDbf;
	}

	protected int getLengthRecord() {
		if (lengthRecord == 0) {
			for (DbfColumn dc : orderedColumns) {
				lengthRecord += dc.getDbfColumnPosition().getColumnLength();
			}
			lengthRecord++; // 1 byte for deleted flag
		}
		return lengthRecord;
	}

	protected InputStream getDbfStream() {
		return dbfStream;
	}

	/**
	 * Returns count of column.
	 * 
	 * @return column count
	 * @since 1.05
	 */
	public int getCountColumns() {
		return orderedColumns.size();
	}

	/**
	 * To String.
	 * 
	 * @since 1.05
	 */
	@Override
	public String toString() {
		return "DbfHeader [firstRecordPosition=" + firstRecordPosition
				+ ", countRecords=" + countRecords + ", countColumns="
				+ getCountColumns() + ", lengthRecord=" + lengthRecord
				+ ", codePage=" + codePage + "]";
	}

	/**
	 * Validates dbf header
	 * 
	 * @return true if header is ok
	 * @since 1.06
	 */
	public boolean isDbfHeaderValid() {
		boolean fok = true;
		if (orderedColumns.size() == 0) {
			fok = false;
		}
		if (fok) {
			for (DbfColumn dc : orderedColumns) {
				if (dc.getColumnName() == null) {
					fok = false;
					break;
				}
				if (dc.getDbfColumnType() == null) {
					fok = false;
					break;
				}
			}
		}
		return fok;
	}

}
