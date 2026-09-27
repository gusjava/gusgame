package gus.game5.main.game.p2.c.card.magic.v1.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gus.game5.core.features.t.T;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0;

public class Library {
	
	public Library(MagicPlayer owner) {
		this.owner = owner;
		cards = new ArrayList<>();
	}
	
	/*
	 * OWNER
	 */
	
	private MagicPlayer owner;
	
	public MagicPlayer getOwner() {
		return owner;
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
	 * SHUFFLE
	 */
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	/*
	 * ADD
	 */
	
	public void add(Card0 card) {
		cards.add(card);
	}
	
	public void add(int nb, T<MagicPlayer,Card0> t) {
		for(int i=0;i<nb;i++) add(t.t(owner));
	}
	
	/*
	 * PICK
	 */
	
	public Card0 pick() {
		if(isEmpty()) return null;
		return cards.remove(0);
	}

}
