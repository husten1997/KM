package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.richard.knightmare.util.Optionsframesuperklasse;

public class Wiki extends Optionsframesuperklasse implements ActionListener{

	private JTextField hilfe = new JTextField();
	private JButton zur�ck;
	
	public Wiki(String s, String b){
		super(s,b);
		hilfe = new JTextField();

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
		
		hilfe.setBounds(100,100,(int)screen.getWidth()-200,(int)screen.getHeight()-200);
		hilfe.setText("");
		hilfe.setEditable(false);
		add(hilfe);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == zur�ck) {
			new Profil(inGame, "back.png", "Knightmare: Profil", false);
			dispose();
		}
	}
}
