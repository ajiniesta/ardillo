package com.iniesta.ardillo;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import com.iniesta.ardillo.domain.ArdilloConnection;

public class TestConnection extends TestCase {

	public void test1() throws ClassNotFoundException, SQLException{
		ArdilloConnection aconn = new ArdilloConnection();
		aconn.setDbms("h2:file");
		aconn.setHost("data/ardillo");
		aconn.setPort(null);
		aconn.setDriver("org.h2.Driver");
		aconn.setUser("SA");
		aconn.setPassword("");
		
		Connection connection = aconn.getConnection();
		assertNotNull(connection);
		connection.close();
	}
}
