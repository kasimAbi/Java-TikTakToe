package tiktaktoe;

public class Player {

	private String name;
	private String symbol;
	private int score = 0;		// wie oft spieler gewonnen hat.
	
	public Player(String name, String symbol) {		// beim object erstellen, mit namen und symbol. Konstruktor
		this.name = name;
		this.symbol = symbol;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public void incScore() {		// wenn spieler gewinnt, einmal mit 1 addieren.
		this.score++;
	}
	
	public int getScore() {
		return this.score;
	}
}