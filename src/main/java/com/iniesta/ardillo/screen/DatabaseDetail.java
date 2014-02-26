package com.iniesta.ardillo.screen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.util.CommonUtil;
import com.iniesta.ardillo.util.ConnectionNode;
import com.iniesta.ardillo.util.DataNode;
import com.iniesta.ardillo.util.DatabaseDataNode;
import com.iniesta.ardillo.util.DatabaseTreeCell;
import com.iniesta.ardillo.util.ExternalBinding;
import com.iniesta.ardillo.util.dddbb.MetaDataCalculations;

public class DatabaseDetail extends Tab {

	private AnchorPane parent;
	private TreeView<DataNode> treeView;
	private Callback<DatabaseDataNode, Void> callbackOpenDatabase;

	public DatabaseDetail(ConnectionNode connectionData, ExternalBinding externalBinding) {
		super(connectionData.getNodeName());
		AnchorPane content = new AnchorPane();
		parent = new AnchorPane();
		CommonUtil.setAnchor0(parent);		
		treeView = new TreeView<DataNode>();
		treeView.setOnMouseClicked(mouseClickHandler());
		CommonUtil.setAnchor0(treeView);
		treeView.setCellFactory(new Callback<TreeView<DataNode>, TreeCell<DataNode>>() {
			public TreeCell<DataNode> call(TreeView<DataNode> arg0) {
				return new DatabaseTreeCell();
			}
		});
		Service<TreeItem<DataNode>> service = generateService(connectionData);
		externalBinding.bindAll(service);
		treeView.rootProperty().bind(service.valueProperty());
		parent.getChildren().add(treeView);
		service.start();
		content.getChildren().add(parent);
		setContent(content);
	}

	private EventHandler<? super MouseEvent> mouseClickHandler() {
		return new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if(treeView.getSelectionModel().getSelectedItem()!=null && event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
					DataNode dataNode = treeView.getSelectionModel().getSelectedItem().getValue();
					if(dataNode instanceof DatabaseDataNode){
						callbackOpenDatabase.call((DatabaseDataNode)treeView.getSelectionModel().getSelectedItem().getValue());
					}
				}
			}
		};
	}

	private Service<TreeItem<DataNode>> generateService(final ConnectionNode connectionData) {
		return new Service<TreeItem<DataNode>>() {
			@Override
			protected Task<TreeItem<DataNode>> createTask() {
				return new Task<TreeItem<DataNode>>() {
					@Override
					protected TreeItem<DataNode> call() throws Exception {
						TreeItem<DataNode> root = new TreeItem<DataNode>(new DataNode(connectionData.getNodeName()));
						List<DatabaseDataNode> tables = MetaDataCalculations.calculateTables(connectionData.getArdillConnection());
						if(tables!=null){
							for (DatabaseDataNode databaseDataNode : tables) {
								root.getChildren().add(new TreeItem<DataNode>(databaseDataNode));
							}
						}
						return root;
					}
				};
			}
		};
	}

	public void setOnOpenDatabase(Callback<DatabaseDataNode, Void> openDatabase) {
		this.callbackOpenDatabase = openDatabase;		
	}

}
