package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0ArtifactCreature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class Card1A_juggernaut extends Card0ArtifactCreature {

	public Card1A_juggernaut(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		//TODO must attack each turn if possible
	}

	@Override
	public String getName() {
		return "Juggernaut";
	}

	@Override
	public String getDescription() {
		return "Must attack each turn if possible. Can't be blocked by walls";
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
		return new ArrayList<>();
	}

	@Override
	public int getForceDefault() {
		return 5;
	}

	@Override
	public int getTouchnessDefault() {
		return 3;
	}
	
	/*
	 * CAN BE BLOCKED
	 */
	
	public boolean canBeBlockedDefault(IPermanentCreature card) {
		//Can't be blocked by walls
		return !card.getCreatureTypes().contains(ETypeCreature.WALL);
	}
}
