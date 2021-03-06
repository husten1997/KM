package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Profil extends Optionsframesuperklasse implements ActionListener, ItemListener {

	private JComboBox<String> sprache;
	private JTextField sprach;
	private ImageIcon io;
	
	private JTextField difficulty;
	private JComboBox<String> schwierigkei;
	
	private String val1[]={"Deutsch", "English"}, val2[] = {"Leicht", "Mittel", "Schwer"};
	
	private JTextField name[];
	private JButton zur�ck, profil, wiki;
	
	private boolean c;

	protected Profil(boolean inGame, String imgName, String namen, boolean config) {
		super(imgName, namen);
		this.inGame = inGame;
		
		c = config;
		
		//Schwierigkeit
		difficulty = new JTextField("Schwierigkeit:");
		difficulty.setEditable(false);
		difficulty.setBounds((screen.width - width) / 2 +100, (screen.height-height)/2+225, 200, 50);
		difficulty.setBorder(null);
		difficulty.setBackground(new Color(0, 0.25f, 0.5f, 1f));
		difficulty.setForeground(Color.white);
		difficulty.setHorizontalAlignment(JLabel.CENTER);
		add(difficulty);
		
		schwierigkei = new JComboBox<String>(val1);
		schwierigkei.validate();
		schwierigkei.setBounds((screen.width - width) / 2 +325, (screen.height-height)/2+150, 200, 50);
		schwierigkei.validate();
		schwierigkei.repaint();
		schwierigkei.addItemListener(this);
		schwierigkei.setSelectedIndex(Integer.parseInt(Loader.getCfgValue("SETTINGS: Default difficulty")));
		schwierigkei.setBorder(null);
		schwierigkei.setBackground(new Color(0, 0.25f, 0.5f, 1f));
		schwierigkei.setForeground(Color.white);
		add(schwierigkei);
		
		//Sprache
		sprach = new JTextField("Sprache:");
		sprach.setEditable(false);
		sprach.setBounds((screen.width - width) / 2 +325, (screen.height-height)/2+100, 200, 50);
		sprach.setBorder(null);
		sprach.setBackground(new Color(0, 0.25f, 0.5f, 1f));
		sprach.setForeground(Color.white);
		sprach.setHorizontalAlignment(JLabel.CENTER);
		add(sprach);
		
		sprache = new JComboBox<String>(val2);
		sprache.setBounds((screen.width - width) / 2 +100, (screen.height-height)/2+275, 200, 50);
		sprache.setBorder(null);
		sprache.setBackground(new Color(0, 0.25f, 0.5f, 1f));
		sprache.setForeground(Color.white);
		add(sprache);
		
		//Profilbild
		profil = new JButton();
		io = new ImageIcon (Loader.getCfgValue("SETTINGS: Profilbild"));
		io.setImage(io.getImage().getScaledInstance(200,100,Image.SCALE_SMOOTH));
		profil.setIcon(io);
		profil.setBounds((screen.width - width) / 2 +325,(screen.height-height)/2+ 225, 200, 100);
		profil.addActionListener(this);
		add(profil);
		
		validate();
		repaint();
		
		//Name
		name = new JTextField[2];
		name[0] = new JTextField("Gib hier deinen Namen ein:");
		name[1] = new JTextField(Loader.getCfgValue("SETTINGS: Profilname"));
		name[0].setBounds((screen.width - width) / 2 +100, (screen.height-height)/2+100, 200, 50);
		name[0].setEditable(false);
		name[0].setBorder(null);
		name[0].setBackground(new Color(0, 0.25f, 0.5f, 1f));
		name[0].setForeground(Color.white);
		name[0].setHorizontalAlignment(JLabel.CENTER);
		name[1].setBounds((screen.width - width) / 2 +100, (screen.height-height)/2+150, 200, 50);
		name[1].setBorder(null);
		name[1].setBackground(new Color(0, 0.25f, 0.5f, 1f));
		name[1].setForeground(Color.white);
		name[1].setHorizontalAlignment(JLabel.CENTER);
		name[0].addKeyListener(this);
		name[1].addKeyListener(this);
		add(name[0]);
		add(name[1]);

		
		//Default
		zur�ck = new JButton("Zur�ck");
		zur�ck.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zur�ck.setFont(new Font("Arial", Font.BOLD, width / 48));
		zur�ck.setBounds(screen.width / 2 + 3 * width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		zur�ck.addActionListener(this);
		zur�ck.setRolloverEnabled(false);
		zur�ck.setFocusable(false);
		add(zur�ck);
		
		wiki = new JButton("Wiki");
		wiki.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		wiki.setFont(new Font("Arial", Font.BOLD, width / 48));
		wiki.setBounds(screen.width / 2 + 3 * width / 8 - width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		wiki.addActionListener(this);
		wiki.setRolloverEnabled(false);
		wiki.setFocusable(false);
		add(wiki);
		
		validate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==wiki){
			new Wiki("back.png", "Knightmare: Wiki");
			dispose();
		}
		
		Loader.changeCfgValue("SETTINGS: Profilname", name[1].getText());
		Loader.changeCfgValue("SETTINGS: Default difficulty", ""+(schwierigkei.getSelectedIndex()));
		if (e.getSource() == zur�ck) {
			if (c) {
				new Configscreen("back.png", "Knightmare: Configscreen");
				this.dispose();
			} else {
				if (inGame) {
					InGameOptionen.instance.dispose();
					InGameOptionen.instance.setUndecorated(isUndecorated());
					InGameOptionen.instance.setVisible(true);
					InGameOptionen.instance.setAutoRequestFocus(true);
					dispose();
				} else {
					new Optionen(false);
					dispose();
				}
			}
		}
		
		if (e.getSource() == profil){
			 final JFileChooser fc = new JFileChooser();
	            fc.showOpenDialog(this);

	            try {
	                io = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
	                Loader.changeCfgValue("SETTINGS: Profilbild", fc.getSelectedFile().getAbsolutePath());
	                io.setImage(io.getImage().getScaledInstance(200,100,Image.SCALE_SMOOTH));
	                profil.setIcon(io);
	            } catch (Exception e1){
	            	
	            }
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Loader.changeCfgValue("SETTINGS: Profilname", name[1].getText());
		if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Best�tigen"))) {
			name[1].setFocusable(false);
			name[0].setRequestFocusEnabled(true);
			name[1].setFocusable(true);
		}
		if(!name[1].isFocusOwner()){
			if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: Fenster- u. Vollbildmodus"))) {
				dispose();
				setUndecorated(!isUndecorated());
				setVisible(true);
				setAutoRequestFocus(true);
				setLocationRelativeTo(null);
				Loader.changeCfgValue("Fullscreen", String.valueOf(isUndecorated()));
			} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume -"))) {
				MoodMusic.changeVolume(-0.5f);
			} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Volume +"))) {
				MoodMusic.changeVolume(+0.5f);
			} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(getString("CONTROL_KEY: Escape/Zur�ck"))) {
				if (c) {
					new Configscreen("back.png", "Knightmare: Configscreen");
					this.dispose();
				} else {
					if (inGame) {
						InGameOptionen.instance.dispose();
						InGameOptionen.instance.setUndecorated(isUndecorated());
						InGameOptionen.instance.setVisible(true);
						InGameOptionen.instance.setAutoRequestFocus(true);
						dispose();
					} else {
						Optionen.instance.dispose();
						Optionen.instance.setUndecorated(isUndecorated());
						Optionen.instance.setVisible(true);
						Optionen.instance.setAutoRequestFocus(true);
						dispose();
					}
				}
			} else if (KeyEvent.getKeyText(e.getExtendedKeyCode()).equals(Loader.getCfgValue("CONTROL_KEY: V-Sync"))) {
				Loader.changeCfgValue("CONTROL_KEY: V-Sync", Loader.getCfgValue("CONTROL_KEY: V-Sync").equals("On") ? "Off" : "On");
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getSource() == schwierigkei.getSelectedItem()){
			Loader.changeCfgValue("SETTINGS: Default difficulty", ""+schwierigkei.getSelectedIndex());
		}
	}

}
