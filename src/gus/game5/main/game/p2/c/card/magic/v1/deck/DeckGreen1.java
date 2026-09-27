package gus.game5.main.game.p2.c.card.magic.v1.deck;

import gus.game5.main.game.p2.c.card.magic.v1.parts.Library;

public class DeckGreen1 extends Deck {

	@Override
	protected void fillLibrary(Library library) {
		
		library.add(15, L_forest_E1);

		library.add(4, CG_giant_spider_E1);
		library.add(4, CG_llanowar_elves_E1);
		library.add(2, CG_craw_wurm_E1);
		library.add(1, CG_force_of_nature_E1);
		library.add(3, CG_grizzly_bears_E1);
		library.add(1, CG_ironroot_treefolk_E1);
		library.add(1, CG_scryb_sprites_E1);
		library.add(2, CG_shanodin_dryads_E1);
		library.add(1, CG_thicket_basilisk_E1);
		
		library.add(1, EG_aspect_of_wolf_E1);
		
		library.add(2, IG_fog_E2);
		library.add(3, IG_giant_growth_E2);
		
		library.add(1, SG_tranquility_E1);
	}
}
