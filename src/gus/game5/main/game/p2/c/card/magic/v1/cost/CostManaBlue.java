package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public final class CostManaBlue extends CostMana {
	
	public static final CostManaBlue MANA = new CostManaBlue();

	private CostManaBlue() {
		super(EColor.BLUE);
	}
}
