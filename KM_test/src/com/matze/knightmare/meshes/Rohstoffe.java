package com.matze.knightmare.meshes;

public class Rohstoffe {
	
	public static Waren Kohle(){
		Waren w = new Waren(0, 0, "Kohle", "kohle.png");
		return w;
	}
	
	public static Waren Eisen(){
		Waren w = new Waren(1, 0, "Eisen", "eisen.png");
		return w;
	}
	
	public static Waren Holz(){
		Waren w = new Waren(2, 0, "Holz", "baum.png");
		return w;
	}
	
	public static Waren Diamant(){
		Waren w = new Waren(3, 0, "Diamant", "dimant.png");
		return w;
	}
	
	public static Waren Pech(){
		Waren w = new Waren(4, 0, "Pech", "pech.png");
		return w;
	}
	
	public static Waren Sand(){
		Waren w = new Waren(5, 0, "Sand", "sand.png");
		return w;
	}
	
	public static Waren Getreide(){
		Waren w = new Waren(6, 0, "Getreide", "woaz.png");
		return w;
	}
	
	public static Waren Lehm(){
		Waren w = new Waren(7, 0, "Lehm", "Lehm.png");
		return w;
	}
	
	public static Waren Stein(){
		Waren w = new Waren(8, 0, "Stein", "stein.png");
		return w;
	}
	
	public static Waren Geld(){
		Waren w = new Waren(9,0,"Geld","muenze.png");
		return w;
	}
	
	public static Waren Fleisch(){
		Waren w = new Waren(10,0,"Fleisch","fleisch.png");
		return w;
	}
	
	public static Waren Glas(){
		Waren w = new Waren(11,0,"Glas","glas.png");
		return w;
	}
	
	public static Waren Mensch(){
		Waren w = new Waren(12,0,"Mensch", "manfred.png");
		//TODO mensch soll zum sammelpunkt gehen
		return w;
	}
	
	public static Waren Armbrust(){
		Waren w = new Waren(13,0,"Armbrust", "Armbrust.png");
		return w;
	}
	
	public static Waren Gebildeter_Mensch(){
		Waren w = new Waren(14,0,"Gebildeter", "people.png");
		return w;
	}
	
	public static Waren Ziegel(){
		Waren w = new Waren(15,0,"Ziegel", "zeigel.png");
		return w;
	}
	
	public static Waren Nothing(){
		return null;
	}
	
	public static Waren Rohstoff_von_Index(int index){
		switch(index){
		case 0:{return Kohle();}
		case 1:{return Eisen();}
		case 2:{return Holz();}
		case 3:{return Diamant();}
		case 4:{return Pech();}
		case 5:{return Sand();}
		case 6:{return Getreide();}
		case 7:{return Lehm();}
		case 8:{return Stein();}
		case 9:{return Geld();}
		case 10:{return Fleisch();}
		case 11:{return Glas();}
		case 12:{return Mensch();}
		case 13:{return Armbrust();}
		case 14:{return Gebildeter_Mensch();}
		case 15:{return Ziegel();}
		default:{return Nothing();}
		}
	}
	
	public static int maxID(){
			int a = 0;
			while(Rohstoffe.Rohstoff_von_Index(a)!=null){
				a++;
			}
			return a;
	}
	
	
}
