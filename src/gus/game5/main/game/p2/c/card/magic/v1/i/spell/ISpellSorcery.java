package gus.game5.main.game.p2.c.card.magic.v1.i.spell;

import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;

public interface ISpellSorcery extends ISpell {

	public boolean canTargetPermanent(IPermanent permanent);
	public boolean canTargetSpell(ISpell spell);
	public boolean canTargetOpponent();
}
