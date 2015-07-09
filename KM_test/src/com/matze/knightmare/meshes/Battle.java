package com.matze.knightmare.meshes;

import com.richard.knightmare.sound.SoundPlayer;
import com.richard.knightmare.util.Loader;

public class Battle {

	// public static void main(String[] args) {
	//
	// Soldat a1[] = new Soldat[100];
	// Soldat a2[] = new Soldat[100];
	//
	//
	// for (int i = 0; i < 10; i++){
	// a1[i] = Rekrutieren.Baliste(0, 0, 0, 0, "Spieler 1", 0);
	// a2[i] = Rekrutieren.Armbrustschuetze(0, 0, 0, 0, "Spieler 2", 0);
	// }
	//
	// for (int i = 10; i < 50; i++){
	// a2[i] = Rekrutieren.Abgesessener_Ritter(0, 0, 0, 0, "Spieler 1", 0);
	// a1[i] = Rekrutieren.Leichter_Lanz_Reiter(0, 0, 0, 0, "Spieler 2", 0);
	// }
	//
	// for (int i = 50; i < 100; i++){
	// a1[i] = Rekrutieren.Bogenschuetze(0, 0, 0, 0, "Spieler 1", 0);
	// a2[i] = Rekrutieren.Bogenschuetze(0, 0, 0, 0, "Spieler 2", 0);
	// }
	//
	// for (int i = 0; i < 100; i++){
	// System.out.println(a1[i].name + " vs " + a2[i].name);
	// System.out.println(kampf(a1[i], a2[i], 0).getSpieler());
	// }
	//
	//
	// }

	/*
	 * public static Soldat kampf(Soldat b, Soldat a, int modus){ Soldat looser
	 * = new Soldat(32, new Pos (0,0), 32, 32, "");
	 * 
	 * while (a.health > 0 && b.health > 0) { if
	 * (b.getEffektivString().contains(a.getTypString())) { a.health -= (int)
	 * (((b.angriff[modus] + b.bonusAng) / a.verteidigung[modus])+1); if
	 * (a.health <= 0) { looser = a; break; } } else { a.health -= (int)
	 * ((b.angriff[modus] / a.verteidigung[modus])+1); if (a.health <= 0) {
	 * looser = a; break; } }
	 * 
	 * if (a.getEffektivString().contains(b.getTypString())) { b.health -= (int)
	 * (((a.angriff[modus] + a.bonusAng) / b.verteidigung[modus])+1); if
	 * (b.health <= 0) { looser = b; break; } } else { b.health -= (int)
	 * ((a.angriff[modus] / b.verteidigung[modus])+1); if (b.health <= 0) {
	 * looser = b; break; } } }
	 * 
	 * return looser; }
	 */

	public static Soldat kampf(Soldat b, Soldat a, int modus) {
		SoundPlayer pl = new SoundPlayer("Swordclash.WAV");
		pl.setVolume(Float.parseFloat(Loader.getCfgValue("Volume")));
		pl.start();
		Soldat looser = null;
		if (b.getEffektivString().contains(a.getTypString())) {
			a.health -= (int) (((b.angriff[modus] + b.bonusAng) / a.verteidigung[modus]) + 1);
			if (a.health <= 0) {
				looser = a;
			}
		} else {
			a.health -= 
					(int) ((b.angriff[modus] 
							/ a.verteidigung[modus]) + 1);
			if (a.health <= 0) {
				looser = a;
			}
		}
		return looser;
	}

	public static Soldat[] massenKampf(Soldat[] ang, Soldat ver, int VerModus) {
		Soldat tod[] = new Soldat[ang.length + 1];
		Soldat nah = ang[0];
		int index = 0;

		for (int i = 0; i < ang.length; i++) {
			if (ang[i].getEffektivString().contains(ver.getTypString())) {
				ver.health -= (int) (((ang[i].angriff[ang[i].getTyp()] + ang[i].bonusAng) / ver.verteidigung[VerModus]) + 1);
			} else {
				ver.health -= (int) ((ang[i].angriff[ang[i].getTyp()] / ver.verteidigung[VerModus]) + 1);
			}

			if (ang[i].getTyp() == 0) {
				nah = ang[i];
			}

			if (ver.health <= 0) {
				tod[index] = ver;
				index++;
				return tod;
			}
		}

		if (ver.getEffektivString().contains(ver.getTypString())) {
			nah.health -= (int) (((ver.angriff[nah.getTyp()] + ver.bonusAng) / nah.verteidigung[0]));
		} else {
			ver.health -= (int) ((ver.angriff[nah.getTyp()] / nah.verteidigung[0]));
		}

		return tod;
	}

}
