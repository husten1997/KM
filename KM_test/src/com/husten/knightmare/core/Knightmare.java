package com.husten.knightmare.core;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.graphicalObjects.*;
import com.husten.knightmare.threads.GrafikThread;
import com.husten.knightmare.threads.WorkingThread;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Soldat;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Environment;
import com.richard.knightmare.util.Pos;
import com.richard.knightmare.util.Vektor;

public class Knightmare implements StringConstants {

	/** time at last frame */
	/** last fps time */
	private long lastFrame, lastFPS;
	/** frames per second */
	private int fps, ebenen = 3, VsyncF = 120, s = 5;
	@SuppressWarnings("unused")
	private double FPS = 60, zomingSpeed = 0.1, scrollingSpeed = 5;

	private String inGameStat = state.NOTHING;

	public static int WIDTH = 1600, HEIGHT = 900;
	private boolean fullscreen = true, Vsync = false, screenToSet = false;

	private Soldat figur, figuren[] = new Soldat[s];
	private Terrain terrain;

	private Pos pos1 = new Pos(0, 0), pos2 = new Pos(0, 0);
	public static double CameraX = 0, CameraY = 0, scale = 1;

	private HashMap<Soldat, Vektor> vektoren = new HashMap<>();

	@SuppressWarnings("unchecked")
	private ArrayList<gasset> selection = new ArrayList<gasset>(), renderList[] = new ArrayList[ebenen], ObjectList[] = new ArrayList[ebenen], pending = new ArrayList<>();
	private ArrayList<Integer> pendingEbenen = new ArrayList<>();

	private TextureLoader textureLoader;
	private static WorkingThread gT;

	private int gameSpeed = 10; // inverted
	
	private gasset[][] world;

	public static void main(String args[]){
		new Knightmare();
	}
	
	public Knightmare(){
		start();
	}

	private void start() {
		gT = new GrafikThread(this);
		
		init();
		objectinit();

		new Timer(true).scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				pollInput();
				calc();

			}
		}, 0, gameSpeed);
		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			if(screenToSet){
				setDisplayMode(WIDTH, HEIGHT, !fullscreen);
				screenToSet = false;
			}
			while(pending.size()>0){
				initRender(pending.get(0), pendingEbenen.get(0));
				pending.remove(0);
				pendingEbenen.remove(0);
			}
			gT.run();
			updateDisplay();
			updateFPS();
		}
		Display.destroy();
	}

	private void init() {
		Environment.setUpEnvironment("Ares", "Knightmare");
		MoodMusic.init();
		// verwendet eure aktuelle desktopauflösung als gameauflösung
		initRes();

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
		glEnable(GL_BLEND);

		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
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

		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
		initDisplay();

	}

	public void initRender(gasset input, int e) {
		if(world[(int) (input.getX()/32)][(int) (input.getY()/32)]==null){
			world[(int) (input.getX()/32)][(int) (input.getY()/32)]=input;
			renderList[e].add(input);
		}
	}

	public void initObject(gasset input, int e, int se) {
		input.setSort(se);
		ObjectList[e].add(input);
		input.setSort(se);
		initRender(input, e);
	}

	public void objectinit() {

		for (int i = 0; i < ebenen; i++) {
			renderList[i] = new ArrayList<gasset>();
		}
		terrain = new Terrain(textureLoader, (512) + 1, (512) + 1);
		world = new gasset[513][513];
		for (int i = 0; i < s; i++) {
			double x = Math.random() * 1200;
			double y = Math.random() * 800;
			figuren[i] = new Soldat(new Pos(x, y), 32, 32, textureLoader, "figure.png");
			figuren[i].setSort(1);
			initRender(figuren[i], 1);
		}

		figur = new Soldat(new Pos(0, 0), 32, 32, textureLoader, "figure.png");

		// Sorting
		for (int i = 0; i < ebenen; i++) {
			renderList[i].sort(new Comparator<gasset>() {
				@Override
				public int compare(gasset i1, gasset i2) {
					return (i1.getSort() < i2.getSort() ? -1 : 1);
				}
			});
		}
	}

	Pos ang = null;

	public void pollInput() {

		if (Keyboard.getEventKey() == Keyboard.KEY_F11) {
			if (fullscreen) {
				WIDTH = 1600;
				HEIGHT = 900;
			} else {
				initRes();
			}
			screenToSet = true;
			if (scale < 0.1f) {
				scale = 0.1f;
			}
			if (scale > terrain.getSx() * 32 / WIDTH) {
				scale = terrain.getSx() * 32 / WIDTH;
			}
			if (CameraX < 0) {
				CameraX = 0;
			}
			if (CameraY < 0) {
				CameraY = 0;
			}
			if (CameraX > terrain.getSx() * 32 - WIDTH * scale) {
				CameraX = terrain.getSx() * 32 - WIDTH * scale;
			}
			if (CameraY > terrain.getSy() * 32 - HEIGHT * scale) {
				CameraY = terrain.getSy() * 32 - HEIGHT * scale;
			}
		}
		// Keyboard------------------------------------------------------------------------------------
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {

				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					System.exit(0);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					scale = 1f;
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_C) {
					inGameStat = state.S_TRUPS;
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					inGameStat = state.S_BUILDINGS;
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_B) {
					inGameStat = state.N_BUILDINGS;
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_N) {
					inGameStat = state.N_TRUPS;
					System.out.println(inGameStat);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
					MoodMusic.changeVolume(-0.5f);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F2) {
					MoodMusic.changeVolume(0.5f);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F12) {
					Vsync = !Vsync;
					// Display.setVSyncEnabled(Vsync);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_NUMPAD0) {
					System.out.println(selection.size());
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_M) {
					MoodMusic.nextClip();
				}
			}
		}
		// Mosue-------------------------------------------------------------------------------------------------------
		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {

				if (Mouse.getEventButton() == 0) {
					int x = (int) (Mouse.getX() * scale + CameraX);
					int y = (int) (Mouse.getY() * scale + CameraY);

					pos1.setX(x);
					pos1.setY(y);

					int xR = x / 32;
					int yR = y / 32;

					switch (inGameStat) {
					case state.N_BUILDINGS:
						Building b = new Building(new Pos(xR * 32, yR * 32), 64, 32, textureLoader, "haus.png");
						b.setSort(2);
						pending.add(b);
						pendingEbenen.add(1);
						break;
					case state.N_TRUPS:
						Soldat s = new Soldat(new Pos(xR * 32, yR * 32), 32, 32, textureLoader, "figure.png");
						s.setSort(1);
						pending.add(s);
						pendingEbenen.add(1);
						break;
					case state.S_TRUPS:
						search(x, y);
						figur = (Soldat) selection.get(selection.size() - 1);
						break;
					case state.S_BUILDINGS:
						search(x, y);
						break;
					}
				}

				if (Mouse.getEventButton() == 2) {
					ang = new Pos(CameraX + Mouse.getX() * scale, CameraY + Mouse.getY() * scale);
				}

				if (Mouse.getEventButton() == 1) {
					int x = (int) (Mouse.getX() * scale + CameraX);
					int y = (int) (Mouse.getY() * scale + CameraY);

					Pos p1 = new Pos(x, y); // Ende

					switch (inGameStat) {
					case state.NOTHING:
						break;
					case state.S_TRUPS:
						for (int i = 0; i < selection.size(); i++) {
							Pos p2 = selection.get(i).getPos(); // Start
							if (selection.get(i).getType().equals(StringConstants.MeshType.EINEHEIT)) {
								Soldat h = (Soldat) selection.get(i);
								if(world[(int) (p1.getX()/32)][(int) (p1.getY()/32)]==null){
									if (vektoren.get(h) == null) {
										vektoren.put(h, new Vektor(p2, p1, h));
									} else {
										vektoren.get(h).setEnde(p1);
									}
								}
							}
						}
						break;
					case state.S_BUILDINGS:
						break;

					}
				}

			} else {
				// Buton releasd
				if (Mouse.getEventButton() == 0) {
					int x = (int) (Mouse.getX() * scale + CameraX);
					int y = (int) (Mouse.getY() * scale + CameraY);

					pos2.setX(x);
					pos2.setY(y);

					switch (inGameStat) {
					case state.S_TRUPS:
						search(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
						for (int i = 0; i < selection.size(); i++) {
							if (selection.get(i).getType().equals(StringConstants.MeshType.EINEHEIT)) {
								((Soldat) selection.get(i)).say();
							}
						}
						break;
					}
				}
			}
		}
		// Keyboard/Mouse
		// holding---------------------------------------------------------------------------
		if (Mouse.isButtonDown(0)) {

		}

		if (Mouse.isButtonDown(1)) {

		}

		int dWheel = Mouse.getDWheel();

		if (dWheel < 0) {
			double width = WIDTH * scale;
			double height = HEIGHT * scale;
			scale += zomingSpeed;
			if (scale > terrain.getSx() * 32 / WIDTH) {
				scale = terrain.getSx() * 32 / WIDTH;
			}
			CameraX += (width - WIDTH * scale) / 2;
			CameraY += (height - HEIGHT * scale) / 2;
			if (CameraX < 0) {
				CameraX = 0;
			}
			if (CameraY < 0) {
				CameraY = 0;
			}
			if (CameraX > terrain.getSx() * 32 - WIDTH * scale) {
				CameraX = terrain.getSx() * 32 - WIDTH * scale;
			}
			if (CameraY > terrain.getSy() * 32 - HEIGHT * scale) {
				CameraY = terrain.getSy() * 32 - HEIGHT * scale;
			}
		} else if (dWheel > 0) {
			double width = WIDTH * scale;
			double height = HEIGHT * scale;
			scale -= zomingSpeed;
			if (scale < 0.1f) {
				scale = 0.1f;
			}
			CameraX += (width - WIDTH * scale) / 2;
			CameraY += (height - HEIGHT * scale) / 2;
			if (CameraX < 0) {
				CameraX = 0;
			}
			if (CameraY < 0) {
				CameraY = 0;
			}
			if (CameraX > terrain.getSx() * 32 - WIDTH * scale) {
				CameraX = terrain.getSx() * 32 - WIDTH * scale;
			}
			if (CameraY > terrain.getSy() * 32 - HEIGHT * scale) {
				CameraY = terrain.getSy() * 32 - HEIGHT * scale;
			}
		}

		if (Mouse.isButtonDown(2)) {
			CameraX = -(Mouse.getX() * scale - ang.getX());
			CameraY = -(Mouse.getY() * scale - ang.getY());
			if (CameraX < 0) {
				CameraX = 0;
			}
			if (CameraY < 0) {
				CameraY = 0;
			}
			if (CameraX > terrain.getSx() * 32 - WIDTH * scale) {
				CameraX = terrain.getSx() * 32 - WIDTH * scale;
			}
			if (CameraY > terrain.getSy() * 32 - HEIGHT * scale) {
				CameraY = terrain.getSy() * 32 - HEIGHT * scale;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("Space is down");
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			figur.setRelativeY(0.3f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			figur.setRelativeX(-0.3f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			figur.setRelativeY(-0.3f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			figur.setRelativeX(0.3f);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			CameraY += scrollingSpeed * scale;
			if (CameraY > terrain.getSy() * 32 - HEIGHT * scale) {
				CameraY = terrain.getSy() * 32 - HEIGHT * scale;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			CameraX -= scrollingSpeed * scale;
			if (CameraX < 0) {
				CameraX = 0;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			CameraY -= scrollingSpeed * scale;
			if (CameraY < 0) {
				CameraY = 0;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			CameraX += scrollingSpeed * scale;
			if (CameraX > terrain.getSx() * 32 - WIDTH * scale) {
				CameraX = terrain.getSx() * 32 - WIDTH * scale;
			}
		}

		if (Mouse.getX() < 32) {
			CameraX -= scrollingSpeed * scale;
			if (CameraX < 0) {
				CameraX = 0;
			}
		}
		if (Mouse.getX() > WIDTH - 32) {
			CameraX += scrollingSpeed * scale;
			if (CameraX > terrain.getSx() * 32 - WIDTH * scale) {
				CameraX = terrain.getSx() * 32 - WIDTH * scale;
			}
		}
		if (Mouse.getY() < 32) {
			CameraY -= scrollingSpeed * scale;
			if (CameraY < 0) {
				CameraY = 0;
			}
		}
		if (Mouse.getY() > HEIGHT - 32) {
			CameraY += scrollingSpeed * scale;
			if (CameraY > terrain.getSy() * 32 - HEIGHT * scale) {
				CameraY = terrain.getSy() * 32 - HEIGHT * scale;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
			// TODO
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			MoodMusic.changeVolume(-0.5f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
			MoodMusic.changeVolume(0.5f);
		}
		// testVariable();

	}

	public void render() {
		terrain.draw();
		for (int e = 0; e < ebenen; e++) {
			for (int i = 0; i < renderList[e].size(); i++) {
				renderList[e].get(i).draw();
			}
		}
		figur.draw();

	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	// Delta --> zeit die seit dem letzten frame vergangen ist => kann man
	// benutzen für frame unabhängige bewegungen zb
	public int getDelta() {
		long time = getTime();

		int delta = (int) (time - lastFrame);
		// System.out.println(time - lastFrame);
		lastFrame = time;

		return delta;
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

	public void search(double x1, double y1, double x2, double y2) {
		if (x1 == x2 && y1 == y2) {
			search(x1, y1);
		} else {
			selection.clear();
			double Px1;
			double Px2;

			double Py1;
			double Py2;

			if (x1 < x2) {
				Px1 = x2;
				Px2 = x1;
			} else {
				Px1 = x1;
				Px2 = x2;
			}

			if (y1 < y2) {
				Py1 = y2;
				Py2 = y1;
			} else {
				Py1 = y1;
				Py2 = y2;
			}

			try {
				for (int e = 0; e < ebenen; e++) {

					for (int i = 0; i < renderList[e].size(); i++) {
						if (renderList[e].get(i).getX() <= Px1 && renderList[e].get(i).getX() >= Px2 && renderList[e].get(i).getY() <= Py1
								&& renderList[e].get(i).getY() >= Py2) {

							if (renderList[e].get(i).getType().equals(StringConstants.MeshType.EINEHEIT)) {
								selection.add(renderList[e].get(i));
							}
						}
					}
				}
			} catch (Exception e) {

			}
		}

	}

	// Für einzelauswahl
	public void search(double x, double y) {
		gasset xy = figur;
		selection.clear();

		try {
			for (int e = 0; e < ebenen; e++) {

				for (int i = 0; i < renderList[e].size(); i++) {
					if (renderList[e].get(i).getX() <= x && renderList[e].get(i).getX() >= x - 64 && renderList[e].get(i).getY() <= y
							&& renderList[e].get(i).getY() >= y - 64) {
						xy = renderList[e].get(i);
					}
				}
			}
		} catch (Exception e) {

		}
		selection.add(xy);
	}

	public void calc() {
		Object[] vek = vektoren.values().toArray();
		Vektor[] vekk = new Vektor[vek.length];
		for (int i = 0; i < vek.length; i++) {
			vekk[i] = (Vektor) vek[i];
		}

		for (int i = 0; i < vekk.length; i++) {
			if (vekk[i].move()) {
				vektoren.remove(vekk[i].getSoldat());
			}
		}
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

	public float getH() {
		return HEIGHT;
	}

	public float getW() {
		return WIDTH;
	}

	public void initRes() {
		HEIGHT = Display.getDesktopDisplayMode().getHeight();
		WIDTH = Display.getDesktopDisplayMode().getWidth();
	}

	public void initDisplay() {
		setDisplayMode(WIDTH, HEIGHT, fullscreen);
		System.out.println("H: " + HEIGHT + " W: " + WIDTH);
	}

	public void updateDisplay() {
		if (Vsync)
			Display.sync(VsyncF);

		Display.update();
	}

	public void grafikCycl() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		// glTranslatef(CameraMX, CameraMY, 0f);
		glOrtho(0, WIDTH * scale, 0, HEIGHT * scale, 3, -1);
		glTranslatef((float) -CameraX, (float) -CameraY, 0f);
		glMatrixMode(GL_MODELVIEW);

		glLoadIdentity();

		render();
	}

}
