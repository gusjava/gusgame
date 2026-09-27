package gus.game5.main.game.p2.c.card.magic.v1.parts;

import java.util.ArrayList;
import java.util.List;

import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0;

public class Graveyard {
	
	public Graveyard() {
		cards = new ArrayList<>();
	}
	
	/*
	 * CARDS
	 */

	private List<Card0> cards;
	
	public List<Card0> getCards() {
		return cards;
	}
	
	/*
	 * CLEAR
	 */
	
	public void clear() {
		cards.clear();
	}
	
	/*
	 * ADD
	 */
	
	public void add(Card0 card) {
		cards.add(card);
	}
}
