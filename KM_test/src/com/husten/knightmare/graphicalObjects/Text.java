package com.husten.knightmare.graphicalObjects;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;
import java.awt.Font;

import com.richard.knightmare.util.Pos;

public class Text extends gasset {
	
//	TrueTypeFont font;
//	String Text;
	private double xPos, yPos, height, width;
	private Texture texture;
//	Color color;
	
//	public Text(String FontS, int style ,int size, String Text, double xPos, double yPos, Color color){
//		Font[] all = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//		
//		Font awtFont = new Font(all[1].getFontName(), style, size);
//		
//		
//	    font = new TrueTypeFont(awtFont, false);
//	    
//	    
////		font = new UnicodeFont(awtFont);
//	    this.Text = Text;
//	    this.xPos = xPos;
//	    this.yPos = yPos;
//	    this.color = color;
//	}
	
	public Text(TextureLoader textureLoader, double width, double height, double xPos, double yPos, String text, Color color, Font font){
		this.xPos = xPos;
		this.yPos = yPos;
		texture = textureLoader.getStringTexture(text, (int) width, (int) height, color, font);
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		
	}

	@Override
	public void draw() {
//		font.drawString((float)xPos, (float)yPos, Text, color);
		// store the current model matrix
				glPushMatrix();
				// bind to the appropriate texture for this sprite
				texture.bind();
				glMatrixMode(GL_TEXTURE);
				glLoadIdentity();
				glRotatef(0, 0f, 0f, 1f);
				glMatrixMode(GL_MODELVIEW);
				// translate to the right location and prepare to draw
				glTranslatef((float) xPos, (float) yPos, 0);
				// draw a quad textured to match the sprite
				glBegin(GL_QUADS);
				{
					glTexCoord2f(-(float) (texture.getWidth()), 0);
					glVertex2f(0, 0);

					glTexCoord2f(-(float) (texture.getWidth()), -(float) (texture.getHeight()));
					glVertex2f(0, (float) height);

					glTexCoord2f(0, -(float) (texture.getHeight()));
					glVertex2f((float) width, (float) height);

					glTexCoord2f(0, 0);
					glVertex2f((float) width, 0);

				}
				glEnd();
				glColor3f(1f, 1f, 0.9f);
				// restore the model view matrix to prevent contamination
				glPopMatrix();
		
		
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