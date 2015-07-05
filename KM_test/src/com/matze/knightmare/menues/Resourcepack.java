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

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.ImageListRenderer;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Resourcepack extends Optionsframesuperklasse implements
		ListSelectionListener, ActionListener {

	private JList<String> list;
	private File path;
	private String[] text;
	private File[] ordner;
	private File[] deleteable;
	private JButton zurück;
	private JButton dele;
	private String fehler[] = new String[3];
	private int first;

	public Resourcepack(boolean inG) {
		super("back.png", "Knightmare: Resourcepack");
		fehler[0] = "(bF) Resourcepack kann nicht 'Default' heißen.";
		fehler[1] = "(bF) Das ist kein Ordner: ";
		fehler[2] = "(bF) Kein gültiges Resourcepack: ";

		inGame = inG;
		
		first = 0;
		
		path = new File(new StringBuilder("C:\\Users\\")
				.append(System.getProperty("user.name"))
				.append("\\Appdata\\Roaming\\ares\\Knightmare\\resourcepacks")
				.toString());
		text = new String[path.list().length + 1];

		ordner = path.listFiles();
		File[] del = new File[ordner.length];
		int delA = 0;

		for (int i = 0; i < text.length; i++) {
			if (i == 0) {
				text[i] = "Default";
			} else {
				// Name = Default
				if (path.list()[i - 1].equals("Default")) {
					text[i] = fehler[0];
					del[delA] = ordner[i - 1];
					delA++;
				} else {
					// Kein Ordner
					if (!ordner[i - 1].isDirectory()) {
						text[i] = fehler[1] + path.list()[i - 1];
						del[delA] = ordner[i - 1];
						delA++;
					} else {
						// Kein gültiger Ordner
						if (ordner[i - 1].listFiles().length < 3) {
							text[i] = fehler[2] + ordner[i - 1].getName();
							del[delA] = ordner[i - 1];
							delA++;
						} else {
							text[i] = path.list()[i - 1];
						}
					}
				}
			}
		}

		list = new JList<String>(text); // data has type Object[]
		list.setCellRenderer(new ImageListRenderer());
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
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds((screen.width - width) / 2 + width / 4,
				(screen.height - height) / 2, width / 2, height);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setBackground(
				new Color(0, 0, 0.25f, 0.25f));
		scroll.getVerticalScrollBar().setForeground(
				new Color(0, 0, 0.25f, 0.25f));
		scroll.getHorizontalScrollBar().setBackground(
				new Color(0, 0, 0.25f, 0.25f));
		scroll.getHorizontalScrollBar().setForeground(
				new Color(0, 0, 0.25f, 0.25f));
		scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
				return b;
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
				return b;
			}

			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(0, 0, 0.25f, 0.25f);
			}
		});
		scroll.getHorizontalScrollBar().setFocusable(false);
		scroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
				return b;
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
				return b;
			}

			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(0, 0, 0.25f, 0.25f);
			}
		});
		scroll.getHorizontalScrollBar().setFocusable(false);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		add(scroll);

		scroll.getVerticalScrollBar().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				repaint();
			}
		});
		scroll.getHorizontalScrollBar().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				repaint();
			}
		});

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

		add(scroll);
		
		if (delA > 0) {
			deleteable = new File[delA];

			for (int i = 0; i < delA; i++) {
				deleteable[i] = del[i];
			}
			dele = new JButton("Remove bad Files (bF)");
			dele.addActionListener(this);
			dele.setBounds(0, 0, 100, 300);
			dele.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
			dele.setFont(new Font("Arial", Font.BOLD, width / 100));
			dele.setBounds((screen.width-width)/2,
					(screen.height - height) / 2 + height - width / 24, width / 8,
					width / 24);
			dele.addActionListener(this);
			dele.setRolloverEnabled(false);
			dele.setFocusable(false);
			add(dele);
		}

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
						if (!text[list.getSelectedIndex()].equals(fehler[0])
								&& (!text[list.getSelectedIndex()]
										.startsWith(fehler[1]))
								&& !text[list.getSelectedIndex()]
										.startsWith(fehler[2])) {
							Loader.changeCfgValue("Resourcepack",
									text[list.getSelectedIndex()]);
							if (inGame){
								InGameOptionen.instance.dispose();
								InGameOptionen.instance.setUndecorated(isUndecorated());
								InGameOptionen.instance.setVisible(true);
								InGameOptionen.instance.setAutoRequestFocus(true);
								dispose();
							} else {
								Optionen.instance.dispose();
								Optionen.instance.setUndecorated(isUndecorated());
								Optionen.instance.setVisible(true);
								Optionen.instance.setAutoRequestFocus(true);
								dispose();
							}
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
	
	private void recursicDelete(File file){
		if(file.isDirectory()){
			String[] names = file.list();
			for(int i = 0; i<names.length; i++){
				recursicDelete(new File(new StringBuilder(file.getAbsolutePath()).append("\\").append(names[i]).toString()));
			}
			file.delete();
		}else{
			file.delete();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == zurück) {
			if (inGame){
				InGameOptionen.instance.setUndecorated(isUndecorated());
				InGameOptionen.instance.setVisible(true);
				InGameOptionen.instance.setAutoRequestFocus(true);
				dispose();
			} else {
				Optionen.instance.setUndecorated(isUndecorated());
				Optionen.instance.setVisible(true);
				Optionen.instance.setAutoRequestFocus(true);
				dispose();
			}
		}
		
		
		if (e.getSource() == dele && first < 2) {
			dele.setText("Drücke erneut");
			repaint();
		}

		
		if (e.getSource() == dele && first == 2) {
			for (int i = 0; i < deleteable.length; i++) {
				recursicDelete(deleteable[i]);
			}
			if (inGame){
				InGameOptionen.instance.setUndecorated(isUndecorated());
				InGameOptionen.instance.setVisible(true);
				InGameOptionen.instance.setAutoRequestFocus(true);
				dispose();
			} else {
				Optionen.instance.setUndecorated(isUndecorated());
				Optionen.instance.setVisible(true);
				Optionen.instance.setAutoRequestFocus(true);
				dispose();
			}
		}
		first++;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(
				getString("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
			if (!inGame){
			dispose();
			setUndecorated(!isUndecorated());
			setVisible(true);
			setAutoRequestFocus(true);
			setLocationRelativeTo(null);
			Loader.changeCfgValue("SETTINGS: Fenstermodus", String.valueOf(!isUndecorated()));
			}
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(
				getString("CONTROL_KEY: Volume -"))) {
			MoodMusic.changeVolume(-0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(
				getString("CONTROL_KEY: Volume +"))) {
			MoodMusic.changeVolume(+0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(
				getString("CONTROL_KEY: Escape/Zurück"))) {
			if (inGame){
				InGameOptionen.instance.setUndecorated(isUndecorated());
				InGameOptionen.instance.setVisible(true);
				InGameOptionen.instance.setAutoRequestFocus(true);
				dispose();
			} else {
				Optionen.instance.setUndecorated(isUndecorated());
				Optionen.instance.setVisible(true);
				Optionen.instance.setAutoRequestFocus(true);
				dispose();
			}
		}
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(
				Loader.getCfgValue("CONTROL_KEY: V-Sync"))) {
			Loader.changeCfgValue("SETTINGS: V-Sync",
					Loader.getCfgValue("SETTINGS: V-Sync").equals("On") ? "Off"
							: "On");
		}
	}
}
