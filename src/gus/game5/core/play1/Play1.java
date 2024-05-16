package gus.game5.core.play1;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.features.p.P;
import gus.game5.core.game.Game1;
import gus.game5.core.util.UtilList;

public abstract class Play1 extends Game1 {
	protected abstract void initialize2();
	
	private List<Player1> players;
	private GameOver gameOver;
	private long lastPlayCount;
	private int playIndex;

	protected void initialize1() {
		players = new ArrayList<>();
		lastPlayCount = 0;
		playIndex = 0;
		gameOver = null;
		initialize2();
	}

	protected void turn() {
		turnStart();
		if(isGameOver()) return;
		
		Player1 current = currentPlayer();
		if(current!=null) {
			boolean played = current.play();
			if(played) {
				lastPlayCount = getCount();
				played();
				nextPlayer();
			}
		}
		turnEnd();
	}
	
	protected void played() {}
	
	protected void turnStart() {}
	
	protected void turnEnd() {}
	
	public long getLastPlayCount() {
		return lastPlayCount;
	}
	
	/*
	 * PLAYER
	 */
	
	protected void addPlayer(Player1 player) {
		player.setIndex(players.size());
		players.add(player);
	}
	
	protected void nextPlayer() {
		playIndex++;
		if(playIndex>=players.size()) playIndex = 0;
	}
	
	public Player1 playerAt(int index) {
		if(players==null) return null;
		return UtilList.get(players, index);
	}
	
	public Player1 firstPlayer() {
		return playerAt(0);
	}
	
	public Player1 secondPlayer() {
		return playerAt(1);
	}
	
	public Player1 currentPlayer() {
		return playerAt(playIndex);
	}
	
	public int playIndex() {
		return playIndex;
	}
	
	public void eachPlayer(P<Player1> p) {
		if(players!=null) for(Player1 player : players) p.p(player);
	}
	
	public List<Player1> getPlayers() {
		return players;
	}
	
	public int playerNumber() {
		return players!=null ? players.size() : 0;
	}
	
	/*
	 * GAME OVER
	 */
	
	public class GameOver {
		private Player1 winner;
		
		public GameOver(Player1 winner) {
			this.winner = winner;
		}
		public Player1 getWinner() {
			return winner;
		}
	}

	public boolean isGameOver() {
		return gameOver!=null;
	}
	
	public void setGameOver(Player1 winner) {
		gameOver = new GameOver(winner);
	}
	
	public GameOver getGameOver() {
		return gameOver;
	}
}
