package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public final class CostManaGreen extends CostMana {
	
	public static final CostManaGreen MANA = new CostManaGreen();

	private CostManaGreen() {
		super(EColor.GREEN);
	}
}
