package gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature;

import gus.game5.core.features.f.F;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class AbilityCreatureFlying extends AbilityCreature {

	public AbilityCreatureFlying(IPermanentCreature creature) {
		super(creature);
	}
	
	/*
	 * INTERRUPTS
	 */

	@Override
	public F<IPermanentCreature> interruptForCanBeBlocked() {
		return IPermanentCreature::hasCreatureAbilityFlying;
	}
}
