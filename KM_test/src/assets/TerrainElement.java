package assets;

import loader.TextureLoader;

public class TerrainElement extends QUAD {
	String type;

	public TerrainElement(float l, float h, float x, float y,TextureLoader loader, String ref, String Type) {
		super(l, h, x, y, loader, ref);
		type = Type;
	}
	
	public TerrainElement(float x, float y,TextureLoader loader, String ref, String Type) {
		super(x, y, loader, ref);
		type = Type;
	}
	
	public TerrainElement(float x, float y,float cs,TextureLoader loader, String ref, String Type) {
		super(x, y, cs, loader, ref);
		type = Type;
		
	}
	
	public void setType(String Type){
		type = Type;
	}
	
	public String getType(){
		return type;
	}
	

}
