package com.richard.knightmare.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.husten.knightmare.worldGen.WorldGenerator;
import com.matze.knightmare.meshes.Ausruestung;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Soldat;

public class Speicherer {

	public boolean speichern(String name, RectangleGraphicalObject[][] world, WorldGenerator generator) {
		File save = new File(new StringBuilder(Loader.getSavesDir().getAbsolutePath()).append("\\save_").append(name).append("_")
				.append(new SimpleDateFormat("yyyy.MM.dd'_'HH.mm.ss").format(new Date())).toString());
		save.mkdir();
		File worldGenCfg = new File(new StringBuilder(save.getAbsolutePath()).append("\\").append("worldGenCfg.cfg").toString());
		try {
			worldGenCfg.createNewFile();
		} catch (Exception e) {
			return false;
		}
		try {
			BufferedWriter cfgWriter = new BufferedWriter(new FileWriter(worldGenCfg));
			cfgWriter.write(generator.getSmoothS());
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.getLW()));
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.getlS()));
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.getlG()));
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.getlR()));
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.getwE()));
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.getwK()));
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.getRouth()));
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.getFallof()));
			cfgWriter.newLine();
			cfgWriter.write(String.valueOf(generator.gethMulti()));
			cfgWriter.close();
		} catch (IOException e) {
			return false;
		}

		for (int x = 0; x < world.length; x++) {
			for (int y = 0; y < world[x].length; y++) {
				if(world[x][y]!=null){
					File currentFile = new File(new StringBuilder(save.getAbsolutePath()).append("\\").append(x).append(".").append(y).append(".Entity").toString());
					try {
						currentFile.createNewFile();
					} catch (Exception e) {
						return false;
					}
					if(world[x][y].getType().equals(StringConstants.MeshType.EINHEIT)){
						try {
							BufferedWriter currentWriter = new BufferedWriter(new FileWriter(currentFile));
							Soldat soldat = (Soldat) world[x][y];
							int[] angriff = soldat.getAngriff();
							for(int i = 0; i< angriff.length; i++){
								currentWriter.write(angriff[i]);
								currentWriter.newLine();
							}
							int[] verteidigung = soldat.getVerteidigung();
							for(int i = 0; i< verteidigung.length; i++){
								currentWriter.write(verteidigung[i]);
								currentWriter.newLine();
							}
							currentWriter.write(soldat.getBonusAng());
							currentWriter.newLine();
							currentWriter.write(soldat.getReichweite());
							currentWriter.newLine();
							currentWriter.write(soldat.getGrundmoral());
							currentWriter.newLine();
							currentWriter.write(soldat.getMoral());
							currentWriter.newLine();
							currentWriter.write(soldat.getAusdauer());
							currentWriter.newLine();
							currentWriter.write(soldat.getGeschwindigkeit());
							currentWriter.newLine();
							currentWriter.write(soldat.getKosten());
							currentWriter.newLine();
							currentWriter.write(soldat.getName());
							currentWriter.newLine();
							currentWriter.write(soldat.getEffektiv());
							currentWriter.newLine();
							currentWriter.write(soldat.getTyp());
							currentWriter.newLine();
							Ausruestung[] aus = soldat.getAusruestung();
							for(int i = 0; i<aus.length; i++){
								if(aus[i]==null){
									currentWriter.write("null");
								}else{
									currentWriter.write(aus[i].getID());
								}
								currentWriter.newLine();
							}
							currentWriter.write(soldat.getHealth());
							currentWriter.close();
						} catch (IOException e) {
							return false;
						}
					}else{
						if((int) world[x][y].getPosition().getX()/32 == x && (int) world[x][y].getPosition().getY()/32 == y){
							try {
								BufferedWriter currentWriter = new BufferedWriter(new FileWriter(currentFile));
								Building building = (Building) world[x][y];
							} catch (IOException e) {
								return false;
							}
						}
					}
				}
			}
		}

		return true;
	}
}
