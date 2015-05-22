package com.matze.knightmare;

public class Armee {

	private Truppen t[];
	private int anz;
	
	public Armee(){
		t = new Truppen[100];
		anz = 0;
	}
	
	public void addTroop(Truppen tr){
		t[anz] = tr;
		anz++;
	}
	
	public void fight(Armee a){
		
	}
	
}
