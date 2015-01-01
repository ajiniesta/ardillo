package com.iniesta.ardillo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iniesta.ardillo.domain.DatabaseType;

public class MapScreen {

	private static final Logger logger = LoggerFactory.getLogger(MapScreen.class);
	
	private Object obj;
	private Map<String, Object> mapParams;

	public MapScreen(Object obj){
		this.obj = obj;
		mapParams = calculateMapParams();
	}

	private Map<String, Object> calculateMapParams() {
		Map<String, Object> mapParams = new HashMap<String, Object>();
		Class<? extends Object> clazz = obj.getClass();
		for(Field field : clazz.getDeclaredFields()){
			FXML fxmlAnnotation = field.getAnnotation(FXML.class);
			if(fxmlAnnotation!=null){
				mapParams.put(field.getName(), getFieldValue(field));
			}
		}
		return mapParams;
	}
	
	private Object getFieldValue(Field field) {
		Object fieldValue = null;
		field.setAccessible(true);
		try {
			fieldValue = field.get(obj);
		} catch (Exception e) {
			logger.error("Couldn't retrieve the value from the field during MapScreen initialization of "+obj.getClass().getCanonicalName());
		}
		return fieldValue;
	}

//	@SuppressWarnings("unchecked")
	public <T> T getParam(String key, Class<T> clazz){
		T obj = null;
		try{
//			obj = (T)mapParams.get(key);
			obj = clazz.cast(mapParams.get(key));
		}catch(ClassCastException e){
			logger.error("Trying to get key with different type");
		}
		return obj;
	}
	
	public String getTextFieldParam(String key){
		return getParam(key, TextInputControl.class).getText();
	}
	
	public DatabaseType getDatabaseTypeParam(String key){
		@SuppressWarnings("unchecked")
		ComboBox<DatabaseType> param = getParam(key, ComboBox.class);
		return param.getSelectionModel().getSelectedItem();
	}
}
