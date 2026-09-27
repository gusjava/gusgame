package gus.game5.main.game.p2.c.card.magic.v1.deck;

import gus.game5.main.game.p2.c.card.magic.v1.parts.Library;

public class DeckWhite1 extends Deck {

	@Override
	protected void fillLibrary(Library library) {
		library.add(1, A_sol_ring_E1);
		library.add(4, A_yotian_soldier_E6);
		
		library.add(15, L_plains_E1);

		library.add(2, CW_benalish_hero_E1);
		library.add(2, CW_mesa_pegasus_E1);
		library.add(4, CW_samite_healer_E1);
		library.add(4, CW_savannah_lions_E1);
		library.add(4, CW_serra_angel_E1);
		library.add(1, CW_wall_of_swords_E1);
		library.add(2, CW_white_knight_E1);
		
		library.add(2, EW_blessing_E1);
		
		library.add(4, IW_disenchant_E1);
		
		library.add(1, SW_balance_E1);
	}
}
