package com.richard.knightmare.util;

import com.husten.knightmare.threads.WorkingThread;

public class GrafikThread extends WorkingThread {
	private Test game;

	public GrafikThread(Test game) {
		this.game = game;
	}

	@Override
	public void run() {
		// while(true){
		game.grafikCycl();
		// }

	}

}
