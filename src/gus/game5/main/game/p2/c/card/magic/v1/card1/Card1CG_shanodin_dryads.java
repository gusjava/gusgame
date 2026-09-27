package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureLandWalk;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaGreen;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeLand;

public class Card1CG_shanodin_dryads extends Card0Creature {

	public Card1CG_shanodin_dryads(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		addCreatureAbility(new AbilityCreatureLandWalk(this, ETypeLand.FOREST));
	}

	@Override
	public String getName() {
		return "Shanodin Dryads";
	}

	@Override
	public String getDescription() {
		return "Forestwalk";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.GREEN);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.NYMPHS);
	}

	@Override
	public int getForceDefault() {
		return 1;
	}

	@Override
	public int getTouchnessDefault() {
		return 1;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(CostManaGreen.MANA);
	}
}
