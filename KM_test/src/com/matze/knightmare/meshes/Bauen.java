package com.matze.knightmare.meshes;

import java.util.Timer;
import java.util.TimerTask;

import com.richard.knightmare.util.Pos;

public class Bauen {

	private static int amountBen�tigt[];
	private static Waren ben�tigt[];
	
	public static Building KohleMine(Pos p, int w, int h){
		Building b = new Building(p, w, h, ".png");
		amountBen�tigt = new int[1];
		b.init(50, 20, 0, 0, "Kohlemine", null, amountBen�tigt, Rohstoffe.Kohle(), 25);
		return b;
	}
	
	public static Building EisenMine(Pos p, int w, int h){
		Building b = new Building(p, w, h, "Eisenerz 1.png");
		
		int am = 1;
		
		ben�tigt = new Waren[am];
		amountBen�tigt = new int[am];
		
		ben�tigt[0] = Rohstoffe.Kohle();
		amountBen�tigt[0] = 1;
		
		b.init(50, 20, 0, 0, "Kohlemine", ben�tigt, amountBen�tigt, Rohstoffe.Eisen(), 100);
		
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
	
	public static Building Lager(Pos p, int w, int h){
		Building b = new Building(p, w, h, "Eisenerz 1.png");
		
//		int am = 1;
//		
//		ben�tigt = new Waren[am];
//		amountBen�tigt = new int[am];
//		
//		ben�tigt[0] = Rohstoffe.Kohle();
//		amountBen�tigt[0] = 1;
		
		b.init(50, 20, 0, 0, "Kohlemine", ben�tigt, amountBen�tigt, Rohstoffe.Eisen(), 1000);
		return b;
	}
	
}
