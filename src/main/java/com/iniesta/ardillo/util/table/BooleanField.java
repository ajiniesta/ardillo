package com.iniesta.ardillo.util.table;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import com.iniesta.ardillo.util.CommonUtil;

public class BooleanField extends Field<Boolean> {

	private SimpleBooleanProperty field;
	
	/**
	 * @param field
	 */
	public BooleanField(Boolean field) {
		super();
		this.field = new SimpleBooleanProperty(field);
	}

	public String getValueToRepresent() {
		return field!=null?field.toString():CommonUtil.NULL;
	}

	/**
	 * @return the field
	 */
	public Boolean getField() {
		return field.getValue();
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Boolean field) {
		this.field.setValue(field);
	}

	@Override
	public ObservableValue<Boolean> property() {
		return field;
	}

}
