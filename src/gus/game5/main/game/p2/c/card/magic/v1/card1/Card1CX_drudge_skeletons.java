package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlack;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CX_drudge_skeletons extends Card0Creature {

	public Card1CX_drudge_skeletons(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Drudge Skeletons";
	}

	@Override
	public String getDescription() {
		return "X: Regenerates";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLACK);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.SKELETONS);
	}

	@Override
	public int getForceDefault() {
		return 4;
	}

	@Override
	public int getTouchnessDefault() {
		return 4;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaBlack.MANA,
				CostManaColorless.MANA);
	}
}
