package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureFlying;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaRed;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CR_shivan_dragon extends Card0Creature {

	public Card1CR_shivan_dragon(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		addCreatureAbility(new AbilityCreatureFlying(this));
		//TODO activated ability
	}

	@Override
	public String getName() {
		return "Shivan Dragon";
	}

	@Override
	public String getDescription() {
		return "Flying, R: +1/+0 until end of turn";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.RED);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.DRAGON);
	}

	@Override
	public int getForceDefault() {
		return 5;
	}

	@Override
	public int getTouchnessDefault() {
		return 5;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaRed.MANA,
				CostManaRed.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}
}
