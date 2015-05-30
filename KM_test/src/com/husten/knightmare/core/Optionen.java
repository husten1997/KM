package com.husten.knightmare.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;

//M
@SuppressWarnings("serial")
public class Optionen extends JFrame implements ChangeListener, ActionListener {

	private JSlider volume;
	private MainMenue mm;
	private int position;
	private JButton optionen[];
	private String[] text = { "Fenstermodus", "Grafikeinstellungen", "Texturepacks", "Zur�ck" };

	public Optionen(boolean undecorated, MainMenue a) {
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
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(Loader.getImage("back.png"), 0, 0, width, height, null);
		setContentPane(new JLabel(new ImageIcon(img)));
		setIconImage(Loader.getImage("Ritter.png"));
		setTitle("Knightmare");

		setUndecorated(undecorated);
		setSize(screen);
		setVisible(true);

		optionen = new JButton[text.length];

		position = (int) ((MoodMusic.getVolume() / 0.84) + 94) - 1;

		volume = new JSlider();
		volume.setMinimum(0);
		volume.setMaximum(100);
		volume.setMajorTickSpacing(5);
		volume.setMinorTickSpacing(1);
		volume.setValue(position);
		volume.setBorder(BorderFactory.createLineBorder(Color.lightGray,1));
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
			optionen[i].setBorder(BorderFactory.createLineBorder(Color.lightGray,1));
			optionen[i].setFont(new Font("Arial", Font.BOLD, 48));
			if (i > 0)
				add(optionen[i - 1]);
		}

		add(volume);
		add(optionen[optionen.length - 1]);
		mm = a;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object q = e.getSource();

		if (q == volume) {
			repaint();
			System.out.println(volume.getValue());
			MoodMusic.setVolume((float) (int) ((6 - ((100 - volume.getValue()) * 0.84))));
			position = volume.getValue();
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object q = arg0.getSource();
		repaint();

		if (q == optionen[optionen.length - 1]) {
			mm.setVisible(true);
			mm.setAutoRequestFocus(true);
			dispose();
		}

	}

}
