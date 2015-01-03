package com.iniesta.ardillo.services;

import javafx.concurrent.Task;

import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.domain.DatabaseType;
import com.iniesta.ardillo.util.MapScreen;

public class DatabaseServices {

	public static StatusService<ArdilloConnection> serviceSave(final MapScreen mapScreen) {
		return new StatusService<ArdilloConnection>() {
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
						DatabaseType dt = mapScreen.getDatabaseTypeParam("comboBoxDatabaseType");
						value.setUser(mapScreen.getTextFieldParam("textFieldUsername"));
						value.setPassword(mapScreen.getTextFieldParam("passwordField"));
						value.setDriver(dt.getDriverClass());
						value.setSchema(mapScreen.getTextFieldParam("textFieldSchema"));
						value.setDbms(dt.getDbms());
						Integer id = new DAOConnection().saveConnection(value);
						if (id == null) {
							updateMessage("Problem saving");
							setErrorMode(true);
						}
						
						if(!isValid(value)){
							value = new ArdilloConnection();
							updateMessage("Invalid connection");
							setErrorMode(true);
						}

						return value;
					}

					/**
					 * TODO a more refined way to detect an invalid ardillo connection
					 * @param value
					 * @return
					 */
					private boolean isValid(ArdilloConnection value) {
						return true;
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

}
