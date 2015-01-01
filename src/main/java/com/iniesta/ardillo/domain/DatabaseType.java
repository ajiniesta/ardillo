package com.iniesta.ardillo.domain;

public class DatabaseType {

	private String databaseName;

	private String driverClass;

	private String dbms;

	public DatabaseType(String databaseName, String driverClass, String dbms) {
		super();
		this.databaseName = databaseName;
		this.driverClass = driverClass;
		this.dbms = dbms;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDbms() {
		return dbms;
	}

	public void setFileName(String fileName) {
		this.dbms = fileName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((databaseName == null) ? 0 : databaseName.hashCode());
		result = prime * result + ((driverClass == null) ? 0 : driverClass.hashCode());
		result = prime * result + ((dbms == null) ? 0 : dbms.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DatabaseType))
			return false;
		DatabaseType other = (DatabaseType) obj;
		if (databaseName == null) {
			if (other.databaseName != null)
				return false;
		} else if (!databaseName.equals(other.databaseName))
			return false;
		if (driverClass == null) {
			if (other.driverClass != null)
				return false;
		} else if (!driverClass.equals(other.driverClass))
			return false;
		if (dbms == null) {
			if (other.dbms != null)
				return false;
		} else if (!dbms.equals(other.dbms))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DatabaseType [databaseName=" + databaseName + ", driverClass=" + driverClass + ", fileName=" + dbms + "]";
	}

}
