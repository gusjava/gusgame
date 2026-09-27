package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureFlying;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlack;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CX_hynotic_specter extends Card0Creature {

	public Card1CX_hynotic_specter(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		addCreatureAbility(new AbilityCreatureFlying(this));
	}

	@Override
	public String getName() {
		return "Hypnotic Specter";
	}

	@Override
	public String getDescription() {
		return "Flying. An opponent damaged by Specter must discard a card at random from his or her hand. "
				+ "Ignore this effect if opponent has no cards left in hand.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLACK);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.SPECTER);
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
				CostManaBlack.MANA,
				CostManaColorless.MANA);
	}
}
