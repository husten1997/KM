package com.matze.knightmare.meshes;

import java.io.Serializable;
import java.util.ArrayList;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;

public class Forschung implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3594729536845339543L;
	private int kostet[];
	private int id;
	private ArrayList<Forschung> setztFVoraus;
	private ArrayList<RectangleGraphicalObject> setztGVoraus;
	private double faktor; // = timer beeinflussung schneller langsamer usw...
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

	public void setKostet(Waren ware, int kostetR) {
		kostet[ware.getID()] = kostetR;
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

	public ArrayList<Forschung> getSetztVoraus() {
		return setztFVoraus;
	}

	public void setSetztVoraus(ArrayList<Forschung> setztVoraus) {
		this.setztFVoraus = setztVoraus;
	}
	
	public void setSetztVoraus(Forschung setztVoraus) {
		this.setztFVoraus.add(setztVoraus);
	}
	
	public ArrayList<RectangleGraphicalObject> getSetztVorausGEb() {
		return setztGVoraus;
	}

	public void setSetztVorausGeb(ArrayList<RectangleGraphicalObject> setztVoraus) {
		this.setztGVoraus = setztVoraus;
	}
	
	public void setSetztVorausGeb(RectangleGraphicalObject setztVoraus) {
		this.setztGVoraus.add(setztVoraus);
	}
	
	
	
}
