package com.matze.knightmare.meshes;

import java.util.Timer;
import java.util.TimerTask;

import com.richard.knightmare.util.Pos;

public class Bauen {

	private static int amountBenötigt[];
	private static Waren benötigt[];
	
	public static Building KohleMine(Pos p, int w, int h){
		Building b = new Building(p, w, h, ".png");
		amountBenötigt = new int[1];
		b.init(50, 20, 0, 0, "Kohlemine", null, amountBenötigt, Rohstoffe.Kohle(), 25);
		return b;
	}
	
	public static Building EisenMine(Pos p, int w, int h){
		Building b = new Building(p, w, h, "Eisenerz 1.png");
		
		int am = 1;
		
		benötigt = new Waren[am];
		amountBenötigt = new int[am];
		
		benötigt[0] = Rohstoffe.Kohle();
		amountBenötigt[0] = 1;
		
		b.init(50, 20, 0, 0, "Kohlemine", benötigt, amountBenötigt, Rohstoffe.Eisen(), 100);
		
		Timer timer = new Timer(true);
		
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				if (benötigt[0].substractWare(amountBenötigt[0])){
					b.WareFertigstellen();	
				}
			}
			
		}, 0, (long) (60000/b.getProdperMin()));
		
	
		return b;
	}
	
	public static Building Lager(Pos p, int w, int h){
		Building b = new Building(p, w, h, "Eisenerz 1.png");
		
//		int am = 1;
//		
//		benötigt = new Waren[am];
//		amountBenötigt = new int[am];
//		
//		benötigt[0] = Rohstoffe.Kohle();
//		amountBenötigt[0] = 1;
		
		b.init(50, 20, 0, 0, "Kohlemine", benötigt, amountBenötigt, Rohstoffe.Eisen(), 1000);
		return b;
	}
	
}
