package com.husten.knightmare.graphicalObjects;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.core.KTM_Game_Main;
import com.husten.knightmare.worldGen.WorldGen;
import com.richard.knightmare.util.Pos;

public class Terrain extends gasset implements StringConstants{
	TerrainElement waterPlane;
	TerrainElement elements[][];
	TextureLoader textureLoader;
	//Achtung sind in World Units --> WU * 32 = PixelUnit
	int Sx; 
	int Sy;
	float xPos;
	float yPos;
	
	public Terrain(TextureLoader tL){
		textureLoader = tL;
		
		Sx = 100;
		Sy = 100;
		initTerrain(Sx, Sy);
	}
	
	public Terrain(TextureLoader tL, int x, int y){
		textureLoader = tL;
		
		Sx = x;
		Sy = y;
		initTerrain(Sx, Sy);
	}
	
	public void initTerrain(int x, int y){
		waterPlane = new TerrainElement(x*32, y*32, 0, 0, textureLoader, "water.png", Material.WATER);
		waterPlane.setTCX(x);
    	waterPlane.setTCY(y);
		elements = new TerrainElement[x][y];
		for(int i = 0; i < x; i++){
			for(int m = 0; m < y; m++){
				elements[i][m] = null;
			}
		}
		WorldGen WG = new WorldGen(elements, this);
		elements = WG.worldGen();
	}
	

	public int getSx() {
		return Sx;
	}

	public void setSx(int sx) {
		Sx = sx;
	}

	public int getSy() {
		return Sy;
	}

	public void setSy(int sy) {
		Sy = sy;
	}

	public TextureLoader getTextureLoader() {
		return textureLoader;
	}

	@Override
	public void draw() {
		waterPlane.draw();
		for(int x1 = (int)(KTM_Game_Main.CameraX/32); x1 < (int)((KTM_Game_Main.CameraX+KTM_Game_Main.WIDTH*KTM_Game_Main.scale)/32+4); x1++){
			for(int y1 = (int)(KTM_Game_Main.CameraY/32); y1 < (int)((KTM_Game_Main.CameraY+KTM_Game_Main.HEIGHT*KTM_Game_Main.scale)/32+4); y1++){
				try {
					elements[x1][y1].draw();
				} catch (Exception e) {

				}
			}
		}
	}

	@Override
	public void draw2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return xPos;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return yPos;
	}

	@Override
	public void setX(float x) {
		xPos = x;
		
	}

	@Override
	public void setY(float y) {
		yPos = y;
		
	}
	
	public void transformXY(float x, float y){
		
	}

	@Override
	public Pos getPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPos(Pos p) {
		// TODO Auto-generated method stub
		
	}

}
