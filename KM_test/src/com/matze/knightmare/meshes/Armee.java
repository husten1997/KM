package com.matze.knightmare.meshes;

public class Armee {

	private Truppen t[];
	private int anz;

	public Armee() {
		t = new Truppen[100];
		anz = 0;
	}

	public void addTroop(Truppen tr) {
		t[anz] = tr;
		anz++;
	}

	public Truppen getTroop(int i) {
		return t[i];
	}

}
