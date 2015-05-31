package com.richard.knightmare.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.GraphicalObject;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.matze.knightmare.meshes.Items;

public class Speicher {
	private static Speicher instance;
	
	public Speicher() {
		instance = this;
		new Converter();
	}

	//TODO static
	public boolean speichern(String name) {
		BufferedImage img = new BufferedImage(Knightmare.world.length, Knightmare.world[0].length, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				GraphicalObject helpobject = Knightmare.world[i][j];
				RG_Code rg;
				// TODO eigene to String methode -> Am MAtze schoffa
				if(helpobject!=null){
					rg = Converter.objectToRg.get(helpobject.toString());
				}else{
					rg = new RG_Code(0, 0);
				}
				// TODO getitems
				// TODO pathfinding speichern
				// TODO attribute
				img.setRGB(i, j, new Color(rg.r, rg.g, 0, 255).getRGB());
			}
		}

		File save = new File(new StringBuilder(Loader.getSavesDir().getAbsolutePath()).append("\\save_").append(name).append("_")
				.append(new SimpleDateFormat("yyyy.MM.dd'_'HH.mm.ss").format(new Date())).toString());
		save.mkdir();
		
		File worldGenCfg = new File(new StringBuilder(save.getAbsolutePath()).append("\\").append("worldGenCfg.cfg").toString());
		try {
			worldGenCfg.createNewFile();
		} catch (IOException e) {
			return false;
		}
		//TODO write to worldgencfg
		
		try {
			ImageIO.write(img, "png", new File(new StringBuilder(save.getAbsolutePath()).append("\\Entities.png").toString()));
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	private void laden() {

	}

	private static class Converter {
		private static HashMap<String, RG_Code> objectToRg = new HashMap<>();
		private static HashMap<RG_Code, RectangleGraphicalObject> rgToObject = new HashMap<>();

		private static HashMap<String, HashMap<Items, BA_Code>> itemToBa = new HashMap<>();
		private static HashMap<String, HashMap<BA_Code, Items>> baToItem = new HashMap<>();

		private Converter() {
			objectToRg.put("a", Speicher.instance.new RG_Code(255, 255));
			// TODO write Dic
		}
	}

	private class RG_Code {
		private int r, g;

		private RG_Code(int r, int g) {
			this.r = r;
			this.g = g;
		}
	}

	private class BA_Code {
		private int b, a;

		private BA_Code(int b, int a) {
			this.b = b;
			this.a = a;
		}
	}
}
