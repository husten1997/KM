package com.matze.knightmare.meshes;

import java.awt.image.BufferedImage;

public class Waren extends Items{

	private int amount;
	private String typ;
	private String textur;
	
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
	
}
