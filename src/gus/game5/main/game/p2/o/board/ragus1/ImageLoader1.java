package gus.game5.main.game.p2.o.board.ragus1;

import java.awt.image.BufferedImage;

import gus.game5.core.game.Game;
import gus.game5.core.util.image.ImageLoader;

public class ImageLoader1 extends ImageLoader {

	public ImageLoader1(Game game) {
		super(game);
	}

	public BufferedImage valueToImg(int value, boolean blocked) {
		if(blocked) {
			if(value>0) return get("RAGUS_playerA_blocked.gif");
			if(value<0) return get("RAGUS_playerB_blocked.gif");
		}
		if(value>0) return get("RAGUS_playerA.gif");
		if(value<0) return get("RAGUS_playerB.gif");
		return null;
	}
}
