package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureFirstStrike;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlack;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CX_black_knight extends Card0Creature {

	public Card1CX_black_knight(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		addCreatureAbility(new AbilityCreatureFirstStrike(this));
		//TODO protection from white
	}

	@Override
	public String getName() {
		return "Black Knight";
	}

	@Override
	public String getDescription() {
		return "Protection from white, first strike";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLACK);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.KNIGHT);
	}

	@Override
	public int getForceDefault() {
		return 2;
	}

	@Override
	public int getTouchnessDefault() {
		return 2;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaBlack.MANA,
				CostManaBlack.MANA);
	}
}
