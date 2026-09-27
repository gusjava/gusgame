package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public final class CostManaBlack extends CostMana {
	
	public static final CostManaBlack MANA = new CostManaBlack();

	private CostManaBlack() {
		super(EColor.BLACK);
	}
}
