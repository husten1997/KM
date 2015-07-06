package com.richard.knightmare.util;

public class Pos {

	private double x, y;

	public Pos(double x, double y) {
		this.x = x;
		this.y = y;

	}

	public double calcDist(double x, double y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setDX(double x){
		this.x += x;
	}
	
	public void setDY(double y){
		this.y += y;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getPos() {
		return "" + "y: " + y + " x: " + x;
	}

}
