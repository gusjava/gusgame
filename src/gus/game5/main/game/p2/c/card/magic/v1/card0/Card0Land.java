package gus.game5.main.game.p2.c.card.magic.v1.card0;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCard;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypePermanent;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentLand;

public abstract class Card0Land 
	extends Card0Permanent 
	implements IPermanentLand {

	public Card0Land(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public List<ETypeCard> getCardTypes() {
		return UtilList.asList(ETypeCard.LAND);
	}

	@Override
	public List<ETypePermanent> getPermanentTypes() {
		return UtilList.asList(ETypePermanent.LAND);
	}
}
