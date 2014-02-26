package com.iniesta.ardillo.util;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class CommonUtil {

	public static final String NULL = "[null]";

	public static void setAnchor0(Node node) {
		setAnchor(node, 0.0, 0.0, 0.0, 0.0);
	}

	public static void setAnchor(Node node, double top, double left, double bottom, double right) {
		AnchorPane.setBottomAnchor(node, bottom);
		AnchorPane.setTopAnchor(node, top);
		AnchorPane.setLeftAnchor(node, left);
		AnchorPane.setRightAnchor(node, right);
	}

	public static BooleanBinding conjunction(ObservableList<BooleanProperty> list) {
		BooleanBinding and = new SimpleBooleanProperty(true).and(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			and = and.and(list.get(i));
		}
		return and;
	}
}
