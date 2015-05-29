package com.richard.knightmare.util;

import java.util.ArrayList;
import com.husten.knightmare.graphicalObjects.GraphicalObject;

public class Pathfinder {

	private ArrayList<PathfinderObject> points = new ArrayList<>();
	private PathfinderObject[][] currentPoses;
	private GraphicalObject[][] world;
	private Pos ziel;

	public static void main(String args[]) {
		new Pathfinder(new GraphicalObject[513][513]);
	}

	public Pathfinder(GraphicalObject[][] world) {
		Pos start = new Pos(0, 0), ziel = new Pos(512, 512);
		System.out.println("Starting from " + start.x + "|" + start.y + ". Aiming for " + ziel.x + "|" + ziel.y);
		currentPoses = new PathfinderObject[world.length][world[0].length];

		this.world = world;
		this.ziel = ziel;

		long t1 = System.currentTimeMillis();
		PathfinderObject startObject = new PathfinderObject(esteem(start), 0, esteem(start), null, start);
		currentPoses[start.x][start.y] = startObject;
		points.add(startObject);
		addCurrentPosses(start);
		ArrayList<PathfinderObject> path = new ArrayList<>();
		PathfinderObject currenObject = points.get(points.size() - 1);
		path.add(currenObject);
		while (currenObject.parent != null) {
			currenObject = currenObject.parent;
			path.add(currenObject);
		}
		System.out.println(System.currentTimeMillis() - t1);
		if(path.size()-1==esteem(start)){
			for (int i = path.size() - 1; i >= 0; i--) {
				System.out.println(path.get(i).point.x + "|" + path.get(i).point.y);
			}
		}else{
			System.out.println("Cannot reach");
		}
	}

	private int esteem(Pos p1) {
		return Math.abs(ziel.x - p1.x) + Math.abs(ziel.y - p1.y);
	}

	private void addCurrentPosses(Pos start) {
		Pos[] ps = new Pos[4];
		ps[0] = translatePos(start, 1, 0);
		ps[1] = translatePos(start, -1, 0);
		ps[2] = translatePos(start, 0, 1);
		ps[3] = translatePos(start, 0, -1);
		int[] ss = new int[4];
		int[] es = new int[4];

		for (int i = 0; i < 4; i++) {
			ss[i] = Integer.MAX_VALUE;
			es[i] = Integer.MAX_VALUE;
			if (ps[i].x < world.length && ps[i].x >= 0 && ps[i].y < world[0].length && ps[i].y >= 0) {
				if (compare(ps[i], ziel)) {
					PathfinderObject endObject = new PathfinderObject(0, currentPoses[start.x][start.y].real++, currentPoses[start.x][start.y].real++,
							currentPoses[start.x][start.y], ps[i]);
					currentPoses[ps[i].x][ps[i].y] = endObject;
					points.add(endObject);
					return;
				}
				if ((!isObstrated(ps[i])) && currentPoses[ps[i].x][ps[i].y] == null) {
					int real = currentPoses[start.x][start.y].real;
					currentPoses[ps[i].x][ps[i].y] = new PathfinderObject(esteem(ps[i]), real++, real++ + esteem(ps[i]), currentPoses[start.x][start.y], ps[i]);
					getSmallestReal(ps[i]);
					ss[i] = currentPoses[ps[i].x][ps[i].y].sum;
					es[i] = currentPoses[ps[i].x][ps[i].y].estimate;
				}
			}
		}

		int minS = Math.min(Math.min(ss[0], ss[1]), Math.min(ss[2], ss[3]));
		if(minS==Integer.MAX_VALUE){
			return;
		}
		ArrayList<Integer> minSs = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			if (minS == ss[i]) {
				minSs.add(i);
			}
		}
		if (minSs.size() != 1) {
			int minE = es[0];
			for (int i = 1; i < minSs.size(); i++) {
				minE = Math.min(minE, es[i]);
			}
			for (int i = 0; i < minSs.size(); i++) {
				if (es[i] == minE) {
					minSs.clear();
					minSs.add(i);
				}
			}
		}
		points.add(currentPoses[ps[minSs.get(0)].x][ps[minSs.get(0)].y]);
		addCurrentPosses(ps[minSs.get(0)]);
	}

	private boolean isObstrated(Pos p) {
		// TODO
		if(compare(p, new Pos(2, 0))||compare(p, new Pos(2, 1))||compare(p, new Pos(1, 2))||compare(p, new Pos(0, 2))){
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

	private void getSmallestReal(Pos p) {
		int real = Integer.MAX_VALUE;
		Pos[] ps = new Pos[4];
		ps[0] = translatePos(p, 1, 0);
		ps[1] = translatePos(p, -1, 0);
		ps[2] = translatePos(p, 0, 1);
		ps[3] = translatePos(p, 0, -1);
		for (int i = 0; i < 4; i++) {
			if (ps[i].x < world.length && ps[i].x >= 0 && ps[i].y < world[0].length && ps[i].y >= 0) {
				if (currentPoses[ps[i].x][ps[i].y] != null) {
					if (real >= currentPoses[ps[i].x][ps[i].y].real) {
						real = currentPoses[ps[i].x][ps[i].y].real;
						currentPoses[p.x][p.y].real = real;
						currentPoses[p.x][p.y].parent = currentPoses[ps[i].x][ps[i].y];
					}
				}
			}
		}
	}

	private class Pos {

		private int x, y;

		private Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private class PathfinderObject {

		private int estimate, real, sum;
		private PathfinderObject parent;
		private Pos point;

		private PathfinderObject(int estimate, int real, int sum, PathfinderObject parent, Pos point) {
			this.estimate = estimate;
			this.real = real;
			this.sum = sum;
			this.parent = parent;
			this.point = point;
		}
	}
}
