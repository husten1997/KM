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

import com.richard.knightmare.util.Pos;
import com.richard.knightmare.util.Texturloader;

public class RectangleGraphicalObject extends GraphicalObject {

	protected int width, height, rotation = 2, id;
	protected boolean wasser = false;
	public int getWidth() {
		return width;
	}

	public void register(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}

	public boolean isWaterproof() {
		return wasser;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	protected String textureName, material;
	protected boolean randomRotation, stratched = true;
	protected Texture texture;
	protected double widthCount = 1, heightCount = 1;

	public RectangleGraphicalObject(Pos position, int width, int height, boolean randomRotation) {
		super(position, MeshType.GROUND);
		this.width = width;
		this.height = height;
		this.randomRotation = randomRotation;
		if (randomRotation) {
			rotation = (int) (Math.random() * 4);
		}
	}

	public RectangleGraphicalObject(Pos position, int width, int height, String textureName, boolean randomRotation) {
		super(position, MeshType.GROUND);
		this.width = width;
		this.height = height;
		this.textureName = textureName;
		this.randomRotation = randomRotation;
		if (randomRotation) {
			rotation = (int) (Math.random() * 4);
		}
	}

	public RectangleGraphicalObject(Pos position, int width, int height, String textureName, boolean randomRotation, String material) {
		super(position, MeshType.GROUND);
		this.width = width;
		this.height = height;
		this.textureName = textureName;
		this.randomRotation = randomRotation;
		if (randomRotation) {
			rotation = (int) (Math.random() * 4);
		}
		this.material = material;
	}
	
	public void initRender(){
		texture = Texturloader.getTexture(textureName);
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
		if (stratched) {
			glBegin(GL_QUADS);
			{
				glTexCoord2f((float) widthCount, 0);
				glVertex2f(0, 0);

				glTexCoord2f((float) widthCount, (float) heightCount);
				glVertex2f(0, (float) height);

				glTexCoord2f(0, (float) heightCount);
				glVertex2f((float) width, (float) height);

				glTexCoord2f(0, 0);
				glVertex2f((float) width, 0);

			}
			glEnd();
		} else {
			glBegin(GL_QUADS);
			{
				glTexCoord2f((float) width / texture.getImageWidth(), 0);
				glVertex2f(0, 0);

				glTexCoord2f((float) width / texture.getImageWidth(), (float) height / texture.getImageHeight());
				glVertex2f(0, (float) height);

				glTexCoord2f(0, (float) height / texture.getImageHeight());
				glVertex2f((float) width, (float) height);

				glTexCoord2f(0, 0);
				glVertex2f((float) width, 0);

			}
			glEnd();
		}

		glColor3f(1f, 1f, 0.9f);
		// restore the model view matrix to prevent contamination
		glPopMatrix();
	}

	public void moveX(double x){
		position.setX(position.getX()+x);
	}
	
	public void moveY(double y){
		position.setY(position.getY()+y);
	}
	
	public void setHeightCount(double heightCount) {
		this.heightCount = heightCount;
	}

	public double getHeightCount() {
		return heightCount;
	}

	public void setWidthCount(double widthCount) {
		this.widthCount = widthCount;
	}

	public double getWidthCount() {
		return widthCount;
	}

	public void setStrached(boolean strached) {
		this.stratched = strached;
	}

	public boolean getStrached() {
		return stratched;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMaterial() {
		return material;
	}

	public void setTextureName(String name) {
		textureName = name;
	}

	public String getTextureName() {
		return textureName;
	}

}
