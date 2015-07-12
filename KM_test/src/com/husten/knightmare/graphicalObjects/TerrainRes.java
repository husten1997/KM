package com.husten.knightmare.graphicalObjects;

import com.richard.knightmare.util.Pos;

public class TerrainRes extends TerrainElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6438720815677305464L;
	int ammount;
	public TerrainRes(Pos pos, float z, String textureName, String material, int ammount) {
		super(pos, z, textureName, material);
		this.ammount = ammount;
	}
	
	public int getAmmount(){
		return ammount;
	}

}
