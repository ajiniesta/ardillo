package com.iniesta.ardillo.screen;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.domain.DatabaseType;
import com.iniesta.ardillo.services.DatabaseServices;
import com.iniesta.ardillo.util.ConnectionNode;
import com.iniesta.ardillo.util.ExternalBinding;
import com.iniesta.ardillo.util.MapScreen;


public class CreateDatabase {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<DatabaseType> comboBoxDatabaseType;

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
    
    @FXML
    private Button buttonTest;
    
    @FXML
    private Button buttonSave;
    
    @FXML
    private Button buttonCancel;
    
    @FXML
    private Button buttonMore;

	private Callback<Void, Void> callbackClose;

	private Callback<ConnectionNode, Void> callBackSuccess;

	private ExternalBinding externalBinding;

    @FXML
    void handleCancelAction(ActionEvent event) {
    	callbackClose.call(null);
    }

    @FXML
    void handleSaveAction(ActionEvent event) {
    	final Service<ArdilloConnection> serviceSave = DatabaseServices.serviceSave(new MapScreen(this));
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
    
    @FXML
    void handleMoreAction(ActionEvent event){
    	FileChooser chooser = new FileChooser();
    	chooser.setTitle("Look for a driver class");
    	chooser.getExtensionFilters().add(new ExtensionFilter("Driver Jar", ".jar"));
    	File file = chooser.showOpenDialog(null);
    	final Service<Boolean> service = DatabaseServices.serviceMore(file);
    	externalBinding.bindAll(service);
    	service.runningProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue){
					
				}
			}
		});
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
