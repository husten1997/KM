package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JButton optionen[];
	private String[] text = { "Fenstermodus", "Grafikeinstellungen", "Tastenbelegung", "Resourcepacks", "Zurück" };
	public static Optionsframesuperklasse instance;

	public Optionen() {
		super("back.png", "Knightmare: Optionen");
		optionen = new JButton[text.length];

		position = (int) ((MoodMusic.getVolume() / 0.84) + 94);

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
		add(optionen[optionen.length - 1]);
		instance = this;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object q = e.getSource();

		if (q == volume) {
			repaint();
			MoodMusic.setVolume((float) (int) ((6 - ((100 - volume.getValue()) * 0.84))));
			Loader.changeCfgValue("Volume", String.valueOf(((6 - ((100 - ((double) volume.getValue())) * 0.84)))));
			position = volume.getValue();
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object q = arg0.getSource();
		repaint();

		if (q == optionen[optionen.length - 1]) {
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
		
		if (q == optionen[0]){
			dispose();
			setUndecorated(!isUndecorated());
			setVisible(true);
			setAutoRequestFocus(true);
			Loader.changeCfgValue("Fullscreen", String.valueOf(isUndecorated()));
			setVisible(true);
		}
		
		if (q == optionen[2]){
			setVisible(false);
			new Tastenbelegung();
		}

		if (q == optionen[3]) {
			setVisible(false);
			new Resourcepack();
		}

	}

}
