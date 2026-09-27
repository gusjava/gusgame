package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class CostDiscard extends Cost {

	private EColor color;
	
	public CostDiscard(EColor color) {
		this.color = color;
	}
	
	public EColor getColor() {
		return color;
	}
}
