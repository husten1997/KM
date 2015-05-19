package res.thread;

import ui.KTM_Game_Main;

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
