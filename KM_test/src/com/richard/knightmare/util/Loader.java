package com.richard.knightmare.util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.lwjgl.BufferUtils;

import com.husten.knightmare.graphicalObjects.Texture;

public class Loader {

	private static File saves, configs, resourcepacks, cfgFile, resCfgFile,
			texturesRes, texturesDefault;
	private static HashMap<String, Texture> textures = new HashMap<>();
	private static HashMap<String, Clip> sounds = new HashMap<>();
	private static ColorModel glAlphaColorModel, glColorModel;
	private static IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);
	private static Speicher speicher = new Speicher();

	public static void initLoader(String firmenname, String spielname) {
		initLoaderWithoutLoad(firmenname, spielname);
		load();
	}

	private static class Cfg {
		private static HashMap<String, String> defaultConfigValues = new HashMap<>(),
				configValues = new HashMap<>();
		private static ArrayList<String> sortedKeys = new ArrayList<>();

		private Cfg() {
			defaultConfigValues.put("Resourcepack", "Default");
			sortedKeys.add("Resourcepack");
			defaultConfigValues.put("Volume", "-27.6");
			sortedKeys.add("Volume");
			defaultConfigValues.put("Fullscreen", "true");
			sortedKeys.add("Fullscreen");

			// WASD
			defaultConfigValues.put("CONTROL_KEY: Vorwärts", "W");
			sortedKeys.add("CONTROL_KEY: Vorwärts");
			defaultConfigValues.put("CONTROL_KEY: Links", "A");
			sortedKeys.add("CONTROL_KEY: Links");
			defaultConfigValues.put("CONTROL_KEY: Rückwärts", "S");
			sortedKeys.add("CONTROL_KEY: Rückwärts");
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

			defaultConfigValues.put("CONTROL_KEY: Escape/Zurück", "ESC");
			sortedKeys.add("CONTROL_KEY: Escape/Zurück");

			defaultConfigValues.put("CONTROL_KEY: Bestätigen", "Eingabe");
			sortedKeys.add("CONTROL_KEY: Bestätigen");

			defaultConfigValues.put("CONTROL_KEY: Fenster- u. Vollbildmodus",
					"F11");
			sortedKeys.add("CONTROL_KEY: Fenster- u. Vollbildmodus");

			defaultConfigValues.put("CONTROL_KEY: Volume +", "F2");
			sortedKeys.add("CONTROL_KEY: Volume +");

			defaultConfigValues.put("CONTROL_KEY: Volume -", "F1");
			sortedKeys.add("CONTROL_KEY: Volume -");
			
			defaultConfigValues.put("SETTINGS: V-Sync", "Off");
			sortedKeys.add("SETTINGS: V-Sync");

			sortedKeys.sort(new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.compareToIgnoreCase(o2);
				}
			});
		}

		private static void loadConfigs() {
			cfgFile = new File(new StringBuilder(configs.getAbsolutePath())
					.append("\\Configs.cfg").toString());

			if (cfgFile.exists()) {
				try {
					// Read configs
					BufferedReader reader = new BufferedReader(new FileReader(
							cfgFile));

					for (int i = 0; i < sortedKeys.size(); i++) {
						String line = reader.readLine();
						if (line != null) {
							configValues.put(sortedKeys.get(i),
									line.substring(line.lastIndexOf("=") + 2));
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
			texturesRes = new File(new StringBuilder(
					resourcepacks.getAbsolutePath()).append("\\")
					.append(getCfgValue("Resourcepack")).append("\\Textures")
					.toString());
			if (!texturesRes.exists()) {
				configValues.put("Resourcepack", "Default");
				writeValues();
				texturesRes = new File(new StringBuilder(
						resourcepacks.getAbsolutePath()).append("\\")
						.append(getCfgValue("Resourcepack"))
						.append("\\Textures").toString());
			}
			if (!(Float.parseFloat(configValues.get("Volume")) <= 6 && -80 <= Float
					.parseFloat(configValues.get("Volume")))) {
				configValues.put("Volume", "-27.6");
				writeValues();
			}
		}

		private static void writeValues() {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						cfgFile));

				for (int i = 0; i < sortedKeys.size(); i++) {
					writer.write(new StringBuilder(sortedKeys.get(i))
							.append(" = ")
							.append(configValues.get(sortedKeys.get(i)))
							.toString());
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {
				// File seems to be broken
			}
		}
	}

	private static class ResCfg {
		private static HashMap<String, String> defaultConfigValues = new HashMap<>(),
				configValues = new HashMap<>();
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
				resCfgFile = new File(new StringBuilder(
						resourcepacks.getAbsolutePath()).append("\\")
						.append(Loader.getCfgValue("Resourcepack"))
						.append("\\Configs.cfg").toString());
			}

			if (resCfgFile.exists()) {
				try {
					// Read configs
					BufferedReader reader = new BufferedReader(new FileReader(
							resCfgFile));

					for (int i = 0; i < sortedKeys.size(); i++) {
						String line = reader.readLine();
						if (line != null) {
							configValues.put(sortedKeys.get(i),
									line.substring(line.lastIndexOf("=") + 2));
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
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						resCfgFile));

				for (int i = 0; i < sortedKeys.size(); i++) {
					writer.write(new StringBuilder(sortedKeys.get(i))
							.append(" = ")
							.append(configValues.get(sortedKeys.get(i)))
							.toString());
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {
				// File seems to be broken
			}
		}
	}

	public static void initLoaderWithoutLoad(String firmenname, String spielname) {

		glAlphaColorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8,
						8, 8 }, true, false, Transparency.TRANSLUCENT,
				DataBuffer.TYPE_BYTE);

		glColorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8,
						8, 0 }, false, false, Transparency.OPAQUE,
				DataBuffer.TYPE_BYTE);
		String path = new StringBuilder("C:\\Users\\")
				.append(System.getProperty("user.name"))
				.append("\\AppData\\Roaming\\").append(firmenname).append("\\")
				.append(spielname).toString();
		saves = new File(new StringBuffer(path).append("\\").append("saves")
				.toString());
		configs = new File(new StringBuffer(path).append("\\")
				.append("configs").toString());
		resourcepacks = new File(new StringBuffer(path).append("\\")
				.append("resourcepacks").toString());

		if (!saves.exists()) {
			saves.mkdirs();
		}
		if (!configs.exists()) {
			configs.mkdirs();
		}
		if (!resourcepacks.exists()) {
			resourcepacks.mkdirs();
		}

		texturesDefault = new File("src\\resources\\textures");

		
		
		//1 Zeile
		DicAddEntry("ESC", "ESCAPE");
		
		//2 Zeile
		DicAddEntry("Zirkumflex (Dead)", "BACKSLASH");
		DicAddEntry("ß", "LBRACKET");
		DicAddEntry("Akut (Dead)", "RBRACKET");
		
		//3 Zeile (Tab geht nicht)
		DicAddEntry("Plus", "EQUALS");
		DicAddEntry("Eingabe", "RETURN");
		
		//NumPad
		DicAddEntry("NumPad +", "ADD");
		DicAddEntry("NumPad -", "SUBTRACT");
		DicAddEntry("NumPad *", "MULTIPLY");
		DicAddEntry("NumPad /", "DIVIDE");
		DicAddEntry("Num", "NUMLOCK");
		DicAddEntry("Löschen", "NONE"); //=5
		
		//4 Zeile
		DicAddEntry("Nummernzeichen", "SLASH");
		DicAddEntry("Feststelltaste", "CAPITAL");
		
		//5 Zeile
		DicAddEntry("Umschalt", "LSHIFT");
		DicAddEntry("Kleiner als", "NONE");
		DicAddEntry("Komma", "COMMA");
		DicAddEntry("Punkt", "PERIOD");
		DicAddEntry("Minus", "MINUS");
		DicAddEntry("Umschalt", "RSHIFT");
		
		//6 Zeile
		DicAddEntry("Strg", "CTRL");
		DicAddEntry("Alt", "LMENU");
		DicAddEntry("Windows", "LMETA");
		DicAddEntry("Leertaste", "SPACE");
		DicAddEntry("Kontextmenü", "APPS");
		
		
		//Druck Block
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
		
		
		//1 Zeile Englisch
		DicAddEntryE("ESC", "ESCAPE");
		
		//2 Zeile
		DicAddEntryE("Zirkumflex (Dead)", "BACKSLASH");
		DicAddEntryE("ß", "LBRACKET");
		DicAddEntryE("Akut (Dead)", "RBRACKET");
		
		//3 Zeile (Tab geht nicht)
		DicAddEntryE("Plus", "EQUALS");
		DicAddEntryE("Eingabe", "RETURN");
		
		//NumPad
		DicAddEntryE("NumPad +", "ADD");
		DicAddEntryE("NumPad -", "SUBTRACT");
		DicAddEntryE("NumPad *", "MULTIPLY");
		DicAddEntryE("NumPad /", "DIVIDE");
		DicAddEntryE("Num", "NUMLOCK");
		DicAddEntryE("Löschen", "NONE"); //=5
		
		//4 Zeile
		DicAddEntryE("Nummernzeichen", "SLASH");
		DicAddEntryE("Feststelltaste", "CAPITAL");
		
		//5 Zeile
		DicAddEntryE("Umschalt", "LSHIFT");
		DicAddEntryE("Kleiner als", "NONE");
		DicAddEntryE("Komma", "COMMA");
		DicAddEntryE("Punkt", "PERIOD");
		DicAddEntryE("Minus", "MINUS");
		DicAddEntryE("Umschalt", "RSHIFT");
		
		//6 Zeile
		DicAddEntryE("Strg", "CTRL");
		DicAddEntryE("Alt", "LMENU");
		DicAddEntryE("Windows", "LMETA");
		DicAddEntryE("Leertaste", "SPACE");
		DicAddEntryE("Kontextmenü", "APPS");
		
		
		//Druck Block
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

		String buchstabe = "QWERTZUIOPÜASDFGHJKLÖÄYXCVBNM";

		// Buchstaben
		for (int i = 0; i < buchstabe.length(); i++) {
			DicAddEntry(buchstabe.substring(i, i + 1),
					buchstabe.substring(i, i + 1));
			DicAddEntryE(buchstabe.substring(i, i + 1),
					buchstabe.substring(i, i + 1));
		}

		// Zahlen
		for (int i = 0; i < 10; i++) {
			DicAddEntry("" + i, i + "");
			DicAddEntryE(""+i, i+"");
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

	public static void load() {
		loadTextures();
		loadSounds();
	}

	private static void DicAddEntry(String deutsch, String englisch) {
		Dictionary.addEntry(englisch, deutsch);
		Dictionary.addEntry(deutsch, deutsch);
	}
	
	private static void DicAddEntryE(String deutsch, String englisch) {
		DictionaryE.addEntry(deutsch, englisch);
		DictionaryE.addEntry(englisch, englisch);
	}

	private static void loadTextures() {
		if (texturesRes.exists()) {
			String[] textureNames = texturesDefault.list();
			if (textureNames != null) {
				for (int i = 0; i < textureNames.length; i++) {
					textures.put(textureNames[i],
							createTexture(textureNames[i]));
				}
			}
		}

	}

	private static void loadSounds() {
		File sounds = new File("src\\resources\\sounds");
		String[] names = sounds.list();
		for (int i = 0; i < names.length; i++) {
			if (getCfgValue("Resourcepack").equals("Default")) {
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File(
							new StringBuilder("src\\resources\\sounds")
									.append("\\").append(names[i]).toString())));
					Loader.sounds.put(names[i], clip);
				} catch (Exception e1) {
					// Just stop trying
				}
			} else {
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File(
							new StringBuilder(resourcepacks.getAbsolutePath())
									.append("\\")
									.append(getCfgValue("Resourcepack"))
									.append("\\Sounds\\").append(names[i])
									.toString())));
					Loader.sounds.put(names[i], clip);
				} catch (Exception e) {
					// Didn't work, trying default
					try {
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(new File(
								new StringBuilder("src\\resources\\sounds")
										.append("\\").append(names[i])
										.toString())));
						Loader.sounds.put(names[i], clip);
					} catch (Exception e1) {
						// Just stop trying
					}
				}
			}
		}
	}

	public static Clip getMusic(String name) {
		if (getCfgValue("Resourcepack").equals("Default")) {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(
						new StringBuilder("src\\resources\\music").append("\\")
								.append(name).toString())));
				return clip;
			} catch (Exception e1) {
				// Just stop trying
			}
		} else {
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File(
						new StringBuilder(resourcepacks.getAbsolutePath())
								.append("\\")
								.append(getCfgValue("Resourcepack"))
								.append("\\Music\\").append(name).toString())));
				return clip;
			} catch (Exception e) {
				// Didn't work, trying default
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File(
							new StringBuilder("src\\resources\\music")
									.append("\\").append(name).toString())));
					return clip;
				} catch (Exception e1) {
					// Just stop trying
				}
			}
		}
		return null;
	}

	

	public static Texture createStringTexture(String text, int width,
			int height, Color color, Font font) {
		int srcPixelFormat;
		// create the texture ID for this texture
		int textureID = createTextureID();
		Texture texture = new Texture(GL_TEXTURE_2D, textureID);
		// bind this texture
		glBindTexture(GL_TEXTURE_2D, textureID);
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(color);
		graphics.setFont(font);
		graphics.drawString(text, 0, (int) (height * 0.75));
		graphics.dispose();
		texture.setWidth(bufferedImage.getWidth());
		texture.setHeight(bufferedImage.getHeight());
		if (bufferedImage.getColorModel().hasAlpha()) {
			srcPixelFormat = GL_RGBA;
		} else {
			srcPixelFormat = GL_RGB;
		}
		// convert that image into a byte buffer of texture data
		ByteBuffer textureBuffer = convertImageData(bufferedImage, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		// produce a texture from the byte buffer
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,
				get2Fold(bufferedImage.getWidth()),
				get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat,
				GL_UNSIGNED_BYTE, textureBuffer);
		return texture;
	}

	public static File getSavesDir(){
		return saves;
	}

	public static Texture getTexture(String name) {
		if (textures.get(name) != null) {
			return textures.get(name);
		} else {
			return createTexture(name);
		}
	}

	public static Clip getSound(String name) {
		return sounds.get(name);
	}

	public static String[] getMusicList() {
		return new File("src\\resources\\music").list();
	}

	private static Texture createTexture(String textureName) {
		int srcPixelFormat;
		// create the texture ID for this texture
		int textureID = createTextureID();
		Texture texture = new Texture(GL_TEXTURE_2D, textureID);
		// bind this texture
		glBindTexture(GL_TEXTURE_2D, textureID);
		BufferedImage bufferedImage = null;
		if (getCfgValue("Resourcepack").equals("Default")) {
			try {
				bufferedImage = ImageIO.read(new File(new StringBuilder(
						"src\\resources\\textures").append("\\")
						.append(textureName).toString()));
			} catch (IOException e1) {
				// Ignore
			}
		} else {
			try {
				bufferedImage = ImageIO.read(new File(new StringBuilder(
						texturesRes.getAbsolutePath()).append("\\")
						.append(textureName).toString()));
			} catch (IOException e) {
				try {
					bufferedImage = ImageIO.read(new File(new StringBuilder(
							"src\\resources\\textures").append("\\")
							.append(textureName).toString()));
				} catch (IOException e1) {
					// Ignore
				}
			}
		}
		if (bufferedImage == null) {
			bufferedImage = new BufferedImage(32, 32,
					BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(textureName, 0, 20);
			graphics.dispose();
		}
		texture.setWidth(bufferedImage.getWidth());
		texture.setHeight(bufferedImage.getHeight());

		if (bufferedImage.getColorModel().hasAlpha()) {
			srcPixelFormat = GL_RGBA;
		} else {
			srcPixelFormat = GL_RGB;
		}
		// convert that image into a byte buffer of texture data
		ByteBuffer textureBuffer = convertImageData(bufferedImage, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		// produce a texture from the byte buffer
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,
				get2Fold(bufferedImage.getWidth()),
				get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat,
				GL_UNSIGNED_BYTE, textureBuffer);
		return texture;
	}

	private static int createTextureID() {
		glGenTextures(textureIDBuffer);
		return textureIDBuffer.get(0);
	}

	@SuppressWarnings("rawtypes")
	private static ByteBuffer convertImageData(BufferedImage bufferedImage,
			Texture texture) {
		ByteBuffer imageBuffer;
		WritableRaster raster;
		BufferedImage texImage;
		int texWidth = 2;
		int texHeight = 2;
		// find the closest power of 2 for the width and height
		// of the produced texture
		while (texWidth < bufferedImage.getWidth()) {
			texWidth *= 2;
		}
		while (texHeight < bufferedImage.getHeight()) {
			texHeight *= 2;
		}
		texture.setTextureHeight(texHeight);
		texture.setTextureWidth(texWidth);
		// create a raster that can be used by OpenGL as a source
		// for a texture
		if (bufferedImage.getColorModel().hasAlpha()) {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
					texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false,
					new Hashtable());
		} else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
					texWidth, texHeight, 3, null);
			texImage = new BufferedImage(glColorModel, raster, false,
					new Hashtable());
		}
		// copy the source image into the produced image
		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0f));
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(bufferedImage, 0, 0, null);
		// build a byte buffer from the temporary image
		// that be used by OpenGL to produce a texture.
		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer())
				.getData();
		imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();
		return imageBuffer;
	}

	private static int get2Fold(int fold) {
		int ret = 2;
		while (ret < fold) {
			ret *= 2;
		}
		return ret;
	}

	public static BufferedImage getImage(String name) {
		BufferedImage bufferedImage = null;
		if (getCfgValue("Resourcepack").equals("Default")) {
			try {
				bufferedImage = ImageIO.read(new File(new StringBuilder(
						"src\\resources\\textures").append("\\").append(name)
						.toString()));
			} catch (IOException e1) {
				// Ignore
			}
		} else {
			try {
				bufferedImage = ImageIO.read(new File(new StringBuilder(
						texturesRes.getAbsolutePath()).append("\\")
						.append(name).toString()));
			} catch (IOException e) {
				try {
					bufferedImage = ImageIO.read(new File(new StringBuilder(
							"src\\resources\\textures").append("\\")
							.append(name).toString()));
				} catch (IOException e1) {
					// Ignore
				}
			}
		}
		if (bufferedImage == null) {
			bufferedImage = new BufferedImage(32, 32,
					BufferedImage.TYPE_INT_ARGB);
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
				bufferedImage = ImageIO.read(new File(
						"src\\resources\\textures\\Icon.png"));
			} catch (IOException e1) {
				// Ignore
			}
		} else {
			try {
				bufferedImage = ImageIO
						.read(new File(new StringBuilder(resourcepacks
								.getAbsolutePath()).append("\\").append(name)
								.append("\\Textures\\Icon.png").toString()));
			} catch (IOException e) {
				try {
					bufferedImage = ImageIO.read(new File(
							"src\\resources\\textures\\Icon.png"));
				} catch (IOException e1) {
					// Ignore
				}
			}
		}
		if (bufferedImage == null) {
			bufferedImage = new BufferedImage(32, 32,
					BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(name, 0, 20);
			graphics.dispose();
		}
		return bufferedImage;
	}
	
	public static void resetCfgValue(String key){
		changeCfgValue(key, getDefaultCfgValue(key));
	}
	
	public static void speichern(String name){
		speicher.speichern(name);
	}
	
	private static String getDefaultCfgValue(String key){
		if (Cfg.sortedKeys.contains(key)) {
			return Cfg.defaultConfigValues.get(key);
		}
		if (ResCfg.sortedKeys.contains(key)) {
			return ResCfg.defaultConfigValues.get(key);
		}
		return null;
	}
}
