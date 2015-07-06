package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.husten.knightmare.core.MainMenue;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Profil extends Optionsframesuperklasse implements ActionListener {

	private JTextField name[];
	private JButton zurück;

	protected Profil(boolean inGame, String imgName, String namen) {
		super(imgName, namen);
		this.inGame = inGame;
		name = new JTextField[2];
		name[0] = new JTextField("Gib hier deinen Namen ein:");
		name[1] = new JTextField(Loader.getCfgValue("SETTINGS: Profilname"));
		name[0].setBounds((screen.width - width) / 2 + 100, 100, 200, 50);
		name[0].setEditable(false);
		name[0].setBorder(null);
		name[0].setBackground(new Color(0, 0.25f, 0.5f, 1f));
		name[0].setForeground(Color.white);
		name[0].setHorizontalAlignment(JLabel.CENTER);
		name[1].setBounds((screen.width - width) / 2 + 320, (screen.height - height) / 2 + 100, 100, 50);
		name[1].setBorder(null);
		name[1].setBackground(new Color(0, 0.25f, 0.5f, 1f));
		name[1].setForeground(Color.white);
		name[1].setHorizontalAlignment(JLabel.CENTER);
		name[0].addKeyListener(this);
		name[1].addKeyListener(this);
		add(name[0]);
		add(name[1]);

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
	public void actionPerformed(ActionEvent e) {
		Loader.changeCfgValue("SETTINGS: Profilname", name[1].getText());
		if (e.getSource() == zurück) {
			if (inGame) {
				dispose();
			} else {
				MainMenue.instance.setUndecorated(isUndecorated());
				MainMenue.instance.setVisible(true);
				MainMenue.instance.setAutoRequestFocus(true);
				dispose();
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Loader.changeCfgValue("SETTINGS: Profilname", name[1].getText());
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Bestätigen"))) {
			name[1].setFocusable(false);
			name[0].setRequestFocusEnabled(true);
			name[1].setFocusable(true);
		}
		if(!name[1].isFocusOwner()){
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
				if (inGame) {
					InGameOptionen.instance.setUndecorated(isUndecorated());
					InGameOptionen.instance.setVisible(true);
					InGameOptionen.instance.setAutoRequestFocus(true);
					dispose();
				} else {
					Optionen.instance.setUndecorated(isUndecorated());
					Optionen.instance.setVisible(true);
					Optionen.instance.setAutoRequestFocus(true);
					dispose();
				}
			} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: V-Sync"))) {
				Loader.changeCfgValue("CONTROL_KEY: V-Sync", Loader.getCfgValue("CONTROL_KEY: V-Sync").equals("On") ? "Off" : "On");
			}
		}
	}

}
