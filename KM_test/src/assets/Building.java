package assets;

import com.husten.knightmare.constants.StringConstants;

import loader.TextureLoader;

public class Building extends QUAD {

	public Building(float l, float h, float x, float y, TextureLoader loader, String ref) {
		super(l, h, x, y, loader, ref);
		type = StringConstants.MeshType.GEBÄUDE;
		
	}
	
	public Building(float x, float y, TextureLoader loader, String ref) {
		super(x, y, loader, ref);
		
	}
	
	public Building(float x, float y, float cs, TextureLoader loader, String ref) {
		super(x, y, cs, loader, ref);
		
	}

}
