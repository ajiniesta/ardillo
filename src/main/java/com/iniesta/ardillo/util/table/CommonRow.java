package com.iniesta.ardillo.util.table;

import java.util.ArrayList;
import java.util.List;

public class CommonRow {

	private List<Field<?>> fields;

	public List<Field<?>> getFields() {
		return fields;
	}

	public void setFields(List<Field<?>> fields) {
		this.fields = fields;
	}
	
	public void add(Field<?> field){
		if(fields==null){
			fields = new ArrayList<Field<?>>();
		}
		fields.add(field);
	}
	
	public void add(Field<?> ... field){
		if(fields==null){
			fields = new ArrayList<Field<?>>();
		}
		if(field!=null){
			for (int i = 0; i < field.length; i++) {
				fields.add(field[i]);	
			}			
		}
	}
}
