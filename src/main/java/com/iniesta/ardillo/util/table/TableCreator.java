package com.iniesta.ardillo.util.table;

import java.sql.ResultSet;
import java.sql.SQLException;
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
			if(colInfo.applyWidth()){
				col.setPrefWidth(colInfo.getWidth());
			}else{
				col.setPrefWidth(colInfo.getDefaultWidth());
			}
		}
		return col;
	}

	private TableColumn<CommonRow, ?> createColumn(TableColumnInfo colInfo, int index) {
		Field<?> type = colInfo.getType().getType();
		switch (colInfo.getType()) {
		case STRING:
			StringField sField = (StringField)type;
			TableColumn<CommonRow, String> stc = new TableColumn<CommonRow, String>(colInfo.getTitle());
			stc.setCellValueFactory(sField.getCellValueFactory(index));
			return stc;
		case NUMBER:
			NumberField nField = (NumberField)type;
			TableColumn<CommonRow, Number> ntc = new TableColumn<CommonRow, Number>(colInfo.getTitle());
			ntc.setCellValueFactory(nField.getCellValueFactory(index));
			return ntc;
		case BOOLEAN:
			BooleanField bField = (BooleanField)type;
			TableColumn<CommonRow, Boolean> btc = new TableColumn<CommonRow, Boolean>(colInfo.getTitle());
			btc.setCellValueFactory(bField.getCellValueFactory(index));
			return btc;
		case BINARY:
			BinaryField binField = (BinaryField)type;
			TableColumn<CommonRow, byte[]> bintc = new TableColumn<CommonRow, byte[]>(colInfo.getTitle());
			bintc.setCellValueFactory(binField.getCellValueFactory(index));
			return bintc;
		default:
			return null;
		}
	}

	public List<TableColumnInfo> getColumns() {
		return columns;
	}

	public Field<?>[] getFields(ResultSet resultSet) throws SQLException {
		Field<?>[] fields = new Field<?>[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			TableColumnInfo tci = columns.get(i);
			fields[i] = tci.getField(resultSet);
		}
		return fields;
	}
	
}
