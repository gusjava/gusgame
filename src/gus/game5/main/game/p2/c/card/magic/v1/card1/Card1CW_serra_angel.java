package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureFlying;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureVigilance;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaWhite;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CW_serra_angel extends Card0Creature {

	public Card1CW_serra_angel(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);

		addCreatureAbility(new AbilityCreatureFlying(this));
		addCreatureAbility(new AbilityCreatureVigilance(this));
	}

	@Override
	public String getName() {
		return "Serra Angel";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.WHITE);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.ANGEL);
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
				CostManaWhite.MANA,
				CostManaWhite.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}
}
