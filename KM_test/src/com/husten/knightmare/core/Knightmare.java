package com.husten.knightmare.core;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.graphicalObjects.DNCycl;
import com.husten.knightmare.graphicalObjects.GraphicalObject;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.husten.knightmare.graphicalObjects.Terrain;
import com.matze.knightmare.menues.Optionen;
import com.matze.knightmare.meshes.Bauen;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Rekrutieren;
import com.matze.knightmare.meshes.Rohstoffe;
import com.matze.knightmare.meshes.Soldat;
import com.matze.knightmare.meshes.Spieler;
import com.richard.knightmare.serial.LoadSaveHandler;
import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Dictionary;
import com.richard.knightmare.util.DictionaryE;
import com.richard.knightmare.util.EntityHandler;
import com.richard.knightmare.util.Loader;
import com.richard.knightmare.util.Pos;
import com.richard.knightmare.util.TWLImage;
import com.richard.knightmare.util.Texturloader;
import com.richard.knightmare.visual.Overlay;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Color;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ResizableFrame;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.Font;
import de.matthiasmann.twl.renderer.Image;
import de.matthiasmann.twl.renderer.MouseCursor;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import de.matthiasmann.twl.utils.TintAnimator;

public class Knightmare extends Widget implements StringConstants {

	private long lastFrame, lastFPS;
	private int fps, ebenen = 3, VsyncF = 120, gameSpeed = 10 /* inverted */, cursorIndex = 0, category = -1, aktuellesGeb�ude = -1, zuletztAktuellesGeb�ude = -1/*,
			buildingSelected = -1*/;//TODO
	@SuppressWarnings("unused")
	private double FPS = 60, zomingSpeed = 0.1, scrollingSpeed = 5, r�ckerstattungsanteil = 0.5;
	private String inGameStat = state.DEFAULT,savePath, GameName;
	public static int WIDTH = 1600, HEIGHT = 900;
	private boolean fullscreen = Loader.getCfgValue("SETTINGS: Fenstermodus").equals("false"), Vsync = false, running = true, baumenueShowen = true,
			rekrutriernShown = false, loaded, forceUpdate= false;
	public static Terrain terrain = new Terrain((512) + 1, (512) + 1);
	private Pos pos1 = new Pos(0, 0), pos2 = new Pos(0, 0), ang = null;
	public static double CameraX = 0, CameraY = 0, scale = 1;
	public static EntityHandler newHandler;
	@SuppressWarnings("unchecked")
	private ArrayList<GraphicalObject> renderList[] = new ArrayList[ebenen], ObjectList[] = new ArrayList[ebenen];
	private Timer timer = new Timer(true);
	private int renderD = 5;
	private Spieler[] spieler;
	private Cursor[] cursors = new Cursor[2];

	public static double breightness = 1;

	private DNCycl DN;

	public Knightmare(Spieler[] spieler, boolean loaded, String savePath, String GameName) {
		this.GameName = GameName;
		this.savePath = savePath;
		this.loaded = loaded;
		Vsync = (Loader.getCfgValue("SETTINGS: V-Sync").equals("On"));
		this.spieler = spieler;
		start();
	}

	public void setAllWG(int smoothS, double lW, double lS, double lG, double lR, double wE, double wK, float routh, float fallof, float hMulti, int seed) {
		terrain.setAll(smoothS, lW, lS, lG, lR, wE, wK, routh, fallof, hMulti, seed);
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
		category = 0;
		setCategory();
		try {
			cursors[1] = new Cursor(32, 32, 0, 31, 1, Texturloader.convertImageData(Loader.getImage("delete.png"), Texturloader.getTexture("delete.png")).asIntBuffer(), null);
			cursors[0] = new Cursor(32, 32, 0, 31, 1, Texturloader.convertImageData(Loader.getImage("CursorIn.png"), Texturloader.getTexture("CursorIn.png")).asIntBuffer(), null);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		try {
			Mouse.setNativeCursor(cursors[0]);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		for (int j = 0; j < categories.length; j++) {
			if (j != category) {
				categories[j].getAnimationState().setAnimationState(STATE_DISABLED, true);
			} else {
				categories[j].getAnimationState().setAnimationState(STATE_DISABLED, false);
			}
		}

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
			if (!inGameStat.equals(state.BAUEN/* N_BUILDINGS */)) {
				aktuellesGeb�ude = -1;
			}

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
		if(loaded){
			Object[] save = LoadSaveHandler.load(savePath);
			newHandler = (EntityHandler) save[0];
			newHandler.ReInitRender();
			newHandler.reInitTimer();
			terrain = (Terrain) save[1];
			terrain.reInit();
			spieler = newHandler.getSpieler();
		}else{
			newHandler = new EntityHandler(513, 513, spieler);
			terrain.gen();
			terrain.initRender();
		}
		// handler = new Pathhandler(513, 513);

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
		// will versuchen dummy objekt einzuf�gen damit die buttons gehen
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

	// Könnte iwann mal zu problemen führen
	private String gFN(int a) {
		return Dictionary.getFullName(Keyboard.getKeyName(a));
	}

	private int getKeyCode(String k) {
		return Keyboard.getKeyIndex(DictionaryE.getFullName(Loader.getCfgValue(k)));
	}

	private void pollInput() throws Exception {
		// Keyboard------------------------------------------------------------------------------------
		while (Keyboard.next()) {
			guiPollInput();
			if (Keyboard.getEventKeyState()) {

				if (getString("CONTROL_KEY: Escape/Zur�ck").equals(gFN(Keyboard.getEventKey()))) {
					LoadSaveHandler.save(GameName, newHandler, terrain);
					MainMenue m = new MainMenue();
					timer.cancel();
					running = false;
					MoodMusic.changeMood("MainMenue");
					m.toFront();
					m.setAlwaysOnTop(true);
					m.setAutoRequestFocus(true);
					return;
				}

				if (getString("CONTROL_KEY: Abrei�en").equals(gFN(Keyboard.getEventKey()))) {
					inGameStat = state.ABREI�EN;
					newHandler.selClear();
					if (cursorIndex == 0) {
						Mouse.setNativeCursor(cursors[1]);
//						Toolkit.getDefaultToolkit().createCustomCursor(Loader.getImage("delete.png"), new Point(0, 0), "del");
//						gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursorDel"));
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
					LoadSaveHandler.save(GameName, newHandler, terrain);
					labelZuTeuer.setText("Saving complete");
					showGedNedSeitWann = 0;
					if (getChildIndex(labelZuTeuer) == -1) {
						add(labelZuTeuer);
						gednedShown = true;
					}
				}

				if (getString("CONTROL_KEY: Baumen� ein/aus").equals(gFN(Keyboard.getEventKey()))) {
					baumenueShowen = !baumenueShowen;
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
						case state.BAUEN:
							if (aktuellesGeb�ude != -1) {
								Building b = Bauen.getBuildingforID(aktuellesGeb�ude, new Pos(xR * 32, yR * 32), spieler[0]);
								boolean hilfsboolean = false;
								boolean hilfsboolean2 = false;
								boolean hilfsboolean3 = false;
								if (b != null) {
									hilfsboolean = b.getSpieler().hatLager();
									hilfsboolean2 = b.getSpieler().hatMarktplatz();
									hilfsboolean3 = b.getSpieler().hatKornspeicher();
								}
								if (b == null) {
									if (spieler[0].hatLager()) {
										labelZuTeuer.setText("Das k�nnen wir uns nicht leisten, " + Loader.getCfgValue("SETTINGS: Profilname"));
									} else {
										labelZuTeuer.setText("Wir m�ssen ein Lager plazieren, " + Loader.getCfgValue("SETTINGS: Profilname"));
									}
									showGedNedSeitWann = 0;
									if (getChildIndex(labelZuTeuer) == -1) {
										add(labelZuTeuer);
										gednedShown = true;
									}
								} else if (newHandler.place(b)) {
									double d = b.getSpieler().getDifficulty();
									d = 2 - d;
									if (d == 0) {
										d = 0.5;
									}
									b.startTimer();
									if (b.getIndex() == 15) {
										if (!hilfsboolean2) {
											b.getSpieler().verteilen(12, (int) (10 * d));

											Bauen.setGesBev(b.getSpieler().getAmountofResource(Rohstoffe.Mensch().getID()));
											
											b.setTimerTask(new TimerTask() {
												@Override
												public void run() {
													if (b.getSpieler().possibleToRemove(Rohstoffe.Fleisch().getID(),
															(int)(Bauen.getGesBev() / 2))) {
														b.getSpieler().abziehen(Rohstoffe.Fleisch().getID(),
																(int) (Bauen.getGesBev() / 2));
													} else {
														b.getSpieler().setAmountofResource(Rohstoffe.Fleisch().getID(), 0);
														if (b.getSpieler().possibleToRemove(12, (int) (Bauen.getGesBev() / 8))){
														b.getSpieler().abziehen(Rohstoffe.Mensch().getID(),
																(int) (Bauen.getGesBev() / 10));
														}														
													}
												}
											});

										}
									}
									if (b.getIndex() == 14) {
										if (!hilfsboolean3) {
											b.getSpieler().verteilen(10, (int) (12 * d));
										}
									}
									if (hilfsboolean) {
										Bauen.kostenAbziehen(b);
									} else {
										// TODO Startrohstoffe
										b.getSpieler().verteilen(2, (int) (40 * d));
										b.getSpieler().verteilen(8, (int) (20 * d));
										b.getSpieler().verteilen(Rohstoffe.Getreide().getID(), (int) (5 * d));

										b.setKostetWarevonIndex(2, (int) (10 * d));
										b.setKostetWarevonIndex(8, (int) (5 * d));
									}
									forceUpdate = true;
								} else {
									labelZuTeuer.setText("Das kann da nicht plaziert werden, " + Loader.getCfgValue("SETTINGS: Profilname"));
									showGedNedSeitWann = 0;
									if (getChildIndex(labelZuTeuer) == -1) {
										add(labelZuTeuer);
										gednedShown = true;
									}
								}
							}
							break;
						case state.DEFAULT:
							newHandler.selClear();
							RectangleGraphicalObject object = newHandler.getOn(xR, yR);
							if (object instanceof Building) {
//								buildingSelected = ((Building) object).getIndex();TODO
								if (((Building) object).getIndex() == 6) {
									aktuellesGeb�ude = 20;
									inGameStat = state.BAUEN;
									if (cursorIndex == 1) {
										Mouse.setNativeCursor(cursors[0]);
//										gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));
										cursorIndex = 0;
									}
									newHandler.selClear();
									labelZuTeuer.setText("Plaziert eure Felder, " + Loader.getCfgValue("SETTINGS: Profilname"));
									showGedNedSeitWann = 0;
									if (getChildIndex(labelZuTeuer) == -1) {
										add(labelZuTeuer);
										gednedShown = true;
									}
								} else if (((Building) object).getIndex() == 18) {
									baumenueShowen = false;
									rekrutriernShown = true;
									// TODO rekrutiern
								}
							}
							break;
						case state.ABREI�EN:
							RectangleGraphicalObject on = newHandler.getOn(xR, yR);
							if (on instanceof Building) {
								if (((Building) on).getIndex() == 2 && !(on.getSpieler().hatWievieleLager() > 1)) {
									showGedNedSeitWann = 0;
									labelZuTeuer.setText("Wir k�nnen unser letztes Lager nicht abrei�en, " + Loader.getCfgValue("SETTINGS: Profilname"));
									if (getChildIndex(labelZuTeuer) == -1) {
										add(labelZuTeuer);
										gednedShown = true;
									}
									break;
								}
								if (((Building) on).getIndex() == 14 && !(on.getSpieler().hatWievieleKornspeicher() > 1)) {
									showGedNedSeitWann = 0;
									labelZuTeuer.setText("Wir k�nnen unseren letzten Kornspeicher nicht abrei�en, " + Loader.getCfgValue("SETTINGS: Profilname"));
									if (getChildIndex(labelZuTeuer) == -1) {
										add(labelZuTeuer);
										gednedShown = true;
									}
									break;
								}
								if (((Building) on).getIndex() == 15 && !(on.getSpieler().hatWievieleMarktplatz() > 1)) {
									showGedNedSeitWann = 0;
									labelZuTeuer.setText("Wir k�nnen unseren letzten Marktplatz nicht abrei�en, " + Loader.getCfgValue("SETTINGS: Profilname"));
									if (getChildIndex(labelZuTeuer) == -1) {
										add(labelZuTeuer);
										gednedShown = true;
									}
									break;
								}
							}

							RectangleGraphicalObject help = newHandler.remove(xR, yR, 0);
							if (help instanceof Building) {
								int[] kosten = ((Building) help).getKostetWarevonArray();
								for (int i = 0; i < kosten.length; i++) {
									help.getSpieler().verteilen(i, (int) Math.round(kosten[i] * r�ckerstattungsanteil));
								}
							}
							break;
						}
					}
				}

				if (Mouse.getEventButton() == 2) {
					ang = new Pos(CameraX + Mouse.getX() * scale, CameraY + Mouse.getY() * scale);
				}
				if (Mouse.getEventButton() == 1) {
					int x = (int) (Mouse.getX() * scale + CameraX);
					int y = (int) (Mouse.getY() * scale + CameraY);

					if (!isOn(Mouse.getX(), Mouse.getY())) {
						newHandler.processRightClick(x, y);
						inGameStat = state.DEFAULT;
						if (cursorIndex == 1) {
							Mouse.setNativeCursor(cursors[0]);
//							gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));
							cursorIndex = 0;
						}
						rekrutriernShown = false;
						baumenueShowen = true;
//						buildingSelected = -1;TODO
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
						case state.DEFAULT:
							newHandler.search(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
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
		newHandler.draw((int) CameraX / 32, (int) CameraY / 32, (int) (WIDTH * scale / 32), (int) (HEIGHT * scale / 32));
		Overlay.render(aktuellesGeb�ude);
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
	// // Für einzelauswahl
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
		if (showGedNedSeitWann > 200) {
			removeGedNed = true;
			showGedNedSeitWann = 0;
			gednedShown = false;
		}
		if (gednedShown) {
			showGedNedSeitWann++;
		}
		// handler.move();
		newHandler.tick();

		// for (Entry<Soldat, Soldat> entry : angriffe.entrySet()) {
		// Soldat krepierd = Battle.kampf(entry.getKey(), entry.getValue(), 0);
		// newHandler.die(krepierd);/*
		// * renderList[1].remove(handler.abrei�en
		// * (krepierd));
		// */
		// if (krepierd != null) {
		// angriffe.remove(entry.getKey());
		// angriffe.remove(krepierd);
		// for (Entry<Soldat, Soldat> entry2 : angriffe.entrySet()) {
		// if (entry2.getValue().equals(krepierd)) {
		// angriffe.remove(entry2.getKey());
		// }
		// }
		// }
		// }
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
		return (baumenueShowen || rekrutriernShown) && x > (WIDTH - width) / 2 && x < (WIDTH + width) / 2 && y < width / 7;
	}

	private void initUI() {
		renderer = null;
		try {
			renderer = new LWJGLRenderer();
		} catch (LWJGLException e) {
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
			URL url2 = Knightmare.class.getResource(new StringBuilder(Loader.getTexturePath().replace('\\', '/')).append("/menue.xml").toString());
			themeManager = ThemeManager.createThemeManager((url == null ? url2 : url), renderer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		gui.applyTheme(themeManager);
		TWLImage.setRenderer(gui.getRenderer());
		gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));

		GUI();

	}

	// UI Var
	private GUI gui;
	private LWJGLRenderer renderer;
	private ThemeManager themeManager;
	private Label l_fps, l_time, labelZuTeuer;
	private ResizableFrame frame, kopfframe, rekrutieren;
	private Button[] categories = new Button[6];
	private Button menue, einstellungen;
	private Button[][] geb�ude = new Button[6][10], rekru = new Button[20][2];
	private Label[] res = new Label[14];
	private Label[] resK = new Label[14];
	private String[] resn = { "IKohle", "IEisen", "IHolz", "IDiamant", "IPech", "ISand", "IWeizen", "ILehm", "IStein", "IM�nze", "IFleisch", "IGlas", "IPeople",
			"Armbrust" };
	private String[] resnT = { "Kohle", "Eisen", "Holz", "Diamanten", "Pech", "Sand", "Weizen", "Lehm", "Stein", "M�nzen", "Nahrung", "Glas", "Menschen", "Armbrust" };
	private boolean removeGedNed = false, gednedShown = false;
	private int showGedNedSeitWann;
	private String[] texInC0 = { buildings.images.KohleMine, buildings.images.EisenMine, buildings.images.Lager, buildings.images.Sandschmelze };
	private String[] texInC1 = { buildings.images.Holzf�ller, buildings.images.Steinbruch, buildings.images.SandGrube, buildings.images.F�rster,
			buildings.images.Lehmgrube, buildings.images.Pechgrube };
	private String[] texInC2 = { buildings.images.Waffenkammer, buildings.images.Kaserne, buildings.images.Schmied };
	private String[] texInC3 = { buildings.images.Kornspeicher, buildings.images.Bauernhof, buildings.images.Viehstall, buildings.images.B�ckerei };
	private String[] texInC4 = { buildings.images.Turm, buildings.images.Mauern };
	private String[] texInC5 = { buildings.images.Haus, buildings.images.Schatzkammer, buildings.images.Marktplatz, buildings.images.Schule, buildings.images.Kirche };
	private String[][] cInTexes = { texInC0, texInC1, texInC2, texInC3, texInC4, texInC5 };
	private String[] imgs = { "cP", "cR", "cM", "cN", "cV", "cZ" }, names = { "Produktion", "Resourcen", "Milit�r", "Nahrung", "Verteidigung", "Zivil" };
	private int[] idsInC0 = { buildings.ids.KohleMine, buildings.ids.EisenMine, buildings.ids.Lager, buildings.ids.Sandschmelze };
	private int[] idsInC1 = { buildings.ids.Holzf�ller, buildings.ids.Steinbruch, buildings.ids.SandGrube, buildings.ids.F�rster, buildings.ids.Lehmgrube,
			buildings.ids.Pechgrube };
	private int[] idsInC2 = { buildings.ids.Waffenkammer, buildings.ids.Kaserne, buildings.ids.Schmied };
	private int[] idsInC3 = { buildings.ids.Kornspeicher, buildings.ids.Bauernhof, buildings.ids.Viehstall, buildings.ids.B�ckerei };
	private int[] idsInC4 = { buildings.ids.Turm, buildings.ids.Mauern };
	private int[] idsInC5 = { buildings.ids.Haus, buildings.ids.Schatzkammer, buildings.ids.Marktplatz, buildings.ids.Schule, buildings.ids.Kirche };
	private int[][] cIndexes = { idsInC0, idsInC1, idsInC2, idsInC3, idsInC4, idsInC5 };

	@Override
	protected void layout() {
		int width = WIDTH;
		if (width > 1920) {
			width = 1920;
		}

		Pos baustart = new Pos((WIDTH - width) / 2 + width / 4 + width / 16, HEIGHT - width / 7 + 75);
		for (int x = 0; x < cInTexes.length; x++) {
			for (int y = 0; y < cInTexes[x].length; y++) {
				Image img = TWLImage.getImage(cInTexes[x][y]);
				double fact = (double) Math.max(img.getHeight(), img.getWidth()) / (double) 64;
				if (y == 0) {
					geb�ude[x][y].setSize((int) (img.getWidth() / fact), (int) (img.getHeight() / fact));
					geb�ude[x][y].setPosition((int) baustart.getX(), (int) baustart.getY());
					geb�ude[x][y].setBackground(img);
				} else {
					geb�ude[x][y].setSize((int) (img.getWidth() / fact), (int) (img.getHeight() / fact));
					geb�ude[x][y].setPosition((int) geb�ude[x][y - 1].getX() + geb�ude[x][y - 1].getWidth() + 10, (int) baustart.getY());
					geb�ude[x][y].setBackground(img);
				}
			}
		}

		rekru[18][0].setSize(64, 64);
		rekru[18][0].setPosition((int) baustart.getX(), (int) baustart.getY());
		rekru[18][0].setBackground(themeManager.getImage("Armbrust"));
		rekru[18][1].setSize(64, 64);
		rekru[18][1].setPosition((int) baustart.getX() + 74, (int) baustart.getY());
		rekru[18][1].setBackground(themeManager.getImage("Armbrust"));

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
		menue.setFont(themeManager.getFont("normalB"));

		einstellungen.setSize(menue.getHeight(), menue.getHeight());
		einstellungen.setPosition(menue.getX() - einstellungen.getWidth(), menue.getY());
		einstellungen.setBackground(themeManager.getImage("cfg"));

		l_fps.adjustSize();
		l_fps.setPosition((WIDTH - width) / 2 + 7, (kopfframe.getHeight() - l_fps.getHeight()) / 2);

		l_time.adjustSize();
		l_time.setPosition(WIDTH / 2 - l_time.getWidth() / 2, (kopfframe.getHeight() - l_time.getHeight()) / 2);

		labelZuTeuer.adjustSize();
		labelZuTeuer.setPosition((WIDTH - labelZuTeuer.getWidth()) / 2, (HEIGHT - labelZuTeuer.getHeight()) / 2);

		res[0].setText(String.valueOf(spieler[0].getAmountofResource(0)));
		res[0].adjustSize();
		res[0].setSize(Math.max(res[0].getWidth(), res[0].getHeight()), Math.max(res[0].getWidth(), res[0].getHeight()));
		res[0].setPosition(l_fps.getX() + l_fps.getWidth() + 10, (kopfframe.getHeight() - res[0].getHeight()) / 2);
		res[0].setBackground(themeManager.getImage("IKohle"));
		resK[0].setSize(res[0].getWidth(), res[0].getHeight());
		resK[0].setPosition(res[0].getX(), res[0].getY() + res[0].getHeight());
		for (int i = 1; i < resn.length; i++) {
			res[i].setText(String.valueOf(spieler[0].getAmountofResource(i)));
			res[i].adjustSize();
			res[i].setSize(Math.max(res[i].getWidth(), res[i].getHeight()), Math.max(res[i].getWidth(), res[i].getHeight()));
			res[i].setPosition(res[i - 1].getX() + res[i - 1].getWidth() + 10, (kopfframe.getHeight() - res[i].getHeight()) / 2);
			res[i].setBackground(themeManager.getImage(resn[i]));
			resK[i].setSize(res[i].getWidth(), res[i].getHeight());
			resK[i].setPosition(res[i].getX(), res[i].getY() + res[i].getHeight());
		}

		if (removeGedNed) {
			removeChild(labelZuTeuer);
			removeGedNed = false;
		}
		showKosten();
		gui.draw();

		// button.adjustSize(); //Calculate optimal size instead of manually
		// setting it
		super.layout();
	}

	private void setCategory() {
		for (Button[] button : geb�ude) {
			for (Button button1 : button) {
				removeChild(button1);
			}
		}
		if (category != -1) {
			for (Button button : geb�ude[category]) {
				if (button != null) {
					add(button);
				}
			}
		}
	}

	private void showKosten() {
//		for (int x = 0; x < cIndexes.length; x++) {
//			for (int y = 0; y < cIndexes[x].length; y++) {
//				if (geb�ude[x][y].getAnimationState().getAnimationState(Button.STATE_HOVER)) {
//					System.out.println("Hovering");TODO
//				}
//			}
//		}
		if (aktuellesGeb�ude != -1) {
			if(aktuellesGeb�ude != zuletztAktuellesGeb�ude || forceUpdate){
				forceUpdate = false;
				int[] help = Bauen.getKostenvonGeb(aktuellesGeb�ude);
				if (aktuellesGeb�ude == 2 && !spieler[0].hatLager()) {
					help = new int[resK.length];
				}
				for (int i = 0; i < resK.length; i++) {
					if (help[i] != 0) {
						if (resK[i].getBackground() == null) {
							resK[i].setBackground(themeManager.getImage("Fenster"));
						}
						resK[i].setText(String.valueOf(help[i]));
						if (help[i] > Integer.parseInt(res[i].getText())) {
							Font helpf = themeManager.getFont("normalR");
							if (!resK[i].getFont().equals(helpf)) {
								resK[i].setFont(helpf);
							}
						} else {
							Font helpf = themeManager.getFont("normalG");
							if (!resK[i].getFont().equals(helpf)) {
								resK[i].setFont(helpf);
							}
						}
					} else {
						resK[i].setText("");
						if (resK[i].getBackground() != null) {
							resK[i].setBackground(null);
						}
					}
				}
				zuletztAktuellesGeb�ude = aktuellesGeb�ude;
			}
		} else {
			for (Label label : resK) {
				label.setText("");
				if (label.getBackground() != null) {
					label.setBackground(null);
				}
			}
		}
	}

	private void GUI() {
		for (int x = 0; x < cIndexes.length; x++) {
			for (int y = 0; y < cIndexes[x].length; y++) {
				geb�ude[x][y] = new Button();
				geb�ude[x][y].setTooltipContent(new Label(Bauen.getBuildingName(cIndexes[x][y])));
				int helpX = x; // Weil java
				int helpY = y;
				geb�ude[x][y].addCallback(new Runnable() {

					@Override
					public void run() {
						geb�ude[helpX][helpY].setTintAnimator(new TintAnimator(geb�ude[helpX][helpY], Color.GRAY));
						inGameStat = state.BAUEN;
						if (cursorIndex == 1) {
							try {
								Mouse.setNativeCursor(cursors[0]);
							} catch (LWJGLException e) {
								e.printStackTrace();
							}
//							gui.setMouseCursor((MouseCursor) themeManager.getCursor("cursor1"));
							cursorIndex = 0;
						}
						aktuellesGeb�ude = cIndexes[helpX][helpY];
						geb�ude[helpX][helpY].getTintAnimator().fadeTo(Color.WHITE, 100);
					}
				});
			}
		}
		rekru[18][0] = new Button();
		rekru[18][0].setTooltipContent(new Label("Armbrustsch�tze"));
		rekru[18][0].addCallback(new Runnable() {

			@Override
			public void run() {
				Pos h = spieler[0].findFreeNearMarkt();
				if (h != null) {
					Soldat s = Rekrutieren.Hussar((int) h.getX() * 32 + 16, (int) h.getY() * 32 + 16, spieler[0]);
					if (s != null) {
						newHandler.place(s);
					}
				}
			}
		});
		rekru[18][1] = new Button();
		rekru[18][1].setTooltipContent(new Label("Speertr�ger"));
		rekru[18][1].addCallback(new Runnable() {

			@Override
			public void run() {
				Pos h = spieler[0].findFreeNearMarkt();
				if (h != null) {
					Soldat s = Rekrutieren.Speertraeger((int) h.getX() * 32 + 16, (int) h.getY() * 32 + 16, 32, 32, spieler[0]);
					if (s != null) {
						newHandler.place(s);
					}
				}
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
					category = help;
					setCategory();
					for (int j = 0; j < categories.length; j++) {
						if (j != help) {
							categories[j].getAnimationState().setAnimationState(STATE_DISABLED, true);
						} else {
							categories[j].getAnimationState().setAnimationState(STATE_DISABLED, false);
						}
					}
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
				new Optionen(true);
			}
		});

		l_fps = new Label("");
		l_fps.setTheme("label");

		l_time = new Label("");
		l_time.setTheme("label");

		labelZuTeuer = new Label("Das K�nnen wir uns nicht leisten " + Loader.getCfgValue("SETTINGS: Profilname"));

		for (int i = 0; i < resn.length; i++) {
			res[i] = new Label();
			res[i].setTooltipContent(new Label(resnT[i]));
			resK[i] = new Label();
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
			add(resK[i]);
		}

		frame = new ResizableFrame();
		frame.setTheme("frame");
		frame.setTitle("Inventory");
		rekrutieren = new ResizableFrame();
		rekrutieren.setTheme("frame");
		rekrutieren.setTitle("Inventory");

		int width = WIDTH;
		if (width > 1920) {
			width = 1920;
		}

		frame.setSize(width, width / 7);
		frame.setPosition((WIDTH - width) / 2, HEIGHT - width / 7);
		rekrutieren.setSize(width, width / 7);
		rekrutieren.setPosition((WIDTH - width) / 2, HEIGHT - width / 7);

		l_fps.setPosition(100, 10);
		l_fps.setSize(20, 40);

		l_time.setPosition(WIDTH - 200, 10);
		l_time.setSize(20, 40);

		gui.draw();

		// button.adjustSize(); //Calculate optimal size instead of manually
		// setting it
	}

	public void removeBaumen�Content() {
		if (category >= 0) {
			for (Button geb : geb�ude[category]) {
				removeChild(geb);
			}
		}
		for (Button button : categories) {
			removeChild(button);
		}
	}

	public void addBaumen�Content() {
		for (Button button : categories) {
			add(button);
		}
		setCategory();
	}

	public void Baumen�() {
		if (baumenueShowen) {
			if (getChildIndex(frame) == -1) {
				add(frame);
				for (Button button : categories) {
					add(button);
				}
				setCategory();
			}
		} else {
			removeChild(frame);
			if (category >= 0) {
				for (Button geb : geb�ude[category]) {
					removeChild(geb);
				}
			}
			for (Button button : categories) {
				removeChild(button);
			}
		}
		if (rekrutriernShown) {
			if (getChildIndex(rekrutieren) == -1) {
				add(rekrutieren);
				add(rekru[18][0]);
				add(rekru[18][1]);
			}

			// TODO
		} else {
			removeChild(rekrutieren);
			removeChild(rekru[18][0]);
			removeChild(rekru[18][1]);
		}
	}

	public void handlInput() {
		gui.setSize();
		gui.updateTime();
		Baumen�();
		try {
			pollInput();
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

}
