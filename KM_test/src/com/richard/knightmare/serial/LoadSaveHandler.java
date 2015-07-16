package com.richard.knightmare.serial;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.husten.knightmare.graphicalObjects.Terrain;
import com.richard.knightmare.util.EntityHandler;
import com.richard.knightmare.util.Loader;

public class LoadSaveHandler {

	public static boolean save(String name, EntityHandler handler, Terrain terrain){
		File save = new File(new StringBuilder(Loader.getSavesDir().getAbsolutePath()).append("\\save_").append(name).append("_")
				.append(new SimpleDateFormat("yyyy.MM.dd'_'HH.mm.ss").format(new Date())).toString());
		save.mkdir();
		return Save.save(new StringBuilder(save.getAbsolutePath()).append("\\handler.ser").toString(), handler) && Save.save(new StringBuilder(save.getAbsolutePath()).append("\\terrain.ser").toString(), terrain);
	}
	
	public static Object[] load(String savePath){
		Object[] returnA = new Object[2];
		returnA[0]=Save.load(new StringBuilder(savePath).append("\\handler.ser").toString());
		returnA[1]=Save.load(new StringBuilder(savePath).append("\\terrain.ser").toString());
		return returnA;
	}
}
