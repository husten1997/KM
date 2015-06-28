package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Bauen {

	private static int amountBen�tigt[];
	private static Waren ben�tigt[];
	
	public static Building KohleMine(Pos p, int w, int h){
		Building b = new Building(p, w, h, ".png");
		amountBen�tigt = new int[1];
		b.init(50, 20, 0, 0, "Kohlemine", null, amountBen�tigt, Rohstoffe.Kohle(), 25);
		return b;
	}
	
	public static Building EisenMine(Pos p, int w, int h){
		Building b = new Building(p, w, h, "Eisenerz 1.png");
		
		int am = 1;
		
		ben�tigt = new Waren[am];
		amountBen�tigt = new int[am];
		
		ben�tigt[0] = Rohstoffe.Kohle();
		amountBen�tigt[0] = 1;
		
		b.init(50, 20, 0, 0, "Kohlemine", ben�tigt, amountBen�tigt, Rohstoffe.Eisen(), 25);
		return b;
	}
	
}
