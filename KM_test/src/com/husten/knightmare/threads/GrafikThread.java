package com.husten.knightmare.threads;

import com.husten.knightmare.core.KTM_Game_Main;

public class GrafikThread extends WorkingThread{
	KTM_Game_Main game;
	
	public GrafikThread(KTM_Game_Main game){
		this.game = game;
	}

	@Override
	public void run() {
//		while(true){
			game.grafikCycl();
//		}
		
		
	}
	
	

}
