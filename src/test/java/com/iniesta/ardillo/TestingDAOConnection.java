package com.iniesta.ardillo;

import junit.framework.TestCase;
import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestingDAOConnection extends TestCase{
	public void testSave(){
		DAOConnection dao = new DAOConnection();
		ArdilloConnection conn = new ArdilloConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmm");
		String date = sdf.format(new Date());
		conn.setName("del"+date);
		
		Integer id = dao.saveConnection(conn);

		assertNotNull(id);
	}
	
	public void testAdd(){
		DAOConnection dao = new DAOConnection();
		ArdilloConnection aconn = new ArdilloConnection();
		aconn.setDbms("h2:file");
		aconn.setHost("data/ardillo");
		aconn.setPort(null);
		aconn.setDriver("org.h2.Driver");
		aconn.setUser("SA");
		aconn.setPassword("");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmm");
		String date = sdf.format(new Date());
		aconn.setName("del"+date);
		
		Integer id = dao.saveConnection(aconn);
		assertNotNull(id);
	}

	public void testListing(){
		DAOConnection dao = new DAOConnection();
		List<ArdilloConnection> list = dao.listConnections();
		assertNotNull(list);
		for(ArdilloConnection conn : list){
			System.out.println(conn);
		}
	}


}