package com.matze.knightmare.meshes;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Building extends RectangleGraphicalObject {	
	private int health;
	private int ProduktionproMinute;
	private int angriff, reichweite;
	private String name;
	private Waren[] benötigt;
	private int[] amountBenötigt;
	private Waren produziert;
	private int amountProduzierteWare;
	private Ausruestung[] verbesserungen;
	private int maxLagerKapazität;

	
	public Building(Pos position, int width, int height, String textureName) {
		super(position, width, height, textureName, false);
		type = MeshType.GEBÄUDE;
		verbesserungen = new Ausruestung[6];
	}

	public void init(int health, int ProduktionproMinute, int angriff,
			int reichweite, String name, Waren[] benötigt,
			int[] amountBenötigt, Waren produziert, int maxLagerKap) {
		this.health = health;
		this.ProduktionproMinute = ProduktionproMinute;
		this.angriff = angriff;
		this.reichweite = reichweite;
		this.name = name;

		this.benötigt = new Waren[benötigt.length];
		this.amountBenötigt = new int[benötigt.length];

		for (int i = 0; i < benötigt.length; i++) {
			this.benötigt[i] = benötigt[i];
			this.amountBenötigt[i] = amountBenötigt[i];
		}
		this.produziert = produziert;
		this.maxLagerKapazität = maxLagerKap;
	}

	public void WareFertigstellen() {
		amountProduzierteWare++;
	}

	public int getAmountProduzierterWare() {
		int hilfe = amountProduzierteWare;
		amountProduzierteWare = 0;
		return hilfe;
	}

	public Waren getProduzierteWare() {
		if (amountProduzierteWare == 0 || produziert == null) {
			System.out.println("Du kannst nichts abholen, oder dieses Gebäude produziert nichts");
			return null;
		}
		return produziert;
	}

	public boolean setVerbesserung(int i, Ausruestung it) {
		if (it.fürGebäude) {
			verbesserungen[i] = it;
			return true;
		}
		return false;

	}

	public void changeStats(Ausruestung a) {
		//int stats = new
	}


}
