package com.husten.knightmare.core;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.CursorLoader;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.graphicalObjects.DNCycl;
import com.husten.knightmare.graphicalObjects.GraphicalObject;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.husten.knightmare.graphicalObjects.Terrain;
import com.husten.knightmare.graphicalObjects.Texture;
import com.husten.knightmare.menues.MainGUI;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Rekrutieren;
import com.matze.knightmare.meshes.Soldat;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Dictionary;
import com.richard.knightmare.util.DictionaryE;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Pathhandler;
import com.richard.knightmare.util.Pos;
import com.richard.knightmare.util.Texturloader;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.FPSCounter;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.input.lwjgl.LWJGLInput;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class Knightmare extends Widget implements StringConstants {

	private long lastFrame, lastFPS;
	private int fps, ebenen = 3, VsyncF = 120, gameSpeed = 10; // inverted
	@SuppressWarnings("unused")
	private double FPS = 60, zomingSpeed = 0.1, scrollingSpeed = 5;
	private String inGameStat = state.NOTHING;
	public static int WIDTH = 1600, HEIGHT = 900;
	private boolean fullscreen = Loader.getCfgValue("SETTINGS: Fenstermodus").equals("false"), Vsync = false, screenToSet = false, running = true;
	private Soldat figur;
	public static Terrain terrain;
	private Pos pos1 = new Pos(0, 0), pos2 = new Pos(0, 0), ang = null;
	public static double CameraX = 0, CameraY = 0, scale = 1;
	private Pathhandler handler;
	@SuppressWarnings("unchecked")
	private ArrayList<GraphicalObject> selection = new ArrayList<>(), renderList[] = new ArrayList[ebenen], ObjectList[] = new ArrayList[ebenen],
			pending = new ArrayList<>();
	private ArrayList<Integer> pendingEbenen = new ArrayList<>();
	private Cursor delete, normal, haus;
	private Timer timer = new Timer(true);

	

	public static Color mainColor = new Color(255, 255, 255);

	private DNCycl DN;

	public Knightmare() {
		Vsync = (Loader.getCfgValue("SETTINGS: V-Sync").equals("On"));
		start();
	}

	private void start() {
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		Texturloader.initLoader();
		objectinit();

		BufferedImage image = Loader.getImage("CursorKM.png");
		try {
			normal = CursorLoader.get().getCursor(Texturloader.convertImageData(image, new Texture(GL_TEXTURE_2D, Texturloader.createTextureID())), 0, 0,
					image.getWidth(), image.getHeight());
			image = Loader.getImage("delete.png");
			delete = CursorLoader.get().getCursor(Texturloader.convertImageData(image, new Texture(GL_TEXTURE_2D, Texturloader.createTextureID())), 0, 0,
					image.getWidth(), image.getHeight());
			image = Loader.getImage("haus.png");
			haus = CursorLoader.get().getCursor(Texturloader.convertImageData(image, new Texture(GL_TEXTURE_2D, Texturloader.createTextureID())), 16, 16,
					image.getWidth(), image.getHeight());
		} catch (Exception e) {
			// Ignore
		}
	}

	public void loop() {
		init();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				calc();
				DN.calc(6);
				l_time.setText(DN.getTimeS());
			}
		}, 0, gameSpeed);
		while (!Display.isCloseRequested() && running) {
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // |GL11.GL_DEPTH_BUFFER_BIT
			grafikCycl();
			
			handlInput();
			
			updateDisplay();
			updateFPS();

		}
		gui.destroy();
		Display.destroy();
	}

	private void init() {
		initRes();

		try {
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

		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
		initDisplay();

	}

	public void initRender(GraphicalObject input, int e) {
		input.initRender();
		renderList[e].add(input);
	}

	public void initObject(GraphicalObject input, int e, int se) {
		input.setSort(se);
		ObjectList[e].add(input);
		input.setSort(se);
		initRender(input, e);
	}

	public void objectinit() {
		for (int i = 0; i < ebenen; i++) {
			renderList[i] = new ArrayList<GraphicalObject>();
		}
		terrain = new Terrain((512) + 1, (512) + 1);
		terrain.initRender();
		handler = new Pathhandler(513, 513);
		// Sorting
		for (int i = 0; i < ebenen; i++) {
			renderList[i].sort(new Comparator<GraphicalObject>() {
				@Override
				public int compare(GraphicalObject i1, GraphicalObject i2) {
					return (i1.getSort() < i2.getSort() ? -1 : 1);
				}
			});
		}
		DN = new DNCycl();

	}

	public void tooggleFullscreen() {
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
		if (scale > terrain.getWidth() * 32 / WIDTH) {
			scale = terrain.getWidth() * 32 / WIDTH;
		}
		if (CameraX < 0) {
			CameraX = 0;
		}
		if (CameraY < 0) {
			CameraY = 0;
		}
		if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
			CameraX = terrain.getWidth() * 32 - WIDTH * scale;
		}
		if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
			CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
		}
		fullscreen = !fullscreen;
	}

	private String getString(String a) {
		return Dictionary.getFullName(Loader.getCfgValue(a));
	}

	// K√∂nnte iwann mal zu problemen f√ºhren
	private String gFN(int a) {
		return Dictionary.getFullName(Keyboard.getKeyName(a));
	}

	private int getKeyCode(String k) {
		return Keyboard.getKeyIndex(DictionaryE.getFullName(Loader.getCfgValue(k)));
	}

	private void pollInput() throws Exception {
//		Mouse.poll();
//		Keyboard.poll();
		if (getString("CONTROL_KEY: Fenster- u. Vollbildmodus").equals(gFN(Keyboard.getEventKey()))) {
			Loader.changeCfgValue("SETTINGS: Fenstermodus", String.valueOf(fullscreen));
			tooggleFullscreen();
		}
		// Keyboard------------------------------------------------------------------------------------
		while (Keyboard.next()) {
			guiPollInput();
			if (Keyboard.getEventKeyState()) {

				if (getString("CONTROL_KEY: Escape/Zur¸ck").equals(gFN(Keyboard.getEventKey()))) {
					MainMenue m = new MainMenue();
					timer.cancel();
					running = false;
					MoodMusic.changeMood("MainMenue");
					m.toFront();
					m.setAlwaysOnTop(true);
					m.setAutoRequestFocus(true);
					return;
				}

				if (getString("CONTROL_KEY: Abreiﬂen").equals(gFN(Keyboard.getEventKey()))) {
					inGameStat = state.ABREIﬂEN;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_T) {
					DN.toggle();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					scale = 1f;
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_Q) {
					// TODO name
					Loader.speichern("Tets");
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
					if (inGameStat.equals(state.N_BUILDINGS)) {
						inGameStat = state.NOTHING;
					} else {
						inGameStat = state.N_BUILDINGS;
					}
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_N) {
					inGameStat = state.N_TRUPS;
					System.out.println(inGameStat);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_J) {
					inGameStat = state.NF_TROOP;
					System.out.println(inGameStat);
				}
				if (getString("CONTROL_KEY: Volume -").equals(gFN(Keyboard.getEventKey()))) {
					MoodMusic.changeVolume(-0.5f);
				}
				if (getString("CONTROL_KEY: Volume +").equals(gFN(Keyboard.getEventKey()))) {
					MoodMusic.changeVolume(0.5f);
				}
				if (getString("CONTROL_KEY: V-Sync").equals(gFN(Keyboard.getEventKey()))) {
					Vsync = !Vsync;
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_NUMPAD0) {
					System.out.println(selection.size());
				}

				if (getString("CONTROL_KEY: Musik wechseln").equals(gFN(Keyboard.getEventKey()))) {
					MoodMusic.nextClip();
				}

				if (getString("CONTROL_KEY: Scrollen -").equals(gFN(Keyboard.getEventKey()))) {
					double width = WIDTH * scale;
					double height = HEIGHT * scale;
					scale += zomingSpeed;
					if (scale > terrain.getWidth() * 32 / WIDTH) {
						scale = terrain.getWidth() * 32 / WIDTH;
					}
					CameraX += (width - WIDTH * scale) / 2;
					CameraY += (height - HEIGHT * scale) / 2;
					if (CameraX < 0) {
						CameraX = 0;
					}
					if (CameraY < 0) {
						CameraY = 0;
					}
					if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
						CameraX = terrain.getWidth() * 32 - WIDTH * scale;
					}
					if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
						CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
					}

				}
				if (getString("CONTROL_KEY: Scrollen +").equals(gFN(Keyboard.getEventKey()))) {
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
					if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
						CameraX = terrain.getWidth() * 32 - WIDTH * scale;
					}
					if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
						CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
					}

				}

			}
		}
		// Mosue-------------------------------------------------------------------------------------------------------
		while (Mouse.next()) {
			guiPollInput();
			
			if (Mouse.getEventButtonState()) {
				//Linksklick
				if (Mouse.getEventButton() == 0) {

					
					int x = (int) (Mouse.getX() * scale + CameraX);
					int y = (int) (Mouse.getY() * scale + CameraY);

					pos1.setX(x);
					pos1.setY(y);

					int xR = x / 32;
					int yR = y / 32;

					switch (inGameStat) {
					case state.N_BUILDINGS:
						Building b = new Building(new Pos(xR * 32, yR * 32), 64, 32, "haus.png");
						if (handler.place(b)) {
							b.setSort(2);
							pending.add(b);
							pendingEbenen.add(1);
						}
						break;
					case state.N_TRUPS:
						Soldat s = Rekrutieren.Hussar(xR * 32, yR * 32, 32, 32, "Spieler 1", 0);
						if (handler.place(s)) {
							s.setSort(1);
							pending.add(s);
							pendingEbenen.add(1);
						}
						break;
					case state.NF_TROOP:
						Soldat sf = Rekrutieren.Bogenschuetze(xR * 32, yR * 32, 32, 32, "Spieler 2", 1);
						if (handler.place(sf)) {
							sf.setSort(1);
							pending.add(sf);
							pendingEbenen.add(1);
						}
						break;
					case state.S_TRUPS:
						search(x, y);
						if (selection.get(selection.size() - 1) instanceof Soldat) {
							figur = (Soldat) selection.get(selection.size() - 1);
						}
						break;
					case state.S_BUILDINGS:
						search(x, y);
						break;
					case state.ABREIﬂEN:
						RectangleGraphicalObject h = handler.abreiﬂen(xR, yR);
						if (h != null) {
							renderList[1].remove(h);
						}
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
							if (selection.get(i).getType().equals(StringConstants.MeshType.EINHEIT)) {
								Soldat h = (Soldat) selection.get(i);
								handler.handle(h, p1, selection.size() + 2);// TODO
																			// rework
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
							if (selection.get(i).getType().equals(StringConstants.MeshType.EINHEIT)) {
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
		int dWheel = Mouse.getDWheel();
		if (dWheel < 0) {
			double width = WIDTH * scale;
			double height = HEIGHT * scale;
			scale += zomingSpeed;
			if (scale > terrain.getWidth() * 32 / WIDTH) {
				scale = terrain.getWidth() * 32 / WIDTH;
			}
			CameraX += (width - WIDTH * scale) / 2;
			CameraY += (height - HEIGHT * scale) / 2;
			if (CameraX < 0) {
				CameraX = 0;
			}
			if (CameraY < 0) {
				CameraY = 0;
			}
			if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
				CameraX = terrain.getWidth() * 32 - WIDTH * scale;
			}
			if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
				CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
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
			if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
				CameraX = terrain.getWidth() * 32 - WIDTH * scale;
			}
			if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
				CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
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
			if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
				CameraX = terrain.getWidth() * 32 - WIDTH * scale;
			}
			if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
				CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
			}

		}

		// TODO JJDK
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("Space is down");
		}

		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Vorw‰rts"))) {
			if (figur != null) {
				figur.moveY(0.3f);
			}
		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Links"))) {
			if (figur != null) {
				figur.moveX(-0.3f);
			}
		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: R¸ckw‰rts"))) {
			if (figur != null) {
				figur.moveY(-0.3f);
			}
		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Rechts"))) {
			if (figur != null) {
				figur.moveX(0.3f);
			}
		}

		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Kamera oben"))) {
			CameraY += scrollingSpeed * scale;
			if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
				CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
			}

		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Kamera links"))) {
			CameraX -= scrollingSpeed * scale;
			if (CameraX < 0) {
				CameraX = 0;
			}

		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Kamera unten"))) {
			CameraY -= scrollingSpeed * scale;
			if (CameraY < 0) {
				CameraY = 0;
			}

		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Kamera rechts"))) {
			CameraX += scrollingSpeed * scale;
			if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
				CameraX = terrain.getWidth() * 32 - WIDTH * scale;
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
			if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
				CameraX = terrain.getWidth() * 32 - WIDTH * scale;
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
			if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
				CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
			}

		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Volume -"))) {
			MoodMusic.changeVolume(-0.5f);
		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Volume +"))) {
			MoodMusic.changeVolume(0.5f);
		}

	}

	public void render() {
		terrain.draw();
		for (int e = 0; e < ebenen; e++) {
			for (int i = 0; i < renderList[e].size(); i++) {
				renderList[e].get(i).draw();
			}
		}
		

	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public int getDelta() {
		long time = getTime();

		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			l_fps.setText("FPS "+fps);
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
						if (renderList[e].get(i).getPosition().getX() <= Px1 && renderList[e].get(i).getPosition().getX() >= Px2
								&& renderList[e].get(i).getPosition().getY() <= Py1 && renderList[e].get(i).getPosition().getY() >= Py2) {

							if (renderList[e].get(i).getType().equals(StringConstants.MeshType.EINHEIT)) {
								selection.add(renderList[e].get(i));
							}
						}
					}
				}
			} catch (Exception e) {

			}
		}

	}

	// F√ºr einzelauswahl
	public void search(double x, double y) {
		GraphicalObject xy = figur;
		selection.clear();

		try {
			for (int e = 0; e < ebenen; e++) {

				for (int i = 0; i < renderList[e].size(); i++) {
					if (renderList[e].get(i).getPosition().getX() <= x && renderList[e].get(i).getPosition().getX() >= x - 64
							&& renderList[e].get(i).getPosition().getY() <= y && renderList[e].get(i).getPosition().getY() >= y - 64) {
						xy = renderList[e].get(i);
					}
				}
			}
		} catch (Exception e) {

		}
		selection.add(xy);
	}

	public void calc() {
		handler.move();
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
		initUI();
	}

	public void updateDisplay() {
		if (Vsync)
			Display.sync(VsyncF);

		Display.update();

	}

	public void grafikCycl() {
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH * scale, 0, HEIGHT * scale, 3, -1);
		glTranslatef((float) -CameraX, (float) -CameraY, 0f);
		glMatrixMode(GL_MODELVIEW);

		glLoadIdentity();
		render();
		
	}

	private void pollInputG() {
//		Mouse.poll();
//		Keyboard.poll();
		if (screenToSet) {
			setDisplayMode(WIDTH, HEIGHT, fullscreen);
			screenToSet = false;
		}
		while (pending.size() > 0 && pendingEbenen.size() > 0) {
			initRender(pending.get(0), pendingEbenen.get(0));
			pending.remove(0);
			pendingEbenen.remove(0);
		}
		/*if (inGameStat.equals(state.ABREIﬂEN)) {
			if (!delete.equals(Mouse.getNativeCursor())) {
				try {
					Mouse.setNativeCursor(delete);
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
		} else if (inGameStat.equals(state.N_BUILDINGS)) {
			if (!haus.equals(Mouse.getNativeCursor())) {
				try {
					Mouse.setNativeCursor(haus);
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (!normal.equals(Mouse.getNativeCursor())) {
				try {
					Mouse.setNativeCursor(normal);
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
		}*/
	}
	
	

	private void initUI(){
		renderer = null;
		try {
			renderer = new LWJGLRenderer();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gui = new GUI(this, renderer);
		
		try{
			themeManager = ThemeManager.createThemeManager(MainGUI.class.getResource("test.xml"), renderer);
		} catch(IOException e){
			e.printStackTrace();
		}

		gui.applyTheme(themeManager);
		
		GUI();
		
		
	}
	
	// UI Var
	private GUI gui;
	private LWJGLRenderer renderer;
	private Button b_NTrups;
	private Button b_TSelect;
	private Button b_NBuilding;
	private ThemeManager themeManager;
	private FPSCounter fpsCounter;
	private Label l_fps;
	private Label l_time;
	
	@Override
	protected void layout() {
		 b_NTrups.setPosition(100, 100);
		 b_NTrups.setSize(100, 33);
		 
		 b_NBuilding.setPosition(100, 150);
		 b_NBuilding.setSize(100, 33);
		 
		 b_TSelect.setPosition(100, 200);
		 b_TSelect.setSize(100, 33);
		 
		 l_fps.setPosition(100, 10);
		 l_fps.setSize(20, 40);
		 
		 l_time.setPosition(WIDTH - 200, 10);
		 l_time.setSize(20, 40);
		 
		    //button.adjustSize(); //Calculate optimal size instead of manually setting it
		super.layout();
	}
	
	private void GUI(){
		b_NTrups = new Button("Set Trup");
		b_NTrups.setTheme("button_Test");
		add(b_NTrups);
		
		b_NTrups.addCallback(new Runnable() {
			
			@Override
			public void run() {
				inGameStat = state.N_TRUPS;
				System.out.println(inGameStat);
				
			}
		});
		
		b_NBuilding = new Button("New Buildung");
		b_NBuilding.setTheme("button_Test");
		add(b_NBuilding);
		
		b_NBuilding.addCallback(new Runnable() {
			
			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				System.out.println(inGameStat);
				
			}
		});
		
		b_TSelect = new Button("Select");
		b_TSelect.setTheme("button_Test");
		add(b_TSelect);
		
		b_TSelect.addCallback(new Runnable() {
			
			@Override
			public void run() {
				inGameStat = state.S_TRUPS;
				System.out.println(inGameStat);
				
			}
		});
		
		l_fps = new Label("");
		l_fps.setTheme("label");
		add(l_fps);
		
		l_time = new Label("");
		l_time.setTheme("label");
		add(l_time);
		
		
	}
	
	public void handlInput(){
		gui.setSize();
		gui.updateTime();
		try{
			pollInput();
		} catch(Exception e){
			e.printStackTrace();
		}
		pollInputG();
		gui.handleKeyRepeat();
		gui.handleTooltips();
		gui.updateTimers();
		gui.invokeRunables();
		gui.validateLayout();
		gui.draw();
		gui.setCursor();
	}
	
	public void guiPollInput(){
		gui.handleKey(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState());
		gui.handleMouse(Mouse.getEventX(), gui.getHeight() - Mouse.getEventY() - 1, Mouse.getEventButton(), Mouse.getEventButtonState());
		
		int wheelDelta = Mouse.getEventDWheel();
	    if(wheelDelta != 0) {
	    	gui.handleMouseWheel(wheelDelta);
	    }
	}

}
