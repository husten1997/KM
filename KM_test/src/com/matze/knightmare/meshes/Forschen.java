package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Forschen {
	
	private static Pos p 		= new Pos(0,0);
	private static Spieler sp 	= Bauen.getMutterNatur();

	public static Forschung DreiFelderWirtschaft(){
		Forschung f = new Forschung(0, 2);
		f.setBestimmt(Bauen.Bauernhof(p,sp));
		f.setKostet(Rohstoffe.Holz(), 15);
		f.setKostet(Rohstoffe.Getreide(), 20);
		f.setSetztVorausGeb(Bauen.Bauernhof(p, sp));
		f.setSetztVoraus(Landwirtschaft());
		return f;
	}
	
	public static Forschung Schifffahrt(){
		Forschung f = new Forschung(1, 2);
		f.setBestimmt(Bauen.Hafen(p, sp));
		f.setKostet(Rohstoffe.Holz(), 15);
		f.setKostet(Rohstoffe.Stein(), 25);
		f.setSetztVorausGeb(Bauen.Hafen(p, sp));
		return f;
	}
	
	public static Forschung Schwarzpulver(){
		Forschung f = new Forschung(2, 2);
		f.setBestimmt(Bauen.Kaserne(p,sp));
		f.setKostet(Rohstoffe.Pech(), 50);
		f.setKostet(Rohstoffe.Sand(), 150);
		f.setKostet(Rohstoffe.Sand(), 30);
		f.setSetztVorausGeb(Bauen.Kaserne(p, sp));
		f.setSetztVorausGeb(Bauen.Schmied(p, sp));
		f.setSetztVoraus(Schifffahrt());
		return f;
	}
	
	public static Forschung Belagerungsgeräte(){
		Forschung f = new Forschung(3, 2);
		//TODO f.setBestimmt(Bauen.Belagerungscamp(p,sp));
		f.setKostet(Rohstoffe.Holz(), 10);
		f.setKostet(Rohstoffe.Stein(), 10);
		f.setKostet(Rohstoffe.Werkzeug(), 10);
		f.setSetztVorausGeb(Bauen.Kaserne(p, sp));
		return f;
	}
	
	public static Forschung Landwirtschaft(){
		Forschung f = new Forschung(4, 2);
		f.setBestimmt(Bauen.Bauernhof(p,sp));
		f.setKostet(Rohstoffe.Holz(), 10);
		f.setKostet(Rohstoffe.Getreide(), 10);
		return f;
	}
	
	public static Forschung getForschungOfID(int id){
		switch(id){
		case 0: return DreiFelderWirtschaft();
		case 1: return Schifffahrt();
		case 2: return Schwarzpulver();
		case 3: return Belagerungsgeräte();
		case 4: return Landwirtschaft();
		default: return null;
		}
	}
	
}
