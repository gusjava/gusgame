package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Instant;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaWhite;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;

public class Card1IW_swords_to_plowshares extends Card0Instant {

	public Card1IW_swords_to_plowshares(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Swords to Plowshares";
	}

	@Override
	public String getDescription() {
		return "Target creature is removed from game entirely; return to owner's deck only when game is over. "
				+ "Creature's controller gains life points equal to creature's power";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.WHITE);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaWhite.MANA);
	}

	@Override
	public boolean canTargetPermanent(IPermanent permanent) {
		return permanent.ofPermanentTypeCreature();
	}
}
