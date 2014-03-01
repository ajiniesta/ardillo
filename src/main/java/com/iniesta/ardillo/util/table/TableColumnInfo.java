package com.iniesta.ardillo.util.table;


public class TableColumnInfo {

	public enum ColumnType {
		STRING(new StringField(null)), //
		NUMBER(new NumberField(null)), //
		BOOLEAN(new BooleanField(null)), //
		BINARY(new BinaryField());

		private Field<?> type;

		private ColumnType(Field<?> type) {
			this.type = type;
		}

		public Field<?> getType() {
			return type;
		}

	}

	private String title;
	private String columnNameSql;
	private ColumnType type;
	private double width;

	/**
	 * @param title
	 * @param type
	 */
	public TableColumnInfo(String title, ColumnType type) {
		super();
		this.title = title;
		this.type = type;
	}

	public TableColumnInfo(String title, String columnNameSql, ColumnType type) {
		super();
		this.title = title;
		this.columnNameSql = columnNameSql;
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ColumnType getType() {
		return type;
	}

	public void setType(ColumnType type) {
		this.type = type;
	}

	public String getColumnNameSql() {
		return columnNameSql;
	}

	public void setColumnNameSql(String columnNameSql) {
		this.columnNameSql = columnNameSql;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnNameSql == null) ? 0 : columnNameSql.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TableColumnInfo))
			return false;
		TableColumnInfo other = (TableColumnInfo) obj;
		if (columnNameSql == null) {
			if (other.columnNameSql != null)
				return false;
		} else if (!columnNameSql.equals(other.columnNameSql))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TableColumnInfo [title=" + title + ", columnNameSql=" + columnNameSql + ", type=" + type + "]";
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

}
