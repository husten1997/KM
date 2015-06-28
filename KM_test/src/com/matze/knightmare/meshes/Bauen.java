package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Bauen {

	private static int amountBenötigt[];
	private static Waren benötigt[];
	
	public static Building KohleMine(Pos p, int w, int h){
		Building b = new Building(p, w, h, ".png");
		amountBenötigt = new int[1];
		b.init(50, 20, 0, 0, "Kohlemine", null, amountBenötigt, Rohstoffe.Kohle(), 25);
		return b;
	}
	
	public static Building EisenMine(Pos p, int w, int h){
		Building b = new Building(p, w, h, "Eisenerz 1.png");
		
		int am = 1;
		
		benötigt = new Waren[am];
		amountBenötigt = new int[am];
		
		benötigt[0] = Rohstoffe.Kohle();
		amountBenötigt[0] = 1;
		
		b.init(50, 20, 0, 0, "Kohlemine", benötigt, amountBenötigt, Rohstoffe.Eisen(), 25);
		return b;
	}
	
}
