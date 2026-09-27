package gus.game5.main.game.p2.c.card.magic.v1.card0;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCard;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;
import gus.game5.main.game.p2.c.card.magic.v1.i.spell.ISpell;
import gus.game5.main.game.p2.c.card.magic.v1.i.spell.ISpellInstant;

public abstract class Card0Instant 
	extends Card0 
	implements ISpellInstant {

	public Card0Instant(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}

	@Override
	public List<ETypeCard> getCardTypes() {
		return UtilList.asList(ETypeCard.SPELL_INSTANT);
	}

	@Override
	public boolean canTargetPermanent(IPermanent permanent) {
		return false;
	}

	@Override
	public boolean canTargetSpell(ISpell spell) {
		return false;
	}

	@Override
	public boolean canTargetOpponent() {
		return false;
	}
}
