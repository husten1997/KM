package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.husten.knightmare.core.MainMenue;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

public class Profil extends Optionsframesuperklasse implements ActionListener{

	private JTextField name[];
	private JButton zurück;
	
	protected Profil(boolean inGame, String imgName, String namen) {
		super(imgName, namen);
		this.inGame = inGame;
		name = new JTextField[2];
		name[0] = new JTextField("Gib hier deinen Namen ein:");
		name[1] = new JTextField(Loader.getCfgValue("SETTINGS: Profilname"));
		name[0].setBounds(100, 100, 200, 50);
		name[0].setEditable(false);
		name[1].setBounds(320,100,100,50);
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
		if (e.getSource() == zurück){
			if (inGame){
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
			Loader.changeCfgValue("SETTINGS: Profilname", name[1].getText());
			if (inGame){
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
		}
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: V-Sync"))){
			Loader.changeCfgValue("CONTROL_KEY: V-Sync", Loader.getCfgValue("CONTROL_KEY: V-Sync").equals("On")?"Off":"On");
		}
	}

}
