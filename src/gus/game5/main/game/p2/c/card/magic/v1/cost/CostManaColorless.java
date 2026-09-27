package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public final class CostManaColorless extends CostMana {
	
	public static final CostManaColorless MANA = new CostManaColorless();

	private CostManaColorless() {
		super(EColor.COLORLESS);
	}
}
