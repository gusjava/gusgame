package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureTrample;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaGreen;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CG_force_of_nature extends Card0Creature {

	public Card1CG_force_of_nature(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		addCreatureAbility(new AbilityCreatureTrample(this));
		
		//TODO upkeep pay
	}

	@Override
	public String getName() {
		return "Force of Nature";
	}

	@Override
	public String getDescription() {
		return "Trample. You must pay GGGG during upkeep or Force of Nature does 8 damage to you. "
				+ "You may still attack with Force of Nature event if you failed to pay the upkeep";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.GREEN);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.FORCE);
	}

	@Override
	public int getForceDefault() {
		return 8;
	}

	@Override
	public int getTouchnessDefault() {
		return 8;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaGreen.MANA,
				CostManaGreen.MANA,
				CostManaGreen.MANA,
				CostManaGreen.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}
}
