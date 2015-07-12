package com.matze.knightmare.meshes;

import java.io.Serializable;

public class Waren extends Items implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2446031232468137386L;
	private int amount;
	private String typ;
	private String textur;
	private int alter, maxAnz;
	
	public Waren(int id, int am, String typ, String t){
		super(id);
		amount = am;
		this.typ = typ;
		textur = t;
	}
	
	public boolean addWare(int maxLagerKapazität){
		if (maxLagerKapazität == amount){
			return false;
		}
		amount++;
		return true;
	}
	
	public boolean substractWare(int abzug){
		if (amount-abzug < 0){
			return false;
		}
		amount -= abzug;
		return true;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void setAmount(int am){
		amount = am;
	}
	
	public String getTextur(){
		return textur;
	}
	
	public String getName(){
		return typ;
	}

	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		this.alter = alter;
	}

	public int getMaxAnz() {
		return maxAnz;
	}

	public void setMaxAnz(int maxAnz) {
		this.maxAnz = maxAnz;
	}
	
}
