package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.core.MainMenue;
import com.matze.knightmare.meshes.Spieler;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Optionsframesuperklasse;

public class Configscreen extends Optionsframesuperklasse implements ActionListener, ItemListener{

	private JButton zurück, spielStarten;
	private Spieler spieler[];
	private JComboBox cB;
	private JTextField name[], beschreibung[];
	private JComboBox team[], spielerArt[], schwierigkeit[];
	private static String[] value = new String[8];
	private int ausgeführt,anzahl;
	
	private String art[] = {"KI","Mensch"};
	private String teamName[];
	private String schwierigkeiten[] = {"Leicht", "Mittel", "Schwer"};
	private String inhalt[] = {"Spieleranzahl:", "Name:", "Typ:", "Team:", "Schwierigkeit:"};
	
	public Configscreen(String imgName, String name) {
		super(imgName, name);
		
		for (int i = 0; i < 8; i++){
			value[0]="Bitte wählen";
			value[i]=""+(i+1);
		}
		
		beschreibung = new JTextField[5];
		beschreibung[0]=new JTextField(inhalt[0]);
		beschreibung[0].setEditable(false);
		beschreibung[0].setFocusable(false);
		beschreibung[0].setBounds(100, 100, 100, 50);
		add(beschreibung[0]);
		for (int i = 0; i < 4; i++){
			beschreibung[i] = new JTextField(inhalt[i+1]);
			beschreibung[i].setBounds(100+(i*150), 200, 100, 50);
			beschreibung[i].setEditable(false);
			beschreibung[i].setFocusable(false);
			add(beschreibung[i]);
		}
		
		cB = new JComboBox(value);
		cB.addItemListener(this);
		cB.setBounds(200, 100, 100, 50);
		add(cB);
		
		//Buttons:
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
		
		spielStarten = new JButton("Starten");
		spielStarten.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		spielStarten.setFont(new Font("Arial", Font.BOLD, width / 48));
		spielStarten.setBounds(screen.width / 2 + 2 * width / 8,
				(screen.height - height) / 2 + height - width / 24, width / 8,
				width / 24);
		spielStarten.addActionListener(this);
		spielStarten.setRolloverEnabled(false);
		spielStarten.setFocusable(false);
		spielStarten.setEnabled(false);
		add(spielStarten);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == zurück) {
			MainMenue.instance.dispose();
			MainMenue.instance.setUndecorated(isUndecorated());
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
		
		if(e.getSource() == spielStarten){
			for (int i = 0; i < anzahl; i++){
				int teamer = 0;
				for (int s = 1; s < anzahl+1; s++){
					if(team[i].getSelectedItem().toString().endsWith(s+"")){
						teamer = s-1;
					}
				}
				System.out.println("Name: " + name[i].getText() + " Team: " + teamer + " SpielerArt: " + spielerArt[i].getSelectedItem().toString() + " Schwierigkeit: " +schwierigkeit[i].getSelectedItem().toString());
				spieler[i] = new Spieler(i,name[i].getText(),teamer,spielerArt[i].getSelectedItem().toString(),schwierigkeit[i].getSelectedItem().toString());
			}
			dispose();
			new Timer(false).schedule(new TimerTask() {

				@Override
				public void run() {
					Loadscreen l = new Loadscreen();
					Knightmare km = new Knightmare(spieler);
					MoodMusic.changeMood("Default");
					l.dispose();
					km.loop();
				}
			}, 0);
		}
		
	}



	@Override
	public void itemStateChanged(ItemEvent arg0) {
		Object q = arg0.getSource();
		
		if ((q == cB) && (!value[cB.getSelectedIndex()].equals(value[0]))){
			ausgeführt++;
			if (ausgeführt != 0){
				remove();
			}
			spielStarten.setEnabled(true);
			anzahl = cB.getSelectedIndex()+1;
			spieler = new Spieler[anzahl];
			name = new JTextField[anzahl];
			teamName = new String[anzahl];
			spielerArt = new JComboBox[anzahl];
		    team = new JComboBox[anzahl];
		    schwierigkeit = new JComboBox[anzahl];
			
			for (int i = 0; i < anzahl; i++){
				teamName[i] = "Team " + (i+1);
			}
			
			for (int i = 0; i < anzahl; i++){
				spielerArt[i] = new JComboBox(art);
				team[i] = new JComboBox(teamName);
				schwierigkeit[i] = new JComboBox(schwierigkeiten);
			}
			
			for (int i = 0; i < anzahl; i++){
				name[i]= new JTextField("Spieler " + (i+1));
				name[i].setBounds(100, 260+(i*60), 100, 50);
				spielerArt[i].setBounds(250, 260+(i*60), 100, 50);
				if (i == 0)
				spielerArt[i].setSelectedIndex(1);
				team[i].setBounds(400, 260+(i*60), 100, 50);
				team[i].setSelectedIndex(i);
				schwierigkeit[i].setBounds(550, 260+(i*60), 100, 50);
				add(name[i]);
				add(spielerArt[i]);
				add(team[i]);
				add(schwierigkeit[i]);
				validate();
				repaint();
			}
		}
		
	}
	
	public void remove(){
		for (int i = 0; i < anzahl; i++){
			this.remove(name[i]);
			this.remove(spielerArt[i]);
			this.remove(team[i]);
			this.remove(schwierigkeit[i]);
		}
	}

}
