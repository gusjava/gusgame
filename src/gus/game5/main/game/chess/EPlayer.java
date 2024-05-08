package gus.game5.main.game.chess;

public enum EPlayer {
	WHITE, BLACK;
	
	public boolean isWhite() {return this==WHITE;}
	public boolean isBlack() {return this==BLACK;}
	
	public EPlayer opposite() {
		return isWhite() ? BLACK : WHITE;
	}
	
	public String getLabel() {
		return isWhite() ? "White": "Black";
	}
}
