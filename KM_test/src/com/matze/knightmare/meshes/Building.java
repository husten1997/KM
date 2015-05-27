package com.matze.knightmare.meshes;

import com.husten.knightmare.graphicalObjects.QUAD;
import com.richard.knightmare.util.Pos;

public class Building extends QUAD {

	public Building(Pos position, double width, double height, String textureName) {
		super(position, width, height, textureName);
		type = MeshType.GEBÄUDE;
	}

//	public Building(Pos position, TextureLoader loader, String textureName) {
//		super(position, loader, textureName);
//
//	}
//
//	public Building(Pos position, double scale, TextureLoader loader, String textureName) {
//		super(position, scale, loader, textureName);
//
//	}

}
