package gus.game5.main.l1.bloon.tower;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.main.l1.bloon.GameBloon;

public class Tower1 extends Tower {
	public Tower1(GameBloon game, Point0 anchor) {
		super(game, anchor);
		setColor(Color.BLACK);
		range = 100;
	}
}