package com.richard.knightmare.buttons;

public abstract class Button {

	private String textur;

	public Button() {

	}

	public Button(String textur) {
		this.textur = textur;
	}

	public String getTextur() {
		return textur;
	}

	public abstract void onClick();

}
