package com.matze.knightmare.meshes;

import java.util.TimerTask;

import com.richard.knightmare.util.Pos;

public class Bauen {
	// TODO Timer stopppen wenn abgerissen Weg
	public static Building KohleMine(Pos p, Spieler sp) {
		Building b = new Building(0, p, 64, 64, "Kohlemine.png");
		b.setSpieler(sp);

		b.setKostetWarevonIndex(2, 10);

		b.addnichtErlaubt("kohle");
		b.addnichtErlaubt("stein");
		b.addnichtErlaubt("eisen");
		b.addnichtErlaubt("baum");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			Waren[] benötigt = new Waren[1];
			int[] amountBenötigt = new int[1];
			b.init(50, 20, 0, 0, "Kohlemine", benötigt, amountBenötigt,
					Rohstoffe.Kohle(), 25);

			b.getTimer().scheduleAtFixedRate(
					new TimerTask() {

						@Override
						public void run() {
							if ((/* Kohle in der Nähe */true) && (/*
																 * Kohle hat
																 * ressourcen
																 */true) && (!(b
									.getAmountProduzierterWareAuslesen() == b
									.getMaxLagerKap()))) {
								b.WareFertigstellen();
							}
						}

					}, (long) (60000 / b.getProdperMin()),
					(long) (60000 / b.getProdperMin()));

			return b;
		}
		return null;
	}

	public static Building EisenMine(Pos p, Spieler sp) {
		Building b = new Building(1, p, 64, 64, "Eisenerz 1.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 25);
		b.setKostetWarevonIndex(8, 10);

		b.addnichtErlaubt("kohle");
		b.addnichtErlaubt("eisen");
		b.addnichtErlaubt("baum");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			int am = 1;

			Waren[] benötigt = new Waren[am];
			int[] amountBenötigt = new int[am];

			benötigt[0] = Rohstoffe.Kohle();
			amountBenötigt[0] = 1;

			b.init(50, 20, 0, 0, "Eisenmine", benötigt, amountBenötigt,
					Rohstoffe.Eisen(), 100);

			b.getTimer().scheduleAtFixedRate(
					new TimerTask() {

						@Override
						public void run() {
							if (benötigt[0].substractWare(amountBenötigt[0])
									&& (!(b.getAmountProduzierterWareAuslesen() == b
											.getMaxLagerKap()))) {
								b.WareFertigstellen();
							}
						}

					}, (long) (60000 / b.getProdperMin()),
					(long) (60000 / b.getProdperMin()));
			return b;
		}
		return null;
	}

	public static Building Lager(Pos p, Spieler sp) {
		Building b = new Building(2, p, 64, 64, "Lager.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(8, 5);

		b.addnichtErlaubt("baum");

		int error = 0;

		if (!sp.hatLager()) {
			b.setKostetWarevonIndex(2, 0);
			b.setKostetWarevonIndex(8, 0);
		}

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO überprüfen

			Waren[] benötigt = new Waren[am];
			int[] amountBenötigt = new int[am];

			for (int i = 0; i < am; i++) {
				benötigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Lagerhaus", benötigt, amountBenötigt, null,
					1000);

			return b;
		}
		return null;
	}

	public static Building Holzfäller(Pos p, Spieler sp) {
		Building b = new Building(3, p, 64, 64, "Holz.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 5);

		b.addnichtErlaubt("baum");
		b.addnichtErlaubt("stein");
		b.addnichtErlaubt("eisen");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			int am = 1;
			Waren[] benötigt = new Waren[am];
			int[] amountBenötigt = new int[am];

			benötigt[0] = Rohstoffe.Holz();
			amountBenötigt[0] = 0;

			b.init(30, 5, 0, 0, "Holzfäller", benötigt, amountBenötigt,
					Rohstoffe.Holz(), 20);

			b.getTimer().scheduleAtFixedRate(
					new TimerTask() {

						@Override
						public void run() {
							if ((/* Baum in der Nähe */true) && (!(b
									.getAmountProduzierterWareAuslesen() == b
									.getMaxLagerKap()))) {
								b.WareFertigstellen();
							}
						}

					}, (long) (60000 / b.getProdperMin()),
					(long) (60000 / b.getProdperMin()));

			return b;
		}
		return null;
	}

	public static Building Haus(Pos p, Spieler sp) {
		Building b = new Building(4, p, 64, 32, "Haus.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 5);

		b.addnichtErlaubt("baum");
		b.addnichtErlaubt("stein");
		b.addnichtErlaubt("eisen");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			int am = 1;
			Waren[] benötigt = new Waren[am];
			int[] amountBenötigt = new int[am];

			benötigt[0] = Rohstoffe.Mensch();
			amountBenötigt[0] = 0;

			b.init(30, 1, 0, 0, "Haus", benötigt, amountBenötigt,
					Rohstoffe.Mensch(), 8);

			b.getTimer().scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					b.setProduktionProMinute(1);
					b.WareFertigstellen();
					// TODO ppm so ändern dass effektivität pro gebäude um 50%
					// gesteigert wird, steht es alleine in einem bestimmten
					// sektor, wird die produktion pro minute um 50% gesenkt
					// (auch negativ möglich, dann sterben die leute)
				}

			}, 0, (long) (600000 / b.getProdperMin()));

			b.getTimer2().scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					b.getSpieler().verteilen(9, 1);
					// b.getSpieler().setAmountofResourcewithIndex(
					// b.getSpieler().getAmountofResource(9) + 1, 9);
					// TODO ppm so ändern dass effektivität pro gebäude um 50%
					// gesteigert wird, steht es alleine in einem bestimmten
					// sektor, wird die produktion pro minute um 50% gesenkt
					// (auch negativ möglich, dann sterben die leute)
				}

			}, (long) (60000), (long) (60000));

			return b;
		}
		return null;
	}

	public static Building Sandschmelze(Pos p, Spieler sp) {
		Building b = new Building(5, p, 64, 64, "Sandschmeiz.png");

		b.addMuss("sand");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 15);

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			Waren[] benötigt = new Waren[1];
			int[] amountBenötigt = new int[1];

			benötigt[0] = Rohstoffe.Sand();
			amountBenötigt[0] = 5;

			b.init(50, 3, 0, 0, "Sandschmelze", benötigt, amountBenötigt,
					Rohstoffe.Glas(), 9);

			b.getTimer().scheduleAtFixedRate(
					new TimerTask() {

						@Override
						public void run() {
							if (benötigt[0].substractWare(amountBenötigt[0])
									&& (!(b.getAmountProduzierterWareAuslesen() == b
											.getMaxLagerKap()))) {
								b.WareFertigstellen();
							}
						}

					}, (long) (60000 / b.getProdperMin()),
					(long) (60000 / b.getProdperMin()));

			return b;
		}
		return null;
	}

	public static Building Bauernhof(Pos p, Spieler sp) {
		Building b = new Building(6, p, 128, 128, "Hof.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 8);
		b.addMuss("gras");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			Waren[] benötigt = new Waren[1];
			int[] amountBenötigt = new int[1];

			b.init(50, 4, 0, 0, "Bauernhof", benötigt, amountBenötigt,
					Rohstoffe.Getreide(), 36);

			b.getTimer().scheduleAtFixedRate(
					new TimerTask() {

						@Override
						public void run() {
							if ((/* Felder in der Nähe */true) && (/*
																 * Felder hat
																 * ressourcen
																 */true) && (!(b
									.getAmountProduzierterWareAuslesen() == b
									.getMaxLagerKap()))) {
								b.WareFertigstellen();
							}
						}

					}, (long) (60000 / b.getProdperMin()),
					(long) (60000 / b.getProdperMin()));

			return b;
		}
		return null;
	}

	public static Building Viehstall(Pos p, Spieler sp) {
		Building b = new Building(7, p, 64, 64, "viecha.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 15);

		b.addnichtErlaubt("baum");
		b.addnichtErlaubt("stein");
		b.addnichtErlaubt("eisen");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			Waren[] benötigt = new Waren[1];
			int[] amountBenötigt = new int[1];

			b.init(50, 5, 0, 0, "Viehstall", benötigt, amountBenötigt,
					Rohstoffe.Fleisch(), 25);

			b.getTimer().scheduleAtFixedRate(
					new TimerTask() {

						@Override
						public void run() {
							if (!(b.getAmountProduzierterWareAuslesen() == b
									.getMaxLagerKap())) {
								b.WareFertigstellen();
							}
						}

					}, (long) (60000 / b.getProdperMin()),
					(long) (60000 / b.getProdperMin()));

			return b;
		}
		return null;
	}

	public static Building Steinbruch(Pos p, Spieler sp) {
		Building b = new Building(8, p, 64, 64, "Steinbruch.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 5);

		b.addMuss("stein");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			Waren[] benötigt = new Waren[1];
			int[] amountBenötigt = new int[1];

			b.init(50, 15, 0, 0, "Steinbruch", benötigt, amountBenötigt,
					Rohstoffe.Fleisch(), 75);

			b.getTimer().scheduleAtFixedRate(
					new TimerTask() {

						@Override
						public void run() {
							if ((/* Stein in der Nähe */true) && (/*
																 * Stein hat
																 * ressourcen
																 */true) && (!(b
									.getAmountProduzierterWareAuslesen() == b
									.getMaxLagerKap()))) {
								b.WareFertigstellen();
							}
						}

					}, (long) (60000 / b.getProdperMin()),
					(long) (60000 / b.getProdperMin()));

			return b;
		}
		return null;
	}

	public static Building Turm(Pos p, Spieler sp) {
		Building b = new Building(9, p, 64, 64, "Turm.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(8, 30);
		b.setKostetWarevonIndex(2, 10);

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			Waren[] benötigt = new Waren[1];
			int[] amountBenötigt = new int[1];

			b.init(1500, 0, 0, 0, "Turm", benötigt, amountBenötigt,
					Rohstoffe.Nothing(), 0);

			return b;
		}
		return null;
	}

	public static Building Mauern(Pos p, Spieler sp) {
		Building b = new Building(10, p, 32, 32, "Mauer.png");
		b.setSpieler(sp);
		b.setKostetWarevonIndex(8, 8);

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			Waren[] benötigt = new Waren[1];
			int[] amountBenötigt = new int[1];

			b.init(1200, 0, 10, 70, "Mauern", benötigt, amountBenötigt,
					Rohstoffe.Nothing(), 0);
			return b;
		}
		return null;
	}

	public static Building Baum(Pos p, Spieler sp) {
		Building b = new Building(11, p, 32, 32, "Baum.png");
		b.setSpieler(new Spieler(-1, "Mutter Natur", -1, "KI", "Leicht"));
		Waren w = Rohstoffe.Holz();
		w.setAmount(50);
		Waren[] benötigt = new Waren[1];
		int[] amountBenötigt = new int[1];
		b.init(10, 0, 0, 0, "Baum", benötigt, amountBenötigt, w, 50);
		return b;
	}

	public static Building Schatzkammer(Pos p, Spieler sp) {
		Building b = new Building(12, p, 64, 32, "Schatzkammer.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(8, 5);

		b.addnichtErlaubt("baum");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO überprüfen

			Waren[] benötigt = new Waren[am];
			int[] amountBenötigt = new int[am];

			for (int i = 0; i < am; i++) {
				benötigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Schatzkammer", benötigt, amountBenötigt, null,
					1000);

			return b;
		}
		return null;
	}

	public static Building Waffenkammer(Pos p, Spieler sp) {
		Building b = new Building(13, p, 64, 64, "Waffenkammer.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(8, 5);

		b.addnichtErlaubt("baum");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO überprüfen

			Waren[] benötigt = new Waren[am];
			int[] amountBenötigt = new int[am];

			for (int i = 0; i < am; i++) {
				benötigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Waffenkammer", benötigt, amountBenötigt, null,
					1000);

			return b;
		}
		return null;
	}

	public static Building Kornspeicher(Pos p, Spieler sp) {
		Building b = new Building(14, p, 32, 32, "Kornspeicher.png");

		b.setSpieler(sp);
		b.setKostetWarevonIndex(2, 10);
		b.setKostetWarevonIndex(8, 5);

		b.addnichtErlaubt("baum");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO überprüfen

			Waren[] benötigt = new Waren[am];
			int[] amountBenötigt = new int[am];

			for (int i = 0; i < am; i++) {
				benötigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Kornspeicher", benötigt, amountBenötigt, null,
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

		b.addnichtErlaubt("baum");

		int error = 0;

		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (b.getSpieler().getAmountofResource(i)
					- b.getKostetWarevonIndex(i) < 0) {
				error++;
			}
		}

		if (error == 0) {

			int am = Rohstoffe.maxID(); // TODO überprüfen

			Waren[] benötigt = new Waren[am];
			int[] amountBenötigt = new int[am];

			for (int i = 0; i < am; i++) {
				benötigt[i] = Rohstoffe.Rohstoff_von_Index(i);
			}

			b.init(75, 0, 0, 0, "Marktplatz", benötigt, amountBenötigt, null,
					1000);

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
			return Holzfäller(p, spieler);
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
		default:
			return null;
		}
	}

	public static int[] getKostenvonGeb(int id) {
		Building b = new Building(0, new Pos(0, 0), 0, 0, "");
		switch (id) {
		case 0: {
			b.setKostetWarevonIndex(2, 10);
			return b.getKostetWarevonArray();
		}
		case 1: {
			b.setKostetWarevonIndex(2, 25);
			b.setKostetWarevonIndex(8, 10);
			return b.getKostetWarevonArray();
		}
		case 2: {
			b.setKostetWarevonIndex(2, 10);
			b.setKostetWarevonIndex(8, 5);
			return b.getKostetWarevonArray();
		}
		case 3: {
			b.setKostetWarevonIndex(2, 5);
			return b.getKostetWarevonArray();
		}
		case 4: {
			b.setKostetWarevonIndex(2, 5);
			return b.getKostetWarevonArray();
		}
		case 5: {
			b.setKostetWarevonIndex(2, 15);
			return b.getKostetWarevonArray();
		}
		case 6: {
			b.setKostetWarevonIndex(2, 8);
			return b.getKostetWarevonArray();
		}
		case 7: {
			b.setKostetWarevonIndex(2, 15);
			return b.getKostetWarevonArray();
		}
		case 8: {
			b.setKostetWarevonIndex(2, 15);
			return b.getKostetWarevonArray();
		}
		case 9: {
			b.setKostetWarevonIndex(8, 30);
			b.setKostetWarevonIndex(2, 10);
			return b.getKostetWarevonArray();
		}
		case 10: {
			b.setKostetWarevonIndex(8, 8);
			return b.getKostetWarevonArray();
		}
		case 11: {
			b.setKostetWarevonIndex(0, 0);
			return b.getKostetWarevonArray();
		}
		case 12: {
			b.setKostetWarevonIndex(2, 10);
			b.setKostetWarevonIndex(8, 5);
			return b.getKostetWarevonArray();
		}
		case 13: {
			b.setKostetWarevonIndex(2, 10);
			b.setKostetWarevonIndex(8, 5);
			return b.getKostetWarevonArray();
		}
		case 14: {
			b.setKostetWarevonIndex(2, 10);
			b.setKostetWarevonIndex(8, 5);
			return b.getKostetWarevonArray();
		}
		case 15: {
			b.setKostetWarevonIndex(2, 10);
			b.setKostetWarevonIndex(8, 5);
			return b.getKostetWarevonArray();
		}
		default:
			return new int[0];
		}
	}

	public static String getBuildingName(int id) {
		switch (id) {
		case 0:
			return "Kohlemine";
		case 1:
			return "Eisenmine";
		case 2:
			return "Lager";
		case 3:
			return "Holzfäller";
		case 4:
			return "Haus";
		case 5:
			return "Sandschmelze";
		case 6:
			return "Bauernhof";
		case 7:
			return "Viehstall";
		case 8:
			return "Steinbruch";
		case 9:
			return "Turm";
		case 10:
			return "Mauern";
		case 11:
			return "Baum";
		case 12:
			return "Schatzkammer";
		case 13:
			return "Waffenkammer";
		case 14:
			return "Kornspeicher";
		case 15:
			return "Martplatz";
		default:
			return "TODO";
		}
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
