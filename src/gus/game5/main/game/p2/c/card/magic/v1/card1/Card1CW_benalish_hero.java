package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureBands;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaWhite;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CW_benalish_hero extends Card0Creature {

	public Card1CW_benalish_hero(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);

		addCreatureAbility(new AbilityCreatureBands(this));
	}

	@Override
	public String getName() {
		return "Benalish Hero";
	}

	@Override
	public String getDescription() {
		return "Bands";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.WHITE);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.HERO);
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
		return UtilList.asList(
				CostManaWhite.MANA);
	}
}
