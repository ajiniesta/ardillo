package com.iniesta.ardillo.screen;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import com.iniesta.ardillo.util.CommonUtil;
import com.iniesta.ardillo.util.DatabaseDataNode;
import com.iniesta.ardillo.util.ExternalBinding;
import com.iniesta.ardillo.util.dddbb.MetaDataCalculations;
import com.iniesta.ardillo.util.table.CommonRow;
import com.iniesta.ardillo.util.table.Field;
import com.iniesta.ardillo.util.table.StringField;

public class ViewTable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Tab tabColumns;

	@FXML
	private Tab tabData;

	private ExternalBinding externalBinding;

	private CommonTable tableColumns;

	private CommonTable tableData;

	private DatabaseDataNode dataNode;

	@FXML
	void initialize() {
		tableColumns = fillTable(tabColumns);
		tableData = fillTable(tabData);
	}

	private CommonTable fillTable(Tab tab) {
		CommonTable table = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/iniesta/ardillo/screen/CommonTable.fxml"));
		try {
			AnchorPane parent = (AnchorPane) loader.load();
			AnchorPane content = new AnchorPane();
			CommonUtil.setAnchor0(parent);
			content.getChildren().add(parent);
			tab.setContent(content);			
			table = (CommonTable)loader.getController();
			table.setExternalBinding(externalBinding);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return table;
	}

	public void setExternalBinding(ExternalBinding externalBinding) {
		this.externalBinding = externalBinding;		
	}

	public void setDatabaseDataNode(DatabaseDataNode dataNode){
		this.dataNode = dataNode;
		//Now the load can start
		fillColumns();
	}

	private void fillColumns() {
		final TableView<CommonRow> tableView = tableColumns.getTableView();
		tableView.getColumns().addAll(columnsForColumns());
		Service<ObservableList<CommonRow>> service = new Service<ObservableList<CommonRow>>() {			
			@Override
			protected Task<ObservableList<CommonRow>> createTask() {
				return new Task<ObservableList<CommonRow>>() {					
					@Override
					protected ObservableList<CommonRow> call() throws Exception {
						ObservableList<CommonRow> items = FXCollections.observableArrayList();
						List<CommonRow> rows = MetaDataCalculations.calculateColumnsDetails(dataNode.getArdilloConnection(), dataNode.getNodeName());
						items.addAll(rows);
						return items;
					}
				};
			}
		};
		tableView.itemsProperty().bind(service.valueProperty());
		externalBinding.bind(service);
		service.start();
		
	}

	private ObservableList<TableColumn<CommonRow, ?>> columnsForColumns() {
		String[] colNames = new String[]{"Table Name","Column Name", "Data Type"};
		ObservableList<TableColumn<CommonRow, ?>> cols = FXCollections.observableArrayList();
		for (int i = 0; i < colNames.length; i++) {
			TableColumn<CommonRow, String> col = new TableColumn<CommonRow, String>(colNames[i]);
			col.setCellValueFactory(StringField.getCellValueFactory(i));
			col.setPrefWidth(100);
			cols.add(col);
		}		
		return cols;
	}
}
