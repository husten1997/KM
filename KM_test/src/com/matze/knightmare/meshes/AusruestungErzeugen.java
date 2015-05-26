package com.matze.knightmare.meshes;

public class AusruestungErzeugen {

	private Items i;
	private int stats[];

	public AusruestungErzeugen() {
		stats = new int[12];
	}

	// s0 - s2 = Angriff|| -s5 = verteidigung
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

	public Items Helm() {
		init_Array(0, 0, 0, 5, 8, 0, 0, 0, 5, 0, 0, 6);
		i = new Ausruestung(0, stats);
		return i;
	}
	
	public Items Brustpanzer() {
		init_Array(0, 0, 0, 10, 10, 0, 0, 0, 10, 0, -5, 5);
		i = new Ausruestung(1, stats);
		return i;
	}
	
	public Items Armschienen() {
		init_Array(0, 0, 0, 10, 0, 0, 5, 0, 5, 2, 0, 4);
		i = new Ausruestung(2, stats);
		return i;
	}
	
	public Items Langschwert() {
		init_Array(12, 0, 0, 0, 0, 0, 2, 0, 15, 0, -2, 4);
		i = new Ausruestung(3, stats);
		return i;
	}
	
	
}
