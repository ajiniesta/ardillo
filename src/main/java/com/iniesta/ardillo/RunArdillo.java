package com.iniesta.ardillo;


import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.iniesta.ardillo.conf.ArdilloConfiguration;
import com.iniesta.ardillo.util.HibernateUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunArdillo extends Application {

	private final static Logger logger = Logger.getLogger(RunArdillo.class);

	public static void main(String[] args) throws Exception {
		initializeApplications();
		logger.info("Welcome!");
		launch(args);
	}

	private static void initializeApplications() {
		ArdilloConfiguration.getInstance();
		
		FileAppender fa = new FileAppender();
		fa.setName("fileapp");
		fa.setFile(ArdilloConfiguration.LOG);
		fa.setLayout(new PatternLayout("%d{ABSOLUTE} %5p %c{1}:%L - %m%n"));
		fa.setThreshold(Logger.getRootLogger().getLevel());
		fa.activateOptions();
		fa.setAppend(true);
		
		Logger.getRootLogger().addAppender(fa);
		
		
	}

	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/iniesta/ardillo/Ardillo.fxml"));
		Parent parent = (Parent) loader.load();
		// Create the scene
		Scene scene = new Scene(parent, 900, 600);
		stage.setScene(scene);
		// Show the stage
		stage.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() throws Exception {
		try {
			super.stop();
			if (!HibernateUtil.getSessionFactory().isClosed()) {
				HibernateUtil.getSessionFactory().close();
			}
		} finally {
			logger.info("Bye Bye");
		}
	}

}