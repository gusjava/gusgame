package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0LandBasic;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeLand;

public class Card1L_swamp extends Card0LandBasic {

	public Card1L_swamp(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public String getName() {
		return "Mountain";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public List<ETypeLand> getLandTypes() {
		return UtilList.asList(ETypeLand.SWAMP);
	}

	@Override
	public List<EColor> getColors() {
		return new ArrayList<>();
	}

	@Override
	public List<Cost> getCost() {
		return new ArrayList<>();
	}
}
