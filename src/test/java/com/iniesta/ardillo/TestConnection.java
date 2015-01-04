package com.iniesta.ardillo;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.iniesta.ardillo.domain.ArdilloConnection;

public class TestConnection {

	@Test
	public void test1() throws ClassNotFoundException, SQLException{
		ArdilloConnection aconn = new ArdilloConnection();
		aconn.setDbms("h2:mem");
		aconn.setPort(null);
		aconn.setDriver("org.h2.Driver");
		aconn.setUser("SA");
		aconn.setPassword("");
		
		Connection connection = aconn.createConnection();
		assertNotNull(connection);
		connection.close();
	}
}
