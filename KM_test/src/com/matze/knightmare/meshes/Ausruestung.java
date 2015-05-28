package com.matze.knightmare.meshes;

public class Ausruestung extends Items{

	protected int[] changeStats; // muss in der Reihenfolge Anfriff[3]
	protected boolean fürGebäude;								// Verteidigung[3] usw angegeben sein

	public Ausruestung(int id) {
		super(id);
		changeStats = new int[12];
		for (int i = 0; i < changeStats.length; i++) {
			changeStats[i] = 0;
		}
	}

	public Ausruestung(int id, int[] a, boolean gebaeude) {
		super(id);
		changeStats = new int[12];
		for (int i = 0; i < changeStats.length; i++) {
			changeStats[i] = a[i];
		}
		fürGebäude = gebaeude;
	}

	public int getCS(int i) {
		return changeStats[i];
	}

	public void setStats(int i, int amount) {
		changeStats[i] = amount;
	}
}
