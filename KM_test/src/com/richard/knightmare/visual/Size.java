package com.richard.knightmare.visual;

import java.awt.Dimension;

public class Size {

	public static Dimension getSize(int index){
		switch (index) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 5:
		case 7:
		case 8:
		case 9:
		case 13:
		case 15:
		case 17:
		case 18:
		case 32:
			return new Dimension(64,64);
		case 4:
		case 12:
			return new Dimension(64, 32);
		case 6:
			return new Dimension(128, 128);
		default:
			return new Dimension(32, 32);
		}
	}
}
