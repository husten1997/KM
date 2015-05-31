package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.husten.knightmare.core.MainMenue;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class Laden extends Optionsframesuperklasse implements ActionListener, ListSelectionListener {

	private JList<String> list;
	private JButton zurück;
	private String[] data;
	private String defaultText[] = {"Keine Speicherstände vorhanden. Neues Spiel?"};
	private boolean speichVorhanden;
	private File path;

	public Laden() {
		super("back.png", "Knightmare: Laden");
		setLocationRelativeTo(null);
		
		
		//SaveFilter
		String a[] = Loader.getSavesDir().list();
		int länge = 0;
		
			//alles .xxx dateien und ordner ohne save am anfang filtern
		for (int i = 0; i < a.length; i++){
			if (a[i].contains(".") || !a[i].startsWith("save")){
				System.out.println(a[i]);
				a[i] = "";
			} else {
				länge++;
			}
		}
		
		for (int i = 0; i < a.length-1; i++){
			if (a[i].equals("")){
				a[i] = a[i+1];
				a[i+1] = "";
			}
		}
		
		data = new String[länge];
		int la = 0;
		for (int i = 0; i < länge; i++){
			while (a[la].equals("")){
				la++;
			}
			data[i] = a[la];
			a[la] = "";
		}
		
		speichVorhanden = !(data.length == 0);
		
		list = new JList<String>((data.length == 0)?defaultText:data); // data has type Object[]
		list.setSize(new Dimension(width, height));
		list.setBounds((screen.width - width) / 2 + width / 4, (screen.height - height) / 2, width / 2, height);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(0, 0, 0.25f, 0.25f));
		list.setForeground(Color.white);
		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.cyan);
		list.setVisibleRowCount(data.length);
		list.setFont(new Font("Arial", Font.BOLD, 40));
		list.setOpaque(false);
		((DefaultListCellRenderer) list.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds((screen.width - width) / 2 + width / 4, (screen.height - height) / 2, width / 2, height);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setBackground(new Color(0, 0, 0.25f, 0.25f));
		scroll.getVerticalScrollBar().setForeground(new Color(0, 0, 0.25f, 0.25f));
		scroll.getHorizontalScrollBar().setBackground(new Color(0, 0, 0.25f, 0.25f));
		scroll.getHorizontalScrollBar().setForeground(new Color(0, 0, 0.25f, 0.25f));
		scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
			@Override
	        protected JButton createDecreaseButton(int orientation) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
	            return b;
	        }

	        @Override    
	        protected JButton createIncreaseButton(int orientation) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
	            return b;
	        }
			
			 @Override 
		        protected void configureScrollBarColors(){
		            this.thumbColor = new Color(0, 0, 0.25f, 0.25f);
		        }
		});
		scroll.getHorizontalScrollBar().setFocusable(false);
		scroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI(){
			@Override
	        protected JButton createDecreaseButton(int orientation) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
	            return b;
	        }

	        @Override    
	        protected JButton createIncreaseButton(int orientation) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
	            return b;
	        }
			
			 @Override 
		        protected void configureScrollBarColors(){
		            this.thumbColor = new Color(0, 0, 0.25f, 0.25f);
		        }
		});
		scroll.getHorizontalScrollBar().setFocusable(false);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		add(scroll);
		
		scroll.getVerticalScrollBar().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// Ignore
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				repaint();
			}
		});
		scroll.getHorizontalScrollBar().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// Ignore
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				repaint();
			}
		});

		zurück = new JButton("Zurück");
		zurück.setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		zurück.setFont(new Font("Arial", Font.BOLD, width / 48));
		zurück.setBounds(screen.width / 2 + 3 * width / 8, (screen.height - height) / 2 + height - width / 24, width / 8, width / 24);
		zurück.addActionListener(this);
		zurück.setRolloverEnabled(false);
		zurück.setFocusable(false);
		add(zurück);

		list.addKeyListener(this);
		list.addListSelectionListener(this);
	}

	private void performAction(int x) {
		if (!speichVorhanden) {
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		Object q = arg0.getSource();

		if (q == list) {
			list.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
						performAction(list.getSelectedIndex());
					}
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
					// Ignore
				}
			});
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == zurück) {
			MainMenue.instance.setVisible(true);
			MainMenue.instance.setAutoRequestFocus(true);
			dispose();
		}
	}
}
