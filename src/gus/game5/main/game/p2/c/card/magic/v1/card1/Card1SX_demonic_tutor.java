package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Sorcery;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaBlack;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaColorless;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class Card1SX_demonic_tutor extends Card0Sorcery {

	public Card1SX_demonic_tutor(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Demonic Tutor";
	}

	@Override
	public String getDescription() {
		return "You may search your library for one card and take it into your hand. Reshuffle your library afterwards.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.BLACK);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(
				CostManaBlack.MANA,
				CostManaColorless.MANA);
	}
}
