package com.richard.knightmare.serial;

import java.util.TimerTask;

import com.husten.knightmare.core.Knightmare;
import com.husten.knightmare.graphicalObjects.RectangleGraphicalObject;
import com.matze.knightmare.meshes.Bauen;
import com.matze.knightmare.meshes.Building;
import com.matze.knightmare.meshes.Rohstoffe;
import com.matze.knightmare.meshes.Soldat;
import com.richard.knightmare.util.Pos;

public class TimerTaskDistributer {

	public static void distribute(RectangleGraphicalObject object) {
		if (object instanceof Building) {
			((Building)object).setTimerTask(getTimerTask((Building) object));
			((Building)object).startTimer();
		} else if (object instanceof Soldat) {
			((Soldat) object).setTimerTask(((Soldat) object).getSold(), ((Soldat) object).getNahrung());
		}
	}

	private static TimerTask getTimerTask(Building b) {
		double d = b.getSpieler().getDifficulty();
		d = 2 - d;
		if (d == 0) {
			d = 0.5;
		}
		double hilfsd = d;
		int index = b.getIndex();
		if (!b.getSpieler().equals(Bauen.getMutterNatur())) {
			switch (index) {
			case 0:
				return new TimerTask() {

					@Override
					public void run() {
						b.getSpieler().verteilen(Rohstoffe.Kohle().getID(), (int) Math.round(1 * hilfsd));
					}

				};
			case 1:
				return new TimerTask() {

					@Override
					public void run() {
						// if (b.getBenötigt()[0].getAmount() > 0){
						// b.getSpieler().verteilen(Rohstoffe.Eisen().getID(),
						// 1);
						// //b.getBenötigt()[0].substractWare(1);
						// b.getSpieler().abziehen(Rohstoffe.Kohle().getID(),1);
						// }

						if (!(b.getSpieler().getAmountofResource(Rohstoffe.Kohle().getID()) - 1 < 0)) {
							b.getSpieler().verteilen(Rohstoffe.Eisen().getID(), (int) Math.round(1 * hilfsd));
							b.getSpieler().abziehen(Rohstoffe.Kohle().getID(), (int) Math.round(1 * hilfsd));
						}
					}

				};
			case 3:
				return new TimerTask() {

					@Override
					public void run() {
						Building k = Knightmare.newHandler.suchBaum((int) (b.getPosition().getX() / 32), (int) (b.getPosition().getY() / 32), b.getReichweite());

						if (k != null) {
							if (k.getBenötigt()[0].substractWare((int) Math.round(1 * hilfsd))) {
								b.getSpieler().verteilen(Rohstoffe.Holz().getID(), (int) Math.round(1 * hilfsd));
								if (k.getBenötigt()[0].getAmount() == (int) Math.round(1 * hilfsd)) {
									Knightmare.newHandler.die(k);
								}
							} else {
								// Pos pk = k.getPosition();
								Knightmare.newHandler.die(k);
								// Knightmare.newHandler.place(Baumstumpf(pk));//TODO
								// weil ged ned in dem Thread
								// k =
								// Knightmare.newHandler.suchBaum((int)p.getX(),
								// (int)p.getY(), b.getReichweite());
							}
						}
					}

				};
			case 4:
				return new TimerTask() {

					@Override
					public void run() {
						b.setProduktionProMinute(1);

						if (b.getSpieler().getAmountofResource(Rohstoffe.Fleisch().getID()) - 2 >= 0) {
							b.getSpieler().verteilen(Rohstoffe.Mensch().getID(), (int) Math.round(1 * hilfsd));
							b.getSpieler().verteilen(9, (int) Math.round(1 * hilfsd));
							b.getSpieler().abziehen(Rohstoffe.Fleisch().getID(), 3);
							Bauen.setGesBev(Bauen.getGesBev() + 1);
						} else {
							if (b.getSpieler().getAmountofResource(Rohstoffe.Mensch().getID()) > 1) {
								b.getSpieler().abziehen(Rohstoffe.Mensch().getID(), 1);
								Bauen.setGesBev(Bauen.getGesBev() - 1);
							}
						}

						// TODO ppm so ändern dass effektivität pro
						// gebäude um 50%
						// gesteigert wird, steht es alleine in einem
						// bestimmten
						// sektor, wird die produktion pro minute um 50%
						// gesenkt
						// (auch negativ möglich, dann sterben die
						// leute)
					}

				};
			case 5:
				return new TimerTask() {

					@Override
					public void run() {
						// if (benötigt[0]
						// .substractWare(amountBenötigt[0])
						// && (!(b.getAmountProduzierterWareAuslesen() == b
						// .getMaxLagerKap()))) {
						// b.WareFertigstellen();
						// }

						if (b.getSpieler().getAmountofResource(b.getBenötigt()[0].getID()) - 3 >= 0) {
							b.getSpieler().verteilen(b.getProduziert().getID(), (int) Math.round(1 * hilfsd));
							b.getSpieler().abziehen(b.getBenötigt()[0].getID(), 3);
						}
					}

				};
			case 6:
				return new TimerTask() {

					@Override
					public void run() {
						// if ((/* Felder in der Nähe */true) && (/*
						// * Felder
						// * hat
						// * ressourcen
						// */true) && (!(b
						// .getAmountProduzierterWareAuslesen() == b
						// .getMaxLagerKap()))) {
						// b.WareFertigstellen();
						// b.getSpieler().verteilen(Rohstoffe.Fleisch().getID(),
						// 1);
						// }

						Building k = Knightmare.newHandler.suchFeld((int) (b.getPosition().getX() / 32), (int) (b.getPosition().getY() / 32), b.getReichweite());

						if (k != null) {
							if (k.getBenötigt()[0].substractWare((int) Math.round(1 * hilfsd))) {
								b.getSpieler().verteilen(Rohstoffe.Getreide().getID(), (int) Math.round(1 * hilfsd));
								if (k.getBenötigt()[0].getAmount() == 0) {
									Knightmare.newHandler.die(k);
								}
							} else {
								// Pos pk = k.getPosition();
								b.getSpieler().verteilen(Rohstoffe.Getreide().getID(), k.getBenötigt()[0].getAmount());
								Knightmare.newHandler.die(k);
								// Knightmare.newHandler.place(Baumstumpf(pk));//TODO
								// weil ged ned in dem Thread
								// k =
								// Knightmare.newHandler.suchBaum((int)p.getX(),
								// (int)p.getY(), b.getReichweite());
							}
						}
					}

				};
			case 7:
				return new TimerTask() {

					@Override
					public void run() {
						// if (!(b.getAmountProduzierterWareAuslesen() == b
						// .getMaxLagerKap())) {
						// b.WareFertigstellen();
						// }
						b.getSpieler().verteilen(Rohstoffe.Fleisch().getID(), (int) Math.round(1 * hilfsd));
					}

				};
			case 8:
				return new TimerTask() {

					@Override
					public void run() {
						// if ((/* Stein in der Nähe */true) && (/*
						// * Stein hat
						// * ressourcen
						// */true) && (!(b
						// .getAmountProduzierterWareAuslesen() == b
						// .getMaxLagerKap()))) {
						// b.WareFertigstellen();
						// }
						b.getSpieler().verteilen(Rohstoffe.Stein().getID(), (int) Math.round(2 * hilfsd));
					}
				};
			case 17:
				return new TimerTask() {

					@Override
					public void run() {
						// if ((/* Stein in der Nähe */true) && (/*
						// * Stein hat
						// * ressourcen
						// */true) && (!(b
						// .getAmountProduzierterWareAuslesen() == b
						// .getMaxLagerKap()))) {
						// b.WareFertigstellen();
						// }
						if (b.getSpieler().getAmountofResource(Rohstoffe.Getreide().getID()) - 3 >= 0) {
							b.getSpieler().verteilen(Rohstoffe.Fleisch().getID(), (int) Math.round(2 * hilfsd));
							b.getSpieler().abziehen(Rohstoffe.Getreide().getID(), 3);
						}
					}
				};
			case 19:
				return new TimerTask() {

					@Override
					public void run() {
						if (b.getSpieler().getAmountofResource(b.getBenötigt()[0].getID()) - 4 >= 0) {
							b.getSpieler().verteilen(Rohstoffe.Armbrust().getID(), (int) Math.round(1 * hilfsd));
							b.getSpieler().abziehen(Rohstoffe.Eisen().getID(), 4);
						}
					}
				};
			case 21:
				return new TimerTask() {

					@Override
					public void run() {
						b.getSpieler().verteilen(Rohstoffe.Sand().getID(), (int) Math.round(1 * hilfsd));
					}

				};
			case 22:
				return new TimerTask() {

					@Override
					public void run() {
						Pos pos = Knightmare.newHandler.suchFrei((int) (b.getPosition().getX() / 32), (int) (b.getPosition().getY() / 32), b.getReichweite());

						if (pos != null) {
							if (b.getSpieler().possibleToRemove(Rohstoffe.Holz().getID(), 1) && b.getSpieler().possibleToRemove(Rohstoffe.Geld().getID(), 5)) {
								b.getSpieler().abziehen(Rohstoffe.Holz().getID(), 1);
								b.getSpieler().abziehen(Rohstoffe.Geld().getID(), 5);
								Knightmare.newHandler.place(Bauen.Baum(pos, Bauen.getMutterNatur()));
							}
						}
					}

				};
			case 23:
				return new TimerTask() {

					@Override
					public void run() {
						if (b.getSpieler().possibleToRemove(Rohstoffe.Mensch().getID(), 1)) {
							if (b.getSpieler().possibleToRemove(Rohstoffe.Fleisch().getID(), 5)) {
								int zufall = (int) Math.random() * 3;
								int zufall2 = (int) Math.random() * 3;
								if (b.getSpieler().possibleToRemove(zufall, zufall2)) {
									b.getSpieler().abziehen(Rohstoffe.Mensch().getID(), 1);
									b.getSpieler().verteilen(Rohstoffe.Gebildeter_Mensch().getID(), 1);
									b.getSpieler().abziehen(Rohstoffe.Fleisch().getID(), 5);
									b.getSpieler().abziehen(zufall, zufall2);
								}
							}
						}
					}

				};
			case 25:
				return new TimerTask() {

					@Override
					public void run() {
						if (b.getSpieler().possibleToRemove(Rohstoffe.Sand().getID(), 6)) {
							b.getSpieler().abziehen(Rohstoffe.Stein().getID(), 6);
							b.getSpieler().verteilen(Rohstoffe.Lehm().getID(), 3);
						}
					}

				};
			case 26:
				return new TimerTask() {

					@Override
					public void run() {
						if (b.getSpieler().possibleToRemove(Rohstoffe.Holz().getID(), 5) && b.getSpieler().possibleToRemove(Rohstoffe.Kohle().getID(), 1)) {
							b.getSpieler().abziehen(Rohstoffe.Holz().getID(), 5);
							b.getSpieler().abziehen(Rohstoffe.Kohle().getID(), 1);
							b.getSpieler().verteilen(Rohstoffe.Pech().getID(), 1);
						}
					}

				};
			case 27:
				return new TimerTask() {

					@Override
					public void run() {
						if (b.getSpieler().possibleToRemove(Rohstoffe.Eisen().getID(), 5) && b.getSpieler().possibleToRemove(Rohstoffe.Kohle().getID(), 2)) {
							b.getSpieler().abziehen(Rohstoffe.Holz().getID(), 5);
							b.getSpieler().abziehen(Rohstoffe.Kohle().getID(), 2);
							b.getSpieler().verteilen(Rohstoffe.Werkzeug().getID(), 1);
						}
					}

				};
			case 28:
				return new TimerTask() {

					@Override
					public void run() {

					}

				};
			case 29:
				return new TimerTask() {

					@Override
					public void run() {
						if (b.getSpieler().possibleToRemove(Rohstoffe.Lehm().getID(), 4)) {
							b.getSpieler().abziehen(Rohstoffe.Lehm().getID(), 4);
							b.getSpieler().verteilen(Rohstoffe.Ziegel().getID(), 2);
						}
					}

				};
			case 30:
				return new TimerTask() {

					@Override
					public void run() {
						if (b.getSpieler().possibleToRemove(Rohstoffe.Getreide().getID(), 2)) {
							b.getSpieler().abziehen(Rohstoffe.Getreide().getID(), 2);
							b.getSpieler().verteilen(Rohstoffe.Tiere().getID(), 1);
						}
					}

				};
			case 31:
				return new TimerTask() {

					@Override
					public void run() {
						if (b.getSpieler().possibleToRemove(Rohstoffe.Tiere().getID(), 2)) {
							b.getSpieler().abziehen(Rohstoffe.Getreide().getID(), 2);
							b.getSpieler().verteilen(Rohstoffe.Tiere().getID(), 1);
						}
					}

				};
			}
		}
		return null;
	}
}
