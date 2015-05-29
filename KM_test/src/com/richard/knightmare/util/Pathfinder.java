package com.richard.knightmare.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.husten.knightmare.graphicalObjects.GraphicalObject;

public class Pathfinder {

	private ArrayList<PathfinderObject> points = new ArrayList<>();
	private HashMap<Pos, PathfinderObject> currentPoses = new HashMap<>();
	private GraphicalObject[][] world;
	private Pos ziel;

	public Pathfinder(GraphicalObject[][] world, Pos start, Pos ziel) {
		this.world = world;
		this.ziel = ziel;

		PathfinderObject startObject = new PathfinderObject(esteem(start), 0, esteem(start), null);
		currentPoses.put(start, startObject);
		points.add(startObject);
		addCurrentPosses(start);
	}

	private int esteem(Pos p1) {
		return (int) Math.abs(ziel.getX() - p1.getX()) + (int) Math.abs(ziel.getY() - p1.getY());
	}

	private void addCurrentPosses(Pos start) {
		Pos[] ps = new Pos[4];
		ps[0] = translatePos(start, 1, 0);
		ps[1] = translatePos(start, -1, 0);
		ps[2] = translatePos(start, 0, 1);
		ps[2] = translatePos(start, 0, -1);
		int[] ss = new int[4];
		int[] es = new int[4];
		
		for(int i = 0; i<4; i++){
			
		}
		if (p1.equals(ziel)) {
			PathfinderObject endObject = new PathfinderObject(0, currentPoses.get(start).real++, currentPoses.get(start).real++, currentPoses.get(start));
			currentPoses.put(p1, endObject);
			points.add(endObject);
			return;
		}
		if (p2.equals(ziel)) {
			PathfinderObject endObject = new PathfinderObject(0, currentPoses.get(start).real++, currentPoses.get(start).real++, currentPoses.get(start));
			currentPoses.put(p1, endObject);
			points.add(endObject);
			return;
		}
		if (p3.equals(ziel)) {
			PathfinderObject endObject = new PathfinderObject(0, currentPoses.get(start).real++, currentPoses.get(start).real++, currentPoses.get(start));
			currentPoses.put(p1, endObject);
			points.add(endObject);
			return;
		}
		if (p4.equals(ziel)) {
			PathfinderObject endObject = new PathfinderObject(0, currentPoses.get(start).real++, currentPoses.get(start).real++, currentPoses.get(start));
			currentPoses.put(p1, endObject);
			points.add(endObject);
			return;
		}
		if ((!isObstrated(p1)) && currentPoses.get(p1) == null) {
			currentPoses.put(p1, new PathfinderObject(esteem(p1), currentPoses.get(start).real++, currentPoses.get(start).real++ + esteem(p1), currentPoses.get(start)));
			getSmallestReal(p1);
			s1 = currentPoses.get(p1).sum;
			e1 = currentPoses.get(p1).estimate;
		}
		if ((!isObstrated(p2)) && currentPoses.get(p2) == null) {
			currentPoses.put(p2, new PathfinderObject(esteem(p2), currentPoses.get(start).real++, currentPoses.get(start).real++ + esteem(p2), currentPoses.get(start)));
			getSmallestReal(p2);
			s2 = currentPoses.get(p2).sum;
			e2 = currentPoses.get(p2).estimate;

		}
		if ((!isObstrated(p3)) && currentPoses.get(p3) == null) {
			currentPoses.put(p3, new PathfinderObject(esteem(p3), currentPoses.get(start).real++, currentPoses.get(start).real++ + esteem(p3), currentPoses.get(start)));
			getSmallestReal(p3);
			s3 = currentPoses.get(p3).sum;
			e3 = currentPoses.get(p3).estimate;

		}
		if ((!isObstrated(p4)) && currentPoses.get(p4) == null) {
			currentPoses.put(p4, new PathfinderObject(esteem(p4), currentPoses.get(start).real++, currentPoses.get(start).real++ + esteem(p4), currentPoses.get(start)));
			getSmallestReal(p4);
			s4 = currentPoses.get(p4).sum;
			e4 = currentPoses.get(p4).estimate;
		}
		//TODO next step
	}

	private boolean isObstrated(Pos p) {
		// TODO
		return false;
	}

	private Pos translatePos(Pos p, int x, int y) {
		return new Pos(p.getX() + x, p.getY() + y);
	}

	private int getSmallestReal(Pos p) {
		int real = Integer.MAX_VALUE;
		Pos p1 = translatePos(p, 1, 0), p2 = translatePos(p, -1, 0), p3 = translatePos(p, 0, 1), p4 = translatePos(p, 0, -1);
		if (currentPoses.get(p1) != null) {
			if (real >= currentPoses.get(p1).real) {
				real = currentPoses.get(p1).real;
				currentPoses.get(p).real = real;
				currentPoses.get(p).parent = currentPoses.get(p1);
			}
		}
		if (currentPoses.get(p2) != null) {
			if (real >= currentPoses.get(p2).real) {
				real = currentPoses.get(p2).real;
				currentPoses.get(p).real = real;
				currentPoses.get(p).parent = currentPoses.get(p2);
			}
		}
		if (currentPoses.get(p3) != null) {
			if (real >= currentPoses.get(p3).real) {
				real = currentPoses.get(p3).real;
				currentPoses.get(p).real = real;
				currentPoses.get(p).parent = currentPoses.get(p3);
			}
		}
		if (currentPoses.get(p4) != null) {
			if (real >= currentPoses.get(p4).real) {
				real = currentPoses.get(p4).real;
				currentPoses.get(p).real = real;
				currentPoses.get(p).parent = currentPoses.get(p4);
			}
		}
		return real;
	}

	private class PathfinderObject {

		private int estimate, real, sum;
		private PathfinderObject parent;

		private PathfinderObject(int estimate, int real, int sum, PathfinderObject parent) {
			this.estimate = estimate;
			this.real = real;
			this.sum = sum;
			this.parent = parent;
		}
	}
}
