package com.husten.knightmare.threads;

import com.husten.knightmare.core.Knightmare;

public class GrafikThread extends WorkingThread {
	private Knightmare game;

	public GrafikThread(Knightmare game) {
		this.game = game;
	}

	@Override
	public void run() {
		// while(true){
		game.grafikCycl();
		// }

	}

}
