package com.matze.knightmare.meshes;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Soldat extends RectangleGraphicalObject {
	private int speed = 5;

	// TODO Inventory
	public Soldat(Pos position, int width, int height, String textureName) {
		super(position, width, height, textureName, false);
		type = MeshType.EINEHEIT;
	}

	public void say() {
		System.out.println("Ich bin da");
	}

	public double getSpeed() {
		return speed;
	}

}
