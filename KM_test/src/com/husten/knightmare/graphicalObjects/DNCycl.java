package com.husten.knightmare.graphicalObjects;

import com.husten.knightmare.core.Knightmare;
import com.richard.knightmare.util.Loader;

import java.lang.Math;

public class DNCycl {
	
	private double time = Double.parseDouble(Loader.getCfgValue("SETTINGS: Startzeit")); //-12 bis 12
	private static double breightnes = 1;
	public static double red = 1;
	public static double green = 1;
	public static double blue = 1;
	private static int monat[] = new int[12];
	private static int tag = 0, aktuMon = 0;
	private static int jahr = 1100;
	
	private static boolean erstesMal = true;
	
	private double inter = 0.1; //Intervall --> 0.1x entspricht 1h
	private double gamespeed = 200; //durchläufe p sec
	
	private boolean run = true;
	
	public static int getAktuMon() {
		return aktuMon;
	}

	public static void setAktuMon(int aktuMon) {
		DNCycl.aktuMon = aktuMon;
	}

	public static int[] getMonat() {
		return monat;
	}

	public static void setMonat(int[] monat) {
		DNCycl.monat = monat;
	}

	public static int getTag() {
		return tag;
	}

	public static void setTag(int tag) {
		DNCycl.tag = tag;
	}

	public static int getJahr() {
		return jahr;
	}

	public static void setJahr(int jahr) {
		DNCycl.jahr = jahr;
	}
	
	
	public DNCycl(){
		
	}
	
	//@parameter secph --> sekunden pro stunde --> wie viele sekunden soll eine spielstunde dauern
	public void calc(int secph){
		if(run){
			double interv = inter/(secph*gamespeed);
			cTime(interv);
			breightnes = funkt3();
//			setGB();
			set();
//			debug();

		}
	}
	
	public void initDate(){
		String m[] = {"31","28","31","30","31","30", "31", "31", "30", "31", "30", "31"};
		
		if (jahr%4 == 0){
			m[1] = "29";
		}
		
		for (int i = 0; i < 12;i++){
			monat[i] = Integer.parseInt(m[i]);
		}
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
	
	private void set(){
		Knightmare.breightness = breightnes;
	}
	
	public void toggle(){
			run = !run;
	}
	
	@SuppressWarnings("unused")
	private double funkt1(){
		double h1 = Math.pow(time, 2);
		double h2 = -(h1/1.8);
		return Math.pow(Math.E, h2);
	}
	
	@SuppressWarnings("unused")
	private double funkt2(){
		return 0.5 * Math.sin(Math.cos(1.3 * time + 1.59) * 0.87 * Math.PI + 1.5 * Math.PI) + 0.54;
	}
	
	private double funkt3(){
		double min = 0.12;
		return (0.5- min/2) * Math.sin(time * Math.PI/1.2 + Math.PI/0.75) + (0.5 + min/2);
	}
	
	public double getTime(){
		return time;
	}
	
	public void setTime(double time){
		this.time = time;
	}
	
	public String getTimeS(){
		initDate();
		int h;
		double time_div;
		int mini;
		String min;
		if(time >= 0){
			h = (int) (time *10);
			time_div = time - ((double)h/10);
			mini = (int) ((time_div*10)*60);
		} else{
			h = 12 - ((int) (time *10)) * -1;
			time_div = (time + ((double)h/10)) * -1;
			mini = 60 - ((int) ((time_div*10)*60));
		}
		if(mini < 10){
			min = "0" + mini;
		} else{
			min = "" + mini;
		}
		
		if (h == 1){
			erstesMal = true;
		}
		
		if (erstesMal) {
			if (h == 0 && min.equals("00")) {
				tag++;
				erstesMal = false;
			}
			if (monat[aktuMon] == tag) {
				tag = 0;
				aktuMon++;
				erstesMal = false;
			}
			if (aktuMon > 11 && tag == 0) {
				aktuMon = 0;
				jahr++;
				erstesMal = false;
			}
		}
		
		String d = ""+(tag+1);
		String m = ""+(aktuMon+1);
		
		if (d.length()==1){
			d = "0"+d;
		}
		if (m.length()==1){
			m = "0"+m;
		}

		return "Heute ist der " + d + "." + m + "."+ jahr + " um " + h + ":" + min + " Uhr";
	}

	public static double getBreightnes() {
		return breightnes;
	}

	public static void setBreightnes(double breightnes) {
		DNCycl.breightnes = breightnes;
	}
	
	private double funkGBM(double x){
		return (Math.pow(x-0.75, 2))*240 - 0.6;
	}
	
	private double funkGBA(double x){
		return (Math.pow(x-2.5, 2))*240 - 0.6;
	}
	
	public void setGB(){
		if(time >= 0.7 && time <= 0.8){
			green = 1 - funkGBM(time);
			blue = 1 - funkGBM(time);
		}
		if(time >= 2.0 && time <= 2.1){
			green = 1 - funkGBA(time);
			blue = 1 - funkGBA(time);
		}
	}
	
}
