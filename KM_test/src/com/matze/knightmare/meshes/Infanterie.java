package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Infanterie extends Soldat {

	public Infanterie(int h, int posx, int posy, int w, int he, String tex) {
		super(h, new Pos(posx,posy), w, he, tex);
		typ = 0;
	}

}
