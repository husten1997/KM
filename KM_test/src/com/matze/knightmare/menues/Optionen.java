package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.husten.knightmare.core.MainMenue;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Optionen extends Optionsframesuperklasse implements ChangeListener, ActionListener{
	
	private JSlider volume;
	private int position;
	private JButton zurück;
	private JButton optionen[];
	private String[] text = { "Grafikeinstellungen", "Resourcepacks", "Tastenbelegung"};
	public static Optionsframesuperklasse instance;

	public Optionen() {
		super("back.png", "Knightmare: Optionen");
		optionen = new JButton[text.length];

		position = (int) ((Float.parseFloat(Loader.getCfgValue("Volume")) / 0.84) + 94);

		volume = new JSlider();
		volume.setMinimum(0);
		volume.setMaximum(100);
		volume.setMajorTickSpacing(5);
		volume.setMinorTickSpacing(1);
		volume.setValue(position);
		volume.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);
		volume.addChangeListener(this);
		volume.setFocusable(false);
		volume.setBounds((screen.width - width) / 2 + width / 4, (screen.height - height) / 2 + (optionen.length - 1) * height / (optionen.length + 1), width / 2,
				height / (optionen.length + 1));
		volume.setBackground(new Color(0, 0, 0.25f, 0.25f));
		volume.setForeground(Color.white);

		for (int i = 0; i < optionen.length; i++) {
			optionen[i] = new JButton(text[i]);
			optionen[i].addActionListener(this);
			optionen[i].setBounds((screen.width - width) / 2 + width / 4,
					(screen.height - height) / 2 + (i < optionen.length - 1 ? i : i + 1) * height / (optionen.length + 1), width / 2, height / (optionen.length + 1));
			optionen[i].setForeground(Color.WHITE);
			optionen[i].setBackground(new Color(0, 0, 0.25f, 0.25f));
			optionen[i].setRolloverEnabled(false);
			optionen[i].setFocusable(false);
			optionen[i].setContentAreaFilled(true);
			optionen[i].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
			optionen[i].setFont(new Font("Arial", Font.BOLD, 48));
			if (i > 0)
				add(optionen[i - 1]);
		}

		add(volume);
		add(optionen[optionen.length-1]);
		zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);
		add(zurück);
		instance = this;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object q = e.getSource();

		if (q == volume) {
			repaint();
			MoodMusic.setVolume((float) ((6 - ((100 - volume.getValue()) * 0.84))));
			Loader.changeCfgValue("Volume", String.valueOf(((6 - ((100 - ((double) volume.getValue())) * 0.84)))));
			position = volume.getValue();
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
			position = (int) Math.round((Double.parseDouble(Loader.getCfgValue("Volume")) / 0.84) + 94)-2;
			volume.setValue(position);
			repaint();
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume +"))) {
			MoodMusic.changeVolume(+0.5f);
			position = (int) Math.round((Double.parseDouble(Loader.getCfgValue("Volume")) / 0.84) + 94)-1;
			volume.setValue(position);
			repaint();
		} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Escape/Zurück"))) {
			MainMenue.instance.setUndecorated(isUndecorated());
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object q = arg0.getSource();
		repaint();

		if (q == zurück) {
			MainMenue.instance.setUndecorated(isUndecorated());
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
		
		if (q == optionen[2]){
			new Tastenbelegung();
			dispose();
		}

		if (q == optionen[1]) {
			new Resourcepack();
			dispose();
		}
		
		if (q == optionen[0]){
			new Grafik("back.png", "Knightmare: Grafikeinstellungen");
		}

	}

}
