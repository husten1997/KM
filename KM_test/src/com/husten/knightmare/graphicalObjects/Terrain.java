package com.husten.knightmare.graphicalObjects;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.worldGen.WorldGen;
import com.richard.knightmare.util.Pos;

public class Terrain extends gasset {
	private TerrainElement waterPlane, elements[][];
	TextureLoader textureLoader;
	// Achtung sind in World Units --> WU * 32 = PixelUnit
	private int Sx, Sy;
	private double x, y;

	public Terrain(TextureLoader tL) {
		textureLoader = tL;

		Sx = 100;
		Sy = 100;
		initTerrain(Sx, Sy);
	}

	public Terrain(TextureLoader tL, int x, int y) {
		textureLoader = tL;

		Sx = x;
		Sy = y;
		initTerrain(Sx, Sy);
	}

	public void initTerrain(int width, int height) {
		waterPlane = new TerrainElement(new Pos(0, 0), width * 32, height * 32, textureLoader, "water.png", Material.WATER);
		waterPlane.setTCX(width);
		waterPlane.setTCY(height);
		elements = new TerrainElement[width][height];
		for (int i = 0; i < width; i++) {
			for (int m = 0; m < height; m++) {
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
		for (int x1 = (int) (Knightmare.CameraX / 32); x1 < (int) ((Knightmare.CameraX + Knightmare.WIDTH * Knightmare.scale) / 32 + 4); x1++) {
			for (int y1 = (int) (Knightmare.CameraY / 32); y1 < (int) ((Knightmare.CameraY + Knightmare.HEIGHT * Knightmare.scale) / 32 + 4); y1++) {
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
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setX(double x) {
		this.x = x;

	}

	@Override
	public void setY(double y) {
		this.y = y;

	}

	public void transformXY(double x, double y) {

	}

	@Override
	public Pos getPos() {
		return null;
	}

	@Override
	public void setPos(Pos p) {
	}
	
	public String getMeterial(int x, int y){
		if(elements[x][y]==null){
			return null;
		}
		return elements[x][y].getType();
	}

}
