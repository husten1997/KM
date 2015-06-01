package com.matze.knightmare.meshes;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Soldat extends RectangleGraphicalObject {
	protected int[] angriff, verteidigung; // [0] entspricht nahkampf [1]
	// entspricht fernkampf, [2]
	// entspricht gegen Gebäude;
	// [0] entspricht nahkampf [1] entspricht fernkampf, [2] entspricht gegen
	// Artillerie;
	protected int bonusAng, reichweite, grundmoral, moral, ausdauer,
			geschwindigkeit, kosten;
	protected String name;
	protected int effektiv, typ;
	protected Ausruestung ausruestung[];
	protected boolean wasser;
	protected int health;

	// TODO Inventory
	public Soldat(int h, Pos position, int width, int height, String textureName) {
		super(position, width, height, textureName, false);
		type = MeshType.EINHEIT;
		angriff = new int[3];
		verteidigung = new int[3];
		ausruestung = new Ausruestung[8];
		health = h;
	}

	public void verbesserung(int[] ang, int[] ver, int b, int reichw,
			int geschw, int mor, int aus) {

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

	public void init(int[] ang, int[] ver, int b, String nam, int reichw,
			int geschw, int kost, int mo, int aus, boolean water) {
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
		wasser = water;
	}
	
	public int getTyp(){
		return typ;
	}
	
	public int getEffektiv() {
		return effektiv;
	}

	public int ausdauerBerechnen(int a, int einheitenFreundlich,
			int einheitenFeindlich) { // int
										// a
										// entspricht
										// der
										// ausdauer
										// die
										// zuvor
										// returnt
										// wird
		ausdauer = a
				* (1 + (moralBerechnen(einheitenFreundlich, einheitenFeindlich) / 100));
		if (ausdauer < 10)
			ausdauer = 10;
		return ausdauer;
	}

	public int moralBerechnen(int einheitenFreundlich, int einheitenFeindlich) {
		moral = grundmoral
				* (1 + ((einheitenFreundlich - einheitenFeindlich) / 100));
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
	
		int[] stats = new int[12];
		
		for (int i = 0; i < stats.length; i++){
			stats[i] = ausruestung[a].getCS(i);
		}
		
		for (int i = 0; i < 3; i++) {
			angriff[i] += stats[i];
			verteidigung[i] += stats[i + 3];
		}
		
		reichweite += stats[6];
		geschwindigkeit += stats[7];
		kosten += stats[8];
		bonusAng += stats[9];
		ausdauer += stats[10];
		grundmoral += stats[11];

	}
	
	//TODO Methode schreiben
	public void stop(){
		
	}

	public void say() {
		System.out.println("Ich bin da");
	}

	public double getSpeed() {
		return geschwindigkeit;
	}

}
