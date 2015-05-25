package com.husten.knightmare.graphicalObjects;

import com.husten.knightmare.constants.StringConstants;
import com.richard.knightmare.util.Pos;

public abstract class gasset implements StringConstants {
	private int sort = 1;

	protected String type = MeshType.GROUND;

	public void setSort(int s) {
		sort = s;
	}

	public int getSort() {
		return sort;
	}

	public abstract void draw();

	public abstract void draw2();

	public abstract float getX();

	public abstract float getY();

	public abstract void setX(float x);

	public abstract void setY(float y);

	public abstract Pos getPos();

	public abstract void setPos(Pos p);

	public String getType() {
		return type;
	}

}
