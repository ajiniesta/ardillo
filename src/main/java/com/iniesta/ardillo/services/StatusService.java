package com.iniesta.ardillo.services;

import javafx.concurrent.Service;

public abstract class StatusService<V> extends Service<V> {

	private boolean errorMode = false;
	
	public boolean isErrorMode(){
		return this.errorMode;
	}
	
	public void setErrorMode(boolean errorMode){
		this.errorMode = errorMode;
	}
}
