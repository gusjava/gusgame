package gus.game5.main.game.p2.c.card.magic.v1.alter.enchant.creature;

import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;
import gus.game5.main.game.p2.c.card.magic.v1.i.source.ISourceEnchant;

public class EnchantCreature {

	public EnchantCreature(IPermanentCreature creature, ISourceEnchant source) {
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
	
	protected ISourceEnchant source;
	
	public ISourceEnchant getSource() {
		return source;
	}
}
