package com.iniesta.ardillo.screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.iniesta.ardillo.util.CommonUtil;
import com.iniesta.ardillo.util.DatabaseDataNode;
import com.iniesta.ardillo.util.ExternalBinding;
import com.iniesta.ardillo.util.table.CommonRow;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

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
		
		
	}
}
