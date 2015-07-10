package com.matze.knightmare.meshes;

import java.util.ArrayList;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;

public class Forschung {

	private int kostet[];
	private int id;
	private double faktor;
	private ArrayList<RectangleGraphicalObject> bestimmt;
	
	public Forschung(int id, double faktor){
		kostet = new int[Rohstoffe.maxID()];
		this.id = id;
		this.setFaktor(faktor);
	}

	public int[] getKostet() {
		return kostet;
	}

	public void setKostet(int kostet[]) {
		this.kostet = kostet;
	}
	
	public int getKostet(int index) {
		return kostet[index];
	}

	public void setKostet(int index, int kostetR) {
		kostet[index] = kostetR;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<RectangleGraphicalObject> getBestimmt() {
		return bestimmt;
	}

	public void setBestimmt(RectangleGraphicalObject bestimmt) {
		this.bestimmt.add(bestimmt);
	}

	public double getFaktor() {
		return faktor;
	}

	public void setFaktor(double faktor2) {
		this.faktor = faktor2;
	}
	
	
	
}
