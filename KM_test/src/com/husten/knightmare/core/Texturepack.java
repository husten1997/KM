package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.richard.knightmare.util.Loader;

@SuppressWarnings("serial")
public class Texturepack extends JFrame implements ListSelectionListener {

	@SuppressWarnings("rawtypes")
	private JList list;
	private File path;
	private String[] text;
	private Optionen op;

	public Texturepack(boolean undecorated, Optionen o) {
		Loader.initLoaderWithoutLoad("Ares", "Knightmare");

		op = o;

		path = new File(new StringBuilder("C:\\Users\\")
				.append(System.getProperty("user.name"))
				.append("\\Appdata\\Roaming\\ares\\Knightmare\\resourcepacks")
				.toString());
		text = path.list();

		double resolution = (double) 16 / (double) 9;
		int width, height;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		if (screen.getWidth() / screen.getHeight() == resolution) {
			width = screen.width;
			height = screen.height;
		} else if (screen.getWidth() / screen.getHeight() > resolution) {
			width = (int) (screen.getHeight() * resolution);
			height = screen.height;
		} else {
			width = screen.width;
			height = (int) (screen.getWidth() / resolution);
		}

		String gewahlt = Loader.getCfgValue("Resourcepack");

		int Autoselect = -1;

		for (int i = 0; i < text.length; i++) {
			if (gewahlt.equals(text[i])) {
				Autoselect = i;
			}
		}

		setSize(width, height);
		setAutoRequestFocus(true);
		setUndecorated(undecorated);
		setVisible(true);

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage("back.png"), 0, 0, width,
				height, null);
		setContentPane(new JLabel(new ImageIcon(img)));

		list = new JList<String>(text); // data has type Object[]
		list.setSize(new Dimension(width, height));
		list.setBounds((screen.width - width) / 2 + width / 4,
				(screen.height - height) / 2, width / 2, height);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setForeground(Color.white);
		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.cyan);
		list.setVisibleRowCount(text.length);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.addListSelectionListener(this);
		list.setOpaque(false);

		if (Autoselect > -1) {
			list.setSelectedIndex(Autoselect);
		}

		add(list);
		((DefaultListCellRenderer) list.getCellRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object q = e.getSource();

		if (q == list) {
			list.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1
							&& e.getClickCount() == 2) {
						Loader.changeCfgValue("Resourcepack",
								text[list.getSelectedIndex()]);
						op.setVisible(true);
						op.setAutoRequestFocus(true);
						dispose();
					}

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// Ignore

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// Ignore

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// Ignore

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// Ignore

				}
			});
		}

	}

}
