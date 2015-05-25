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

import java.io.IOException;

import com.richard.knightmare.util.Pos;

public class QUAD extends gasset {
	private double height, width, Cs = 1, tc_x = 1, tc_y = 1;
	private Pos position;
	private int rotation;
	private Texture texture;

	public QUAD(Pos position, double width, double height) {
		this.height = height;
		this.width = width;
		this.position = position;
		rotation = (int) (Math.random() * 4);
	}

	public QUAD(float l, float h, float x, float y, TextureLoader loader, String ref) {
		height = h;
		width = l;
		position = new Pos(x, y);
		double ran = Math.random() * 4;
		rotation = (int) ran;
		try {
			texture = loader.getTexture(ref);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public QUAD(float x, float y, float cs, TextureLoader loader, String ref) {
		position = new Pos(x, y);
		Cs = cs;
		double ran = Math.random() * 4;
		rotation = (int) ran;
		try {
			texture = loader.getTexture(ref);
			init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public QUAD(float x, float y, TextureLoader loader, String ref) {
		position = new Pos(x, y);
		double ran = Math.random() * 4;
		rotation = (int) ran;
		try {
			texture = loader.getTexture(ref);
			init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public QUAD(float h, float b, float x, float y) {
		height = h;
		width = b;
		position.setX(x);
		position.setY(y);
		double ran = Math.random() * 4;
		rotation = (int) ran;
	}

	public void init() {
		height = texture.getImageHeight() * Cs;
		width = texture.getImageWidth() * Cs;
	}
	// Getters § Setters ------------------------------------

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	// Relatiever setter
	public void setrH(float h) {
		height += h;
	}

	public void setCS(float cs) {
		Cs = cs;
	}

	public float getCS() {
		return Cs;
	}

	public float getL() {
		return width;
	}

	public void setL(float l) {
		width = l;
	}

	public void setrL(float l) {
		width += l;
	}

	@Override
	public float getX() {
		return (float) position.getX();
	}

	public float getXC() {
		return (float) position.getX() + 16;
	}

	@Override
	public void setX(float x) {
		position.setX(x);
	}

	public void setrX(float x) {
		position.setX(position.getX() + x);
	}

	@Override
	public float getY() {
		return (float) position.getY();
	}

	@Override
	public void setY(float y) {
		position.setY(y);
	}

	public void setrY(float y) {
		position.setY(position.getY() + y);
	}

	public void setTCX(float x) {
		tc_x = x;
	}

	public void setTCY(float y) {
		tc_y = y;
	}

	public void transformXY(float x, float y) {
		position.setX(x);
		position.setY(y);
	}

	public Pos getPos() {
		return position;
	}

	public void setPos(Pos pos) {
		this.position = pos;
	}

	@Override
	public void draw2() {
		double x = ((Math.random() * 2) - 1) * 5;
		double y = ((Math.random() * 2) - 1) * 5;
		setrX((float) x);
		setrY((float) y);
		draw();
		glColor3f(1f, 1f, 1f);
	}

	@Override
	public void draw() {
		// store the current model matrix
		glPushMatrix();
		// bind to the appropriate texture for this sprite
		texture.bind();
		glMatrixMode(GL_TEXTURE);
		glLoadIdentity();
		glRotatef(90 * rotation, 0f, 0f, 1f);
		glMatrixMode(GL_MODELVIEW);
		// translate to the right location and prepare to draw
		glTranslatef((float) position.getX(), (float) position.getY(), 0);
		// draw a quad textured to match the sprite
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);

			glTexCoord2f(0, -texture.getHeight() * tc_y);
			glVertex2f(0, height);

			glTexCoord2f(-texture.getWidth() * tc_x, -texture.getHeight() * tc_y);
			glVertex2f(width, height);

			glTexCoord2f(-texture.getWidth() * tc_x, 0);
			glVertex2f(width, 0);

		}
		glEnd();
		glColor3f(1f, 1f, 0.9f);
		// restore the model view matrix to prevent contamination
		glPopMatrix();
	}

}
