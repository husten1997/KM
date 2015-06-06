package com.husten.knightmare.menues;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.lwjgl.LWJGLException;

import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class MainGUI extends Widget {
	
	private LWJGLRenderer renderer;
	private GUI gui;
	
	public MainGUI(Widget rootPane) throws LWJGLException, MalformedURLException, IOException{
			renderer = new LWJGLRenderer();
			gui = new GUI(rootPane, renderer);
			ThemeManager theme = ThemeManager.createThemeManager(new File("gameui.xml").toURL(), renderer);
			//Probleme mit dem xml-File #ichsteignichtganzdurch
			
			
	}
	
	public void update(){
		gui.update();
	}

}
