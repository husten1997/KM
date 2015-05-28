package com.husten.knightmare.core;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;

public class MainMenueJFrame extends JFrame implements ActionListener{

	public MainMenueJFrame(){
		//Musik
	}
	
	
	public static void main(String[] args) {
		JFrame a = new JFrame();
		
		BufferedImage image;
		
		try {
			image = ImageIO.read(new File("C:\\Users\\Matthias Kettl\\Pictures\\Strategie\\Menue.jpg"));
			// Set your Image Here.
			a.setContentPane(new JLabel(new ImageIcon(image)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		a.setUndecorated(true);
		a.setTitle("Knightmare");
		a.setVisible(true);
		a.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
