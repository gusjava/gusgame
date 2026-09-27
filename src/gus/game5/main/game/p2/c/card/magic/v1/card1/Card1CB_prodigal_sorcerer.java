package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlue;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CB_prodigal_sorcerer extends Card0Creature {

	public Card1CB_prodigal_sorcerer(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		//TODO activated ability
	}

	@Override
	public String getName() {
		return "Prodigal Sorcerer";
	}

	@Override
	public String getDescription() {
		return "Tap to do 1 damage to any target.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLUE);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.WIZARD);
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
				CostManaBlue.MANA,
				CostManaColorless.MANA,
				CostManaColorless.MANA);
	}
}
