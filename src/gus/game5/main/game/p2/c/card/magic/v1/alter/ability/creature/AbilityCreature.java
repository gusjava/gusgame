package gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature;

import gus.game5.core.features.f.F;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class AbilityCreature {

	public AbilityCreature(IPermanentCreature creature) {
		this.creature = creature;
	}
	
	/*
	 * CREATURE
	 */
	
	protected IPermanentCreature creature;
	
	public IPermanentCreature getCreature() {
		return creature;
	}
	
	/*
	 * INTERRUPTS
	 */

	public Boolean interruptForSummoningSickness() {
		return null;
	}

	public Boolean interruptForCanAttack() {
		return null;
	}

	public F<IPermanentCreature> interruptForCanBlock() {
		return null;
	}

	public F<IPermanentCreature> interruptForCanBeBlocked() {
		return null;
	}
}
