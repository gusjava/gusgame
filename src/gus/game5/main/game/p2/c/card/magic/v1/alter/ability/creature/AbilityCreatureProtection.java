package gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;

public class AbilityCreatureProtection extends AbilityCreature {

	public AbilityCreatureProtection(IPermanentCreature creature, EColor color) {
		super(creature);
		this.color = color;
	}
	
	/*
	 * COLOR
	 */
	
	private EColor color;
	
	public EColor getColor() {
		return color;
	}

}
