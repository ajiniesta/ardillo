package com.iniesta.ardillo.services;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.iniesta.ardillo.domain.DatabaseType;
import com.iniesta.ardillo.util.Environment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LoadDriversService extends Service<ObservableList<DatabaseType>> {

	private static final Logger logger = Logger.getLogger(LoadDriversService.class);
	
	private DatabaseType defaultH2(){
		return new DatabaseType("H2 File", "org.h2.Driver", "h2:file");
	}	

	private DatabaseType defaultH2Server(){
		return new DatabaseType("H2 Server", "org.h2.Driver", "h2:tcp");
	}	

	@Override
	protected Task<ObservableList<DatabaseType>> createTask() {
		return new Task<ObservableList<DatabaseType>>() {
			@Override
			protected ObservableList<DatabaseType> call() throws Exception {
				ObservableList<DatabaseType> supportedDatabases = FXCollections.observableArrayList();
				supportedDatabases.addAll(defaultH2(), defaultH2Server());
				try{
					Properties drivers = new Properties();
					drivers.load(new FileInputStream(new File(Environment.getConfDir()+"/drivers.properties")));
					Set<String> keys = getKeys(drivers);					
					for (String key : keys) {
						DatabaseType dt = extractKey(drivers, key);
						if(isLoaded(dt)){
							supportedDatabases.add(dt);
						}
					}
				}catch(Exception e){
					logger.error("Error during the driver detection", e);
				}
				return supportedDatabases;
			}
		};
	}

	private Set<String> getKeys(Properties props){
		Set<String> keys = new HashSet<String>();
		Set<Object> propertiesKeySet = props.keySet();
		for (Object object : propertiesKeySet) {
			String realKey = object.toString();
			String[] split = realKey.split("\\.");
			if(split!=null && split.length>1){
				keys.add(split[1]);
			}
		}
		return keys;
	}
	
	private DatabaseType extractKey(Properties props, String key){
		String realKeyPrefix = "driver."+key; 
		String name = props.getProperty(realKeyPrefix+".name");
		String clazz = props.getProperty(realKeyPrefix+".class");
		String dburlmode = props.getProperty(realKeyPrefix+".dburlmode");
		return new DatabaseType(name, clazz, dburlmode);
	}
	
	private boolean isLoaded(DatabaseType dt){
		boolean isLoaded = false;
		try {
			Class.forName(dt.getDriverClass());
			isLoaded = true;
		} catch (ClassNotFoundException e) {
		}
		return isLoaded;
	}
}
