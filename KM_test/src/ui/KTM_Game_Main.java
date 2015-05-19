package ui;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import loader.TextureLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.richard.knightmare.sound.MoodMusic;
import com.richard.knightmare.util.Environment;

import res.*;
import res.thread.GrafikThread;
import res.thread.WorkingThread;
import assets.*;

public class KTM_Game_Main implements StringConstants {

	/** time at last frame */
	private long lastFrame;
	/** frames per second */
	private int fps;
	private float FPS = 60;
	/** last fps time */
	private long lastFPS;

	private int ebenen = 3;
	private String inGameStat = NOTHING;

	public static int WIDTH = 1600;
	public static int HEIGHT = 900;
	private boolean fS = true; // fullscreen?
	private boolean Vsync = false;
	private int VsyncF = 120;
	private float delta;

	private Soldat figur;
	private Terrain terrain;
	
	private Pos pos1 = new Pos(0, 0);
	private Pos pos2 = new Pos(0, 0);
	public static float CameraX = 0;
	public static float CameraY = 0;
	private int s = 5;
	public static float scale = 1f;
	
	private HashMap<Soldat, Vektor> vektoren = new HashMap<>();

	private ArrayList<gasset> selection = new ArrayList<gasset>();

	private ArrayList<gasset> renderList[] = new ArrayList[ebenen];

	private Soldat figuren[] = new Soldat[s];

	private TextureLoader textureLoader;
	static WorkingThread gT;

	public static void main(String[] argv) {
		KTM_Game_Main hw = new KTM_Game_Main();
		gT = new GrafikThread(hw);
		hw.start();
	}

	public void start() {

		init();
		objectinit();
		
		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			delta = ((float) (1000))/(FPS==0?1:FPS);
			System.out.println("Fps:"+FPS+"|Delta:"+delta);
//			grafikCycl();
			
			gameCycl();
			gT.run();
			updateDisplay();
			updateFPS();
		}
		Display.destroy();
	}

	public void init() {
		Environment.setUpEnvironment("Ares", "Knightmare");
		MoodMusic.init();
		// verwendet eure aktuelle desktopauflösung als gameauflösung
		initRes();

		try {
//			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
//			DisplayMode DM = new DisplayMode(WIDTH, HEIGHT);
//			DM.
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

	public void initRender(gasset input, int e, int se) {
		input.setSort(se);
		renderList[e].add(input);
	}

	public void objectinit() {

		for (int i = 0; i < ebenen; i++) {
			renderList[i] = new ArrayList<gasset>();
		}

		for (int i = 0; i < s; i++) {
			double x = Math.random() * 1200;
			double y = Math.random() * 800;
			figuren[i] = new Soldat(32, 32, (float) x, (float) y, textureLoader, "textures/figure.png");
			initRender(figuren[i], 1, 1);
		}
		terrain = new Terrain(textureLoader, (512) + 1, (512) + 1);

		figur = new Soldat(32, 32, 0, 0, textureLoader, "textures/figure.png");

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

	public void pollInput() {
//		int delta = this.delta;

		if (Mouse.isButtonDown(0)) {

		}
		
		if (Mouse.isButtonDown(1)) {

		}
		
		int dWheel = Mouse.getDWheel();
	    if (dWheel < 0) {
	        scale += 0.1f;
//	        glOrtho(0, WIDTH*scale, 0, HEIGHT*scale, 3, -1);
	    } else if (dWheel > 0){
	        scale -= 0.1f;
//	        glOrtho(0, WIDTH*scale, 0, HEIGHT*scale, 3, -1);
	   }
		
		

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("Space is down");
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			figur.setrY(0.3f * delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			figur.setrX(-0.3f * delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			figur.setrY(-0.3f * delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			figur.setrX(0.3f * delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			CameraY += 0.5f * delta;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			CameraX -= 0.5f * delta;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			CameraY -= 0.5f * delta;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			CameraX += 0.5f * delta;
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

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {

				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					System.exit(0);
				}
				
				if (Keyboard.getEventKey() == Keyboard.KEY_R) {
					scale = 1f;
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_C) {
					inGameStat = S_TRUPS;
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					inGameStat = S_BUILDINGS;
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_B) {
					inGameStat = N_BUILDINGS;
					System.out.println(inGameStat);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_N) {
					inGameStat = N_TRUPS;
					System.out.println(inGameStat);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
					MoodMusic.changeVolume(-0.5f);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F2) {
					MoodMusic.changeVolume(0.5f);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_F11) {
					if (fS) {
						WIDTH = 1600;
						HEIGHT = 900;
					} else {
						initRes();
					}
					setDisplayMode(WIDTH, HEIGHT, !fS);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_F12) {
					Vsync = !Vsync;
//					Display.setVSyncEnabled(Vsync);
				}
				
				if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD0){
					System.out.println(selection.size());
				}

			}
		}

		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				
				if (Mouse.getEventButton() == 0) {
					int x = Mouse.getX() + (int) CameraX;
					int y = Mouse.getY() + (int) CameraY;

					x*=scale;
					y*=scale;
					
					pos1.setxPos(x);
					pos1.setyPos(y);

					int xR = x / 32;
					int yR = y / 32;

					switch (inGameStat) {
					case N_BUILDINGS:
						initRender(new Building((xR / 2) * 64, yR * 32, textureLoader, "textures/Haus.png"), 1, 2);
						break;
					case N_TRUPS:
						initRender(new Soldat(xR * 32, yR * 32, textureLoader, "textures/figure.png"), 1, 1);
						break;
					case S_TRUPS:
						search(x, y);
						figur = (Soldat) selection.get(selection.size() - 1);
						break;
					case S_BUILDINGS:
						search(x, y);
						break;
					}
				}

				if (Mouse.getEventButton() == 1) {
					int x = Mouse.getX() + (int) CameraX;
					int y = Mouse.getY() + (int) CameraY;
					
					x*=scale;
					y*=scale;
					
					Pos p1 = new Pos(x, y); //Ende

					switch (inGameStat) {
					case NOTHING:
						break;
					case S_TRUPS:
						for (int i = 0; i < selection.size(); i++) {
							Pos p2 = selection.get(i).getPos(); //Start
							if(selection.get(i).getType().equals(StringConstants.EINEHEIT)){
								Soldat h = (Soldat) selection.get(i);
								if(vektoren.get(h)==null){
									vektoren.put(h, new Vektor(p2, p1, h));
								}else{
									vektoren.get(h).setEnde(p1);
								}
							}
						}
						break;
					case S_BUILDINGS:
						break;

					}
				}

			} else {
				//Buton releasd
				if (Mouse.getEventButton() == 0) {
					int x = Mouse.getX() + (int) CameraX;
					int y = Mouse.getY() + (int) CameraY;

					x*=scale;
					y*=scale;
					
					pos2.setxPos(x);
					pos2.setyPos(y);

					int xR = x / 32;
					int yR = y / 32;

					switch (inGameStat) {
					case N_BUILDINGS:
						initRender(new Building((xR / 2) * 64, yR * 32, textureLoader, "textures/Haus.png"), 1, 2);
						break;
					case N_TRUPS:
						initRender(new Soldat(xR * 32, yR * 32, textureLoader, "textures/figure.png"), 1, 1);
						break;
					case S_TRUPS:
						search((float) pos1.getxPos(), (float) pos1.getyPos(), (float) pos2.getxPos(), (float) pos2.getyPos());
						for (int i = 0; i < selection.size(); i++) {
							if(selection.get(i).getType().equals(StringConstants.EINEHEIT)){
								((Soldat) selection.get(i)).say();
							}
						}
						break;
					case S_BUILDINGS:
						search(x, y);
						break;

					}
				}
			}
		}

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
//		System.out.println(time - lastFrame);
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

	public void search(float x1, float y1, float x2, float y2) {
		if(x1 == x2 && y1 == y2){
			search(x1, y1);
		}else{
			selection.clear();
			float Px1;
			float Px2;

			float Py1;
			float Py2;

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

							if(renderList[e].get(i).getType().equals(StringConstants.EINEHEIT)){
								selection.add(renderList[e].get(i));
							}
						}
					}
				}
			} catch (Exception e) {

			}
		}
		
	}
	//Für einzelauswahl
	public void search(float x, float y) {
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
//		float delta = getDelta()*0.5f;
		Object[] vek = vektoren.values().toArray();
		Vektor[] vekk = new Vektor[vek.length];
		for(int i = 0; i<vek.length; i++){
			vekk[i] = (Vektor) vek[i];
		}
		
		for(int i = 0; i<vekk.length; i++){
			if(vekk[i].move(delta*0.1f)){
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
			fS = fullscreen;
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
		System.out.println("H: " +  HEIGHT + " W: " + WIDTH);
	}

	public void initDisplay() {

		setDisplayMode(WIDTH, HEIGHT, fS);
		System.out.println("H: " +  HEIGHT + " W: " + WIDTH);
	}

	public void updateDisplay() {
		if(Vsync)Display.sync(VsyncF);
		
		
		Display.update();
	}
	
	public void grafikCycl(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
//		glTranslatef(CameraMX, CameraMY, 0f);
		glOrtho(0, WIDTH*scale, 0, HEIGHT*scale, 3, -1);
		glTranslatef(-CameraX, -CameraY, 0f);
		
		
		
		

		glMatrixMode(GL_MODELVIEW);

		glLoadIdentity();

		render();
	}
	
	public void gameCycl(){
		
		testVariable();
		calc();
		pollInput();
		
	}
	
	public void updateZoom(){
		
	}
	
	private void testVariable(){
		if(scale < 0.1f){
			scale = 0.1f;
		}
	}
	
	

}
