package com.matze.knightmare.menues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.richard.knightmare.util.Optionsframesuperklasse;

@SuppressWarnings("serial")
public class LadenTest extends Optionsframesuperklasse implements ActionListener, ListSelectionListener {
	
	private JList<String> list;
	private JButton zurück;
	private String[] data = { "Keine Speicherstände vorhanden. Neues Spiel?" };

	public LadenTest() {
		super("back.png", "Knightmare: Laden");
		setLocationRelativeTo(null);

		list = new JList<String>(data); // data has type Object[]
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
		add(list);
		((DefaultListCellRenderer) list.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);

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
		if (data[0].equals("Keine Speicherstände vorhanden. Neues Spiel?")) {
			MainMenueTest.instance.setVisible(true);
			MainMenueTest.instance.setAutoRequestFocus(true);
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
			MainMenueTest.instance.setVisible(true);
			MainMenueTest.instance.setAutoRequestFocus(true);
			dispose();
		}
	}
}
