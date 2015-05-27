package com.matze.knightmare.meshes;

public class Kavallerie extends Truppen {

	protected boolean hatpferd;

	public Kavallerie(int h) {
		super(h);
		typ = 1;
		hatpferd = true;
	}

	public void setPferd(boolean x) {
		hatpferd = x;
	}

}
