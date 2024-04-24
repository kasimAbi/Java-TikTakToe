package tiktaktoe;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		Player playerOne = new Player("(player X)", "X");
		Player playerTwo = new Player("(player O)", "O");
		Player playerVsCPU = new Player("(Player against computer)", "X");
		Computer computer = new Computer("O");
		
		Playground playground = new Playground();
		Scanner input = new Scanner(System.in);
		String selection = "0";		// men�auswahl
		// solange ausf�hren wie selection ungleich 9 ist.
		while(!selection.equals("9")) {
			// men�
			System.out.print("Bitte ausw�hlen:\n (1) player vs player\n (2) player vs computer\n (3) Score anzeigen\n (4) Spielernamen eintragen/�ndern f�r Spieler 1\n (5) Spielernamen eintragen/�ndern f�r Spieler 2\n (6) Reihenfolge zwischen Spieler und Spieler �ndern\n (7) Spielernamen eintragen/�ndern f�r Spieler gegen Computer.\n (8) Reihenfolge zwischen Spieler und Computer �ndern.\n (9) Programm beenden\n\nIhre Wahl: ");
			selection = input.nextLine();
			System.out.println("\n\n");
			// variablendeklarationen
			String aktuellesSymbol = "", feld = "";
			
			switch(selection) {		// je nach was man bei men� ausgesucht hat wird ausgef�hrt
				case "1": 	// player vs player
					playground.spielfeldLeeren();		// spielfeld leeren/ bzw. mit freiesFeld-Symbol (*) f�llen (siehe Klasse Playground).
					aktuellesSymbol = playerOne.getSymbol();	// Symbol, welcher an der Reihe ist wird in aktuellesSymbol �berschrieben.
					
					for(int sequence = 0; sequence < 9; sequence++){	// 9 felder, darum maximal 9 mal ausf�hren.
						// sagt welcher spieler dran ist. und fragt nach dem feld wo der spieler setzen will
						System.out.print("Player vs Player:\n\n" + playground.toString() + "\n\nSpieler " + (playerOne.getSymbol().equals(aktuellesSymbol) ? playerOne.getName() : playerTwo.getName()) + " ist am Zug. Setze auf: ");
						feld = input.nextLine();	// zu setzendes feld eingeben
				
						if(!playground.setzeSymbol(aktuellesSymbol, feld)) {	// versucht zu setzen. wenns klappt wird ein true zur�ckgegeben von der methode
																				// setzeSymbol(). Ansonsten wird ein false zur�ckgegeben und die if-Abfrage wird ausgef�hrt.
							System.out.println("Dieser Punkt funktioniert nicht.");
							sequence--;		// damit der gleiche spieler wieder in zug kommt weil er nicht setzen konnte, wird sequence mit 1 subtrahiert.
						}else {
							System.out.println("Setzen war erfolgreich.");		// wenn gesetzt werden konnte, wird das ausgegeben.
						}
						if(playground.getWinner().equals(playerOne.getSymbol())) {	// pr�fen ob spieler 1 gewonnen hat
							playerOne.incScore();		// wenn ja wird ihm ein punkt gew�hrt.
							sequence = 100;			// und um die for-schleife zu beenden wird sequence mit einer Zahl gr��er 9 �berschrieben.
						}else if(playground.getWinner().equals(playerTwo.getSymbol())){		// pr�ft ob spieler2 gewonnen hat und dann gleiches Prinzip.
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
						System.out.println("Gewonnen hat: "  + (playground.getWinner().equals(playerOne.getSymbol()) ? playerOne.getName() : playerTwo.getName()) + ".\n\n" + playground.toString() + "\n\nBitte einmal Enter dr�cken um fortzufahren. ");
					}else {
						System.out.println("Gewonnen hat keiner. Es war ein Unentschieden. Bitte einmal Enter dr�cken um fortzufahren. ");
					}
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann wer gewonnen hat.
					break;
				case "2": 
					playground.spielfeldLeeren();	// spielfeld leeren/ mit freiesFeld-Symbol (*) f�llen. siehe Playground klasse.
					aktuellesSymbol = "X";
					// auswahl von schwierigkeit
					System.out.print("Bitte Schwierigkeit ausw�hlen:\n (1) Einfach\n (2) Mittel\n (3) Schwer\n (default): Einfach\n\nIhre Wahl: ");
					String level = input.nextLine();
					computer.setLevel(level);		// schwierigkeitslevel setzen.
					
					for(int sequence = 0; sequence < 9; sequence++){		// gleiches prinzip wie spieler vs spieler. max. 9 z�ge
						// gibt aus welcher spieler am zug ist.
						System.out.print("Player vs Computer:\n\n" + playground.toString() + "\n\nSpieler " + (playerVsCPU.getSymbol().equals(aktuellesSymbol) ? playerVsCPU.getName() : computer.getName()) + " ist am Zug. Setze auf: ");
						if(playerVsCPU.getSymbol().equals(aktuellesSymbol)) {		// pr�ft ob spieler am zug ist. wenn ja wird verlangt das spieler 1 setzt (feldeingabe)
							feld = input.nextLine();
							if(!playground.setzeSymbol(aktuellesSymbol, feld)) { // wenn er nicht setzen konnte wird sequence mit 1 subtrahiert damit spieler wieder in zug kommt.
								System.out.println("Dieser Punkt funktioniert nicht.");
								sequence--;
							}else {		// wenn gesetzt werden konnte, wird reihenfolge an CPU gegeben.
								System.out.println("Setzen war erfolgreich.");
								aktuellesSymbol = computer.getSymbol();
							}
						}else {		// CPU setzt auf ein feld und �bergibt anschlie�end reihenfolge an spieler weiter.
							playground = computer.setPoint(playground);
							aktuellesSymbol = playerVsCPU.getSymbol();
						}
						if(playground.getWinner().equals(playerVsCPU.getSymbol())) {	// kontrolle ob Spieler gewonnen hat. wenn ja wird ihm ein Punkt gew�hrt.
							playerVsCPU.incScore();
							sequence = 100;		// damit for schleife beendet wird, wird sequence mit einer zahl �ber 9 �berschrieben.
						}else if(playground.getWinner().equals(computer.getSymbol())){	// kontrolle ob CPU gewonnen hat. Programmablauf wie bei spieler.
							computer.incScore();
							sequence = 100;
						}
					}
					System.out.println(playground.toString());
					if(!playground.getWinner().equals(playground.getFreiesFeld())) {		// gibt aus wer gewonnen hat oder ob es ein unentschieden ist.
						System.out.println("Gewonnen hat: "  + (playground.getWinner().equals(playerVsCPU.getSymbol()) ? playerVsCPU.getName() : computer.getName()) + ".\n\n" + playground.toString() + "\n\nBitte einmal Enter dr�cken um fortzufahren. ");
					}else {
						System.out.println("Gewonnen hat keiner. Es war ein Unentschieden. Bitte einmal Enter dr�cken um fortzufahren. ");
					}
					input.nextLine(); 	// damit programm zum stehen kommt und der benutzer lesen kann wer gewonnen hat.
					break;
				case "3":	// score ausgeben. gibt player vs player score als erstes aus, anschlie�end player vs CPU
					System.out.println("\n\n\n" + playerOne.getName() + " " + playerOne.getScore() + " : " + playerTwo.getScore()  + " " + playerTwo.getName());
					System.out.println(playerVsCPU.getName() + " " + playerVsCPU.getScore() + " : " + computer.getScore()  + " " + computer.getName() + ". \nBitte einmal Enter dr�cken um fortzufahren. ");
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "4": 
					System.out.print("\n\n\nWie m�chten Sie " + playerOne.getName() + " nennen: ");		// spieler 1 namensgebung
					playerOne.setName(input.nextLine());
					System.out.print("\nIhr neuer Name lautet: " + playerOne.getName() + ". \nBitte einmal Enter dr�cken um fortzufahren. ");
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "5": 
					System.out.print("\n\n\nWie m�chten Sie " + playerTwo.getName() + " nennen: ");	// spieler 2 namensgebung
					playerTwo.setName(input.nextLine());
					System.out.print("\nIhr neuer Name lautet: " + playerTwo.getName() + ". \nBitte einmal Enter dr�cken um fortzufahren. ");
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "6": 
					Player zwischenspeicher = playerOne;		// player vs player reihenfolge �ndern. mit dreieckstausch.
					playerOne = playerTwo;
					playerTwo = zwischenspeicher;
					System.out.println("Spieler gegen Spieler Reihenfolge wurde ge�ndert." + ". \nBitte einmal Enter dr�cken um fortzufahren. ");
					input.nextLine(); 	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "7":
					System.out.print("\n\n\nWie m�chten Sie " + playerVsCPU.getName() + " nennen: ");		// spielernamen �ndern, welches gegen CPU spielt.
					playerVsCPU.setName(input.nextLine());
					System.out.print("\nIhr neuer Name lautet: " + playerVsCPU.getName() + ". \nBitte einmal Enter dr�cken um fortzufahren. ");
					input.nextLine(); 	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "8":		// spielerreihenfolge mit CPU �ndern.
					if(playerVsCPU.getSymbol().equals("O")) {		// pr�fen ob Symbol von spieler "O" ist. wenn ja spieler symbol auf "X" setzen und von CPU symbol auf "O" setzen.
						playerVsCPU.setSymbol("X");
						computer.setSymbol("O");
					}else {
						playerVsCPU.setSymbol("O");		// Spieler symbol auf "O" setzen und CPU symbol auf "X"
						computer.setSymbol("X");
					}
					System.out.println("Spieler gegen CPU Reihenfolge wurde ge�ndert." + ". \nBitte einmal Enter dr�cken um fortzufahren. ");
					input.nextLine(); 	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				case "9": 
					System.out.println("Programm wird nun beendet." + ". \nBitte einmal Enter dr�cken um fortzufahren. ");		// programm beenden.
					input.nextLine();	// damit programm zum stehen kommt und der benutzer lesen kann was passiert.
					break;
				default: System.out.println("Ung�ltige Auswahl.");		// ung�ltige auswahl.
			}
		}
		input.close();		// scanner schlie�en.
	}	
}