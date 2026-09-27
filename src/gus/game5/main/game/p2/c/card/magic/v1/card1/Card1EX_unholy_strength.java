package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Enchantment;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlack;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;

public class Card1EX_unholy_strength extends Card0Enchantment {

	public Card1EX_unholy_strength(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Unholy Strength";
	}

	@Override
	public String getDescription() {
		return "Target creature gains +2/+1";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLACK);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaBlack.MANA);
	}

	@Override
	public boolean canTargetPermanent(IPermanent permanent) {
		return permanent.ofPermanentTypeCreature();
	}
}
