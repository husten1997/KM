package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

import com.husten.knightmare.core.Knightmare;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Button;
import com.richard.knightmare.util.Dictionary;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Pos;

@SuppressWarnings("serial")
public class MainMenuePanel extends JPanel implements MouseListener {

	private ArrayList<Button> buttons = new ArrayList<>();

	public MainMenuePanel(int width, int height) {
		setBackground(new Color(0, 0, 0, 1));
		// Spiel Starten
		buttons.add(new Button(new Pos(w(Loader.getCfgValue("Button: Spielstarten (posx1)")) * width, h(Loader.getCfgValue("Button: Spielstarten (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Spielstarten (posx2)")) * width, h(Loader.getCfgValue("Button: Spielstarten (posy2)")) * height)) {
			@Override
			public void onClick() {
				// dispose();
				new Timer(false).schedule(new TimerTask() {

					@Override
					public void run() {
						Loadscreen l = new Loadscreen();
						Knightmare km = new Knightmare();
						MoodMusic.changeMood("Default");
						l.dispose();
						km.loop();
					}
				}, 0);// TODO
			}
		});

		// Optionen
		buttons.add(new Button(new Pos(w(Loader.getCfgValue("Button: Optionen (posx1)")) * width, h(Loader.getCfgValue("Button: Optionen (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Optionen (posx2)")) * width, h(Loader.getCfgValue("Button: Optionen (posy2)")) * height)) {
			@Override
			public void onClick() {
				MainFrame.instance.changeTo(2);
			}
		});

		// Laden
		buttons.add(new Button(new Pos(w(Loader.getCfgValue("Button: Laden (posx1)")) * width, h(Loader.getCfgValue("Button: Laden (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Laden (posx2)")) * width, h(Loader.getCfgValue("Button: Laden (posy2)")) * height)) {
			@Override
			public void onClick() {
				MainFrame.instance.changeTo(3);
			}
		});

		// Schliessen
		buttons.add(new Button(new Pos(w(Loader.getCfgValue("Button: Schliessen (posx1)")) * width, h(Loader.getCfgValue("Button: Schliessen (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Schliessen (posx2)")) * width, h(Loader.getCfgValue("Button: Schliessen (posy2)")) * height)) {
			@Override
			public void onClick() {
				MainFrame.instance.changeTo(4);
			}
		});
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
		click(new Pos(e.getX(), e.getY()));
	}

	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
			MainFrame.instance.dispose();
			MainFrame.instance.setUndecorated(!MainFrame.instance.isUndecorated());
			setVisible(true);
			MainFrame.instance.setAutoRequestFocus(true);
			MainFrame.instance.setLocationRelativeTo(null);
			Loader.changeCfgValue("SETTINGS: Fenstermodus", String.valueOf(!MainFrame.instance.isUndecorated()));
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume -"))) {
			MoodMusic.changeVolume(-0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume +"))) {
			MoodMusic.changeVolume(+0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Escape/Zurück"))) {
			MainFrame.instance.changeTo(3);
		}
	}
	
	public String getString(String a){
		return Dictionary.getFullName(Loader.getCfgValue(a));
	}
}