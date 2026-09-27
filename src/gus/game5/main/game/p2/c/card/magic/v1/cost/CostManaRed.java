package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public final class CostManaRed extends CostMana {
	
	public static final CostManaRed MANA = new CostManaRed();

	private CostManaRed() {
		super(EColor.RED);
	}
}
