package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureDefender;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaRed;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CR_wall_of_stone extends Card0Creature {

	public Card1CR_wall_of_stone(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);

		addCreatureAbility(new AbilityCreatureDefender(this));
	}

	@Override
	public String getName() {
		return "Wall of Stone";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.RED);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.WALL);
	}

	@Override
	public int getForceDefault() {
		return 0;
	}

	@Override
	public int getTouchnessDefault() {
		return 8;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaRed.MANA,
				CostManaRed.MANA,
				CostManaColorless.MANA);
	}
}
