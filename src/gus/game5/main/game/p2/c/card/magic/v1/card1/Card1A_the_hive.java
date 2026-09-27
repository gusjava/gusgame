package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Artifact;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class Card1A_the_hive extends Card0Artifact {

	public Card1A_the_hive(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "The Hive";
	}

	@Override
	public String getDescription() {
		return "5: Creates one Giant Wasp, a 1/1 flying creature. Represent Wasps with tokens making sure to indicate when each Wasp is tapped."
				+ " Wasps can't attack during the turn created. Treat Wasps like artifact creatures in every way, except that they are removed from the game entirely"
				+ " if they ever leave play. If the Hive is destroyed, the Wasps must still be killed individually.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.COLORLESS);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}
}
