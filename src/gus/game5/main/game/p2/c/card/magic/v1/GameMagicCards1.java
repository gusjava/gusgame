package gus.game5.main.game.p2.c.card.magic.v1;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.List;

import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.play1.Play1;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeImg;
import gus.game5.core.shape.ShapeList;
import gus.game5.main.game.p2.c.card.magic.v1.card0.Card0;
import gus.game5.main.game.p2.c.card.magic.v1.deck.Deck;
import gus.game5.main.game.p2.c.card.magic.v1.deck.DeckBlack1;
import gus.game5.main.game.p2.c.card.magic.v1.deck.DeckBlue1;
import gus.game5.main.game.p2.c.card.magic.v1.deck.DeckGreen1;
import gus.game5.main.game.p2.c.card.magic.v1.deck.DeckRed1;
import gus.game5.main.game.p2.c.card.magic.v1.deck.DeckWhite1;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETurnStep;

public class GameMagicCards1 extends Play1 {
	
	public static void main(String[] args) {
		GameMagicCards1 main = new GameMagicCards1();
		main.displayInWindows();
		main.start();
	}
	
	
	protected void initSettings(Settings s) {
		s.setTitle("Magic Cards Battle");
		s.setWidth(1400);
		s.setHeight(750);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	protected void initialize2() {
		imageLoader = new ImageLoader1(this);

		Deck deckW = new DeckWhite1();
		Deck deckG = new DeckGreen1();
		Deck deckR = new DeckRed1();
		Deck deckX = new DeckBlack1();
		Deck deckB = new DeckBlue1();
		
		
		Deck deck1 = deckW;
		Deck deck2 = deckG;
		
		MagicPlayer player1 = new MagicPlayer(this, deck1, "Player 1");
		MagicPlayer player2 = new MagicPlayer(this, deck2, "Player 2");
		
		addPlayer(player1);
		addPlayer(player2);
		
		player1.init();
		player2.init();
		
		cardDisplays = newShapeList();
		
		{
			List<Card0> cards = player1.getHand().getCards();
			for(int i=0;i<cards.size();i++) {
				Card0 card = cards.get(i);
				double factor = 1.4;
				Point1 p = p1(10+i*65*factor, 600);
				cardDisplays.add(new CardDisplay(p, factor, card));
			}
		}
		{
			List<Card0> cards = player2.getHand().getCards();
			for(int i=0;i<cards.size();i++) {
				Card0 card = cards.get(i);
				double factor = 1.4;
				Point1 p = p1(10+i*65*factor, 10);
				cardDisplays.add(new CardDisplay(p, factor, card));
			}
		}
		
		turnStep = ETurnStep.UPKEEP;
	}
	
	/*
	 * IMAGE LOADER
	 */
	
	private ImageLoader1 imageLoader;
	
	public BufferedImage getCardImage(String imgKey) {
		return imageLoader.getCardImage(imgKey);
	}
	
	
	
	/*
	 * TURN
	 */
	
	protected void turnStart() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
	}
	
	protected void played() {
		
	}
	
	protected void turnEnd() {
		
	}
	
	/*
	 * TURN STEP
	 */
	
	private ETurnStep turnStep;
	
	/*
	 * PLAYER
	 */
	
	public MagicPlayer getOpponent(MagicPlayer player) {
		return (MagicPlayer) otherPlayer(player);
	}
	
	
	
	/*
	 * CARD DISPLAY
	 */
	
	private ShapeList<CardDisplay> cardDisplays;
	
	private class CardDisplay extends ShapeImg {
		private Card0 card;
		
		public CardDisplay(Point0 anchor, double factor, Card0 card) {
			super(anchor, AnchorType.NW, card.getImage());
			this.card = card;

			//dimensions d'une carte : 62 x 88mm
			setHeight(88*factor);
			setWidth(62*factor);
		}
		
		public Card0 getCard() {
			return card;
		}
	}
}
