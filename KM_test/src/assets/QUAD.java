package assets;

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

import loader.TextureLoader;
import res.Pos;

public class QUAD extends gasset {
	float H; //Höhe
	float L; //Länge
	Pos pos; //Position
	float Cs = 1;
	int rot; //rotation

	float tc_x = 1; 
	float tc_y = 1;
	
	private Texture texture;
	
	public QUAD(float l, float h, float x, float y, float r){
		H = h;
		L = l;
		pos = new Pos(x, y);
		double ran = Math.random()*4;
		rot = (int) ran;
	}
	
	public QUAD(float l, float h, float x, float y, TextureLoader loader, String ref){
		H = h;
		L = l;
		pos = new Pos(x, y);
		double ran = Math.random()*4;
		rot = (int) ran;
		try {
			texture = loader.getTexture(ref);
		} catch (IOException e) {
			e.printStackTrace();
		      System.exit(-1);
		}
	}
	
	public QUAD( float x, float y, float cs,  TextureLoader loader, String ref){
		pos = new Pos(x, y);
		Cs = cs;
		double ran = Math.random()*4;
		rot = (int) ran;
		try {
			texture = loader.getTexture(ref);
			init();
		} catch (IOException e) {
			e.printStackTrace();
		    System.exit(-1);
		}
	}
	
	public QUAD( float x, float y,  TextureLoader loader, String ref){
		pos = new Pos(x, y);
		double ran = Math.random()*4;
		rot = (int) ran;
		try {
			texture = loader.getTexture(ref);
			init();
		} catch (IOException e) {
			e.printStackTrace();
		    System.exit(-1);
		}
	}
	
	public QUAD(float h, float b, float x, float y){
		H = h;
		L = b;
		pos.setxPos(x);
		pos.setyPos(y);
		double ran = Math.random()*4;
		rot = (int) ran;
	}
	
	public void init(){
		H = texture.getImageHeight()* Cs;
		L = texture.getImageWidth()* Cs;
	}
	//Getters § Setters ------------------------------------
	
	public float getH() {
		return H;
	}

	public void setH(float h) {
		H = h;
	}
	
	//Relatiever setter
	public void setrH(float h) {
		H += h;
	}
	
	public void setCS(float cs){
		Cs = cs;
	}
	
	public float getCS(){
		return Cs;
	}
	
	public float getL() {
		return L;
	}

	public void setL(float l) {
		L = l;
	}
	
	public void setrL(float l) {
		L += l;
	}
	
	@Override
	public float getX() {
		return (float)pos.getxPos();
	}

	@Override
	public void setX(float x) {
		pos.setxPos(x);
	}
	
	public void setrX(float x) {
		pos.setxPos(pos.getxPos() + x);
	}
	
	@Override
	public float getY() {
		return (float) pos.getyPos();
	}

	@Override
	public void setY(float y) {
		pos.setyPos(y);
	}
	
	public void setrY(float y) {
		pos.setyPos(pos.getyPos() + y);
	}
	
	public void setTCX(float x){
		tc_x = x;
	}
	
	public void setTCY(float y){
		tc_y = y;
	}
	
	public void transformXY(float x, float y){
		pos.setxPos(x);
		pos.setyPos(y);
	}
	
	public Pos getPos(){
		return pos;
	}
	
	public void setPos(Pos pos){
		this.pos = pos;
	}
	
	@Override
	public void draw2(){
		double x = ((Math.random()*2)-1) * 5;
		double y = ((Math.random()*2)-1) * 5;
		setrX((float)x);
		setrY((float)y);
		draw();
		glColor3f(1f, 1f, 1f);
	}

	@Override
	public void draw(){
		// store the current model matrix
        glPushMatrix();
        // bind to the appropriate texture for this sprite
        texture.bind();
        glMatrixMode(GL_TEXTURE);
        glLoadIdentity();
        glRotatef(90*rot, 0f, 0f, 1f);
        glMatrixMode(GL_MODELVIEW);
        // translate to the right location and prepare to draw
        glTranslatef((float)pos.getxPos(), (float)pos.getyPos(), 0);
        // draw a quad textured to match the sprite
        glBegin(GL_QUADS);{
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
 
            glTexCoord2f(0, -texture.getHeight()*tc_y);
            glVertex2f(0, H);
 
            glTexCoord2f(-texture.getWidth()*tc_x, -texture.getHeight()*tc_y);
            glVertex2f(L, H);
 
            glTexCoord2f(-texture.getWidth()*tc_x, 0);
            glVertex2f(L, 0);
            
        }
        glEnd();
        glColor3f(0.7f, 0.7f, 0.8f);
        // restore the model view matrix to prevent contamination
        glPopMatrix();
	}

	

}
