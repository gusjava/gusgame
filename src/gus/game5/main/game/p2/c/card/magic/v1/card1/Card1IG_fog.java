package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Instant;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaGreen;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public class Card1IG_fog extends Card0Instant {

	public Card1IG_fog(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		//cette carte devra s'enregistrer au près du gameEngine comme un intercepteur de phase COMBAT_RESOLUTON
	}

	@Override
	public String getName() {
		return "Fog";
	}

	@Override
	public String getDescription() {
		return "Creatures attack and block as normal, but none deal any damage. All attacking creatures are still tapped. Play any time before attack damage is dealt.";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.GREEN);
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(CostManaGreen.MANA);
	}
}
