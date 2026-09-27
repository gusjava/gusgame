package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaWhite;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CW_samite_healer extends Card0Creature {

	public Card1CW_samite_healer(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		//TODO capacity
	}

	@Override
	public String getName() {
		return "Samite Healer";
	}

	@Override
	public String getDescription() {
		return "Tap to prevent 1 damage to any target";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.WHITE);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.CLERIC);
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
				CostManaWhite.MANA,
				CostManaColorless.MANA);
	}
}
