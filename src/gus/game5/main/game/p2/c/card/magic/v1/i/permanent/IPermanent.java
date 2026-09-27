package gus.game5.main.game.p2.c.card.magic.v1.i.permanent;

import java.util.List;

import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypePermanent;
import gus.game5.main.game.p2.c.card.magic.v1.i.IHasColors;

public interface IPermanent
	extends IHasColors {
	
	/*
	 * PERMANENT TYPES
	 */
	
	public List<ETypePermanent> getPermanentTypes();
	
	public default boolean ofPermanentTypeLand() {
		return getPermanentTypes().contains(ETypePermanent.LAND);
	}
	
	public default boolean ofPermanentTypeCreature() {
		return getPermanentTypes().contains(ETypePermanent.CREATURE);
	}
	
	public default boolean ofPermanentTypeArtifact() {
		return getPermanentTypes().contains(ETypePermanent.ARTIFACT);
	}
	
	public default boolean ofPermanentTypeEnchantment() {
		return getPermanentTypes().contains(ETypePermanent.ENCHANTMENT);
	}
	
	/*
	 * LEGENDARY
	 */
	
	public boolean isLegendary();
	
	/*
	 * CONTROLLER
	 */

	public MagicPlayer getController();
	public void setController(MagicPlayer controller);
	
	/*
	 * BATTLE FIELD
	 */
	
	public void onEnterBattleField();
	public void onExitBattleField();
	public boolean justEnteredBattleField();
	
	/*
	 * TAPPED
	 */
	
	public void setTapped(boolean tapped);
	public boolean isTapped();
	
	public default void tap() {
		setTapped(true);
	}
	
	public default void untap() {
		setTapped(false);
	}
}
