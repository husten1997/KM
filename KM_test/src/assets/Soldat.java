package assets;

import loader.TextureLoader;
import res.StringConstants;

public class Soldat extends QUAD {
	private int health;
	private int speed = 0;
	//TODO Inventory
	public Soldat(float l, float h, float x, float y, TextureLoader loader, String ref) {
		super(l, h, x, y, loader, ref);
		speed = 5;
		type = StringConstants.EINEHEIT;
	}
	
	public Soldat(float x, float y, TextureLoader loader, String ref) {
		super(x, y, loader, ref);
		
	}
	
	public Soldat(float x, float y,float cs, TextureLoader loader, String ref) {
		super(x, y, cs, loader, ref);
		
	}
	
	public void say(){
		System.out.println("Ich bin da");
	}
	
	public double getSpeed(){
		return speed;
	}
	
	
	
	

}
