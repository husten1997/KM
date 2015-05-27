package com.husten.knightmare.graphicalObjects;

import com.husten.knightmare.worldGen.WorldGenerator;
import com.richard.knightmare.util.Pos;
import com.richard.knightmare.util.Test;

public class World extends GraphicalObject{

	private RectangleGraphicalObject waterPlane, elements[][];
	private int width, height;
	
	public World(int width, int height) {
		super(new Pos(0,0), MeshType.GROUND);
		this.width = width;
		this.height = height;
		waterPlane = new RectangleGraphicalObject(position, width*32, height*32, "water.png", false, Material.WATER);
		elements = new RectangleGraphicalObject[width][height];
		WorldGenerator WG = new WorldGenerator(elements, this);
		elements = WG.worldGen();
	}

	@Override
	public void draw() {
		waterPlane.draw();
		for (int x1 = (int) (Test.CameraX / 32); x1 < (int) ((Test.CameraX + Test.WIDTH * Test.scale) / 32 + 4); x1++) {
			for (int y1 = (int) (Test.CameraY / 32); y1 < (int) ((Test.CameraY + Test.HEIGHT * Test.scale) / 32 + 4); y1++) {
				try {
					elements[x1][y1].draw();
				} catch (Exception e) {

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

}