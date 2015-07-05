package com.matze.knightmare.meshes;

public class Spieler {

	private int amountResourcesOfIndex[] = new int [Rohstoffe.maxID()];
	private int team;
	private int index;
	private String name;
	
	public Spieler(int SpielerNR, String name, int team){
		this.setTeam(team);
		this.setName(name);
		this.index = SpielerNR;
		
		amountResourcesOfIndex[getIndexofResource("Holz")] = 20;
		amountResourcesOfIndex[getIndexofResource("Kohle")] = 5;
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
	
	
	
}
