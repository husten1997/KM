package com.husten.knightmare.graphicalObjects;

import com.husten.knightmare.constants.StringConstants;
import com.richard.knightmare.util.Pos;

public abstract class GraphicalObject implements StringConstants{

	protected int sort = 1;
	protected String type;
	protected Pos position;

	
	public GraphicalObject(Pos position, String type){
		this.position = position;
		this.type = type;
	}
	
	public abstract void draw();
	
	public void setPosition(Pos position){
		this.position = position;
	}
	
	public Pos getPosition(){
		return position;
	}
	
	public void setSort(int sort){
		this.sort = sort;
	}
	
	public int getSort(){
		return sort;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
