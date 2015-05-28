package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Button;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Pos;

@SuppressWarnings("serial")
public class MainMenueJFrame extends JFrame {

	private ArrayList<Button> buttons = new ArrayList<>();
	
	private MainMenueJFrame() {
		Loader.initLoaderWithoutLoad("Ares", "Knightmare");
		MoodMusic.init();
		double resolution = (double) 16/ (double) 9;
		int width, height;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		if(screen.getWidth()/screen.getHeight()==resolution){
			width = screen.width;
			height = screen.height;
		}else if(screen.getWidth()/screen.getHeight()>resolution){
			width = (int) (screen.getHeight()*resolution);
			height = screen.height;
		}else{
			width = screen.width;
			height = (int) (screen.getWidth()/resolution);
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		// Set your Image Here.
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage("menue.png"), 0, 0, width,
				height, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(Loader.getImage("Ritter.png"));
		setUndecorated(true);
		setTitle("Knightmare");
		setSize(screen);
		setVisible(true);
		
		buttons.add(new Button(new Pos(0, 0), new Pos(0.5*width, 0.25*height)) {
			
			@Override
			public void onClick() {
				dispose();
			}
		});
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// Ignore
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// Ignore
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// Ignore
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// Ignore
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				click(new Pos(e.getX()-(screen.getWidth()-width)/2, e.getY()-(screen.getHeight()-height)/2));
			}
		});
	}

	public static void main(String[] args) {
		new MainMenueJFrame();
	}
	
	private void click(Pos point){
		for(int i = 0; i <buttons.size(); i++){
			if(isOn(buttons.get(i).getP1(), buttons.get(i).getP2(), point)){
				buttons.get(i).onClick();
				return;
			}
		}
	}
	
	private boolean isBetween(double p1, double p2, double sp){
		if(sp>=p1 && sp<=p2){
			return true;
		}else if (sp>=p2 && sp<=p1){
			return true;
		}
		return false;
	}
	
	private boolean isOn(Pos p1, Pos p2, Pos p){
		return isBetween(p1.getX(), p2.getX(), p.getX())&&isBetween(p1.getY(), p2.getY(), p.getY());
	}
}
