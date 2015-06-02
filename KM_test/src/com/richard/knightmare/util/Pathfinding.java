package com.richard.knightmare.util;

import java.util.ArrayList;

import com.husten.knightmare.core.Knightmare;
import com.matze.knightmare.meshes.Soldat;

public class Pathfinding {

	private ArrayList<PathObject> points = new ArrayList<>(), possiblePoints = new ArrayList<>(), path = new ArrayList<>();
	private ArrayList<Vektor> vektoren = new ArrayList<>();
	private PathObject[][] pointsInGrid, possiblePointsInGrid;
	private Pos ziel, start;
	private com.richard.knightmare.util.Pos realStart, realZiel;
	private com.richard.knightmare.util.Pos currentVektorStartPos;
	private boolean alternate = false, sucess = false;
	private Soldat soldat;

	public Pathfinding(Soldat soldat, com.richard.knightmare.util.Pos ende) {
		this.soldat = soldat;
		realStart = soldat.getPosition();
		realZiel = ende;
		start = new Pos((int) (realStart.getX() / 32), (int) (realStart.getY() / 32));
		ziel = new Pos((int) (ende.getX() / 32), (int) (ende.getY() / 32));
	}

	private void recursivVektorProduction(int index) {
		if (index == 0) {
			vektoren.add(new Vektor(currentVektorStartPos, realZiel, soldat));
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

	public ArrayList<Vektor> pathfind() {
		if (!isObstrated(ziel)) {
			currentVektorStartPos = realStart;
			pointsInGrid = new PathObject[Knightmare.world.length][Knightmare.world[0].length];
			possiblePointsInGrid = new PathObject[Knightmare.world.length][Knightmare.world[0].length];
			PathObject startObjt = new PathObject(esteem(start), 0, esteem(start), null, start);
			possiblePointsInGrid[start.x][start.y] = startObjt;
			pointsInGrid[start.x][start.y] = startObjt;
			points.add(startObjt);
			poses(startObjt);
		}
		if (sucess) {
			path = new ArrayList<>();
			PathObject currenObject = points.get(points.size() - 2);
			path.add(currenObject);
			while (currenObject.parent != null) {
				currenObject = currenObject.parent;
				path.add(currenObject);
			}
			recursivVektorProduction(path.size() - 1);
			return vektoren;
		}
		return null;
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
					if (compare(ps[i], ziel)) {
						int h = startPos.real;
						points.add(new PathObject(0, h++, h++, startPos, ps[i]));
						sucess = true;
						return;
					} else if ((!isObstrated(ps[i])) && possiblePointsInGrid[ps[i].x][ps[i].y] == null) {
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
				// findnNextPos(points.get(points.size() - 1));
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
		return p.x < Knightmare.world.length && p.x >= 0 && p.y < Knightmare.world[0].length && p.y >= 0;
	}

	private boolean isObstrated(Pos p) {
		if (soldat.isWaterproof()) {
			if (Knightmare.terrain.getMeterial(p.x, p.y) == null) {
				return Knightmare.world[p.x][p.y] != null;
			} else {
				return true;
			}
		} else {
			if (Knightmare.terrain.getMeterial(p.x, p.y) == null) {
				return true;
			} else {
				return Knightmare.world[p.x][p.y] != null;
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

	private class Pos {

		private int x, y;

		private Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
