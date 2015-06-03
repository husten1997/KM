package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements KeyListener {

	private MainMenuePanel mainMenue;
	private OptinenPanel optionen;
	private JPanel loadscreen, laden, resourcepack, tastenbelegung, grafik;
	public static MainFrame instance;
	private int currentlyActive, width, height;

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		Loader.initLoader("Ares", "Knightmare");
		MoodMusic.addMood("MainMenue");
		MoodMusic.addClipToMood("MainMenue", "Knightmare_Soundtrack_4.WAV");
		MoodMusic.init("MainMenue");
		new MainFrame();
	}

	private MainFrame() {
		super("Knightmare: Menue");
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Loader.getImage("Cursor.png"), new Point(), "Knightmare_Cursor"));
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		double resolution = (double) 16 / (double) 9;
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
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage("menue.png"), 0, 0, width, height, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		
		mainMenue = new MainMenuePanel(width, height);
		mainMenue.setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
		optionen = new OptinenPanel(width, height);
		optionen.setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
		
		add(mainMenue);
		currentlyActive = 0;
		mainMenue.addMouseListener(mainMenue);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setIconImage(Loader.getImage("Icon.png"));
		setUndecorated(Loader.getCfgValue("SETTINGS: Fenstermodus").equals("false"));
		setSize(screen);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		setVisible(true);
		instance = this;
		addKeyListener(this);
	}

	public void changeTo(int id) {
		switch (id) {
		case 0:
			dispose();
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			img.getGraphics().drawImage(Loader.getImage("menue.png"), 0, 0, width, height, null);
			setContentPane(new JLabel(new ImageIcon(img)));
			add(mainMenue);
			setVisible(true);
			repaint();
			currentlyActive = 0;
			//MainMenue
			break;
		case 1:
			//Start
			break;
		case 2:
			System.out.println("d");
			dispose();
			BufferedImage imgB = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			imgB.getGraphics().drawImage(Loader.getImage("back.png"), 0, 0, width, height, null);
			setContentPane(new JLabel(new ImageIcon(imgB)));
			remove(mainMenue);
			add(optionen);
			setVisible(true);
			repaint();
			currentlyActive = 2;
			//Optinen
			break;
		case 3:
			//Laden
			break;
		case 4:
			dispose();
			break;
		case 5:
			//Tastenbelegung
			break;
		case 6:
			//resourcepack
			break;
		case 7:
			//Grafik
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Ignore
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (currentlyActive) {
		case 0:
			mainMenue.keyPressed(e);
			break;
		case 1:

			break;
		case 2:
			optionen.keyPressed(e);
			break;
		case 3:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
		case 8:

			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Ignore
	}

}
