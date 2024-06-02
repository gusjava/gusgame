package gus.game5.main.game.p2.c.board.chess.v1;

public enum EState {
	SAFE, CHECKED, MATE;
	
	public boolean isSafe() {return this==SAFE;}
	public boolean isChecked() {return this==CHECKED;}
	public boolean isMate() {return this==MATE;}
}
