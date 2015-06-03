package com.richard.knightmare.util;

import java.util.HashMap;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.matze.knightmare.meshes.Soldat;

public class Pathhandler {

	private int i = 1;
	private HashMap<Integer, Pathfinding> pathfinding = new HashMap<>(), toDo = new HashMap<>();
	private HashMap<Integer, com.richard.knightmare.util.Pos> toChange = new HashMap<>();
	private HashMap<Integer, Integer> maxTrys = new HashMap<>(), trys = new HashMap<>();
	public static RectangleGraphicalObject[][] world;

	public Pathhandler() {

	}

	private void register(RectangleGraphicalObject s) {
		if (s.getID() == 0) {
			s.register(i);
			i++;
		}
	}

	public RectangleGraphicalObject abreißen(int x, int y) {
		RectangleGraphicalObject h = world[x][y];
		if (h != null) {
			// System.out.println("del request"+h.getID());
			if (!isCurrentlyPathfinding(h.getID())) {
				// System.out.println("del"+h.getID());
				if (h.getWidth() > 32) {
					world[(int) (h.getPosition().getX() / 32)][(int) (h.getPosition().getY() / 32)] = null;
					world[(int) (h.getPosition().getX() / 32) + 1][(int) (h.getPosition().getY() / 32)] = null;
					return h;
				} else {
					world[x][y] = null;
					return h;
				}
			}
		}
		return null;
	}

	public boolean place(RectangleGraphicalObject toPlace) {
		if (toPlace.getWidth() > 32) {
			if (!isObstracted((int) (toPlace.getPosition().getX() / 32), (int) (toPlace.getPosition().getY() / 32), toPlace)
					&& !isObstracted((int) (toPlace.getPosition().getX() / 32) + 1, (int) (toPlace.getPosition().getY() / 32), toPlace)) {
				register(toPlace);
				world[(int) (toPlace.getPosition().getX() / 32)][(int) (toPlace.getPosition().getY() / 32)] = toPlace;
				world[(int) (toPlace.getPosition().getX() / 32) + 1][(int) (toPlace.getPosition().getY() / 32)] = toPlace;
				return true;
			}

		} else {
			if (!isObstracted((int) (toPlace.getPosition().getX() / 32), (int) (toPlace.getPosition().getY() / 32), toPlace)) {
				register(toPlace);
				world[(int) (toPlace.getPosition().getX() / 32)][(int) (toPlace.getPosition().getY() / 32)] = toPlace;
				return true;
			}
		}
		return false;
	}

	private boolean isObstracted(int x, int y, RectangleGraphicalObject soldat) {
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

	public boolean isCurrentlyPathfinding(int key) {
		if (pathfinding.get(key) != null) {
			return true;
		}
		if (toChange.get(key) != null) {
			return true;
		}
		if (toDo.get(key) != null) {
			return true;
		}
		return false;
	}

	public void handle(Soldat s, com.richard.knightmare.util.Pos ziel, int trys) {
		register(s);
		maxTrys.put(s.getID(), trys);
		this.trys.put(s.getID(), trys);
		if (pathfinding.containsKey(s.getID())) {
			if (!((int) pathfinding.get(s.getID()).getZiel().getX() == (int) (ziel.getX() / 32)
					&& (int) pathfinding.get(s.getID()).getZiel().getY() == (int) (ziel.getY() / 32))) {
				pathfinding.get(s.getID()).setContinuing();
				toChange.put(s.getID(), ziel);
			}
		} else {
			Pathfinding p = new Pathfinding(s, ziel);
			com.richard.knightmare.util.Pathfinding.Pos pos = p.pathfind();
			// System.out.println("Moving to " + pos);
			if (pos == null) {
				pathfinding.put(s.getID(), p);
			} else {
				this.trys.put(s.getID(), this.trys.get(s.getID()) - 1);
				toDo.put(s.getID(), p);
			}
		}
	}

	public void move() {
		Object[] keysToDo = toDo.keySet().toArray();
		for (int i = 0; i < keysToDo.length; i++) {
			Pathfinding p = toDo.get(keysToDo[i]);
			Soldat s = p.getSoldat();
			com.richard.knightmare.util.Pathfinding.Pos pos = p.pathfind();
			if (pos == null) {
				pathfinding.put(s.getID(), p);
				toDo.remove(keysToDo[i]);
				trys.put(s.getID(), maxTrys.get(s.getID()));
			} else {
				if (trys.get(s.getID()) == 0) {
					toDo.put((Integer) keysToDo[i], new Pathfinding(s, new Pos(pos.x * 32 + 16, pos.y * 32 + 16)));
				}

				if (trys.get(s.getID()) <= -maxTrys.get(s.getID())) {
					toDo.remove(s.getID());
				}
			}
			trys.put(s.getID(), trys.get(s.getID()) - 1);
		}

		Object[] keys = pathfinding.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			if (!pathfinding.get(keys[i]).move()) {
				if (pathfinding.get(keys[i]).getFinished()) {
					pathfinding.remove(keys[i]);
				} else {
					if (!pathfinding.get(keys[i]).getContinuing()) {
						Pathfinding p = new Pathfinding(pathfinding.get(keys[i]).getSoldat(), toChange.get(keys[i]));
						com.richard.knightmare.util.Pathfinding.Pos pos = p.pathfind();
						if (pos == null) {
							pathfinding.put((Integer) keys[i], p);
						} else {
							this.trys.put((Integer) keys[i], trys.get(keys[i]) - 1);
							toDo.put((Integer) keys[i], p);
							pathfinding.remove(keys[i]);
						}
					} else {
						Pathfinding p = pathfinding.get(keys[i]);

						com.richard.knightmare.util.Pathfinding.Pos pos = pathfinding.get(keys[i]).pathfind();
						if (pos != null) {
							this.trys.put((Integer) keys[i], this.trys.get(keys[i]) - 1);
							toDo.put((Integer) keys[i], p);
							pathfinding.remove(keys[i]);
						}
					}
				}
			}
		}

	}
}
