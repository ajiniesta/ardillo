package com.iniesta.ardillo;

import java.sql.Driver;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import com.iniesta.ardillo.util.DriverJarUtils;

public class DriverJarUtilsTest {

	interface A {
		
	}
	
	class B implements A{
		
	}
	
	@Before
	public void setUp(){
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception{
		DriverJarUtils dju = new DriverJarUtils();
		Set<Class<? extends Driver>> drivers = dju.extractAllJDBCDrivers();
		for (Class<? extends Driver> clazz : drivers) {
			System.out.println(clazz.getCanonicalName());
		}
	}
	
	@Test
	public void testInnerReflections06() throws Exception{
		Reflections reflections = new Reflections("com");
		Set<Class<? extends A>> types = reflections.getSubTypesOf(A.class);
		System.out.println("Number of Types... " + types.size());
		for (Class<? extends A> ser : types) {
			System.out.println(ser.getName());
		}
	}
	
	@Test
	public void testInnerReflections2() throws Exception{
		Reflections reflections = new Reflections("org");
		Set<Class<? extends Driver>> types = reflections.getSubTypesOf(Driver.class);
		System.out.println("Number of Types... " + types.size());
		for (Class<? extends Driver> ser : types) {
			System.out.println(ser.getName());
		}
		
	}
}
