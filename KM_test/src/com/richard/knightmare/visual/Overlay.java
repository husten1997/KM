package com.richard.knightmare.visual;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.matze.knightmare.meshes.Bauen;
import com.matze.knightmare.meshes.Building;
import com.richard.knightmare.util.Pos;

public class Overlay {

	private static ArrayList<RectangleGraphicalObject> list = new ArrayList<>();
	private static int mx, my, ind;

	public static void render(int index, RectangleGraphicalObject[][] world) {
		if (index != -1) {
			if (index != ind || mx != Mouse.getX() || my != Mouse.getY()) {
				ind = index;
				mx = Mouse.getX();
				my = Mouse.getY();
				list.clear();
				for (int x = (int) (mx + Knightmare.CameraX) / 32; x < (int) (mx + Knightmare.CameraX + Size.getSize(index).getWidth()) / 32; x++) {
					for (int y = (int) (my + Knightmare.CameraY) / 32; y < (int) (my + Knightmare.CameraY + Size.getSize(index).getHeight()) / 32; y++) {
						if (isObstractedForBuilding(x, y, Bauen.getBuildingforID(index, new Pos(x * 32, y * 32), Bauen.getMutterNatur()), world)) {
							RectangleGraphicalObject obj = new RectangleGraphicalObject(new Pos(x * 32, y * 32), 32, 32, "OverlayRed.png", false);
							obj.initRender();
							list.add(obj);
						} else {
							RectangleGraphicalObject obj = new RectangleGraphicalObject(new Pos(x * 32, y * 32), 32, 32, "OverlayGreen.png", false);
							obj.initRender();
							list.add(obj);
						}
					}
				}
			}
			for (RectangleGraphicalObject object : list) {
				object.draw();
			}
		}
	}

	private static boolean isObstractedForBuilding(int x, int y, Building building, RectangleGraphicalObject[][] world) {
		if (world[x][y] != null) {
			return true;
		}
		for (String muss : building.getMuss()) {
			return !muss.equals((Knightmare.terrain.getMeterial(x, y) == null ? StringConstants.Material_t.WATER : Knightmare.terrain.getMeterial(x, y)));
		}
		for (String darfNicht : building.getnichtErlaubt()) {
			if (darfNicht.equals((Knightmare.terrain.getMeterial(x, y) == null ? StringConstants.Material_t.WATER : Knightmare.terrain.getMeterial(x, y)))) {
				return true;
			}
		}
		return false;
	}
}
