package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.DefaultButtonRenderer;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Tastenbelegung extends Optionsframesuperklasse implements ActionListener, ListSelectionListener {

	private int ButtonClicked = -1;
	private boolean changing = false;
	private JButton zurück;
	private JList<String> list;
	private String hilfe[];
	private String text[] = { "Vorwärts", "Rückwärts", "Links", "Rechts", "Kamera oben", "Kamera unten", "Kamera links", "Kamera rechts", "Escape/Zurück", "Bestätigen",
			"Fenster- u. Vollbildmodus", "Volume +", "Volume -", "Musik wechseln", "Scrollen +", "Scrollen -" };

	public Tastenbelegung() {
		super("back.png", "Knightmare: Tastenbelegung");

		hilfe = new String[text.length];
		String[] leer = new String[text.length];

		for (int i = 0; i < text.length; i++) {
			hilfe[i] = text[i] + ": " + Loader.getCfgValue("CONTROL_KEY: " + text[i]);
			leer[i] = " ";
		}

		list = new JList<String>(hilfe);
		zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);

		list.setSize(new Dimension(width, height));
		list.setBounds((screen.width - width) / 2 + width / 4, (screen.height - height) / 2, width / 2, height);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setForeground(Color.white);
		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.cyan);
		list.setVisibleRowCount(text.length);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.setOpaque(false);
		list.addListSelectionListener(this);
		list.addKeyListener(this);
		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				ButtonClicked = list.getSelectedIndex();
				hilfe[ButtonClicked] = text[ButtonClicked] + ": Taste drücken";
				repaint();
				changing = true;
			}
		});
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

		add(scroll);
		add(zurück);

		JList<String> list = new JList<>(leer);
		list.setCellRenderer(new DefaultButtonRenderer());
		list.setSize(new Dimension(120, height));
		list.setBounds((screen.width - width) / 2 + width / 4 - 120, (screen.height - height) / 2, 120, height);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setForeground(Color.white);
		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.cyan);
		list.setVisibleRowCount(text.length);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.setOpaque(false);
		list.addListSelectionListener(this);
		list.addKeyListener(this);
		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// Ignore
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = list.getSelectedIndex();
				Loader.resetCfgValue("CONTROL_KEY: " + text[index]);
				hilfe[index] = text[index] + ": " + Loader.getCfgValue("CONTROL_KEY: " + text[index]);
				repaint();
			}
		});
		JScrollPane scrol = new JScrollPane(list);
		scrol.setBounds((screen.width - width) / 2 + width / 4 - 120, (screen.height - height) / 2, 120, height);
		scrol.setBorder(null);
		scrol.getVerticalScrollBar().setBackground(new Color(0, 0, 0.25f, 0.25f));
		scrol.getVerticalScrollBar().setForeground(new Color(0, 0, 0.25f, 0.25f));
		scrol.getHorizontalScrollBar().setBackground(new Color(0, 0, 0.25f, 0.25f));
		scrol.getHorizontalScrollBar().setForeground(new Color(0, 0, 0.25f, 0.25f));
		scrol.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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
				// TODO
			}
		});
		scrol.getHorizontalScrollBar().setFocusable(false);
		scrol.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
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
				// TODO
			}
		});
		scrol.getHorizontalScrollBar().setFocusable(false);
		scrol.setOpaque(false);
		scrol.getViewport().setOpaque(false);
		add(scrol);

		scrol.getVerticalScrollBar().addMouseListener(new MouseListener() {

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
		scrol.getHorizontalScrollBar().addMouseListener(new MouseListener() {

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
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (ButtonClicked > -1 && changing) {
			Loader.changeCfgValue("CONTROL_KEY: " + text[ButtonClicked], KeyEvent.getKeyText(e.getExtendedKeyCode()));
			hilfe[ButtonClicked] = text[ButtonClicked] + ": " + Loader.getCfgValue("CONTROL_KEY: " + text[ButtonClicked]);
			repaint();
			validate();
			ButtonClicked = -1;
			changing = false;
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Bestätigen"))) {
			ButtonClicked = list.getSelectedIndex();
			hilfe[ButtonClicked] = "Drücke eine Taste zum Zuweisen";
			repaint();
			changing = true;
		} else {
			if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
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
				Optionen.instance.setUndecorated(isUndecorated());
				Optionen.instance.setVisible(true);
				Optionen.instance.setAutoRequestFocus(true);
				dispose();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object q = e.getSource();

		if (q == zurück) {
			Optionen.instance.setUndecorated(isUndecorated());
			Optionen.instance.setVisible(true);
			Optionen.instance.setAutoRequestFocus(true);
			dispose();
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == list) {
			// ButtonClicked=list.getSelectedIndex();
			// hilfe[ButtonClicked] = "Drücke eine Taste zum Zuweisen";
			repaint();
			validate();
		}
	}
}
