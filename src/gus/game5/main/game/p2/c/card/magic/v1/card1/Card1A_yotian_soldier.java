package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureVigilance;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0ArtifactCreature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1A_yotian_soldier extends Card0ArtifactCreature {

	public Card1A_yotian_soldier(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		addCreatureAbility(new AbilityCreatureVigilance(this));
	}

	@Override
	public String getName() {
		return "Yotian Soldior";
	}

	@Override
	public String getDescription() {
		return "Attacking does not cause Yotian Soldier to tap";
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
				CostManaColorless.MANA);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return new ArrayList<>();
	}

	@Override
	public int getForceDefault() {
		return 1;
	}

	@Override
	public int getTouchnessDefault() {
		return 4;
	}
}
