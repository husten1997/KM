package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.husten.knightmare.constants.StringConstants;
import com.matze.knightmare.menues.Credits;
import com.matze.knightmare.menues.Laden;
import com.matze.knightmare.menues.Loadscreen;
import com.matze.knightmare.menues.Optionen;
import com.matze.knightmare.meshes.Spieler;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Button;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;
import com.richard.knightmare.util.Pos;

@SuppressWarnings("serial")
public class MainMenue extends Optionsframesuperklasse {

	private ArrayList<Button> buttons = new ArrayList<>();
	public static Optionsframesuperklasse instance;
	private Spieler[] spieler;
	private static Logger LOG = LogManager.getLogger(MainMenue.class);

	public static void main(String[] args) {
		LOG.info("TEST MSG");
		Locale.setDefault(Locale.ENGLISH);
		Loader.initLoader("Ares", "Knightmare");
		MoodMusic.addMood("MainMenue");
		MoodMusic.addClipToMood("MainMenue", "Knightmare_Soundtrack_4.WAV");
		MoodMusic.init("MainMenue");
		new MainMenue();
	}

	public MainMenue() {
		super("menue.png", "Knightmare: MainMenue");
		// Spiel Starten
		spieler = new Spieler[2];
		spieler[0] =  new Spieler(0, "Spieler 1", 1);
		spieler[1] =  new Spieler(0, "Spieler 2", 2);
		
		buttons.add(new Button(new Pos(w(Loader.getCfgValue("Button: Spielstarten (posx1)")) * width, h(Loader.getCfgValue("Button: Spielstarten (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Spielstarten (posx2)")) * width, h(Loader.getCfgValue("Button: Spielstarten (posy2)")) * height)) {
			
			@Override
			public void onClick() {
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
		});

		// Optionen
		buttons.add(new Button(new Pos(w(Loader.getCfgValue("Button: Optionen (posx1)")) * width, h(Loader.getCfgValue("Button: Optionen (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Optionen (posx2)")) * width, h(Loader.getCfgValue("Button: Optionen (posy2)")) * height)) {
			@Override
			public void onClick() {
				new Optionen(false).setAlwaysOnTop(true);
				dispose();
			}
		});

		// Laden
		buttons.add(new Button(new Pos(w(Loader.getCfgValue("Button: Laden (posx1)")) * width, h(Loader.getCfgValue("Button: Laden (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Laden (posx2)")) * width, h(Loader.getCfgValue("Button: Laden (posy2)")) * height)) {
			@Override
			public void onClick() {
				new Laden().setAlwaysOnTop(true);
				dispose();
			}
		});

		// Schliessen
		buttons.add(new Button(new Pos(w(Loader.getCfgValue("Button: Schliessen (posx1)")) * width, h(Loader.getCfgValue("Button: Schliessen (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Schliessen (posx2)")) * width, h(Loader.getCfgValue("Button: Schliessen (posy2)")) * height)) {
			@Override
			public void onClick() {
				dispose();
			}
		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Ignore
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
				click(new Pos(e.getX() - (screen.getWidth() - width) / 2, e.getY() - (screen.getHeight() - height) / 2));
			}
		});
		JButton version = new JButton(StringConstants.VERSION);
		version.setBounds((screen.width - width) / 2,
		(screen.height - height) / 2 + height - width / 24,
		width / 8, width / 24);
		version.setHorizontalAlignment(SwingConstants.LEFT);
		version.setVerticalAlignment(SwingConstants.BOTTOM);
		version.setFont(new Font("Arial", Font.BOLD, width / 100));
		version.setFocusable(false);
		version.setForeground(Color.WHITE);
		version.setContentAreaFilled(false);
		version.setBorder(null);
		version.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Credits().setAlwaysOnTop(true);
				dispose();
			}
		});
		add(version);
		
		
		instance = this;
	}
	
	private double w(String s) {
		double x = Double.parseDouble(s);
		return (double) x / (double) 1920;
	}

	private double h(String s) {
		double x = Double.parseDouble(s);
		return (double) x / (double) 1080;
	}
	
	private void click(Pos point) {
		for (int i = 0; i < buttons.size(); i++) {
			if (isOn(buttons.get(i).getP1(), buttons.get(i).getP2(), point)) {
				buttons.get(i).onClick();
				return;
			}
		}
	}

	private boolean isBetween(double p1, double p2, double sp) {
		if (sp >= p1 && sp <= p2) {
			return true;
		} else if (sp >= p2 && sp <= p1) {
			return true;
		}
		return false;
	}

	private boolean isOn(Pos p1, Pos p2, Pos p) {
		return isBetween(p1.getX(), p2.getX(), p.getX()) && isBetween(p1.getY(), p2.getY(), p.getY());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
			dispose();
			setUndecorated(!isUndecorated());
			setVisible(true);
			setAutoRequestFocus(true);
			setLocationRelativeTo(null);
			Loader.changeCfgValue("SETTINGS: Fenstermodus", String.valueOf(!isUndecorated()));
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume -"))) {
			MoodMusic.changeVolume(-0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume +"))) {
			MoodMusic.changeVolume(+0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Escape/Zurück"))) {
			dispose();
		}
	}
}
