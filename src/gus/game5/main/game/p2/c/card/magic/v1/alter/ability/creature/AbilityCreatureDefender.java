package gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature;

import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class AbilityCreatureDefender extends AbilityCreature {

	public AbilityCreatureDefender(IPermanentCreature creature) {
		super(creature);
	}
	
	/*
	 * INTERRUPTS
	 */

	public Boolean interruptForCanAttack() {
		return false;
	}
}
