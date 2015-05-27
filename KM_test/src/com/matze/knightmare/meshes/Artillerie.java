package com.matze.knightmare.meshes;

public class Artillerie extends Truppen {

	private int ben�tigteMann;
	private int mann;
	
	public Artillerie(int h){
		super(h);
		typ = 2;
	}
	
	public void setMann(int ma){
		ben�tigteMann = ma;
	}
	
	public boolean Mann_Beordern(Truppen t){
		
		if (mann == ben�tigteMann){
			t.stop();
			return true;
		}
		
		mann++;
		
		if (mann == ben�tigteMann){
			return true;
		}
		
		return false;
	}
	
	public boolean Mann_Abziehen(){
		mann--;
		return false;
	}
	
}
