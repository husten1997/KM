package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Button;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Pos;

@SuppressWarnings("serial")
public class MainMenue extends JFrame implements KeyListener {

	private ArrayList<Button> buttons = new ArrayList<>();
	private MainMenue mm;

	public MainMenue(String Imagename) {
		MoodMusic.changeMood("MainMenue");
		double resolution = (double) 16 / (double) 9;
		int width, height;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		if (screen.getWidth() / screen.getHeight() == resolution) {
			width = screen.width;
			height = screen.height;
		} else if (screen.getWidth() / screen.getHeight() > resolution) {
			width = (int) (screen.getHeight() * resolution);
			height = screen.height;
		} else {
			width = screen.width;
			height = (int) (screen.getWidth() / resolution);
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		// Set your Image Here.
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage(Imagename), 0, 0, width,
				height, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(Loader.getImage("Ritter.png"));
		setUndecorated(Loader.getCfgValue("Fullscreen").equals("true"));
		setTitle("Knightmare");
		setSize(screen);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		setVisible(true);

		addKeyListener(this);

		mm = this;

		// Spiel Starten
		buttons.add(new Button(new Pos(w(Loader
				.getCfgValue("Button: Spielstarten (posx1)")) * width, h(Loader
				.getCfgValue("Button: Spielstarten (posy1)")) * height),
				new Pos(w(Loader.getCfgValue("Button: Spielstarten (posx2)"))
						* width, h(Loader
						.getCfgValue("Button: Spielstarten (posy2)")) * height)) {
			@Override
			public void onClick() {
				dispose();
				new Timer(false).schedule(new TimerTask() {

					@Override
					public void run() {
						MainMenue m = new MainMenue("loadscreen.png");
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Knightmare km = new Knightmare();
						MoodMusic.changeMood("Default");
						m.dispose();
						km.loop();
					}
				}, 0);
			}
		});

		// Optionen
		buttons.add(new Button(new Pos(w(Loader
				.getCfgValue("Button: Optionen (posx1)")) * width, h(Loader
				.getCfgValue("Button: Optionen (posy1)")) * height), new Pos(
				w(Loader.getCfgValue("Button: Optionen (posx2)")) * width,
				h(Loader.getCfgValue("Button: Optionen (posy2)")) * height)) {
			@Override
			public void onClick() {
				new Optionen(mm).setAlwaysOnTop(true);
				;
				setVisible(false);
			}
		});

		// Laden
		buttons.add(new Button(new Pos(w(Loader
				.getCfgValue("Button: Laden (posx1)")) * width, h(Loader
				.getCfgValue("Button: Laden (posy1)")) * height), new Pos(
				w(Loader.getCfgValue("Button: Laden (posx2)")) * width,
				h(Loader.getCfgValue("Button: Laden (posy2)")) * height)) {
			@Override
			public void onClick() {
				new Laden(mm).setAlwaysOnTop(true);
				;
				setVisible(false);
			}
		});

		// Schliessen
		buttons.add(new Button(new Pos(w(Loader
				.getCfgValue("Button: Schliessen (posx1)")) * width, h(Loader
				.getCfgValue("Button: Schliessen (posy1)")) * height), new Pos(
				w(Loader.getCfgValue("Button: Schliessen (posx2)")) * width,
				h(Loader.getCfgValue("Button: Schliessen (posy2)")) * height)) {
			@Override
			public void onClick() {
				dispose();
			}
		});

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// Ignore
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// Ignore
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Volume -"))) {
					MoodMusic.changeVolume(-0.5f);
				} else if (e.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Volume +"))) {
					MoodMusic.changeVolume(+0.5f);
				} else if (e.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Escape/Zurück"))) {
					dispose();
				}
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
				if (Imagename.equals("menue.png")) {
					click(new Pos(e.getX() - (screen.getWidth() - width) / 2,
							e.getY() - (screen.getHeight() - height) / 2));
				}
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

	public static void main(String[] args) {
		Loader.initLoaderWithoutLoad("Ares", "Knightmare");
		MoodMusic.addMood("MainMenue");
		// TODO SPielt nur 1x ab
		MoodMusic.addClipToMood("MainMenue", "Knightmare_Soundtrack_4.WAV");
		MoodMusic.init("MainMenue");
		new MainMenue("menue.png");
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
		return isBetween(p1.getX(), p2.getX(), p.getX())
				&& isBetween(p1.getY(), p2.getY(), p.getY());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
			mm.dispose();
			mm.setUndecorated(!isUndecorated());
			mm.setVisible(true);
			mm.setAutoRequestFocus(true);
			mm.setLocationRelativeTo(null);
			Loader.changeCfgValue("Fullscreen", String.valueOf(isUndecorated()));
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
