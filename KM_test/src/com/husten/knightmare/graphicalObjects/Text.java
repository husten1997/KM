package com.husten.knightmare.graphicalObjects;

import static org.lwjgl.opengl.GL11.glRotatef;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import org.newdawn.slick.*;

import com.richard.knightmare.util.Pos;

public class Text extends gasset {
	
	TrueTypeFont font;
	String Text;
	double xPos;
	double yPos;
	Color color;
	
	public Text(String FontS, int style ,int size, String Text, double xPos, double yPos, Color color){
		Font[] all = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		
		Font awtFont = new Font(all[1].getFontName(), style, size);
		
		
	    font = new TrueTypeFont(awtFont, false);
	    
	    
//		font = new UnicodeFont(awtFont);
	    this.Text = Text;
	    this.xPos = xPos;
	    this.yPos = yPos;
	    this.color = color;
	}

	@Override
	public void draw() {
		font.drawString((float)xPos, (float)yPos, Text, color);
		
		
		
	}

	@Override
	public void draw2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return xPos;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return yPos;
	}

	@Override
	public void setX(double x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pos getPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPos(Pos p) {
		// TODO Auto-generated method stub
		
	}

}