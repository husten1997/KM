package com.husten.knightmare.graphicalObjects;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.worldGen.WorldGenerator;
import com.richard.knightmare.util.Pos;

public class Terrain extends GraphicalObject{

	private RectangleGraphicalObject waterPlane, elements[][];
	private int width, height;
	
	public Terrain(int width, int height) {
		super(new Pos(0,0), MeshType.GROUND);
		this.width = width;
		this.height = height;
		waterPlane = new RectangleGraphicalObject(position, width*32, height*32, "water.png", false, Material.WATER);
		waterPlane.setStrached(false);
		elements = new RectangleGraphicalObject[width][height];
		WorldGenerator WG = new WorldGenerator(elements, this);
		elements = WG.worldGen();
	}

	@Override
	public void draw() {
		waterPlane.draw();
		for (int x1 = (int) (Knightmare.CameraX / 32); x1 < (int) ((Knightmare.CameraX + Knightmare.WIDTH * Knightmare.scale) / 32 + 4); x1++) {
			for (int y1 = (int) (Knightmare.CameraY / 32); y1 < (int) ((Knightmare.CameraY + Knightmare.HEIGHT * Knightmare.scale) / 32 + 4); y1++) {
				if(x1<elements.length){
					if(y1<elements[x1].length){
						if(elements[x1][y1]!=null){
							elements[x1][y1].draw();
						}
					}
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