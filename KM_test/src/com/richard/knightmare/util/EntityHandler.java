package com.richard.knightmare.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.matze.knightmare.meshes.Battle;
import com.matze.knightmare.meshes.Bauen;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Soldat;
import com.matze.knightmare.meshes.Spieler;
import com.richard.knightmare.sound.SoundPlayer;

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
	private SoundPlayer battleplayer = new SoundPlayer("Swordclash.WAV");
	private Timer battletimer;

	public EntityHandler(int width, int height, Spieler[] spieler) {
		this.spieler = spieler;
		world = new RectangleGraphicalObject[width][height];
		battleplayer.setVolume(Float.parseFloat(Loader.getCfgValue("Volume")));

		battletimer = new Timer(true);
		battletimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				for (int x = 0; x < world.length; x++) {
					for (int y = 0; y < world[x].length; y++) {
						if (getOn(x, y) != null) {
							if (getOn(x, y) instanceof Soldat) {
								if (kannK�mpfen((Soldat) getOn(x, y), getOn(x - 1, y))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x - 1, y), 0);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK�mpfen((Soldat) getOn(x, y), getOn(x, y - 1))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x, y - 1), 0);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK�mpfen((Soldat) getOn(x, y), getOn(x + 1, y))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x + 1, y), 0);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK�mpfen((Soldat) getOn(x, y), getOn(x, y + 1))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x, y + 1), 0);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								}
							}
						}
					}
				}
			}
		}, 0, 1000);
	}
	
	public void shotdown(){
		battleplayer.stop();
		battletimer.cancel();
	}

	private boolean kannK�mpfen(Soldat s1, RectangleGraphicalObject s2) {
		if (s2 instanceof Soldat) {
			return s1.getSpieler().getTeam() != ((Soldat) s2).getSpieler().getTeam();
		}
		return false;
	}

	private void register(RectangleGraphicalObject object) {
		if (object.getID() == 0) {
			object.register(id);
			id++;
		}
	}

	public RectangleGraphicalObject getOn(int x, int y) {
		if (x > 0 && x < world.length && y > 0 && y < world.length) {
			return world[x][y];
		}
		return null;
	}

	public void draw() {
		for (RectangleGraphicalObject entity : entities) {
			entity.draw();
		}
	}

	public void draw(int x, int y, int width, int height) {
		for (int i = x; i <= Math.min(x + width, world.length - 1); i++) {
			for (int j = y; j <= Math.min(y + height, world[x].length - 1); j++) {
				if (world[i][j] != null) {
					world[i][j].draw();
				}
			}
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
				if (object instanceof Soldat) {
					if (isObstractedFor(startW + i, startH + j, object)) {
						return false;
					}
				} else {
					if (isObstractedForBuilding(startW + i, startH + j, (Building) object)) {
						return false;
					}
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
			if (((Building) object).getIndex() == 14) {
				for (Spieler hansl : spieler) {
					if (hansl.equals(object.getSpieler())) {
						hansl.addKornspeicher(object);
					}
				}
			}
			if (((Building) object).getIndex() == 15) {
				for (Spieler hansl : spieler) {
					if (hansl.equals(object.getSpieler())) {
						hansl.addMarktplatz(object);
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

						if (entities.get(i) instanceof Soldat) {
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
					if (entities.get(i) instanceof Soldat) {
						selection.add(entities.get(i));
					}
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
				if (world[xPos][yPos].getSpieler().getTeam() != selection.get(0).getSpieler().getTeam()) {
					chasing.put((Soldat) selection.get(0), world[xPos][yPos]);
					pathfindTo(x, y, selection.get(0));
				} else {
					pathfindTo(x, y, selection.get(0));
				}
			} else {
				pathfindTo(x, y, selection.get(0));
			}
		} else if (selection.size() > 1) {
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

	public boolean remove(RectangleGraphicalObject object, int abrei�enderSpieler) {
		int w = object.getWidth() / 32;
		int h = object.getHeight() / 32;
		int startW = (int) (object.getPosition().getX() / 32);
		int startH = (int) (object.getPosition().getY() / 32);
		if (world[startW][startH].getSpieler().equals(spieler[abrei�enderSpieler]) || world[startW][startH].getSpieler().equals(Bauen.getMutterNatur())) {
			entities.remove(object);

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
				if (((Building) object).getIndex() == 14) {
					for (Spieler hansl : spieler) {
						if (hansl.getIndex() == object.getSpieler().getIndex()) {
							hansl.removeKornspeicher(object);
						}
					}
				}
				if (((Building) object).getIndex() == 15) {
					for (Spieler hansl : spieler) {
						if (hansl.getIndex() == object.getSpieler().getIndex()) {
							hansl.removeMarktplatz(object);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	public RectangleGraphicalObject remove(int x, int y, int abrei�enderSpieler) {
		RectangleGraphicalObject object = world[x][y];
		if (object != null) {
			if (!remove(object, abrei�enderSpieler)) {
				return null;
			}
		}
		return object;
	}

	public boolean die(RectangleGraphicalObject object) {
		int w = object.getWidth() / 32;
		int h = object.getHeight() / 32;
		int startW = (int) (object.getPosition().getX() / 32);
		int startH = (int) (object.getPosition().getY() / 32);
		entities.remove(object);

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
			if (((Building) object).getIndex() == 14) {
				for (Spieler hansl : spieler) {
					if (hansl.getIndex() == object.getSpieler().getIndex()) {
						hansl.removeKornspeicher(object);
					}
				}
			}
			if (((Building) object).getIndex() == 15) {
				for (Spieler hansl : spieler) {
					if (hansl.getIndex() == object.getSpieler().getIndex()) {
						hansl.removeMarktplatz(object);
					}
				}
			}
		}
		return true;
	}

	public RectangleGraphicalObject die(int x, int y) {
		RectangleGraphicalObject object = world[x][y];
		if (object != null) {
			if (!die(object)) {
				return null;
			}
		}
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

	private boolean isObstractedForBuilding(int x, int y, Building building) {
		if (world[x][y] != null) {
			return true;
		}
		for (String muss : building.getMuss()) {
			return !Knightmare.terrain.getMeterial(x, y).equals(muss);
		}
		for (String darfNicht : building.getnichtErlaubt()) {
			if (Knightmare.terrain.getMeterial(x, y).equals(darfNicht)) {
				return true;
			}
		}
		return false;
	}

	public Building suchBaum(int x, int y, int radius) {
		for (int i = Math.max(x - radius, 0); i < Math.min(x + radius, world.length); i++) {
			for (int j = Math.max(y - radius, 0); j < Math.max(y + radius, world[x].length); j++) {
				if (world[i][j] instanceof Building) {
					if (((Building) world[i][j]).getIndex() == 11) {
						return (Building) world[i][j];
					}

				}
			}
		}
		return null;
	}

	public Pos suchFrei(int x, int y, int radius) {
		for (int i = Math.max(x - radius, 0); i < Math.min(x + radius, world.length); i++) {
			for (int j = Math.max(y - radius, 0); j < Math.max(y + radius, world[x].length); j++) {
				if (world[i][j] == null) {
					return new Pos(i, j);

				}
			}
		}
		return null;
	}

	public Building suchFeld(int x, int y, int radius) {
		for (int i = Math.max(x - radius, 0); i < Math.min(x + radius, world.length); i++) {
			for (int j = Math.max(y - radius, 0); j < Math.max(y + radius, world[x].length); j++) {
				if (world[i][j] instanceof Building) {
					if (((Building) world[i][j]).getIndex() == 20) {
						return (Building) world[i][j];
					}

				}
			}
		}
		return null;
	}

}
