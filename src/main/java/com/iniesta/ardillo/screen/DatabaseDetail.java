package com.iniesta.ardillo.screen;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import com.iniesta.ardillo.util.CommonUtil;
import com.iniesta.ardillo.util.ConnectionNode;
import com.iniesta.ardillo.util.DataNode;
import com.iniesta.ardillo.util.DatabaseTreeCell;
import com.iniesta.ardillo.util.ExternalBinding;

public class DatabaseDetail extends Tab{

	private AnchorPane parent;
	private TreeView<DataNode> treeView;

	public DatabaseDetail(ConnectionNode connectionData, ExternalBinding externalBinding) {
		super(connectionData.getNodeName());
		AnchorPane content = new AnchorPane();
		parent = new AnchorPane();
		CommonUtil.setAnchor0(parent);
		treeView = new TreeView<DataNode>();
		treeView.setCellFactory(new Callback<TreeView<DataNode>, TreeCell<DataNode>>() {			
			public TreeCell<DataNode> call(TreeView<DataNode> arg0) {
				return new DatabaseTreeCell();
			}
		});
		Service<TreeItem<DataNode>> service = generateService(connectionData);
		externalBinding.bindAll(service);
		treeView.rootProperty().bind(service.valueProperty());
		service.start();
		content.getChildren().add(parent);
		setContent(content);
	}

	private Service<TreeItem<DataNode>> generateService(final ConnectionNode connectionData) {
		return new Service<TreeItem<DataNode>>() {
			@Override
			protected Task<TreeItem<DataNode>> createTask() {
				return new Task<TreeItem<DataNode>>() {					
					@Override
					protected TreeItem<DataNode> call() throws Exception {
						TreeItem<DataNode> root = new TreeItem<DataNode>(new DataNode(connectionData.getNodeName()));
						
						return root;
					}
				};
			}
		};
	}

}
