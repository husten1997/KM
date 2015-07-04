package com.husten.knightmare.graphicalObjects;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.worldGen.WorldGenerator;
import com.richard.knightmare.util.Pos;

public class Terrain extends GraphicalObject{

	private TerrainElement waterPlane, elements[][];
	private int width, height;
	
	/**
	 * @var Variable für Render distance (wie viele felder preloded werden)
	 * 		Jeh höher der wert desto schlechter die Performance
	 */
	private int renderD = 4;
	
	
	public Terrain(int width, int height) {
		super(new Pos(0,0), MeshType.GROUND);
		this.width = width;
		this.height = height;
		
		elements = new TerrainElement[width][height];
		WorldGenerator WG = new WorldGenerator(elements, width, height);
		elements = WG.worldGen();
		waterPlane = new TerrainElement(position,(float) WG.getLW(), width*32, height*32, "water.png", Material_t.WATER);
		waterPlane.setStrached(false);
		
		
	}

	@Override
	public void draw() {
		waterPlane.draw();
		int sp_x = (int) (Knightmare.CameraX / 32);
		int sp_y = (int) (Knightmare.CameraY / 32);
		if(sp_x - renderD >= 0){
			sp_x -= renderD;
		}
		if(sp_y -renderD >= 0){
			sp_y -= renderD;
		}
		int ep_x = (int) ((Knightmare.CameraX + Knightmare.WIDTH * Knightmare.scale) / 32 + renderD);
		int ep_y = (int) ((Knightmare.CameraY + Knightmare.HEIGHT * Knightmare.scale) / 32 + renderD);
		
		for (int x1 = sp_x; x1 < ep_x; x1++) {
			for (int y1 = sp_y; y1 < ep_y; y1++) {
				try{
					if(elements[x1][y1]!=null){
						elements[x1][y1].draw();
					}
				}catch(Exception e){
					//Scheiß doch draf i schreib koe 100 Ifs des is a nimma performant
				}
			}
		}
	}
	
	
	public String getMeterial(int x, int y){
		if(elements[x][y]==null){
			return null;
		}
		return elements[x][y].getType();
	}
	
	public TerrainElement getElement(int x, int y){
		return elements[x][y];
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void initRender() {
		waterPlane.initRender();
		for(int i = 0; i<elements.length; i++){
			for(int j = 0; j<elements[i].length; j++){
				if(elements[i][j]!=null){
					elements[i][j].initRender();
				}
			}
		}
	}

}
