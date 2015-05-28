package com.richard.knightmare.util;

public abstract class Button {

	private Pos p1, p2;
	
	public Button(Pos p1, Pos p2){
		this.setP1(p1);
		this.setP2(p2);
	}
	
	public abstract void onClick();

	public Pos getP2() {
		return p2;
	}

	public void setP2(Pos p2) {
		this.p2 = p2;
	}

	public Pos getP1() {
		return p1;
	}

	public void setP1(Pos p1) {
		this.p1 = p1;
	}
}
