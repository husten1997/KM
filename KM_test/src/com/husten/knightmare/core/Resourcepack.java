package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.richard.knightmare.util.Loader;

@SuppressWarnings("serial")
public class Resourcepack extends JFrame implements ListSelectionListener,
		KeyListener, ActionListener {

	private JList<String> list;
	private File path;
	private String[] text;
	private Optionen op;
	private JButton zurück;
	private String fehler;

	public Resourcepack(Optionen o) {
		Loader.initLoaderWithoutLoad("Ares", "Knightmare");

		op = o;
		fehler = "Resourcepack kann nicht 'Default' heißen";

		path = new File(new StringBuilder("C:\\Users\\")
				.append(System.getProperty("user.name"))
				.append("\\Appdata\\Roaming\\ares\\Knightmare\\resourcepacks")
				.toString());
		text = new String[path.list().length + 1];

		for (int i = 0; i < text.length; i++) {
			if (i == 0) {
				text[i] = "Default";
			} else {
				if (path.list()[i - 1].equals("Default")) {
					text[i] = fehler;
				} else {
					text[i] = path.list()[i - 1];
				}
			}
		}

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
		setBackground(Color.BLACK);

		String gewahlt = Loader.getCfgValue("Resourcepack");

		int Autoselect = -1;

		for (int i = 0; i < text.length; i++) {
			if (gewahlt.equals(text[i])) {
				Autoselect = i;
			}
		}

		setSize(screen);
		setAutoRequestFocus(true);
		setUndecorated(Loader.getCfgValue("Fullscreen").equals("true"));
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
		list.addKeyListener(this);

		if (Autoselect > -1) {
			list.setSelectedIndex(Autoselect);
		}

		add(list);
		((DefaultListCellRenderer) list.getCellRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8,
				(screen.height - height) / 2 + height - width / 24, width / 8,
				width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);
		add(zurück);
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
						if (!text[list.getSelectedIndex()].equals(fehler)) {
							Loader.changeCfgValue("Resourcepack",
									text[list.getSelectedIndex()]);
							op.setVisible(true);
							op.setAutoRequestFocus(true);
							dispose();
						}
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == zurück) {
			op.setVisible(true);
			op.setAutoRequestFocus(true);
			dispose();
		}

	}

	public void keyPressed(KeyEvent arg0) {
		if (arg0.getExtendedKeyCode() == 10) {
			Loader.changeCfgValue("Resourcepack", text[list.getSelectedIndex()]);
			op.setVisible(true);
			op.setAutoRequestFocus(true);
			dispose();
		} else if (arg0.getExtendedKeyCode() == 27) {
			op.setVisible(true);
			op.setAutoRequestFocus(true);
			dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
