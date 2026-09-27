package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Instant;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlack;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class Card1IX_dark_ritual extends Card0Instant {

	public Card1IX_dark_ritual(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Dark Ritual";
	}

	@Override
	public String getDescription() {
		return "Add 3 black mana to your mana pool";
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
}
