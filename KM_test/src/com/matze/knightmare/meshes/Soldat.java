package com.matze.knightmare.meshes;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.graphicalObjects.QUAD;
import com.husten.knightmare.graphicalObjects.TextureLoader;

public class Soldat extends QUAD {
	private int health;
	private int speed = 5;
	//TODO Inventory
	public Soldat(float l, float h, float x, float y, TextureLoader loader, String ref) {
		super(l, h, x, y, loader, ref);
		type = StringConstants.MeshType.EINEHEIT;
	}
	
	public Soldat(float x, float y, TextureLoader loader, String ref) {
		super(x, y, loader, ref);
		
	}
	
	public Soldat(float x, float y,float cs, TextureLoader loader, String ref) {
		super(x, y, cs, loader, ref);
		
	}
	
	public void say(){
		System.out.println("Ich bin da");
	}
	
	public double getSpeed(){
		return speed;
	}
	
	
	
	

}
