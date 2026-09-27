package gus.game5.main.game.p2.c.card.magic.v1;

import java.awt.image.BufferedImage;

import gus.game5.core.game.Game;
import gus.game5.core.util.image.ImageLoader;

public class ImageLoader1 extends ImageLoader {

	public ImageLoader1(Game game) {
		super(game);
	}

	public BufferedImage getCardImage(String imgKey) {
		return get("images/"+imgKey+".jpg");
	}
}
