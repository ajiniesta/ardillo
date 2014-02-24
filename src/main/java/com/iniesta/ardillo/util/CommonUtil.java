package com.iniesta.ardillo.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class CommonUtil {

	public static void setAnchor0(Node node){
		setAnchor(node, 0.0, 0.0, 0.0, 0.0);
	}
	
	public static void setAnchor(Node node, double top, double left, double bottom, double right){
		AnchorPane.setBottomAnchor(node, bottom);
		AnchorPane.setTopAnchor(node, top);
		AnchorPane.setLeftAnchor(node, left);
		AnchorPane.setRightAnchor(node, right);
	}
}
