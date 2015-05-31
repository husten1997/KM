package com.richard.knightmare.util;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class ImageListRenderer extends DefaultListCellRenderer {

	@SuppressWarnings("rawtypes")
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		label.setIcon(Loader.getResourcepackIcon(String.valueOf(value)));
		label.setHorizontalTextPosition(JLabel.RIGHT);
		return label;
	}
}
