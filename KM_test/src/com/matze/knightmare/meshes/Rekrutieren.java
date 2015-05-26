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
	
	
	//Infanterie:
	
	public Truppen Bogenschuetze(){
		t = new Infanterie();
		init_Array(5,25,0,4,7,1);
		t.init(ang, ver, 12, "Bogenschütze", 73, 20, 15, 25, 50, false);
		return t;
	}
	
	public Truppen Ritter(){
		t = new Infanterie();
		init_Array(25,0,0,15,15,5);
		t.init(ang, ver, 15, "Ritter", 32, 15, 25, 30, 15, false);
		return t;
	}
	
	public Truppen Kolbentraeger(){
		t = new Infanterie();
		init_Array(20, 0, 0, 10, 8, 1);
		t.init(ang, ver, 10, "Kolbenträger", 32, 23, 18, 23, 40, false);
		return t;
	}
	
	public Truppen Armbrustschuetze(){
		t = new Infanterie();
		init_Array(5, 25, 0, 10, 15, 0);
		t.init(ang, ver, 20, "Armbrustschütze", 90, 20, 30, 30, 45, false);
		return t;
	}
	
	public Truppen Speertraeger(){
		t = new Infanterie();
		init_Array(15, 0, 0, 15, 5, 2);
		t.init(ang, ver, 40, "Speerträger", 32, 10, 12, 20, 20, false);
		return t;
	}
	
	public Truppen Pikeniere(){
		t = new Infanterie();
		init_Array(15, 0, 0, 20, 15, 10);
		t.init(ang, ver, 25, "Pikeniere", 32, 12, 25, 20, 30, false);
		return t;
	}
	
	public Truppen Phalanx(){
		t = new Infanterie();
		init_Array(10, 0, 0, 5, 5, 0);
		t.init(ang, ver, 50, "Phalanx", 32, 24, 18, 24, 30, false);
		return t;
	}
	
	
	//Kavallerie:
	
	public Truppen Leichte_Reiter(){
		t = new Kavallerie();
		init_Array(25, 0, 0, 10, 8, 2);
		t.init(ang, ver, 30, "Leichte Reiterei", 32, 28, 30, 40, 50, false);
		return t;
	}
	
	public Truppen Schwere_Reiter(){
		t = new Kavallerie();
		init_Array(35, 0, 0, 15, 13, 5);
		t.init(ang, ver, 50, "Schwere Reiterei", 32, 28, 40, 50, 40, false);
		return t;
	}
	
	public Truppen Leichte_Lanz_Reiter(){
		t = new Kavallerie();
		init_Array(20, 0, 0, 5, 5, 0);
		t.init(ang, ver, 20, "Leichte Lanzreiter", 40, 40, 25, 20, 50, false);
		return t;
	}
	
	public Truppen Schwere_Lanz_Reiter(){
		t = new Kavallerie();
		init_Array(30, 0, 0, 15, 10, 5);
		t.init(ang, ver, 24, "Schwere Lanzreiter", 42, 38, 30, 23, 40, false);
		return t;
	}
	
	public Truppen Hussaren(){
		t = new Kavallerie();
		init_Array(40, 0, 0, 20, 20, 15);
		t.init(ang, ver, 35, "Huassaren", 40, 35, 50, 40, 40, false);
		return t;
	}
	
	public Truppen Leichte_Fernkampf_Reiter(){
		t = new Kavallerie();
		init_Array(8, 30, 0, 5, 8, 0);
		t.init(ang, ver, 15, "Berittener Bogenschütze", 70, 30, 35, 25, 30, false);
		return t;
	}
	
	public Truppen Schwere_Fernkampf_Reiter(){
		t = new Kavallerie();
		init_Array(12, 40, 0, 15, 14, 0);
		t.init(ang, ver, 25, "Schwere berittene Bogenschützen", 80, 28, 40, 30, 35, false);
		return t;
	}
	
	
	//Artillerie:
	
	public Truppen Kanone(){
		t = new Artillerie();
		init_Array(5, 80, 60, 8, 10, 2);
		t.init(ang, ver, 40, "Kanone", 200, 5, 150, 30, 20, false);
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
	
	
	//Fahrzeuge:
	
	public Truppen Handelsschiff(){
		t = new Vehicle();
		return t;
	}
	
	public Truppen Großes_Kriegsschiff(){
		t = new Vehicle();
		return t;
	}
	
	public Truppen Kleines_Kriegsschiff(){
		t = new Vehicle();
		return t;
	}
	
	public Truppen Bewaffnetes_Handelschiff(){
		t = new Vehicle();
		return t;
	}
	
	public Truppen Kutsche(){
		t = new Vehicle();
		return t;
	}
	
	public Truppen Handelskarren(){
		t = new Vehicle();
		return t;
	}
	
	public Truppen Transportkarren(){
		t = new Vehicle();
		return t;
	}
	
}
