package com.richard.knightmare.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.husten.knightmare.graphicalObjects.GraphicalObject;

public class Path2 {

	private ArrayList<PathObject> points = new ArrayList<>(), possiblePoints = new ArrayList<>();
	private PathObject[][] pointsInGrid, possiblePointsInGrid;
	private GraphicalObject[][] world;
	private Pos ziel;
	private BufferedWriter br;

	public static void main(String[] args) {
		new Path2(new GraphicalObject[513][513]);
	}

	private Path2(GraphicalObject[][] world) {
		try {
			br = new BufferedWriter(new FileWriter(new File("D:\\Log.log")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pos start = new Pos(0, 0);
		ziel = new Pos(512, 512);

		System.out.println("Starting from " + start.x + "|" + start.y + ". Aiming for " + ziel.x + "|" + ziel.y);
		pointsInGrid = new PathObject[world.length][world[0].length];
		possiblePointsInGrid = new PathObject[world.length][world[0].length];
		this.world = world;
		PathObject startObjt = new PathObject(esteem(start), 0, esteem(start), null, start);
		possiblePointsInGrid[start.x][start.y] = startObjt;
		pointsInGrid[start.x][start.y] = startObjt;
		points.add(startObjt);
		long t1 = System.currentTimeMillis();
		findnNextPos(startObjt);
		ArrayList<PathObject> path = new ArrayList<>();
		PathObject currenObject = points.get(points.size() - 1);
		path.add(currenObject);
		while (currenObject.parent != null) {
			currenObject = currenObject.parent;
			path.add(currenObject);
		}
		System.out.println("Time passed: " + (System.currentTimeMillis() - t1));
		if (path.size() - 1 == esteem(start)) {
			for (int i = path.size() - 1; i >= 0; i--) {
				System.out.println(path.get(i).point.x + "|" + path.get(i).point.y);
			}
		} else {
			System.out.println("Cannot reach");
			for (int i = path.size() - 1; i >= 0; i--) {
				System.out.println(path.get(i).point.x + "|" + path.get(i).point.y);
			} // TODO
		}
	}

	private void findnNextPos(PathObject p) {
		// System.out.println("searching at:" + p.point.x + "|" + p.point.y);
		try {
			br.write("Searching at: " + p.point.x + "|" + p.point.y);
			br.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pos[] ps = new Pos[4];
		ps[0] = translatePos(p.point, 1, 0);
		ps[1] = translatePos(p.point, -1, 0);
		ps[2] = translatePos(p.point, 0, 1);
		ps[3] = translatePos(p.point, 0, -1);

		for (int i = 0; i < 4; i++) {
			if (isValid(ps[i])) {
				if (compare(ps[i], ziel)) {
					int h = p.real;
					points.add(new PathObject(0, h++, h++, p, ps[i]));
					return;
				} else if ((!isObstrated(ps[i])) && possiblePointsInGrid[ps[i].x][ps[i].y] == null) {
					// System.out.println("Found " + ps[i].x + "|" + ps[i].y);
					int h = p.real;
					PathObject obj = new PathObject(esteem(ps[i]), h++, esteem(ps[i]) + h++, p, ps[i]);
					shorten(obj);
					possiblePointsInGrid[obj.point.x][obj.point.y] = obj;
					possiblePoints.add(obj);
				}
			}
		}
		// System.out.println("We now know: ");
		// for (int i = 0; i < possiblePoints.size(); i++) {
		// System.out.println(possiblePoints.get(i).point.x + "|" +
		// possiblePoints.get(i).point.y);
		// }
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
			for (int i = 0; i < minSumIndexes.size(); i++) {
				if (minEs == possiblePoints.get(minSumIndexes.get(i)).estimate) {
					int index = minSumIndexes.get(i);
					minSumIndexes.clear();
					minSumIndexes.add(index);
					break;
				}
			}
		}
		points.add(possiblePoints.get(minSumIndexes.get(0)));
		pointsInGrid[possiblePoints.get(minSumIndexes.get(0)).point.x][possiblePoints.get(minSumIndexes.get(0)).point.y] = possiblePoints.get(minSumIndexes.get(0));
		possiblePoints.remove((int) minSumIndexes.get(0));
		if (!points.get(points.size() - 1).equals(p)) {
			findnNextPos(points.get(points.size() - 1));
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
		return p.x < world.length && p.x >= 0 && p.y < world[0].length && p.y >= 0;
	}

	private boolean isObstrated(Pos p) {
		// TODO
		if (compare(p, new Pos(2, 0)) || compare(p, new Pos(2, 1))
				|| compare(p, new Pos(1, 2))/* ||compare(p, new Pos(0, 2)) */) {
			return true;
		}
		return false;
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
