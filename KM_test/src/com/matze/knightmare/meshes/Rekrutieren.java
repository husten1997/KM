package com.matze.knightmare.meshes;

public class Rekrutieren {

	private static int ang[] = new int[3], ver[] = new int[3];

	private static void init_Array(int a0, int a1, int a2, int v0, int v1, int v2) {
		ang[0] = a0;
		ang[1] = a1;
		ang[2] = a2;
		ver[0] = v0;
		ver[1] = v1;
		ver[2] = v2;
	}

	// Infanterie:
	
		//Typ: 0: Infanterie
		//Typ: 1: Fernkampfinfanterie
	 	//Typ: 2: Artillerie
		//Typ: 3: Kavallerie
		//Typ: 4: Fahrzeuge

	public static Soldat Bogenschuetze(int posx, int posy, int w, int he, String spieler, int team) {
		Infanterie t = new Infanterie(20, posx, posy, w, he, "Bogenschütze.png");
		init_Array(5, 25, 0, 4, 7, 1);
		t.init(ang, ver, 12, "Bogenschütze", 73, 20, 15, 25, 50, false);
		t.setTyp(1);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Abgesessener_Ritter(int posx, int posy, int w, int he, String spieler, int team) {
		Infanterie t = new Infanterie(40, posx, posy, w, he, "Abgesessener Ritter.png");
		init_Array(25, 0, 0, 15, 15, 5);
		t.init(ang, ver, 15, "Abgesessener Ritter", 32, 15, 25, 30, 15, false);
		t.setTyp(0);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Kolbentraeger(int posx, int posy, int w, int he, String spieler, int team) {
		Infanterie t = new Infanterie(25, posx, posy, w, he, "Kolbenträger.png");
		init_Array(20, 0, 0, 10, 8, 1);
		t.init(ang, ver, 10, "Kolbenträger", 32, 23, 18, 23, 40, false);
		t.setTyp(0);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Armbrustschuetze(int posx, int posy, int w, int he, String spieler, int team) {
		Infanterie t = new Infanterie(28, posx, posy, w, he, "Armbrustschütze.png");
		init_Array(5, 25, 0, 10, 15, 0);
		t.init(ang, ver, 20, "Armbrustschütze", 90, 20, 30, 30, 45, false);
		t.setTyp(1);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Speertraeger(int posx, int posy, int w, int he, String spieler, int team) {
		Infanterie t = new Infanterie(30, posx, posy, w, he, "Speerträger.png");
		init_Array(15, 0, 0, 15, 5, 2);
		t.init(ang, ver, 40, "Speerträger", 32, 10, 12, 20, 20, false);
		t.setTyp(0);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Pikenier(int posx, int posy, int w, int he, String spieler, int team) {
		Infanterie t = new Infanterie(45, posx, posy, w, he, "Pikenier.png");
		init_Array(15, 0, 0, 20, 15, 10);
		t.init(ang, ver, 25, "Pikenier", 32, 12, 25, 20, 30, false);
		t.setTyp(0);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Hoplit(int posx, int posy, int w, int he, String spieler, int team) {
		Infanterie t = new Infanterie(38, posx, posy, w, he, "Hoplit.png");
		init_Array(10, 0, 0, 5, 5, 0);
		t.init(ang, ver, 50, "Hoplit", 32, 24, 18, 24, 30, false);
		t.setTyp(0);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	// Kavallerie:

	public static Soldat Leichter_Reiter(int posx, int posy, int w, int he, String spieler, int team) {
		Kavallerie t = new Kavallerie(45, posx, posy, w, he, "Leichter Reiter.png");
		init_Array(25, 0, 0, 10, 8, 2);
		t.init(ang, ver, 30, "Leichter Reiter", 32, 28, 30, 40, 50, false);
		t.setTyp(3);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Schwerer_Reiter(int posx, int posy, int w, int he, String spieler, int team) {
		Kavallerie t = new Kavallerie(60, posx, posy, w, he, "Schwerer Reiter.png");
		init_Array(35, 0, 0, 15, 13, 5);
		t.init(ang, ver, 50, "Schwerer Reiter", 32, 28, 40, 50, 40, false);
		t.setTyp(3);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Leichter_Lanz_Reiter(int posx, int posy, int w, int he, String spieler, int team) {
		Kavallerie t = new Kavallerie(40, posx, posy, w, he, "Leichter Lanzreiter.png");
		init_Array(20, 0, 0, 5, 5, 0);
		t.init(ang, ver, 20, "Leichter Lanzreiter", 40, 40, 25, 20, 50, false);
		t.setTyp(3);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Schwerer_Lanz_Reiter(int posx, int posy, int w, int he, String spieler, int team) {
		Kavallerie t = new Kavallerie(50, posx, posy, w, he, "Schwerer Lanzreiter.png");
		init_Array(30, 0, 0, 15, 10, 5);
		t.init(ang, ver, 24, "Schwerer Lanzreiter", 42, 38, 30, 23, 40, false);
		t.setTyp(3);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Hussar(int posx, int posy, int w, int he, String spieler, int team) {
		Kavallerie t = new Kavallerie(60, posx, posy, w, he, "Hussar.png");
		init_Array(40, 0, 0, 20, 20, 15);
		t.init(ang, ver, 35, "Hussar", 40, 35, 50, 40, 40, false);
		t.setTyp(3);
		return t;
	}

	public static Soldat Leichter_Fernkampf_Reiter(int posx, int posy, int w, int he, String spieler, int team) {
		Kavallerie t = new Kavallerie(35, posx, posy, w, he, "Berittener Bogenschütze.png");
		init_Array(8, 30, 0, 5, 8, 0);
		t.init(ang, ver, 15, "Berittener Bogenschütze", 70, 30, 35, 25, 30, false);
		t.setTyp(3);
		return t;
	}

	public static Soldat Schwerer_Fernkampf_Reiter(int posx, int posy, int w, int he, String spieler, int team) {
		Kavallerie t = new Kavallerie(45, posx, posy, w, he, "Schwerer berittener Bogenschütze.png");
		init_Array(12, 40, 0, 15, 14, 0);
		t.init(ang, ver, 25, "Schwerer berittener Bogenschütze", 80, 28, 40, 30, 35, false);
		t.setTyp(3);
		return t;
	}

	// Artillerie:

	public static Soldat Kanone(int posx, int posy, int w, int he, String spieler, int team) {
		Artillerie t = new Artillerie(50, posx, posy, w, he, "Kanone.png");
		init_Array(5, 80, 60, 8, 10, 2);
		t.init(ang, ver, 40, "Kanone", 200, 5, 150, 30, -20, false);
		t.setMann(6);
		t.setTyp(2);
		return t;
	}

	public static Soldat Leiter(int posx, int posy, int w, int he, String spieler, int team) {
		Artillerie t = new Artillerie(10, posx, posy, w, he, "Leiternträger.png");
		init_Array(1, 0, 0, 1, 1, 1);
		t.init(ang, ver, 0, "Leiternträger", 32, 60, 5, 10, -20, false);
		t.setMann(1);
		t.setTyp(2);
		return t;
	}

	public static Soldat Katapult(int posx, int posy, int w, int he, String spieler, int team) {
		Artillerie t = new Artillerie(22, posx, posy, w, he, "Katapult.png");
		init_Array(0, 30, 50, 10, 5, 15);
		t.init(ang, ver, 20, "Katapult", 350, 15, 80, 0, -5, false);
		t.setMann(4);
		t.setTyp(2);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Rammbock(int posx, int posy, int w, int he, String spieler, int team) {
		Artillerie t = new Artillerie(28, posx, posy, w, he, "Rammbock.png");
		init_Array(0, 0, 40, 10, 30, 20);
		t.init(ang, ver, 5, "Rammbock", 40, 20, 60, 0, -5, false);
		t.setMann(4);
		t.setTyp(2);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Schild(int posx, int posy, int w, int he, String spieler, int team) {
		Artillerie t = new Artillerie(70, posx, posy, w, he, "Schild.png");
		init_Array(0, 0, 0, 20, 60, 20);
		t.init(ang, ver, 0, "Schild", 0, 30, 50, 4, -2, false);
		t.setMann(1);
		t.setTyp(2);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Baliste(int posx, int posy, int w, int he, String spieler, int team) {
		Artillerie t = new Artillerie(45, posx, posy, w, he, "Baliste.png");
		init_Array(10, 40, 0, 2, 15, 10);
		t.init(ang, ver, 10, "Baliste", 380, 30, 80, 10, -10, false);
		t.setMann(4);
		t.setTyp(2);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Tribok(int posx, int posy, int w, int he, String spieler, int team) {
		Artillerie t = new Artillerie(70, posx, posy, w, he, "Tribok.png");
		init_Array(10, 90, 90, 10, 14, 10);
		t.init(ang, ver, 2, "Tribok", 800, 10, 200, 30, -20, false);
		t.setMann(8);
		t.setTyp(2);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	// Fahrzeuge:

	public static Soldat Kleines_Handelsschiff(int posx, int posy, int width, int height, String spieler, int team) {
		Vehicle t = new Vehicle(250, posx, posy, width, height, "Handelsschiff.png");
		init_Array(0, 0, 0, 40, 40, 20);
		t.init(ang, ver, 0, "Handelsschiff", 0, 60, 120, 20, 100, true);
		t.setSlots(4, 20, true, true);
		t.setTyp(4);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Großes_Handelsschiff(int posx, int posy, int width, int height, String spieler, int team) {
		Vehicle t = new Vehicle(500, posx, posy, width, height, "Großes Handelsschiff.png");
		init_Array(0, 0, 0, 40, 40, 20);
		t.init(ang, ver, 0, "Großes Handelsschiff", 0, 50, 150, 20, 100, true);
		t.setSlots(4, 40, true, true);
		t.setTyp(4);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Bewaffnetes_Handelsschiff(int posx, int posy, int width, int height, String spieler, int team) {
		Vehicle t = new Vehicle(650, posx, posy, width, height, "Bewaffnetes Handelsschiff.png");
		init_Array(15, 60, 12, 15, 20, 10);
		t.init(ang, ver, 10, "Bewaffnetes Handelsschiff", 400, 130, 180, 40, 100, true);
		t.setSlots(3, 25, true, true);
		t.setTyp(4);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Kleines_Kriegsschiff(int posx, int posy, int width, int height, String spieler, int team) {
		Vehicle t = new Vehicle(750, posx, posy, width, height, "Kleines Kriegsschiff.png");
		init_Array(30, 120, 25, 25, 30, 20);
		t.init(ang, ver, 15, "Kleines Kriegsschiff", 600, 100, 180, 60, 100, true);
		t.setSlots(2, 10, true, true);
		t.setTyp(4);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Großes_Kriegsschiff(int posx, int posy, int width, int height, String spieler, int team) {
		Vehicle t = new Vehicle(900, posx, posy, width, height, "Großes Kriegsschiff.png");
		init_Array(60, 240, 50, 40, 50, 40);
		t.init(ang, ver, 30, "Großes Kriegsschiff", 800, 80, 240, 70, 100, true);
		t.setSlots(2, 15, true, true);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Kutsche(int posx, int posy, int width, int height, String spieler, int team) {
		Vehicle t = new Vehicle(100, posx, posy, width, height, "Kutsche.png");
		init_Array(0, 0, 0, 10, 10, 5);
		t.init(ang, ver, 0, "Kutsche", 0, 50, 50, 0, 60, false);
		t.setSlots(2, 10, false, true);
		t.setTyp(4);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Handelskarren(int posx, int posy, int width, int height, String spieler, int team) {
		Vehicle t = new Vehicle(30, posx, posy, width, height, "Hanndelskarren.png");
		init_Array(0, 0, 0, 7, 7, 0);
		t.init(ang, ver, 0, "Hanndelskarren", 0, 20, 10, 0, 10, false);
		t.setSlots(2, 12, true, false);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

	public static Soldat Transportkarren(int posx, int posy, int width, int height, String spieler, int team) {
		Vehicle t = new Vehicle(20, posx, posy, width, height, "Transportkarren.png");
		init_Array(0, 0, 0, 5, 5, 0);
		t.init(ang, ver, 0, "Transportkarren", 0, 20, 15, 10, 30, false);
		t.setSlots(2, 20, true, false);
		t.setTyp(4);
		t.setTeam(team);
		t.setSpieler(spieler);
		return t;
	}

}
