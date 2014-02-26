package com.iniesta.ardillo.util.table;

import com.iniesta.ardillo.util.CommonUtil;

public class StringField implements Field {

	private String field;

	public StringField(String field) {
		super();
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValueToRepresent() {
		return field!=null?field:CommonUtil.NULL;
	}

}
