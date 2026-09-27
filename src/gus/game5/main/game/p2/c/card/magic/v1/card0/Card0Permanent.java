package gus.game5.main.game.p2.c.card.magic.v1.card0;

import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;

public abstract class Card0Permanent 
	extends Card0 
	implements IPermanent {

	public Card0Permanent(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
	}
	
	/*
	 * CONTROLLER
	 */
	
	protected MagicPlayer controller;
	
	@Override
	public MagicPlayer getController() {
		return controller;
	}

	@Override
	public void setController(MagicPlayer controller) {
		this.controller = controller;
	}
	
	/*
	 * BATTLE FIELD
	 */
	
	@Override
	public void onEnterBattleField() {
		setController(owner);
		setJustEnteredBattleField(true);
	}

	@Override
	public void onExitBattleField() {
		setController(null);
		setJustEnteredBattleField(false);
	}
	
	private boolean justEnteredBattleField = true;

	public void setJustEnteredBattleField(boolean justEnteredBattleField) {
		this.justEnteredBattleField = justEnteredBattleField;
	}
	
	@Override
	public boolean justEnteredBattleField() {
		return justEnteredBattleField;
	}
	
	/*
	 * LEGENDARY
	 */
	
	@Override
	public boolean isLegendary() {
		return false;
	}
	
	/*
	 * TAPPED
	 */
	
	private boolean tapped = false;

	@Override
	public void setTapped(boolean tapped) {
		this.tapped = tapped;
	}
	
	@Override
	public boolean isTapped() {
		return tapped;
	}
}
