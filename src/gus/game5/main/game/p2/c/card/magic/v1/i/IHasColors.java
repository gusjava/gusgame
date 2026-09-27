package gus.game5.main.game.p2.c.card.magic.v1.i;

import java.util.List;

import gus.game5.main.game.p2.c.card.magic.v1.enu.EColor;

public interface IHasColors {

	public List<EColor> getColors();
	
	public default boolean ofColorWhite() {
		return getColors().contains(EColor.WHITE);
	}
	
	public default boolean ofColorGreen() {
		return getColors().contains(EColor.GREEN);
	}
	
	public default boolean ofColorREd() {
		return getColors().contains(EColor.RED);
	}
	
	public default boolean ofColorBlack() {
		return getColors().contains(EColor.BLACK);
	}
	
	public default boolean ofColorBlue() {
		return getColors().contains(EColor.BLUE);
	}
}
