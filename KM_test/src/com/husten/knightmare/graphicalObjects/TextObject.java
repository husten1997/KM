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
import com.richard.knightmare.util.Texturloader;

public class TextObject extends RectangleGraphicalObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8261856611373028118L;
	private Color color;
	private Font font;

	public TextObject(Pos position, int width, int height, String text, Color color, Font font) {
		super(position, width, height, text, false);
		this.color = color;
		this.font = font;
	}

	public void initRender() {
		texture = Texturloader.createStringTexture(textureName, width, height, color, font);
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
		// translate to the right location and prepare to draw
		glTranslatef((float) position.getX(), (float) position.getY(), 0);
		// draw a quad textured to match the sprite
		if (stretched) {
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

}
