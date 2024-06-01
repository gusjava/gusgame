package gus.game5.main.game.p2.board.ragus1;

import gus.game5.core.gui.Action1;
import gus.game5.core.gui.JToolBar1;

public class PlayerScoreBar extends JToolBar1 {
	private static final long serialVersionUID = 1L;

	private PlayerRagus player;
	
	public PlayerScoreBar() {
		super();
	}
	
	public void setPlayer(PlayerRagus player) {
		this.player = player;
	}
	
	public void refresh() {
		removeAll();
		if(player==null) return;
		int score = player.getScore();
		for(int i=0;i<PlayerRagus.SCORE_MAX;i++) {
			add(new Action1(i<score ? player.getIconS() : player.getIconW()));
		}
	}
}
