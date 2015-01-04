package com.iniesta.ardillo;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.util.HibernateUtil;

public class TestingDAOConnection {

	@BeforeClass
	public static void setup() throws Exception {

		Field field = HibernateUtil.class.getDeclaredField("sessionFactory");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, HibernateUtil.testSessionFactory());
	}

	@Test
	public void testSave() {
		DAOConnection dao = new DAOConnection();
		ArdilloConnection conn = new ArdilloConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss.S");
		String date = sdf.format(new Date());
		conn.setName("del" + date);

		Integer id = dao.saveConnection(conn);

		assertNotNull(id);
	}

	@Test
	public void testAdd() {
		DAOConnection dao = new DAOConnection();
		ArdilloConnection aconn = new ArdilloConnection();
		aconn.setDbms("h2:file");
		aconn.setHost("data/ardillo");
		aconn.setPort(null);
		aconn.setDriver("org.h2.Driver");
		aconn.setUser("SA");
		aconn.setPassword("");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss.S");
		String date = sdf.format(new Date());
		aconn.setName("del" + date);

		Integer id = dao.saveConnection(aconn);
		assertNotNull(id);
	}

	@Test
	public void testListing() {
		DAOConnection dao = new DAOConnection();
		List<ArdilloConnection> list = dao.listConnections();
		assertNotNull(list);
		for (ArdilloConnection conn : list) {
			System.out.println(conn);
		}
	}

}