package com.husten.knightmare.graphicalObjects;

import com.richard.knightmare.util.Pos;
import org.lwjgl.util.vector.Vector3f;;

public class TerrainElement extends RectangleGraphicalObject {
	
	//Nicht löschen ist wichtig für die verwaltung von zb Ertzen!!
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4213324615954665305L;
	Vector3f position;
	String Material;
	
	public TerrainElement(Pos pos, float z, int w, int h, String textureName, String material) {
		super(pos, w, h, textureName, true, material);
		position = new Vector3f((float) pos.getX(), (float) pos.getY(), z);
		
		this.Material = material;
		
	}
	
	public TerrainElement(Pos pos, float z, String textureName, String material) {
		super(pos, 32, 32, textureName, true, material);
		position = new Vector3f((float) pos.getX(), (float) pos.getY(), z);
		
		this.Material = material;
		
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getY(){
		return position.y;
	}
	
	public float getZ(){
		return position.y;
	}
	
	public String getMaterial(){
		return material;
		
	}

}
