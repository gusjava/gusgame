package gus.game5.main.game.p2.c.card.magic.v1.deck;

import gus.game5.core.features.t.T;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1A_javemdae_tome;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1A_juggernaut;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1A_living_wall;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1A_meekstone;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1A_sol_ring;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1A_the_hive;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1A_yotian_soldier;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CB_prodigal_sorcerer;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_craw_wurm;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_force_of_nature;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_giant_spider;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_grizzly_bears;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_ironroot_treefolk;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_llanowar_elves;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_scryb_sprites;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_shanodin_dryads;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CG_thicket_basilisk;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CR_hill_giant;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CR_kird_ape;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CR_shivan_dragon;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CR_wall_of_stone;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CW_benalish_hero;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CW_mesa_pegasus;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CW_samite_healer;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CW_savannah_lions;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CW_serra_angel;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CW_wall_of_swords;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CW_white_knight;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CX_black_knight;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CX_drudge_skeletons;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CX_hynotic_specter;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CX_nightmare;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CX_royal_assassin;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1CX_sengir_vampire;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1EB_stasis;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1EG_aspect_of_wolf;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1EW_blessing;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1IG_fog;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1IG_giant_growth;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1IR_lightning_bolt;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1IW_disenchant;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1L_forest;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1L_mountain;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1L_plains;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1L_swamp;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1SG_tranquility;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1SW_balance;
import gus.game5.main.game.p2.c.card.magic.v1.card1.Card1SX_demonic_tutor;
import gus.game5.main.game.p2.c.card.magic.v1.parts.Library;

public abstract class Deck {

	public Library generateLibrary(MagicPlayer owner) {
		Library library = new Library(owner);
		fillLibrary(library);
		library.shuffle();
		return library;
	}
	
	protected abstract void fillLibrary(Library library);
	
	protected interface CardT extends T<MagicPlayer,Card0> {}
	
	

	// ARTIFACTS

	public final static CardT A_javemdae_tome_E1 = owner->new Card1A_javemdae_tome(owner, "A_javemdae_tome_E1");
	public final static CardT A_juggernaut_E1 = owner->new Card1A_juggernaut(owner, "A_juggernaut_E1");
	public final static CardT A_living_wall_E1 = owner->new Card1A_living_wall(owner, "A_living_wall_E1");
	public final static CardT A_meekstone_E1 = owner->new Card1A_meekstone(owner, "A_meekstone_E1");
	public final static CardT A_sol_ring_E1 = owner->new Card1A_sol_ring(owner, "A_sol_ring_E1");
	public final static CardT A_the_hive_E1 = owner->new Card1A_the_hive(owner, "A_the_hive_E1");
	public final static CardT A_yotian_soldier_E6 = owner->new Card1A_yotian_soldier(owner, "A_yotian_soldier_E6");
	
	// CREATURES
	
	public final static CardT CB_prodigal_sorcerer_E1 = owner->new Card1CB_prodigal_sorcerer(owner, "CB_prodigal_sorcerer_E1");
	
	public final static CardT CG_craw_wurm_E1 = owner->new Card1CG_craw_wurm(owner, "CG_craw_wurm_E1");
	public final static CardT CG_force_of_nature_E1 = owner->new Card1CG_force_of_nature(owner, "CG_force_of_nature_E1");
	public final static CardT CG_giant_spider_E1 = owner->new Card1CG_giant_spider(owner, "CG_giant_spider_E1");
	public final static CardT CG_grizzly_bears_E1 = owner->new Card1CG_grizzly_bears(owner, "CG_grizzly_bears_E1");
	public final static CardT CG_ironroot_treefolk_E1 = owner->new Card1CG_ironroot_treefolk(owner, "CG_ironroot_treefolk_E1");
	public final static CardT CG_llanowar_elves_E1 = owner->new Card1CG_llanowar_elves(owner, "CG_llanowar_elves_E1");
	public final static CardT CG_scryb_sprites_E1 = owner->new Card1CG_scryb_sprites(owner, "CG_scryb_sprites_E1");
	public final static CardT CG_shanodin_dryads_E1 = owner->new Card1CG_shanodin_dryads(owner, "CG_shanodin_dryads_E1");
	public final static CardT CG_thicket_basilisk_E1 = owner->new Card1CG_thicket_basilisk(owner, "CG_thicket_basilisk_E1");
	
	public final static CardT CR_hill_giant_E1 = owner->new Card1CR_hill_giant(owner, "CR_hill_giant_E1");
	public final static CardT CR_kird_ape_E4 = owner->new Card1CR_kird_ape(owner, "CR_kird_ape_E4");
	public final static CardT CR_shivan_dragon_E1 = owner->new Card1CR_shivan_dragon(owner, "CR_shivan_dragon_E1");
	public final static CardT CR_wall_of_stone_E1 = owner->new Card1CR_wall_of_stone(owner, "CR_wall_of_stone_E1");
	
	public final static CardT CW_benalish_hero_E1 = owner->new Card1CW_benalish_hero(owner, "CW_benalish_hero_E1");
	public final static CardT CW_mesa_pegasus_E1 = owner->new Card1CW_mesa_pegasus(owner, "CW_mesa_pegasus_E1");
	public final static CardT CW_samite_healer_E1 = owner->new Card1CW_samite_healer(owner, "CW_samite_healer_E1");
	public final static CardT CW_savannah_lions_E1 = owner->new Card1CW_savannah_lions(owner, "CW_savannah_lions_E1");
	public final static CardT CW_serra_angel_E1 = owner->new Card1CW_serra_angel(owner, "CW_serra_angel_E1");
	public final static CardT CW_wall_of_swords_E1 = owner->new Card1CW_wall_of_swords(owner, "CW_wall_of_swords_E1");
	public final static CardT CW_white_knight_E1 = owner->new Card1CW_white_knight(owner, "CW_white_knight_E1");
	
	public final static CardT CX_black_knight_E1 = owner->new Card1CX_black_knight(owner, "CCX_black_knight_E1");
	public final static CardT CX_drudge_skeletons_E1 = owner->new Card1CX_drudge_skeletons(owner, "CX_drudge_skeletons_E1");
	public final static CardT CX_hynotic_specter_E1 = owner->new Card1CX_hynotic_specter(owner, "CX_hynotic_specter_E1");
	public final static CardT CX_nightmare_E1 = owner->new Card1CX_nightmare(owner, "CX_nightmare_E1");
	public final static CardT CX_royal_assassin_E1 = owner->new Card1CX_royal_assassin(owner, "CX_royal_assassin_E1");
	public final static CardT CX_sengir_vampire_E1 = owner->new Card1CX_sengir_vampire(owner, "CX_sengir_vampire_E1");
	
	// ENCHANTMENTS
	
	public final static CardT EB_stasis_E1 = owner->new Card1EB_stasis(owner, "EB_stasis_E1");
	
	public final static CardT EG_aspect_of_wolf_E1 = owner->new Card1EG_aspect_of_wolf(owner, "EG_aspect_of_wolf_E1");
	
	public final static CardT EW_blessing_E1 = owner->new Card1EW_blessing(owner, "EW_blessing_E1");
	
	// INSTANTS
	
	public final static CardT IG_fog_E2 = owner->new Card1IG_fog(owner, "IG_fog_E2");
	public final static CardT IG_giant_growth_E2 = owner->new Card1IG_giant_growth(owner, "IG_giant_growth_E2");
	
	public final static CardT IR_lightning_bolt_E1 = owner->new Card1IR_lightning_bolt(owner, "IR_lightning_bolt_E1");
	
	public final static CardT IW_disenchant_E1 = owner->new Card1IW_disenchant(owner, "IW_disenchant_E1");
	
	// LANDS

	public final static CardT L_forest_E1 = owner->new Card1L_forest(owner, "L_forest_E1");
	public final static CardT L_island_E1 = owner->new Card1L_swamp(owner, "L_island_E1");
	public final static CardT L_mountain_E1 = owner->new Card1L_mountain(owner, "L_mountain_E1");
	public final static CardT L_plains_E1 = owner->new Card1L_plains(owner, "L_plains_E1");
	public final static CardT L_swamp_E1 = owner->new Card1L_swamp(owner, "L_swamp_E1");
	
	// SORCERIES

	public final static CardT SG_tranquility_E1 = owner->new Card1SG_tranquility(owner, "SG_tranquility_E1");
	
	public final static CardT SW_balance_E1 = owner->new Card1SW_balance(owner, "SW_balance_E1");
	
	public final static CardT SX_demonic_tutor_E1 = owner->new Card1SX_demonic_tutor(owner, "SX_demonic_tutor_E1");
	
}
