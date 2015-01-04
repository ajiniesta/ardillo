package com.iniesta.ardillo.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.iniesta.ardillo.domain.ArdilloConnection;

@SuppressWarnings("deprecation")
public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new AnnotationConfiguration() //
				.configure() //
				.addPackage("com.iniesta.ardillo.domain") //the fully qualified package name
				.addAnnotatedClass(ArdilloConnection.class) //
				.buildSessionFactory(); //	
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static SessionFactory testSessionFactory() {
		return new AnnotationConfiguration() //
			.configure() //
			.setProperty("hibernate.connection.url", "jdbc:h2:mem") //
			.addPackage("com.iniesta.ardillo.domain") //
			.addAnnotatedClass(ArdilloConnection.class) //
			.buildSessionFactory(); //
	}

}