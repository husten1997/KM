package com.matze.knightmare;

public class Truppen{

	protected int[] angriff; //[0] entspricht nahkampf [1] entspricht fernkampf, [2] entspricht gegen Gebäude;
	protected int[] verteidigung; //[0] entspricht nahkampf [1] entspricht fernkampf, [3] entspricht gegen Artillerie;
	protected String name;
	protected int reichweite;
	protected int geschwindigkeit;
	protected int kosten;
	protected Ausruestung ausruestung[];
	//protected sound s;
	
	public Truppen(){
		angriff = new int[3];
		verteidigung = new int[3];
		ausruestung = new Ausruestung[8];
	}
	
	public void verbesserung(int[] ang, int[] ver, int reichw, int geschw){
		
		for (int i = 0; i < angriff.length; i++){
			angriff[i] += ang[i];
			verteidigung[i] += ver[i]; 
		}
		
		reichweite += reichw;
		geschwindigkeit += geschw;
	}
	
	public void init(int[] ang, int[] ver, String nam, int reichw, int geschw, int kost){
		for (int i = 0; i < 2; i++){
			angriff[i] += ang[i];
			verteidigung[i] += ver[i]; 
		}
		
		reichweite += reichw;
		geschwindigkeit += geschw;
		name = nam;
		kosten = kost;
	}
	
	public void setAusruestung(int i, Ausruestung aus){
		ausruestung[i] = aus;
		changeStats(i);
	}
	
	private void changeStats(int a){
		int[] stats = ausruestung[a].getCS();
			for(int i = 0; i < 3; i++){
				angriff[i] += stats[i];
				verteidigung[i+3] += stats[i+3]; 
			}
		reichweite += stats[6];
		geschwindigkeit += stats[7];
		kosten += stats[8]; 
		
	}
	
}
