package com.matze.knightmare.meshes;

public class Ausruestung {

	protected int[] changeStats; // muss in der Reihenfolge Anfriff[3]
									// Verteidigung[3] usw angegeben sein

	public Ausruestung() {
		changeStats = new int[10];
		for (int i = 0; i < changeStats.length; i++) {
			changeStats[i] = 0;
		}
	}
	
	public Ausruestung(int[] a) {
		changeStats = new int[12];
		for (int i = 0; i < changeStats.length; i++) {
			changeStats[i] = a[i];
		}
	}

	public int[] getCS() {
		return changeStats;
	}
	
	public void setStats(int i, int amount){
		changeStats[i]=amount;
	}
}
