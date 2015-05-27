package com.husten.knightmare.core;


import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Comparator;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import com.husten.knightmare.graphicalObjects.QUAD;
import com.husten.knightmare.graphicalObjects.Text;
import com.husten.knightmare.graphicalObjects.gasset;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Pos;


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
	

	
	

//	private TextureLoader textureLoader;
	

	

	public static void main(String[] args) {
				new MainMenue().spielStarten();
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
			pollinput();
			updateDisplay();
			updateFPS();
		}
		Display.destroy();
	}
	
	@SuppressWarnings("unchecked")
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
		
		QUAD Background = new QUAD(new Pos(0, 0),WIDTH, HEIGHT, /*textureLoader*//*loader, */"menue.png");
		Background.setTCX(1);
		Background.setTCY(1);
		Font[] all = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		
		Font awtFont = new Font(all[1].getFontName(), Font.BOLD, 25);
		Text Start = new Text((double) 1000,(double) 1000,(double) 100,(double) 100, "Start", java.awt.Color.blue, awtFont);
		
		initRender(Background, 0, 0);
		initRender(Start, 1, 0);
	}
	
	private void init() {
//		Environment.setUpEnvironment("Ares", "Knightmare");
		// verwendet eure aktuelle desktopauflösung als gameauflösung
		initRes();
		
		 // load a default java font
		

		try {
			 Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			// DisplayMode DM = new DisplayMode(WIDTH, HEIGHT);
			// DM.
			Display.create();
			

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		// enable textures since we're going to use these for our sprites
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);        
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);                    
  
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        GL11.glClearDepth(1);                                       
  
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
  
        GL11.glViewport(WIDTH, HEIGHT, -WIDTH, -HEIGHT);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
  
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
//		glTranslatef(0, 0, 0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

//		textureLoader = new TextureLoader();
		Loader.initLoader("Ares", "Knightmare");
		MoodMusic.init();

		
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
	
	public void pollinput(){
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			System.exit(1);
		}
	}


}
