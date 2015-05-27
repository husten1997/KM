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
		Infanterie t = new Infanterie();
		init_Array(5,25,0,4,7,1);
		t.init(ang, ver, 12, "Bogenschütze", 73, 20, 15, 25, 50, false);
		return t;
	}
	
	public Truppen Ritter(){
		Infanterie t = new Infanterie();
		init_Array(25,0,0,15,15,5);
		t.init(ang, ver, 15, "Ritter", 32, 15, 25, 30, 15, false);
		return t;
	}
	
	public Truppen Kolbentraeger(){
		Infanterie t = new Infanterie();
		init_Array(20, 0, 0, 10, 8, 1);
		t.init(ang, ver, 10, "Kolbenträger", 32, 23, 18, 23, 40, false);
		return t;
	}
	
	public Truppen Armbrustschuetze(){
		Infanterie t = new Infanterie();
		init_Array(5, 25, 0, 10, 15, 0);
		t.init(ang, ver, 20, "Armbrustschütze", 90, 20, 30, 30, 45, false);
		return t;
	}
	
	public Truppen Speertraeger(){
		Infanterie t = new Infanterie();
		init_Array(15, 0, 0, 15, 5, 2);
		t.init(ang, ver, 40, "Speerträger", 32, 10, 12, 20, 20, false);
		return t;
	}
	
	public Truppen Pikeniere(){
		Infanterie t = new Infanterie();
		init_Array(15, 0, 0, 20, 15, 10);
		t.init(ang, ver, 25, "Pikeniere", 32, 12, 25, 20, 30, false);
		return t;
	}
	
	public Truppen Phalanx(){
		Infanterie t = new Infanterie();
		init_Array(10, 0, 0, 5, 5, 0);
		t.init(ang, ver, 50, "Phalanx", 32, 24, 18, 24, 30, false);
		return t;
	}
	
	
	//Kavallerie:
	
	public Truppen Leichte_Reiter(){
		Kavallerie t = new Kavallerie();
		init_Array(25, 0, 0, 10, 8, 2);
		t.init(ang, ver, 30, "Leichte Reiterei", 32, 28, 30, 40, 50, false);
		return t;
	}
	
	public Truppen Schwere_Reiter(){
		Kavallerie t = new Kavallerie();
		init_Array(35, 0, 0, 15, 13, 5);
		t.init(ang, ver, 50, "Schwere Reiterei", 32, 28, 40, 50, 40, false);
		return t;
	}
	
	public Truppen Leichte_Lanz_Reiter(){
		Kavallerie t = new Kavallerie();
		init_Array(20, 0, 0, 5, 5, 0);
		t.init(ang, ver, 20, "Leichte Lanzreiter", 40, 40, 25, 20, 50, false);
		return t;
	}
	
	public Truppen Schwere_Lanz_Reiter(){
		Kavallerie t = new Kavallerie();
		init_Array(30, 0, 0, 15, 10, 5);
		t.init(ang, ver, 24, "Schwere Lanzreiter", 42, 38, 30, 23, 40, false);
		return t;
	}
	
	public Truppen Hussaren(){
		Kavallerie t = new Kavallerie();
		init_Array(40, 0, 0, 20, 20, 15);
		t.init(ang, ver, 35, "Huassaren", 40, 35, 50, 40, 40, false);
		return t;
	}
	
	public Truppen Leichte_Fernkampf_Reiter(){
		Kavallerie t = new Kavallerie();
		init_Array(8, 30, 0, 5, 8, 0);
		t.init(ang, ver, 15, "Berittener Bogenschütze", 70, 30, 35, 25, 30, false);
		return t;
	}
	
	public Truppen Schwere_Fernkampf_Reiter(){
		Kavallerie t = new Kavallerie();
		init_Array(12, 40, 0, 15, 14, 0);
		t.init(ang, ver, 25, "Schwere berittene Bogenschützen", 80, 28, 40, 30, 35, false);
		return t;
	}
	
	
	//Artillerie:
	
	public Truppen Kanone(){
		Artillerie t = new Artillerie();
		init_Array(5, 80, 60, 8, 10, 2);
		t.init(ang, ver, 40, "Kanone", 200, 5, 150, 30, -20, false);
		return t;
	}
	
	public Truppen Leiter(){
		Artillerie t = new Artillerie();
		init_Array(1, 0, 0, 1, 1, 1);
		t.init(ang, ver, 0, "Leiterträger", 32, 60, 5, 10, -20, false);
		return t;
	}
	
	public Truppen Katapult(){
		Artillerie t = new Artillerie();
		init_Array(0, 30, 50, 10, 5, 15);
		t.init(ang, ver, 20, "Katapult", 350, 15, 80, 0, -5, false);
		return t;
	}
	
	public Truppen Rammbock(){
		Artillerie t = new Artillerie();
		init_Array(0, 0, 40, 10, 30, 20);
		t.init(ang, ver, 5, "Rammbock", 40, 20, 60, 0, -5, false);
		return t;
	}
	
	public Truppen Schild(){
		Artillerie t = new Artillerie();
		init_Array(0, 0, 0, 20, 60, 20);
		t.init(ang, ver, 0, "Schild", 0, 30, 50, 4, -2, false);
		return t;
	}
	
	public Truppen Baliste(){
		Artillerie t = new Artillerie();
		init_Array(10, 40, 0, 2, 15, 10);
		t.init(ang, ver, 10, "Baliste", 380, 30, 80, 10, -10, false);
		return t;
	}
	
	public Truppen Tribok(){
		Artillerie t = new Artillerie();
		init_Array(10, 90, 90, 10, 14, 10);
		t.init(ang, ver, 2, "Tribok", 800, 10, 200, 30, -20, false);
		return t;
	}
	
	public Truppen Leitern(){
		Artillerie t = new Artillerie();
		return t;
	}
	
	
	//Fahrzeuge:
	
	public Truppen Handelsschiff(){
		Vehicle t = new Vehicle();
		init_Array(0, 0, 0, 40, 40, 20);
		t.init(ang, ver, 0, "Handelsschiff", 0, 60, 120, 20, 100, true);
		t.setSlots(4, 20, true, true);
		return t;
	}
	
	public Truppen Grosses_Handelsschiff(){
		Vehicle t = new Vehicle();
		init_Array(0, 0, 0, 40, 40, 20);
		t.init(ang, ver, 0, "Handelsschiff", 0, 50, 150, 20, 100, true);
		t.setSlots(4, 40, true, true);
		return t;
	}
	
	public Truppen Bewaffnetes_Handelschiff(){
		Vehicle t = new Vehicle();
		init_Array(15, 60, 12, 15, 20, 10);
		t.init(ang, ver, 10, "Bewaffnetes Handelsschiff", 400, 130, 180, 40, 100, true);
		t.setSlots(3, 25, true, true);
		return t;
	}
	
	public Truppen Kleines_Kriegsschiff(){
		Vehicle t = new Vehicle();
		init_Array(30, 120, 25, 25, 30, 20);
		t.init(ang, ver, 15, "Kleines Kriegsschiff", 600, 100, 180, 60, 100, true);
		t.setSlots(2, 10, true, true);
		return t;
	}
	
	public Truppen Großes_Kriegsschiff(){
		Vehicle t = new Vehicle();
		init_Array(60, 240, 50, 40, 50, 40);
		t.init(ang, ver, 30, "Großes Kriegsschiff", 800, 80, 240, 70, 100, true);
		t.setSlots(2, 15, true, true);
		return t;
	}
	
	public Truppen Kutsche(){
		Vehicle t = new Vehicle();
		init_Array(0, 0, 0, 10, 10, 5);
		t.init(ang, ver, 0, "Kutsche", 0, 50, 50, 0, 60, false);
		t.setSlots(2, 10, false, true);
		return t;
	}
	
	public Truppen Handelskarren(){
		Vehicle t = new Vehicle();
		init_Array(0, 0, 0, 7, 7, 0);
		t.init(ang, ver, 0, "Hanndelskarren", 0, 20, 10, 0, 10, false);
		t.setSlots(2, 12, true, false);
		return t;
	}
	
	public Truppen Transportkarren(){
		Vehicle t = new Vehicle();
		init_Array(0, 0, 0, 5, 5, 0);
		t.init(ang, ver, 0, "Transportkarren", 0, 20, 15, 10, 30, false);
		t.setSlots(2, 20, true, false);
		return t;
	}
	
}
