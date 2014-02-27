package com.iniesta.ardillo.util.table;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public abstract class Field<T> {

	public abstract String getValueToRepresent();
	
	public abstract ObservableValue<T> property();
	
	public Callback<CellDataFeatures<CommonRow, T>, ObservableValue<T>> getCellValueFactory(final int index) {
		return new Callback<TableColumn.CellDataFeatures<CommonRow,T>, ObservableValue<T>>() {			
			@SuppressWarnings("unchecked")
			public ObservableValue<T> call(CellDataFeatures<CommonRow, T> cdf) {
				return cdf.getValue().getFields().get(index).property();				
			}
		};
	}
}
