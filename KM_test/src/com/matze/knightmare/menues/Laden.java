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
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.husten.knightmare.core.MainMenue;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Laden extends Optionsframesuperklasse implements ActionListener, ListSelectionListener {

	private JList<String> list;
	private JButton zurück;
	private String[] data;
	private File[] removeAble;
	private String defaultText[] = { "Keine Speicherstände vorhanden. Neues Spiel?"};
	private JButton löschen;
	private boolean speichVorhanden, open = false;
	
	public Laden() {
		super("back.png", "Knightmare: Laden");
		setLocationRelativeTo(null);
		
		File[] remove = Loader.getSavesDir().listFiles();
		String a[] = Loader.getSavesDir().list();
		
		for (int i = 0; i < a.length; i++){
			if (!(a[i].startsWith("save") && remove[i].isDirectory() && remove[i].listFiles().length > 1)) {
				a[i] = "";
				remove[i] = null;
				for (int f = i; f < a.length-1; f++){
					remove[f] = remove[f+1]; 
					a[f] = a[f+1];
				}
			}		
		}
		
		int durch = 0;
		if (a.length != 0) {
			while (a[durch] != "") {
				durch++;
				if (durch >= a.length) {
					break;
				}
			}
		}
		
		data = new String[durch];
		removeAble = new File[durch];
		
		for (int i = 0; i < durch; i++){
			data[i] = a[i];
			removeAble[i] = remove[i];
		}

		speichVorhanden = !(data.length == 0);

		list = new JList<String>(!speichVorhanden? defaultText : data); // data
																			// has
																			// type
																			// Object[]
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
		((DefaultListCellRenderer) list.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds((screen.width - width) / 2 + width / 4, (screen.height - height) / 2, width / 2, height);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setBackground(new Color(0, 0, 0.25f, 0.25f));
		scroll.getVerticalScrollBar().setForeground(new Color(0, 0, 0.25f, 0.25f));
		scroll.getHorizontalScrollBar().setBackground(new Color(0, 0, 0.25f, 0.25f));
		scroll.getHorizontalScrollBar().setForeground(new Color(0, 0, 0.25f, 0.25f));
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

		if (data.length > 0) {
			löschen = new JButton("Spielstand löschen");
			löschen.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
			löschen.setFont(new Font("Arial", Font.BOLD, width / 100));
			löschen.setBounds((screen.width - width) / 2,
					(screen.height - height) / 2 + height - width / 24,
					width / 8, width / 24);
			löschen.addActionListener(this);
			löschen.setRolloverEnabled(false);
			löschen.setFocusable(false);
			add(löschen);
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

		list.addKeyListener(this);
		list.addListSelectionListener(this);
	}

	private void performAction(int x) {
		System.out.println("Hier at" + System.currentTimeMillis());
		if (!open && speichVorhanden == false && list.getSelectedIndex() == 0 && list.getSelectedValue().equals("Keine Speicherstände vorhanden. Neues Spiel?")) {
			new Configscreen("back.png","Configscreen");
			open = true;
			dispose();
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
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
			dispose();
			setUndecorated(!isUndecorated());
			setVisible(true);
			setAutoRequestFocus(true);
			setLocationRelativeTo(null);
			Loader.changeCfgValue("Fullscreen", String.valueOf(isUndecorated()));
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume -"))) {
			MoodMusic.changeVolume(-0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume +"))) {
			MoodMusic.changeVolume(+0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Escape/Zurück"))) {
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: V-Sync"))){
			Loader.changeCfgValue("CONTROL_KEY: V-Sync", Loader.getCfgValue("CONTROL_KEY: V-Sync").equals("On")?"Off":"On");
		}
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Abreißen"))){
			if (data.length > 0) {
				recursicDelete(removeAble[list.getSelectedIndex()]);
				new Laden();
				dispose();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == zurück) {
			MainMenue.instance.dispose();
			MainMenue.instance.setUndecorated(isUndecorated());
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
		if (arg0.getSource() == löschen) {
			recursicDelete(removeAble[list.getSelectedIndex()]);
			new Laden();
			dispose();
		}
	}
}
