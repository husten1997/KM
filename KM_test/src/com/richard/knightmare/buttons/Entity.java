package com.richard.knightmare.buttons;

public abstract class Entity extends Button{

	private String player;
	private int team;
	
	public Entity(String player, int team, String texur){
		super(texur);
		this.player = player;
		this.team = team;
	}
	
	public String getPlayer(){
		return player;
	}
	
	public int getTeam(){
		return team;
	}
	
	public abstract boolean isTroop();
}
