package com.iniesta.ardillo.util.table;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

public class TableCreator {

	private List<TableColumnInfo> columns;
	
	public TableCreator(TableColumnInfo ... columnInfos){
		if(columnInfos!=null){
			columns = Arrays.asList(columnInfos);
		}
	}
	
	public ObservableList<TableColumn<CommonRow, ?>> generateColumns(){
		ObservableList<TableColumn<CommonRow, ?>> cols = null;
		if(columns!=null){
			cols = FXCollections.observableArrayList();
			for (int index = 0; index < columns.size(); index++) {
				TableColumnInfo colInfo = columns.get(index);
				cols.add(generateColumn(colInfo,index));	
			}
		}
		return cols;
	}

	private TableColumn<CommonRow, ?> generateColumn(TableColumnInfo colInfo, int index) {
		TableColumn<CommonRow, ?> col = null;
		if(colInfo!=null){
			col = createColumn(colInfo, index);
			col.setPrefWidth(colInfo.getWidth());
		}
		return col;
	}

	private TableColumn<CommonRow, ?> createColumn(TableColumnInfo colInfo, int index) {
		Field<?> type = colInfo.getType().getType();
		if(type instanceof StringField){
			StringField field = (StringField)type;
			TableColumn<CommonRow, String> tc = new TableColumn<CommonRow, String>(colInfo.getTitle());
			tc.setCellValueFactory(field.getCellValueFactory(index));
			return tc;
		}else if(type instanceof NumberField){
			NumberField field = (NumberField)type;
			TableColumn<CommonRow, Number> tc = new TableColumn<CommonRow, Number>(colInfo.getTitle());
			tc.setCellValueFactory(field.getCellValueFactory(index));
			return tc;
		}else if(type instanceof BooleanField){
			BooleanField field = (BooleanField)type;
			TableColumn<CommonRow, Boolean> tc = new TableColumn<CommonRow, Boolean>(colInfo.getTitle());
			tc.setCellValueFactory(field.getCellValueFactory(index));
			return tc;
		}else if(type instanceof BinaryField){
			BinaryField field = (BinaryField)type;
			TableColumn<CommonRow, byte[]> tc = new TableColumn<CommonRow, byte[]>(colInfo.getTitle());
			tc.setCellValueFactory(field.getCellValueFactory(index));
			return tc;
		}else{
			return null;
		}
	}

	public List<TableColumnInfo> getColumns() {
		return columns;
	}
	
}
