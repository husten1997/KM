package com.richard.knightmare.util;

import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class DefaultButtonRenderer extends DefaultListCellRenderer {

	@SuppressWarnings("rawtypes")
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		BufferedImage img = new BufferedImage(120, 40, BufferedImage.TYPE_INT_ARGB);
		img.createGraphics().drawImage(Loader.getImage("Default.png"), 0, 0, img.getWidth(), img.getHeight(), null);
		label.setIcon(new ImageIcon(img));
		label.setHorizontalTextPosition(JLabel.RIGHT);
		return label;
	}
}
