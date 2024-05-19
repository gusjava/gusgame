package gus.game5.main.game.board2.chess1;

public class GameOver {
	private EPlayer winner;
	
	public GameOver(EPlayer winner) {
		this.winner = winner;
	}
	
	public String getDescription() {
		if(winner==null) return "Draw";
		return "Chess Mate. "+winner.getLabel()+" won the game";
	}
}
