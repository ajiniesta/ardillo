package com.iniesta.ardillo.util.table;

import com.iniesta.ardillo.util.CommonUtil;

public class NumericField implements Field {

	private Integer field;

	public NumericField(Integer field) {
		super();
		this.field = field;
	}

	public Integer getField() {
		return field;
	}

	public void setField(Integer field) {
		this.field = field;
	}

	public String getValueToRepresent() {
		return field!=null?field.toString():CommonUtil.NULL;
	}

}
