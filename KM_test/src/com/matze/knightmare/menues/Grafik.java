package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import com.richard.knightmare.util.Optionsframesuperklasse;

public class Grafik extends Optionsframesuperklasse implements ActionListener {

	private JButton zurück;

	protected Grafik(String imgName, String name) {
		super(imgName, name);
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
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(
				getString("CONTROL_KEY: Escape/Zurück"))) {
			Optionen.instance.setVisible(true);
			Optionen.instance.setAutoRequestFocus(true);
			dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object q = e.getSource();

		if (q == zurück) {
			Optionen.instance.setVisible(true);
			Optionen.instance.setAutoRequestFocus(true);
			dispose();
		}

	}

}
