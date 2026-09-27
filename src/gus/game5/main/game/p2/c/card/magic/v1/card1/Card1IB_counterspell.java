package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Instant;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlue;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.spell.ISpell;

public class Card1IB_counterspell extends Card0Instant {

	public Card1IB_counterspell(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Counterspell";
	}

	@Override
	public String getDescription() {
		return "Counters target spell as it is being cast";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLUE);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(CostManaBlue.MANA);
	}

	@Override
	public boolean canTargetSpell(ISpell spell) {
		return true;
	}
}
