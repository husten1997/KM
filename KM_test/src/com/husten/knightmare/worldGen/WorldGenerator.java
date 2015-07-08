package com.husten.knightmare.worldGen;

import java.util.Random;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.core.Knightmare;
import com.richard.knightmare.util.Pos;
import com.richard.knightmare.util.Vektor;
import com.husten.knightmare.graphicalObjects.TerrainElement;
import com.husten.knightmare.graphicalObjects.TerrainRes;
import com.matze.knightmare.meshes.Bauen;

public class WorldGenerator implements StringConstants {
	
	private TerrainElement World[][];

	private int x, y, smoothS = 71;

	private double lW = 0.58, lS = 0.61, lG = 0.81, lR = 1;
	
	private double wE = 0.07;
	private double wK = 0.12;
	private double wT = 0.07;
	
	private float max, min, dif;

	private float[][] hm;

	private float routh = 0.6f, fallof = 0.8f, hMulti = 2f;

	private static int seed = 1005464490;
	private Random rand;
	public static Random prand = new Random(seed);
	private Generator generator;

	public WorldGenerator(TerrainElement world[][], int x, int y) {
		World = world;
		this.x = x;
		this.y = y;
		hm = new float[x][y];

	}
	
	public void setAll(int smoothS, double lW, double lS, double lG, double lR, double wE, double wK, float routh, float fallof, float hMulti, int seed){
		this.smoothS = smoothS;
		this.lW = lW;
		this.lS = lS;
		this.lG = lG;
		this.lR =lR;
		this.wE = wE;
		this.wK = wK;
		this.routh = routh;
		this.fallof = fallof;
		this.hMulti = hMulti;
		WorldGenerator.seed = seed;
	}

	public TerrainElement[][] worldGen() {
		gen();
		return World;
	}
	
	

	/**
	 * Die auskomentierten Variablen sind mit absicht drinnen da man mithilfe
	 * von time die generierungsdauer auslesen kann!
	 */
	public void gen() {
		rand = new Random(seed);
		generator = new RandomGenerator(rand, hMulti, -1);
		long Zvor = System.currentTimeMillis();
		genHM(seed, routh);
		set();
		trans();
		long Znach = System.currentTimeMillis();
		long time = Znach - Zvor;
		System.out.println("Done in: " + time + "ms");
	}

	public void genHM(int seed, float r) {
		int inter = Math.round(x / 2);
		square(inter + 1, inter + 1, inter, r);
		smooth(smoothS);
	}

	public void set() {
		min = hm[1][1];
		max = hm[1][1];
		for (int i = 0; i < hm.length; i++) {
			for (int j = 0; j < hm.length; j++) {
				min = (hm[i][j] < min ? hm[i][j] : min);
				max = (hm[i][j] > max ? hm[i][j] : max);
			}
		}
		dif = max - min;
	}

	public void trans() {
		double WW = min + (lW * dif);
		double WS = min + (lS * dif);
		double WG = min + (lG * dif);
		double WR = min + (lR * dif);
		
		Random randRes = new Random(rand.nextLong());
		for (int i = 0; i < hm.length; i++) {
			for (int j = 0; j < hm.length; j++) {
				float z = hm[i][j];
				int x = randRes.nextInt(100);

				if (z < WW) {
					World[i][j] = null;
				}
				if (z > WW && z < WS) {
					World[i][j] = new TerrainElement(new Pos(i*32, j*32), z, "sand.png", Material_t.SAND);
				}
				if (z > WS && z < WG) {
					World[i][j] = new TerrainElement(new Pos(i * 32, j * 32), z, "gras.png",  Material_t.GRAS);
					
					if(x < wT*100){
						
						Knightmare.newHandler.place(Bauen.Baum(new Pos(i * 32, j * 32), null));
					}
				}
				if (z > WG && z < WR) {
					World[i][j] = new TerrainElement(new Pos(i * 32, j * 32), z, "rock.png", Material_t.ROCK);
					
					if(x < wE*100){
						World[i][j] = new TerrainRes(new Pos(i * 32, j * 32), z, "eisen.png", Material_t.IRON,randRes.nextInt(100));
					}
					if(x < (wK + wE)*100 && x > wE*100){
						World[i][j] = new TerrainRes(new Pos(i * 32, j * 32), z, "kohle.png", Material_t.COAL,randRes.nextInt(180));
					}
				}
			}
		}
	}
	
	

	public void square(int xPos, int yPos, int inter, float r) {
		if (xPos < x && xPos >= 0 && yPos < y && yPos >= 0) {
			if (hm[xPos][yPos] == 0.0) {
				hm[xPos][yPos] = generator.getValue(xPos, yPos, r, dsquare(xPos, yPos, inter));
			}
			diamond(xPos + inter, yPos, inter, r);
			diamond(xPos, yPos + inter, inter, r);
			diamond(xPos - inter, yPos, inter, r);
			diamond(xPos, yPos - inter, inter, r);
		}
	}

	public void diamond(int xPos, int yPos, int inter, float r) {
		if (inter >= 1 && xPos < x && xPos >= 0 && yPos < y && yPos >= 0) {
			if (hm[xPos][yPos] == 0.0) {
				hm[xPos][yPos] = generator.getValue(xPos, yPos, r, ddia(xPos, yPos, inter));

				Vektor M1 = middl(new Vektor((double) (xPos + inter), (double) (yPos)), new Vektor((double) (xPos), (double) (yPos + inter)));
				square((int) M1.getX() + (inter / 2), (int) M1.getY() + (inter / 2), (int) Math.round(inter / 2), r * fallof);

				Vektor M2 = middl(new Vektor((double) (xPos - inter), (double) (yPos)), new Vektor((double) (xPos), (double) (yPos + inter)));
				square((int) M2.getX() + (inter / 2), (int) M2.getY() + (inter / 2), (int) Math.round(inter / 2), r * fallof);

				Vektor M3 = middl(new Vektor((double) (xPos + inter), (double) (yPos)), new Vektor((double) (xPos), (double) (yPos - inter)));
				square((int) M3.getX() + (inter / 2), (int) M3.getY() + (inter / 2), (int) Math.round(inter / 2), r * fallof);

				Vektor M4 = middl(new Vektor((double) (xPos - inter), (double) (yPos)), new Vektor((double) (xPos), (double) (yPos - inter)));
				square((int) M4.getX() + (inter / 2), (int) M4.getY() + (inter / 2), (int) Math.round(inter / 2), r * fallof);
			}
		} else {

		}

	}

	public Vektor middl(Vektor v1, Vektor v2) {
		double x1 = (v1.getX() + v2.getX()) / 2;
		double y1 = (v1.getY() + v2.getY()) / 2;
		return new Vektor(x1, y1);
	}

	public float ddia(int xPos, int yPos, int inter) {
		int blub = 0;
		float value = 0;
		if (xPos + inter < x) {
			value += hm[xPos + inter][yPos];
			blub++;
		}
		if (xPos - inter > 0) {
			value += hm[xPos - inter][yPos];
			blub++;
		}
		if (yPos + inter < y) {
			value += hm[xPos][yPos + inter];
			blub++;
		}
		if (yPos - inter > 0) {
			value += hm[xPos][yPos - inter];
			blub++;
		}
		return value / blub;
	}

	public float dsquare(int xPos, int yPos, int inter) {
		int blub = 0;
		float value = 0;
		if (xPos + inter < x && yPos + inter < y) {
			value += hm[xPos + inter][yPos + inter];
			blub++;
		}
		if (xPos - inter >= 0 && yPos - inter >= 0) {
			value += hm[xPos - inter][yPos - inter];
			blub++;
		}
		if (xPos - inter >= 0 && yPos + inter < y) {
			value += hm[xPos - inter][yPos + inter];
			blub++;
		}
		if (xPos + inter < x && yPos - inter >= 0) {
			value += hm[xPos + inter][yPos - inter];
			blub++;
		}
		return value / blub;
	}

	public void smooth(int am) {
		for (int s = 0; s < am; s++) {
			for (int i = 0; i < hm.length; i++) {
				for (int j = 0; j < hm.length; j++) {
					hm[i][j] = ddia(i, j, 1);
				}
			}
		}
	}

	public void init(int smoothS, double lW, double lS, double lG, double lR, double wE, double wK, float routh, float fallof, float hMulti) {
		this.smoothS = smoothS;
		this.lW = lW;
		this.lS = lS;
		this.lG = lG;
		this.lR = lR;
		this.wE = wE;
		this.wK = wK;
		this.routh = routh;
		this.fallof = fallof;
		this.hMulti = hMulti;
	}

	public double getLW(){
		return lW;
	}

	public int getSmoothS() {
		return smoothS;
	}

	public double getlW() {
		return lW;
	}

	public double getlS() {
		return lS;
	}

	public double getlG() {
		return lG;
	}

	public double getlR() {
		return lR;
	}

	public double getwE() {
		return wE;
	}

	public double getwK() {
		return wK;
	}

	public float getRouth() {
		return routh;
	}

	public float getFallof() {
		return fallof;
	}

	public float gethMulti() {
		return hMulti;
	}

	public static int getSeed() {
		return seed;
	}

	public Generator getGenerator() {
		return generator;
	}
	
	
	

}
