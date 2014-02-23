package com.iniesta.ardillo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import com.iniesta.ardillo.dao.DAOConnection;
import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.screen.CreateDatabase;
import com.iniesta.ardillo.util.ConnectionNode;
import com.iniesta.ardillo.util.ConnectionTreeCell;
import com.iniesta.ardillo.util.ConnectionWithDataNode;

public class Ardillo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TreeView<ConnectionNode> treeViewConnections;
    
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
    void initialize() {
    	backgroundTasks();
    	fxInitialize();
    }
    
    private void fxInitialize() {
		ImageView imageView = new ImageView("/images/add_ddbb.png");
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		buttonAddConnection.setGraphic(imageView);
		treeViewConnections.setCellFactory(new Callback<TreeView<ConnectionNode>, TreeCell<ConnectionNode>>() {			
			public TreeCell<ConnectionNode> call(TreeView<ConnectionNode> arg0) {
				return new ConnectionTreeCell();
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
			AnchorPane.setBottomAnchor(parent, 0.0);
			AnchorPane.setTopAnchor(parent, 0.0);
			AnchorPane.setLeftAnchor(parent, 0.0);
			AnchorPane.setRightAnchor(parent, 0.0);
			content.getChildren().add(parent);
			tab.setContent(content);
			
			CreateDatabase createDatabase = (CreateDatabase)loader.getController();
			tab.setClosable(true);
			createDatabase.setThingsToBind(progressBar);
			createDatabase.setThingsToBindMessage(messageLeftStatus);
			createDatabase.setOnCloseAction(new Callback<Void, Void>() {
				public Void call(Void arg0) {
					mainTabPane.getTabs().remove(tab);
					return null;
				}
			});
			createDatabase.setOnSuccessful(new Callback<ConnectionNode, Void>() {
				public Void call(ConnectionNode connNode) {
					treeViewConnections.getRoot().getChildren().add(new TreeItem<ConnectionNode>(connNode));
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
    	TreeItem<ConnectionNode> root = new TreeItem<ConnectionNode>(new ConnectionNode("Connections"));
    	root.setExpanded(true);
    	DAOConnection dao = new DAOConnection();
    	List<ArdilloConnection> list = dao.listConnections();
    	if(list!=null){
    		for (ArdilloConnection ardilloConnection : list) {
				root.getChildren().add(new TreeItem<ConnectionNode>(new ConnectionWithDataNode(ardilloConnection)));
			}
    	}
    	setTreeRoot(root);
    	}catch(Exception e){}
    }

	private void setTreeRoot(final TreeItem<ConnectionNode> root) {
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
}
