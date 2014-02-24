package com.iniesta.ardillo.util;

import javafx.concurrent.Service;

public abstract class ExternalBinding {

	public void bindAll(Service<?> service){
		bind(service);
		bindMessages(service);
	}
	
	public abstract void bind(Service<?> service);
	
	public abstract void bindMessages(Service<?> service);
}
