package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class ResourcepackTest extends Optionsframesuperklasse implements ListSelectionListener, ActionListener{

	private JList<String> list;
	private File path;
	private String[] text;
	private JButton zurück;
	private String fehler;
	
	public ResourcepackTest(){
		super("back.png", "Knightmare: Resourcepack");
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

		String gewahlt = Loader.getCfgValue("Resourcepack");

		int Autoselect = -1;

		for (int i = 0; i < text.length; i++) {
			if (gewahlt.equals(text[i])) {
				Autoselect = i;
			}
		}
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
							OptionenTest.instance.setVisible(true);
							OptionenTest.instance.setAutoRequestFocus(true);
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
			OptionenTest.instance.setVisible(true);
			OptionenTest.instance.setAutoRequestFocus(true);
			dispose();
		}

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
			dispose();
			setUndecorated(!isUndecorated());
			setVisible(true);
			setAutoRequestFocus(true);
			setLocationRelativeTo(null);
			Loader.changeCfgValue("Fullscreen", String.valueOf(isUndecorated()));
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Volume -"))) {
			MoodMusic.changeVolume(-0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Volume +"))) {
			MoodMusic.changeVolume(+0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Escape/Zurück"))) {
			OptionenTest.instance.setVisible(true);
			OptionenTest.instance.setAutoRequestFocus(true);
			dispose();
		}
	}
}
