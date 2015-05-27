package com.matze.knightmare.meshes;

public class Artillerie extends Truppen {

	private int benötigteMann;
	private int mann;
	
	public Artillerie(int h){
		super(h);
		typ = 2;
	}
	
	public void setMann(int ma){
		benötigteMann = ma;
	}
	
	public boolean Mann_Beordern(Truppen t){
		
		if (mann == benötigteMann){
			t.stop();
			return true;
		}
		
		mann++;
		
		if (mann == benötigteMann){
			return true;
		}
		
		return false;
	}
	
	public boolean Mann_Abziehen(){
		mann--;
		return false;
	}
	
}
