package com.iniesta.ardillo;

import com.iniesta.ardillo.util.HibernateUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunArdillo extends Application{

	public static void main(String[] args) throws Exception{
		launch(args);
	}

	public void start(Stage stage) throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/iniesta/ardillo/Ardillo.fxml"));
		Parent parent = (Parent) loader.load();
		// Create the scene
		Scene scene = new Scene(parent, 900, 600);
		stage.setScene(scene);
		// Show the stage
		stage.show();		
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		HibernateUtil.getSessionFactory().close();
	}	
	
}