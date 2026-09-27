package gus.game5.main.game.p2.c.card.magic.v1.deck;

import gus.game5.main.game.p2.c.card.magic.v1.parts.Library;

public class DeckRed1 extends Deck {

	@Override
	protected void fillLibrary(Library library) {
		
		library.add(10, L_mountain_E1);
		
		library.add(4, CR_kird_ape_E4);
		
		library.add(4, IR_lightning_bolt_E1);
	}
}
