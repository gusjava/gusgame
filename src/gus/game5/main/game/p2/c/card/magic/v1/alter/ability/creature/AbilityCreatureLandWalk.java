package gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature;

import gus.game5.core.features.f.F;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeLand;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class AbilityCreatureLandWalk extends AbilityCreature {

	public AbilityCreatureLandWalk(IPermanentCreature creature, ETypeLand landType) {
		super(creature);
		this.landType = landType;
	}
	
	/*
	 * LAND TYPE
	 */
	
	private ETypeLand landType;

	public ETypeLand getLandType() {
		return landType;
	}
	
	/*
	 * INTERRUPTS
	 */

	@Override
	public F<IPermanentCreature> interruptForCanBeBlocked() {
		if(creature.getController().getOpponent().getBattleField().hasLand(landType))
			return c->false;
		return null;
	}
}
