package com.iniesta.ardillo.screen;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import com.iniesta.ardillo.util.CommonUtil;
import com.iniesta.ardillo.util.DatabaseDataNode;
import com.iniesta.ardillo.util.ExternalBinding;
import com.iniesta.ardillo.util.dddbb.MetaDataCalculations;
import com.iniesta.ardillo.util.dddbb.QueryExecutor;
import com.iniesta.ardillo.util.table.CommonRow;
import com.iniesta.ardillo.util.table.TableColumnInfo;
import com.iniesta.ardillo.util.table.TableColumnInfo.ColumnType;
import com.iniesta.ardillo.util.table.TableCreator;

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
		fillData();
	}

	private void fillData() {
		final TableView<CommonRow> tableView = tableData.getTableView();
		final Service<TableCreator> serviceTableCreator = generateTableCreator();
		externalBinding.bind(serviceTableCreator);
		serviceTableCreator.runningProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(!newValue){
					Service<ObservableList<CommonRow>> serviceData = generateData(serviceTableCreator.getValue());
					externalBinding.bind(serviceData);
					tableView.getColumns().addAll(serviceTableCreator.getValue().generateColumns());
					tableView.itemsProperty().bind(serviceData.valueProperty());
					serviceData.start();
				}
			}
		});		
		serviceTableCreator.start();
	}

	protected Service<ObservableList<CommonRow>> generateData(final TableCreator tc) {
		return new Service<ObservableList<CommonRow>>() {
			@Override
			protected Task<ObservableList<CommonRow>> createTask() {
				return new Task<ObservableList<CommonRow>>() {					
					@Override
					protected ObservableList<CommonRow> call() throws Exception {
						return FXCollections.observableArrayList(QueryExecutor.calculateData(dataNode.getArdilloConnection(), "select * from "+dataNode.getNodeName(), tc));						
					}
				};
			}
		};
	}

	private Service<TableCreator> generateTableCreator() {
		return new Service<TableCreator>() {			
			@Override
			protected Task<TableCreator> createTask() {
				return new Task<TableCreator>() {
					@Override
					protected TableCreator call() throws Exception {
						TableCreator tc = new TableCreator(MetaDataCalculations.calculateColumnsDetails(dataNode.getArdilloConnection(), dataNode.getNodeName()));
						return tc;
					}
				};
			}
		};
	}

	private void fillColumns() {
		final TableView<CommonRow> tableView = tableColumns.getTableView();
		final TableCreator tc = new TableCreator(generateColInfo());
		tableView.getColumns().addAll(tc.generateColumns());
		Service<ObservableList<CommonRow>> service = new Service<ObservableList<CommonRow>>() {			
			@Override
			protected Task<ObservableList<CommonRow>> createTask() {
				return new Task<ObservableList<CommonRow>>() {					
					@Override
					protected ObservableList<CommonRow> call() throws Exception {
						ObservableList<CommonRow> items = FXCollections.observableArrayList();
						List<CommonRow> rows = MetaDataCalculations.calculateData(dataNode.getArdilloConnection(), new Callback<Connection, ResultSet>() {							
							public ResultSet call(Connection connection) {
								ResultSet resultSet = null;
								try{
								DatabaseMetaData metaData = connection.getMetaData();
								resultSet = metaData.getColumns(null, dataNode.getArdilloConnection().getSchema(), dataNode.getNodeName(),"%");;
								}catch(Exception ex){}
								return resultSet;
							}
						}, tc);
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

	private TableColumnInfo[] generateColInfo() {
		return new TableColumnInfo[]{//
				new TableColumnInfo("Table Name", "TABLE_NAME", ColumnType.STRING),//
				new TableColumnInfo("Column Name", "COLUMN_NAME", ColumnType.STRING),//
				new TableColumnInfo("Data Type", "TYPE_NAME", ColumnType.STRING),//
				new TableColumnInfo("Column Size", "COLUMN_SIZE", ColumnType.NUMBER),//
				new TableColumnInfo("Decimal Digits", "DECIMAL_DIGITS", ColumnType.NUMBER),//
				new TableColumnInfo("Nullable", "NULLABLE", ColumnType.NUMBER),//
				new TableColumnInfo("Is Nullable", "IS_NULLABLE", ColumnType.STRING)};
	}

}
