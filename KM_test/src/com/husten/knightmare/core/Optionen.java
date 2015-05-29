package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.richard.knightmare.sound.MoodMusic;

public class Optionen extends JFrame implements ChangeListener, ActionListener{

	private JSlider volume;
	private MainMenueJFrame mm;
	private JButton optionen[];
	private String[] text = {"a", "b", "c", "Zurück"};
	
	public Optionen(int w, int h, boolean u, MainMenueJFrame a){
		setUndecorated(u);
		setSize(w, h);
		setVisible(true);
		setLayout(new GridLayout(5,1));
		
		optionen = new JButton[4];
		
		volume = new JSlider();
		volume.setMinimum(-70);
		volume.setMaximum(20);
		volume.setMajorTickSpacing(5);
		volume.setMinorTickSpacing(1);
		volume.setValue(0);
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);
		volume.addChangeListener(this);
		add(volume);
		
		for (int i = 0; i < optionen.length; i++){
			optionen[i] = new JButton(text[i]);
			optionen[i].addActionListener(this);
			add(optionen[i]);
		}
		mm = a;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object q = e.getSource();
		
		if (q == volume){
			System.out.println(volume.getValue());
			MoodMusic.setVolume((float)volume.getValue());
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object q = arg0.getSource();
		
		if (q == optionen[3]){
			mm.setVisible(true);
			dispose();
		}
		
	}
	
}
