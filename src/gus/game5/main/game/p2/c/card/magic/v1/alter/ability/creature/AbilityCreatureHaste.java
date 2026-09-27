package gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature;

import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class AbilityCreatureHaste extends AbilityCreature {

	// Célérité
	public AbilityCreatureHaste(IPermanentCreature creature) {
		super(creature);
	}
	
	/*
	 * INTERRUPTS
	 */

	@Override
	public Boolean interruptForSummoningSickness() {
		return false;
	}

}
