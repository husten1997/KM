package com.matze.knightmare.meshes;

public class Rohstoffe {

	private static int amountResourcesOfIndex[] = new int [maxID()];
	
	public static Waren setAmountofResource(int amount, int index){
		amountResourcesOfIndex[index] = amount;
		return Rohstoff_von_Index(index);
	}
	
	public static Waren setAmountofResource(int amount, String name){
		int index = getIndexofResource(name);
		amountResourcesOfIndex[index] = amount;
		return Rohstoff_von_Index(index);
	}
	
	public static int getAmountofResource(int index){
		return amountResourcesOfIndex[index];
	}
	
	public static int getIndexofResource(String name){
		for (int i = 0; i < maxID(); i++){
			if (name.equals(Rohstoff_von_Index(i).getName())){
				return i;
			}
		}
		System.out.println(name + ": Resource nicht vorhanden");
		return -1;
	}
	
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
