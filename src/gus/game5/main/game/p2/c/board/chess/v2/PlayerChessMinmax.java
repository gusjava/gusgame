package gus.game5.main.game.p2.c.board.chess.v2;

import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.BLACK;
import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.WHITE;

import java.util.HashSet;
import java.util.Set;

import gus.game5.core.play1.Player1;

public class PlayerChessMinmax extends Player1 {

	private GameChess2 game;
	private Set<String> playedPositions = new HashSet<>();

	public PlayerChessMinmax(GameChess2 game) {
		this.game = game;
	}

	public boolean play() throws Exception {
		int[][] data = game.boardData();
		playedPositions.add(UtilChessMinmax.positionKey(data));

		int[] play = UtilChessMinmax.computePlay(getValue(), data, playedPositions);
		if(play==null) throw new Exception("PlayerChessMinmax found no play");

		int[] start = new int[] {play[0], play[1]};
		int[] end = new int[] {play[2], play[3]};

		game.attemptToPlay(getValue(), start, end);
		return true;
	}

	public int getValue() {
		return isFirst() ? WHITE : BLACK;
	}

	public String getDisplay() {
		return isFirst() ? "White" : "Black";
	}

	public String getType() {
		return "Min-Max";
	}
}
