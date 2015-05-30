package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.richard.knightmare.util.Loader;

//M
public class Laden extends JFrame implements ListSelectionListener {

	private JList list;
	private String[] data = { "getSpeicherStand1", "getSpeicherstand2", "getSpeicherstandX"};
	private String zurück = "Zurück";
	private MainMenueJFrame mm;

	public Laden(int w, int h, boolean u, MainMenueJFrame a) {
		setSize(w, h);
		setUndecorated(u);
		setVisible(true);
		
		Loader.initLoaderWithoutLoad("Ares", "Knightmare");
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage("back.png"), 0, 0, w,
				h, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		
		String[] b = new String[data.length+1];
		
		for (int i = 0; i < data.length; i++){
			b[i] = data[i];
		}
		
		setLocationRelativeTo(null);
		
		b[data.length] = zurück;
		
		list = new JList(b); // data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.black);
		list.setForeground(Color.white);
		list.addListSelectionListener(this);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(data.length+1);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.setSize(new Dimension(w, h));
		list.setOpaque(false);
		
		setLayout(new FlowLayout());
		add(list);
		mm = a;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object q = e.getSource();

		if (q == list) {
			if (list.getSelectedIndex() == data.length) {
				mm.setVisible(true);
				dispose();
			} else {
				//TODO load(list.getSelectedIndex());
			}
		}

	}

}
