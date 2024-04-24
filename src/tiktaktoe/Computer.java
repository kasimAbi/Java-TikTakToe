package tiktaktoe;

public class Computer {
	
	private final String name = "(computer)";
	private String symbol;
	private int score = 0;
	private String level = "0";
	
	public Computer(String symbol) {	// Konstruktor
		this.symbol = symbol; // symbol festlegen beim erstellen des objekts.
	}
	
	public Playground setPoint(Playground playground) {	
		int returnValue;		// für rückgabewerte von methoden
		switch(this.level) {	// je nach level, welches ausgesucht wurde vom spieler wird mit switch/case ausgesucht.
		case "1":	// einfach. wird eine zufällige zahl generiert und gesetzt.
			while(!playground.setzeSymbol(this.symbol, this.getPoint((int)(Math.random() * 9) + 1)));
			break;
		case "2":	// mittel. wird kontrolliert ob jemand am gewinnen ist, wird entweder geblockt oder gewinnt selber. hierbei ist egal wer am gewinnen ist. wenn zB. computer und spieler
					// gleichzeitig am gewinnen ist, kann es sein das der computer blockt statt selber gewinnt.
			returnValue = playground.winnable();
			if(returnValue != 0) {
				if(!playground.setzeSymbol(this.symbol, this.getPoint(returnValue))) {
					while(!playground.setzeSymbol(this.symbol, this.getPoint((int)(Math.random() * 9) + 1)));
				}
			} else {	// wenn keiner gewinnen kann wird mit zufahlszahl gesetzt.
				while(!playground.setzeSymbol(this.symbol, this.getPoint((int)(Math.random() * 9) + 1)));
			}
			break;
		case "3":	// schwer
			returnValue = playground.amGewinnen(symbol);	// kontrolliert ob CPU am gewinnen ist und erste Priorität ist es zu gewinnen.
			if(returnValue != 0) {
				if(!playground.setzeSymbol(this.symbol, this.getPoint(returnValue))) {
					while(!playground.setzeSymbol(this.symbol, this.getPoint((int)(Math.random() * 9) + 1)));
				}
			} else {	// wenn er nicht gewinnen kann, dann guckt ob er der gegner am gewinnen ist und blockt.
				returnValue = playground.winnable();
				if( returnValue != 0) {
					if(!playground.setzeSymbol(this.symbol, this.getPoint(returnValue))) {
						while(!playground.setzeSymbol(this.symbol, this.getPoint((int)(Math.random() * 9) + 1)));
					}
				} else {	// wenn keiner am gewinnen ist, dann prüft er sonderfälle. heißt ob der gegner ihn austricksen möchte.
					returnValue = playground.sonderFälle();
					if(returnValue != 0) {
						if(!playground.setzeSymbol(this.symbol, this.getPoint(returnValue))) {
							while(!playground.setzeSymbol(this.symbol, this.getPoint((int)(Math.random() * 9) + 1)));
						}
					} else {	// wenn der gegner ihn nicht austricksen möchte oder kann, versucht er in eines der wichtigen felder zu setzen. wenn das auch nciht geht dann zufällig irgendwo.
						if(!playground.wichtigeFelder(symbol)){
							while(!playground.setzeSymbol(this.symbol, this.getPoint((int)(Math.random() * 9) + 1)));
						}
					}
				}
			}
			break;
		default: while(!playground.setzeSymbol(this.symbol, this.getPoint((int)(Math.random() * 9) + 1)));		// wenn benutzer bei schwierigkeitsauswahl was falsches eingegeben hat, ist
																												// automatisch schwierigkeit einfach. und es wird zufällig irgendwo gesetzt.
		}
		return playground;	// das spielfeld wird überarbeitet zurückgegeben.
	}
	
	public String getPoint(int randomPoint) {
		switch(randomPoint) {	// da das Spielfeld mit String felder arbeitet, werden die Zahlen die vom zufallsgenerator erstellt worden sind, je nach feld in String zurückgegeben.
		case 1: return "q1";
		case 2: return "w1";
		case 3: return "e1";
		case 4: return "q2";
		case 5: return "w2";
		case 6: return "e2";
		case 7: return "q3";
		case 8: return "w3";
		case 9: return "e3";
		default: return "";
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
	
	public void incScore() { //wenn CPU gewinnt, einmal mit 1 addieren.
		this.score++;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getLevel() {
		return this.level;
	}
}