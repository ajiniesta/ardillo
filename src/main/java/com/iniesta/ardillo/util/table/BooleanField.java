package com.iniesta.ardillo.util.table;

import com.iniesta.ardillo.util.CommonUtil;

public class BooleanField implements Field {

	private Boolean field;
	
	/**
	 * @param field
	 */
	public BooleanField(Boolean field) {
		super();
		this.field = field;
	}

	public String getValueToRepresent() {
		return field!=null?field.toString():CommonUtil.NULL;
	}

	/**
	 * @return the field
	 */
	public Boolean getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Boolean field) {
		this.field = field;
	}

}
