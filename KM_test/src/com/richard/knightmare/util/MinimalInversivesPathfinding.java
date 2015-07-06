package com.richard.knightmare.util;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.husten.knightmare.core.Knightmare;
import com.matze.knightmare.meshes.Soldat;

public class MinimalInversivesPathfinding {

	private ArrayList<PathObject> points = new ArrayList<>(), possiblePoints = new ArrayList<>(), path = new ArrayList<>();
	private ArrayList<Vektor> vektoren = new ArrayList<>();
	private PathObject[][] pointsInGrid, possiblePointsInGrid;
	private Pos ziel, start, ersatzziel;
	private com.richard.knightmare.util.Pos realStart, realZiel;
	private com.richard.knightmare.util.Pos currentVektorStartPos;
	private boolean alternate = false, sucess = false, moveable = false, finishedmoving = false, continueing = true;
	private Soldat soldat;

	public com.richard.knightmare.util.Pos getZiel() {
		return new com.richard.knightmare.util.Pos(ziel.x, ziel.y);
	}

	private static Logger LOG = LogManager.getLogger(MinimalInversivesPathfinding.class);

	public MinimalInversivesPathfinding(Soldat soldat, com.richard.knightmare.util.Pos ende) {
		this.soldat = soldat;
		realStart = soldat.getPosition();
		realZiel = ende;
		start = new Pos((int) (realStart.getX() / 32), (int) (realStart.getY() / 32));
		ziel = new Pos((int) (ende.getX() / 32), (int) (ende.getY() / 32));
		LOG.info("Konstruktor gelaufern");
		// System.out.println("constuktor");
	}

	public void setContinuing() {
		continueing = false;
	}

	public boolean getContinuing() {
		return continueing;
	}
	
	public void stop(){
		soldat.setPosition(vektoren.get(0).getStart());
	}

	public Soldat getSoldat() {
		return soldat;
	}

	public boolean move() {
		if (moveable) {
			if (!vektoren.get(0).isAlreadyMoved()) {
				if (!finishedmoving && continueing
						&& !isObstracted(new Pos((int) (vektoren.get(0).getEnde().getX() / 32), (int) (vektoren.get(0).getEnde().getY() / 32)))) {
					EntityHandler.world[(int) (vektoren.get(0).getStart().getX() / 32)][(int) (vektoren.get(0).getStart().getY() / 32)] = null;
					EntityHandler.world[(int) (vektoren.get(0).getEnde().getX() / 32)][(int) (vektoren.get(0).getEnde().getY() / 32)] = soldat;
				} else {
					return false;
				}
			}
			if (vektoren.get(0).move()) {
				if (vektoren.size() > 1) {
					vektoren.remove(0);
					return true;
				} else {
					finishedmoving = true;
					return false;
				}
			}
		}
		return true;
	}

	public boolean getFinished() {
		return finishedmoving;
	}

	private void recursivVektorProduction(int index) {
		if (index == 0) {
			vektoren.add(new Vektor(currentVektorStartPos,
					(ersatzziel == null ? realZiel : new com.richard.knightmare.util.Pos(ersatzziel.x * 32 + 16, ersatzziel.y * 32 + 16)), soldat));
			return;
		}
		if (index > 1) {
			if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 1, 0))
					&& compare(path.get(index - 2).point, translatePos(path.get(index).point, 1, 1))) {
				vektoren.add(new Vektor(currentVektorStartPos,
						new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 32, (int) (currentVektorStartPos.getY() / 32) * 32 + 16),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				vektoren.add(new Vektor(currentVektorStartPos, new com.richard.knightmare.util.Pos(currentVektorStartPos.getX() + 31, currentVektorStartPos.getY() + 31),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				recursivVektorProduction(index - 2);
				return;
			}
			if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 1, 0))
					&& compare(path.get(index - 2).point, translatePos(path.get(index).point, 1, -1))) {
				vektoren.add(new Vektor(currentVektorStartPos,
						new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 32, (int) (currentVektorStartPos.getY() / 32) * 32 + 16),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				vektoren.add(new Vektor(currentVektorStartPos, new com.richard.knightmare.util.Pos(currentVektorStartPos.getX() + 31, currentVektorStartPos.getY() - 31),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				recursivVektorProduction(index - 2);
				return;
			}
			if (compare(path.get(index - 1).point, translatePos(path.get(index).point, -1, 0))
					&& compare(path.get(index - 2).point, translatePos(path.get(index).point, -1, 1))) {
				vektoren.add(new Vektor(currentVektorStartPos,
						new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32, (int) (currentVektorStartPos.getY() / 32) * 32 + 16),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				vektoren.add(new Vektor(currentVektorStartPos, new com.richard.knightmare.util.Pos(currentVektorStartPos.getX() - 31, currentVektorStartPos.getY() + 31),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				recursivVektorProduction(index - 2);
				return;
			}
			if (compare(path.get(index - 1).point, translatePos(path.get(index).point, -1, 0))
					&& compare(path.get(index - 2).point, translatePos(path.get(index).point, -1, -1))) {
				vektoren.add(new Vektor(currentVektorStartPos,
						new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32, (int) (currentVektorStartPos.getY() / 32) * 32 + 16),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				vektoren.add(new Vektor(currentVektorStartPos, new com.richard.knightmare.util.Pos(currentVektorStartPos.getX() - 31, currentVektorStartPos.getY() - 31),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				recursivVektorProduction(index - 2);
				return;
			}
			if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 0, 1))
					&& compare(path.get(index - 2).point, translatePos(path.get(index).point, 1, 1))) {
				vektoren.add(new Vektor(currentVektorStartPos,
						new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 16, (int) (currentVektorStartPos.getY() / 32) * 32 + 32),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				vektoren.add(new Vektor(currentVektorStartPos, new com.richard.knightmare.util.Pos(currentVektorStartPos.getX() + 31, currentVektorStartPos.getY() + 31),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				recursivVektorProduction(index - 2);
				return;
			}
			if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 0, 1))
					&& compare(path.get(index - 2).point, translatePos(path.get(index).point, -1, 1))) {
				vektoren.add(new Vektor(currentVektorStartPos,
						new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 16, (int) (currentVektorStartPos.getY() / 32) * 32 + 32),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				vektoren.add(new Vektor(currentVektorStartPos, new com.richard.knightmare.util.Pos(currentVektorStartPos.getX() - 31, currentVektorStartPos.getY() + 31),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				recursivVektorProduction(index - 2);
				return;
			}
			if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 0, -1))
					&& compare(path.get(index - 2).point, translatePos(path.get(index).point, 1, -1))) {
				vektoren.add(new Vektor(currentVektorStartPos,
						new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 16, (int) (currentVektorStartPos.getY() / 32) * 32),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				vektoren.add(new Vektor(currentVektorStartPos, new com.richard.knightmare.util.Pos(currentVektorStartPos.getX() + 31, currentVektorStartPos.getY() - 31),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				recursivVektorProduction(index - 2);
				return;
			}
			if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 0, -1))
					&& compare(path.get(index - 2).point, translatePos(path.get(index).point, -1, -1))) {
				vektoren.add(new Vektor(currentVektorStartPos,
						new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 16, (int) (currentVektorStartPos.getY() / 32) * 32),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				vektoren.add(new Vektor(currentVektorStartPos, new com.richard.knightmare.util.Pos(currentVektorStartPos.getX() - 31, currentVektorStartPos.getY() - 31),
						soldat));
				currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
				recursivVektorProduction(index - 2);
				return;
			}
		}
		if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 1, 0))) {
			vektoren.add(new Vektor(currentVektorStartPos,
					new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 48, (int) (currentVektorStartPos.getY() / 32) * 32 + 16),
					soldat));
			currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
			recursivVektorProduction(index - 1);
			return;
		}
		if (compare(path.get(index - 1).point, translatePos(path.get(index).point, -1, 0))) {
			vektoren.add(new Vektor(currentVektorStartPos,
					new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 - 16, (int) (currentVektorStartPos.getY() / 32) * 32 + 16),
					soldat));
			currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
			recursivVektorProduction(index - 1);
			return;
		}
		if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 0, 1))) {
			vektoren.add(new Vektor(currentVektorStartPos,
					new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 16, (int) (currentVektorStartPos.getY() / 32) * 32 + 48),
					soldat));
			currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
			recursivVektorProduction(index - 1);
			return;
		}
		if (compare(path.get(index - 1).point, translatePos(path.get(index).point, 0, -1))) {
			vektoren.add(new Vektor(currentVektorStartPos,
					new com.richard.knightmare.util.Pos((int) (currentVektorStartPos.getX() / 32) * 32 + 16, (int) (currentVektorStartPos.getY() / 32) * 32 - 16),
					soldat));
			currentVektorStartPos = vektoren.get(vektoren.size() - 1).getEnde();
			recursivVektorProduction(index - 1);
			return;
		}
	}

	public Pos pathfind() {
		currentVektorStartPos = realStart;
		pointsInGrid = new PathObject[EntityHandler.world.length][EntityHandler.world[0].length];
		possiblePointsInGrid = new PathObject[EntityHandler.world.length][EntityHandler.world[0].length];
		PathObject startObjt = new PathObject(esteem(start), 0, esteem(start), null, start);
		possiblePointsInGrid[start.x][start.y] = startObjt;
		pointsInGrid[start.x][start.y] = startObjt;
		points.add(startObjt);
		poses(startObjt);
		if (isObstracted(ziel)) {
			int eMin = Integer.MAX_VALUE;
			for (int i = 0; i < points.size(); i++) {
				eMin = Math.min(eMin, points.get(i).estimate);
			}
			for (int i = 0; i < points.size(); i++) {
				if (eMin == points.get(i).estimate) {
					ersatzziel = points.get(i).point;
					break;
				}
			}
			return ersatzziel;
		}
		if (!sucess) {
			int eMin = Integer.MAX_VALUE;
			for (int i = 0; i < points.size(); i++) {
				eMin = Math.min(eMin, points.get(i).estimate);
			}
			for (int i = 0; i < points.size(); i++) {
				if (eMin == points.get(i).estimate) {
					ersatzziel = points.get(i).point;
					break;
				}
			}
			return ersatzziel;
		}
		path = new ArrayList<>();
		PathObject currenObject = points.get(points.size() - 2);
		path.add(currenObject);
		while (currenObject.parent != null) {
			currenObject = currenObject.parent;
			path.add(currenObject);
		}
		recursivVektorProduction(path.size() - 1);
		// System.out.println("finished pathfinding");
		moveable = true;
		return ersatzziel;
	}

	private void poses(PathObject startPos) {
		while (true) {
			Pos[] ps = new Pos[4];
			ps[0] = translatePos(startPos.point, 1, 0);
			ps[1] = translatePos(startPos.point, -1, 0);
			ps[2] = translatePos(startPos.point, 0, 1);
			ps[3] = translatePos(startPos.point, 0, -1);

			for (int i = 0; i < 4; i++) {
				if (isValid(ps[i])) {
					if (compare(ps[i], ziel) && !isObstracted(ziel)) {
						int h = startPos.real;
						points.add(new PathObject(0, h++, h++, startPos, ps[i]));
						sucess = true;
						return;
					} else if ((!isObstracted(ps[i])) && possiblePointsInGrid[ps[i].x][ps[i].y] == null) {
						int h = startPos.real;
						PathObject obj = new PathObject(esteem(ps[i]), h++, esteem(ps[i]) + h++, startPos, ps[i]);
						shorten(obj);
						possiblePointsInGrid[obj.point.x][obj.point.y] = obj;
						possiblePoints.add(obj);
					}
				}
			}
			int minSum = Integer.MAX_VALUE;
			for (int i = 0; i < possiblePoints.size(); i++) {
				minSum = Math.min(minSum, possiblePoints.get(i).sum);
			}
			if (minSum == Integer.MAX_VALUE) {
				return;
			}
			ArrayList<Integer> minSumIndexes = new ArrayList<>();
			for (int i = 0; i < possiblePoints.size(); i++) {
				if (minSum == possiblePoints.get(i).sum) {
					minSumIndexes.add(i);
				}
			}
			if (minSumIndexes.size() != 1) {
				int minEs = Integer.MAX_VALUE;
				for (int i = 0; i < minSumIndexes.size(); i++) {
					minEs = Math.min(minEs, possiblePoints.get(minSumIndexes.get(i)).estimate);
				}
				if (minEs == Integer.MAX_VALUE) {
					return;
				}
				if (alternate) {
					for (int i = minSumIndexes.size() - 1; i > -1; i--) {
						if (minEs == possiblePoints.get(minSumIndexes.get(i)).estimate) {
							int index = minSumIndexes.get(i);
							minSumIndexes.clear();
							minSumIndexes.add(index);
							break;
						}
					}
				} else {
					for (int i = 0; i < minSumIndexes.size(); i++) {
						if (minEs == possiblePoints.get(minSumIndexes.get(i)).estimate) {
							int index = minSumIndexes.get(i);
							minSumIndexes.clear();
							minSumIndexes.add(index);
							break;
						}
					}
				}
				alternate = !alternate;
			}
			points.add(possiblePoints.get(minSumIndexes.get(0)));
			pointsInGrid[possiblePoints.get(minSumIndexes.get(0)).point.x][possiblePoints.get(minSumIndexes.get(0)).point.y] = possiblePoints.get(minSumIndexes.get(0));
			possiblePoints.remove((int) minSumIndexes.get(0));
			if (!points.get(points.size() - 1).equals(startPos)) {
				startPos = points.get(points.size() - 1);
			} else {
				break;
			}
		}
	}

	private void shorten(PathObject p) {
		int r = p.real;
		PathObject parent = p.parent;
		if (isValid(new Pos(p.point.x + 1, p.point.y))) {
			if (pointsInGrid[p.point.x + 1][p.point.y] != null) {
				if (pointsInGrid[p.point.x + 1][p.point.y].real + 1 < r) {
					r = pointsInGrid[p.point.x + 1][p.point.y].real;
					parent = pointsInGrid[p.point.x + 1][p.point.y].parent;
				}
			}
		}
		if (isValid(new Pos(p.point.x - 1, p.point.y))) {
			if (pointsInGrid[p.point.x - 1][p.point.y] != null) {
				if (pointsInGrid[p.point.x - 1][p.point.y].real + 1 < r) {
					r = pointsInGrid[p.point.x - 1][p.point.y].real;
					parent = pointsInGrid[p.point.x - 1][p.point.y].parent;
				}
			}
		}
		if (isValid(new Pos(p.point.x, p.point.y + 1))) {
			if (pointsInGrid[p.point.x][p.point.y + 1] != null) {
				if (pointsInGrid[p.point.x][p.point.y + 1].real + 1 < r) {
					r = pointsInGrid[p.point.x][p.point.y + 1].real;
					parent = pointsInGrid[p.point.x][p.point.y + 1].parent;
				}
			}
		}
		if (isValid(new Pos(p.point.x, p.point.y - 1))) {
			if (pointsInGrid[p.point.x][p.point.y - 1] != null) {
				if (pointsInGrid[p.point.x][p.point.y - 1].real + 1 < r) {
					r = pointsInGrid[p.point.x][p.point.y - 1].real;
					parent = pointsInGrid[p.point.x][p.point.y - 1].parent;
				}
			}
		}
		p.parent = parent;
		p.real = r;
	}

	private boolean isValid(Pos p) {
		return p.x < EntityHandler.world.length && p.x >= 0 && p.y < EntityHandler.world[0].length && p.y >= 0;
	}

	private boolean isObstracted(Pos p) {
		if (soldat.isWaterproof()) {
			if (Knightmare.terrain.getMeterial(p.x, p.y) == null) {
				if (EntityHandler.world[p.x][p.y] != null) {
					return EntityHandler.world[p.x][p.y].getID() != soldat.getID();
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			if (Knightmare.terrain.getMeterial(p.x, p.y) == null) {
				return true;
			} else {
				if (EntityHandler.world[p.x][p.y] != null) {
					return EntityHandler.world[p.x][p.y].getID() != soldat.getID();
				} else {
					return false;
				}
			}
		}
	}

	private Pos translatePos(Pos p, int x, int y) {
		return new Pos(p.x + x, p.y + y);
	}

	private boolean compare(Pos p1, Pos p2) {
		return (p1.x == p2.x) && (p1.y == p2.y);
	}

	private int esteem(Pos p1) {
		return Math.abs(ziel.x - p1.x) + Math.abs(ziel.y - p1.y);
	}

	private class PathObject {
		private int estimate, real, sum;
		private PathObject parent;
		private Pos point;

		private PathObject(int estimate, int real, int sum, PathObject parent, Pos point) {
			this.estimate = estimate;
			this.real = real;
			this.sum = sum;
			this.parent = parent;
			this.point = point;
		}
	}

	class Pos {

		int x;
		int y;

		private Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
