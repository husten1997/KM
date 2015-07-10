package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Forschen {

	public static Forschung DreiFelderWirtschaft(){
		Forschung f = new Forschung(0, 2);
		f.setBestimmt(Bauen.Bauernhof(new Pos(0,0), Bauen.getMutterNatur()));
		return f;
	}
	
}
