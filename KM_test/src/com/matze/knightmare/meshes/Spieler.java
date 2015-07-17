package com.matze.knightmare.meshes;

import java.io.Serializable;
import java.util.ArrayList;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.richard.knightmare.util.Pos;

public class Spieler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6857734496210559804L;
	private int team;
	private int index;
	private String typ;
	private int difficulty;
	private String name;
	private ArrayList<Building> lager = new ArrayList<>(),
			schatzkammern = new ArrayList<>(),
			waffenkammer = new ArrayList<>(), kornspeicher = new ArrayList<>(),
			marktpl�tze = new ArrayList<>();
	private ArrayList<Forschung> forschung = new ArrayList<>();
	private int gesinnung[];

	public boolean hatLager() {
		return lager.size() > 0;
	}
	
	public int hatWievieleLager() {
		return lager.size();
	}

	public boolean hatMarktplatz() {
		return marktpl�tze.size() > 0;
	}
	
	public int hatWievieleMarktplatz() {
		return marktpl�tze.size();
	}

	public boolean hatKornspeicher() {
		return kornspeicher.size() > 0;
	}
	
	public int hatWievieleKornspeicher() {
		return kornspeicher.size();
	}
	
	public void setAmountofResource(int index, int am){
		if (index == 9) {
			for (Building Schatzkammer : schatzkammern) {
				Schatzkammer.getBen�tigt()[index].setAmount(am);
			}		}
		if (index == 10) {
			for (Building Kornspeicher : kornspeicher) {
				Kornspeicher.getBen�tigt()[index].setAmount(am);
			}		}
		if (index == 12) {
			for (Building marktplatz : marktpl�tze) {
				marktplatz.getBen�tigt()[index].setAmount(am);
			}
		}
		if (index == 13) {
			for (Building Waffenkammer : waffenkammer) {
				Waffenkammer.getBen�tigt()[index].setAmount(am);
			}
		}
		for (Building Lager : lager) {
			Lager.getBen�tigt()[index].setAmount(am);
		}
	}

	public Spieler(int SpielerNR, String name, int team, String typ,
			String schwierigkeit, int an) {
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
		
		gesinnung = new int[an];
		gesinnung[0]  = 100;
	}
	
	public int getGesinnungGegen�berVonSpieler(int id){
		return gesinnung[id];
	}
	
	public void setGesinnungGegen�berVonSpieler(int id, int ges){
		gesinnung[id] = ges;
	}
	
	public void �ndernGesinnungGegen�berVonSpieler(int id, int ges){
		gesinnung[id] += ges;
	}

	public void verteilen(int warenID, int amount) {
		if (warenID == 9) {
			for (Building s : schatzkammern) {
				int frei = s.getMaxLagerKap();
				for (Waren ware : s.getBen�tigt()) {
					frei -= ware.getAmount();
				}
				int ablegen = Math.min(frei, amount);
				s.setWarenAmount(warenID, ablegen);
				amount -= ablegen;
				if (amount == 0) {
					return;
				}
			}
			return;
		}
		if (warenID == 10) {
			for (Building k : kornspeicher) {
				int frei = k.getMaxLagerKap();
				for (Waren ware : k.getBen�tigt()) {
					frei -= ware.getAmount();
				}
				int ablegen = Math.min(frei, amount);
				k.setWarenAmount(warenID, ablegen);
				amount -= ablegen;
				if (amount == 0) {
					return;
				}
			}
			return;
		}
		if (warenID == 12) {
			for (Building m : marktpl�tze) {
				int frei = m.getMaxLagerKap();
				for (Waren ware : m.getBen�tigt()) {
					frei -= ware.getAmount();
				}
				int ablegen = Math.min(frei, amount);
				m.setWarenAmount(warenID, ablegen);
				amount -= ablegen;
				if (amount == 0) {
					return;
				}
			}
			return;
		}
		if (warenID == 13) {
			for (Building w : waffenkammer) {
				int frei = w.getMaxLagerKap();
				for (Waren ware : w.getBen�tigt()) {
					frei -= ware.getAmount();
				}
				int ablegen = Math.min(frei, amount);
				w.setWarenAmount(warenID, ablegen);
				amount -= ablegen;
				if (amount == 0) {
					return;
				}
			}
			return;
		}
		for (Building l : lager) {
			int frei = l.getMaxLagerKap();
			for (Waren ware : l.getBen�tigt()) {
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
				int drin = s.getBen�tigt()[warenID].getAmount();
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
				int drin = k.getBen�tigt()[warenID].getAmount();
				int abziehen = Math.min(drin, amount);
				k.deminishWarenAmount(warenID, abziehen);
				amount -= abziehen;
				if (amount == 0) {
					return;
				}
			}
		}
		if (warenID == 12) {
			for (Building m : marktpl�tze) {
				int drin = m.getBen�tigt()[warenID].getAmount();
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
				int drin = w.getBen�tigt()[warenID].getAmount();
				int abziehen = Math.min(drin, amount);
				w.deminishWarenAmount(warenID, abziehen);
				amount -= abziehen;
				if (amount == 0) {
					return;
				}
			}
		}
		for (Building l : lager) {
			int drin = l.getBen�tigt()[warenID].getAmount();
			int abziehen = Math.min(drin, amount);
			l.deminishWarenAmount(warenID, abziehen);
			amount -= abziehen;
			if (amount == 0) {
				break;
			}
		}
	}

	public int getAmountofResource(int index) {
		int help = 0;
		if (index == 9) {
			for (Building Schatzkammer : schatzkammern) {
				help += Schatzkammer.getBen�tigt()[index].getAmount();
			}
			return help;
		}
		if (index == 10) {
			for (Building Kornspeicher : kornspeicher) {
				help += Kornspeicher.getBen�tigt()[index].getAmount();
			}
			return help;
		}
		if (index == 12) {
			for (Building marktplatz : marktpl�tze) {
				help += marktplatz.getBen�tigt()[index].getAmount();
			}
			return help;
		}
		if (index == 13) {
			for (Building Waffenkammer : waffenkammer) {
				help += Waffenkammer.getBen�tigt()[index].getAmount();
			}
			return help;
		}
		for (Building Lager : lager) {
			help += Lager.getBen�tigt()[index].getAmount();
		}
		return help;
	}

	public int getIndexofResource(String name) {
		for (int i = 0; i < Rohstoffe.maxID(); i++) {
			if (name.equals(Rohstoffe.Rohstoff_von_Index(i).getName())) {
				return i;
			}
		}
		return -1;
	}
	
	public void addForschung(Forschung f){
		forschung.add(f);
	}
	
	public boolean hatForschung(Forschung f){
		for (Forschung a: forschung){
			if (a.equals(f)){
				return true;
			}
		}
		return false;
		
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

	public void addKornspeicher(RectangleGraphicalObject rgo) {
		kornspeicher.add((Building) rgo);
	}

	public void removeKornspeicher(RectangleGraphicalObject rgo) {
		kornspeicher.remove((Building) rgo);
	}

	public void addMarktplatz(RectangleGraphicalObject rgo) {
		marktpl�tze.add((Building) rgo);
	}

	public void removeMarktplatz(RectangleGraphicalObject rgo) {
		marktpl�tze.remove((Building) rgo);
	}
	
	public boolean possibleToRemove(int index, int amount){
		if (getAmountofResource(index) - amount >= 0){
			return true;
		}
		return false;
	}

	public Pos findFreeNearMarkt(){
		for(Building markt: marktpl�tze){
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)-1, (int)(markt.getPosition().getY()/32)-1)==null){
				return new Pos((int)(markt.getPosition().getX()/32)-1, (int)(markt.getPosition().getY()/32)-1);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)-1, (int)(markt.getPosition().getY()/32))==null){
				return new Pos((int)(markt.getPosition().getX()/32)-1, (int)(markt.getPosition().getY()/32));
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)-1, (int)(markt.getPosition().getY()/32)+1)==null){
				return new Pos((int)(markt.getPosition().getX()/32)-1, (int)(markt.getPosition().getY()/32)+1);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)-1, (int)(markt.getPosition().getY()/32)+2)==null){
				return new Pos((int)(markt.getPosition().getX()/32)-1, (int)(markt.getPosition().getY()/32)+2);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32), (int)(markt.getPosition().getY()/32)+2)==null){
				return new Pos((int)(markt.getPosition().getX()/32), (int)(markt.getPosition().getY()/32)+2);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)+1, (int)(markt.getPosition().getY()/32)+2)==null){
				return new Pos((int)(markt.getPosition().getX()/32)+1, (int)(markt.getPosition().getY()/32)+2);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)+2, (int)(markt.getPosition().getY()/32)+2)==null){
				return new Pos((int)(markt.getPosition().getX()/32)+2, (int)(markt.getPosition().getY()/32)+2);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)+2, (int)(markt.getPosition().getY()/32)+1)==null){
				return new Pos((int)(markt.getPosition().getX()/32)+2, (int)(markt.getPosition().getY()/32)+1);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)+2, (int)(markt.getPosition().getY()/32))==null){
				return new Pos((int)(markt.getPosition().getX()/32)+2, (int)(markt.getPosition().getY()/32));
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)+2, (int)(markt.getPosition().getY()/32)-1)==null){
				return new Pos((int)(markt.getPosition().getX()/32)+2, (int)(markt.getPosition().getY()/32)-1);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32)+1, (int)(markt.getPosition().getY()/32)-1)==null){
				return new Pos((int)(markt.getPosition().getX()/32)+1, (int)(markt.getPosition().getY()/32)-1);
			}
			if(Knightmare.newHandler.getOn((int)(markt.getPosition().getX()/32), (int)(markt.getPosition().getY()/32)-1)==null){
				return new Pos((int)(markt.getPosition().getX()/32), (int)(markt.getPosition().getY()/32)-1);
			}
		}
		return null;
	}
}
