package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.core.MainMenue;
import com.matze.knightmare.meshes.Spieler;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Configscreen extends Optionsframesuperklasse implements
		ActionListener, ItemListener, ChangeListener {

	// Basics
	private JButton zurück, spielStarten, bild[];
	private Spieler spieler[];
	private JComboBox<String> cB;
	private JTextField name[], beschreibung[];
	private JComboBox<String> team[], spielerArt[], schwierigkeit[];
	private static String[] value = new String[8];
	private int ausgeführt, anzahl;
	ImageIcon ic = new ImageIcon(Loader.getCfgValue("SETTINGS: Profilbild"));

	private String art[] = { "KI", "Mensch" };
	private String teamName[];
	private String schwierigkeiten[] = { "Leicht", "Mittel", "Schwer" };
	private String inhalt[] = { "Spieleranzahl:", "Name:", "Typ:", "Team:",
			"Schwierigkeit:", "Bild:" };
	private Color c[] = new Color[8];

	// Worldgen
	private JLabel[] sett;
	private JSlider[] settings;
	private int seedValue;
	private JTextField seed;
	private JButton random;
	private String[] setNames = { "Smoothing", "Wasserlevel", "Sandlevel",
			"Graslevel", "Steinlevel", "Eisenwahrscheinlichkeit",
			"Kohlewahrscheinlichkeit", "Routhness", "Falloff", "Uhrzeit" };

	public Configscreen(String imgName, String name) {
		super(imgName, name);

		// Worldgen
		seedValue = 1005464490;
		sett = new JLabel[setNames.length];
		settings = new JSlider[setNames.length];
		seed = new JTextField("" + seedValue);
		random = new JButton("Random");
		random.addActionListener(this);
		random.setBounds((screen.width - width) / 2 + 1300,
				(screen.height - height) / 2 + (75 * 6) + 25, 200, 50);
		seed.setBounds((screen.width - width) / 2 + 1000,
				(screen.height - height) / 2 + 75 * 6 + 25, 200, 50);

		for (int i = 0; i < setNames.length; i++) {
			sett[i] = new JLabel();
			sett[i].setText(setNames[i]);
			sett[i].setForeground(Color.WHITE);
			settings[i] = new JSlider();
			settings[i].setMaximum(100);
			settings[i].setMinimum(0);
			settings[i].setMinorTickSpacing(1);
			settings[i].setMajorTickSpacing(10);
			settings[i].addChangeListener(this);
			if (i < setNames.length / 2) {
				sett[i].setBounds((screen.width - width) / 2 + 1000,
						(screen.height - height) / 2 + 100 + (75 * i) - 35,
						200, 50);
				settings[i].setBounds((screen.width - width) / 2 + 1000,
						(screen.height - height) / 2 + 100 + (75 * i), 200, 50);
			} else {
				sett[i].setBounds((screen.width - width) / 2 + 1300,
						(screen.height - height) / 2 + 100
								+ (75 * (i - (setNames.length / 2))) - 35, 200,
						50);
				settings[i].setBounds((screen.width - width) / 2 + 1300,
						(screen.height - height) / 2 + 100
								+ (75 * (i - (setNames.length / 2))), 200, 50);
			}
			add(sett[i]);
			add(settings[i]);
		}

		settings[setNames.length - 1].setMaximum(23);
		settings[setNames.length - 1].setMinimum(0);
		settings[setNames.length - 1].setValue(10);

		add(random);
		add(seed);

		settings[0].setValue(71);
		settings[1].setValue(58);
		settings[2].setValue(61);
		settings[3].setValue(81);
		settings[4].setValue(100);
		settings[5].setValue(7);
		settings[6].setValue(12);
		settings[7].setValue(60);
		settings[8].setValue(80);

		// Basics
		for (int i = 0; i < 8; i++) {
			value[0] = "Bitte wählen";
			value[i] = "" + (i + 1);
		}

		beschreibung = new JTextField[5];
		beschreibung[0] = new JTextField(inhalt[0]);
		beschreibung[0].setEditable(false);
		beschreibung[0].setFocusable(false);
		beschreibung[0].setBounds((screen.width - width) / 2 + 100,
				(screen.height - height) / 2 + 100, 100, 50);
		beschreibung[0].setBorder(null);
		beschreibung[0].setBackground(new Color(0, 0.25f, 0.5f, 1f));
		beschreibung[0].setForeground(Color.white);
		beschreibung[0].setHorizontalAlignment(JLabel.CENTER);
		add(beschreibung[0]);
		for (int i = 0; i < 5; i++) {
			beschreibung[i] = new JTextField(inhalt[i + 1]);
			beschreibung[i].setBounds((screen.width - width) / 2 + 100
					+ (i * 150), (screen.height - height) / 2 + 200, 100, 50);
			beschreibung[i].setEditable(false);
			beschreibung[i].setFocusable(false);
			beschreibung[i].setBorder(null);
			beschreibung[i].setBackground(new Color(0, 0.25f, 0.5f, 1f));
			beschreibung[i].setForeground(Color.white);
			beschreibung[i].setHorizontalAlignment(JLabel.CENTER);
			add(beschreibung[i]);
		}
		refresh();

		cB = new JComboBox<String>(value);
		cB.addItemListener(this);
		cB.setBounds((screen.width - width) / 2 + 200,
				(screen.height - height) / 2 + 100, 100, 50);
		cB.setBorder(null);
		cB.setBackground(new Color(0, 0.25f, 0.5f, 1f));
		cB.setForeground(Color.white);
		add(cB);

		// Buttons:
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
		} else {
		
		if (bild[0].getIcon().toString().equals(Loader.getCfgValue("SETTINGS: Profilbild"))) {
			if (e.getSource() == bild[0]) {
				new Profil(false, "back.png", "Knightmare: Profil", true);
				this.dispose();
			}
		}

		if (e.getSource() == random) {
			seedValue = (int) (Math.random() * Integer.MAX_VALUE);
			seed.setText(seedValue + "");
		}

		if (e.getSource() == spielStarten) {
			System.out.println("Loader"
					+ Loader.getCfgValue("SETTINGS: Startzeit"));
			for (int i = 0; i < anzahl; i++) {
				int teamer = 0;
				for (int s = 1; s < anzahl + 1; s++) {
					if (team[i].getSelectedItem().toString().endsWith(s + "")) {
						teamer = s - 1;
					}
				}
				spieler[i] = new Spieler(i, name[i].getText(), teamer,
						spielerArt[i].getSelectedItem().toString(),
						schwierigkeit[i].getSelectedItem().toString(), anzahl);
			}
			dispose();
			new Timer(false).schedule(new TimerTask() {

				@Override
				public void run() {
					Loadscreen l = new Loadscreen();
					Knightmare km = new Knightmare(spieler);
					km.setAllWG(settings[0].getValue(),
							(double) settings[1].getValue() / 100,
							(double) settings[2].getValue() / 100,
							(double) settings[3].getValue() / 100,
							(double) settings[4].getValue() / 100,
							(double) settings[5].getValue() / 100,
							(double) settings[6].getValue() / 100,
							(float) settings[7].getValue() / 100,
							(float) settings[8].getValue() / 100, 2f,
							Integer.parseInt(seed.getText()));
					MoodMusic.changeMood("Default");
					l.dispose();
					km.loop();
				}
			}, 0);
		}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		Object q = arg0.getSource();

		if ((q == cB) && (!value[cB.getSelectedIndex()].equals(value[0]))) {
			ausgeführt++;
			if (ausgeführt != 0) {
				remove();
			}

			c[1] = Color.white;
			c[2] = Color.blue;
			c[3] = Color.gray;
			c[4] = Color.green;
			c[5] = Color.pink;
			c[6] = Color.magenta;
			c[7] = Color.CYAN;

			spielStarten.setEnabled(true);
			anzahl = cB.getSelectedIndex() + 1;
			spieler = new Spieler[anzahl];
			name = new JTextField[anzahl];
			teamName = new String[anzahl];
			bild = new JButton[anzahl];
			spielerArt = new JComboBox[anzahl];
			team = new JComboBox[anzahl];
			schwierigkeit = new JComboBox[anzahl];

			for (int i = 0; i < anzahl; i++) {
				teamName[i] = "Team " + (i + 1);
			}

			for (int i = 0; i < anzahl; i++) {
				spielerArt[i] = new JComboBox<String>(art);
				team[i] = new JComboBox<String>(teamName);
				schwierigkeit[i] = new JComboBox<String>(schwierigkeiten);
			}

			for (int i = 0; i < anzahl; i++) {
				name[i] = new JTextField("Spieler " + (i + 1));
				name[0].setText(Loader.getCfgValue("SETTINGS: Profilname"));
				name[i].setBounds((screen.width - width) / 2 + 100,
						(screen.height - height) / 2 + 260 + (i * 60), 100, 50);
				name[i].setBorder(null);
				name[i].setBackground(new Color(0, 0.25f, 0.5f, 1f));
				name[i].setForeground(Color.white);
				name[i].setHorizontalAlignment(JLabel.CENTER);
				spielerArt[i].setBounds((screen.width - width) / 2 + 250,
						(screen.height - height) / 2 + 260 + (i * 60), 100, 50);
				spielerArt[i].setBorder(null);
				spielerArt[i].setBackground(new Color(0, 0.25f, 0.5f, 1f));
				spielerArt[i].setForeground(Color.white);
				bild[i] = new JButton();
				bild[i].setBounds((screen.width - width) / 2 + 700,
						(screen.height - height) / 2 + 260 + (i * 60), 100, 50);

				if (i != 0) {
					bild[i].setBackground(c[i]);
				} else {
					ic.setImage(ic.getImage().getScaledInstance(
							bild[i].getWidth(), bild[i].getHeight(),
							Image.SCALE_SMOOTH));
					bild[i].setIcon(ic);
					System.out.println(Integer.parseInt(Loader
							.getCfgValue("SETTINGS: Default difficulty")));
					schwierigkeit[0].setSelectedIndex(Integer.parseInt(Loader
							.getCfgValue("SETTINGS: Default difficulty")));
				}
				
				bild[i].addActionListener(this);

				if (i == 0)
					spielerArt[i].setSelectedIndex(1);

				team[i].setBounds((screen.width - width) / 2 + 400,
						(screen.height - height) / 2 + 260 + (i * 60), 100, 50);
				team[i].setBorder(null);
				team[i].setBackground(new Color(0, 0.25f, 0.5f, 1f));
				team[i].setForeground(Color.white);
				team[i].setSelectedIndex(i);
				schwierigkeit[i].setBounds((screen.width - width) / 2 + 550,
						(screen.height - height) / 2 + 260 + (i * 60), 100, 50);
				schwierigkeit[i].setBorder(null);
				schwierigkeit[i].setBackground(new Color(0, 0.25f, 0.5f, 1f));
				schwierigkeit[i].setForeground(Color.white);
				add(name[i]);
				add(spielerArt[i]);
				add(team[i]);
				add(schwierigkeit[i]);
				add(bild[i]);
				validate();
				repaint();
			}
		}

	}

	public void remove() {
		for (int i = 0; i < anzahl; i++) {
			this.remove(name[i]);
			this.remove(spielerArt[i]);
			this.remove(team[i]);
			this.remove(schwierigkeit[i]);
			this.remove(bild[i]);
		}
	}

	public void refresh() {
		for (int i = 0; i < setNames.length; i++) {
			sett[i].setText(setNames[i] + ": " + settings[i].getValue() + "%");
		}
		sett[setNames.length - 1].setText(setNames[setNames.length - 1] + ": "
				+ settings[setNames.length - 1].getValue() + ":00 Uhr");
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		String time = settings[setNames.length - 1].getValue() + "";
		if (time.length() == 1) {
			time = "0." + settings[setNames.length - 1].getValue();
		} else {
			time = time.substring(0, 1) + "." + time.substring(1, 2);
		}
		Loader.changeCfgValue("SETTINGS: Startzeit", time);
		System.out.println(Loader.getCfgValue("SETTINGS: Startzeit") + ";");
		refresh();
	}
	
	public void refreshBild(){
		for (int i = 0; i < bild.length; i++){
			if (i != 0) {
				bild[i].setBackground(c[i]);
			} else {
				ic.setImage(ic.getImage().getScaledInstance(
						bild[i].getWidth(), bild[i].getHeight(),
						Image.SCALE_SMOOTH));
				bild[i].setIcon(ic);
				System.out.println(Integer.parseInt(Loader
						.getCfgValue("SETTINGS: Default difficulty")));
				schwierigkeit[i].setSelectedIndex(Integer.parseInt(Loader
						.getCfgValue("SETTINGS: Default difficulty")));
			}
		}
	}

}
