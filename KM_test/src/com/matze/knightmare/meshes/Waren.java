package com.matze.knightmare.meshes;

import java.awt.image.BufferedImage;

public class Waren {

	private int amount;
	private String typ;
	private BufferedImage textur;
	
	public Waren(int am, String typ, BufferedImage t){
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
	
	public boolean substractWare(){
		if (amount == 0){
			return false;
		}
		amount--;
		return true;
	}
	
}
