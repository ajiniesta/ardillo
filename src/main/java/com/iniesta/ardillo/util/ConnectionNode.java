package com.iniesta.ardillo.util;

import com.iniesta.ardillo.domain.ArdilloConnection;

public class ConnectionNode extends DataNode {
	private ArdilloConnection ardillConnection;

	public ConnectionNode(ArdilloConnection ardilloConnection) {
		super();
		ardillConnection = ardilloConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iniesta.ardillo.util.ConnectionNode#getNodeName()
	 */
	@Override
	public String getNodeName() {
		String nodeName = null;
		if (ardillConnection != null) {
			nodeName = ardillConnection.getName();
		}
		return nodeName;
	}

	/**
	 * @return the ardillConnection
	 */
	public ArdilloConnection getArdillConnection() {
		return ardillConnection;
	}
}
