package com.husten.knightmare.core;


import static org.lwjgl.opengl.GL11.*;


import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.husten.knightmare.graphicalObjects.QUAD;
import com.husten.knightmare.graphicalObjects.Text;
import com.husten.knightmare.graphicalObjects.TextureLoader;
import com.husten.knightmare.graphicalObjects.gasset;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Environment;
import com.richard.knightmare.util.Pos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainMenue {
	
	/** time at last frame */
	/** last fps time */
	private long lastFrame, lastFPS;
	/** frames per second */
	private int fps, ebenen = 3, VsyncF = 120, s = 5;
	@SuppressWarnings("unused")
	private double FPS = 60;

	

	public static int WIDTH = 1600, HEIGHT = 900;
	private boolean fullscreen = false, Vsync = false;
	
	private ArrayList[] renderList = new ArrayList[ebenen];
	

	
	

	private TextureLoader textureLoader;
	

	

	public static void main(String[] args) {
		JFrame f = new JFrame("Ähh");
		JButton start = new JButton("Start");
		f.add(start);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MainMenue().spielStarten();
			}
		});
	}
	
	private void spielStarten(){
		start();
//		new Knightmare();
	}
	
	private void start() {
		
		
		init();
		objectinit();

		
		while (!Display.isCloseRequested()) {
//			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			render();
			updateDisplay();
			updateFPS();
		}
		Display.destroy();
	}
	
	public void objectinit() {

		for (int i = 0; i < ebenen; i++) {
			renderList[i] = new ArrayList<gasset>();
		}

		// Sorting
		for (int i = 0; i < ebenen; i++) {
			renderList[i].sort(new Comparator<gasset>() {
				@Override
				public int compare(gasset i1, gasset i2) {
					return (i1.getSort() < i2.getSort() ? -1 : 1);
				}
			});
		}
		
		QUAD Background = new QUAD(new Pos(0, 0),WIDTH, HEIGHT, textureLoader, "gras.png");
		Background.setTCX(WIDTH/32);
		Background.setTCY(HEIGHT/32);
		Text Start = new Text("Times New Roman", Font.BOLD, 24, "START", 100, 100, Color.yellow);
		
//		initRender(Background, 0, 0);
		initRender(Start, 1, 0);
	}
	
	private void init() {
		Environment.setUpEnvironment("Ares", "Knightmare");
		MoodMusic.init();
		// verwendet eure aktuelle desktopauflösung als gameauflösung
		initRes();
		
		 // load a default java font
		

		try {
			// Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			// DisplayMode DM = new DisplayMode(WIDTH, HEIGHT);
			// DM.
			Display.create();
			Display.setDisplayModeAndFullscreen(new DisplayMode(WIDTH, HEIGHT));
			glViewport(0, 0, WIDTH, HEIGHT);

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		// enable textures since we're going to use these for our sprites
		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL11.GL_SMOOTH);
		glEnable(GL_BLEND);

//		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// disable the OpenGL depth test since we're rendering 2D graphics
		glDisable(GL_DEPTH_TEST);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 3, -1);
		glTranslatef(0, 0, 0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		textureLoader = new TextureLoader();

		
		lastFPS = getTime(); // call before loop to initialise fps timer
		initDisplay();

	}
	
	public void updateDisplay() {
		if (Vsync)
			Display.sync(VsyncF);

		Display.update();
	}
	
	public void initRes() {
		HEIGHT = Display.getDesktopDisplayMode().getHeight();
		WIDTH = Display.getDesktopDisplayMode().getWidth();
	}

	public void initDisplay() {
		setDisplayMode(WIDTH, HEIGHT, fullscreen);
		System.out.println("H: " + HEIGHT + " W: " + WIDTH);
	}
	
	public void setDisplayMode(int width, int height, boolean fullscreen) {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against
						// the
						// original display mode then it's probably best to go
						// for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
			this.fullscreen = fullscreen;
			// VsyncF = Display.getDisplayMode().getFrequency();

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
		}
	}
	
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			FPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;

	}
	
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void initRender(gasset input, int e, int se) {
		input.setSort(se);
		renderList[e].add(input);
	}
	
	public void render() {
		
		for (int e = 0; e < ebenen; e++) {
			for (int i = 0; i < renderList[e].size(); i++) {
				((gasset) renderList[e].get(i)).draw();
			}
		}
		

	}


}
