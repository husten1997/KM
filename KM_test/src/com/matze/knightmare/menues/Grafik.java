package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.husten.knightmare.core.MainMenue;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Grafik extends Optionsframesuperklasse implements ActionListener {

	private JButton zurück;
	private JButton settings[];
	private String[] text = { "V-Sync", "Fenstermodus"};

	protected Grafik(String imgName, String name) {
		super(imgName, name);

		settings = new JButton[text.length];
		
		for (int i = 0; i < text.length; i++) {
			settings[i] = new JButton(text[i] + ": " + Loader.getCfgValue("SETTINGS: " + text[i]));
			settings[i].addActionListener(this);
			settings[i].setBounds((screen.width - width) / 2 + width / 4, (screen.height - height) / 2 + i * height / settings.length, width / 2,
					height / settings.length);
			settings[i].setForeground(Color.WHITE);
			settings[i].setBackground(new Color(0, 0, 0.25f, 0.25f));
			settings[i].setRolloverEnabled(false);
			settings[i].setFocusable(false);
			settings[i].setContentAreaFilled(true);
			settings[i].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
			settings[i].setFont(new Font("Arial", Font.BOLD, 48));
			add(settings[i]);
		}

		zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);
		add(zurück);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Escape/Zurück"))) {
			Optionen.instance.dispose();
			Optionen.instance.setUndecorated(isUndecorated());
			Optionen.instance.setVisible(true);
			Optionen.instance.setAutoRequestFocus(true);
			dispose();
		}
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: V-Sync"))){
			Loader.changeCfgValue("SETTINGS: V-Sync", Loader.getCfgValue("SETTINGS: V-Sync").equals("On")?"Off":"On");
			settings[0].setText(text[0] + " = " + Loader.getCfgValue("SETTINGS: " + text[0]));
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object q = e.getSource();

		if (q == zurück) {
			Optionen.instance.setUndecorated(isUndecorated());
			Optionen.instance.setVisible(true);
			Optionen.instance.setAutoRequestFocus(true);
			dispose();
		}

		if (q == settings[0]) {
			Loader.changeCfgValue("SETTINGS: " + text[0], Loader.getCfgValue("SETTINGS: " + text[0]).equals("On") ? "Off" : "On");
			settings[0].setText(text[0] + " = " + Loader.getCfgValue("SETTINGS: " + text[0]));
			repaint();
		}
		
		if (q == settings[1]) {
			MainMenue.instance.setUndecorated(!isUndecorated());
			dispose();
			setUndecorated(!isUndecorated());
			setVisible(true);
			setAutoRequestFocus(true);
			Loader.changeCfgValue("SETTINGS: Fenstermodus", String.valueOf(!isUndecorated()));
			settings[1].setText("Fenstermodus: " + (Loader.getCfgValue("SETTINGS: " + text[1])));
			repaint();
			setVisible(true);
		}

	}

}
