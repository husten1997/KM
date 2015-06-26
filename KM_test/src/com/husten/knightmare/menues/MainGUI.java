package com.husten.knightmare.menues;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.lwjgl.LWJGLException;

import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

public class MainGUI extends Widget {
	
	private LWJGLRenderer renderer;
	private GUI gui;
	ThemeManager theme;
	private Button button;
	
	public MainGUI(Widget rootPane) throws LWJGLException, MalformedURLException, IOException{
			renderer = new LWJGLRenderer();
			gui = new GUI(rootPane, renderer);
			theme = ThemeManager.createThemeManager(this.getClass().getResource("test.xml"), renderer);
			//Probleme mit dem xml-File #ichsteignichtganzdurch
			gui.applyTheme(theme);
			init();
			
	}
	
	public void update(){
		gui.update();
	}
	
	public void destroy(){
		gui.destroy();
		theme.destroy();
	}
	
	public void init(){
		button = new Button("Epic button");
	    button.setTheme("button_Test");
	    button.setPosition(100, 100);
		button.setSize(500, 500);
	    add(button);
	}

	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		 button.setPosition(100, 100);
		 button.setSize(100, 33);
		super.layout();
	}
	
	

}
