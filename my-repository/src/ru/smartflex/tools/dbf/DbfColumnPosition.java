package ru.smartflex.tools.dbf;

/**
 * Contains position data for xBase field.
 * @author galisha
 * @since 1.00
 */
public class DbfColumnPosition {
	private int offset = -1;
	private int columnLength = 0;
	private int columnDotAmount = 0;

	protected DbfColumnPosition(int offset, int columnLength,
			int columnDotAmount) {
		super();
		this.offset = offset;
		this.columnLength = columnLength;
		this.columnDotAmount = columnDotAmount;
	}

	protected DbfColumnPosition(int columnLength, int columnDotAmount) {
		super();
		this.columnLength = columnLength;
		this.columnDotAmount = columnDotAmount;
	}

	protected int getOffset() {
		return offset;
	}

	protected void setOffset(int offset) {
		this.offset = offset;
	}

	protected int getColumnLength() {
		return columnLength;
	}

	protected void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	protected int getColumnDotAmount() {
		return columnDotAmount;
	}

	protected void setColumnDotAmount(int columnDotAmount) {
		this.columnDotAmount = columnDotAmount;
	}

	/**
	 * toString method
	 * @since 1.07
	 */
	@Override
	public String toString() {
		return "DbfColumnPosition [offset=" + offset + ", columnLength="
				+ columnLength + "]";
	}

}
