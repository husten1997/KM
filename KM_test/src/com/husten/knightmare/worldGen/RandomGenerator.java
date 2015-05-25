package com.husten.knightmare.worldGen;

import java.util.Random;

public class RandomGenerator extends Generator {
	private Random rand;
	private float argA, argB;

	public RandomGenerator(Random rand, float argA, float argB) {
		this.rand = rand;
		this.argA = argA;
		this.argB = argB;
	}

	@Override
	public float getValue(int xPos, int yPos, float routh, float d) {
		return d + routh * (argA * rand.nextFloat() + argB);
	}

}
