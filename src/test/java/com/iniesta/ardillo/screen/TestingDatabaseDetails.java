package com.iniesta.ardillo.screen;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.util.DatabaseDataNode;
import com.iniesta.ardillo.util.dddbb.MetaDataCalculations;

public class TestingDatabaseDetails {

	@Test
	public void test1() throws ClassNotFoundException, SQLException{
		ArdilloConnection aconn = new ArdilloConnection();
		aconn.setName("data_ardillo");
		aconn.setDbms("h2:file");
		aconn.setHost("data/ardillo");
		aconn.setPort(null);
		aconn.setDriver("org.h2.Driver");
		aconn.setUser("SA");
		aconn.setPassword("");
		aconn.setSchema("PUBLIC");
		
		Connection connection = aconn.createConnection();
		assertNotNull(connection);
		List<DatabaseDataNode> tables = MetaDataCalculations.calculateTables(aconn);
		assertNotNull(tables);
		assertTrue(tables.size()>0);
		System.out.println(tables);
		connection.close();
	}
	
	@Test
	public void test2() throws ClassNotFoundException, SQLException{
		ArdilloConnection aconn = new ArdilloConnection();
		aconn.setName("data_ardillo");
		aconn.setDbms("h2:mem");
		aconn.setHost("");
		aconn.setPort(null);
		aconn.setDriver("org.h2.Driver");
		aconn.setUser("SA");
		aconn.setPassword("");
//		aconn.setSchema();
		
		Connection connection = aconn.createConnection();
		assertNotNull(connection);
		List<DatabaseDataNode> tables = MetaDataCalculations.calculateTables(aconn);
		assertNotNull(tables);
		assertTrue(tables.size()>0);
		System.out.println(tables);
		connection.close();
	}
}
