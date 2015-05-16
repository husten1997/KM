package res.generator;

import java.util.Random;

public class SinGenerator extends Generator{
	
	Random rand;
	float argA;
	float argB;
	float argC;
	
	public SinGenerator(Random rand, float argA, float argB, float argC) {
		this.rand = rand;
		this.argA = argA;
		this.argB = argB;
		this.argC = argC;
	}

	@Override
	public float getValue(int xPos, int yPos, float routh, float d) {
		
		return (float) (Math.sin(xPos/argA)*Math.sin(yPos/argB))*argC;
	}

}
