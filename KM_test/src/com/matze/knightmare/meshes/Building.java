package com.matze.knightmare.meshes;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Building extends RectangleGraphicalObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -981478998399012244L;

	private Spieler s;

	private int kostetWarevonIndex[];
	private int health;
	private int index;
	private double ProduktionproMinute;
	private int angriff, reichweite;
	private String name;
	private Waren[] ben�tigt;
	private int[] amountBen�tigt;
	private Waren produziert;
	private int amountProduzierteWare;
	private Ausruestung[] verbesserungen;
	private int maxLagerKapazit�t;
	private transient Timer timer = new Timer(true), timer2 = new Timer(true);
	private transient TimerTask tt;
	private ArrayList<String> erlaubt = new ArrayList<>();
	private ArrayList<String> muss = new ArrayList<>();

	public Building(int index, Pos position, int width, int height, String textureName) {
		super(position, width, height, textureName, false);
		this.setIndex(index);
		type = MeshType.GEB�UDE;
		verbesserungen = new Ausruestung[6];
		kostetWarevonIndex = new int[Rohstoffe.maxID()];
	}

	public void init(int health, double d, int angriff, int reichweite, String name, Waren[] ben�tigt, int[] amountBen�tigt, Waren produziert,
			int maxLagerKap) {
		this.setHealth(health);
		this.ProduktionproMinute = d;
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
		produziert.addWare(maxLagerKapazit�t);
		amountProduzierteWare++;
	}

	public void setWarenAmount(int i, int kap) {
		for (int b = 0; b < kap; b++) {
			ben�tigt[i].addWare(maxLagerKapazit�t);
		}
	}

	public void deminishWarenAmount(int i, int kap) {
		for (int b = 0; b < kap; b++) {
			ben�tigt[i].substractWare(1);
		}
	}

	public Waren getAmountProduzierterWare() {
		amountProduzierteWare = 0;
		return produziert;
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

	public double getProdperMin() {
		return ProduktionproMinute;
	}

	public void changeStats(Ausruestung a) {
		// int stats = new
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

	public Waren[] getBen�tigt() {
		return ben�tigt;
	}

	public int[] getAmountBen�tigt() {
		return amountBen�tigt;
	}

	public Waren getProduziert() {
		return produziert;
	}

	public int getamountProdzuierteWare() {
		return amountProduzierteWare;
	}

	public Ausruestung[] getVerbesserung() {
		return verbesserungen;
	}

	public int getMaxLagerKap() {
		return maxLagerKapazit�t;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setProduktionProMinute(int p) {
		ProduktionproMinute = p;
	}

	public void setSpieler(Spieler sp) {
		s = sp;
	}

	public Spieler getSpieler() {
		return s;
	}

	public int getKostetWarevonIndex(int i) {
		return kostetWarevonIndex[i];
	}

	public void setKostetWarevonIndex(int i, int amount) {
		kostetWarevonIndex[i] = amount;
	}

	public int[] getKostetWarevonArray() {
		return kostetWarevonIndex;
	}

	public Timer getTimer() {
		return timer;
	}

	public Timer getTimer2() {
		return timer2;
	}

	public void addnichtErlaubt(String e) {
		erlaubt.add(e);
	}

	public ArrayList<String> getnichtErlaubt() {
		return erlaubt;
	}

	public ArrayList<String> getMuss() {
		return muss;
	}

	public void addMuss(String m) {
		muss.add(m);
	}

	public void setTimerTask(TimerTask tt) {
		this.tt = tt;
	}

	public void startTimer() {
		if (ProduktionproMinute != 0 && tt!=null) {
			timer.scheduleAtFixedRate(tt, (long) (60000 / ProduktionproMinute), (long) (60000 / ProduktionproMinute));
		}
	}
	
	public String[] getAttribute() {
		String[] a = new String[4];
		String[] ben = new String[getBen�tigt().length];
		a[0] = "";
		for (int i = 0; i < ben.length; i++) {
			ben[i] = "null";
		}
		for (int i = 0; i < ben.length; i++) {
			if (!getName().equals("Kornspeicher")
					&& !getName().equals("Waffenkammer")
					&& !getName().equals("Lager")
					&& !getName().equals("Schatzkammer") && produziert != null) {
				ben[i] = amountBen�tigt[i] + " " + getBen�tigt()[i].getName();
			} else {
				if (getBen�tigt()[i].getAmount() != 0) {
					ben[i] = getBen�tigt()[i].getAmount() + " "
							+ getBen�tigt()[i].getName();
				}
			}
			if (ben[i] != "null")
				a[0] = a[0] + ben[i] + "; ";
		}
		if (!getName().equals("Kornspeicher")
				&& !getName().equals("Waffenkammer")
				&& !getName().equals("Lager")
				&& !getName().equals("Schatzkammer") && produziert != null) {
			a[0] = "Dieses Geb�ude ben�tigt: " + a[0] + "um "
					+ produziert.getName() + " zu erstellen";
		} else {
			a[0] = "Dieses Geb�ude lagert: " + a[0];
		}

		a[1] = "Gesundheit: " + getHealth();

		if (produziert != null) {
			a[2] = "Produziert " + getProdperMin() + " " + produziert.getName()
					+ " pro Tag";
		} else {
			a[2] = "Dieses Geb�ude produziert nichts";
		}

		a[3] = "Maximaler Lagerplatz: " + getMaxLagerKap();

		return a;
	}

}
