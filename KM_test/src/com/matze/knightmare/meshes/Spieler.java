package com.matze.knightmare.meshes;

public class Spieler {

	private int amountResourcesOfIndex[] = new int [Rohstoffe.maxID()];
	private int team;
	private int index;
	private String typ;
	private int difficulty;
	private String name;
	
	public Spieler(int SpielerNR, String name, int team, String typ, String schwierigkeit){
		this.setTeam(team);
		this.setName(name);
		this.index = SpielerNR;
		this.setTyp(typ);
		
		for (int i = 0; i < 3; i++){
			if (schwierigkeit.equals("Leicht")){
				setDifficulty(0);
			}
			if (schwierigkeit.equals("Mittel")){
				setDifficulty(1);
			}
			if (schwierigkeit.equals("Schwer")){
				setDifficulty(2);
			}
		}
		
		amountResourcesOfIndex[getIndexofResource("Holz")] = 30;
		amountResourcesOfIndex[getIndexofResource("Kohle")] = 5;
		amountResourcesOfIndex[getIndexofResource("Stein")] = 15;
	}
	
	
	//Rohstoffe
	public Waren setAmountofResourcewithIndex(int amount, int index){
		amountResourcesOfIndex[index] = amount;
		return Rohstoffe.Rohstoff_von_Index(index);
	}
	
	public Waren setAmountofResourcewithName(int amount, String name){
		int index = getIndexofResource(name);
		amountResourcesOfIndex[index] = amount;
		return Rohstoffe.Rohstoff_von_Index(index);
	}
	
	public int getAmountofResource(int index){
		return amountResourcesOfIndex[index];
	}
	
	public int getIndexofResource(String name){
		for (int i = 0; i < Rohstoffe.maxID(); i++){
			if (name.equals(Rohstoffe.Rohstoff_von_Index(i).getName())){
				return i;
			}
		}
		System.out.println(name + ": Resource nicht vorhanden");
		return -1;
	}


	//getter und setter
	public int getTeam() {
		return team;
	}


	public void setTeam(int team) {
		this.team = team;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public int getIndex(){
		return index;
	}


	public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}


	public String getTyp() {
		return typ;
	}


	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	
	
}
