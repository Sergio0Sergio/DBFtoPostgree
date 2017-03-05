package ru.smartflex.tools.dbf.mem;

import java.util.Date;

/**
 * Object container for xBase (Foxpro and Clipper) memory arrays
 * 
 * @author galisha
 * @since 1.05
 */
public class MemArray {

	private int rows = 0;
	private int cols = 0;
	private int size = 0;
	private int index = 0;

	private Object[] objs = null;

	protected MemArray(int rows, int cols) {
		super();
		this.rows = rows;
		this.cols = cols;

		size = rows;
		if (cols > 0) {
			size = rows * cols;
		}
		objs = new Object[size];
	}

	protected int getRows() {
		return rows;
	}

	protected int getCols() {
		return cols;
	}

	private void checkPossibleToSave() {
		if (index >= size)
			throw new MemEngineException(MemConstants.EXCP_INDEX_OUT_OF_BOUND
					+ String.valueOf(size));
	}

	protected Object getElement(int row, int col) {
		Object obj = null;

		if (cols > 0 && col > cols) {
			throw new MemEngineException(MemConstants.EXCP_ARRAY_OVER_COL
					+ String.valueOf(rows));
		}
		if (row > rows) {
			throw new MemEngineException(MemConstants.EXCP_ARRAY_OVER_ROW
					+ String.valueOf(rows));
		}
		if (cols > 0) {
			if (col <= 0) {
				throw new MemEngineException(MemConstants.EXCP_ARRAY_COL_BAD);
			}
		}
		if (row <= 0) {
			throw new MemEngineException(MemConstants.EXCP_ARRAY_ROW_BAD);
		}

		int index = -1;
		if (cols == 0) {
			index = row - 1;
		} else {
			index = ((row - 1) * 2 + col) - 1;
		}

		obj = objs[index];

		return obj;
	}

	protected void addDate(Date d) {
		checkPossibleToSave();

		objs[index++] = d;
	}

	protected void addString(String s) {
		checkPossibleToSave();

		objs[index++] = s;
	}

	protected void addBoolean(boolean b) {
		checkPossibleToSave();

		if (b) {
			objs[index++] = Boolean.TRUE;
		} else {
			objs[index++] = Boolean.FALSE;
		}
	}

	protected void addDouble(double d) {
		checkPossibleToSave();

		objs[index++] = Double.valueOf(d);
	}

	protected boolean isAbbleToAppend() {
		boolean fok = true;
		if (index >= size) {
			fok = false;
		}
		return fok;
	}
}
