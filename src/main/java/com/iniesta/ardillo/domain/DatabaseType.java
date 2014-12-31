package com.iniesta.ardillo.domain;

public class DatabaseType {

	private String databaseName;

	private String driverClass;

	private String fileName;

	public DatabaseType(String databaseName, String driverClass, String fileName) {
		super();
		this.databaseName = databaseName;
		this.driverClass = driverClass;
		this.fileName = fileName;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((databaseName == null) ? 0 : databaseName.hashCode());
		result = prime * result + ((driverClass == null) ? 0 : driverClass.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
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
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DatabaseType [databaseName=" + databaseName + ", driverClass=" + driverClass + ", fileName=" + fileName + "]";
	}

}
