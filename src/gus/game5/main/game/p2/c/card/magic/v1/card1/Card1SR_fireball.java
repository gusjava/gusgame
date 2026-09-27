package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Sorcery;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorlessMany;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaRed;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class Card1SR_fireball extends Card0Sorcery {

	public Card1SR_fireball(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Fireball";
	}

	@Override
	public String getDescription() {
		return "Fireball does X damages total, divided evenly (round down) "
				+ "amoung any number of targets. "
				+ "Play 1 extra mana for each target beyond the first";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.RED);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaRed.MANA,
				CostManaColorlessMany.MANA);
	}
}
