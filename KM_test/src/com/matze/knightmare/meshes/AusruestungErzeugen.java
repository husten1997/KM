package com.matze.knightmare.meshes;

public class AusruestungErzeugen {

	private Ausruestung i;
	private int stats[];

	public AusruestungErzeugen() {
		stats = new int[12];
	}

	public void init_Array(int ang0, int ang1, int ang2, int ver0, int ver1, int ver2,
			int reichweite, int v, int cost, int bAng, int ausd, int moral) {
		stats[0] = ang0;
		stats[1] = ang1;
		stats[2] = ang2;
		stats[3] = ver0;
		stats[4] = ver1;
		stats[5] = ver2;
		stats[6] = reichweite;
		stats[7] = v;
		stats[8] = cost;
		stats[9] = bAng;
		stats[10] = ausd;
		stats[11] = moral;
	}

	public Ausruestung Helm() {
		init_Array(0, 0, 0, 5, 8, 0, 0, 0, 5, 0, 0, 6);
		i = new Ausruestung(0, stats, false);
		return i;
	}
	
	public Ausruestung Brustpanzer() {
		init_Array(0, 0, 0, 10, 10, 0, 0, 0, 10, 0, -5, 5);
		i = new Ausruestung(1, stats, false);
		return i;
	}
	
	public Ausruestung Armschienen() {
		init_Array(0, 0, 0, 10, 0, 0, 5, 0, 5, 2, 0, 4);
		i = new Ausruestung(2, stats, false);
		return i;
	}
	
	public Ausruestung Langschwert() {
		init_Array(12, 0, 0, 0, 0, 0, 2, 0, 15, 0, -2, 4);
		i = new Ausruestung(3, stats, false);
		return i;
	}
	
	public Ausruestung Wurfspeer() {
		init_Array(0, 15, 0, 0, 0, 0, 80, 0, 15, 5, 0, 3);
		i = new Ausruestung(4, stats, false);
		return i;
	}
	
	public Ausruestung Leichte_Ruestung() {
		init_Array(0, 0, 0, 4, 5, 0, 0, 10, 10, 0, 15, -5);
		i = new Ausruestung(5, stats, false);
		return i;
	}
	
	public Ausruestung Schwere_Ruestung() {
		init_Array(0, 0, 0, 20, 15, 5, 0, -10, 25, 0, -7, 2);
		i = new Ausruestung(6, stats, false);
		return i;
	}
	
	public Ausruestung test() {
		init_Array(12, 0, 0, 0, 0, 0, 2, 0, 15, 0, -2, 4);
		i = new Ausruestung(7, stats, false);
		return i;
	}
	
	public Ausruestung Feuerpfeil_Basiliste(){
		init_Array(0, 10, 40, 0, 0, 0, -64, 0, 10, 5, 0, 10);
		i = new Ausruestung(8, stats, false);
		return i;
	}
	
	public Ausruestung Feuerpfeile_Bogenschütze(){
		init_Array(0, 10, 5, 0, 0, 0, -32, 0, 0, 5, 0, 8);
		i = new Ausruestung(9, stats, false);
		return i;
	}
	
	
	
	
}
