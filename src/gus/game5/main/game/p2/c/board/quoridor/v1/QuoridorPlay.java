package gus.game5.main.game.p2.c.board.quoridor.v1;

public class QuoridorPlay {

	public static QuoridorPlay move(int i, int j) {
		return new QuoridorPlay(true, i, j, false);
	}

	public static QuoridorPlay wall(int i, int j, boolean horizontal) {
		return new QuoridorPlay(false, i, j, horizontal);
	}

	private final boolean move;
	private final int i;
	private final int j;
	private final boolean horizontal;

	private QuoridorPlay(boolean move, int i, int j, boolean horizontal) {
		this.move = move;
		this.i = i;
		this.j = j;
		this.horizontal = horizontal;
	}

	public boolean isMove() {
		return move;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public String toString() {
		if(move) return "move["+i+","+j+"]";
		return "wall["+i+","+j+","+(horizontal?"H":"V")+"]";
	}
}
