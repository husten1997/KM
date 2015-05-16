package com.richard.knightmare.util;

import java.awt.event.MouseEvent;

import com.richard.knightmare.buttons.Entity;

public class Map {

	private int resolution;
	private String[][] ground;
	private Entity[][] entities;
	
	public Map(int width, int height, int resolution){
		this.resolution = resolution;
		ground = new String[width][height];
		entities = new Entity[width][height];
	}
	
	public void setGround(int x, int y, String textur){
		ground[x][y] = textur;
	}
	
	public void setGround(String[][] ground){
		this.ground = ground;
	}
	
	public void setEntity(int x, int y, Entity entity){
		entities[x][y] = entity;
	}
	
	public String[][] getGround(){
		return ground;
	}
	
	public Entity[][] getEntities(){
		return entities;
	}
	
	public int getResolution(){
		return resolution;
	}
	
	public Entity getEntity(int x, int y){
		return entities[x][y];
	}
	
	public String getGround(int x, int y){
		return ground[x][y];
	}
	
	public void onClick(MouseEvent e){
		if(entities[e.getX()/resolution][e.getY()/resolution]!=null){
			entities[e.getX()/resolution][e.getY()/resolution].onClick();
		}
	}
}
