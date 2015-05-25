package com.husten.knightmare.worldGen;

import java.util.Random;

public class SinGenerator extends Generator {

	@SuppressWarnings("unused")
	private Random rand;
	private float argA, argB, argC;

	public SinGenerator(Random rand, float argA, float argB, float argC) {
		this.rand = rand;
		this.argA = argA;
		this.argB = argB;
		this.argC = argC;
	}

	@Override
	public float getValue(int xPos, int yPos, float routh, float d) {

		return (float) (Math.sin(xPos / argA) * Math.sin(yPos / argB)) * argC;
	}

}
