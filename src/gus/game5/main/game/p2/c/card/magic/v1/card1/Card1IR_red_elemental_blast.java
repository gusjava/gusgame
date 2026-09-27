package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Instant;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaRed;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;
import gus.game5.main.game.p2.c.card.magic.v1.i.spell.ISpell;

public class Card1IR_red_elemental_blast extends Card0Instant {

	public Card1IR_red_elemental_blast(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Red Elemental Blast";
	}

	@Override
	public String getDescription() {
		return "Counters a blue spell being cast or destroys a blue card in play";
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
		return permanent.getColors().contains(EColor.BLUE);
	}

	@Override
	public boolean canTargetSpell(ISpell spell) {
		return spell.getColors().contains(EColor.BLUE);
	}
}
