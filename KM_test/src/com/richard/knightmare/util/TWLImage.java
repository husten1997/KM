package com.richard.knightmare.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.matthiasmann.twl.Color;
import de.matthiasmann.twl.renderer.Image;
import de.matthiasmann.twl.renderer.Renderer;
import de.matthiasmann.twl.renderer.Texture;

public class TWLImage {

	private static Renderer render;

	public static Image getImage(String name) {
		URL url = null;
		try {
			url = new URL(new StringBuilder(Loader.getTexturePath().replace('\\', '/')).append("/").append(name).toString());
		} catch (MalformedURLException e) {
			// Leck mich
		}
		URL url2 = TWLImage.class.getResource(new StringBuilder(Loader.getTexturePath().replace('\\', '/')).append("/").append(name).toString());
		Texture t = null;
		try {
			t = render.loadTexture((url == null ? url2 : url), "COLOR", "LINEAR");
		} catch (IOException e) {
			// Nope
		}
		return t.getImage(0, 0, t.getWidth(), t.getHeight(), Color.WHITE, false, Texture.Rotation.CLOCKWISE_180);
	}

	public static void setRenderer(Renderer renderer) {
		TWLImage.render = renderer;
	}
}
