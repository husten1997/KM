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
import java.util.HashMap;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.lwjgl.BufferUtils;

import com.husten.knightmare.graphicalObjects.Texture;

public class Loader {

	private static File saves, configs, resourcepacks, cfgFile, texturesRes, texturesDefault;
	private static HashMap<String, Texture> textures = new HashMap<>();
	private static HashMap<String, Clip> sounds = new HashMap<>();
	private static ColorModel glAlphaColorModel, glColorModel;
	private static IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);

	private static HashMap<String, String> defaultConfigValues = new HashMap<>(), configValues = new HashMap<>();

	public static void initLoader(String firmenname, String spielname) {
		initLoaderWithoutLoad(firmenname, spielname);
		load();
	}

	public static void initLoaderWithoutLoad(String firmenname, String spielname) {
		// Config Values
		defaultConfigValues.put("Resourcepack", "Default");
		defaultConfigValues.put("Volume", "-27.6");

		glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, Transparency.TRANSLUCENT,
				DataBuffer.TYPE_BYTE);

		glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 }, false, false, Transparency.OPAQUE,
				DataBuffer.TYPE_BYTE);
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

		// Config File
		cfgFile = new File(new StringBuilder(configs.getAbsolutePath()).append("\\Configs.cfg").toString());

		if (cfgFile.exists()) {
			try {
				// Read configs
				BufferedReader reader = new BufferedReader(new FileReader(cfgFile));

				Object[] keys = defaultConfigValues.keySet().toArray();
				for (int i = 0; i < keys.length; i++) {
					String line = reader.readLine();
					configValues.put((String) keys[i], line.substring(line.lastIndexOf("=") + 2));
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
		texturesRes = new File(new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(getCfgValue("Resourcepack")).append("\\Textures").toString());
		texturesDefault = new File("src\\resources\\textures");
		//Configs are valid?
		if(!texturesRes.exists()){
			configValues.put("Resourcepack", "Default");
			writeValues();
			texturesRes = new File(new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(getCfgValue("Resourcepack")).append("\\Textures").toString());
		}
		if(!(Float.parseFloat(configValues.get("Volume"))<=6 && -80>= Float.parseFloat(configValues.get("Volume")))){
			configValues.put("Volume", String.valueOf(-27.6f));
			writeValues();
		}
	}

	private static void writeValues() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(cfgFile));

			Object[] keys = configValues.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				writer.write(new StringBuilder((String) keys[i]).append(" = ").append(configValues.get((String) keys[i])).toString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			// File seems to be broken
		}
	}

	public static void load() {
		loadTextures();
		loadSounds();
	}

	private static void loadTextures() {
		if (texturesRes.exists()) {
			String[] textureNames = texturesDefault.list();
			if (textureNames != null) {
				for (int i = 0; i < textureNames.length; i++) {
					textures.put(textureNames[i], createTexture(textureNames[i]));
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
					clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder("src\\resources\\sounds").append("\\").append(names[i]).toString())));
					Loader.sounds.put(names[i], clip);
				} catch (Exception e1) {
					// Just stop trying
				}
			} else {
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder(resourcepacks.getAbsolutePath()).append("\\").append(getCfgValue("Resourcepack"))
							.append("\\Sounds\\").append(names[i]).toString())));
					Loader.sounds.put(names[i], clip);
				} catch (Exception e) {
					// Didn't work, trying default
					try {
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(new File(new StringBuilder("src\\resources\\sounds").append("\\").append(names[i]).toString())));
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

	public static Texture createStringTexture(String text, int width, int height, Color color, Font font) {
		int srcPixelFormat;
		// create the texture ID for this texture
		int textureID = createTextureID();
		Texture texture = new Texture(GL_TEXTURE_2D, textureID);
		// bind this texture
		glBindTexture(GL_TEXTURE_2D, textureID);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, get2Fold(bufferedImage.getWidth()), get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat, GL_UNSIGNED_BYTE,
				textureBuffer);
		return texture;
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
				bufferedImage = ImageIO.read(new File(new StringBuilder("src\\resources\\textures").append("\\").append(textureName).toString()));
			} catch (IOException e1) {
				// Ignore
			}
		} else {
			try {
				bufferedImage = ImageIO.read(new File(new StringBuilder(texturesRes.getAbsolutePath()).append("\\").append(textureName).toString()));
			} catch (IOException e) {
				try {
					bufferedImage = ImageIO.read(new File(new StringBuilder("src\\resources\\textures").append("\\").append(textureName).toString()));
				} catch (IOException e1) {
					// Ignore
				}
			}
		}
		if (bufferedImage == null) {
			bufferedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
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
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, get2Fold(bufferedImage.getWidth()), get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat, GL_UNSIGNED_BYTE,
				textureBuffer);
		return texture;
	}

	private static int createTextureID() {
		glGenTextures(textureIDBuffer);
		return textureIDBuffer.get(0);
	}

	@SuppressWarnings("rawtypes")
	private static ByteBuffer convertImageData(BufferedImage bufferedImage, Texture texture) {
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
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
		} else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
			texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
		}
		// copy the source image into the produced image
		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0f));
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(bufferedImage, 0, 0, null);
		// build a byte buffer from the temporary image
		// that be used by OpenGL to produce a texture.
		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
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
		configValues.put(key, value);
		writeValues();
	}

	public static String getCfgValue(String key) {
		return configValues.get(key);
	}
}
