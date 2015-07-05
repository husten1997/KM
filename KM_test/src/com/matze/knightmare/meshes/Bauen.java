package com.matze.knightmare.meshes;

import java.util.Timer;
import java.util.TimerTask;

import com.richard.knightmare.util.Pos;

public class Bauen {

//	private static int amountBen�tigt[];
//	private static Waren ben�tigt[];
	
	public static Building KohleMine(Pos p, String spieler, int team){
		Building b = new Building(0, p, 64, 64, "Kohlemine.png");
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		b.init(50, 20, 0, 0, "Kohlemine", ben�tigt, amountBen�tigt, Rohstoffe.Kohle(), 25);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Kohle in der N�he*/true) && (/*Kohle hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building EisenMine(Pos p, String spieler, int team){
		Building b = new Building(1, p, 64, 64, "Eisenerz 1.png");
		
		int am = 1;
		
		Waren[] ben�tigt = new Waren[am];
		int[] amountBen�tigt = new int[am];
		
		ben�tigt[0] = Rohstoffe.Kohle();
		amountBen�tigt[0] = 1;
		
		b.setTeam(team);
		b.setSpieler(spieler);
		
		b.init(50, 20, 0, 0, "Eisenmine", ben�tigt, amountBen�tigt, Rohstoffe.Eisen(), 100);
		
		Timer timer = new Timer(true);
		
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if (ben�tigt[0].substractWare(amountBen�tigt[0]) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
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
		
		int am = Rohstoffe.maxID()+1; //TODO �berpr�fen
		
		Waren[] ben�tigt = new Waren[am];
		int[] amountBen�tigt = new int[am];
		
		for (int i = 0; i < am; i++){
			ben�tigt[i] = Rohstoffe.Rohstoff_von_Index(i);
		}
		
		b.init(75, 0, 0, 0, "Lagerhaus", ben�tigt, amountBen�tigt, null, 1000);
		
		return b;
	}
	
	public static Building Holzf�ller(Pos p, String spieler, int team){
		Building b = new Building(3, p, 64, 64, "Holz.png");
		
		int am = 1;
		Waren[] ben�tigt = new Waren[am];
		int[] amountBen�tigt = new int[am];
		
		ben�tigt[0] = Rohstoffe.Holz();
		amountBen�tigt[0] = 0;
		
		b.setTeam(team);
		b.setSpieler(spieler);
		b.init(30, 10, 0, 0, "Holzf�ller", ben�tigt, amountBen�tigt, Rohstoffe.Holz(), 20);
		
		Timer timer = new Timer(true);

		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Baum in der N�he*/true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building Haus(Pos p, String spieler, int team){
		Building b = new Building(4, p, 64, 32, "Haus.png");
		
		int am = 1;
		Waren[] ben�tigt = new Waren[am];
		int[] amountBen�tigt = new int[am];
		
		ben�tigt[0] = Rohstoffe.Mensch();
		amountBen�tigt[0] = 0;
		
		b.setTeam(team);
		b.setSpieler(spieler);
		b.init(30, 10, 0, 0, "Haus", ben�tigt, amountBen�tigt, Rohstoffe.Mensch(), 0);
		
		Timer timer = new Timer(true);

		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				b.setProduktionProMinute(1);
				b.WareFertigstellen();
				//TODO ppm so �ndern dass effektivit�t pro geb�ude um 50% gesteigert wird, steht es alleine in einem bestimmten sektor, wird die produktion pro minute um 50% gesenkt (auch negativ m�glich, dann sterben die leute)
			}
			
		}, 0, (long) (600000/b.getProdperMin()));
		
		return b;
	}
	
	
	public static Building Sandschmelze(Pos p, String spieler, int team){
		Building b = new Building(5, p, 64, 32, "Sandschmeiz.png");
		
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		
		b.init(50, 20, 0, 0, "Sandschmelze", ben�tigt, amountBen�tigt, Rohstoffe.Sand(), 10);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Sand in der N�he*/true) && (/*Sand hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building Bauernhof(Pos p, String spieler, int team){
		Building b = new Building(6, p, 64, 32, "Hof.png");
		
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		
		b.init(50, 20, 0, 0, "Bauernhof", ben�tigt, amountBen�tigt, Rohstoffe.Getreide(), 5);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Felder in der N�he*/true) && (/*Felder hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building Viehstall(Pos p, String spieler, int team){
		Building b = new Building(7, p, 64, 32, "viecha.png");
		
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		
		b.init(50, 20, 0, 0, "Viehstall", ben�tigt, amountBen�tigt, Rohstoffe.Fleisch(), 5);
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
		Building b = new Building(8, p, 64, 32, "Steinbruch.png");
		
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		
		b.init(50, 20, 0, 0, "Steinbruch", ben�tigt, amountBen�tigt, Rohstoffe.Fleisch(), 5);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if ((/* Stein in der N�he*/true) && (/*Stein hat ressourcen*/ true) && (!(b.getAmountProduzierterWareAuslesen() == b.getMaxLagerKap()))){
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
		case 3: {return Holzf�ller(p, spieler, team);}
		case 4: {return Haus(p, spieler, team);}
		case 5: {return Sandschmelze(p, spieler, team);}
		case 6: {return Bauernhof(p, spieler, team);}
		case 7: {return Viehstall(p, spieler, team);}
		case 8: {return Steinbruch(p, spieler, team);}
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
			return "Holzf�ller";
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
		default:
			return "TODO";
		}
	}
}
