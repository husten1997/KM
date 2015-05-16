package com.richard.knightmare.sound;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.richard.knightmare.util.Environment;

public class MusikPlayer {
	
	private Clip clip;
	private FloatControl ctrl;
	private Float volume;
	
	public MusikPlayer(String name){
		clip = Environment.getMusic(name);
		ctrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volume = ctrl.getValue();
	}
	
	public long start(){
		long lenght = clip.getMicrosecondLength()/1000000;
		clip.setFramePosition(0);
		clip.start();
		Timer ti = new Timer(true);
		ti.schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(lenght);
				} catch (InterruptedException e) {
					//Ignore
				}
			}
		}, 0);
		return lenght;
	}
	
	public void stop(){
		clip.stop();
	}
	
	public void changeVolume(Float change){
		try{
			ctrl.setValue(ctrl.getValue()+change);
		}catch(Exception e){
			//Limited between 6 and -80
		}
		volume = ctrl.getValue();
	}
	
	public void setVolume(Float volume){
		try{
			ctrl.setValue(volume);
		}catch(Exception e){
			//Limited between 6 and -80
		}
	}
	
	public void changeClip(String name){
		stop();
		clip = Environment.getMusic(name);
		ctrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		setVolume(volume);
	}
}
