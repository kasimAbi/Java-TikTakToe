package tiktaktoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		Player playerOne = new Player("(player X)", "X");
		Player playerTwo = new Player("(player O)", "O");
		Player playerVsCPU = new Player("(Player against computer)", "X");
		Computer computer = new Computer("O");
		
		Playground playground = new Playground();
		Scanner input = new Scanner(System.in);
		String selection = "0";		// menüauswahl
		// solange ausführen wie selection ungleich 9 ist.
		while(!selection.equals("9")) {
			// menü
			System.out.print("Bitte auswählen:\n (1) player vs player\n (2) player vs computer\n (3) Score anzeigen\n (4) Spielernamen eintragen/ändern für Spieler 1\n (5) Spielernamen eintragen/ändern für Spieler 2\n (6) Reihenfolge zwischen Spieler und Spieler ändern\n (7) Spielernamen eintragen/ändern für Spieler gegen Computer.\n (8) Reihenfolge zwischen Spieler und Computer ändern.\n (9) Programm beenden\n\nIhre Wahl: ");
			selection = input.nextLine();
			System.out.println("\n\n");
			// variablendeklarationen
			String aktuellesSymbol = "", feld = "";
			
			switch(selection) {		// je nach was man bei menü ausgesucht hat wird ausgeführt
				case "1": 	// player vs player
					playground.spielfeldLeeren();		// spielfeld leeren/ bzw. mit freiesFeld-Symbol (*) füllen (siehe Klasse Playground).
					aktuellesSymbol = playerOne.getSymbol();	// Symbol, welcher an der Reihe ist wird in aktuellesSymbol überschrieben.
					
					for(int sequence = 0; sequence < 9; sequence++){	// 9 felder, darum maximal 9 mal ausführen.
						// sagt welcher spieler dran ist. und fragt nach dem feld wo der spieler setzen will
						System.out.print("Player vs Player:\n\n" + playground.toString() + "\n\nSpieler " + (playerOne.getSymbol().equals(aktuellesSymbol) ? playerOne.getName() : playerTwo.getName()) + " ist am Zug. Setze auf: ");
						feld = input.nextLine();	// zu setzendes feld eingeben
				
						if(!playground.setzeSymbol(aktuellesSymbol, feld)) {	// versucht zu setzen. wenns klappt wird ein true zurückgegeben von der methode
																				// setzeSymbol(). Ansonsten wird ein false zurückgegeben und die if-Abfrage wird ausgeführt.
							System.out.println("Dieser Punkt funktioniert nicht.");
							sequence--;		// damit der gleiche spieler wieder in zug kommt weil er nicht setzen konnte, wird sequence mit 1 subtrahiert.
						}else {
							System.out.println("Setzen war erfolgreich.");		// wenn gesetzt werden konnte, wird das ausgegeben.
						}
						if(playground.getWinner().equals(playerOne.getSymbol())) {	// prüfen ob spieler 1 gewonnen hat
							playerOne.incScore();		// wenn ja wird ihm ein punkt gewährt.
							sequence = 100;			// und um die for-schleife zu beenden wird sequence mit einer Zahl größer 9 überschrieben.
						}else if(playground.getWinner().equals(playerTwo.getSymbol())){		// prüft ob spieler2 gewonnen hat und dann gleiches Prinzip.
							playerTwo.incScore();
							sequence = 100;
						}else {
							if(sequence % 2 == 0) {		// wenn sequence mit 2 teilbar ist, ist spieler 2 am zug.
								aktuellesSymbol = playerTwo.getSymbol();
							}else {		// wenn nicht dann Spieler 1.
								aktuellesSymbol = playerOne.getSymbol();
							}
						}	
					}
					System.out.println(playground.toString());
					if(!playground.getWinner().equals(playground.getFreiesFeld())) { // gibt aus wer gewonnen hat oder ob es ein Untenschieden ist.
						System.out.println("Gewonnen hat: "  + (playground.getWinner().equals(playerOne.getSymbol()) ? playerOne.getName() : playerTwo.getName()) + ".\n\n" + playground.toString() + "\n\nBitte einmal Enter drücken um fortzufahren. ");
					}else {
						System.out.println("Gewonnen hat keiner. Es war ein Unentschieden. Bitte einmal Enter drücken um fortzufahren. ");
					}
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann wer gewonnen hat.
					break;
				case "2": 
					playground.spielfeldLeeren();	// spielfeld leeren/ mit freiesFeld-Symbol (*) füllen. siehe Playground klasse.
					aktuellesSymbol = "X";
					// auswahl von schwierigkeit
					System.out.print("Bitte Schwierigkeit auswählen:\n (1) Einfach\n (2) Mittel\n (3) Schwer\n (default): Einfach\n\nIhre Wahl: ");
					String level = input.nextLine();
					computer.setLevel(level);		// schwierigkeitslevel setzen.
					
					for(int sequence = 0; sequence < 9; sequence++){		// gleiches prinzip wie spieler vs spieler. max. 9 züge
						// gibt aus welcher spieler am zug ist.
						System.out.print("Player vs Computer:\n\n" + playground.toString() + "\n\nSpieler " + (playerVsCPU.getSymbol().equals(aktuellesSymbol) ? playerVsCPU.getName() : computer.getName()) + " ist am Zug. Setze auf: ");
						if(playerVsCPU.getSymbol().equals(aktuellesSymbol)) {		// prüft ob spieler am zug ist. wenn ja wird verlangt das spieler 1 setzt (feldeingabe)
							feld = input.nextLine();
							if(!playground.setzeSymbol(aktuellesSymbol, feld)) { // wenn er nicht setzen konnte wird sequence mit 1 subtrahiert damit spieler wieder in zug kommt.
								System.out.println("Dieser Punkt funktioniert nicht.");
								sequence--;
							}else {		// wenn gesetzt werden konnte, wird reihenfolge an CPU gegeben.
								System.out.println("Setzen war erfolgreich.");
								aktuellesSymbol = computer.getSymbol();
							}
						}else {		// CPU setzt auf ein feld und übergibt anschließend reihenfolge an spieler weiter.
							playground = computer.setPoint(playground);
							aktuellesSymbol = playerVsCPU.getSymbol();
						}
						if(playground.getWinner().equals(playerVsCPU.getSymbol())) {	// kontrolle ob Spieler gewonnen hat. wenn ja wird ihm ein Punkt gewährt.
							playerVsCPU.incScore();
							sequence = 100;		// damit for schleife beendet wird, wird sequence mit einer zahl über 9 überschrieben.
						}else if(playground.getWinner().equals(computer.getSymbol())){	// kontrolle ob CPU gewonnen hat. Programmablauf wie bei spieler.
							computer.incScore();
							sequence = 100;
						}
					}
					System.out.println(playground.toString());
					if(!playground.getWinner().equals(playground.getFreiesFeld())) {		// gibt aus wer gewonnen hat oder ob es ein unentschieden ist.
						System.out.println("Gewonnen hat: "  + (playground.getWinner().equals(playerVsCPU.getSymbol()) ? playerVsCPU.getName() : computer.getName()) + ".\n\n" + playground.toString() + "\n\nBitte einmal Enter drücken um fortzufahren. ");
					}else {
						System.out.println("Gewonnen hat keiner. Es war ein Unentschieden. Bitte einmal Enter drücken um fortzufahren. ");
					}
					input.nextLine(); 	// damit programm zum stehen kommt und der benutzer lesen kann wer gewonnen hat.
					break;
				case "3":	// score ausgeben. gibt player vs player score als erstes aus, anschließend player vs CPU
					System.out.println("\n\n\n" + playerOne.getName() + " " + playerOne.getScore() + " : " + playerTwo.getScore()  + " " + playerTwo.getName());
					System.out.println(playerVsCPU.getName() + " " + playerVsCPU.getScore() + " : " + computer.getScore()  + " " + computer.getName() + ". \nBitte einmal Enter drücken um fortzufahren. ");
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "4": 
					System.out.print("\n\n\nWie möchten Sie " + playerOne.getName() + " nennen: ");		// spieler 1 namensgebung
					playerOne.setName(input.nextLine());
					System.out.print("\nIhr neuer Name lautet: " + playerOne.getName() + ". \nBitte einmal Enter drücken um fortzufahren. ");
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "5": 
					System.out.print("\n\n\nWie möchten Sie " + playerTwo.getName() + " nennen: ");	// spieler 2 namensgebung
					playerTwo.setName(input.nextLine());
					System.out.print("\nIhr neuer Name lautet: " + playerTwo.getName() + ". \nBitte einmal Enter drücken um fortzufahren. ");
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "6": 
					Player zwischenspeicher = playerOne;		// player vs player reihenfolge ändern. mit dreieckstausch.
					playerOne = playerTwo;
					playerTwo = zwischenspeicher;
					System.out.println("Spieler gegen Spieler Reihenfolge wurde geändert." + ". \nBitte einmal Enter drücken um fortzufahren. ");
					input.nextLine(); 	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "7":
					System.out.print("\n\n\nWie möchten Sie " + playerVsCPU.getName() + " nennen: ");		// spielernamen ändern, welches gegen CPU spielt.
					playerVsCPU.setName(input.nextLine());
					System.out.print("\nIhr neuer Name lautet: " + playerVsCPU.getName() + ". \nBitte einmal Enter drücken um fortzufahren. ");
					input.nextLine(); 	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "8":		// spielerreihenfolge mit CPU ändern.
					if(playerVsCPU.getSymbol().equals("O")) {		// prüfen ob Symbol von spieler "O" ist. wenn ja spieler symbol auf "X" setzen und von CPU symbol auf "O" setzen.
						playerVsCPU.setSymbol("X");
						computer.setSymbol("O");
					}else {
						playerVsCPU.setSymbol("O");		// Spieler symbol auf "O" setzen und CPU symbol auf "X"
						computer.setSymbol("X");
					}
					System.out.println("Spieler gegen CPU Reihenfolge wurde geändert." + ". \nBitte einmal Enter drücken um fortzufahren. ");
					input.nextLine(); 	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "9": 
					System.out.println("Programm wird nun beendet." + ". \nBitte einmal Enter drücken um fortzufahren. ");		// programm beenden.
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				default: System.out.println("Ungültige Auswahl.");		// ungültige auswahl.
			}
		}
		input.close();		// scanner schließen.
	}	
}