package gus.game5.main.game.p2.c.card.magic.v1.cost;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public final class CostManaColorlessMany extends CostMana {
	
	public static final CostManaColorlessMany MANA = new CostManaColorlessMany();

	private CostManaColorlessMany() {
		super(EColor.COLORLESS);
	}
}
