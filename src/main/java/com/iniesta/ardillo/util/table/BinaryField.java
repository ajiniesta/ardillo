package com.iniesta.ardillo.util.table;

import javafx.beans.value.ObservableValue;

public class BinaryField extends Field<byte[]>{

	public String getValueToRepresent() {
		return "[binary]";
	}

	@Override
	public ObservableValue<byte[]> property() {
		return null;
	}

}
