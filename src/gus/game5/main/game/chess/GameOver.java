package gus.game5.main.game.chess;

public class GameOver {
	private EPlayer winner;
	
	public GameOver(EPlayer winner) {
		this.winner = winner;
	}
	
	public String getDescription() {
		if(winner==null) return "Draw";
		return winner.getLabel()+" won the game";
	}
}
