package gus.game5.main.game.p2.c.card.magic.v1.card1;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0Creature;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.cost.CostManaRed;
import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public class Card1CR_kird_ape extends Card0Creature {

	public Card1CR_kird_ape(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		//TODO mettre en place les modificateurs de force et touchness
	}

	@Override
	public String getName() {
		return "Kird Ape";
	}

	@Override
	public String getDescription() {
		return "Kird Ape gains +1/+2 if you have any forests in play";
	}

	@Override
	public List<EColor> getColors() {
		return UtilList.asList(EColor.RED);
	}

	@Override
	public List<ETypeCreature> getCreatureTypes() {
		return UtilList.asList(ETypeCreature.APE);
	}

	@Override
	public int getForceDefault() {
		if(controller==null) return 1;
		return controller.getBattleField().hasLandForest() ? 2 : 1;
	}

	@Override
	public int getTouchnessDefault() {
		if(controller==null) return 1;
		return controller.getBattleField().hasLandForest() ? 3 : 1;
	}

	@Override
	public List<Cost> getCost() {
		return UtilList.asList(CostManaRed.MANA);
	}
}
