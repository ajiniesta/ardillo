package com.iniesta.ardillo.util;

import com.iniesta.ardillo.domain.ArdilloConnection;

public class ConnectionWithDataNode extends ConnectionNode{

	private ArdilloConnection ardillConnection;
	
	public ConnectionWithDataNode(ArdilloConnection ardilloConnection){
		super();
		ardillConnection = ardilloConnection;
	}

	/* (non-Javadoc)
	 * @see com.iniesta.ardillo.util.ConnectionNode#getNodeName()
	 */
	@Override
	public String getNodeName() {
		String nodeName = null;
		if(ardillConnection!=null){
			nodeName = ardillConnection.getName();
		}
		return nodeName;
	}	
	
}
