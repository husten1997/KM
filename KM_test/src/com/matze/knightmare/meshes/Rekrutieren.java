package com.matze.knightmare.meshes;

public class Rekrutieren {

	private Truppen t;
	private int ang[],ver[];
	
	public Rekrutieren(){
		ang = ver = new int[3];
	}
	
	public void init_Array(int a0, int a1, int a2, int v0, int v1, int v2){
		ang[0] = a0;
		ang[1] = a1;
		ang[2] = a2;
		ver[0] = v0;
		ver[1] = v1;
		ver[2] = v2;
	}
	
	public Truppen Bogenschuetze(){
		t = new Infanterie();
		init_Array(5,25,0,4,7,1);
		t.init(ang, ver, 12, "Bogenschütze", 50, 20, 15, 25, 50, false);
		return t;
	}
	
	public Truppen Ritter(){
		t = new Infanterie();
		init_Array(25,0,0,15,15,5);
		t.init(ang, ver, 15, "Ritter", 5, 15, 25, 30, 15, false);
		return t;
	}
	
	public Truppen Kolbentraeger(){
		t = new Infanterie();
		init_Array(20, 0, 0, 10, 8, 1);
		t.init(ang, ver, 10, "Kolbenträger", 5, 23, 18, 23, 40, false);
		return t;
	}
	
	public Truppen Armbrustschuetze(){
		t = new Infanterie();
		return t;
	}
	
	public Truppen Speertraeger(){
		t = new Infanterie();
		return t;
	}
	
	public Truppen Pikeniere(){
		t = new Infanterie();
		return t;
	}
	
	public Truppen Phalanx(){
		t = new Infanterie();
		return t;
	}
	
	public Truppen Leichte_Reiter(){
		t = new Kavallerie();
		return t;
	}
	
	public Truppen Hussaren(){
		t = new Kavallerie();
		return t;
	}
	
	public Truppen Schwere_Reiter(){
		t = new Kavallerie();
		return t;
	}
	
	public Truppen Leichte_Fernkampf_Reiter(){
		t = new Kavallerie();
		return t;
	}
	
	public Truppen Schwere_Fernkampf_Reiter(){
		t = new Kavallerie();
		return t;
	}
	
	public Truppen Kanone(){
		t = new Artillerie();
		return t;
	}
	
	public Truppen Leiter(){
		t = new Artillerie();
		return t;
	}
	
	public Truppen Katapult(){
		t = new Artillerie();
		return t;
	}
	
	public Truppen Rammbock(){
		t = new Artillerie();
		return t;
	}
	
	public Truppen Schild(){
		t = new Artillerie();
		return t;
	}
	
	public Truppen Baliste(){
		t = new Artillerie();
		return t;
	}
	
	public Truppen Tribok(){
		t = new Artillerie();
		return t;
	}
	
	public Truppen Leitern(){
		t = new Artillerie();
		return t;
	}
	
}
