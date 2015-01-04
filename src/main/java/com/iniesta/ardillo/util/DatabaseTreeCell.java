package com.iniesta.ardillo.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;

public class DatabaseTreeCell extends TreeCell<DataNode> {
	
	@Override
	protected void updateItem(DataNode item, boolean empty) {
		super.updateItem(item, empty);
		if(!empty){
			setText(item.getNodeName());
			if(item instanceof DatabaseDataNode){
				ImageView image = new ImageView("/images/table.png");
				image.setFitWidth(20);
				image.setFitHeight(20);
				setGraphic(image);
//				setContextMenu(generateContextMenu(item));
			}
		}
	}

	private ContextMenu generateContextMenu(DataNode item) {
		ContextMenu cm = new ContextMenu();
		MenuItem delete = new MenuItem("Delete...");
		delete.setOnAction(deleteAction(item));
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(edit(item));
		cm.getItems().addAll(edit, delete);
		return cm;
	}

	private EventHandler<ActionEvent> edit(DataNode item) {
		// TODO Auto-generated method stub
		return null;
	}

	private EventHandler<ActionEvent> deleteAction(final DataNode item) {
		return new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Dialog.showConfirm("Are you sure do you want to delelete the Database "+item.getNodeName()+"?");
			}
		};
	}
}
