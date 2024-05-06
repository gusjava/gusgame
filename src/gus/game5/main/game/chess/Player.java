package gus.game5.main.game.chess;

public enum Player {
	WHITE, BLACK;
	
	public boolean isWhite() {return this==WHITE;}
	public boolean isBlack() {return this==BLACK;}
	
	public Player opposite() {
		return isWhite() ? BLACK : WHITE;
	}
	
	public String getLabel() {
		return isWhite() ? "White": "Black";
	}
}
