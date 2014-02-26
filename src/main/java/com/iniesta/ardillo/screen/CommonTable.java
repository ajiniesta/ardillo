package com.iniesta.ardillo.screen;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import com.iniesta.ardillo.util.ExternalBinding;
import com.iniesta.ardillo.util.table.CommonRow;


public class CommonTable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<CommonRow> tableView;

	private ExternalBinding externalBinding;


    @FXML
    void handleExportAction(ActionEvent event) {
    }

    @FXML
    void initialize() {


    }

	public void setExternalBinding(ExternalBinding externalBinding) {
		this.externalBinding = externalBinding;		
	}
	
	public TableView<CommonRow> getTableView(){
		return tableView;
	}
}