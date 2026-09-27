package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Instant;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaRed;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;

public class Card1IR_lightning_bolt extends Card0Instant {

	public Card1IR_lightning_bolt(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Lightning Bolt";
	}

	@Override
	public String getDescription() {
		return "Lightning Bolt does 3 damage to one target";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.RED);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(CostManaRed.MANA);
	}

	@Override
	public boolean canTargetPermanent(IPermanent permanent) {
		return permanent.ofPermanentTypeCreature();
	}

	@Override
	public boolean canTargetOpponent() {
		return true;
	}
}
