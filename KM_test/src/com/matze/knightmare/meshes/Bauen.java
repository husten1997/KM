package com.matze.knightmare.meshes;

import java.util.Timer;
import java.util.TimerTask;

import com.richard.knightmare.util.Pos;

public class Bauen {

//	private static int amountBenötigt[];
//	private static Waren benötigt[];
	
	public static Building KohleMine(Pos p, String spieler, int team){
		Building b = new Building(0, p, 64, 64, "Kohlemine.png");
		Waren[] benötigt = new Waren[1];
		int[] amountBenötigt = new int[1];
		b.init(50, 20, 0, 0, "Kohlemine", benötigt, amountBenötigt, Rohstoffe.Kohle(), 25);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Kohle in der Nähe*/true) && (/*Kohle hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building EisenMine(Pos p, String spieler, int team){
		Building b = new Building(1, p, 64, 64, "Eisenerz 1.png");
		
		int am = 1;
		
		Waren[] benötigt = new Waren[am];
		int[] amountBenötigt = new int[am];
		
		benötigt[0] = Rohstoffe.Kohle();
		amountBenötigt[0] = 1;
		
		b.setTeam(team);
		b.setSpieler(spieler);
		
		b.init(50, 20, 0, 0, "Eisenmine", benötigt, amountBenötigt, Rohstoffe.Eisen(), 100);
		
		Timer timer = new Timer(true);
		
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if (benötigt[0].substractWare(amountBenötigt[0]) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
	
		return b;
	}
	
	public static Building Lager(Pos p, String spieler, int team){
		Building b = new Building(2, p, 64, 64, "Lager.png");
		
		b.setTeam(team);
		b.setSpieler(spieler);
		
		int am = Rohstoffe.maxID()+1; //TODO überprüfen
		
		Waren[] benötigt = new Waren[am];
		int[] amountBenötigt = new int[am];
		
		for (int i = 0; i < am; i++){
			benötigt[i] = Rohstoffe.Rohstoff_von_Index(i);
		}
		
		b.init(75, 0, 0, 0, "Lagerhaus", benötigt, amountBenötigt, null, 1000);
		
		return b;
	}
	
	public static Building Holzfäller(Pos p, String spieler, int team){
		Building b = new Building(3, p, 64, 64, "Holz.png");
		
		int am = 1;
		Waren[] benötigt = new Waren[am];
		int[] amountBenötigt = new int[am];
		
		benötigt[0] = Rohstoffe.Holz();
		amountBenötigt[0] = 0;
		
		b.setTeam(team);
		b.setSpieler(spieler);
		b.init(30, 5, 0, 0, "Holzfäller", benötigt, amountBenötigt, Rohstoffe.Holz(), 20);
		
		Timer timer = new Timer(true);

		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Baum in der Nähe*/true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building Haus(Pos p, String spieler, int team){
		Building b = new Building(4, p, 64, 32, "Haus.png");
		
		int am = 1;
		Waren[] benötigt = new Waren[am];
		int[] amountBenötigt = new int[am];
		
		benötigt[0] = Rohstoffe.Mensch();
		amountBenötigt[0] = 0;
		
		b.setTeam(team);
		b.setSpieler(spieler);
		b.init(30, 1, 0, 0, "Haus", benötigt, amountBenötigt, Rohstoffe.Mensch(), 8);
		
		Timer timer = new Timer(true);

		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				b.setProduktionProMinute(1);
				b.WareFertigstellen();
				//TODO ppm so ändern dass effektivität pro gebäude um 50% gesteigert wird, steht es alleine in einem bestimmten sektor, wird die produktion pro minute um 50% gesenkt (auch negativ möglich, dann sterben die leute)
			}
			
		}, 0, (long) (600000/b.getProdperMin()));
		
		return b;
	}
	
	
	public static Building Sandschmelze(Pos p, String spieler, int team){
		Building b = new Building(5, p, 64, 64, "Sandschmeiz.png");
		
		Waren[] benötigt = new Waren[1];
		int[] amountBenötigt = new int[1];
		
		benötigt[0] = Rohstoffe.Sand();
		amountBenötigt[0] = 5;
		
		b.init(50, 3, 0, 0, "Sandschmelze", benötigt, amountBenötigt, Rohstoffe.Glas(), 9);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if (benötigt[0].substractWare(amountBenötigt[0]) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building Bauernhof(Pos p, String spieler, int team){
		Building b = new Building(6, p, 128, 128, "Hof.png");
		
		Waren[] benötigt = new Waren[1];
		int[] amountBenötigt = new int[1];
		
		b.init(50, 4, 0, 0, "Bauernhof", benötigt, amountBenötigt, Rohstoffe.Getreide(), 36);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Felder in der Nähe*/true) && (/*Felder hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building Viehstall(Pos p, String spieler, int team){
		Building b = new Building(7, p, 64, 64, "viecha.png");
		
		Waren[] benötigt = new Waren[1];
		int[] amountBenötigt = new int[1];
		
		b.init(50, 5, 0, 0, "Viehstall", benötigt, amountBenötigt, Rohstoffe.Fleisch(), 25);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap())){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	
	public static Building Steinbruch(Pos p, String spieler, int team){
		Building b = new Building(8, p, 64, 64, "Steinbruch.png");
		
		Waren[] benötigt = new Waren[1];
		int[] amountBenötigt = new int[1];
		
		b.init(50, 15, 0, 0, "Steinbruch", benötigt, amountBenötigt, Rohstoffe.Fleisch(), 75);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Stein in der Nähe*/true) && (/*Stein hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building Turm(Pos p, String spieler, int team){
		Building b = new Building(9, p, 64, 64, "Turm.png");
		
		Waren[] benötigt = new Waren[1];
		int[] amountBenötigt = new int[1];
		
		b.init(1500, 0, 0, 0, "Turm", benötigt, amountBenötigt, Rohstoffe.Nothing(), 0);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Stein in der Nähe*/true) && (/*Stein hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building Mauern(Pos p, String spieler, int team){
		Building b = new Building(10, p, 64, 32, "Mauer.png");
		
		Waren[] benötigt = new Waren[1];
		int[] amountBenötigt = new int[1];
		
		b.init(1200, 0, 10, 70, "Mauern", benötigt, amountBenötigt, Rohstoffe.Nothing(), 0);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Stein in der Nähe*/true) && (/*Stein hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	
	
	
	public static Building getBuildingforID(int id, Pos p, String spieler, int team){
		switch (id){
		case 0: {return KohleMine(p, spieler, team);}
		case 1: {return EisenMine(p, spieler, team);}
		case 2: {return Lager(p, spieler, team);}
		case 3: {return Holzfäller(p, spieler, team);}
		case 4: {return Haus(p, spieler, team);}
		case 5: {return Sandschmelze(p, spieler, team);}
		case 6: {return Bauernhof(p, spieler, team);}
		case 7: {return Viehstall(p, spieler, team);}
		case 8: {return Steinbruch(p, spieler, team);}
		case 9: {return Turm(p, spieler, team);}
		case 10: {return Mauern(p, spieler, team);}
		default: return null;
		}
	}
	
	public static String getBuildingName(int id){
		switch (id) {
		case 0:
			return "Kohlemine";
		case 1:
			return "Eisenmine";
		case 2:
			return "Lager";
		case 3:
			return "Holzfäller";
		case 4:
			return "Haus";
		case 5:
			return "Sandschmelze";
		case 6:
			return "Bauernhof";
		case 7:
			return "Viehstall";
		case 8:
			return "Steinbruch";
		case 9:
			return "Turm";
		case 10:
			return "Mauern";
		default:
			return "TODO";
		}
	}
}
