package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureReach;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaGreen;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CG_giant_spider extends Card0Creature {

	public Card1CG_giant_spider(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		addCreatureAbility(new AbilityCreatureReach(this));
	}

	@Override
	public String getName() {
		return "Giant Spider";
	}

	@Override
	public String getDescription() {
		return "Does not fly, but can block flying creatures.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.GREEN);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.SPIDER);
	}

	@Override
	public int getForceDefault() {
		return 2;
	}

	@Override
	public int getTouchnessDefault() {
		return 4;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaGreen.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}
}
