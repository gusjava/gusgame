package gus.game5.main.game.p2.c.card.magic.v1.i.permanent;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeLand;

public interface IPermanentLand extends IPermanent {

	public List<ETypeLand> getLandTypes();
	
	public default boolean ofLandType(ETypeLand type) {
		return UtilList.any(getLandTypes(), t->t==type);
	}
}
