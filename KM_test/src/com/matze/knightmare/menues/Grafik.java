package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

public class Grafik extends Optionsframesuperklasse implements ActionListener {

	private JButton zur�ck;
	private JButton settings[];
	private String[] text = {"V-Sync"};

	protected Grafik(String imgName, String name) {
		super(imgName, name);

		settings = new JButton[text.length];

		for (int i = 0; i < text.length; i++) {
			settings[i] = new JButton(text[0] + ": "
					+ Loader.getCfgValue("SETTINGS: " + text[0]));
			settings[i].addActionListener(this);
			settings[i].setBounds((screen.width - width) / 2 + width / 4,
					(screen.height - height) / 2
							+ (i < settings.length - 1 ? i : i + 1) * height
							/ (settings.length + 1), width / 2, height
							/ (settings.length + 1));
			settings[i].setForeground(Color.WHITE);
			settings[i].setBackground(new Color(0, 0, 0.25f, 0.25f));
			settings[i].setRolloverEnabled(false);
			settings[i].setFocusable(false);
			settings[i].setContentAreaFilled(true);
			settings[i].setBorder(BorderFactory.createLineBorder(
					Color.lightGray, 1));
			settings[i].setFont(new Font("Arial", Font.BOLD, 48));
			add(settings[i]);
		}

		zur�ck = new JButton("Zur�ck");
		zur�ck.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zur�ck.setFont(new Font("Arial", Font.BOLD, width / 48));
		zur�ck.setBounds(screen.width / 2 + 3 * width / 8,
				(screen.height - height) / 2 + height - width / 24, width / 8,
				width / 24);
		zur�ck.addActionListener(this);
		zur�ck.setRolloverEnabled(false);
		zur�ck.setFocusable(false);
		add(zur�ck);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(
				getString("CONTROL_KEY: Escape/Zur�ck"))) {
			Optionen.instance.setVisible(true);
			Optionen.instance.setAutoRequestFocus(true);
			dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object q = e.getSource();

		if (q == zur�ck) {
			Optionen.instance.setVisible(true);
			Optionen.instance.setAutoRequestFocus(true);
			dispose();
		}

		if (q == settings[0]) {
			Loader.changeCfgValue(
					"SETTINGS: " + text[0],
					Loader.getCfgValue("SETTINGS: " + text[0]).equals("On") ? "Off"
							: "On");
			settings[0].setText(text[0] + " = " + Loader.getCfgValue("SETTINGS: " + text[0]));
			repaint();
		}

	}

}
