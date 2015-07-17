package com.richard.knightmare.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import com.husten.knightmare.constants.StringConstants;
import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.matze.knightmare.meshes.Battle;
import com.matze.knightmare.meshes.Bauen;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Soldat;
import com.matze.knightmare.meshes.Spieler;
import com.matze.knightmare.meshes.Vehicle;
import com.matze.knightmare.meshes.Waren;
import com.richard.knightmare.serial.TimerTaskDistributer;
import com.richard.knightmare.sound.SoundPlayer;

public class EntityHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1624519500467505785L;
	private RectangleGraphicalObject[][] world, worldFieWarenTransport;
	private ArrayList<RectangleGraphicalObject> entities = new ArrayList<>(), entitiesWaren = new ArrayList<>();
	private int id = 1, idWaren = 1, ticksSinceLastRetry, ticksSinceLastRetryWaren;
	private HashMap<Soldat, SingleManPathfinding> finding = new HashMap<>();
	private HashMap<Soldat, MinimalInversivesPathfinding> findingFianWarnHansl = new HashMap<>();
	private HashMap<Soldat, Pos> actualDeastination = new HashMap<>(), replacedDestination = new HashMap<>(), actualWaren = new HashMap<>(),
			replacedWaren = new HashMap<>();
	private HashMap<Soldat, Integer> triesOnActual = new HashMap<>(), triesOnReplaced = new HashMap<>(), triesActualWaren = new HashMap<>(),
			triesReplacedWaren = new HashMap<>();
	private HashMap<Soldat, RectangleGraphicalObject> chasing = new HashMap<>(), chasingWaren = new HashMap<>();
	private ArrayList<RectangleGraphicalObject> selection = new ArrayList<>();
	private Spieler[] spieler;
	private boolean processing = false, ticking = false;
	private transient SoundPlayer battleplayer;
	private transient Timer battletimer;

	public void load(){
		battleplayer = new SoundPlayer("Swordclash.WAV");
		battleplayer.setVolume(Float.parseFloat(Loader.getCfgValue("Volume")));
		battletimer = new Timer(true);
		battletimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				for (int x = 0; x < world.length; x++) {
					for (int y = 0; y < world[x].length; y++) {
						if (getOn(x, y) != null) {
							if (getOn(x, y) instanceof Soldat) {
								if (worldFieWarenTransport[x][y] != null) {
									if (((Soldat) getOn(x, y)).getSpieler().getTeam() != ((Soldat) worldFieWarenTransport[x][y]).getSpieler().getTeam()) {
										dieWarenHansl(x, y);
									}
								}
								if (kannK‰mpfen((Soldat) getOn(x, y), getOn(x - 1, y))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x - 1, y), 0);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK‰mpfen((Soldat) getOn(x, y), getOn(x, y - 1))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x, y - 1), 0);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK‰mpfen((Soldat) getOn(x, y), getOn(x + 1, y))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x + 1, y), 0);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK‰mpfen((Soldat) getOn(x, y), getOn(x, y + 1))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x, y + 1), 0);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK‰mpfenGeb((Soldat) getOn(x, y), getOn(x - 1, y))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x - 1, y), 2);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK‰mpfenGeb((Soldat) getOn(x, y), getOn(x, y - 1))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x, y - 1), 2);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK‰mpfenGeb((Soldat) getOn(x, y), getOn(x + 1, y))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x + 1, y), 2);
									if (looser != null) {
										die(looser);
										battleplayer.stop();
									}
								} else if (kannK‰mpfenGeb((Soldat) getOn(x, y), getOn(x, y + 1))) {
									battleplayer.start();
									Soldat looser = Battle.kampf((Soldat) getOn(x, y), (Soldat) getOn(x, y + 1), 2);
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
	
	public EntityHandler(int width, int height, Spieler[] spieler) {
		this.spieler = spieler;
		world = new RectangleGraphicalObject[width][height];
		worldFieWarenTransport = new RectangleGraphicalObject[width][height];
		load();
	}

	public void selClear() {
		selection.clear();
	}

	public void shotdown() {
		battleplayer.stop();
		battletimer.cancel();
	}

	private boolean kannK‰mpfen(Soldat s1, RectangleGraphicalObject s2) {
		if (s2 instanceof Soldat) {
			if (s2.getSpieler().getTeam() != -1) {
				return s1.getSpieler().getTeam() != ((Soldat) s2).getSpieler().getTeam();
			}
		}
		return false;
	}

	private boolean kannK‰mpfenGeb(Soldat s1, RectangleGraphicalObject s2) {
		if (s2 instanceof Building) {
			if (s2.getSpieler().getTeam() != -1) {
				return s1.getSpieler().getTeam() != ((Building) s2).getSpieler().getTeam();
			}
		}
		return false;
	}

	private void register(RectangleGraphicalObject object) {
		if (object.getID() == 0) {
			object.register(id);
			id++;
		}
	}

	private void registerWaren(RectangleGraphicalObject object) {
		if (object.getID() == 0) {
			object.register(idWaren);
			idWaren++;
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
		for (int i = x; i <= Math.min(x + width, worldFieWarenTransport.length - 1); i++) {
			for (int j = y; j <= Math.min(y + height, worldFieWarenTransport[x].length - 1); j++) {
				if (worldFieWarenTransport[i][j] != null) {
					worldFieWarenTransport[i][j].draw();
				}
			}
		}
		// for (int i = x; i <= Math.min(x + width, world.length - 1); i++) {
		// for (int j = y; j <= Math.min(y + height, world[x].length - 1); j++)
		// {
		// if (world[i][j] != null) {
		// world[i][j].draw();
		// }
		// }
		// }
		for (int i = Math.min(x + width, world.length - 1); i >= x; i--) {
			for (int j = Math.min(y + height, world[x].length - 1); j >= y; j--) {
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

	public boolean placeWaren(RectangleGraphicalObject object) {
		registerWaren(object);
		int w = object.getWidth() / 32;
		int h = object.getHeight() / 32;
		int startW = (int) (object.getPosition().getX() / 32);
		int startH = (int) (object.getPosition().getY() / 32);

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (object instanceof Soldat) {
					if (isObstractedForWaren(startW + i, startH + j, object)) {
						return false;
					}
				} else {
					if (isObstractedForBuildingWaren(startW + i, startH + j, (Building) object)) {
						return false;
					}
				}
			}
		}
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				worldFieWarenTransport[startW + i][startH + j] = object;
			}
		}
		object.initRender();
		entitiesWaren.add(object);
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

						if (entities.get(i) instanceof Soldat && entities.get(i).getSpieler().equals(spieler[0])) {
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
					if (entities.get(i) instanceof Soldat && entities.get(i).getSpieler().equals(spieler[0])) {
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
						SingleManPathfinding path = new SingleManPathfinding(entry.getKey(), replacedDestination.get(entry.getKey()), world);
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
					SingleManPathfinding path = new SingleManPathfinding(entry.getKey(), actualDeastination.get(entry.getKey()), world);
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
					SingleManPathfinding path = new SingleManPathfinding(entry.getKey(), new Pos(x, y), world);
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
		tickWaren();
		ticking = false;
	}

	private void tickWaren() {
		if (ticksSinceLastRetryWaren > 20) {
			ArrayList<Soldat> toRemoveActual = new ArrayList<>();
			for (Entry<Soldat, Pos> entry : actualWaren.entrySet()) {
				if (triesActualWaren.get(entry.getKey()) > 5) {
					if (triesReplacedWaren.get(entry.getKey()) > 5) {
						triesReplacedWaren.remove(entry.getKey());
						replacedWaren.remove(entry.getKey());
						triesActualWaren.remove(entry.getKey());
						// actualDeastination.remove(entry.getKey());
						toRemoveActual.add(entry.getKey());
						chasingWaren.remove(entry.getKey());
						Waren[] w = ((Vehicle) entry.getKey()).getSlots();
						for (Waren waren : w) {
							entry.getKey().getSpieler().verteilen(waren.getID(), waren.getAmount());
						}
						dieWarenHansl((int) entry.getKey().getPosition().getX() / 32, (int) entry.getKey().getPosition().getY() / 32);
					} else {
						MinimalInversivesPathfinding path = new MinimalInversivesPathfinding(entry.getKey(), replacedWaren.get(entry.getKey()),worldFieWarenTransport,world);
						com.richard.knightmare.util.MinimalInversivesPathfinding.Pos alternative = path.pathfind();
						if (alternative == null) {
							findingFianWarnHansl.put(entry.getKey(), path);
							triesReplacedWaren.remove(entry.getKey());
							replacedWaren.remove(entry.getKey());
							triesActualWaren.remove(entry.getKey());
							// actualDeastination.remove(entry.getKey());
							toRemoveActual.add(entry.getKey());
						} else {
							replacedWaren.put(entry.getKey(), new Pos(alternative.x * 32 + 16, alternative.y * 32 + 16));
							triesReplacedWaren.put(entry.getKey(), triesReplacedWaren.get(entry.getKey()) + 1);
						}
					}
				} else {
					MinimalInversivesPathfinding path = new MinimalInversivesPathfinding(entry.getKey(), actualWaren.get(entry.getKey()),worldFieWarenTransport,world);
					com.richard.knightmare.util.MinimalInversivesPathfinding.Pos alternative = path.pathfind();
					if (alternative == null) {
						findingFianWarnHansl.put(entry.getKey(), path);
						triesReplacedWaren.remove(entry.getKey());
						replacedWaren.remove(entry.getKey());
						triesActualWaren.remove(entry.getKey());
						// actualDeastination.remove(entry.getKey());
						toRemoveActual.add(entry.getKey());
					} else {
						triesActualWaren.put(entry.getKey(), triesActualWaren.get(entry.getKey()) + 1);
					}
				}
			}
			for (Soldat soldat : toRemoveActual) {
				actualWaren.remove(soldat);
			}
			ticksSinceLastRetryWaren = 0;
		}
		ticksSinceLastRetryWaren++;
		ArrayList<Soldat> toRemove = new ArrayList<>();
		HashMap<Soldat, MinimalInversivesPathfinding> toPut = new HashMap<>();
		for (Entry<Soldat, MinimalInversivesPathfinding> entry : findingFianWarnHansl.entrySet()) {
			if (!entry.getValue().move()) {
				if (entry.getValue().getFinished()) {
					// finding.remove(entry.getKey());
					toRemove.add(entry.getKey());
					if (chasingWaren.containsKey(entry.getKey())) {
						chasingWaren.remove(entry.getKey());
						// TODO is already near?
					}
				} else {
					findingFianWarnHansl.get(entry.getKey()).stop();
					// pathfindTo(entry.getValue().getZiel().getX()*32+16,
					// entry.getValue().getZiel().getY()*32+16, entry.getKey());
					double x = entry.getValue().getZiel().getX() * 32 + 16, y = entry.getValue().getZiel().getY() * 32 + 16;
					MinimalInversivesPathfinding path = new MinimalInversivesPathfinding(entry.getKey(), new Pos(x, y),worldFieWarenTransport,world);
					com.richard.knightmare.util.MinimalInversivesPathfinding.Pos alternative = path.pathfind();
					if (alternative == null) {
						toPut.put(entry.getKey(), path);
						// finding.put(entry.getKey(), path);
					} else {
						// finding.remove(entry.getKey());
						toRemove.add(entry.getKey());
						actualWaren.put(entry.getKey(), new Pos(x, y));
						triesActualWaren.put(entry.getKey(), 1);
						replacedWaren.put(entry.getKey(), new Pos(alternative.x * 32 + 16, alternative.y * 32 + 16));
						triesReplacedWaren.put(entry.getKey(), 0);
					}
				}
			}
		}
		for (Soldat soldat : toRemove) {
			findingFianWarnHansl.remove(soldat);
		}
		for (Entry<Soldat, MinimalInversivesPathfinding> entry : toPut.entrySet()) {
			findingFianWarnHansl.put(entry.getKey(), entry.getValue());
		}
	}

	public void pathfindTo(double x, double y, RectangleGraphicalObject object) {
		if (finding.containsKey(object)) {
			finding.get(object).stop();
		}
		finding.remove(object);
		SingleManPathfinding path = new SingleManPathfinding((Soldat) object, new Pos(x, y), world);
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

	public void pathfindToWarenHansl(double x, double y, RectangleGraphicalObject object) {
		if (findingFianWarnHansl.containsKey(object)) {
			findingFianWarnHansl.get(object).stop();
		}
		findingFianWarnHansl.remove(object);
		MinimalInversivesPathfinding path = new MinimalInversivesPathfinding((Soldat) object, new Pos(x, y),worldFieWarenTransport,world);
		com.richard.knightmare.util.MinimalInversivesPathfinding.Pos alternative = path.pathfind();
		if (alternative == null) {
			findingFianWarnHansl.put((Soldat) object, path);
		} else {
			actualWaren.put((Soldat) object, new Pos(x, y));
			triesActualWaren.put((Soldat) object, 1);
			replacedWaren.put((Soldat) object, new Pos(alternative.x * 32 + 16, alternative.y * 32 + 16));
			triesReplacedWaren.put((Soldat) object, 0);
		}
	}

	public boolean remove(RectangleGraphicalObject object, int abreiﬂenderSpieler) {
		int w = object.getWidth() / 32;
		int h = object.getHeight() / 32;
		int startW = (int) (object.getPosition().getX() / 32);
		int startH = (int) (object.getPosition().getY() / 32);
		if (world[startW][startH].getSpieler().equals(spieler[abreiﬂenderSpieler]) || world[startW][startH].getSpieler().equals(Bauen.getMutterNatur())) {
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

	public RectangleGraphicalObject remove(int x, int y, int abreiﬂenderSpieler) {
		RectangleGraphicalObject object = world[x][y];
		if (object != null) {
			if (!remove(object, abreiﬂenderSpieler)) {
				return null;
			}
		}
		return object;
	}

	public boolean die(RectangleGraphicalObject object) {
		if (object instanceof Soldat) {
			((Soldat) object).stirb();
			if (finding.containsKey(object)) {
				finding.get(object).stop();
			}
			finding.remove(object);
			actualDeastination.remove(object);
			triesOnActual.remove(object);
			triesOnReplaced.remove(object);
			replacedDestination.remove(object);
		}

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

	public void dieWarenHansl(int x, int y) {
		RectangleGraphicalObject object = worldFieWarenTransport[x][y];
		if (findingFianWarnHansl.containsKey(object)) {
			findingFianWarnHansl.get(object).stop();
		}
		findingFianWarnHansl.remove(object);
		actualWaren.remove(object);
		triesActualWaren.remove(object);
		triesReplacedWaren.remove(object);
		replacedWaren.remove(object);
		entitiesWaren.remove(object);
		worldFieWarenTransport[x][y] = null;
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

	private boolean isObstractedForWaren(int x, int y, RectangleGraphicalObject soldat) {
		if (soldat.isWaterproof()) {
			if (Knightmare.terrain.getMeterial(x, y) == null) {
				return world[x][y] != null;
			} else {
				return true;
			}
		} else {
			if (Knightmare.terrain.getMeterial(x, y) == null) {
				return true;
			} else {
				return world[x][y] != null;
			}
		}
	}

	private boolean isObstractedForBuilding(int x, int y, Building building) {
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

	private boolean isObstractedForBuildingWaren(int x, int y, Building building) {
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
				if (world[i][j] == null && Knightmare.terrain.getElement(i, j) != null) {
					return new Pos(i * 32, j * 32);

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
	
	public Spieler[] getSpieler(){
		return spieler;
	}
	
	public void ReInitRender(){
		for(RectangleGraphicalObject obj: entities){
			obj.initRender();
		}
		for(RectangleGraphicalObject obj: entitiesWaren){
			obj.initRender();
		}
	}
	
	public void reInitTimer(){
		for(RectangleGraphicalObject obj: entities){
			TimerTaskDistributer.distribute(obj);
		}
	}

}
