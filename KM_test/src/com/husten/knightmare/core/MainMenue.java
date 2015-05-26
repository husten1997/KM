package com.husten.knightmare.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenue {

	public static void main(String[] args) {
		JFrame f = new JFrame("Ähh");
		JButton start = new JButton("Start");
		f.add(start);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MainMenue().spielStarten();
			}
		});
	}
	
	private void spielStarten(){
		new Knightmare();
	}

}
