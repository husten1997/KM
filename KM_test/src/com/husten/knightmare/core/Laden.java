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

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.richard.knightmare.util.Loader;

//M
@SuppressWarnings("serial")
public class Laden extends JFrame implements KeyListener, ListSelectionListener, ActionListener {

	private JList<String> list;
	private JButton zurück;
	private String[] data = {"Keine Speicherstände vorhanden. Neues Spiel?" };
	private MainMenue mm;

	public Laden(MainMenue a) {
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
		// Set your Image Here.
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage("back.png"), 0, 0, width, height, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(Loader.getImage("Ritter.png"));
		setTitle("Knightmare");

		setUndecorated(Loader.getCfgValue("Fullscreen").equals("true"));
		setSize(screen);
		setVisible(true);

		setLocationRelativeTo(null);

		list = new JList<String>(data); // data has type Object[]
		list.setSize(new Dimension(width, height));
		list.setBounds((screen.width - width) / 2 + width / 4, (screen.height - height) / 2, width / 2, height);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setForeground(Color.white);
		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.cyan);
		list.setVisibleRowCount(data.length);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.setOpaque(false);
		add(list);
		((DefaultListCellRenderer) list.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);

		zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);
		add(zurück);

		mm = a;

		list.addKeyListener(this);
		list.addListSelectionListener(this);

	}

	private void performAction(int x) {
		if (data[0].equals("Keine Speicherstände vorhanden. Neues Spiel?")) {
			mm.setVisible(true);
			mm.setAutoRequestFocus(true);
			dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyText(arg0.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Bestätigen"))) {
			performAction(list.getSelectedIndex());
		} else if (arg0.getKeyText(arg0.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Escape/Zurück"))) {
			mm.setVisible(true);
			mm.setAutoRequestFocus(true);
			dispose();
		} else {
			if (arg0.getKeyText(arg0.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Fenster- u. Vollbildmodus"))){
				dispose();
				mm.dispose();
				mm.setUndecorated(!isUndecorated());
				mm.setVisible(true);
				mm.setAutoRequestFocus(true);
				mm.setLocationRelativeTo(null);
				setUndecorated(!isUndecorated());
				setVisible(true);
				Loader.changeCfgValue("Fullscreen", String.valueOf(isUndecorated()));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		Object q = arg0.getSource();

		if (q == list) {
			list.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
						performAction(list.getSelectedIndex());
					}

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == zurück) {
			mm.setVisible(true);
			mm.setAutoRequestFocus(true);
			dispose();
		}
	}

}
