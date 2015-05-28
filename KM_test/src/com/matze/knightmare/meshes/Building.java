package com.matze.knightmare.meshes;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Building extends RectangleGraphicalObject {

	public Building(Pos position, int width, int height, String textureName) {
		super(position, width, height, textureName, false);
		type = MeshType.GEBÄUDE;
	}

}
