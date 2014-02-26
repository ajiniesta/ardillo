package com.iniesta.ardillo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.screen.CreateDatabase;
import com.iniesta.ardillo.screen.DatabaseDetail;
import com.iniesta.ardillo.screen.TableTab;
import com.iniesta.ardillo.util.CommonUtil;
import com.iniesta.ardillo.util.ConnectionNode;
import com.iniesta.ardillo.util.ConnectionTreeCell;
import com.iniesta.ardillo.util.DataNode;
import com.iniesta.ardillo.util.DatabaseDataNode;
import com.iniesta.ardillo.util.ExternalBinding;

public class Ardillo extends ExternalBinding {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TreeView<DataNode> treeViewConnections;
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private Label messageLeftStatus;
    
    @FXML
    private Label messageRightStatus;
    
    @FXML
    private Button buttonAddConnection;
    
    @FXML
    private TabPane mainTabPane;
    
    @FXML
    private TabPane tabPaneConnections;

    @FXML
    void initialize() {
    	backgroundTasks();
    	fxInitialize();
    }
    
    private void fxInitialize() {
		treeViewConnections.setCellFactory(new Callback<TreeView<DataNode>, TreeCell<DataNode>>() {			
			public TreeCell<DataNode> call(TreeView<DataNode> arg0) {
				return new ConnectionTreeCell();
			}
		});
		treeViewConnections.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if(treeViewConnections.getSelectionModel().getSelectedItem()!=null && event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
					connectDatabase();
				}
			}
		});
	}

	@FXML
    void handleAddConnection(ActionEvent action){
		final Tab tab = new Tab("Create Database Connection");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/iniesta/ardillo/screen/CreateDatabase.fxml"));
		try {
			AnchorPane parent = (AnchorPane) loader.load();
			AnchorPane content = new AnchorPane();
			CommonUtil.setAnchor0(parent);
			content.getChildren().add(parent);
			tab.setContent(content);
			
			CreateDatabase createDatabase = (CreateDatabase)loader.getController();
			tab.setClosable(true);
			createDatabase.setExternalBinding((ExternalBinding)this);			
			createDatabase.setOnCloseAction(new Callback<Void, Void>() {
				public Void call(Void arg0) {
					mainTabPane.getTabs().remove(tab);
					return null;
				}
			});
			createDatabase.setOnSuccessful(new Callback<ConnectionNode, Void>() {
				public Void call(ConnectionNode connNode) {
					treeViewConnections.getRoot().getChildren().add(new TreeItem<DataNode>(connNode));
					return null;
				}
			});
			mainTabPane.getTabs().add(tab);
		} catch (IOException e) {
			e.printStackTrace();
		}		
    }

    private void backgroundTasks() {
    	Service<Void> service = new Service<Void>() {			
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						fillTree();
						return null;
					}
				};
			}
		};
    	progressBar.visibleProperty().bind(service.runningProperty());
    	service.start();
	}

	private void fillTree(){
    	try{
    	TreeItem<DataNode> root = new TreeItem<DataNode>(new DataNode("Connections"));
    	root.setExpanded(true);
    	DAOConnection dao = new DAOConnection();
    	List<ArdilloConnection> list = dao.listConnections();
    	if(list!=null){
    		for (ArdilloConnection ardilloConnection : list) {
				root.getChildren().add(new TreeItem<DataNode>(new ConnectionNode(ardilloConnection)));
			}
    	}
    	setTreeRoot(root);
    	}catch(Exception e){}
    }

	private void setTreeRoot(final TreeItem<DataNode> root) {
		if(Platform.isFxApplicationThread()){
			treeViewConnections.setRoot(root);
		}else{
			Platform.runLater(new Runnable() {				
				public void run() {
					treeViewConnections.setRoot(root);					
				}
			});
		}
	}
	
	@FXML
	void handleConnectionAction(ActionEvent action){
		connectDatabase();
	}

	private void connectDatabase() {
		TreeItem<DataNode> selectedItem = treeViewConnections.getSelectionModel().getSelectedItem();
		if(selectedItem!=null && selectedItem.getValue() instanceof ConnectionNode){
			DatabaseDetail tab = new DatabaseDetail((ConnectionNode)selectedItem.getValue(), (ExternalBinding)this);
			tab.setOnOpenDatabase(new Callback<DatabaseDataNode, Void>() {
				public Void call(DatabaseDataNode databaseDataNode) {
					createTabDatabase(databaseDataNode);
					return null;
				}
			});
			tabPaneConnections.getTabs().add(tab);
			tabPaneConnections.getSelectionModel().select(tab);
		}
	}

	protected void createTabDatabase(DatabaseDataNode databaseDataNode) {
		TableTab tab = new TableTab(databaseDataNode, (ExternalBinding)this);
		mainTabPane.getTabs().add(tab);
		mainTabPane.getSelectionModel().select(tab);
	}

	@Override
	public void bind(Service<?> service) {
		progressBar.visibleProperty().bind(service.runningProperty());
	}

	@Override
	public void bindMessages(Service<?> service) {
		messageLeftStatus.textProperty().bind(service.messageProperty());		
	}
}
