package gus.game5.main.game.p2.c.card.magic.v1.parts;

import java.util.ArrayList;
import java.util.List;

import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0;

public class Hand {
	
	public static final int MAX_NB = 7;
	
	public Hand() {
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
	 * SIZE
	 */
	
	public int size() {
		return cards.size();
	}
	
	/*
	 * IS EMPTY
	 */
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	/*
	 * IS FULL
	 */
	
	public boolean isFull() {
		return size()==MAX_NB;
	}
	
	/*
	 * ADD
	 */
	
	public void add(Card0 card) {
		cards.add(card);
	}
}
