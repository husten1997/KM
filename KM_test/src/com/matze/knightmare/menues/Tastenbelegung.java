package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Dictionary;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Tastenbelegung extends Optionsframesuperklasse implements ActionListener {

	private int ButtonClicked = -1;
	private JButton zurück;
	private JButton tasten[];
	private String text[] = { "Vorwärts", "Rückwärts", "Links", "Rechts", "Kamera oben", "Kamera unten", "Kamera links", "Kamera rechts", "Escape/Zurück", "Bestätigen",
			"Fenster- u. Vollbildmodus", "Volume +", "Volume -" };

	public Tastenbelegung() {
		super("back.png", "Knightmare: Tastenbelegung");
		tasten = new JButton[text.length];
		zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);

		for (int i = 0; i < tasten.length; i++) {
			tasten[i] = new JButton(text[i] + ": " + Loader.getCfgValue("CONTROL_KEY: " + text[i]));
			tasten[i].addActionListener(this);
			tasten[i].setBounds((screen.width - width) / 2 + width / 4, (screen.height - height) / 2 + i * height / (tasten.length), width / 2, height / (tasten.length));
			tasten[i].setForeground(Color.WHITE);
			tasten[i].setBackground(new Color(0, 0, 0.25f, 0.25f));
			tasten[i].setRolloverEnabled(false);
			tasten[i].setFocusable(false);
			tasten[i].setContentAreaFilled(true);
			tasten[i].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
			tasten[i].setFont(new Font("Arial", Font.BOLD, 48));
			add(tasten[i]);
		}
		add(zurück);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (ButtonClicked > -1) {
			Loader.changeCfgValue("CONTROL_KEY: " + text[ButtonClicked], KeyEvent.getKeyText(e.getExtendedKeyCode()));
			tasten[ButtonClicked].setText(text[ButtonClicked] + ": " + Loader.getCfgValue("CONTROL_KEY: " + text[ButtonClicked]));
			repaint();
			ButtonClicked = -1;
		}else{
			if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
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
				Optionen.instance.setVisible(true);
				Optionen.instance.setAutoRequestFocus(true);
				dispose();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object q = e.getSource();

		for (int i = 0; i < text.length; i++) {
			if (q == tasten[i]) {
				tasten[i].setText("Drücke die Taste die du zuweisen willst");
				repaint();
				ButtonClicked = i;
			}
		}

		if (q == zurück) {
			Optionen.instance.setVisible(true);
			Optionen.instance.setAutoRequestFocus(true);
			dispose();
		}

	}
}
