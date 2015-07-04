package com.matze.knightmare.meshes;

import java.util.Timer;
import java.util.TimerTask;

import com.richard.knightmare.util.Pos;

public class Bauen {

//	private static int amountBen�tigt[];
//	private static Waren ben�tigt[];
	
	public static Building KohleMine(Pos p, int w, int h, String spieler, int team){
		Building b = new Building(0, p, w, h, "Kohlemine.png");
		Waren[] ben�tigt = new Waren[1];
		int[] amountBen�tigt = new int[1];
		b.init(50, 20, 0, 0, "Kohlemine", ben�tigt, amountBen�tigt, Rohstoffe.Kohle(), 25);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
					b.WareFertigstellen();
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
		return b;
	}
	
	public static Building EisenMine(Pos p, int w, int h, String spieler, int team){
		Building b = new Building(1, p, w, h, "Eisenerz 1.png");
		
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
				if (ben�tigt[0].substractWare(amountBen�tigt[0])){
					b.WareFertigstellen();
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
	
		return b;
	}
	
	public static Building Lager(Pos p, int w, int h, String spieler, int team){
		Building b = new Building(2, p, w, h, "Eisenerz 1.png");
		
		b.setTeam(team);
		b.setSpieler(spieler);
		
		int am = Rohstoffe.maxID()+1; //TODO �berpr�fen
		
		Waren[] ben�tigt = new Waren[am];
		int[] amountBen�tigt = new int[am];
		
		for (int i = 0; i < am; i++){
			ben�tigt[i] = Rohstoffe.Rohstoff_von_Index(i);
		}
		
		b.init(75, 0, 0, 0, "Lagerhaus", ben�tigt, amountBen�tigt, null, 1000);
		b.setTeam(team);
		b.setSpieler(spieler);
		
		return b;
	}
	
	public static Building getBuildingforID(int id, Pos p, int w, int h, String spieler, int team){
		switch (id){
		case 0: {return KohleMine(p, w, h, spieler, team);}
		case 1: {return EisenMine(p, w, h, spieler, team);}
		case 2: {return Lager(p, w, h, spieler, team);}
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
		default:
			return "TODO";
		}
	}
}
