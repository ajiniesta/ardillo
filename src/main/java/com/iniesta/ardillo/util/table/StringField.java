package com.iniesta.ardillo.util.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import com.iniesta.ardillo.util.CommonUtil;

public class StringField extends Field<String> {

	private SimpleStringProperty field;
	
	public StringField(String field) {
		super();
		this.field = new SimpleStringProperty(field);
	}
	
	public String getField() {
		return field.getValue();
	}

	public void setField(String field) {
		this.field.setValue(field);
	}

	public String getValueToRepresent() {
		return field!=null?field.getValue():CommonUtil.NULL;
	}
	
	public ObservableValue<String> property(){
		return field;
	}

}
