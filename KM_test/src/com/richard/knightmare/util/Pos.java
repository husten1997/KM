package com.richard.knightmare.util;

public class Pos {
	
	double xPos;
	double yPos;

	public Pos(double x, double y){
		xPos = x;
		yPos = y;
		
	}

	public double calcDist(double x, double y){
		double x1 = Math.pow(x - xPos, 2);
		double y1 = Math.pow(y - yPos, 2);
		double xy = x1 + y1;
		return Math.sqrt(xy);
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	
	public String getPos(){
		return "" + "y: " + yPos + " x: " + xPos;
	}

	

}
