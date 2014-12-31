package com.iniesta.ardillo.services;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.domain.DatabaseType;
import com.iniesta.ardillo.util.DriverJarUtils;
import com.iniesta.ardillo.util.Environment;
import com.iniesta.ardillo.util.MapScreen;

public class DatabaseServices {

	public static Service<ArdilloConnection> serviceSave(final MapScreen mapScreen) {
		return new Service<ArdilloConnection>() {
			@Override
			protected Task<ArdilloConnection> createTask() {
				return new Task<ArdilloConnection>() {
					@Override
					protected ArdilloConnection call() throws Exception {
						ArdilloConnection value = null;
						value = new ArdilloConnection();
						value.setName(mapScreen.getTextFieldParam("textFieldConnectionName"));
						value.setHost(mapScreen.getTextFieldParam("textFieldHost"));
						value.setPort(calculatePort(mapScreen));
						value.setUser(mapScreen.getTextFieldParam("textFieldUsername"));
						value.setPassword(mapScreen.getTextFieldParam("passwordField"));
						value.setDriver(mapScreen.getTextFieldParam("textFieldDriver"));
						value.setSchema(mapScreen.getTextFieldParam("textFieldSchema"));
						Integer id = new DAOConnection().saveConnection(value);
						if (id == null) {
							updateMessage("Problem saving");
						}

						return value;
					}

					private Integer calculatePort(final MapScreen mapScreen) {
						String text = mapScreen.getTextFieldParam("textFieldPort");
						Integer port = -1;
						if (text != null && !text.trim().isEmpty()) {
							port = new Integer(text);
						}
						return port;
					}
				};
			}
		};
	}

	public static Service<Boolean> serviceMore(final File file) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Service<ObservableList<DatabaseType>> serviceLoadDrivers(){
		return new Service<ObservableList<DatabaseType>>() {
			@Override
			protected Task<ObservableList<DatabaseType>> createTask() {
				return new Task<ObservableList<DatabaseType>>() {
					@Override
					protected ObservableList<DatabaseType> call() throws Exception {
						ObservableList<DatabaseType> supportedDatabases = FXCollections.observableArrayList();
						File fileDriversDir = Environment.getFileDriversDir();
						for(File file : fileDriversDir.listFiles()){
							DatabaseType dt = null;
							if(isAJar(file) && (dt = new DriverJarUtils().extractDatabaseType(file))!=null){
								supportedDatabases.add(dt);
							}
						}
						return supportedDatabases;
					}

					private boolean isAJar(File file) throws IOException {
						return file.getCanonicalPath().toLowerCase().endsWith(".jar");
					}
				};
			}
		};
	}

}
