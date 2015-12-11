package com.iniesta.ardillo.conf;

import java.io.File;

public class ArdilloConfiguration {

	private static ArdilloConfiguration instance;
	
	private final static String USER_HOME = System.getProperty("user.home");
	public final static String CONF_HOME = USER_HOME + System.getProperty("file.separator") + ".ardillo";
	private final static File CONFIGURATION = new File(CONF_HOME);
	private final static String LOGS_DIR = CONF_HOME + System.getProperty("file.separator") + "logs";
	public final static String LOG = LOGS_DIR + System.getProperty("file.separator") + "ardillo.log";
	public final static String DATA_DIR = CONF_HOME + System.getProperty("file.separator") + "data";

	
	private ArdilloConfiguration(){
		if(!CONFIGURATION.exists()){
			createConfigurationDirectory();
		}
		
		
	}
	
	private void createConfigurationDirectory() {
		CONFIGURATION.mkdir();
		new File(CONF_HOME + System.getProperty("file.separator") + "drivers").mkdir();		
		new File(LOGS_DIR).mkdir();
		new File(DATA_DIR).mkdir();
	}

	public static ArdilloConfiguration getInstance(){
		if(instance==null){
			instance = new ArdilloConfiguration();
		}
		return instance;
	}
	
}
