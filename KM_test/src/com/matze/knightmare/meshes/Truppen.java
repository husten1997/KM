package com.matze.knightmare.meshes;

public class Truppen {

	protected int[] angriff, verteidigung; // [0] entspricht nahkampf [1]
											// entspricht fernkampf, [2]
											// entspricht gegen Gebäude;
	// [0] entspricht nahkampf [1] entspricht fernkampf, [2] entspricht gegen
	// Artillerie;
	protected int bonusAng, reichweite, grundmoral, moral, ausdauer, geschwindigkeit, kosten;
	protected String name;
	protected Ausruestung ausruestung[];
	// protected sound s;

	public Truppen() {
		angriff = new int[3];
		verteidigung = new int[3];
		ausruestung = new Ausruestung[8];
	}

	public void verbesserung(int[] ang, int[] ver, int b, int reichw, int geschw, int mor, int aus) {

		for (int i = 0; i < angriff.length; i++) {
			angriff[i] += ang[i];
			verteidigung[i] += ver[i];
		}
		bonusAng = b;
		reichweite += reichw;
		geschwindigkeit += geschw;
		grundmoral += mor;
		ausdauer = aus;
	}

	public void init(int[] ang, int[] ver, int b, String nam, int reichw, int geschw, int kost, int mo, int aus) {
		for (int i = 0; i < 2; i++) {
			angriff[i] += ang[i];
			verteidigung[i] += ver[i];
		}
		bonusAng = b;
		reichweite += reichw;
		geschwindigkeit += geschw;
		name = nam;
		kosten = kost;
		grundmoral = mo;
		ausdauer = aus;
	}

	public int ausdauerBerechnen(int a, int einheitenFreundlich, int einheitenFeindlich) { // int
																							// a
																							// entspricht
																							// der
																							// ausdauer
																							// die
																							// zuvor
																							// returnt
																							// wird
		ausdauer = a * (1 + (moralBerechnen(einheitenFreundlich, einheitenFeindlich) / 100));
		if (ausdauer < 10)
			ausdauer = 10;
		return ausdauer;
	}

	public int moralBerechnen(int einheitenFreundlich, int einheitenFeindlich) {
		moral = grundmoral * (1 + ((einheitenFreundlich - einheitenFeindlich) / 100));
		return moral;
	}

	public int getMoral() {
		return moral;
	}

	public void setAusruestung(int i, Ausruestung aus) {
		ausruestung[i] = aus;
		changeStats(i);
	}

	private void changeStats(int a) {
		int[] stats = ausruestung[a].getCS();
		for (int i = 0; i < 3; i++) {
			angriff[i] += stats[i];
			verteidigung[i + 3] += stats[i + 3];
		}
		reichweite += stats[6];
		geschwindigkeit += stats[7];
		kosten += stats[8];
		bonusAng += stats[9];
		ausdauer += stats[10];
		grundmoral += stats[11];

	}

}
