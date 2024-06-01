package gus.game5.main.game.p2.board.chess1;

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
