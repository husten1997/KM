package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.richard.knightmare.util.Loader;

public class Tastenbelegung extends JFrame implements ActionListener,
		KeyListener, MouseListener {

	private int ButtonClicked = -1;
	private JButton zurück;
	private Optionen op;
	private JButton tasten[];
	private String text[] = { "Vorwärts", "Rückwärts", "Links", "Rechts",
			"Kamera oben", "Kamera unten", "Kamera links", "Kamera rechts",
			"Escape/Zurück", "Bestätigen", "Fenster- u. Vollbildmodus", "Volume +", "Volume -" };

	public Tastenbelegung(Optionen o) {
		op = o;
		
		double resolution = (double) 16 / (double) 9;
		int width, height;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		if (screen.getWidth() / screen.getHeight() == resolution) {
			width = screen.width;
			height = screen.height;
		} else if (screen.getWidth() / screen.getHeight() > resolution) {
			width = (int) (screen.getHeight() * resolution);
			height = screen.height;
		} else {
			width = screen.width;
			height = (int) (screen.getWidth() / resolution);
		}
		setBackground(Color.BLACK);

		tasten = new JButton[text.length];

		// Set your Image Here.
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage("back.png"), 0, 0, width,
				height, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(Loader.getImage("Ritter.png"));
		setTitle("Knightmare");

		setUndecorated(Loader.getCfgValue("Fullscreen").equals("true"));
		setSize(screen);

		zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8,
				(screen.height - height) / 2 + height - width / 24, width / 8,
				width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);
		
		
		for (int i = 0; i < tasten.length; i++) {
			tasten[i] = new JButton(text[i] + ": "
					+ Loader.getCfgValue("CONTROL_KEY: " + text[i]));
			tasten[i].addActionListener(this);
			tasten[i].setBounds((screen.width - width) / 2 + width / 4,
					(screen.height - height) / 2
							+ i* height
							/ (tasten.length), width / 2, height
							/ (tasten.length));
			tasten[i].setForeground(Color.WHITE);
			tasten[i].setBackground(new Color(0, 0, 0.25f, 0.25f));
			tasten[i].setRolloverEnabled(false);
			tasten[i].setFocusable(false);
			tasten[i].setContentAreaFilled(true);
			tasten[i].setBorder(BorderFactory.createLineBorder(Color.lightGray,
					1));
			tasten[i].setFont(new Font("Arial", Font.BOLD, 48));
			add(tasten[i]);
		}
		add(zurück);
		setVisible(true);
		setAlwaysOnTop(true);
		addKeyListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (ButtonClicked > -1){
			Loader.changeCfgValue("CONTROL_KEY: " + text[ButtonClicked], KeyEvent.getKeyText(e.getExtendedKeyCode()));
			tasten[ButtonClicked].setText(text[ButtonClicked] + ": " + Loader.getCfgValue("CONTROL_KEY: " + text[ButtonClicked]));
			ButtonClicked = -1;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object q = e.getSource();
		
		for (int i = 0; i < text.length; i++){
			if (q == tasten[i]){
				tasten[i].setText("Drücke die Taste die du zuweisen willst");
				ButtonClicked = i;
			}
		}
		
		if (q == zurück){
			op.setVisible(true);
			op.setAutoRequestFocus(true);
			dispose();	
		}
		
	}
}
