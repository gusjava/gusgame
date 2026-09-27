package gus.game5.main.game.p2.c.card.magic.v1;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.play1.Player1;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0;
import gus.game5.main.game.p2.c.card.magic.v1.deck.Deck;
import gus.game5.main.game.p2.c.card.magic.v1.parts.BattleField;
import gus.game5.main.game.p2.c.card.magic.v1.parts.Graveyard;
import gus.game5.main.game.p2.c.card.magic.v1.parts.Hand;
import gus.game5.main.game.p2.c.card.magic.v1.parts.Library;

public class MagicPlayer extends Player1 {
	
	private List<Card0> stack = new ArrayList<>();
	private List<Card0> exil = new ArrayList<>();
	
	
	public MagicPlayer(GameMagicCards1 game, Deck deck, String name) {
		this.game = game;
		this.deck = deck;
		this.name = name;
	}
	
	/*
	 * GAME
	 */
	
	private GameMagicCards1 game;

	public GameMagicCards1 getGame() {
		return game;
	}
	
	/*
	 * DECK
	 */

	private Deck deck;
	
	public Deck getDeck() {
		return deck;
	}
	
	/*
	 * NAME
	 */
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	/*
	 * LIFE
	 */

	private int life;
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public int getLife() {
		return life;
	}
	
	/*
	 * BATTLE FIELD
	 */
	
	private BattleField battleField;
	
	public BattleField getBattleField() {
		return battleField;
	}
	
	/*
	 * HAND
	 */

	private Hand hand;
	
	public Hand getHand() {
		return hand;
	}
	
	/*
	 * GRAVEYARD
	 */
	
	private Graveyard graveyard;
	
	public Graveyard getGraveyard() {
		return graveyard;
	}
	
	/*
	 * LIBRARY
	 */
	
	private Library library;
	
	public Library getLibrary() {
		return library;
	}
	
	/*
	 * OPPONENT
	 */
	
	public MagicPlayer getOpponent() {
		return game.getOpponent(this);
	}
	
	/*
	 * INIT
	 */
	
	public void init() {
		library = deck.generateLibrary(this);
		
		battleField = new BattleField();
		graveyard = new Graveyard();
		hand = new Hand();
		
		stack = new ArrayList<>();
		exil = new ArrayList<>();
		
		life = 20;
		drawFullHand();
	}
	
	
	/*
	 * DRAW FULL HAND
	 */
	
	public boolean drawFullHand() {
		while(!hand.isFull())
			if(!drawCard()) return false;
		return true;
	}
	
	
	/*
	 * DRAW CARD
	 */
	
	public boolean drawCard() {
		Card0 card = library.pick();
		if(card==null) return false;
		hand.add(card);
		return true;
	}
	
	
	/*
	 * PLAY
	 */

	@Override
	public boolean play() throws Exception {
		return false;
	}
}
