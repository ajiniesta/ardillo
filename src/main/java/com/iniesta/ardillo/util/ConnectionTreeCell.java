package com.iniesta.ardillo.util;

import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;

public class ConnectionTreeCell extends TreeCell<ConnectionNode>{

	@Override
	protected void updateItem(ConnectionNode item, boolean empty) {
		super.updateItem(item, empty);
		if(!empty){
			setText(item.getNodeName());
			if(item instanceof ConnectionWithDataNode){
				ImageView image = new ImageView("/images/ddbb.png");
				image.setFitWidth(20);
				image.setFitHeight(20);
				setGraphic(image);
			}
		}
	}

}
