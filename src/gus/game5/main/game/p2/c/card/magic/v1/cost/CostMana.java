package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class CostMana extends Cost {

	private EColor color;
	
	public CostMana(EColor color) {
		this.color = color;
	}
	
	public EColor getColor() {
		return color;
	}
}
