package com.matze.knightmare.meshes;

import java.util.ArrayList;

import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;

public class Spieler {

	// private int amountResourcesOfIndex[] = new int[Rohstoffe.maxID()];
	private int team;
	private int index;
	private String typ;
	private int difficulty;
	private String name;
	private ArrayList<Building> lager = new ArrayList<>(),
			schatzkammern = new ArrayList<>(),
			waffenkammer = new ArrayList<>(), kornspeicher = new ArrayList<>(),
			marktplätze = new ArrayList<>();

	public boolean hatLager() {
		return lager.size() > 0;
	}
	
	public int hatWievieleLager() {
		return lager.size();
	}

	public boolean hatMarktplatz() {
		return marktplätze.size() > 0;
	}
	
	public int hatWievieleMarktplatz() {
		return marktplätze.size();
	}

	public boolean hatKornspeicher() {
		return kornspeicher.size() > 0;
	}
	
	public int hatWievieleKornspeicher() {
		return kornspeicher.size();
	}

	public Spieler(int SpielerNR, String name, int team, String typ,
			String schwierigkeit) {
		this.setTeam(team);
		this.setName(name);
		this.index = SpielerNR;
		this.setTyp(typ);

		for (int i = 0; i < 3; i++) {
			if (schwierigkeit.equals("Leicht")) {
				setDifficulty(0);
			}
			if (schwierigkeit.equals("Mittel")) {
				setDifficulty(1);
			}
			if (schwierigkeit.equals("Schwer")) {
				setDifficulty(2);
			}
		}

		// amountResourcesOfIndex[getIndexofResource("Holz")] = 30;
		// amountResourcesOfIndex[getIndexofResource("Kohle")] = 5;
		// amountResourcesOfIndex[getIndexofResource("Stein")] = 15;
	}

	// Rohstoffe
	// public Waren setAmountofResourcewithIndex(int amount, int index) {
	// amountResourcesOfIndex[index] = amount;
	// return Rohstoffe.Rohstoff_von_Index(index);
	// }

	public void verteilen(int warenID, int amount) {
		if (warenID == 9) {
			for (Building s : schatzkammern) {
				int frei = s.getMaxLagerKap();
				for (Waren ware : s.getBenötigt()) {
					frei -= ware.getAmount();
				}
				int ablegen = Math.min(frei, amount);
				s.setWarenAmount(warenID, ablegen);
				amount -= ablegen;
				if (amount == 0) {
					return;
				}
			}
		}
		if (warenID == 10) {
			for (Building k : kornspeicher) {
				int frei = k.getMaxLagerKap();
				for (Waren ware : k.getBenötigt()) {
					frei -= ware.getAmount();
				}
				int ablegen = Math.min(frei, amount);
				k.setWarenAmount(warenID, ablegen);
				amount -= ablegen;
				if (amount == 0) {
					return;
				}
			}
		}
		if (warenID == 12) {
			for (Building m : marktplätze) {
				int frei = m.getMaxLagerKap();
				for (Waren ware : m.getBenötigt()) {
					frei -= ware.getAmount();
				}
				int ablegen = Math.min(frei, amount);
				m.setWarenAmount(warenID, ablegen);
				amount -= ablegen;
				if (amount == 0) {
					return;
				}
			}
		}
		if (warenID == 13) {
			for (Building w : waffenkammer) {
				int frei = w.getMaxLagerKap();
				for (Waren ware : w.getBenötigt()) {
					frei -= ware.getAmount();
				}
				int ablegen = Math.min(frei, amount);
				w.setWarenAmount(warenID, ablegen);
				amount -= ablegen;
				if (amount == 0) {
					return;
				}
			}
		}
		for (Building l : lager) {
			int frei = l.getMaxLagerKap();
			for (Waren ware : l.getBenötigt()) {
				frei -= ware.getAmount();
			}
			int ablegen = Math.min(frei, amount);
			l.setWarenAmount(warenID, ablegen);
			amount -= ablegen;
			if (amount == 0) {
				break;
			}
		}
	}

	public void abziehen(int warenID, int amount) {
		if (warenID == 9) {
			for (Building s : schatzkammern) {
				int drin = s.getBenötigt()[warenID].getAmount();
				int abziehen = Math.min(drin, amount);
				s.deminishWarenAmount(warenID, abziehen);
				amount -= abziehen;
				if (amount == 0) {
					return;
				}
			}
		}
		if (warenID == 10) {
			for (Building k : kornspeicher) {
				int drin = k.getBenötigt()[warenID].getAmount();
				int abziehen = Math.min(drin, amount);
				k.deminishWarenAmount(warenID, abziehen);
				amount -= abziehen;
				if (amount == 0) {
					return;
				}
			}
		}
		if (warenID == 12) {
			for (Building m : marktplätze) {
				int drin = m.getBenötigt()[warenID].getAmount();
				int abziehen = Math.min(drin, amount);
				m.deminishWarenAmount(warenID, abziehen);
				amount -= abziehen;
				if (amount == 0) {
					return;
				}
			}
		}
		if (warenID == 13) {
			for (Building w : waffenkammer) {
				int drin = w.getBenötigt()[warenID].getAmount();
				int abziehen = Math.min(drin, amount);
				w.deminishWarenAmount(warenID, abziehen);
				amount -= abziehen;
				if (amount == 0) {
					return;
				}
			}
		}
		for (Building l : lager) {
			int drin = l.getBenötigt()[warenID].getAmount();
			int abziehen = Math.min(drin, amount);
			l.deminishWarenAmount(warenID, abziehen);
			amount -= abziehen;
			if (amount == 0) {
				break;
			}
		}
	}

	// public Waren setAmountofResourcewithName(int amount, String name) {
	// int index = getIndexofResource(name);
	// amountResourcesOfIndex[index] = amount;
	// return Rohstoffe.Rohstoff_von_Index(index);
	// }

	public int getAmountofResource(int index) {
		int help = 0;
		if (index == 9) {
			for (Building Schatzkammer : schatzkammern) {
				help += Schatzkammer.getBenötigt()[index].getAmount();
			}
			return help;
		}
		if (index == 10) {
			for (Building Kornspeicher : kornspeicher) {
				help += Kornspeicher.getBenötigt()[index].getAmount();
			}
			return help;
		}
		if (index == 12) {
			for (Building marktplatz : marktplätze) {
				help += marktplatz.getBenötigt()[index].getAmount();
			}
			return help;
		}
		if (index == 13) {
			for (Building Waffenkammer : waffenkammer) {
				help += Waffenkammer.getBenötigt()[index].getAmount();
			}
			return help;
		}
		for (Building Lager : lager) {
			help += Lager.getBenötigt()[index].getAmount();
		}
		return help;/* amountResourcesOfIndex[index]; */
	}

	public int getIndexofResource(String name) {
		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (name.equals(Rohstoffe.Rohstoff_von_Index(i).getName())) {
				return i;
			}
		}
		System.out.println(name + ": Resource nicht vorhanden");
		return -1;
	}

	// getter und setter
	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public void addLager(RectangleGraphicalObject rgo) {
		lager.add((Building) rgo);
	}

	public void removeLager(RectangleGraphicalObject rgo) {
		lager.remove((Building) rgo);
	}

	public void addSchatzkammer(RectangleGraphicalObject rgo) {
		schatzkammern.add((Building) rgo);
	}

	public void removeSchatzkammer(RectangleGraphicalObject rgo) {
		schatzkammern.remove((Building) rgo);
	}

	public void addWaffenkammer(RectangleGraphicalObject rgo) {
		waffenkammer.add((Building) rgo);
	}

	public void removeWaffenkammer(RectangleGraphicalObject rgo) {
		waffenkammer.remove((Building) rgo);
	}

}
