package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Artillerie extends Soldat {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303156782095984835L;
	private int ben�tigteMann;
	private int mann;
	
	public Artillerie(int h, int posx, int posy, int w, int he, String tex) {
		super(h, new Pos(posx, posy), w, he, tex);
		typ = 2;
	}
	
	public void setMann(int ma){
		ben�tigteMann = ma;
	}
	
	public boolean Mann_Beordern(Soldat t){
		
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
