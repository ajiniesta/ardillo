package com.iniesta.ardillo.screen;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.util.ConnectionNode;
import com.iniesta.ardillo.util.ExternalBinding;


public class CreateDatabase {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> comboBoxDatabaseType;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField textFieldConnectionName;

    @FXML
    private TextField textFieldDriver;

    @FXML
    private TextField textFieldHost;

    @FXML
    private TextField textFieldPort;

    @FXML
    private TextField textFieldSchema;

    @FXML
    private TextField textFieldUsername;

	private Callback<Void, Void> callbackClose;

	private Callback<ConnectionNode, Void> callBackSuccess;

	private ExternalBinding externalBinding;

    @FXML
    void handleCancelAction(ActionEvent event) {
    	callbackClose.call(null);
    }

    @FXML
    void handleSaveAction(ActionEvent event) {
    	final Service<ArdilloConnection> serviceSave = service();
		externalBinding.bindAll(serviceSave);
		serviceSave.runningProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(!newValue){
					ArdilloConnection value = serviceSave.getValue();
					if(value!=null){
						ConnectionNode connNode = new ConnectionNode(value);
				    	callBackSuccess.call(connNode);
					}
				}
			}
		});		
    	serviceSave.start();
    }

	private Service<ArdilloConnection> service() {
		return new Service<ArdilloConnection>() {
			@Override
			protected Task<ArdilloConnection> createTask() {
				return new Task<ArdilloConnection>() {					
					@Override
					protected ArdilloConnection call() throws Exception {
						ArdilloConnection value = null;
						try{
							value = new ArdilloConnection();
							value.setName(textFieldConnectionName.getText());
							value.setHost(textFieldHost.getText());
							value.setPort(new Integer(textFieldPort.getText()));
							value.setUser(textFieldUsername.getText());
							value.setPassword(passwordField.getText());
							value.setDriver(textFieldDriver.getText());
							value.setSchema(textFieldSchema.getText());
							Integer id = new DAOConnection().saveConnection(value);
							if(id==null){
								updateMessage("Problem saving");
							}
						}catch(NumberFormatException e){
							updateMessage("Port has to be numeric");
						}
						return value;
					}
				};
			}
		};
	}

	@FXML
    void handleTestAction(ActionEvent event) {
    }

    @FXML
    void initialize() {


    }

	public void setOnCloseAction(Callback<Void, Void> callback) {
		this.callbackClose = callback;		
	}

	public void setOnSuccessful(Callback<ConnectionNode, Void> callback) {
		this.callBackSuccess = callback;		
	}

	public void setExternalBinding(ExternalBinding externalBinding) {
		this.externalBinding = externalBinding;		
	}

}
