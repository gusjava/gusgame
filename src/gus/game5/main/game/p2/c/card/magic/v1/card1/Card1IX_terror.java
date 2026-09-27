package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Instant;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlack;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;

public class Card1IX_terror extends Card0Instant {

	public Card1IX_terror(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Terror";
	}

	@Override
	public String getDescription() {
		return "Destroys target creature without possibility of regeneration. "
				+ "Does not affect black creatures and artifact creatures.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLACK);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaBlack.MANA,
				CostManaColorless.MANA);
	}

	@Override
	public boolean canTargetPermanent(IPermanent permanent) {
		if(permanent.ofPermanentTypeArtifact()) return false;
		if(permanent.ofColorBlack()) return false;
		return permanent.ofPermanentTypeCreature();
	}
}
