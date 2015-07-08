package com.richard.knightmare.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Soldat;
import com.matze.knightmare.meshes.Spieler;

public class EntityHandler {

	public static RectangleGraphicalObject[][] world;
	private ArrayList<RectangleGraphicalObject> entities = new ArrayList<>();
	private int id = 1, ticksSinceLastRetry;
	private HashMap<Soldat, SingleManPathfinding> finding = new HashMap<>();
	private HashMap<Soldat, Pos> actualDeastination = new HashMap<>(), replacedDestination = new HashMap<>();
	private HashMap<Soldat, Integer> triesOnActual = new HashMap<>(), triesOnReplaced = new HashMap<>();
	private HashMap<Soldat, RectangleGraphicalObject> chasing = new HashMap<>();
	private ArrayList<RectangleGraphicalObject> selection = new ArrayList<>();
	private Spieler[] spieler;
	private boolean processing = false, ticking = false;

	public EntityHandler(int width, int height, Spieler[] spieler) {
		this.spieler = spieler;
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

	public ArrayList<RectangleGraphicalObject> getSelection() {
		return selection;
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
		if (object instanceof Building) {
			if (((Building) object).getIndex() == 2) {
				for (Spieler hansl : spieler) {
					if (hansl.equals(object.getSpieler())) {
						hansl.addLager(object);
					}
				}
			}
			if (((Building) object).getIndex() == 12) {
				for (Spieler hansl : spieler) {
					if (hansl.equals(object.getSpieler())) {
						hansl.addSchatzkammer(object);
					}
				}
			}
			if (((Building) object).getIndex() == 13) {
				for (Spieler hansl : spieler) {
					if (hansl.equals(object.getSpieler())) {
						hansl.addWaffenkammer(object);
					}
				}
			}
		}
		return true;
	}

	public void search(double x1, double y1, double x2, double y2) {
		if (x1 == x2 && y1 == y2) {
			search(x1, y1);
		} else {
			selection.clear();
			double Px1;
			double Px2;

			double Py1;
			double Py2;

			if (x1 < x2) {
				Px1 = x2;
				Px2 = x1;
			} else {
				Px1 = x1;
				Px2 = x2;
			}

			if (y1 < y2) {
				Py1 = y2;
				Py2 = y1;
			} else {
				Py1 = y1;
				Py2 = y2;
			}

			try {
				for (int i = 0; i < entities.size(); i++) {
					if (entities.get(i).getPosition().getX() <= Px1 && entities.get(i).getPosition().getX() >= Px2 && entities.get(i).getPosition().getY() <= Py1
							&& entities.get(i).getPosition().getY() >= Py2) {

						if (entities.get(i).getType().equals(StringConstants.MeshType.EINHEIT)) {
							selection.add((RectangleGraphicalObject) entities.get(i));
						}
					}
				}
			} catch (Exception e) {

			}
		}

	}

	public void search(double x, double y) {
		selection.clear();

		try {
			for (int i = 0; i < entities.size(); i++) {
				if (entities.get(i).getPosition().getX() <= x && entities.get(i).getPosition().getX() >= x - 64 && entities.get(i).getPosition().getY() <= y
						&& entities.get(i).getPosition().getY() >= y - 64) {
					selection.add(entities.get(i));
				}
			}
		} catch (Exception e) {

		}
	}

	public void processRightClick(double x, double y) {
		processing = true;
		while (ticking) {
			// Wait
		}
		int xPos = (int) x / 32;
		int yPos = (int) y / 32;

		if (selection.size() == 1) {
			if (world[xPos][yPos] != null) {
				if (world[xPos][yPos]
						.getSpieler()
						.getTeam() != 
						selection
						.get(0)
						.getSpieler()
						.getTeam()) {
					chasing.put((Soldat) selection.get(0), world[xPos][yPos]);
					pathfindTo(x, y, selection.get(0));
				} else {
					pathfindTo(x, y, selection.get(0));
				}
			} else {
				pathfindTo(x, y, selection.get(0));
			}
		} else {
			for (RectangleGraphicalObject soldat : selection) {
				if (world[xPos][yPos] != null) {
					if (world[xPos][yPos].getSpieler().getTeam() != soldat.getSpieler().getTeam()) {
						chasing.put((Soldat) soldat, world[xPos][yPos]);
						pathfindTo(x, y, soldat);
					} else {
						pathfindTo(x, y, soldat);
					}
				} else {
					pathfindTo(x, y, soldat);
				}
			}
		}
		processing = false;
	}

	public void tick() {
		if (processing) {
			return;
		}
		ticking = true;
		if (ticksSinceLastRetry > 20) {
			ArrayList<Soldat> toRemoveActual = new ArrayList<>();
			for (Entry<Soldat, Pos> entry : actualDeastination.entrySet()) {
				if (triesOnActual.get(entry.getKey()) > 5) {
					if (triesOnReplaced.get(entry.getKey()) > 5) {
						triesOnReplaced.remove(entry.getKey());
						replacedDestination.remove(entry.getKey());
						triesOnActual.remove(entry.getKey());
						// actualDeastination.remove(entry.getKey());
						toRemoveActual.add(entry.getKey());
						chasing.remove(entry.getKey());
					} else {
						SingleManPathfinding path = new SingleManPathfinding(entry.getKey(), replacedDestination.get(entry.getKey()));
						com.richard.knightmare.util.SingleManPathfinding.Pos alternative = path.pathfind();
						if (alternative == null) {
							finding.put(entry.getKey(), path);
							triesOnReplaced.remove(entry.getKey());
							replacedDestination.remove(entry.getKey());
							triesOnActual.remove(entry.getKey());
							// actualDeastination.remove(entry.getKey());
							toRemoveActual.add(entry.getKey());
						} else {
							replacedDestination.put(entry.getKey(), new Pos(alternative.x * 32 + 16, alternative.y * 32 + 16));
							triesOnReplaced.put(entry.getKey(), triesOnReplaced.get(entry.getKey()) + 1);
						}
					}
				} else {
					SingleManPathfinding path = new SingleManPathfinding(entry.getKey(), actualDeastination.get(entry.getKey()));
					com.richard.knightmare.util.SingleManPathfinding.Pos alternative = path.pathfind();
					if (alternative == null) {
						finding.put(entry.getKey(), path);
						triesOnReplaced.remove(entry.getKey());
						replacedDestination.remove(entry.getKey());
						triesOnActual.remove(entry.getKey());
						// actualDeastination.remove(entry.getKey());
						toRemoveActual.add(entry.getKey());
					} else {
						triesOnActual.put(entry.getKey(), triesOnActual.get(entry.getKey()) + 1);
					}
				}
			}
			for (Soldat soldat : toRemoveActual) {
				actualDeastination.remove(soldat);
			}
			ticksSinceLastRetry = 0;
		}
		ticksSinceLastRetry++;
		ArrayList<Soldat> toRemove = new ArrayList<>();
		HashMap<Soldat, SingleManPathfinding> toPut = new HashMap<>();
		for (Entry<Soldat, SingleManPathfinding> entry : finding.entrySet()) {
			if (!entry.getValue().move()) {
				if (entry.getValue().getFinished()) {
					// finding.remove(entry.getKey());
					toRemove.add(entry.getKey());
					if (chasing.containsKey(entry.getKey())) {
						chasing.remove(entry.getKey());
						// TODO is already near?
					}
				} else {
					finding.get(entry.getKey()).stop();
					// pathfindTo(entry.getValue().getZiel().getX()*32+16,
					// entry.getValue().getZiel().getY()*32+16, entry.getKey());
					double x = entry.getValue().getZiel().getX() * 32 + 16, y = entry.getValue().getZiel().getY() * 32 + 16;
					SingleManPathfinding path = new SingleManPathfinding(entry.getKey(), new Pos(x, y));
					com.richard.knightmare.util.SingleManPathfinding.Pos alternative = path.pathfind();
					if (alternative == null) {
						toPut.put(entry.getKey(), path);
						// finding.put(entry.getKey(), path);
					} else {
						// finding.remove(entry.getKey());
						toRemove.add(entry.getKey());
						actualDeastination.put(entry.getKey(), new Pos(x, y));
						triesOnActual.put(entry.getKey(), 1);
						replacedDestination.put(entry.getKey(), new Pos(alternative.x * 32 + 16, alternative.y * 32 + 16));
						triesOnReplaced.put(entry.getKey(), 0);
					}
				}
			}
		}
		for (Soldat soldat : toRemove) {
			finding.remove(soldat);
		}
		for (Entry<Soldat, SingleManPathfinding> entry : toPut.entrySet()) {
			finding.put(entry.getKey(), entry.getValue());
		}
		ticking = false;
	}

	public void pathfindTo(double x, double y, RectangleGraphicalObject object) {
		if (finding.containsKey(object)) {
			finding.get(object).stop();
		}
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
		entities.remove(object);
		int w = object.getWidth() / 32;
		int h = object.getHeight() / 32;
		int startW = (int) (object.getPosition().getX() / 32);
		int startH = (int) (object.getPosition().getY() / 32);

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				world[startW + i][startH + j] = null;
			}
		}
		if (object instanceof Building) {
			((Building) object).getTimer().cancel();
			if (((Building) object).getIndex() == 4) {
				((Building) object).getTimer2().cancel();
			}
			if (((Building) object).getIndex() == 2) {
				for (Spieler hansl : spieler) {
					if (hansl.getIndex() == object.getSpieler().getIndex()) {
						hansl.removeLager(object);
					}
				}
			}
			if (((Building) object).getIndex() == 12) {
				for (Spieler hansl : spieler) {
					if (hansl.getIndex() == object.getSpieler().getIndex()) {
						hansl.removeSchatzkammer(object);
					}
				}
			}
			if (((Building) object).getIndex() == 13) {
				for (Spieler hansl : spieler) {
					if (hansl.getIndex() == object.getSpieler().getIndex()) {
						hansl.removeWaffenkammer(object);
					}
				}
			}
		}
	}

	public RectangleGraphicalObject remove(int x, int y) {
		RectangleGraphicalObject object = world[x][y];
		remove(object);
		return object;
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
