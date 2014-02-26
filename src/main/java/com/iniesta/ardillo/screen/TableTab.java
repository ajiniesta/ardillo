package com.iniesta.ardillo.screen;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import com.iniesta.ardillo.util.CommonUtil;
import com.iniesta.ardillo.util.DatabaseDataNode;
import com.iniesta.ardillo.util.ExternalBinding;

public class TableTab extends Tab{

	public TableTab(DatabaseDataNode databaseDataNode, ExternalBinding externalBinding){
		super(databaseDataNode.getNodeName());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/iniesta/ardillo/screen/ViewTable.fxml"));
		try {
			AnchorPane parent = (AnchorPane) loader.load();
			AnchorPane content = new AnchorPane();
			CommonUtil.setAnchor0(parent);
			content.getChildren().add(parent);
			setContent(content);
			
			ViewTable viewTable = (ViewTable)loader.getController();
			viewTable.setExternalBinding(externalBinding);
			viewTable.setDatabaseDataNode(databaseDataNode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
