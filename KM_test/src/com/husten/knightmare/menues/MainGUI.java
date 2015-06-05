package com.husten.knightmare.menues;

import org.lwjgl.LWJGLException;

import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;

public class MainGUI extends Widget {
	
	private LWJGLRenderer renderer;
	
	public MainGUI(Widget rootPane) throws LWJGLException{
			renderer = new LWJGLRenderer();
			GUI gui = new GUI(rootPane, renderer);
			
			
	}

}
