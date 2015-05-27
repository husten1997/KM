package com.matze.knightmare.meshes;

public class Vehicle extends Truppen {

	private int slots;
	private Truppen[] beladen;
	private int[] anzahl;
	private int slotGr�sse;
	private boolean warenTransport = false, truppenTransport = false;

	public Vehicle() {
		typ = 3;
	}

	public void setSlots(int slot, int slotGr��e, boolean warenT, boolean truppenT) {
		slots = slot;
		beladen = new Truppen[slots];
		anzahl = new int[slots];

		for (int i = 0; i < slots; i++) {
			beladen[i] = null;
		}
		
		slotGr�sse = slotGr��e;
		
		warenTransport = warenT;
		truppenTransport = truppenT;
	}

	public boolean addTruppe(Truppen truppe) {
		
		for (int i = 0; i < slots; i++){
			if (truppe.name.equals(beladen[i].name) && anzahl[i] < slotGr�sse){
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
