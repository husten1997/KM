package com.matze.knightmare.meshes;

import com.husten.knightmare.graphicalObjects.QUAD;
import com.richard.knightmare.util.Pos;

public class Soldat extends QUAD {
	private int speed = 5;

	// TODO Inventory
	public Soldat(Pos position, double width, double height, String textureName) {
		super(position, width, height, textureName);
		type = MeshType.EINEHEIT;
	}

//	public Soldat(Pos position, TextureLoader loader, String ref) {
//		super(position, loader, ref);
//	}
//
//	public Soldat(Pos position, double scale, TextureLoader loader, String textureName) {
//		super(position, scale, loader, textureName);
//	}

	public void say() {
		System.out.println("Ich bin da");
	}

	public double getSpeed() {
		return speed;
	}

}
