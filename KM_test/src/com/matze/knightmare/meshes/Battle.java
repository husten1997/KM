package com.matze.knightmare.meshes;

import com.richard.knightmare.util.Pos;

public class Battle {	
	
	public static void main(String[] args) {
		
		Soldat a1[] = new Soldat[100];
		Soldat a2[] = new Soldat[100];
		
		
		for (int i = 0; i < 10; i++){
			a1[i] = Rekrutieren.Baliste(0, 0, 0, 0);
			a2[i] = Rekrutieren.Armbrustschuetze(0, 0, 0, 0);
		}
		
		for (int i = 10; i < 50; i++){
			a2[i] = Rekrutieren.Abgesessener_Ritter(0, 0, 0, 0);
			a1[i] = Rekrutieren.Leichter_Lanz_Reiter(0, 0, 0, 0);
		}
		
		for (int i = 50; i < 100; i++){
			a1[i] = Rekrutieren.Bogenschuetze(0, 0, 0, 0);
			a2[i] = Rekrutieren.Hoplit(0, 0, 0, 0);
		}
		
		for (int i = 0; i < 100; i++){
			System.out.println(a1[i].name + " vs " + a2[i].name);
			System.out.println(kampf(a1[i], a2[i], 0).name);
		}


	}
	
	public static Soldat kampf(Soldat a, Soldat b, int modus){
		Soldat winner = new Soldat(0,new Pos(3,3),0,0,"this");
		winner.setName("Tie");
		
		while (a.health > 0 && b.health > 0) {
			if (b.getEffektivString().contains(a.getTypString())) {
				if (b.angriff[modus] + b.bonusAng - a.verteidigung[modus] > 0) {
					a.health -= b.angriff[modus] + a.bonusAng
							- a.verteidigung[modus];
					if (a.health <= 0) {
						winner = b;
					}
				}
			} else {
				if (b.angriff[modus] - a.verteidigung[modus] > 0) {
					a.health -= b.angriff[modus] - a.verteidigung[modus];
					if (a.health <= 0) {
						winner = b;
					}
				}
			}
			
			if (a.getEffektivString().contains(b.getTypString())) {
				if (a.angriff[modus] + a.bonusAng - b.verteidigung[modus] > 0) {
					b.health -= a.angriff[modus] + a.bonusAng
							- b.verteidigung[modus];
					if (b.health <= 0) {
						winner = a;
					}
				}
			} else {
				if (b.angriff[modus] - a.verteidigung[modus] > 0) {
					b.health -= a.angriff[modus] - b.verteidigung[modus];
					if (b.health <= 0) {
						winner = a;
					}
				}
			}
			
		}
		
		return winner;
	}

}
