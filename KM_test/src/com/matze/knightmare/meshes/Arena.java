package com.matze.knightmare.meshes;

public class Arena {

	private Armee a1, a2;
	private int angr[], vert[];

	public Arena() {
		angr = new int[3];
		vert = new int[3];

		a1 = new Armee();
		a2 = new Armee();

		for (int i = 0; i < 50; i++) {
			init(a1, (int) (Math.random() * 3));
		}

		for (int i = 0; i < 50; i++) {
			init(a2, (int) (Math.random() * 3));
			System.out.println(i + " " + a1.getTroop(i).name + " vs. " + a2.getTroop(i).name);
		}

		fight();
	}

	public void fight() {
		for (int i = 0; i < 50; i++) {
			while (a1.getTroop(i).verteidigung[0] > 0 && a2.getTroop(i).verteidigung[0] > 0) {
				if (a2.getTroop(i).verteidigung[0] > 0)
					a1.getTroop(i).verteidigung[0] -= a2.getTroop(i).angriff[0];
				if (a1.getTroop(i).verteidigung[0] > 0)
					a2.getTroop(i).verteidigung[0] -= a1.getTroop(i).angriff[0];
			}
		}

		int ar1 = 0;
		int ar2 = 0;

		for (int i = 0; i < 50; i++) {
			if (a1.getTroop(i).verteidigung[0] < 0)
				ar1++;
			else
				ar2++;
		}

		if (ar1 > ar2) {
			System.out.println("Armee 1 hat gewonnen " + ar1 + ":" + ar2);
		} else {
			System.out.println("Armee 1 hat gewonnen " + ar1 + ":" + ar2);
		}

	}

	public void init(Armee a, int typ) {
		Kavallerie k = new Kavallerie();
		Artillerie at = new Artillerie();
		Infanterie i = new Infanterie();

		fillAngrVert();

		k.init(angr, vert, 20, "Kav", 10, 30, 15, 40, 55);
		at.init(angr, vert, 30, "Art", 100, 10, 100, 50, 40);
		i.init(angr, vert, 20, "Infa", 5, 20, 25, 35, 35);

		switch (typ) {
		case 0: {
			a.addTroop(k);
			break;
		}
		case 1: {
			a.addTroop(at);
			break;
		}
		case 2: {
			a.addTroop(i);
			break;
		}
		default: {
			a.addTroop(i);
			break;
		}
		}
	}

	public void fillAngrVert() {
		for (int a = 0; a < 2; a++) {
			for (int i = 0; i < 3; i++) {
				if (a == 0) {
					angr[i] = (int) (Math.random() * 50) + 20;
				} else {
					vert[i] = (int) (Math.random() * 50) + 20;
				}
			}
		}
	}

	public static void main(String[] argv) {
		new Arena();
	}

}
