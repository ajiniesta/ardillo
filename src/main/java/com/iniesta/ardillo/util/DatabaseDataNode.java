package com.iniesta.ardillo.util;

import com.iniesta.ardillo.domain.ArdilloConnection;

public class DatabaseDataNode extends DataNode {

	ArdilloConnection ardilloConnection;
	
	public DatabaseDataNode(String tableName, ArdilloConnection ardilloConnection) {
		super(tableName);
		this.ardilloConnection = ardilloConnection;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DatabaseDataNode [getNodeName()=" + getNodeName() + "]";
	}

	public ArdilloConnection getArdilloConnection(){
		return ardilloConnection;
	}
}
