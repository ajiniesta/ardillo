package com.iniesta.ardillo.util.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import com.iniesta.ardillo.util.CommonUtil;

public class StringField implements Field {

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
	
	public SimpleStringProperty propertyField(){
		return field;
	}

	public static Callback<CellDataFeatures<CommonRow, String>, ObservableValue<String>> getCellValueFactory(final int index) {
		return new Callback<TableColumn.CellDataFeatures<CommonRow,String>, ObservableValue<String>>() {			
			public ObservableValue<String> call(CellDataFeatures<CommonRow, String> cdf) {
				Field f = cdf.getValue().getFields().get(index);
				if(f instanceof StringField){
					return ((StringField)f).propertyField();
				}else{
					return null;
				}
			}
		};
	}

}
