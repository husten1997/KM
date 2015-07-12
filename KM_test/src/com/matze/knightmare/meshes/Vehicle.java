package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Vehicle extends Soldat {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5973691387257683336L;
	@SuppressWarnings("unused")
	private Soldat[] beladen;
	@SuppressWarnings("unused")
	private int[] anzahl;
	private int slotGr�sse;
	private Waren[] slots;

	public Vehicle(int h, int posx, int posy, int width, int height,
			String textur) {
		super(h, new Pos(posx, posy), width, height, textur);
		typ = 3;
	}

	public void setSlots(int slot, int slotGr��e) {
		slots = new Waren[slot];
		for (int i = 0; i < slot; i++) {
			slots[i].setMaxAnz(slotGr��e);
		}
	}

	@SuppressWarnings("unused")
	public Waren addWare(Waren ware) {
		for (int i = 0; i < slots.length; i++) {
			if (ware.getID() == slots[i].getID()) {
				for (int s = 0; s < slotGr�sse; s++) {
					if (slots[s].getAmount() + 1 <= slotGr�sse) {
						ware.substractWare(1);
						slots[s].addWare(slotGr�sse);
						return ware;
					}
				}
			}
		}
		for (int i = 0; i < slots.length; i++) {
			for (int f = 0; f < slots.length; f++) {
				if (slots[f] == null) {
					slots[f] = ware;
					slots[f].setAmount(0);
					for (int t = 0; t < slotGr�sse; t++) {
						slots[f].addWare(slotGr�sse);
						ware.substractWare(1);
					}
					return ware;
				} else {
					System.out.println("Kann nichts ablegen");
					return ware;
				}
			}
		}
		return ware;
	}
	public Waren[] getSlots(){
		return slots;
	}
}
