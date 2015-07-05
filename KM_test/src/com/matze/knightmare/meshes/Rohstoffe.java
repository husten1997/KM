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
	
	public static Waren Mensch(){
		Waren w = new Waren(9,0,"Mensch", "manfred.png");
		//TODO mensch soll zum sammelpunkt gehen
		return w;
	}
	
	public static Waren Geld(){
		Waren w = new Waren(10,0,"Geld","muenze.png");
		return w;
	}
	
	public static Waren Fleisch(){
		Waren w = new Waren(11,0,"Fleisch","fleisch.png");
		return w;
	}
	
	public static Waren Glas(){
		Waren w = new Waren(12,0,"Glas","glas.png");
		return w;
	}
	
	public static Waren Nothing(){
		return null;
	}
	
	public static Waren Rohstoff_von_Index(int index){
		Waren w = null;
		switch(index){
		case 0:{ w = Kohle(); break;}
		case 1:{ w = Eisen(); break;}
		case 2:{ w = Holz(); break;}
		case 3:{ w = Diamant(); break;}
		case 4:{ w = Pech(); break;}
		case 5:{ w = Sand(); break;}
		case 6:{ w = Getreide(); break;}
		case 7:{ w = Lehm(); break;}
		case 8:{ w = Stein(); break;}
		case 9:{ w = Mensch(); break;}
		default: {w = null; break;}
		}
		return w;
	}
	
	public static int maxID(){
			int a = 0;
			while(Rohstoffe.Rohstoff_von_Index(a)!=null){
				a++;
			}
			return a;
	}
	
	
}
