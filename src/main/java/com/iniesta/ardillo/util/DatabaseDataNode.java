package com.iniesta.ardillo.util;

public class DatabaseDataNode extends DataNode {

	public DatabaseDataNode(String tableName) {
		super(tableName);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DatabaseDataNode [getNodeName()=" + getNodeName() + "]";
	}

	
}
