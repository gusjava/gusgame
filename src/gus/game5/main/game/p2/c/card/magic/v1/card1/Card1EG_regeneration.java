package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Enchantment;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaGreen;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;

public class Card1EG_regeneration extends Card0Enchantment {

	public Card1EG_regeneration(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Regeneration";
	}

	@Override
	public String getDescription() {
		return "G: Target creature regenerates.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.GREEN);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaGreen.MANA,
				CostManaColorless.MANA);
	}

	@Override
	public boolean canTargetPermanent(IPermanent permanent) {
		return permanent.ofPermanentTypeCreature();
	}
}
