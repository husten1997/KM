package com.richard.knightmare.serial;

public class Zeit {

	private double time;
	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getJahr() {
		return jahr;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}

	public int getMonat() {
		return monat;
	}

	public void setMonat(int monat) {
		this.monat = monat;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	private int jahr, monat, tag;
	
	public Zeit(int jahr, int monat, int tag, double time){
		this.jahr = jahr;
		this.monat = monat;
		this.tag = tag;
		this.time = time;
	}
}
