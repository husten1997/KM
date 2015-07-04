package com.richard.knightmare.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.husten.knightmare.core.MainMenue;
import com.richard.knightmare.sound.MoodMusic;

@SuppressWarnings("serial")
public class Optionsframesuperklasse extends JFrame implements KeyListener {

	protected int width, height;
	protected Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	protected Optionsframesuperklasse(String imgName, String name) {
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Loader.getImage("Cursor.png"), new Point(), "Knightmare_Cursor"));
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		// Set your Image Here.
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage(imgName), 0, 0, width, height, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(Loader.getImage("Icon.png"));
		setUndecorated(Loader.getCfgValue("SETTINGS: Fenstermodus").equals("false"));
		setTitle(name);
		setSize(screen);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		setVisible(true);

		addKeyListener(this);
	}
	
	public String getString(String a){
		return Dictionary.getFullName(Loader.getCfgValue(a));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
			dispose();
			setUndecorated(!isUndecorated());
			setVisible(true);
			setAutoRequestFocus(true);
			setLocationRelativeTo(null);
			Loader.changeCfgValue("Fullscreen", String.valueOf(isUndecorated()));
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume -"))) {
			MoodMusic.changeVolume(-0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume +"))) {
			MoodMusic.changeVolume(+0.5f);
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Escape/Zurück"))) {
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: V-Sync"))){
			Loader.changeCfgValue("CONTROL_KEY: V-Sync", Loader.getCfgValue("CONTROL_KEY: V-Sync").equals("On")?"Off":"On");
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// Ignore
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Ignore
	}
}
