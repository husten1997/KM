package com.richard.knightmare.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Loader {

	private static File saves, configs, resourcepacks, cfgFile, resCfgFile, texturesRes;

	public static void initLoader(String firmenname, String spielname) {
		String path = new StringBuilder("C:\\Users\\").append(System.getProperty("user.name")).append("\\AppData\\Roaming\\").append(firmenname).append("\\")
				.append(spielname).toString();
		saves = new File(new StringBuffer(path).append("\\").append("saves").toString());
		configs = new File(new StringBuffer(path).append("\\").append("configs").toString());
		resourcepacks = new File(new StringBuffer(path).append("\\").append("resourcepacks").toString());

		if (!saves.exists()) {
			saves.mkdirs();
		}
		if (!configs.exists()) {
			configs.mkdirs();
		}
		if (!resourcepacks.exists()) {
			resourcepacks.mkdirs();
		}

		// 1 Zeile
		DicAddEntry("ESC", "ESCAPE");

		// 2 Zeile
		DicAddEntry("Zirkumflex (Dead)", "BACKSLASH");
		DicAddEntry("�", "LBRACKET");
		DicAddEntry("Akut (Dead)", "RBRACKET");

		// 3 Zeile (Tab geht nicht)
		DicAddEntry("Plus", "EQUALS");
		DicAddEntry("Eingabe", "RETURN");

		// NumPad
		DicAddEntry("NumPad +", "ADD");
		DicAddEntry("NumPad -", "SUBTRACT");
		DicAddEntry("NumPad *", "MULTIPLY");
		DicAddEntry("NumPad /", "DIVIDE");
		DicAddEntry("Num", "NUMLOCK");
		DicAddEntry("L�schen", "NONE"); // =5

		// 4 Zeile
		DicAddEntry("Nummernzeichen", "SLASH");
		DicAddEntry("Feststelltaste", "CAPITAL");

		// 5 Zeile
		DicAddEntry("Umschalt", "LSHIFT");
		DicAddEntry("Kleiner als", "NONE");
		DicAddEntry("Komma", "COMMA");
		DicAddEntry("Punkt", "PERIOD");
		DicAddEntry("Minus", "MINUS");
		DicAddEntry("Umschalt", "RSHIFT");

		// 6 Zeile
		DicAddEntry("Strg", "CTRL");
		DicAddEntry("Alt", "LMENU");
		DicAddEntry("Windows", "LMETA");
		DicAddEntry("Leertaste", "SPACE");
		DicAddEntry("Kontextmen�", "APPS");

		// Druck Block
		DicAddEntry("Einfg", "INSERT");
		DicAddEntry("Pos 1", "HOME");
		DicAddEntry("Bild auf", "PRIOR");
		DicAddEntry("Entf", "DELETE");
		DicAddEntry("Ende", "END");
		DicAddEntry("Bild ab", "NEXT");

		// Pfeiltasten
		DicAddEntry("Oben", "UP");
		DicAddEntry("Unten", "DOWN");
		DicAddEntry("Links", "LEFT");
		DicAddEntry("Rechts", "RIGHT");

		// 1 Zeile Englisch
		DicAddEntryE("ESC", "ESCAPE");

		// 2 Zeile
		DicAddEntryE("Zirkumflex (Dead)", "BACKSLASH");
		DicAddEntryE("�", "LBRACKET");
		DicAddEntryE("Akut (Dead)", "RBRACKET");

		// 3 Zeile (Tab geht nicht)
		DicAddEntryE("Plus", "EQUALS");
		DicAddEntryE("Eingabe", "RETURN");

		// NumPad
		DicAddEntryE("NumPad +", "ADD");
		DicAddEntryE("NumPad -", "SUBTRACT");
		DicAddEntryE("NumPad *", "MULTIPLY");
		DicAddEntryE("NumPad /", "DIVIDE");
		DicAddEntryE("Num", "NUMLOCK");
		DicAddEntryE("L�schen", "NONE"); // =5

		// 4 Zeile
		DicAddEntryE("Nummernzeichen", "SLASH");
		DicAddEntryE("Feststelltaste", "CAPITAL");

		// 5 Zeile
		DicAddEntryE("Umschalt", "LSHIFT");
		DicAddEntryE("Kleiner als", "NONE");
		DicAddEntryE("Komma", "COMMA");
		DicAddEntryE("Punkt", "PERIOD");
		DicAddEntryE("Minus", "MINUS");
		DicAddEntryE("Umschalt", "RSHIFT");

		// 6 Zeile
		DicAddEntryE("Strg", "CTRL");
		DicAddEntryE("Alt", "LMENU");
		DicAddEntryE("Windows", "LMETA");
		DicAddEntryE("Leertaste", "SPACE");
		DicAddEntryE("Kontextmen�", "APPS");

		// Druck Block
		DicAddEntryE("Einfg", "INSERT");
		DicAddEntryE("Pos 1", "HOME");
		DicAddEntryE("Bild auf", "PRIOR");
		DicAddEntryE("Entf", "DELETE");
		DicAddEntryE("Ende", "END");
		DicAddEntryE("Bild ab", "NEXT");

		// Pfeiltasten
		DicAddEntryE("Oben", "UP");
		DicAddEntryE("Unten", "DOWN");
		DicAddEntryE("Links", "LEFT");
		DicAddEntryE("Rechts", "RIGHT");

		String buchstabe = "QWERTZUIOP�ASDFGHJKL��YXCVBNM";

		// Buchstaben
		for (int i = 0; i < buchstabe.length(); i++) {
			DicAddEntry(buchstabe.substring(i, i + 1), buchstabe.substring(i, i + 1));
			DicAddEntryE(buchstabe.substring(i, i + 1), buchstabe.substring(i, i + 1));
		}

		// Zahlen
		for (int i = 0; i < 10; i++) {
			DicAddEntry("" + i, i + "");
			DicAddEntryE("" + i, i + "");
		}

		// F-Tasten
		for (int i = 1; i < 13; i++) {
			DicAddEntry("F" + i, "F" + i);
			DicAddEntryE("F" + i, "F" + i);
		}

		new Cfg();
		new ResCfg();

		Cfg.loadConfigs();
		ResCfg.loadConfigs();
	}

	private static class Cfg {
		private static HashMap<String, String> defaultConfigValues = new HashMap<>(), configValues = new HashMap<>();
		private static ArrayList<String> sortedKeys = new ArrayList<>();

		private Cfg() {
			defaultConfigValues.put("Resourcepack", "Default");
			sortedKeys.add("Resourcepack");
			defaultConfigValues.put("Volume", "-27.6");
			sortedKeys.add("Volume");

			// WASD
			defaultConfigValues.put("CONTROL_KEY: Vorw�rts", "W");
			sortedKeys.add("CONTROL_KEY: Vorw�rts");
			defaultConfigValues.put("CONTROL_KEY: Links", "A");
			sortedKeys.add("CONTROL_KEY: Links");
			defaultConfigValues.put("CONTROL_KEY: R�ckw�rts", "S");
			sortedKeys.add("CONTROL_KEY: R�ckw�rts");
			defaultConfigValues.put("CONTROL_KEY: Rechts", "D");
			sortedKeys.add("CONTROL_KEY: Rechts");

			// -><-
			defaultConfigValues.put("CONTROL_KEY: Kamera oben", "Oben");
			sortedKeys.add("CONTROL_KEY: Kamera oben");
			defaultConfigValues.put("CONTROL_KEY: Kamera links", "Links");
			sortedKeys.add("CONTROL_KEY: Kamera links");
			defaultConfigValues.put("CONTROL_KEY: Kamera unten", "Unten");
			sortedKeys.add("CONTROL_KEY: Kamera unten");
			defaultConfigValues.put("CONTROL_KEY: Kamera rechts", "Rechts");
			sortedKeys.add("CONTROL_KEY: Kamera rechts");

			// Rest
			defaultConfigValues.put("CONTROL_KEY: V-Sync", "F12");
			sortedKeys.add("CONTROL_KEY: V-Sync");

			defaultConfigValues.put("CONTROL_KEY: Escape/Zur�ck", "ESC");
			sortedKeys.add("CONTROL_KEY: Escape/Zur�ck");

			defaultConfigValues.put("CONTROL_KEY: Best�tigen", "Eingabe");
			sortedKeys.add("CONTROL_KEY: Best�tigen");

			defaultConfigValues.put("CONTROL_KEY: Fenster- u. Vollbildmodus", "F11");
			sortedKeys.add("CONTROL_KEY: Fenster- u. Vollbildmodus");

			defaultConfigValues.put("CONTROL_KEY: Volume +", "F2");
			sortedKeys.add("CONTROL_KEY: Volume +");

			defaultConfigValues.put("CONTROL_KEY: Volume -", "F1");
			sortedKeys.add("CONTROL_KEY: Volume -");

			defaultConfigValues.put("CONTROL_KEY: Scrollen -", "NumPad -");
			sortedKeys.add("CONTROL_KEY: Scrollen -");

			defaultConfigValues.put("CONTROL_KEY: Scrollen +", "NumPad +");
			sortedKeys.add("CONTROL_KEY: Scrollen +");

			defaultConfigValues.put("CONTROL_KEY: Musik wechseln", "M");
			sortedKeys.add("CONTROL_KEY: Musik wechseln");

			defaultConfigValues.put("CONTROL_KEY: Abrei�en", "Entf");
			sortedKeys.add("CONTROL_KEY: Abrei�en");

			defaultConfigValues.put("CONTROL_KEY: Quicksave", "Q");
			sortedKeys.add("CONTROL_KEY: Quicksave");

			defaultConfigValues.put("CONTROL_KEY: Baumen� ein/aus", "B");
			sortedKeys.add("CONTROL_KEY: Baumen� ein/aus");

			// Settings
			defaultConfigValues.put("SETTINGS: V-Sync", "Off");
			sortedKeys.add("SETTINGS: V-Sync");

			defaultConfigValues.put("SETTINGS: Fenstermodus", "false");
			sortedKeys.add("SETTINGS: Fenstermodus");

			defaultConfigValues.put("SETTINGS: Profilname", "Lord Siegmund");
			sortedKeys.add("SETTINGS: Profilname");

			defaultConfigValues.put("SETTINGS: Startzeit", "1.0");
			sortedKeys.add("SETTINGS: Startzeit");

			defaultConfigValues.put("SETTINGS: Profilbild", "src\\resources\\textures\\profil.png");
			sortedKeys.add("SETTINGS: Profilbild");

			defaultConfigValues.put("SETTINGS: Default difficulty", "1");
			sortedKeys.add("SETTINGS: Default difficulty");

			sortedKeys.sort(new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.compareToIgnoreCase(o2);
				}
			});
		}

		private static void loadConfigs() {
			cfgFile = new File(new StringBuilder(configs.getAbsolutePath()).append("\\Configs.cfg").toString());

			if (cfgFile.exists()) {
				try {
					// Read configs
					BufferedReader reader = new BufferedReader(new FileReader(cfgFile));

					for (int i = 0; i < sortedKeys.size(); i++) {
						String line = reader.readLine();
						if (line != null) {
							configValues.put(sortedKeys.get(i), line.substring(line.lastIndexOf("=") + 2));
						} else {
							try {
								// Write configs
								configValues = defaultConfigValues;
								cfgFile.createNewFile();
							} catch (IOException e) {
								// File seems to be broken
							}
							writeValues();
							break;
						}
					}
					reader.close();
				} catch (IOException e) {
					// File seems to be broken
				}
			} else {
				try {
					// Write configs
					configValues = defaultConfigValues;
					cfgFile.createNewFile();
				} catch (IOException e) {
					// File seems to be broken
				}
				writeValues();
			}

			// Validate
			texturesRes = new File(new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(getCfgValue("Resourcepack")).append("\\Textures").toString());
			if (!texturesRes.exists()) {
				configValues.put("Resourcepack", "Default");
				writeValues();
				texturesRes = new File(
						new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(getCfgValue("Resourcepack")).append("\\Textures").toString());
			}
			if (!(Float.parseFloat(configValues.get("Volume")) <= 6 && -80 <= Float.parseFloat(configValues.get("Volume")))) {
				configValues.put("Volume", "-27.6");
				writeValues();
			}
		}

		private static void writeValues() {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(cfgFile));

				for (int i = 0; i < sortedKeys.size(); i++) {
					writer.write(new StringBuilder(sortedKeys.get(i)).append(" = ").append(configValues.get(sortedKeys.get(i))).toString());
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {
				// File seems to be broken
			}
		}
	}

	private static class ResCfg {
		private static HashMap<String, String> defaultConfigValues = new HashMap<>(), configValues = new HashMap<>();
		private static ArrayList<String> sortedKeys = new ArrayList<>();

		private ResCfg() {
			defaultConfigValues.put("Button: Spielstarten (posx1)", "848");
			sortedKeys.add("Button: Spielstarten (posx1)");
			defaultConfigValues.put("Button: Spielstarten (posy1)", "465");
			sortedKeys.add("Button: Spielstarten (posy1)");

			defaultConfigValues.put("Button: Spielstarten (posx2)", "1920");
			sortedKeys.add("Button: Spielstarten (posx2)");
			defaultConfigValues.put("Button: Spielstarten (posy2)", "586");
			sortedKeys.add("Button: Spielstarten (posy2)");

			defaultConfigValues.put("Button: Optionen (posx1)", "848");
			sortedKeys.add("Button: Optionen (posx1)");
			defaultConfigValues.put("Button: Optionen (posy1)", "608");
			sortedKeys.add("Button: Optionen (posy1)");

			defaultConfigValues.put("Button: Optionen (posx2)", "1920");
			sortedKeys.add("Button: Optionen (posx2)");
			defaultConfigValues.put("Button: Optionen (posy2)", "729");
			sortedKeys.add("Button: Optionen (posy2)");

			defaultConfigValues.put("Button: Laden (posx1)", "848");
			sortedKeys.add("Button: Laden (posx1)");
			defaultConfigValues.put("Button: Laden (posy1)", "751");
			sortedKeys.add("Button: Laden (posy1)");

			defaultConfigValues.put("Button: Laden (posx2)", "1920");
			sortedKeys.add("Button: Laden (posx2)");
			defaultConfigValues.put("Button: Laden (posy2)", "838");
			sortedKeys.add("Button: Laden (posy2)");

			defaultConfigValues.put("Button: Schliessen (posx1)", "848");
			sortedKeys.add("Button: Schliessen (posx1)");
			defaultConfigValues.put("Button: Schliessen (posy1)", "894");
			sortedKeys.add("Button: Schliessen (posy1)");

			defaultConfigValues.put("Button: Schliessen (posx2)", "1920");
			sortedKeys.add("Button: Schliessen (posx2)");
			defaultConfigValues.put("Button: Schliessen (posy2)", "967");
			sortedKeys.add("Button: Schliessen (posy2)");

			sortedKeys.sort(new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.compareToIgnoreCase(o2);
				}
			});
		}

		private static void loadConfigs() {
			if (Loader.getCfgValue("Resourcepack").equals("Default")) {
				resCfgFile = new File("src\\resources\\Configs.cfg");
			} else {
				resCfgFile = new File(
						new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(Loader.getCfgValue("Resourcepack")).append("\\Configs.cfg").toString());
			}

			if (resCfgFile.exists()) {
				try {
					// Read configs
					BufferedReader reader = new BufferedReader(new FileReader(resCfgFile));

					for (int i = 0; i < sortedKeys.size(); i++) {
						String line = reader.readLine();
						if (line != null) {
							configValues.put(sortedKeys.get(i), line.substring(line.lastIndexOf("=") + 2));
						} else {
							try {
								// Write configs
								configValues = defaultConfigValues;
								cfgFile.createNewFile();
							} catch (IOException e) {
								// File seems to be broken
							}
							writeValues();
							break;
						}
					}
					reader.close();
				} catch (IOException e) {
					// File seems to be broken
				}
			} else {
				try {
					// Write configs
					configValues = defaultConfigValues;
					resCfgFile.createNewFile();
				} catch (IOException e) {
					// File seems to be broken
				}
				writeValues();
			}

			// Validate
		}

		private static void writeValues() {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(resCfgFile));

				for (int i = 0; i < sortedKeys.size(); i++) {
					writer.write(new StringBuilder(sortedKeys.get(i)).append(" = ").append(configValues.get(sortedKeys.get(i))).toString());
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {
				// File seems to be broken
			}
		}
	}

	private static void DicAddEntry(String deutsch, String englisch) {
		Dictionary.addEntry(englisch, deutsch);
		Dictionary.addEntry(deutsch, deutsch);
	}

	private static void DicAddEntryE(String deutsch, String englisch) {
		DictionaryE.addEntry(deutsch, englisch);
		DictionaryE.addEntry(englisch, englisch);
	}

	public static Clip getSound(String name) {
		if (getCfgValue("Resourcepack").equals("Default")) {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder("src\\resources\\sounds").append("\\").append(name).toString())));
				return clip;
			} catch (Exception e1) {
				// Just stop trying
			}
		} else {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(getCfgValue("Resourcepack"))
						.append("\\Sounds\\").append(name).toString())));
				return clip;
			} catch (Exception e) {
				// Didn't work, trying default
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder("src\\resources\\sounds").append("\\").append(name).toString())));
					return clip;
				} catch (Exception e1) {
					// Just stop trying
				}
			}
		}
		return null;

	}

	public static Clip getMusic(String name) {
		if (getCfgValue("Resourcepack").equals("Default")) {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder("src\\resources\\music").append("\\").append(name).toString())));
				return clip;
			} catch (Exception e1) {
				// Just stop trying
			}
		} else {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(getCfgValue("Resourcepack"))
						.append("\\Music\\").append(name).toString())));
				return clip;
			} catch (Exception e) {
				// Didn't work, trying default
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder("src\\resources\\music").append("\\").append(name).toString())));
					return clip;
				} catch (Exception e1) {
					// Just stop trying
				}
			}
		}
		return null;
	}

	public static File getSavesDir() {
		return saves;
	}

	public static String[] getMusicList() {
		return new File("src\\resources\\music").list();
	}

	public static BufferedImage getImage(String name) {
		BufferedImage bufferedImage = null;
		if (getCfgValue("Resourcepack").equals("Default")) {
			try {
				bufferedImage = ImageIO.read(new File(new StringBuilder("src\\resources\\textures").append("\\").append(name).toString()));
			} catch (IOException e1) {
				// Ignore
			}
		} else {
			try {
				bufferedImage = ImageIO.read(new File(new StringBuilder(texturesRes.getAbsolutePath()).append("\\").append(name).toString()));
			} catch (IOException e) {
				try {
					bufferedImage = ImageIO.read(new File(new StringBuilder("src\\resources\\textures").append("\\").append(name).toString()));
				} catch (IOException e1) {
					// Ignore
				}
			}
		}
		if (bufferedImage == null) {
			bufferedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(name, 0, 20);
			graphics.dispose();
		}
		return bufferedImage;
	}

	public static void changeCfgValue(String key, String value) {
		if (Cfg.sortedKeys.contains(key)) {
			Cfg.configValues.put(key, value);
			Cfg.writeValues();
		}
		if (ResCfg.sortedKeys.contains(key)) {
			ResCfg.configValues.put(key, value);
			ResCfg.writeValues();
		}
	}

	public static File getTexturesRes() {
		return texturesRes;
	}

	public static String getCfgValue(String key) {
		if (Cfg.sortedKeys.contains(key)) {
			return Cfg.configValues.get(key);
		}
		if (ResCfg.sortedKeys.contains(key)) {
			return ResCfg.configValues.get(key);
		}
		return null;
	}

	public static BufferedImage getResourcepackIcon(String name) {
		BufferedImage bufferedImage = null;
		if (name.equals("Default")) {
			try {
				bufferedImage = ImageIO.read(new File("src\\resources\\textures\\Icon.png"));
			} catch (IOException e1) {
				// Ignore
			}
		} else {
			try {
				bufferedImage = ImageIO
						.read(new File(new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(name).append("\\Textures\\Icon.png").toString()));
			} catch (IOException e) {
				try {
					bufferedImage = ImageIO.read(new File("src\\resources\\textures\\Icon.png"));
				} catch (IOException e1) {
					// Ignore
				}
			}
		}
		if (bufferedImage == null) {
			bufferedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(name, 0, 20);
			graphics.dispose();
		}
		return bufferedImage;
	}

	public static void resetCfgValue(String key) {
		changeCfgValue(key, getDefaultCfgValue(key));
	}

	private static String getDefaultCfgValue(String key) {
		if (Cfg.sortedKeys.contains(key)) {
			return Cfg.defaultConfigValues.get(key);
		}
		if (ResCfg.sortedKeys.contains(key)) {
			return ResCfg.defaultConfigValues.get(key);
		}
		return null;
	}

	public static String getTexturePath() {
		if (getCfgValue("Resourcepack").equals("Default")) {
			return "/resources/textures";
		} else {
			return new StringBuilder("file:/").append(texturesRes.getAbsolutePath()).toString();
		}
	}
}
