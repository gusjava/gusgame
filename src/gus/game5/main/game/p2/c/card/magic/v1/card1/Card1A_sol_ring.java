package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Artifact;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class Card1A_sol_ring extends Card0Artifact {

	public Card1A_sol_ring(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		//TODO action
	}

	@Override
	public String getName() {
		return "Sol Ring";
	}

	@Override
	public String getDescription() {
		return "Add 2 colorless mana to your mana pool. Tapping this artifact can be played as an interrupt.";
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
