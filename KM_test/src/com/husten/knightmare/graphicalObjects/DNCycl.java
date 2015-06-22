package com.husten.knightmare.graphicalObjects;

import java.awt.Color;

import com.husten.knightmare.core.Knightmare;

import java.lang.Math;

public class DNCycl {
	
	private double time = 0; //-12 bis 12
	private double breightnes = 1;
	private int red = 255;
	private int green = 255;
	private int blue = 255;
	
	private double inter = 0.1; //Intervall --> 0.1x entspricht 1h
	private double gamespeed = 100; //durchläufe p sec
	
	private boolean run = true;
	
	
	
	public DNCycl(){
		
	}
	//@parameter secph --> sekunden pro stunde --> wie viele sekunden soll eine spielstunde dauern
	public void calc(int secph){
		if(run){
			double interv = inter/(secph*gamespeed);
			cTime(interv);
			breightnes = funkt3();
			set();
			System.out.println(getTimeS());
		}
	}
	
	private void set(){
		Knightmare.mainColor = new Color((int)(red*breightnes), (int)(green*breightnes), (int)(blue*breightnes));
	}
	
	private void cTime(double i){
		double h = time += i;
		if(h > 2.4){
			time = 0;
			time += i;
		} else{
			time += i;
		}
	}
	
	public void toggle(){
			run = !run;
	}
	
	private double funkt1(){
		
		double h1 = Math.pow(time, 2);
		double h2 = -(h1/1.8);
		return Math.pow(Math.E, h2);
	}
	
	private double funkt2(){
		return 0.5 * Math.sin(Math.cos(1.3 * time + 1.59) * 0.87 * Math.PI + 1.5 * Math.PI) + 0.54;
	}
	
	private double funkt3(){
		double min = 0.05;
		return (0.5- min/2) * Math.sin(time * Math.PI/1.2 + Math.PI/0.67) + (0.5 + min/2);
	}
	
	public double getTime(){
		return time;
	}
	
	public void setTime(double time){
		this.time = time;
	}
	
	public String getTimeS(){
		int h;
		double time_div;
		int min;
		if(time >= 0){
			h = (int) (time *10);
			time_div = time - ((double)h/10);
			min = (int) ((time_div*10)*60);
		} else{
			h = 12 - ((int) (time *10)) * -1;
			time_div = (time + ((double)h/10)) * -1;
			min = 60 - ((int) ((time_div*10)*60));
		}
		return "" + h + ":" + min;
	}
	

}
