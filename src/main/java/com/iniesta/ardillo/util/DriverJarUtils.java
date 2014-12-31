package com.iniesta.ardillo.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.Set;

import org.reflections.Reflections;

import com.iniesta.ardillo.domain.DatabaseType;

public class DriverJarUtils {
	
	private Set<Class<? extends Driver>> drivers;

	public DriverJarUtils(){
		extractAllJDBCDrivers();
	}
	
	public DatabaseType extractDatabaseType(File file) {
		DatabaseType dt = null;
		try {
			addURL(file.toURI().toURL());
			extractAllJDBCDrivers();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	public Set<Class<? extends Driver>> extractAllJDBCDrivers(){
		Reflections reflections = new Reflections("org");
		drivers = reflections.getSubTypesOf(Driver.class);
		return drivers;
	}
	
	public static void addURL(URL u) throws IOException {
        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<?> sysclass = URLClassLoader.class;

        try {
            Method method = sysclass.getDeclaredMethod("addURL", new Class[] {URL.class});
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] {u});
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }

    }
}
