package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureDefender;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0ArtifactCreature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1A_living_wall extends Card0ArtifactCreature {

	public Card1A_living_wall(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		addCreatureAbility(new AbilityCreatureDefender(this));
		//TODO regenerates
	}

	@Override
	public String getName() {
		return "Living Wall";
	}

	@Override
	public String getDescription() {
		return "Counts as a wall. 1: Regenerates";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.COLORLESS);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.WALL);
	}

	@Override
	public int getForceDefault() {
		return 5;
	}

	@Override
	public int getTouchnessDefault() {
		return 3;
	}
}
