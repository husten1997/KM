package com.husten.knightmare.graphicalObjects;

import java.awt.Color;

import com.husten.knightmare.core.Knightmare;

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
			setTime(interv);
			double h1 = Math.pow(time, 2);
			double h2 = -(h1/1.8);
			breightnes = Math.pow(Math.E, h2);
//			System.out.println("B: " + breightnes + " X: " + time);
			set();
		}
	}
	
	private void set(){
		Knightmare.mainColor = new Color((int)(red*breightnes), (int)(green*breightnes), (int)(blue*breightnes));
	}
	
	private void setTime(double i){
		double h = time += i;
		if(h > 1.2){
			time = -1.2;
			time += i;
		} else{
			time += i;
		}
	}
	
	public void toggle(){
			run = !run;
	}

}
