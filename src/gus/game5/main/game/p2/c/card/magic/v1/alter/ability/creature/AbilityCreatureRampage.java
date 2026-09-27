package gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature;

import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class AbilityCreatureRampage extends AbilityCreature {

	public AbilityCreatureRampage(IPermanentCreature creature, int value) {
		super(creature);
		this.value = value;
	}
	
	/*
	 * VALUE
	 */
	
	private int value;
	
	public int getValue() {
		return value;
	}

}
