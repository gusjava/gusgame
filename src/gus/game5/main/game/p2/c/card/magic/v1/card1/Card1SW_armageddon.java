package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Sorcery;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaWhite;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class Card1SW_armageddon extends Card0Sorcery {

	public Card1SW_armageddon(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Armageddon";
	}

	@Override
	public String getDescription() {
		return "All lands in play are destroyed";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.WHITE);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaWhite.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}
}
