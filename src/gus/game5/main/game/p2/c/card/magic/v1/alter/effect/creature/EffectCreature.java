package gus.game5.main.game.p2.c.card.magic.v1.alter.effect.creature;

import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;
import gus.game5.main.game.p2.c.card.magic.v1.i.source.ISourceEffect;

public class EffectCreature {

	public EffectCreature(IPermanentCreature creature, ISourceEffect source) {
		this.creature = creature;
		this.source = source;
	}
	
	/*
	 * CREATURE
	 */
	
	protected IPermanentCreature creature;
	
	public IPermanentCreature getCreature() {
		return creature;
	}
	
	/*
	 * SOURCE
	 */
	
	protected ISourceEffect source;
	
	public ISourceEffect getSource() {
		return source;
	}
}
