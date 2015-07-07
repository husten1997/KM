package com.husten.knightmare.graphicalObjects;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.worldGen.WorldGenerator;
import com.matze.knightmare.meshes.Spieler;
import com.richard.knightmare.util.Pos;
import com.richard.knightmare.util.Texturloader;

public class RectangleGraphicalObject extends GraphicalObject {

	protected int width = 0, height = 0, t_rotation = 0, m_rotation = 0, id, xz, yz, lastM;
	protected double k = (32*Math.sqrt(2)-32)/2;
	protected boolean wasser = false;
	protected Spieler s;

	public int getWidth() {
		return width;
	}

	public void register(int id) {
		this.id = id;
	}

	public int getID() {
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
	protected boolean randomRotation, stretched = true;
	protected Texture texture;
	protected double widthCount = 1, heightCount = 1;
	protected final Color fColor = new Color(255, 255, 255);

	public RectangleGraphicalObject(Pos position, int width, int height, boolean randomRotation) {
		super(position, MeshType.GROUND);
		this.width = width;
		this.height = height;
		this.randomRotation = randomRotation;

	}

	public RectangleGraphicalObject(Pos position, int width, int height, String textureName, boolean randomRotation) {
		super(position, MeshType.GROUND);
		this.width = width;
		this.height = height;
		this.textureName = textureName;
		this.randomRotation = randomRotation;
	}

	public RectangleGraphicalObject(Pos position, int width, int height, String textureName, boolean randomRotation, String material) {
		super(position, MeshType.GROUND);
		this.width = width;
		this.height = height;
		this.textureName = textureName;
		this.randomRotation = randomRotation;
		this.material = material;
	}

	public RectangleGraphicalObject(Pos position, String textureName, boolean randomRotation, String material) {
		super(position, MeshType.GROUND);
		this.textureName = textureName;
		this.randomRotation = randomRotation;
		this.material = material;
	}

	public RectangleGraphicalObject(Pos position, double widthCount, double heightCount, String textureName, boolean randomRotation, String material) {
		super(position, MeshType.GROUND);
		this.widthCount = widthCount;
		this.heightCount = heightCount;
		this.textureName = textureName;
		this.randomRotation = randomRotation;
		this.material = material;
	}

	public void initRender() {
		texture = Texturloader.getTexture(textureName);
		if (width == 0 && height == 0) {
			initTexture();
		}
		init();

	}

	public void init() {
		if (stretched) {
			widthCount = 1;
			heightCount = 1;
		} else {
			widthCount = width / texture.getImageWidth();
			heightCount = height / texture.getImageHeight();
		}
		if (randomRotation) {
			t_rotation = WorldGenerator.prand.nextInt(4) + 1;
		}
	}

	public void initTexture() {
		height = texture.getImageHeight();
		width = texture.getImageWidth();
	}

	@Override
	public void draw() {
		// store the current model matrix
		glPushMatrix();
		// bind to the appropriate texture for this sprite
		
		texture.bind();
		glMatrixMode(GL_TEXTURE);
		glLoadIdentity();

		glRotatef(90 * t_rotation, 0f, 0f, 1f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		// translate to the right location and prepare to draw
		
		glTranslatef((float) position.getX()+xz, (float) position.getY()+yz, 0);
		glRotatef(45*m_rotation, 0f, 0f, 1f);
		glColor3f((float) (fColor.getRed() / 255 * Knightmare.breightness), (float) (fColor.getGreen() / 255 * Knightmare.breightness), (float) (fColor.getBlue() / 255 * Knightmare.breightness));
		// draw a quad textured to match the sprite
		
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
		
		
		
		// restore the model view matrix to prevent contamination
		
		glPopMatrix();

	}

	public void moveX(double x) {
		position.setX(position.getX()+x);
	}

	public void moveY(double y) {
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
		this.stretched = strached;
	}

	public boolean getStrached() {
		return stretched;
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

	public Color getColor() {
		return fColor;
	}

	public int getMrotation(){
		return m_rotation;
	}
	
	public void setMRotation(int m_rotation){
		if(m_rotation==lastM){
			this.m_rotation = m_rotation;
			System.out.println(m_rotation);
			switch (m_rotation) {
			case 0://Oben
				xz = 0;
				yz = 0;
				break;
			case 1://Links-Oben
				xz = 32-(int) k;
				yz = -16;
				break;
			case 2://Links!
				xz = 32;
				yz = 0;
				break;
			case 3://Links-Unten
				xz = (int) k+32;
				yz = 16;
				break;
			case 4://Unten
				xz = 32;
				yz = 32;
				break;
			case 5://Rechts-Unten
				xz = (int)k;
				yz = 48;
				break;
			case 6://Rechts!
				xz = 0;
				yz = 32;
				break;
			case 7://Rechts-Oben
				xz = -16;
				yz = (int)-k+16;
				break;
			}
		}else{
			lastM = m_rotation;
		}
	}

	public void setSpieler(Spieler s){
		this.s = s;
	}
	
	public Spieler getSpieler(){
		return s;
	}
	
	
	
	
	

}
