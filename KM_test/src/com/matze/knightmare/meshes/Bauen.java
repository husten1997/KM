package com.matze.knightmare.meshes;

import java.util.TimerTask;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.core.Knightmare;
import com.richard.knightmare.util.Pos;

public class Bauen {
	// TODO Timer stopppen wenn abgerissen Weg
	private static Spieler mutterNatur = new Spieler(-1, "Mama Natur", -1,
			"KI", "Schwer");

	public static Building KohleMine(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(0, p, 64, 64, "Kohlemine.png");
		b.setSpieler(sp);

		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 2);
		
		
		b.addnichtErlaubt(StringConstants.Material_t.GRAS);
		b.addnichtErlaubt(StringConstants.Material_t.SAND);
		b.addnichtErlaubt(StringConstants.Material_t.MOOR);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);

		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];
			b.init(50,2, 0, 0, "Kohlemine", ben�tigt, amountBen�tigt,
					Rohstoffe.Kohle(), 25);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
								b.getSpieler().verteilen(Rohstoffe.Kohle().getID(), (int) Math.round(1*hilfsd));
							}

						});
			}

			return b;
		}
		return null;
	}

	public static Building EisenMine(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(1, p, 64, 64, "Eisenerz 1.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 15);
		b.setKostetWarevonIndex(8, 10);
		b.setKostetWarevonIndex(Rohstoffe.Glas().getID(), 5);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 3);

		b.addnichtErlaubt(StringConstants.Material_t.GRAS);
		b.addnichtErlaubt(StringConstants.Material_t.SAND);
		b.addnichtErlaubt(StringConstants.Material_t.MOOR);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);

		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = 1;

			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			ben�tigt[0] = Rohstoffe.Kohle();
			amountBen�tigt[0] = 1;

			b.init(50, 1, 0, 0, "Eisenmine", ben�tigt, amountBen�tigt,
					Rohstoffe.Eisen(), 100);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
//								if (b.getBen�tigt()[0].getAmount() > 0){
//									b.getSpieler().verteilen(Rohstoffe.Eisen().getID(), 1);
//									//b.getBen�tigt()[0].substractWare(1);
//									b.getSpieler().abziehen(Rohstoffe.Kohle().getID(),1);
//								}
								
								if (!(b.getSpieler().getAmountofResource(Rohstoffe.Kohle().getID())-1 < 0)){
									b.getSpieler().verteilen(Rohstoffe.Eisen().getID(), (int) Math.round(1*hilfsd));
									b.getSpieler().abziehen(Rohstoffe.Kohle().getID(), (int) Math.round(1*hilfsd));
								}
							}

						});
			}
			return b;
		}
		return null;
	}

	public static Building Lager(Pos p, Spieler sp) {
		Building b = new Building(2, p, 64, 64, "Lager.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(8, 5);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.hatLager()) {
			b.setKostetWarevonIndex(2, 0);
			b.setKostetWarevonIndex(8, 0);
		}

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID();

			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			for (int i = 0; i < am; i++) {
				ben�tigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Lagerhaus", ben�tigt, amountBen�tigt, null,
					1000);

			return b;
		}
		return null;
	}

	public static Building Holzf�ller(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(3, p, 64, 64, "Holz.png");
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 3);

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 5);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = 1;
			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			ben�tigt[0] = Rohstoffe.Holz();
			amountBen�tigt[0] = 0;

			b.init(30, 5, 0, 5, "Holzf�ller", ben�tigt, amountBen�tigt,
					Rohstoffe.Holz(), 20);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
								Building k = Knightmare.newHandler.suchBaum((int)(p.getX()/32), (int)(p.getY()/32), b.getReichweite());
								
								if(k!=null){
									if (k.getBen�tigt()[0].substractWare((int) Math.round(1*hilfsd))){
										b.getSpieler().verteilen(Rohstoffe.Holz().getID(), (int) Math.round(1*hilfsd));
										if (k.getBen�tigt()[0].getAmount()==(int) Math.round(1*hilfsd)){
											Knightmare.newHandler.die(k);
										}
									}else{
//										Pos pk = k.getPosition();
										Knightmare.newHandler.die(k);
//										Knightmare.newHandler.place(Baumstumpf(pk));//TODO weil ged ned in dem Thread
//										k = Knightmare.newHandler.suchBaum((int)p.getX(), (int)p.getY(), b.getReichweite());
									}
								}
							}

						});
			}

			return b;
		}
		return null;
	}

	public static Building Haus(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(4, p, 64, 32, "Haus.png");
		b.setSpieler(sp);

		b.setKostetWarevonIndex(2, 5);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 2);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = 1;
			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			ben�tigt[0] = Rohstoffe.Mensch();
			amountBen�tigt[0] = 2;

			b.init(30, 1, 0, 0, "Haus", ben�tigt, amountBen�tigt,
					Rohstoffe.Mensch(), 8);

			
			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {
							
							@Override
							public void run() {
								b.setProduktionProMinute(1);

								if (b.getSpieler().getAmountofResource(Rohstoffe.Fleisch().getID()) - 2 >= 0){
									b.getSpieler().verteilen(Rohstoffe.Mensch().getID(), (int) Math.round(1*hilfsd));
									b.getSpieler().verteilen(9, (int) Math.round(1*hilfsd));
									b.getSpieler().abziehen(Rohstoffe.Fleisch().getID(), 3);
								} else {
									if (b.getSpieler().getAmountofResource(Rohstoffe.Mensch().getID()) > 1){
										b.getSpieler().abziehen(Rohstoffe.Mensch().getID(), 1);
									}
								}
								
								// TODO ppm so �ndern dass effektivit�t pro
								// geb�ude um 50%
								// gesteigert wird, steht es alleine in einem
								// bestimmten
								// sektor, wird die produktion pro minute um 50%
								// gesenkt
								// (auch negativ m�glich, dann sterben die
								// leute)
							}

						});
			}

			// @Override
			// public void run() {
			// b.getSpieler().verteilen(9, 1);
			// // b.getSpieler().setAmountofResourcewithIndex(
			// // b.getSpieler().getAmountofResource(9) + 1, 9);
			// // TODO ppm so �ndern dass effektivit�t pro geb�ude um 50%
			// // gesteigert wird, steht es alleine in einem bestimmten
			// // sektor, wird die produktion pro minute um 50% gesenkt
			// // (auch negativ m�glich, dann sterben die leute)
			// }
			//
			// }, (long) (60000), (long) (60000));

			return b;
		}
		return null;
	}

	public static Building Sandschmelze(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(5, p, 64, 64, "Sandschmeiz.png");

		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 15);
		b.setKostetWarevonIndex(Rohstoffe.Stein().getID(), 10);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 4);

		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];

			ben�tigt[0] = Rohstoffe.Sand();
			amountBen�tigt[0] = 5;

			b.init(50, 3, 0, 0, "Sandschmelze", ben�tigt, amountBen�tigt,
					Rohstoffe.Glas(), 9);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
//								if (ben�tigt[0]
//										.substractWare(amountBen�tigt[0])
//										&& (!(b.getAmountProduzierterWareAuslesen() == b
//												.getMaxLagerKap()))) {
//									b.WareFertigstellen();
//								}
								
								if (b.getSpieler().getAmountofResource(b.getBen�tigt()[0].getID()) - 3 >= 0){
									b.getSpieler().verteilen(b.getProduziert().getID(), (int) Math.round(1*hilfsd));
									b.getSpieler().abziehen(b.getBen�tigt()[0].getID(), 3);
								}
							}

						});
			}

			return b;
		}
		return null;
	}

	public static Building Bauernhof(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(6, p, 128, 128, "Hof.png");

		b.setSpieler(sp);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		b.setKostetWarevonIndex(2, 8);
		b.setKostetWarevonIndex(Rohstoffe.Glas().getID(), 5);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 3);

		b.addMuss(StringConstants.Material_t.GRAS);

		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];

			b.init(50, 2, 0, 0, "Bauernhof", ben�tigt, amountBen�tigt,
					Rohstoffe.Getreide(), 36);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
//								if ((/* Felder in der N�he */true) && (/*
//																	 * Felder
//																	 * hat
//																	 * ressourcen
//																	 */true) && (!(b
//										.getAmountProduzierterWareAuslesen() == b
//										.getMaxLagerKap()))) {
//									b.WareFertigstellen();
//									b.getSpieler().verteilen(Rohstoffe.Fleisch().getID(), 1);
//								}
								
								Building k = Knightmare.newHandler.suchFeld((int)(p.getX()/32), (int)(p.getY()/32), b.getReichweite());
								
								if(k!=null){
									if (k.getBen�tigt()[0].substractWare((int) Math.round(1*hilfsd))){
										b.getSpieler().verteilen(Rohstoffe.Getreide().getID(), (int) Math.round(1*hilfsd));
										if (k.getBen�tigt()[0].getAmount()==0){
											Knightmare.newHandler.die(k);
										}
									}else{
//										Pos pk = k.getPosition();
										b.getSpieler().verteilen(Rohstoffe.Getreide().getID(), k.getBen�tigt()[0].getAmount());
										Knightmare.newHandler.die(k);
//										Knightmare.newHandler.place(Baumstumpf(pk));//TODO weil ged ned in dem Thread
//										k = Knightmare.newHandler.suchBaum((int)p.getX(), (int)p.getY(), b.getReichweite());
									}
								}
							}

						});
			}

			return b;
		}
		return null;
	}

	public static Building Viehstall(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(7, p, 64, 64, "viecha.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 8);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 4);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		b.addMuss(StringConstants.Material_t.GRAS);

		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];

			b.init(50, 3, 0, 0, "Viehstall", ben�tigt, amountBen�tigt,
					Rohstoffe.Fleisch(), 25);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
//								if (!(b.getAmountProduzierterWareAuslesen() == b
//										.getMaxLagerKap())) {
//									b.WareFertigstellen();
//								}
								b.getSpieler().verteilen(Rohstoffe.Fleisch().getID(), (int) Math.round(1*hilfsd));
							}

						});
			}

			return b;
		}
		return null;
	}

	public static Building Steinbruch(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(8, p, 64, 64, "Steinbruch.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 5);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 3);

		b.addnichtErlaubt(StringConstants.Material_t.GRAS);
		b.addnichtErlaubt(StringConstants.Material_t.SAND);
		b.addnichtErlaubt(StringConstants.Material_t.MOOR);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];

			b.init(50, 4, 0, 0, "Steinbruch", ben�tigt, amountBen�tigt,
					Rohstoffe.Stein(), 75);

			if (!sp.getName().equals("Mama Natur")) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
//								if ((/* Stein in der N�he */true) && (/*
//																	 * Stein hat
//																	 * ressourcen
//																	 */true) && (!(b
//										.getAmountProduzierterWareAuslesen() == b
//										.getMaxLagerKap()))) {
//									b.WareFertigstellen();
//								}
								b.getSpieler().verteilen(Rohstoffe.Stein().getID(), (int) Math.round(2*hilfsd));
						}});
			}

			return b;
		}
		return null;
	}

	public static Building Turm(Pos p, Spieler sp) {
		Building b = new Building(9, p, 64, 64, "Turm.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(8, 30);
		b.setKostetWarevonIndex(2, 10);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];

			b.init(1500, 0, 0, 0, "Turm", ben�tigt, amountBen�tigt,
					Rohstoffe.Nothing(), 0);

			return b;
		}
		return null;
	}

	public static Building Mauern(Pos p, Spieler sp) {
		Building b = new Building(10, p, 32, 32, "Mauer.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(8, 8);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];

			b.init(1200, 0, 10, 70, "Mauern", ben�tigt, amountBen�tigt,
					Rohstoffe.Nothing(), 0);
			return b;
		}
		return null;
	}

	public static Building Baum(Pos p, Spieler sp) {
		Building b = new Building(11, p, 32, 32, "Baum.png");
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		b.setSpieler(mutterNatur);
		Waren w = Rohstoffe.Holz();
		w.setAmount(20);
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		amountBen�tigt[0]=1;
		ben�tigt[0] = w;
		b.init(10, 0, 0, 0, "Baum", ben�tigt, amountBen�tigt, null, 50);
		return b;
	}

	public static Building Schatzkammer(Pos p, Spieler sp) {
		Building b = new Building(12, p, 64, 32, "Schatzkammer.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(8, 5);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO �berpr�fen

			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			for (int i = 0; i < am; i++) {
				ben�tigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Schatzkammer", ben�tigt, amountBen�tigt, null,
					1000);

			return b;
		}
		return null;
	}

	public static Building Waffenkammer(Pos p, Spieler sp) {
		Building b = new Building(13, p, 64, 64, "Waffenkammer.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 15);
		b.setKostetWarevonIndex(8, 8);
		b.setKostetWarevonIndex(Rohstoffe.Glas().getID(), 8);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO �berpr�fen

			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			for (int i = 0; i < am; i++) {
				ben�tigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Waffenkammer", ben�tigt, amountBen�tigt, null,
					1000);

			return b;
		}
		return null;
	}

	public static Building Kornspeicher(Pos p, Spieler sp) {
		Building b = new Building(14, p, 32, 32, "Kornspeicher.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 5);
		b.setKostetWarevonIndex(8, 5);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO �berpr�fen

			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			for (int i = 0; i < am; i++) {
				ben�tigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Kornspeicher", ben�tigt, amountBen�tigt, null,
					1000);

			return b;
		}
		return null;
	}

	public static Building Marktplatz(Pos p, Spieler sp) {
		Building b = new Building(15, p, 64, 64, "Marktplatz.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(8, 5);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO �berpr�fen

			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			for (int i = 0; i < am; i++) {
				ben�tigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Marktplatz", ben�tigt, amountBen�tigt, null,
					1000);

			return b;
		}
		return null;
	}
	
	public static Building Baumstumpf(Pos p){
		Building b = new Building(16, p, 32, 32, "baumstumpf.png");
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		b.setKostetWarevonIndex(Rohstoffe.Geld().getID(), -10);
		b.setSpieler(mutterNatur);
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		b.init(10, 0, 0, 0, "Baumstumpf", ben�tigt, amountBen�tigt, Rohstoffe.Nothing(), 50);
		return b;
	}
	
	
	public static Building B�ckerei(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(17, p, 64, 64, "B�ckerei.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 12);
		b.setKostetWarevonIndex(Rohstoffe.Stein().getID(), 10);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 4);

		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];
			
			ben�tigt[0] = Rohstoffe.Getreide();
			amountBen�tigt[0] = 3;

			b.init(50, 3, 0, 0, "B�ckerei", ben�tigt, amountBen�tigt,
					Rohstoffe.Stein(), 75);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
//								if ((/* Stein in der N�he */true) && (/*
//																	 * Stein hat
//																	 * ressourcen
//																	 */true) && (!(b
//										.getAmountProduzierterWareAuslesen() == b
//										.getMaxLagerKap()))) {
//									b.WareFertigstellen();
//								}
								if (b.getSpieler().getAmountofResource(Rohstoffe.Getreide().getID())-3 >= 0){
									b.getSpieler().verteilen(Rohstoffe.Fleisch().getID(), (int) Math.round(1*hilfsd));
									b.getSpieler().abziehen(Rohstoffe.Getreide().getID(), 3);
								}
						}});
			}

			return b;
		}
		return null;
	}
	
	public static Building Kaserne(Pos p, Spieler sp) {
		Building b = new Building(18, p, 64, 64, "Kaserne.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 20);
		b.setKostetWarevonIndex(Rohstoffe.Stein().getID(), 15);
		b.setKostetWarevonIndex(Rohstoffe.Glas().getID(), 5);

		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];

			b.init(50, 4, 0, 0, "Kaserne", ben�tigt, amountBen�tigt,
					null, 75);

			return b;
		}
		return null;
	}
	
	public static Building Schmied(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(19, p, 32, 32, "Schmied.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 8);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 3);
		b.setKostetWarevonIndex(Rohstoffe.Stein().getID(), 12);

		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];
			
			ben�tigt[0] = Rohstoffe.Eisen();
			amountBen�tigt[0] = 2;

			b.init(50, 2, 0, 0, "Schmied", ben�tigt, amountBen�tigt,
					Rohstoffe.Armbrust(), 75);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
								if (b.getSpieler().getAmountofResource(ben�tigt[0].getID())-4 >= 0){
								b.getSpieler().verteilen(Rohstoffe.Armbrust().getID(), (int) Math.round(1*hilfsd));
								b.getSpieler().abziehen(Rohstoffe.Eisen().getID(), 4);
								}
						}});
			}

			return b;
		}
		return null;
	}
	
	public static Building Feld(Pos p, Spieler sp) {
		Building b = new Building(20, p, 32, 32, "ocka.png");
		b.addMuss(StringConstants.Material_t.GRAS);
		b.setKostetWarevonIndex(Rohstoffe.Getreide().getID(), 2);
		b.setSpieler(sp);
		Waren w = Rohstoffe.Holz();
		w.setAmount(20);
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		amountBen�tigt[0]=1;
		ben�tigt[0] = w;
		b.init(10, 0, 0, 0, "Feld", ben�tigt, amountBen�tigt, null, 50);
		return b;
	}
	
	public static Building SandGrube(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(21, p, 32, 32, "Sandgrube.png");
		b.setSpieler(sp);

		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 2);
		b.addMuss(StringConstants.Material_t.SAND);

		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			Waren[] ben�tigt = new Waren[1];
			int[] amountBen�tigt = new int[1];
			b.init(50, 5, 0, 0, "Sandgrube", ben�tigt, amountBen�tigt,
					Rohstoffe.Sand(), 25);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
								b.getSpieler().verteilen(Rohstoffe.Sand().getID(), (int) Math.round(1*hilfsd));
							}

						});
			}

			return b;
		}
		return null;
	}

	public static Building F�rster(Pos p, Spieler sp) {
		double d = sp.getDifficulty();
		d = 2-d;
		if(d==0){
			d=0.5;
		}
		double hilfsd = d;
		Building b = new Building(22, p, 32, 32, "F�rster.png");
		b.setKostetWarevonIndex(Rohstoffe.Mensch().getID(), 3);

		b.setSpieler(sp);
		b.setKostetWarevonIndex(Rohstoffe.Holz().getID(), 10);
		b.setKostetWarevonIndex(Rohstoffe.Stein().getID(), 5);
		b.addnichtErlaubt(StringConstants.Material_t.WATER);
		int error = 0;

		if (!sp.equals(mutterNatur)) {
			for (int i = 0; i < Rohstoffe.maxID(); i++) {
				if (b.getSpieler().getAmountofResource(i)
						- b.getKostetWarevonIndex(i) < 0) {
					error++;
				}
			}
		}

		if (error == 0) {

			int am = 1;
			Waren[] ben�tigt = new Waren[am];
			int[] amountBen�tigt = new int[am];

			ben�tigt[0] = Rohstoffe.Holz();
			amountBen�tigt[0] = 1;

			b.init(30, d, 0, 5, "F�rster", ben�tigt, amountBen�tigt,
					Rohstoffe.Holz(), 20);

			if (!sp.equals(mutterNatur)) {
				b.setTimerTask(
						new TimerTask() {

							@Override
							public void run() {
								Pos pos = Knightmare.newHandler.suchFrei((int)(p.getX()/32), (int)(p.getY()/32), b.getReichweite());
								
								if(pos!=null){
									if (b.getSpieler().getAmountofResource(ben�tigt[0].getID())-1 >= 0){
										b.getSpieler().abziehen(Rohstoffe.Holz().getID(), 1);
										Knightmare.newHandler.place(Bauen.Baum(pos, mutterNatur));
									}
								}
							}

						});
			}

			return b;
		}
		return null;
	}
	

	public static Building getBuildingforID(int id, Pos p, Spieler spieler) {
		switch (id) {
		case 0: {
			return KohleMine(p, spieler);
		}
		case 1: {
			return EisenMine(p, spieler);
		}
		case 2: {
			return Lager(p, spieler);
		}
		case 3: {
			return Holzf�ller(p, spieler);
		}
		case 4: {
			return Haus(p, spieler);
		}
		case 5: {
			return Sandschmelze(p, spieler);
		}
		case 6: {
			return Bauernhof(p, spieler);
		}
		case 7: {
			return Viehstall(p, spieler);
		}
		case 8: {
			return Steinbruch(p, spieler);
		}
		case 9: {
			return Turm(p, spieler);
		}
		case 10: {
			return Mauern(p, spieler);
		}
		case 11: {
			return Baum(p, spieler);
		}
		case 12: {
			return Schatzkammer(p, spieler);
		}
		case 13: {
			return Waffenkammer(p, spieler);
		}
		case 14: {
			return Kornspeicher(p, spieler);
		}
		case 15: {
			return Marktplatz(p, spieler);
		}
		case 16: {
			return Baumstumpf(p);
		}
		case 17: {
			return B�ckerei(p, spieler);
		}
		case 18: {
			return Kaserne(p, spieler);
		}
		case 19: {
			return Schmied(p, spieler);
		}
		case 20: {
			return Feld(p, spieler);
		}
		case 21: {
			return SandGrube(p, spieler);
		}
		case 22: {
			return F�rster(p, spieler);
		}
		default:
			return null;
		}
	}
	
	public static Spieler getMutterNatur(){
		return mutterNatur;
	}

	public static int[] getKostenvonGeb(int id) {
		return getBuildingforID(id, new Pos(0, 0), mutterNatur)
				.getKostetWarevonArray();
	}

	public static String getBuildingName(int id) {
		return getBuildingforID(id, new Pos(0,0), mutterNatur).getName();
	}

	public static void kostenAbziehen(Building b) {
		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			b.getSpieler().abziehen(i, b.getKostetWarevonIndex(i));
			// b.getSpieler().setAmountofResourcewithIndex
			// (b.getSpieler().getAmountofResource(i)-b.getKostetWarevonIndex(i),
			// i);
		}
	}
}
