package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public final class CostManaWhite extends CostMana {
	
	public static final CostManaWhite MANA = new CostManaWhite();

	private CostManaWhite() {
		super(EColor.WHITE);
	}
}
