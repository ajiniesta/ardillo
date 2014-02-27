package com.iniesta.ardillo.util.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

import com.iniesta.ardillo.util.CommonUtil;

public class NumberField extends Field<Number> {

	private SimpleIntegerProperty field;

	public NumberField(Integer field) {
		super();
		this.field = new SimpleIntegerProperty(field);
	}

	public Integer getField() {
		return field.getValue();
	}

	public void setField(Integer field) {
		this.field.setValue(field);
	}

	public ObservableValue<Number> property(){
		return field;
	}
	
	public String getValueToRepresent() {
		return field!=null?field.getValue().toString():CommonUtil.NULL;
	}
	
}
