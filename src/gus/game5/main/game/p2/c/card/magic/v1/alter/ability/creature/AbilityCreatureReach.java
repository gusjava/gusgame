package gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature;

import gus.game5.core.features.f.F;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class AbilityCreatureReach extends AbilityCreature {

	public AbilityCreatureReach(IPermanentCreature creature) {
		super(creature);
	}
	
	/*
	 * INTERRUPTS
	 */

	@Override
	public F<IPermanentCreature> interruptForCanBlock() {
		return IPermanentCreature::hasCreatureAbilityFlying;
		//TODO revoir, le mécanisme général n'est pas au point
	}

}
