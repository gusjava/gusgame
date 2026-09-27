package gus.game5.main.game.p2.c.card.magic.v1.card0;

import java.awt.image.BufferedImage;
import java.util.List;

import gus.game5.main.game.p2.c.card.magic.v1.GameMagicCards1;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.cost.Cost;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCard;

public abstract class Card0 {
	
	protected MagicPlayer owner;
	protected GameMagicCards1 game;
	protected BufferedImage image;
	
	public Card0(MagicPlayer owner, String imgKey) {
		this.owner = owner;
		game = owner.getGame();
		image = game.getCardImage(imgKey);
		
		if(image==null) throw new RuntimeException("Null image for card "+getName()+" (imgKey="+imgKey+")");
	}
	
	public MagicPlayer getOwner() {
		return owner;
	}
	
	public GameMagicCards1 getGame() {
		return game;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public abstract String getName();
	public abstract String getDescription();
	public abstract List<ETypeCard> getCardTypes();
	public abstract List<Cost> getCost();
	
	/*
	 * OF TYPE
	 */
	
	public boolean ofCardTypeLand() {
		return getCardTypes().contains(ETypeCard.LAND);
	}
	
	public boolean ofCardTypeSpellCreature() {
		return getCardTypes().contains(ETypeCard.SPELL_CREATURE);
	}
	
	public boolean ofCardTypeSpellArtifact() {
		return getCardTypes().contains(ETypeCard.SPELL_ARTIFACT);
	}
	
	public boolean ofCardTypeSpellEnchantment() {
		return getCardTypes().contains(ETypeCard.SPELL_ENCHANTMENT);
	}
	
	public boolean ofCardTypeSpellSorcery() {
		return getCardTypes().contains(ETypeCard.SPELL_SORCERY);
	}
	
	public boolean ofCardTypeSpellInstant() {
		return getCardTypes().contains(ETypeCard.SPELL_INSTANT);
	}
}
