package com.richard.knightmare.util;

import java.io.Serializable;

public class Pos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2766801952370805318L;
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
