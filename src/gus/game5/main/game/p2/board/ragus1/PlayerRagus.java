package gus.game5.main.game.p2.board.ragus1;

import static gus.game5.main.game.p2.board.ragus1.UtilRagus.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import gus.game5.core.play1.Player1;

public abstract class PlayerRagus extends Player1 {
    public static final int SCORE_MAX = 4;
	
	protected GameRagus1 game;
	protected int value;
	
	protected int score;
	protected Icon iconS;
	protected Icon iconW;
	
	
	public PlayerRagus(GameRagus1 game, int value) {
		this.game = game;
		this.value = value;

		score = 0;
		iconS = valueToIconS();
		iconW = valueToIconW();
	}
	
	public int getValue() {
		return value;
	}
	
	public int getScore() {
		return score;
	}
	
	public void incrScore() {
		score++;
	}
	
	public boolean hasMaxScore() {
		return score==SCORE_MAX;
	}
	
	public String getDisplay() {
		return isFirst() ? "Dino" : "Anubis";
	}
	
	public Icon getIconS() {
		return iconS;
	}
	
	public Icon getIconW() {
		return iconW;
	}
	
	public int getTargetIndex() {
		return targetIndex(value);
	}
	
	public int getHomeIndex() {
		return homeIndex(value);
	}
	
	private ImageIcon valueToIconS() {
		if(value==PLAYER_DINO) return game.iconAt("RAGUS_playerA_s.gif");
		if(value==PLAYER_ANUBIS) return game.iconAt("RAGUS_playerB_s.gif");
		return null;
	}
	
	private ImageIcon valueToIconW() {
		if(value==PLAYER_DINO) return game.iconAt("RAGUS_playerA_w.gif");
		if(value==PLAYER_ANUBIS) return game.iconAt("RAGUS_playerB_w.gif");
		return null;
	}
}
