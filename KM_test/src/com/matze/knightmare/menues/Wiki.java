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
	private JButton zurück;
	private String vvor = "Gebäudebeschreibungen:";
	private String[] erklärung = {
			"Produziert Kohle",
			"Produziert Eisen, benötigt Kohle um es das Eisen zu schmelzen",
			"Lagerplatz für einige Waren, wenn voll werden die Waren an Arme verschenkt",
			"Holzt Bäume in der Nähe ab, bis er keine mehr findet",
			"Benötigt zwei Menschen und produziert ab dann 1/min, verbraucht Nahrung, bringt Steuern",
			"Benötigt Sand, produziert Glas",
			"Braucht Felder, die einen Samen(Getreide) zum anpflanzen benötigen um daraus 20 Getreide zu erstellen",
			"Produziert Nahrung",
			"Produziert Stein",
			"Verteidigt deine Stadt",
			"Verteidigt deine Stadt",
			"Beinhaltet 20 Holz",
			"Lagert dein Einkommen, wenn es voll ist wird dein Einkommen an Arme verschenkt",
			"Lagert Waffen, wenn voll werden die Waffen an die Bauern verschenkt",
			"Lagert Nahrung, wenn voll wird eine doppelte Ration ausgegeben",
			"Muss platziert werden um die 1. Bevölkerung zu erhalten und um zu Handeln",
			"Kann durch Förster zu einem Baum ausgebaut werden",
			"Produziert aus 3 Weizen 1 Brot", "Ermöglicht Rekrutrierung",
			"Stellt Waffen her",
			"Wird von einem Bauernhof zum Bewirtschaften benötigt",
			"Produziert Sand", "Benötigt einen Setzling(Holz) und pflanzt damit einen Baum", "Bildet eure Bürger aus, benötigt jeden Tag eine zufällige Ressource um zu forschen, ist diese nicht vorhanden kann keiner ausgebildet werden",
			"Bekehrt dir Leute zum Glauben, die Kirche gewährt dir einen Steuerbonus"};
	private String spielzüge[] = {" ", "Wichtige Schritte zum Erfolgreichen Abschluss des Spiels:", "     Zu Beginn muss ein Lagerhaus platziert werden", "     Danach muss ein Marktplatz errichtet werden", "     Freie Bevölkerung verbraucht 0.5 Nahrung pro Tag"};
	private String[] data = new String[1+erklärung.length+spielzüge.length];

	public Wiki(String s, String b) {
		super(s, b);

		data[0] = vvor;
		
		for (int i = 1; i < erklärung.length+1; i++) {
			data[i] = "     " + Bauen.getBuildingName(i-1) + ": " + erklärung[i-1];
		} 
		for (int i = erklärung.length+1; i < data.length; i++){
			data[i] = spielzüge[i-erklärung.length-1];
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
		if (arg0.getSource() == zurück) {
			new Profil(inGame, "back.png", "Knightmare: Profil", false);
			dispose();
		}
	}
}
