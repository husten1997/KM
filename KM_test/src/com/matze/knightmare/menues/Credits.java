package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.husten.knightmare.constants.StringConstants.names;
import com.husten.knightmare.core.MainMenue;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Credits extends Optionsframesuperklasse implements ActionListener {

	JList<String> list;
	String[] data = { "Lead-Programmer:", names.R, " ", "Graphic-Programmer:", names.H, " ", "Outsourced Resource Manager & Assistant Programmer:", names.M, " ",
			"Preloading:", names.R, "Config-File:", names.M, "Worldgen:", names.H, "Soundsystem:", names.M + ", " + names.R, "Troops & Buildings:", names.M,
			"Resourcepacks:", names.R, "MainGame:", names.H, "Combat System:", names.M, "Pathfinding:", names.R, "Main Menue:", names.M + ", " + names.R, " ",
			"Spacial Thanks:", "Stefan Schmalzbauer for awsome music", "Jean-Francois Frauendorfer for textures" };

	public Credits() {
		super("back.png", "Knightmare: Credits");
		list = new JList<>(data);
		list.setSize(new Dimension(width, height));
		list.setBounds((screen.width - width) / 2 + width / 8, (screen.height - height) / 2, 3 * width / 4, height);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setForeground(Color.white);
		list.setSelectionBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setSelectionForeground(Color.white);
		list.setVisibleRowCount(data.length);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.setOpaque(false);
		list.setSelectedIndex(0);
		((DefaultListCellRenderer) list.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds((screen.width - width) / 2 + width / 8, (screen.height - height) / 2, 3 * width / 4, height);
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

		JButton zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);
		add(zurück);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainMenue.instance.dispose();
		MainMenue.instance.setUndecorated(isUndecorated());
		MainMenue.instance.setVisible(true);
		MainMenue.instance.setAutoRequestFocus(true);
		dispose();
	}
}
