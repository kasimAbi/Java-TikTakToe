package tiktaktoe;

import java.util.Arrays;

public class Playground {
	private String[][] spielfeld = new String[3][3];
	private String freiesFeld = "*";
	
	public Playground() {	// Konstruktor
		spielfeldLeeren();		// beim objekt erstellen soll spielfeld geleert werden. 
	}
	
	public void spielfeldLeeren() {
		Arrays.fill(spielfeld[0], freiesFeld);		// zeile 0 wird mit freiesFeld-Symbol überschrieben/ gefüllt
		Arrays.fill(spielfeld[1], freiesFeld);		// zeile 1 wird mit freiesFeld-Symbol überschrieben/ gefüllt
		Arrays.fill(spielfeld[2], freiesFeld);		// zeile 2 wird mit freiesFeld-Symbol überschrieben/ gefüllt
	}
	
	public boolean setzeSymbol(String symbol, String feld) {
		switch(feld) {		// die zahlen zB. 0, 0 sind die 2d-Array koordinaten.
		case "q1": if(isAble(0, 0, symbol)) { return true; } break;		// isAble kontrolliert ob das Feld zulässt das man setzen kann. wenn gesetzt werden konnte, wird true zurückgegeben
		case "q2": if(isAble(1, 0, symbol)) {  return true; } break;	// und das programm gibt dann ebenfalls true zurück.
		case "q3": if(isAble(2, 0, symbol)) {  return true; } break;	
		case "w1": if(isAble(0, 1, symbol)) {  return true; } break;
		case "w2": if(isAble(1, 1, symbol)) {  return true; } break;
		case "w3": if(isAble(2, 1, symbol)) {  return true; } break;
		case "e1": if(isAble(0, 2, symbol)) {  return true; } break;
		case "e2": if(isAble(1, 2, symbol)) {  return true; } break;
		case "e3": if(isAble(2, 2, symbol)) {  return true; } break;
		default: 
		}
		return false;	// wenn isAble false zurückgibt, wird false zurückgegeben.
	}
	
	private boolean isAble(int row, int column, String symbol) {
		if(spielfeld[row][column].equals(freiesFeld)) {		// kontrolliert ob an der übergebenen parameter zeile und spalte des 2d arrays ein freiesFeld ist. Also ob man setzen kann.
			spielfeld[row][column] = symbol;		// wenn gesetzt werden kann, wird symbol in die koordinate gespeichert.
			return true;		// und anschließend ein true zurückgegeben.
		}
		return false;		// wenn nicht gesetzt werden kann, wird ein false zurückgegeben.
	}
	
	public int winnable() {
		// links nach rechts und oben nach unten prüfen.
		for(int index = 0; index < 3; index++) {
			// links nach rechts kontrolle (zeilen). 2 felder die nebeneinander liegen werden kontrolliert ob es gleiche symbole sind. und dann das dritte feld ob das ein freiesFeld ist.
			if(spielfeld[index][0].equals(spielfeld[index][1]) && !spielfeld[index][0].equals(freiesFeld) && spielfeld[index][2].equals(freiesFeld)) {
				return 3 + (index * 3);
			}
			else if(spielfeld[index][0].equals(spielfeld[index][2]) && !spielfeld[index][0].equals(freiesFeld)&& spielfeld[index][1].equals(freiesFeld)) {
				return 2 + (index * 3);
			}
			else if(spielfeld[index][1].equals(spielfeld[index][2]) && !spielfeld[index][1].equals(freiesFeld)&& spielfeld[index][0].equals(freiesFeld)) {
				return 1 + (index * 3);
			}
			// oben nach unten kontrolle (spalten). 2 felder die untereinander liegen werden kontrolliert ob es gleiche symbole sind. und dann das dritte feld ob das ein freiesFeld ist.
			else if(spielfeld[0][index].equals(spielfeld[1][index]) && !spielfeld[0][index].equals(freiesFeld)&& spielfeld[2][index].equals(freiesFeld)) {
				return 7 + index;
			}
			else if(spielfeld[0][index].equals(spielfeld[2][index]) && !spielfeld[0][index].equals(freiesFeld)&& spielfeld[1][index].equals(freiesFeld)) {
				return 4 + index;
			}
			else if(spielfeld[1][index].equals(spielfeld[2][index]) && !spielfeld[1][index].equals(freiesFeld)&& spielfeld[0][index].equals(freiesFeld)) {
				return 1 + index;
			}
		}
		// oben diagonal nach unten kontrolle.
		if(spielfeld[0][0].equals(spielfeld[1][1]) && !spielfeld[0][0].equals(freiesFeld)&& spielfeld[2][2].equals(freiesFeld)) {
			return 9;
		}
		else if(spielfeld[0][0].equals(spielfeld[2][2]) && !spielfeld[0][0].equals(freiesFeld)&& spielfeld[1][1].equals(freiesFeld)) {
			return 5;
		}
		else if(spielfeld[1][1].equals(spielfeld[2][2]) && !spielfeld[1][1].equals(freiesFeld)&& spielfeld[0][0].equals(freiesFeld)) {
			return 1;
		}
		// unten diagonal nach oben kontrolle
		else if(spielfeld[0][2].equals(spielfeld[1][1]) && !spielfeld[0][2].equals(freiesFeld)&& spielfeld[2][0].equals(freiesFeld)) {
			return 7;
		}
		else if(spielfeld[0][2].equals(spielfeld[2][0]) && !spielfeld[0][2].equals(freiesFeld)&& spielfeld[1][1].equals(freiesFeld)) {
			return 5;
		}
		else if(spielfeld[1][1].equals(spielfeld[2][0]) && !spielfeld[1][1].equals(freiesFeld)&& spielfeld[0][2].equals(freiesFeld)) {
			return 3;
		}
		return 0;
	}
	
	public int sonderFälle() { // es gibt eine methode die schnell zum sieg führt und die CPU schnell austricksen lässt. Diese wurde hier behoben. So das die Schwierigkeit sehr schwer ist.
		// wenn feld 1 und feld 9 zB. gleich sind und dann auf feld 7 setzt, hat man 2 möglichkeiten zu gewinnen. man hat aber nur einen zug um zu blocken. diesen zug erkennt die CPU vorher
		// und blockt das.
		if(spielfeld[0][0].equals(spielfeld[2][2]) && (spielfeld[0][2].equals(freiesFeld) || spielfeld[2][0].equals(freiesFeld)) && !spielfeld[0][0].equals(freiesFeld)){
			if(spielfeld[0][1].equals(freiesFeld)) {
				return 2;
			}else if(spielfeld[1][0].equals(freiesFeld)) {
				return 4;
			}else if(spielfeld[1][2].equals(freiesFeld)) {
				return 6;
			}else if(spielfeld[2][1].equals(freiesFeld)) {
				return 8;
			}
		}
		else if(spielfeld[2][0].equals(spielfeld[0][2]) && (spielfeld[2][2].equals(freiesFeld) || spielfeld[0][0].equals(freiesFeld)) && !spielfeld[2][0].equals(freiesFeld)){
			if(spielfeld[0][1].equals(freiesFeld)) {
				return 2;
			}else if(spielfeld[1][0].equals(freiesFeld)) {
				return 4;
			}else if(spielfeld[1][2].equals(freiesFeld)) {
				return 6;
			}else if(spielfeld[2][1].equals(freiesFeld)) {
				return 8;
			}
		}
		// wenn zB. speiler auf feld 4 setzt, anschließend auf feld 8, kann er nächsten zug auf feld 7 setzen (also dazwischen) um 2 gewinnmöglichkeiten zu haben. dieser wird hier geblockt.
		else if(spielfeld[0][1].equals(spielfeld[1][0]) && spielfeld[0][0].equals(freiesFeld) && spielfeld[0][2].equals(freiesFeld) && spielfeld[2][0].equals(freiesFeld) && !spielfeld[0][1].equals(freiesFeld)){
			return 1;
		}
		else if(spielfeld[0][1].equals(spielfeld[1][2]) && spielfeld[0][2].equals(freiesFeld) && spielfeld[0][0].equals(freiesFeld) && spielfeld[2][2].equals(freiesFeld) && !spielfeld[0][1].equals(freiesFeld)){
			return 3;
		}
		else if(spielfeld[2][1].equals(spielfeld[1][0]) && spielfeld[2][0].equals(freiesFeld) && spielfeld[2][2].equals(freiesFeld) && spielfeld[0][0].equals(freiesFeld) && !spielfeld[2][1].equals(freiesFeld)){
			return 7;
		}
		else if(spielfeld[2][1].equals(spielfeld[1][2]) && spielfeld[2][2].equals(freiesFeld) && spielfeld[2][0].equals(freiesFeld) && spielfeld[0][2].equals(freiesFeld) && !spielfeld[2][1].equals(freiesFeld)){
			return 9;
		}
		return 0;
	}
	
	public int amGewinnen(String symbol) {		// genau wie winnable nur das dass eigene Symbol kontrolliert wird.
		// links nach rechts und oben nach unten prüfen.
		for(int index = 0; index < 3; index++) {
			// links nach rechts kontrolle
			if(spielfeld[index][0].equals(spielfeld[index][1]) && spielfeld[index][0].equals(symbol) && spielfeld[index][2].equals(freiesFeld)) {
				return 3 + (index * 3);
			}
			else if(spielfeld[index][0].equals(spielfeld[index][2]) && spielfeld[index][0].equals(symbol) && spielfeld[index][1].equals(freiesFeld)) {
				return 2 + (index * 3);
			}
			else if(spielfeld[index][1].equals(spielfeld[index][2]) && spielfeld[index][1].equals(symbol) && spielfeld[index][0].equals(freiesFeld)) {
				return 1 + (index * 3);
			}
			else if(spielfeld[0][index].equals(spielfeld[1][index]) && spielfeld[0][index].equals(symbol) && spielfeld[2][index].equals(freiesFeld)) {
				return 7 + index;
			}
			else if(spielfeld[0][index].equals(spielfeld[2][index]) && spielfeld[0][index].equals(symbol) && spielfeld[1][index].equals(freiesFeld)) {
				return 4 + index;
			}
			else if(spielfeld[1][index].equals(spielfeld[2][index]) && spielfeld[1][index].equals(symbol) && spielfeld[0][index].equals(freiesFeld)) {
				return 1 + index;
			}
		}
		// oben diagonal nach unten kontrolle
		if(spielfeld[0][0].equals(spielfeld[1][1]) && spielfeld[0][0].equals(symbol) && spielfeld[2][2].equals(freiesFeld)) {
			return 9;
		}
		else if(spielfeld[0][0].equals(spielfeld[2][2]) && spielfeld[0][0].equals(symbol) && spielfeld[1][1].equals(freiesFeld)) {
			return 5;
		}
		else if(spielfeld[1][1].equals(spielfeld[2][2]) && spielfeld[1][1].equals(symbol) && spielfeld[0][0].equals(freiesFeld)) {
			return 1;
		}
		// unten diagonal nach oben kontrolle
		else if(spielfeld[0][2].equals(spielfeld[1][1]) && spielfeld[0][2].equals(symbol) && spielfeld[2][0].equals(freiesFeld)) {
			return 7;
		}
		else if(spielfeld[0][2].equals(spielfeld[2][0]) && spielfeld[0][2].equals(symbol) && spielfeld[1][1].equals(freiesFeld)) {
			return 5;
		}
		else if(spielfeld[1][1].equals(spielfeld[2][0]) && spielfeld[1][1].equals(symbol) && spielfeld[0][2].equals(freiesFeld)) {
			return 3;
		}
		return 0;
	}
	
	public boolean wichtigeFelder(String symbol) {
		// wichtige felder um dem Spieler schwierig zu machen, sind vorallem das mittlere Feld, anschließend die Seite.
		if(setzeSymbol(symbol, "w2")) { return true; }
		else if(setzeSymbol(symbol, "q1")) { return true; }
		else if(setzeSymbol(symbol, "e3")) { return true; }
		else if(setzeSymbol(symbol, "e1")) { return true; }
		else if(setzeSymbol(symbol, "q3")) { return true; }
		return false;
	}
	
	public String getWinner() {
		// links nach rechts und oben nach unten prüfen.
		for(int index = 0; index < 3; index++) {
			// links nach rechts kontrolle. kontrolliert alle felder von einer Zeile ob das gleiches Symbol ist und das es kein freiesFeld-Symbol ist.
			if(spielfeld[index][0].equals(spielfeld[index][1]) && spielfeld[index][0].equals(spielfeld[index][2]) && !spielfeld[index][0].equals(freiesFeld)) {
				return spielfeld[index][0];
			}
			// oben nach unten kontrolle. kontrolliert alle felder von einer spalte ob das gleiches Symbol ist und das es kein freiesFeld-Symbol ist.
			if(spielfeld[0][index].equals(spielfeld[1][index]) && spielfeld[0][index].equals(spielfeld[2][index]) && !spielfeld[0][index].equals(freiesFeld)) {
				return spielfeld[0][index];
			}
		}
		// oben diagonal nach unten kontrolle. kontrolliert alle felder von links nach rechts die diagonale ob das gleiches Symbol ist und das es kein freiesFeld-Symbol ist.
		if(spielfeld[0][0].equals(spielfeld[1][1]) && spielfeld[0][0].equals(spielfeld[2][2]) && !spielfeld[0][0].equals(freiesFeld)) {
			return spielfeld[0][0];
		}
		// unten diagonal nach oben kontrolle. kontrolliert alle felder von rechts nach links die diagonale ob das gleiches Symbol ist und das es kein freiesFeld-Symbol ist.
		if(spielfeld[0][2].equals(spielfeld[1][1]) && spielfeld[0][2].equals(spielfeld[2][0]) && !spielfeld[0][2].equals(freiesFeld)) {
			return spielfeld[0][2];
		}
		return freiesFeld;		// wenn keiner gewonnen hat, wird freiesFeld-Symbol zurückgegeben. also ein (*) symbol.
	}
	
 	public String getFreiesFeld() {
 		return this.freiesFeld;
 	}
 	
	@Override
	public String toString() {
		String zwischenspeicher = "\tq\tw\te";		// erstellt ein String, wo alle Zeichen und Symbole gespeichert werden.
		for (int zeile = 0; zeile < spielfeld.length; zeile++) {		// for-schleife geht die zeilen.
			zwischenspeicher = zwischenspeicher + "\n\n\n" + (zeile + 1) + "\t";	// zeile+1 für zahlen links neben dem feld.
			for (int spalte = 0; spalte < (spielfeld[0].length); spalte++) { // for-schleife geht die spalten.
				zwischenspeicher += spielfeld[zeile][spalte] + "\t";	// jedes feld wird einzelnd in zwischenspeicher gespeichert.
			}
		}
		zwischenspeicher = zwischenspeicher + "\n\n";
		return zwischenspeicher;		// als fertiges String zurückgegeben.
	}
}