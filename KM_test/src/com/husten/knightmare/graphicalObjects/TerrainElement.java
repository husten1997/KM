package com.husten.knightmare.graphicalObjects;

import com.richard.knightmare.util.Pos;

public class TerrainElement extends QUAD {
	String type;

	public TerrainElement(Pos position, double width, double height, TextureLoader loader, String textureName, String Type) {
		super(position, width, height, loader, textureName);
		type = Type;
	}

	public TerrainElement(Pos postition, TextureLoader loader, String textureName, String Type) {
		super(postition, loader, textureName);
		type = Type;
	}

	public TerrainElement(Pos postition, double cs, TextureLoader loader, String textureName, String Type) {
		super(postition, cs, loader, textureName);
		type = Type;

	}

	public void setType(String Type) {
		type = Type;
	}

	public String getType() {
		return type;
	}

}
