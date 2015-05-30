package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import com.richard.knightmare.util.Loader;

//M
@SuppressWarnings("serial")
public class Laden extends JFrame implements KeyListener {

	private JList<String> list;
	private String[] data = { "getSpeicherStand1", "getSpeicherstand2",
			"getSpeicherstandX" };
	private String zurück = "Zurück";
	private MainMenueJFrame mm;

	public Laden(boolean undecorated, MainMenueJFrame a) {
		Loader.initLoaderWithoutLoad("Ares", "Knightmare");
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
		// Set your Image Here.
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage("back.png"), 0, 0, width,
				height, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(Loader.getImage("Ritter.png"));
		setTitle("Knightmare");

		setUndecorated(undecorated);
		setSize(screen);
		setVisible(true);

		String[] b = new String[data.length + 1];

		for (int i = 0; i < data.length; i++) {
			b[i] = data[i];
		}

		setLocationRelativeTo(null);

		b[data.length] = zurück;

		setLayout(new FlowLayout());

		list = new JList<String>(b); // data has type Object[]
		list.setSize(new Dimension(width, height));
		list.setBounds((int) list.getBounds().getX(),
				(screen.height - height) / 2, width, height);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(0, 0, 0, 1));
		list.setForeground(Color.white);
		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.cyan);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(data.length + 1);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.setOpaque(false);

		add(list);
		mm = a;

		list.addKeyListener(this);

	}

	private void performAction(int x) {
		if (x == data.length){
			mm.setVisible(true);
			dispose();
		} else {
			//load(x);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getExtendedKeyCode() == 10) {
			performAction(list.getSelectedIndex());
		} else if (arg0.getExtendedKeyCode() == 27) {
			mm.setVisible(true);
			dispose();
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

}
