package com.richard.knightmare.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.matze.knightmare.meshes.Soldat;

public class EntityHandler {

	private RectangleGraphicalObject[][] world;
	private ArrayList<RectangleGraphicalObject> entities = new ArrayList<>();
	private int id = 1, ticksSinceLastRetry;
	private HashMap<Soldat, SingleManPathfinding> finding = new HashMap<>();
	private HashMap<Soldat, Pos> actualDeastination = new HashMap<>(), replacedDestination = new HashMap<>();
	private HashMap<Soldat, Integer> triesOnActual = new HashMap<>(), triesOnReplaced = new HashMap<>();
	private HashMap<Soldat, RectangleGraphicalObject> chasing = new HashMap<>();

	public EntityHandler(int width, int height) {
		world = new RectangleGraphicalObject[width][height];
	}

	private void register(RectangleGraphicalObject object) {
		if (object.getID() == 0) {
			object.register(id);
			id++;
		}
	}

	public void draw() {
		for (RectangleGraphicalObject entity : entities) {
			entity.draw();
		}
	}

	public boolean place(RectangleGraphicalObject object) {
		register(object);
		int w = object.getWidth() / 32;
		int h = object.getHeight() / 32;
		int startW = (int) (object.getPosition().getX() / 32);
		int startH = (int) (object.getPosition().getY() / 32);

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (isObstractedFor(startW + i, startH + j, object)) {
					return false;
				}
			}
		}
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				world[startW + i][startH + j] = object;
			}
		}
		object.initRender();
		entities.add(object);
		return true;
	}

	public void processRightClick(double x, double y, ArrayList<RectangleGraphicalObject> selection) {
		int xPos = (int) x / 32;
		int yPos = (int) y / 32;

		if (selection.size() == 1) {
			if (world[xPos][yPos] != null) {
				if (world[xPos][yPos].getTeam() != selection.get(0).getTeam()) {
					chasing.put((Soldat) selection.get(0), world[xPos][yPos]);
					pathfindTo(x, y, selection.get(0));
				} else {
					pathfindTo(x, y, selection.get(0));
				}
			} else {
				pathfindTo(x, y, selection.get(0));
			}
		} else {
			// TODO future work
		}
	}

	public void tick() {
		// TODO
	}

	public void pathfindTo(double x, double y, RectangleGraphicalObject object) {
		finding.remove(object);
		SingleManPathfinding path = new SingleManPathfinding((Soldat) object, new Pos(x, y));
		com.richard.knightmare.util.SingleManPathfinding.Pos alternative = path.pathfind();
		if (alternative == null) {
			finding.put((Soldat) object, path);
		} else {
			actualDeastination.put((Soldat) object, new Pos(x, y));
			triesOnActual.put((Soldat) object, 1);
			replacedDestination.put((Soldat) object, new Pos(alternative.x * 32 + 16, alternative.y * 32 + 16));
			triesOnReplaced.put((Soldat) object, 0);
		}
	}

	public void remove(RectangleGraphicalObject object) {
		int w = object.getWidth() / 32;
		int h = object.getHeight() / 32;
		int startW = (int) (object.getPosition().getX() / 32);
		int startH = (int) (object.getPosition().getY() / 32);

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				world[startW + i][startH + j] = null;
			}
		}
		entities.remove(object);
	}

	private boolean isObstractedFor(int x, int y, RectangleGraphicalObject soldat) {
		if (soldat.isWaterproof()) {
			if (Knightmare.terrain.getMeterial(x, y) == null) {
				if (world[x][y] != null) {
					return world[x][y].getID() != soldat.getID();
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			if (Knightmare.terrain.getMeterial(x, y) == null) {
				return true;
			} else {
				if (world[x][y] != null) {
					return world[x][y].getID() != soldat.getID();
				} else {
					return false;
				}
			}
		}
	}

}
