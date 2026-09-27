package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Enchantment;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlue;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;

public class Card1EB_stasis extends Card0Enchantment {

	public Card1EB_stasis(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Stasis";
	}

	@Override
	public String getDescription() {
		return "Players do not get an untap phase. "
				+ "Pay B during upkeep or Stasis is destroyed.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLUE);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaBlue.MANA,
				CostManaColorless.MANA);
	}

	@Override
	public boolean canTargetPermanent(IPermanent permanent) {
		return false;
	}
}
