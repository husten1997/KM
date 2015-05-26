package com.husten.knightmare.core;

import org.lwjgl.input.Keyboard;

public class MainMenue {

	public static void main(String[] args) {
		while(true){
			if(Keyboard.getEventKey() == Keyboard.KEY_S){
				new MainMenue().spielStarten();
			}
		}
	}
	
	private void spielStarten(){
		new Knightmare();
	}

}
