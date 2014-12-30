package com.iniesta.ardillo.util;

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
			}
		}
	}
}
