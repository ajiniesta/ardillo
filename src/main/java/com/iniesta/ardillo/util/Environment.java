package com.iniesta.ardillo.util;

import java.io.File;

public class Environment {

	public static final String ARDILLO_HOME = "ARDILLO_HOME";

	public static String getArdilloHome(){
		return System.getenv().get(ARDILLO_HOME);
	}
	
	public static String getConfDir(){
		return getArdilloHome()+"/conf";
	}
	
	public static String getDriversDir(){
		return getArdilloHome()+"/drivers";
	}
	
	public static File getFileDriversDir(){
		File file = new File(getDriversDir());
		if(file!=null && file.exists() && file.isDirectory() && file.canWrite()){
			return file;
		}else{
			return null;
		}
	}
}
