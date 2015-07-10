package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.matze.knightmare.meshes.Bauen;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Wiki extends Optionsframesuperklasse implements ActionListener {

	private JList<String> list;
	private JButton zur�ck;
	private String vvor = "Geb�udebeschreibungen:";
	private String[] erkl�rung = {
			"Produziert Kohle",
			"Produziert Eisen, ben�tigt Kohle um es das Eisen zu schmelzen",
			"Lagerplatz f�r einige Waren, wenn voll werden die Waren an Arme verschenkt",
			"Holzt B�ume in der N�he ab, bis er keine mehr findet",
			"Ben�tigt zwei Menschen und produziert ab dann 1/min, verbraucht Nahrung, bringt Steuern",
			"Ben�tigt Sand, produziert Glas",
			"Braucht Felder, die einen Samen(Getreide) zum anpflanzen ben�tigen um daraus 20 Getreide zu erstellen",
			"Produziert Nahrung",
			"Produziert Stein",
			"Verteidigt deine Stadt",
			"Verteidigt deine Stadt",
			"Beinhaltet 20 Holz",
			"Lagert dein Einkommen, wenn es voll ist wird dein Einkommen an Arme verschenkt",
			"Lagert Waffen, wenn voll werden die Waffen an die Bauern verschenkt",
			"Lagert Nahrung, wenn voll wird eine doppelte Ration ausgegeben",
			"Muss platziert werden um die 1. Bev�lkerung zu erhalten und um zu Handeln",
			"Kann durch F�rster zu einem Baum ausgebaut werden",
			"Produziert aus 3 Weizen 1 Brot", "Erm�glicht Rekrutrierung",
			"Stellt Waffen her",
			"Wird von einem Bauernhof zum Bewirtschaften ben�tigt",
			"Produziert Sand", "Ben�tigt einen Setzling(Holz) und pflanzt damit einen Baum", "Bildet eure B�rger aus, ben�tigt jeden Tag eine zuf�llige Ressource um zu forschen, ist diese nicht vorhanden kann keiner ausgebildet werden",
			"Bekehrt dir Leute zum Glauben, die Kirche gew�hrt dir einen Steuerbonus"};
	private String spielz�ge[] = {" ", "Wichtige Schritte zum Erfolgreichen Abschluss des Spiels:", "     Zu Beginn muss ein Lagerhaus platziert werden", "     Danach muss ein Marktplatz errichtet werden", "     Freie Bev�lkerung verbraucht 0.5 Nahrung pro Tag"};
	private String[] data = new String[1+erkl�rung.length+spielz�ge.length];

	public Wiki(String s, String b) {
		super(s, b);

		data[0] = vvor;
		
		for (int i = 1; i < erkl�rung.length+1; i++) {
			data[i] = "     " + Bauen.getBuildingName(i-1) + ": " + erkl�rung[i-1];
		} 
		for (int i = erkl�rung.length+1; i < data.length; i++){
			data[i] = spielz�ge[i-erkl�rung.length-1];
		}

		list = new JList<>(data);
		list.setSize(new Dimension(width, height));
		list.setBounds((screen.width - width) / 2 + width / 8,
				(screen.height - height) / 2, 3 * width / 4, height);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setForeground(Color.white);
		list.setSelectionBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setSelectionForeground(Color.white);
		list.setVisibleRowCount(data.length);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.setFocusable(false);
		list.setOpaque(false);
		add(list);
		// ((DefaultListCellRenderer)
		// list.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds((screen.width - width) / 2 + width / 8,
				(screen.height - height) / 2, 3 * width / 4, height);
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

		zur�ck = new JButton("Zur�ck");
		zur�ck.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zur�ck.setFont(new Font("Arial", Font.BOLD, width / 48));
		zur�ck.setBounds(screen.width / 2 + 3 * width / 8,
				(screen.height - height) / 2 + height - width / 24, width / 8,
				width / 24);
		zur�ck.addActionListener(this);
		zur�ck.setRolloverEnabled(false);
		zur�ck.setFocusable(false);
		add(zur�ck);

	}
	
	public void setSelectedIndexOn(int id){
		inGame = true;
		list.setFocusable(true);
		list.setSelectedIndex(id+1);
		repaint();
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == zur�ck) {
			new Profil(inGame, "back.png", "Knightmare: Profil", false);
			dispose();
		}
	}
}
