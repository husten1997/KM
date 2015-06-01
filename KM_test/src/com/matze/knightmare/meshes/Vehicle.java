package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Vehicle extends Soldat {

	private int slots;
	private Soldat[] beladen;
	private int[] anzahl;
	private int slotGrösse;
	private boolean warenTransport = false, truppenTransport = false;

	public Vehicle(int h, int posx, int posy, int width, int height, String textur) {
		super(h, new Pos(posx, posy), width, height,  textur);
		typ = 3;
	}

	public void setSlots(int slot, int slotGröße, boolean warenT, boolean truppenT) {
		slots = slot;
		beladen = new Soldat[slots];
		anzahl = new int[slots];

		for (int i = 0; i < slots; i++) {
			beladen[i] = null;
		}
		
		slotGrösse = slotGröße;
		
		warenTransport = warenT;
		truppenTransport = truppenT;
	}

	public boolean addTruppe(Soldat truppe) {
		
		for (int i = 0; i < slots; i++){
			if (truppe.name.equals(beladen[i].name) && anzahl[i] < slotGrösse){
				anzahl[i]++;
				return true;
			}
		}
		
		for (int i = 0; i < slots; i++){
			if (beladen[i] == null){
				beladen[i] = truppe;
				anzahl[i]++;
				return true;
			}
		}
			return false;
	}

}
