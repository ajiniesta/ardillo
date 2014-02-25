package com.iniesta.ardillo.screen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.util.DatabaseDataNode;

import junit.framework.TestCase;

public class TestingDatabaseDetails extends TestCase{

	public void test1() throws ClassNotFoundException, SQLException{
		ArdilloConnection aconn = new ArdilloConnection();
		aconn.setName("data_ardillo");
		aconn.setDbms("h2:file");
		aconn.setHost("data/ardillo");
		aconn.setPort(null);
		aconn.setDriver("org.h2.Driver");
		aconn.setUser("SA");
		aconn.setPassword("");
		aconn.setPrefixDB("PUBLIC");
		
		Connection connection = aconn.createConnection();
		assertNotNull(connection);
		List<DatabaseDataNode> tables = DatabaseDetail.calculateTables(aconn);
		assertNotNull(tables);
		assertTrue(tables.size()>0);
		System.out.println(tables);
		connection.close();
	}
}
