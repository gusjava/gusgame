package gus.game5.core.draw;

import java.awt.Color;
import java.awt.Graphics2D;

import gus.game5.core.game.Game;

public class DrawGameBorder implements Draw {

	private Game game;
	private int gap;
	
	public DrawGameBorder(Game game, int gap) {
		this.game = game;
		this.gap = gap;
	}

	public void draw(Graphics2D g2) {
		int height = game.gameHeight();
		int width = game.gameWidth();
		Color background = game.getBackground();
		
		g2.setColor(background);
		
		g2.fillRect(0, 0, width, gap);
		g2.fillRect(0, height-gap, width, gap);
		
		g2.fillRect(0, 0, gap, height);
		g2.fillRect(width-gap, 0, gap, height);
	}
}
