package com.richard.knightmare.util;

import com.matze.knightmare.meshes.Soldat;

public class Vektor {

	private Pos start, ende;
	private Soldat soldat;
	private double x, y, m;

	public Vektor(Pos pos1, Pos pos2, Soldat soldat) {
		pos2 = new Pos(pos2.getX() - 16, pos2.getY() - 16);
		start = pos1;
		ende = pos2;
		x = ende.getX() - start.getX();
		y = ende.getY() - start.getY();
		m = x / y;
		this.soldat = soldat;
	}

	public Vektor(Pos pos1) {
		x = pos1.getX();
		y = pos1.getY();
	}

	public Vektor(double win, float l) {
		x = l * Math.cos(win);
		y = l * Math.sin(win);
	}

	public Vektor(double v1, double v2) {
		this.x = v1;
		this.y = v2;
	}

	public void setEnde(Pos ende) {
		ende = new Pos(ende.getX() - 16, ende.getY() - 16);
		this.ende = ende;
	}

	public double getLenght() {
		return Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Pos getNPos(Pos pos) {
		pos.setX(pos.getX() + x);
		pos.setY(pos.getY() + y);
		return pos;
	}

	public void setVektor(Pos pos1, Pos pos2) {
		x = pos2.getX() - pos1.getX();
		y = pos2.getY() - pos1.getY();
	}

	public Pos red(double xy, Pos pos) {

		if (getLenght() > 0) {
			if (x < 0) {
				x += xy;
			} else {
				x -= xy;
			}
			if (y < 0) {
				y += xy;
			} else {
				y -= xy;
			}
		}
		return getNPos(pos);
	}

	public double getM() {
		return y / x;
	}

	public Vektor middl(Vektor v1, Vektor v2) {
		double x1 = (v1.getX() + v2.getX()) / 2;
		double y1 = (v1.getY() + v2.getY()) / 2;
		return new Vektor(x1, y1);
	}

	public Soldat getSoldat() {
		return soldat;
	}

	public double distance(Pos p1, Pos p2) {
		return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
	}

	public boolean move() {
		double dx = soldat.getSpeed() / Math.sqrt((1.0 / (m * m)) + 1.0);
		double dy = soldat.getSpeed() / Math.sqrt(m * m + 1.0);
		Pos newStart;
		if (ende.getX() > start.getX()) {
			if (ende.getY() > start.getY()) {
				newStart = new Pos(start.getX() + dx, start.getY() + dy);
			} else {
				newStart = new Pos(start.getX() + dx, start.getY() - dy);
			}
		} else {
			if (ende.getY() > start.getY()) {
				newStart = new Pos(start.getX() - dx, start.getY() + dy);
			} else {
				newStart = new Pos(start.getX() - dx, start.getY() - dy);
			}
		}
		if (distance(newStart, ende) < distance(start, ende)) {
			soldat.setPos(newStart);
			start = newStart;
			x = ende.getX() - start.getX();
			y = ende.getY() - start.getY();
			m = x / y;
		} else {
			soldat.setPos(ende);
			return true;
		}
		return false;
	}

}