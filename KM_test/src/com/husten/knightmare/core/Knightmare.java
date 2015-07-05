package com.husten.knightmare.core;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.graphicalObjects.DNCycl;
import com.husten.knightmare.graphicalObjects.GraphicalObject;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.husten.knightmare.graphicalObjects.Terrain;
import com.husten.knightmare.menues.MainGUI;
import com.matze.knightmare.menues.InGameOptionen;
import com.matze.knightmare.meshes.Battle;
import com.matze.knightmare.meshes.Bauen;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Rekrutieren;
import com.matze.knightmare.meshes.Soldat;
import com.matze.knightmare.meshes.Spieler;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Dictionary;
import com.richard.knightmare.util.DictionaryE;
import com.richard.knightmare.util.EntityHandler;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Pos;
import com.richard.knightmare.util.Texturloader;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ResizableFrame;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.MouseCursor;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class Knightmare extends Widget implements StringConstants {

	private long lastFrame, lastFPS;
	private int fps, ebenen = 3, VsyncF = 120, gameSpeed = 10 /* inverted */, cursorIndex = 0, category = -1, aktuellesGeb‰ude = -1;
	@SuppressWarnings("unused")
	private double FPS = 60, zomingSpeed = 0.1, scrollingSpeed = 5;
	private String inGameStat = state.S_TRUPS;
	public static int WIDTH = 1600, HEIGHT = 900;
	private boolean fullscreen = Loader.getCfgValue("SETTINGS: Fenstermodus").equals("false"), Vsync = false, running = true, baumenueShowen = true;
	private Soldat figur;
	public static Terrain terrain;
	private Pos pos1 = new Pos(0, 0), pos2 = new Pos(0, 0), ang = null;
	public static double CameraX = 0, CameraY = 0, scale = 1;
	// private Pathhandler handler;
	private EntityHandler newHandler;
	// private ArrayList<RectangleGraphicalObject> selection = new
	// ArrayList<>();
	@SuppressWarnings("unchecked")
	private ArrayList<GraphicalObject> renderList[] = new ArrayList[ebenen], ObjectList[] = new ArrayList[ebenen];
	private Timer timer = new Timer(true);
	private HashMap<Soldat, Soldat> angriffe = new HashMap<>();
	private int renderD = 5;
	private Spieler[] spieler;

	public static double breightness = 1;

	private DNCycl DN;

	public Knightmare(Spieler[] spieler) {
		Vsync = (Loader.getCfgValue("SETTINGS: V-Sync").equals("On"));
		this.spieler = spieler;
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
			glScissor(-renderD, -renderD, WIDTH + renderD, HEIGHT + renderD);
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
		glEnable(GL_SCISSOR_TEST);

		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		// disable the OpenGL depth test since we're rendering 2D graphics
		glDisable(GL_DEPTH_TEST);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 3, -1);
		glTranslatef(0, 0, 0f);
		glScissor(-renderD, -renderD, WIDTH + renderD, HEIGHT + renderD);
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
		// handler = new Pathhandler(513, 513);
		newHandler = new EntityHandler(513, 513);
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
		initRender(new RectangleGraphicalObject(new Pos(0, 0), 1, 1, "", false), 1);
		// will versuchen dummy objekt einzuf¸gen damit die buttons gehen

	}

	public void tooggleFullscreen() {
		if (fullscreen) {
			WIDTH = 1600;
			HEIGHT = 900;
		} else {
			initRes();
		}
		setDisplayMode(WIDTH, HEIGHT, fullscreen);
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
		// Mouse.poll();
		// Keyboard.poll();
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
					if (cursorIndex == 0) {
						gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursorDel"));
						cursorIndex = 1;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_T) {
					DN.toggle();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					scale = 1f;
				}

				if (getString("CONTROL_KEY: Quicksave").equals(gFN(Keyboard.getEventKey()))) {
					// TODO name
					Loader.speichern("Test");
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_C) {
					inGameStat = state.S_TRUPS;
					if (cursorIndex == 1) {
						gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));
						cursorIndex = 0;
					}
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					inGameStat = state.S_BUILDINGS;
					if (cursorIndex == 1) {
						gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));
						cursorIndex = 0;
					}
					System.out.println(inGameStat);
				}

				if (getString("CONTROL_KEY: Baumen¸ ein/aus").equals(gFN(Keyboard.getEventKey()))) {
					baumenueShowen = !baumenueShowen;
					// inGameStat = state.N_BUILDINGS;
					if (cursorIndex == 1) {
						gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));
						cursorIndex = 0;
					}
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_N) {
					inGameStat = state.N_TRUPS;
					if (cursorIndex == 1) {
						gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));
						cursorIndex = 0;
					}
					System.out.println(inGameStat);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_J) {
					inGameStat = state.NF_TROOP;
					if (cursorIndex == 1) {
						gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));
						cursorIndex = 0;
					}
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

				// if (Keyboard.getEventKey() == Keyboard.KEY_NUMPAD0) {
				// System.out.println(selection.size());
				// }

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
				// Linksklick
				if (Mouse.getEventButton() == 0) {

					int x = (int) (Mouse.getX() * scale + CameraX);
					int y = (int) (Mouse.getY() * scale + CameraY);

					if (!isOn(Mouse.getX(), Mouse.getY())) {
						pos1.setX(x);
						pos1.setY(y);

						int xR = x / 32;
						int yR = y / 32;

						switch (inGameStat) {
						case state.N_BUILDINGS:
							if (aktuellesGeb‰ude != -1) {
								Building b = Bauen.getBuildingforID(aktuellesGeb‰ude, new Pos(xR * 32, yR * 32), spieler[0]);
								if (/* handler.place(b) */newHandler.place(b)) {
									// b.setSort(0);
									// initRender(b, 1);
								}
							}
							break;
						case state.N_TRUPS:
							Soldat s = Rekrutieren.Hussar(xR * 32, yR * 32, 32, 32, spieler[0]);
							if (/* handler.place(s) */newHandler.place(s)) {
								// s.setSort(1);
								// initRender(s, 1);
							}
							break;
						case state.NF_TROOP:
							Soldat sf = Rekrutieren.Bogenschuetze(xR * 32, yR * 32, 32, 32, spieler[0]);
							if (/* handler.place(sf) */newHandler.place(sf)) {
								// sf.setSort(1);
								// initRender(sf, 1);
							}
							break;
						case state.S_TRUPS:
							newHandler.search(x, y);
							if (newHandler.getSelection().size() > 0) {
								if (newHandler.getSelection().get(newHandler.getSelection().size() - 1) instanceof Soldat) {
									figur = (Soldat) newHandler.getSelection().get(newHandler.getSelection().size() - 1);
								}
							}
							// search(x, y);
							// if (selection.get(selection.size() - 1)
							// instanceof Soldat) {
							// figur = (Soldat) selection.get(selection.size() -
							// 1);
							// }
							break;
						case state.S_BUILDINGS:
							newHandler.search(x, y);
							// search(x, y);
							break;
						case state.ABREIﬂEN:
							/*
							 * RectangleGraphicalObject h = handler.abreiﬂen(xR,
							 * yR)
							 */newHandler.remove(xR, yR);
							;
							// if (h != null) {
							// renderList[1].remove(h);
							// }
							break;
						}
					}
				}

				if (Mouse.getEventButton() == 2) {
					ang = new Pos(CameraX + Mouse.getX() * scale, CameraY + Mouse.getY() * scale);
					// ang = new Pos(Mouse.getX(), Mouse.getY());
				}
				if (Mouse.getEventButton() == 1) {
					int x = (int) (Mouse.getX() * scale + CameraX);
					int y = (int) (Mouse.getY() * scale + CameraY);

					if (!isOn(Mouse.getX(), Mouse.getY())) {

						// Pos p1 = new Pos(x, y); // Ende

						switch (inGameStat) {
						case state.NOTHING:
							break;
						case state.S_TRUPS:
							newHandler.processRightClick(x, y);
							/*
							 * Soldat bogi = Rekrutieren.Abgesessener_Ritter(0,
							 * 0, 32, 32, "Spieler 2", 1); for (int i = 0; i <
							 * selection.size(); i++) { if
							 * (selection.get(i).getType().equals(
							 * StringConstants.MeshType.EINHEIT)) { Soldat h =
							 * (Soldat) selection.get(i); handler.handle(h, p1,
							 * selection.size() + 2);// TODO // rework
							 * angriffe.put(h, bogi); angriffe.put(bogi, h); } }
							 */
							break;
						case state.S_BUILDINGS:
							break;
						}
					}
				}
			} else {
				// Button released
				if (Mouse.getEventButton() == 0) {
					int x = (int) (Mouse.getX() * scale + CameraX);
					int y = (int) (Mouse.getY() * scale + CameraY);

					if (!isOn(Mouse.getX(), Mouse.getY())) {
						pos2.setX(x);
						pos2.setY(y);

						switch (inGameStat) {
						case state.S_TRUPS:
							newHandler.search(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
							// for (int i = 0; i < selection.size(); i++) {
							// if
							// (selection.get(i).getType().equals(StringConstants.MeshType.EINHEIT))
							// {
							// ((Soldat) selection.get(i)).say();
							// }
							// }
							break;
						}
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
			CameraY += (scrollingSpeed * scale) * gui.getCurrentDeltaTime() * 0.5;
			if (CameraY > terrain.getHeight() * 32 - HEIGHT * scale) {
				CameraY = terrain.getHeight() * 32 - HEIGHT * scale;
			}

		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Kamera links"))) {
			CameraX -= (scrollingSpeed * scale) * gui.getCurrentDeltaTime() * 0.5;
			if (CameraX < 0) {
				CameraX = 0;
			}

		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Kamera unten"))) {
			CameraY -= (scrollingSpeed * scale) * gui.getCurrentDeltaTime() * 0.5;
			if (CameraY < 0) {
				CameraY = 0;
			}

		}
		if (Keyboard.isKeyDown(getKeyCode("CONTROL_KEY: Kamera rechts"))) {
			CameraX += (scrollingSpeed * scale) * gui.getCurrentDeltaTime() * 0.5;
			if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
				CameraX = terrain.getWidth() * 32 - WIDTH * scale;
			}

		}

		if (Mouse.getX() < 32) {
			CameraX -= (scrollingSpeed * scale) * gui.getCurrentDeltaTime() * 0.5;
			if (CameraX < 0) {
				CameraX = 0;
			}

		}
		if (Mouse.getX() > WIDTH - 32) {
			CameraX += (scrollingSpeed * scale) * gui.getCurrentDeltaTime() * 0.5;
			if (CameraX > terrain.getWidth() * 32 - WIDTH * scale) {
				CameraX = terrain.getWidth() * 32 - WIDTH * scale;
			}

		}
		if (Mouse.getY() < 32) {
			CameraY -= (scrollingSpeed * scale) * gui.getCurrentDeltaTime() * 0.5;
			if (CameraY < 0) {
				CameraY = 0;
			}

		}
		if (Mouse.getY() > HEIGHT - 32) {
			CameraY += (scrollingSpeed * scale) * gui.getCurrentDeltaTime() * 0.5;
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
		newHandler.draw();

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
			l_fps.setText("FPS " + fps);
			FPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	// public void search(double x1, double y1, double x2, double y2) {
	// if (x1 == x2 && y1 == y2) {
	// search(x1, y1);
	// } else {
	// selection.clear();
	// double Px1;
	// double Px2;
	//
	// double Py1;
	// double Py2;
	//
	// if (x1 < x2) {
	// Px1 = x2;
	// Px2 = x1;
	// } else {
	// Px1 = x1;
	// Px2 = x2;
	// }
	//
	// if (y1 < y2) {
	// Py1 = y2;
	// Py2 = y1;
	// } else {
	// Py1 = y1;
	// Py2 = y2;
	// }
	//
	// try {
	// for (int e = 0; e < ebenen; e++) {
	//
	// for (int i = 0; i < renderList[e].size(); i++) {
	// if (renderList[e].get(i).getPosition().getX() <= Px1 &&
	// renderList[e].get(i).getPosition().getX() >= Px2
	// && renderList[e].get(i).getPosition().getY() <= Py1 &&
	// renderList[e].get(i).getPosition().getY() >= Py2) {
	//
	// if
	// (renderList[e].get(i).getType().equals(StringConstants.MeshType.EINHEIT))
	// {
	// selection.add((RectangleGraphicalObject) renderList[e].get(i));
	// }
	// }
	// }
	// }
	// } catch (Exception e) {
	//
	// }
	// }
	//
	// }
	//
	// // F√ºr einzelauswahl
	// public void search(double x, double y) {
	// GraphicalObject xy = figur;
	// selection.clear();
	//
	// try {
	// for (int e = 0; e < ebenen; e++) {
	//
	// for (int i = 0; i < renderList[e].size(); i++) {
	// if (renderList[e].get(i).getPosition().getX() <= x &&
	// renderList[e].get(i).getPosition().getX() >= x - 64
	// && renderList[e].get(i).getPosition().getY() <= y &&
	// renderList[e].get(i).getPosition().getY() >= y - 64) {
	// xy = renderList[e].get(i);
	// }
	// }
	// }
	// } catch (Exception e) {
	//
	// }
	// selection.add((RectangleGraphicalObject) xy);
	// }

	public void calc() {
		// handler.move();
		newHandler.tick();

		for (Entry<Soldat, Soldat> entry : angriffe.entrySet()) {
			Soldat krepierd = Battle.kampf(entry.getKey(), entry.getValue(), 0);
			newHandler.remove(krepierd);/*
										 * renderList[1].remove(handler.abreiﬂen
										 * (krepierd));
										 */
			if (krepierd != null) {
				angriffe.remove(entry.getKey());
				angriffe.remove(krepierd);
				for (Entry<Soldat, Soldat> entry2 : angriffe.entrySet()) {
					if (entry2.getValue().equals(krepierd)) {
						angriffe.remove(entry2.getKey());
					}
				}
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

	private boolean isOn(double x, double y) {
		int width = WIDTH;
		if (width > 1920) {
			width = 1920;
		}
		if (x > (WIDTH - width) / 2 && x < (WIDTH + width) / 2 && y > HEIGHT - kopfframe.getHeight()) {
			return true;
		}
		return baumenueShowen && x > (WIDTH - width) / 2 && x < (WIDTH + width) / 2 && y < width / 7;
	}

	private void initUI() {
		renderer = null;
		try {
			renderer = new LWJGLRenderer();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gui = new GUI(this, renderer);

		try {
			URL url = null;
			try {
				url = new URL(new StringBuilder(Loader.getTexturePath().replace('\\', '/')).append("/menue.xml").toString());
			} catch (MalformedURLException e) {
				// Leck mich
			}
			URL url2 = MainGUI.class.getResource(new StringBuilder(Loader.getTexturePath().replace('\\', '/')).append("/menue.xml").toString());
			themeManager = ThemeManager.createThemeManager((url == null ? url2 : url), renderer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		gui.applyTheme(themeManager);
		gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));

		GUI();

	}

	// UI Var
	private GUI gui;
	private LWJGLRenderer renderer;
	private ThemeManager themeManager;
	private Label l_fps;
	private Label l_time;
	private ResizableFrame frame, kopfframe;
	private Button[] categories = new Button[6];
	private Button menue, einstellungen;
	private Button[][] geb‰ude = new Button[6][10];
	private Label[] res = new Label[11];
	private String[] resn = {"IKohle", "IEisen", "IHolz", "IDiamant", "IPech", "ISand", "IWeizen", "ILehm", "IStein", "IM¸nze", "IGlas"};

	@Override
	protected void layout() {
		int width = WIDTH;
		if (width > 1920) {
			width = 1920;
		}
		Pos baustart = new Pos((WIDTH - width) / 2 + width / 4 + width / 16, HEIGHT - width / 7 + 75);
		geb‰ude[0][0].setSize(64, 64);
		geb‰ude[0][0].setPosition((int) baustart.getX(), (int) baustart.getY());
		geb‰ude[0][0].setBackground(themeManager.getImage("kohlemine"));
		geb‰ude[0][1].setSize(64, 64);
		geb‰ude[0][1].setPosition((int) baustart.getX() + 74, (int) baustart.getY());
		geb‰ude[0][1].setBackground(themeManager.getImage("eisenmine"));
		geb‰ude[0][2].setSize(64, 64);
		geb‰ude[0][2].setPosition((int) baustart.getX() + 148, (int) baustart.getY());
		geb‰ude[0][2].setBackground(themeManager.getImage("lager"));
		geb‰ude[0][3].setSize(64, 64);
		geb‰ude[0][3].setPosition((int) baustart.getX() + 222, (int) baustart.getY());
		geb‰ude[0][3].setBackground(themeManager.getImage("Sandschmelze"));

		geb‰ude[1][0].setSize(64, 64);
		geb‰ude[1][0].setPosition((int) baustart.getX(), (int) baustart.getY());
		geb‰ude[1][0].setBackground(themeManager.getImage("Holz"));
		geb‰ude[1][1].setSize(64, 64);
		geb‰ude[1][1].setPosition((int) baustart.getX() + 74, (int) baustart.getY());
		geb‰ude[1][1].setBackground(themeManager.getImage("Steinbruch"));

		geb‰ude[3][0].setSize(64, 64);
		geb‰ude[3][0].setPosition((int) baustart.getX(), (int) baustart.getY());
		geb‰ude[3][0].setBackground(themeManager.getImage("Hof"));
		geb‰ude[3][1].setSize(64, 64);
		geb‰ude[3][1].setPosition((int) baustart.getX() + 74, (int) baustart.getY());
		geb‰ude[3][1].setBackground(themeManager.getImage("Viecha"));

		geb‰ude[4][0].setSize(64, 64);
		geb‰ude[4][0].setPosition((int) baustart.getX(), (int) baustart.getY());
		geb‰ude[4][0].setBackground(themeManager.getImage("Turm"));
		geb‰ude[4][1].setSize(32, 32);
		geb‰ude[4][1].setPosition((int) baustart.getX() + 74, (int) baustart.getY());
		geb‰ude[4][1].setBackground(themeManager.getImage("Mauer"));

		geb‰ude[5][0].setSize(64, 32);
		geb‰ude[5][0].setPosition((int) baustart.getX(), (int) baustart.getY());
		geb‰ude[5][0].setBackground(themeManager.getImage("Haus"));

		kopfframe.setSize(width, 2 * HEIGHT / 45);
		kopfframe.setPosition((WIDTH - width) / 2, 0);
		kopfframe.setBackground(themeManager.getImageNoWarning("kopfzeile"));

		Pos start = new Pos((WIDTH - width) / 2 + width / 4 + width / 16, HEIGHT - 85);
		for (int i = 0; i < categories.length; i++) {
			categories[i].setSize(40, 40);
			categories[i].setPosition((int) start.getX() + 40 * i, (int) start.getY());
			categories[i].setBackground(themeManager.getImageNoWarning(imgs[i]));
		}

		menue.setSize(kopfframe.getHeight() * 2 - 7, kopfframe.getHeight() - 12);
		menue.setPosition((WIDTH + width) / 2 - menue.getWidth() - 6, 7);
		menue.setBackground(themeManager.getImage("button.background"));

		einstellungen.setSize(menue.getHeight(), menue.getHeight());
		einstellungen.setPosition(menue.getX() - einstellungen.getWidth(), menue.getY());
		einstellungen.setBackground(themeManager.getImage("cfg"));

		l_fps.adjustSize();
		l_fps.setPosition((WIDTH - width) / 2 + 7, (kopfframe.getHeight() - l_fps.getHeight()) / 2);

		l_time.adjustSize();
		l_time.setPosition(WIDTH / 2 - l_time.getWidth() / 2, (kopfframe.getHeight() - l_time.getHeight()) / 2);

		res[0].adjustSize();
		res[0].setSize(Math.max(res[0].getWidth(), res[0].getHeight()), Math.max(res[0].getWidth(), res[0].getHeight()));
		res[0].setPosition(l_fps.getX() + l_fps.getWidth() + 10, (kopfframe.getHeight() - res[0].getHeight()) / 2);
		res[0].setBackground(themeManager.getImage("IKohle"));
		for(int i = 1; i<resn.length; i++){
			res[i].adjustSize();
			res[i].setSize(Math.max(res[i].getWidth(), res[i].getHeight()), Math.max(res[i].getWidth(), res[i].getHeight()));
			res[i].setPosition(res[i-1].getX() + res[i-1].getWidth() + 10, (kopfframe.getHeight() - res[i].getHeight()) / 2);
			res[i].setBackground(themeManager.getImage(resn[i]));
			
		}

		gui.draw();

		// button.adjustSize(); //Calculate optimal size instead of manually
		// setting it
		super.layout();
	}
	
	private void setCategory() {
		for (Button[] button : geb‰ude) {
			for (Button button1 : button) {
				frame.removeChild(button1);
			}
		}
		if (category != -1) {
			for (Button button : geb‰ude[category]) {
				if (button != null) {
					frame.add(button);
				}
			}
		}
	}
	
	private String[] imgs = { "cP", "cR", "cM", "cN", "cV", "cZ" }, names = { "Produktion", "Resourcen", "Milit‰r", "Nahrung", "Verteidigung", "Zivil" };

	private void GUI() {
		for (int i = 0; i < 3; i++) {
			geb‰ude[0][i] = new Button();
			Label helpT = new Label(Bauen.getBuildingName(i));
			geb‰ude[0][i].setTooltipContent(helpT);
			int wieso = i;
			geb‰ude[0][i].addCallback(new Runnable() {

				@Override
				public void run() {
					inGameStat = state.N_BUILDINGS;
					aktuellesGeb‰ude = wieso;
				}
			});
		}
		geb‰ude[0][3] = new Button();
		Label helpTS = new Label(Bauen.getBuildingName(5));
		geb‰ude[0][3].setTooltipContent(helpTS);
		geb‰ude[0][3].addCallback(new Runnable() {

			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				aktuellesGeb‰ude = 5;
			}
		});

		geb‰ude[1][0] = new Button();
		Label helpT = new Label(Bauen.getBuildingName(3));
		geb‰ude[1][0].setTooltipContent(helpT);
		geb‰ude[1][0].addCallback(new Runnable() {

			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				aktuellesGeb‰ude = 3;
			}
		});
		geb‰ude[1][1] = new Button();
		Label helpTSt = new Label(Bauen.getBuildingName(8));
		geb‰ude[1][1].setTooltipContent(helpTSt);
		geb‰ude[1][1].addCallback(new Runnable() {

			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				aktuellesGeb‰ude = 8;
			}
		});

		geb‰ude[3][0] = new Button();
		Label helpTHo = new Label(Bauen.getBuildingName(6));
		geb‰ude[3][0].setTooltipContent(helpTHo);
		geb‰ude[3][0].addCallback(new Runnable() {

			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				aktuellesGeb‰ude = 6;
			}
		});
		geb‰ude[3][1] = new Button();
		Label helpVie = new Label(Bauen.getBuildingName(7));
		geb‰ude[3][1].setTooltipContent(helpVie);
		geb‰ude[3][1].addCallback(new Runnable() {

			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				aktuellesGeb‰ude = 7;
			}
		});

		geb‰ude[4][0] = new Button();
		Label helpTT = new Label(Bauen.getBuildingName(9));
		geb‰ude[4][0].setTooltipContent(helpTT);
		geb‰ude[4][0].addCallback(new Runnable() {

			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				aktuellesGeb‰ude = 9;
			}
		});
		geb‰ude[4][1] = new Button();
		Label helpTMau = new Label(Bauen.getBuildingName(10));
		geb‰ude[4][1].setTooltipContent(helpTMau);
		geb‰ude[4][1].addCallback(new Runnable() {

			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				aktuellesGeb‰ude = 10;
			}
		});

		geb‰ude[5][0] = new Button();
		Label helpTH = new Label(Bauen.getBuildingName(4));
		geb‰ude[5][0].setTooltipContent(helpTH);
		geb‰ude[5][0].addCallback(new Runnable() {

			@Override
			public void run() {
				inGameStat = state.N_BUILDINGS;
				aktuellesGeb‰ude = 4;
			}
		});

		for (int i = 0; i < categories.length; i++) {
			categories[i] = new Button();
			Label l = new Label(names[i]);
			categories[i].setTooltipContent(l);
			int help = i;
			categories[i].addCallback(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < categories.length; j++) {
						if (j != help) {
							categories[j].getAnimationState().setAnimationState(STATE_DISABLED, true);
						} else {
							categories[j].getAnimationState().setAnimationState(STATE_DISABLED, false);
						}
					}
					category = help;
					setCategory();
					System.out.println(category);
				}
			});
		}

		menue = new Button("Menue");
		menue.addCallback(new Runnable() {

			@Override
			public void run() {
				MainMenue m = new MainMenue();
				timer.cancel();
				running = false;
				MoodMusic.changeMood("MainMenue");
				m.toFront();
				m.setAlwaysOnTop(true);
				m.setAutoRequestFocus(true);
			}
		});
		einstellungen = new Button();
		einstellungen.addCallback(new Runnable() {

			@Override
			public void run() {
				new InGameOptionen(Knightmare.this);
			}
		});

		l_fps = new Label("");
		l_fps.setTheme("label");

		l_time = new Label("");
		l_time.setTheme("label");

		for(int i = 0; i<resn.length; i++){
			res[i] = new Label("10");
		}

		kopfframe = new ResizableFrame();
		kopfframe.setTheme("frame");
		kopfframe.setTitle("Header");
		kopfframe.add(menue);
		kopfframe.add(einstellungen);
		add(kopfframe);
		add(l_fps);
		add(l_time);
		for (int i = 0; i < resn.length; i++) {
			add(res[i]);
		}

		frame = new ResizableFrame();
		frame.setTheme("frame");
		frame.setTitle("Inventory");
		for (Button button : categories) {
			frame.add(button);
		}

		int width = WIDTH;
		if (width > 1920) {
			width = 1920;
		}

		frame.setSize(width, width / 7);
		frame.setPosition((WIDTH - width) / 2, HEIGHT - width / 7);

		l_fps.setPosition(100, 10);
		l_fps.setSize(20, 40);

		l_time.setPosition(WIDTH - 200, 10);
		l_time.setSize(20, 40);

		gui.draw();

		// button.adjustSize(); //Calculate optimal size instead of manually
		// setting it
	}

	public void handlInput() {
		gui.setSize();
		gui.updateTime();
		try {
			pollInput();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (baumenueShowen) {
			if (getChildIndex(frame) == -1) {
				add(frame);
			}
		} else {
			removeChild(frame);
		}

		gui.handleKeyRepeat();
		gui.handleTooltips();
		gui.updateTimers();
		gui.invokeRunables();
		gui.validateLayout();
		gui.draw();
		// gui.setCursor();TODO
	}

	public void guiPollInput() {
		gui.handleKey(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState());
		gui.handleMouse(Mouse.getEventX(), gui.getHeight() - Mouse.getEventY() - 1, Mouse.getEventButton(), Mouse.getEventButtonState());

		int wheelDelta = Mouse.getEventDWheel();
		if (wheelDelta != 0) {
			gui.handleMouseWheel(wheelDelta);
		}
	}

	public void setFocus() {
		try {
			Display.setDisplayModeAndFullscreen(new DisplayMode(WIDTH, HEIGHT));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
