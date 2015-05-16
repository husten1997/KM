package com.richard.knightmare.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Environment {

	private static File resConfig, resourceDir;
	private static String gameDirPath, resourceDirPath;
	private static HashMap<String, BufferedImage> textures = new HashMap<>();
	private static HashMap<String, Clip> sounds = new HashMap<>();
	private static HashMap<String, Map> maps = new HashMap<>();

	public static void setUpEnvironment(String companiesName, String gamesName) {
		gameDirPath = new StringBuilder("C:\\Users\\").append(System.getProperty("user.name")).append("\\AppData\\Roaming\\").append(companiesName).append("\\").append(gamesName).toString();
		resourceDir = new File(new StringBuilder(gameDirPath).append("\\Resources").toString());
		File configsDir = new File(new StringBuilder(gameDirPath).append("\\Configs").toString());
		File savesDir = new File(new StringBuilder(gameDirPath).append("\\saves").toString());

		if (!resourceDir.exists()) {
			resourceDir.mkdirs();
		}
		if (!configsDir.exists()) {
			configsDir.mkdir();
		}
		if (!savesDir.exists()) {
			savesDir.mkdir();
		}

		resConfig = new File(new StringBuilder(configsDir.getAbsolutePath()).append("\\Resourcepack.cfg").toString());

		try {
			if (resConfig.createNewFile()) {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resConfig));
				bufferedWriter.write("Default");
				bufferedWriter.close();
			}
		} catch (IOException e) {
			// Cannot create configuration file or write to configuration file,
			// assuming default at all times.
		}

		resourceDirPath = new StringBuilder(resourceDir.getAbsolutePath()).append("\\Default").toString();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(resConfig));
			resourceDirPath = new StringBuilder(resourceDir.getAbsolutePath()).append("\\").append(bufferedReader.readLine()).toString();
			bufferedReader.close();
		} catch (IOException e) {
			// Configuration file does not exist, assuming default.
		}
		load();
	}

	private static void load() {
		loadImages();
		loadSounds();
		loadMaps();
	}

	private static void loadImages() {
		String path = "\\Default\\Textures";

		File textures = new File(new StringBuilder(resourceDir.getAbsolutePath()).append(path).toString());
		String[] names = textures.list();
		for (int i = 0; i < names.length; i++) {
			try {
				Environment.textures.put(names[i], ImageIO.read(new File(new StringBuilder(resourceDirPath).append("\\Textures\\").append(names[i]).toString())));
			} catch (IOException e) {
				// Image not load-able trying default
				try {
					Environment.textures.put(names[i], ImageIO.read(new File(new StringBuilder(path).append("\\").append(names[i]).toString())));
				} catch (IOException e1) {
					// Just stop trying
				}
			}
		}
	}

	private static Clip loadMusic(String name) {
		String path = "\\Default\\Music";

		File music = new File(new StringBuilder(resourceDir.getAbsolutePath()).append(path).toString());
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder(resourceDirPath).append("\\Music\\").append(name).toString())));
			return clip;
		} catch (Exception e) {
			// Didn't work, trying default
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder(music.getAbsolutePath()).append("\\").append(name).toString())));
				return clip;
			} catch (Exception e1) {
				// Just stop trying
			}	
		}
		return null;
	}

	private static void loadSounds() {
		String path = "\\Default\\Sounds";

		File sounds = new File(new StringBuilder(resourceDir.getAbsolutePath()).append(path).toString());
		String[] names = sounds.list();
		for (int i = 0; i < names.length; i++) {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder(resourceDirPath).append("\\Sounds\\").append(names[i]).toString())));
				Environment.sounds.put(names[i], clip);
			} catch (Exception e) {
				// Didn't work, trying default
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder(path).append("\\").append(names[i]).toString())));
					Environment.sounds.put(names[i], clip);
				} catch (Exception e1) {
					// Just stop trying
				}
			}
		}
	}
	
	private static void loadMaps(){
		File mapDir = new File(new StringBuilder(resourceDirPath).append("\\Maps").toString());
		String[] mapList = mapDir.list();
		for(int i = 0; i<mapList.length; i++){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(new File(new StringBuilder(mapDir.getAbsolutePath()).append("\\").append(mapList[i]).toString())));
				String line = reader.readLine();
				ArrayList<String[]> lines = new ArrayList<>();
				int y = 0;
				while(line!=null){
					String[] lineArray = new String[line.length()];
					for(int x = 0; x<line.length(); x++){
						lineArray[x] = line.substring(x, x+1);
					}
					lines.add(lineArray);
					y++;
					line = reader.readLine();
				}
				String map[][] = new String[lines.get(0).length][lines.size()]; 
				Map m = new Map(lines.get(0).length, lines.size(), 32);
				for(int x = 0; x<lines.get(0).length; x++){
					for(y = 0; y<lines.size(); y++){
						map[x][y]=Dictionary.getFullName(lines.get(y)[x]);
					}
				}
				m.setGround(map);
				maps.put(mapList[i], m);
				reader.close();
			} catch (IOException e) {
				// Ignore
			}
		}
	}

	public static void changeResourcePack(String name) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resConfig));
		bufferedWriter.write(name);
		bufferedWriter.close();
		resourceDirPath = new StringBuilder(resourceDir.getAbsolutePath()).append("\\").append(name).toString();
		load();
	}

	public static BufferedImage getTexture(String name) {
		return textures.get(name);
	}
	
	public static Clip getMusic(String name) {
		return loadMusic(name);
	}
	
	public static Clip getSound(String name) {
		return sounds.get(name);
	}

	public static Map getMap(String name){
		return maps.get(name);
	}
	
	public static String[] getMusicList() {
		return new File(resourceDirPath + "\\Music").list();
	}

}
