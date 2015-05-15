package ui;


import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.Comparator;

import loader.TextureLoader;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.richard.knightmare.Environment;
import com.richard.knightmare.MoodMusic;

import res.*;
import assets.*;


public class KTM_Game_Main implements Strings{
	
	/** time at last frame */
	private long lastFrame;
    /** frames per second */
	private int fps;
    /** last fps time */
	private long lastFPS;
    
	private int ebenen = 3;
	private String inGameStat = NOTHING;
    
    public static int WIDTH = 1600;
    public static int HEIGHT = 900;
    private boolean fS = true; //fullscreen?
    private int delta;

    private Soldat figur;
    private Terrain terrain;
    private float CameraMXR = 0;
    private float CameraMYD = 0;
    private float CameraMXL = 0;
    private float CameraMYU = 0;
    private float CameraMX = 0;
    private float CameraMY = 0;
    private Pos pos1 = new Pos(0,0);
    private Pos pos2 = new Pos(0,0);
    private Pos p1;
    public static float CameraX = 0;
    public static float CameraY = 0;
    private ArrayList<VektorHandler> CalV = new ArrayList<VektorHandler>();
    private Vektor xy;
    private int s = 5;
    
    private ArrayList<gasset> selection = new ArrayList<gasset>();
    
    private ArrayList<gasset> renderList[] = new ArrayList[ebenen];
    
    private Soldat figuren[] = new Soldat[s];
    
    private TextureLoader   textureLoader;
    
    
    
    public static void main(String[] argv){
		KTM_Game_Main hw = new KTM_Game_Main();
		hw.start();
	}

	public void start(){
		
	    init();
	    objectinit();
	    
		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			glMatrixMode(GL_PROJECTION);
			glTranslatef(CameraMX, CameraMY, 0f);
			CameraMX = 0;
			CameraMY = 0;
			
			CameraMYU = 0;
			CameraMYD = 0;
			
			CameraMXL = 0;
			CameraMXR = 0;
			
			glMatrixMode(GL_MODELVIEW);
			
	        glLoadIdentity();
	
			render();
			calc();
			pollInput();
			CameraMX -= CameraMXR + CameraMXL;
			CameraMY -= CameraMYU + CameraMYD;
			CameraX -= CameraMX;
			CameraY -= CameraMY;
			Display.update();
			updateFPS();
		}
		Display.destroy();
	}

	public void init(){
		Environment.setUpEnvironment("Ares", "Knightmare");
		MoodMusic.init();
		
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			
			Display.create();
		}catch(LWJGLException e){
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
	    setDisplayMode(WIDTH, HEIGHT, fS);
	}

	public void initRender(gasset input, int e, int se){
		input.setSort(se);
		renderList[e].add(input);
	}

	public void objectinit(){
    	
    	for(int i = 0; i < ebenen; i++){
    		renderList[i] = new ArrayList<gasset>();
    	}

    	for(int i = 0; i < s; i++){
    		double x = Math.random()*1200;
    		double y = Math.random()*800;
    		figuren[i] = new Soldat(32, 32, (float)x, (float)y, textureLoader, "textures/figure.png" );
    		initRender(figuren[i], 1,1);
    	}
    	terrain = new Terrain(textureLoader, (512)+1, (512)+1);
    	

    	figur = new Soldat(32, 32, 0, 0, textureLoader, "textures/figure.png");

    	//Sorting
    	for(int i = 0; i < ebenen; i++){
    		renderList[i].sort(new Comparator<gasset>() {
    	        @Override
    	        public int compare(gasset  i1, gasset  i2)
    	        {
    	            return  (i1.getSort() < i2.getSort() ? -1:1);
    	        }
    	    });
    	}
    }
    
    public void pollInput(){
		
		if(Mouse.isButtonDown(0)){
			
		}
		if(Mouse.isButtonDown(1)){
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			System.out.println("Space is down");
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			figur.setrY(0.3f*delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			figur.setrX(-0.3f*delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			figur.setrY(-0.3f*delta);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			figur.setrX(0.3f*delta);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			setDisplayMode(WIDTH, HEIGHT, !fS);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			CameraMYU = 0.5f * delta;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			CameraMXL = -0.5f * delta;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			CameraMYD = -0.5f * delta;	
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			CameraMXR = 0.5f * delta;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
			
		}
		
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){

				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					System.exit(0);
				}
				
				if(Keyboard.getEventKey() == Keyboard.KEY_C){
					inGameStat = S_TRUPS;
					System.out.println(inGameStat);
				}
				
				if(Keyboard.getEventKey() == Keyboard.KEY_V){
					inGameStat = S_BUILDINGS;
					System.out.println(inGameStat);
				}
				
				if(Keyboard.getEventKey() == Keyboard.KEY_B){
					inGameStat = N_BUILDINGS;
					System.out.println(inGameStat);
				}
				
				if(Keyboard.getEventKey() == Keyboard.KEY_N){
					inGameStat = N_TRUPS;
					System.out.println(inGameStat);
				}
			}
		}
		
		while(Mouse.next()){
			if(Mouse.getEventButtonState()){
				if(Mouse.getEventButton() == 0){
					int x = Mouse.getX() + (int)CameraX;
					int y = Mouse.getY() + (int)CameraY;
					
					pos1.setxPos(x);
					pos1.setyPos(y);
					
					int xR = x/32;
					int yR = y/32;
					
					switch (inGameStat){
					case N_BUILDINGS:
						initRender(new Building((xR/2)*64, yR*32, textureLoader, "textures/Haus.png"), 1, 2);
						break;
					case N_TRUPS:
						initRender(new Soldat(xR*32, yR*32, textureLoader, "textures/figure.png"), 1, 1);
						break;
					case S_TRUPS:
						search(x, y);
						figur = (Soldat)selection.get(selection.size()-1);
						break;
					case S_BUILDINGS:
						search(x, y);
						break;
					}
				}
				
				
				if(Mouse.getEventButton() == 1){
					int x = Mouse.getX() + (int)CameraX;
					int y = Mouse.getY() +  (int)CameraY;
					
					p1 = new Pos(x, y);
					
					switch (inGameStat){
					case NOTHING:
						break;
					case S_TRUPS:
					      for(int i = 0; i <selection.size(); i++){
					       Pos p2 = selection.get(i).getPos();

					       xy = new Vektor(p2, p1);
					       CalV.add(new VektorHandler(selection.get(i), xy, p2, p1));
					      }
					      break;
					case S_BUILDINGS:
						break;
					
					}
				}
				
				
			}
			else{
				if(Mouse.getEventButton() == 0){
					int x = Mouse.getX() + (int)CameraX;
					int y = Mouse.getY() + (int)CameraY;
					
					pos2.setxPos(x);
					pos2.setyPos(y);
					
					int xR = x/32;
					int yR = y/32;
					
					switch (inGameStat) {
					case N_BUILDINGS:
						initRender(new Building((xR/2)*64, yR*32, textureLoader, "textures/Haus.png"), 1, 2);
						break;
					case N_TRUPS:
						initRender(new Soldat(xR*32, yR*32, textureLoader, "textures/figure.png"), 1, 1);
						break;
					case S_TRUPS:
						search((float)pos1.getxPos(), (float)pos1.getyPos(), (float)pos2.getxPos(), (float)pos2.getyPos());
						for(int i = 0; i < selection.size(); i++){
							((Soldat)selection.get(i)).say();
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
	
	public void render(){
		terrain.draw();
		for(int e = 0; e < ebenen; e++){
			for(int i = 0; i < renderList[e].size(); i++){
				renderList[e].get(i).draw();
			}
		}
		figur.draw();
		
	}
	
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	//Delta --> zeit die seit dem letzten frame vergangen ist => kann man benutzen für frame unabhängige bewegungen zb
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	         
	    return delta;
	}
	
	public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
        delta = getDelta();
    }
	
	public void search(float x1, float y1, float x2, float y2){
		selection.clear();
		float Px1;
		float Px2;
		
		float Py1;
		float Py2;
		
		if(x1 < x2){
			Px1 = x2;
			Px2 = x1;
		}
		else{
			Px1 = x1;
			Px2 = x2;
		}
		
		if(y1 < y2){
			Py1 = y2;
			Py2 = y1;
		}
		else{
			Py1 = y1;
			Py2 = y2;
		}
		
		try {
			for(int e = 0; e < ebenen; e++){
				
				for(int i = 0; i < renderList[e].size(); i++){
					if(renderList[e].get(i).getX() <= Px1 && renderList[e].get(i).getX() >= Px2  &&
							renderList[e].get(i).getY() <= Py1 &&renderList[e].get(i).getY() >= Py2) {
						
						selection.add(renderList[e].get(i));
					}
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	public void search(float x, float y){
		gasset xy = figur;
		selection.clear();
		
		try {
			for(int e = 0; e < ebenen; e++){
				
				for(int i = 0; i < renderList[e].size(); i++){
					if(renderList[e].get(i).getX() <= x && renderList[e].get(i).getX() >= x-64  &&
							renderList[e].get(i).getY() <= y &&renderList[e].get(i).getY() >= y-64) {
						xy = renderList[e].get(i);
					}
				}
			}
		} catch (Exception e) {
			
		}
		selection.add(xy);
	}
	
	public void calc(){
		for(int i = 0; i < CalV.size(); i++){
			CalV.get(i).red();
			System.out.println(CalV.get(i).getO().getPos().getPos());
		}
	}
	
	public void setDisplayMode(int width, int height, boolean fullscreen) {
		 
        // return if requested DisplayMode is already set
                if ((Display.getDisplayMode().getWidth() == width) && 
            (Display.getDisplayMode().getHeight() == height) && 
            (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {
            DisplayMode targetDisplayMode = null;
             
            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;
                 
                for (int i=0;i<modes.length;i++) {
                    DisplayMode current = modes[i];
                     
                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }
 
                        // if we've found a match for bpp and frequence against the 
                        // original display mode then it's probably best to go for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                            (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width,height);
            }
             
            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
                return;
            }
 
            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);
            fS = fullscreen;
            
             
        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
        }
    }

	public float getH(){
		return HEIGHT;
	}

	public float getW(){
		return WIDTH;
	}

}
