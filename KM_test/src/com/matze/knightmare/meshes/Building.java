package com.matze.knightmare.meshes;

import java.util.Timer;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Building extends RectangleGraphicalObject {
	
	private Spieler s;
	
	private int kostetWarevonIndex[];
	private int health;
	private int index;
	private int ProduktionproMinute;
	private int angriff, reichweite;
	private String name;
	private Waren[] benötigt;
	private int[] amountBenötigt;
	private Waren produziert;
	private int amountProduzierteWare;
	private Ausruestung[] verbesserungen;
	private int maxLagerKapazität;
	private Timer timer = new Timer(true);

	
	public Building(int index, Pos position, int width, int height, String textureName) {
		super(position, width, height, textureName, false);
		this.setIndex(index);
		type = MeshType.GEBÄUDE;
		verbesserungen = new Ausruestung[6];
		kostetWarevonIndex = new int[Rohstoffe.maxID()];
	}

	public void init(int health, int ProduktionproMinute, int angriff,
			int reichweite, String name, Waren[] benötigt,
			int[] amountBenötigt, Waren produziert, int maxLagerKap) {
		this.setHealth(health);
		this.ProduktionproMinute = ProduktionproMinute;
		this.setAngriff(angriff);
		this.setReichweite(reichweite);
		this.setName(name);

		this.benötigt = new Waren[benötigt.length];
		this.amountBenötigt = new int[benötigt.length];

		for (int i = 0; i < benötigt.length; i++) {
			this.benötigt[i] = benötigt[i];
			this.amountBenötigt[i] = amountBenötigt[i];
		}
		this.produziert = produziert;
		this.maxLagerKapazität = maxLagerKap;
	}

	public void WareFertigstellen() {
		produziert.addWare(maxLagerKapazität);
		amountProduzierteWare++;
	}
	
	public void setWarenAmount(int i, int kap){
		for (int b = 0; b < kap; b++){
			benötigt[i].addWare(maxLagerKapazität);
		}
	}
	
	public void deminishWarenAmount(int i, int kap){
		for (int b = 0; b < kap; b++){
			System.out.println("kann abziehen: "+benötigt[i].substractWare(1));
		}
	}

	public Waren getAmountProduzierterWare() {
		return produziert;
	}
	
	public int getAmountProduzierterWareAuslesen() {
		return amountProduzierteWare;
	}

	public Waren getProduzierteWare() {
		if (amountProduzierteWare == 0 || produziert == null) {
			System.out.println("Du kannst nichts abholen, oder dieses Gebäude produziert nichts");
			return null;
		}
		return produziert;
	}

	public boolean setVerbesserung(int i, Ausruestung it) {
		if (it.fürGebäude) {
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
	
	public Waren[] getBenötigt(){
		return benötigt;
	}
	
	public int[] getAmountBenötigt(){
		return amountBenötigt;
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
		return maxLagerKapazität;
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

	public Spieler getSpieler(){
		return s;
	}

	public int getKostetWarevonIndex(int i) {
		return kostetWarevonIndex[i];
	}

	public void setKostetWarevonIndex(int i, int amount) {
		kostetWarevonIndex[i]=amount;
	}
	
	public int[] getKostetWarevonArray() {
		return kostetWarevonIndex;
	}
	
	public Timer getTimer(){
		return timer;
	}


}
