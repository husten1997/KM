package com.matze.knightmare.meshes;

public class Rohstoffe {

	public static Waren Kohle(){
		Waren w = new Waren(0, 0, "Kohle", "kohle.png");
		return w;
	}
	
	public static Waren Eisen(){
		Waren w = new Waren(1, 0, "Eisen", "eisen.png");
		return w;
	}
	
}
