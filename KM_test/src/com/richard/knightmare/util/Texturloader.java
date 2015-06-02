package com.richard.knightmare.util;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

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
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.husten.knightmare.graphicalObjects.Texture;

public class Texturloader {

	private static IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);
	private static ColorModel glAlphaColorModel, glColorModel;
	private static HashMap<String, Texture> textures = new HashMap<>();
	private static boolean Default = true;
	private static File texturesDefault = new File("src\\resources\\textures"), texturesRes;
	
	public static void initLoader() {
		Default = Loader.getCfgValue("Resourcepack").equals("Default");
		texturesRes = Loader.getTexturesRes();
		glAlphaColorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8,
						8, 8 }, true, false, Transparency.TRANSLUCENT,
				DataBuffer.TYPE_BYTE);

		glColorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8,
						8, 0 }, false, false, Transparency.OPAQUE,
				DataBuffer.TYPE_BYTE);
		load();
	}
	
	public static int createTextureID() {
		glGenTextures(textureIDBuffer);
		return textureIDBuffer.get(0);
	}
	
	private static Texture createTexture(String textureName) {
		int srcPixelFormat;
		// create the texture ID for this texture
		int textureID = createTextureID();
		Texture texture = new Texture(GL_TEXTURE_2D, textureID);
		// bind this texture
		glBindTexture(GL_TEXTURE_2D, textureID);
		BufferedImage bufferedImage = null;
		if (Default) {
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
	
	public static Texture getTexture(String name) {
		if (textures.get(name) != null) {
			return textures.get(name);
		} else {
			textures.put(name, createTexture(name));
			return textures.get(name);
		}
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
	
	private static void load() {
		loadTextures();
	}
	
	private static int get2Fold(int fold) {
		int ret = 2;
		while (ret < fold) {
			ret *= 2;
		}
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	public static ByteBuffer convertImageData(BufferedImage bufferedImage,
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

}
