package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaGreen;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CG_ironroot_treefolk extends Card0Creature {

	public Card1CG_ironroot_treefolk(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Iconroot Treefolk";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.GREEN);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.TREEFOLK);
	}

	@Override
	public int getForceDefault() {
		return 3;
	}

	@Override
	public int getTouchnessDefault() {
		return 5;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaGreen.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}
}
