package assets;

import loader.TextureLoader;

public class Soldat extends QUAD {
	private int health;
	public Soldat(float l, float h, float x, float y, TextureLoader loader, String ref) {
		super(l, h, x, y, loader, ref);
		
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
	
	
	
	

}
