package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Kavallerie extends Soldat {

	protected boolean hatpferd;

	public Kavallerie(int h, int posx, int posy, int w, int he, String tex) {
		super(h, new Pos(posx, posy), w, he, tex);
		typ = 1;
		hatpferd = true;
	}

	public void setPferd(boolean x) {
		hatpferd = x;
	}

}
