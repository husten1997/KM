package com.matze.knightmare.meshes;

import static org.lwjgl.opengl.GL11.*;

import java.util.Timer;
import java.util.TimerTask;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Soldat extends RectangleGraphicalObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8303289313627505214L;
	protected int[] angriff, verteidigung; // [0] entspricht nahkampf [1]
	// entspricht fernkampf, [2]
	// entspricht gegen Geb�ude;
	// [0] entspricht nahkampf [1] entspricht fernkampf, [2] entspricht gegen
	// Artillerie;
	protected int bonusAng, reichweite, grundmoral, moral, ausdauer, geschwindigkeit, kosten;
	protected String name;
	protected int effektiv, typ;
	protected Ausruestung ausruestung[];
	protected int health, sold, nahrung;
	protected int team;
	protected Spieler sp;
	protected Timer t = new Timer(true);
	protected TimerTask tt;
	protected int id;

	// TODO Inventory
	public Soldat(int h, Pos position, int width, int height, String textureName) {
		super(position, width, height, textureName, false);
		type = MeshType.EINHEIT;
		angriff = new int[3];
		verteidigung = new int[3];
		ausruestung = new Ausruestung[8];
		health = h;
	}

	public void verbesserung(int[] ang, int[] ver, int b, int reichw, int geschw, int mor, int aus) {

		for (int i = 0; i < angriff.length; i++) {
			angriff[i] += ang[i];
			verteidigung[i] += ver[i];
		}
		bonusAng = b;
		reichweite += reichw;
		geschwindigkeit += geschw;
		grundmoral += mor;
		ausdauer = aus;
	}

	public void init(int[] ang, int[] ver, int b, String nam, int reichw, int geschw, int kost, int mo, int aus, boolean water) {
		for (int i = 0; i < 2; i++) {
			angriff[i] += ang[i];
			verteidigung[i] += ver[i];
		}
		bonusAng = b;
		reichweite += reichw;
		geschwindigkeit += geschw;
		name = nam;
		kosten = kost;
		grundmoral = mo;
		ausdauer = aus;
		wasser = water;
	}

	public int getTyp() {
		return typ;
	}

	public String getTypString() {
		return typ + "";
	}

	public void setTyp(int i) {
		typ = i;
	}

	public String getEffektivString() {
		effektiv = 0;
		switch (typ) {
		case 0: {
			effektiv = 321;
			break;
		}
		case 1: {
			effektiv = 0;
			break;
		}
		case 2: {
			effektiv = 310;
			break;
		}
		case 3: {
			effektiv = 10;
			break;
		}
		case 4: {
			effektiv = 3210;
			break;
		}
		}
		return effektiv + "";
	}

	public int ausdauerBerechnen(int a, int einheitenFreundlich, int einheitenFeindlich) { // int
																							// a
																							// entspricht
																							// der
																							// ausdauer
																							// die
																							// zuvor
																							// returnt
																							// wird
		ausdauer = a * (1 + (moralBerechnen(einheitenFreundlich, einheitenFeindlich) / 100));
		if (ausdauer < 10)
			ausdauer = 10;
		return ausdauer;
	}

	public int moralBerechnen(int einheitenFreundlich, int einheitenFeindlich) {
		moral = grundmoral * (1 + ((einheitenFreundlich - einheitenFeindlich) / 100));
		return moral;
	}

	public int getMoral() {
		return moral;
	}

	public void setAusruestung(int i, Ausruestung aus) {
		ausruestung[i] = aus;
		changeStats(i);
	}

	private void changeStats(int a) {

		int[] stats = new int[12];

		for (int i = 0; i < stats.length; i++) {
			stats[i] = ausruestung[a].getCS(i);
		}

		for (int i = 0; i < 3; i++) {
			angriff[i] += stats[i];
			verteidigung[i] += stats[i + 3];
		}

		reichweite += stats[6];
		geschwindigkeit += stats[7];
		kosten += stats[8];
		bonusAng += stats[9];
		ausdauer += stats[10];
		grundmoral += stats[11];

	}

	// TODO Methode schreiben
	public void stop() {

	}

	public double getSpeed() {
		return geschwindigkeit;
	}

	public int[] getAngriff() {
		return angriff;
	}

	public int[] getVerteidigung() {
		return verteidigung;
	}

	public int getBonusAng() {
		return bonusAng;
	}

	public int getReichweite() {
		return reichweite;
	}

	public int getGrundmoral() {
		return grundmoral;
	}

	public int getAusdauer() {
		return ausdauer;
	}

	public int getGeschwindigkeit() {
		return geschwindigkeit;
	}

	public int getKosten() {
		return kosten;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public Ausruestung[] getAusruestung() {
		return ausruestung;
	}

	public int getHealth() {
		return health;
	}

	public void setSpieler(Spieler spieler) {
		sp = spieler;
	}

	public Spieler getSpieler() {
		return sp;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public int getNahrung() {
		return nahrung;
	}

	public void setNahrung(int nahrung) {
		this.nahrung = nahrung;
	}

	@Override
	public void draw() {
		// store the current model matrix
		glPushMatrix();
		// bind to the appropriate texture for this sprite

		texture.bind();
		glMatrixMode(GL_TEXTURE);
		glLoadIdentity();

		glRotatef(90 * t_rotation, 0f, 0f, 1f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		// translate to the right location and prepare to draw

		glTranslatef((float) position.getX() + xz - 16, (float) position.getY() + yz - 16, 0);
		glRotatef(45 * m_rotation, 0f, 0f, 1f);
		glColor3f((float) (fColor.getRed() / 255 * Knightmare.breightness), (float) (fColor.getGreen() / 255 * Knightmare.breightness),
				(float) (fColor.getBlue() / 255 * Knightmare.breightness));
		// draw a quad textured to match the sprite

		glBegin(GL_QUADS);
		{
			glTexCoord2f((float) widthCount, 0);
			glVertex2f(0, 0);

			glTexCoord2f((float) widthCount, (float) heightCount);
			glVertex2f(0, (float) height);

			glTexCoord2f(0, (float) heightCount);
			glVertex2f((float) width, (float) height);

			glTexCoord2f(0, 0);
			glVertex2f((float) width, 0);

		}
		glEnd();

		// restore the model view matrix to prevent contamination

		glPopMatrix();

	}

	public void setTimerTask(int Sold, int nahrung) {
		this.sold = Sold;
		this.nahrung = nahrung;
		tt = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (getSpieler().possibleToRemove(Rohstoffe.Geld().getID(), Sold) && getSpieler().possibleToRemove(Rohstoffe.Fleisch().getID(), nahrung)) {
					getSpieler().abziehen(Rohstoffe.Geld().getID(), Sold);
					moral = 100;
				} else {
					if (!getSpieler().possibleToRemove(Rohstoffe.Fleisch().getID(), nahrung) || moral == 1) {
						Knightmare.newHandler.die(Soldat.this);
						t.cancel();
					}
					moral = 1;
				}
			}
		};
		t = new Timer(true);
		t.scheduleAtFixedRate(tt, 60000, 60000);
	}
	
	public int getID(){
		return id;
	}
	
	public void setID(int i){
		id = i;
	}
	
	public void stirb(){
		t.cancel();
	}

}
