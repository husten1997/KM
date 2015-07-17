package com.matze.knightmare.meshes;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;

public class Battle {

	public static Soldat kampf(Soldat b, Soldat a, int modus) {
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
	
	public static RectangleGraphicalObject kampf(Soldat b, Building a, int modus) {
		RectangleGraphicalObject looser = null;
		
		a.setHealth(a.getHealth()-b.angriff[modus]);
		
		if (a.getHealth() <= 0){
			looser = a;
		}
		
		b.health -= a.getAngriff(); 
		
		if (b.health <= 0){
			looser = b;
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
