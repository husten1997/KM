package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import com.richard.knightmare.util.Optionsframesuperklasse;

public class Grafik extends Optionsframesuperklasse implements ActionListener {

	private JButton zur�ck;

	protected Grafik(String imgName, String name) {
		super(imgName, name);
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

	}

}
