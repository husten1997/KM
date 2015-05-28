package com.matze.knightmare.meshes;

public class Gebaeude {

	private int health;
	private int ProduktionproMinute;
	private int angriff, reichweite;
	private String name;
	private Waren[] ben�tigt;
	private int[] amountBen�tigt;
	private Waren produziert;
	private int amountProduzierteWare;
	private Ausruestung[] verbesserungen;

	public Gebaeude() {
		verbesserungen = new Ausruestung[6];
	}

	public void init(int health, int ProduktionproMinute, int angriff,
			int reichweite, String name, Waren[] ben�tigt,
			int[] amountBen�tigt, Waren produziert) {
		this.health = health;
		this.ProduktionproMinute = ProduktionproMinute;
		this.angriff = angriff;
		this.reichweite = reichweite;
		this.name = name;

		this.ben�tigt = new Waren[ben�tigt.length];
		this.amountBen�tigt = new int[ben�tigt.length];

		for (int i = 0; i < ben�tigt.length; i++) {
			this.ben�tigt[i] = ben�tigt[i];
			this.amountBen�tigt[i] = amountBen�tigt[i];
		}
		this.produziert = produziert;
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
			System.out.println("Du kannst nichts abholen, oder dieses Geb�ude produziert nichts");
			return null;
		}
		return produziert;
	}

	public boolean setVerbesserung(int i, Ausruestung it) {
		if (it.f�rGeb�ude) {
			verbesserungen[i] = it;
			return true;
		}
		return false;

	}

	public void changeStats(Ausruestung a) {
		//int stats = new
	}

}
