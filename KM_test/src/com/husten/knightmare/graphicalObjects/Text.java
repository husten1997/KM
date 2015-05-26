package com.husten.knightmare.graphicalObjects;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.richard.knightmare.util.Pos;

public class Text extends gasset {
	
	TrueTypeFont font;
	String Text;
	double xPos;
	double yPos;
	Color color;
	
	public Text(String Font, int style ,int size, String Text, double xPos, double yPos, Color color){
		Font awtFont = new Font(Font, style, size);
	    font = new TrueTypeFont(awtFont, false);
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