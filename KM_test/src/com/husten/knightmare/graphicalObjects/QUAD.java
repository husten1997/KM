package com.husten.knightmare.graphicalObjects;

import static org.lwjgl.opengl.GL11.*;

import com.richard.knightmare.util.Pos;

public class QUAD extends gasset {
	private double height, width, scale = 1, tc_x = 1, tc_y = 1;
	private Pos position;
	private int rotation;
	private Texture texture;
	private TextureLoader loader;
	private String name;

	public QUAD(Pos position, double width, double height) {
		this.height = height;
		this.width = width;
		this.position = position;
		rotation = (int) (Math.random() * 4);
	}

	public QUAD(Pos position, double width, double height, TextureLoader loader, String textureName) {
		this.height = height;
		this.width = width;
		this.position = position;
		rotation = (int) (Math.random() * 4);
		name = textureName;
		this.loader = loader;
	}

	public QUAD(Pos position, double scale, TextureLoader loader, String textureName) {
		this.position = position;
		this.scale = scale;
		rotation = (int) (Math.random() * 4);
		name = textureName;
		this.loader = loader;
		init();
	}

	public QUAD(Pos position, TextureLoader loader, String textureName) {
		this.position = position;
		rotation = (int) (Math.random() * 4);
		name = textureName;
		this.loader = loader;
		init();
	}

	public void init() {
		height = texture.getImageHeight() * scale;
		width = texture.getImageWidth() * scale;
	}
	// Getters § Setters ------------------------------------

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	// Relatiever setter
	public void setRelativeHeight(double height) {
		this.height += height;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getScale() {
		return scale;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setRelativeWidth(double width) {
		this.width += width;
	}

	@Override
	public double getX() {
		return position.getX();
	}

	public double getXC() {
		return position.getX() + 16;
	}

	@Override
	public void setX(double x) {
		position.setX(x);
	}

	public void setRelativeX(double x) {
		position.setX(position.getX() + x);
	}

	@Override
	public double getY() {
		return position.getY();
	}

	@Override
	public void setY(double y) {
		position.setY(y);
	}

	public void setRelativeY(double y) {
		position.setY(position.getY() + y);
	}

	public void setTCX(double x) {
		tc_x = x;
	}

	public void setTCY(double y) {
		tc_y = y;
	}

	public void transformXY(double x, double y) {
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
		setRelativeX(((Math.random() * 2) - 1) * 5);
		setRelativeY(((Math.random() * 2) - 1) * 5);
		draw();
		glColor3f(1f, 1f, 1f);
	}

	@Override
	public void draw() {
		texture = loader.getTexture(name);
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

			glTexCoord2f(0, (float) (texture.getHeight() * tc_y));
			glVertex2f(0, (float) height);

			glTexCoord2f((float) (texture.getWidth() * tc_x), (float) (texture.getHeight() * tc_y));
			glVertex2f((float) width, (float) height);

			glTexCoord2f((float) (texture.getWidth() * tc_x), 0);
			glVertex2f((float) width, 0);

		}
		glEnd();
		glColor3f(1f, 1f, 0.9f);
		// restore the model view matrix to prevent contamination
		glPopMatrix();
	}

}
