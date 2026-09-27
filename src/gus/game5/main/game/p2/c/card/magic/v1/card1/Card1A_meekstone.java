package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Artifact;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class Card1A_meekstone extends Card0Artifact {

	public Card1A_meekstone(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		//TODO action on untap phase
	}

	@Override
	public String getName() {
		return "Meekstone";
	}

	@Override
	public String getDescription() {
		return "Any creature with power greater than 2 may not be untapped as normal during the untap phase.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.COLORLESS);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaColorless.MANA);
	}
}
