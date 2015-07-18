package com.richard.knightmare.visual;

import org.lwjgl.input.Mouse;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Overlay {

	public static void render(int index) {
		if (index != -1) {
			RectangleGraphicalObject object = new RectangleGraphicalObject(new Pos(((int)((Mouse.getX()+Knightmare.CameraX)/32))*32, ((int)((Mouse.getY()+Knightmare.CameraY)/32))*32), Size.getSize(index).width, Size.getSize(index).height,"Overlay.png", false);
			object.initRender();
			object.draw();
		}
	}
}
