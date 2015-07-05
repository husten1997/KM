package com.matze.knightmare.meshes;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Building extends RectangleGraphicalObject {
	
	private Spieler s;
	
	private int health;
	private int index;
	private int ProduktionproMinute;
	private int angriff, reichweite;
	private String name;
	private Waren[] ben�tigt;
	private int[] amountBen�tigt;
	private Waren produziert;
	private int amountProduzierteWare;
	private Ausruestung[] verbesserungen;
	private int maxLagerKapazit�t;

	
	public Building(int index, Pos position, int width, int height, String textureName) {
		super(position, width, height, textureName, false);
		this.setIndex(index);
		type = MeshType.GEB�UDE;
		verbesserungen = new Ausruestung[6];
	}

	public void init(int health, int ProduktionproMinute, int angriff,
			int reichweite, String name, Waren[] ben�tigt,
			int[] amountBen�tigt, Waren produziert, int maxLagerKap) {
		this.setHealth(health);
		this.ProduktionproMinute = ProduktionproMinute;
		this.setAngriff(angriff);
		this.setReichweite(reichweite);
		this.setName(name);

		this.ben�tigt = new Waren[ben�tigt.length];
		this.amountBen�tigt = new int[ben�tigt.length];

		for (int i = 0; i < ben�tigt.length; i++) {
			this.ben�tigt[i] = ben�tigt[i];
			this.amountBen�tigt[i] = amountBen�tigt[i];
		}
		this.produziert = produziert;
		this.maxLagerKapazit�t = maxLagerKap;
	}

	public void WareFertigstellen() {
		amountProduzierteWare++;
	}
	
	public void setWarenAmount(int i, int kap){
		for (int b = 0; b < kap; b++){
			ben�tigt[i].addWare(maxLagerKapazit�t);
		}
	}

	public int getAmountProduzierterWare() {
		int hilfe = amountProduzierteWare;
		amountProduzierteWare = 0;
		return hilfe;
	}
	
	public int getAmountProduzierterWareAuslesen() {
		return amountProduzierteWare;
	}

	public Waren getProduzierteWare() {
		if (amountProduzierteWare == 0 || produziert == null) {
			System.out.println("Du kannst nichts abholen, oder dieses Geb�ude produziert nichts");
			return null;
		}
		return produziert;
	}

	public boolean setVerbesserung(int i, Ausruestung it) {
		if (it.f�rGeb�ude) {
			verbesserungen[i] = it;
			return true;
		}
		return false;

	}
	
	public int getProdperMin(){
		return ProduktionproMinute;
	}

	public void changeStats(Ausruestung a) {
		//int stats = new
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAngriff() {
		return angriff;
	}

	public void setAngriff(int angriff) {
		this.angriff = angriff;
	}

	public int getReichweite() {
		return reichweite;
	}

	public void setReichweite(int reichweite) {
		this.reichweite = reichweite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Waren[] getBen�tigt(){
		return ben�tigt;
	}
	
	public int[] getAmountBen�tigt(){
		return amountBen�tigt;
	}
	
	public Waren getProduziert(){
		return produziert;
	}
	
	public int getamountProdzuierteWare(){
		return amountProduzierteWare;
	}
	
	public Ausruestung[] getVerbesserung(){
		return verbesserungen;
	}
	
	public int getMaxLagerKap(){
		return maxLagerKapazit�t;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setProduktionProMinute(int p){
		ProduktionproMinute = p;
	}
	
	public void setSpieler(Spieler sp){
		s = sp;
	}



}
