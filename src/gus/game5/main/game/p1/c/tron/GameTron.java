package gus.game5.main.game.p1.c.tron;

import static gus.game5.core.util.UtilRandom.chance;
import static gus.game5.core.util.UtilRandom.randomElement;

import gus.game5.core.util.Dir;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;

public class GameTron extends Game1 {

	public static final long SLEEP = 12;
	public static final int TPS = 80;

	public static final int COLS = 40;
	public static final int ROWS = 30;
	public static final int CELL = 16;
	public static final int GAP = 10;
	public static final int TOP = 46;
	public static final int BOTTOM = 32;

	public static final int SCORE_TO_WIN = 5;
	public static final int BASE_MOVE_INTERVAL = 7;
	public static final int MIN_MOVE_INTERVAL = 3;
	public static final int AI_RANDOM_CHANCE = 8;

	public static final int READY_TICKS = TPS*1;
	public static final int ROUND_OVER_TICKS = TPS*2;

	public static final int START_ROW = ROWS/2;
	public static final int START_PLAYER_COL = COLS/4;
	public static final int START_AI_COL = COLS*3/4;

	private static final int OWNER_PLAYER = 1;
	private static final int OWNER_AI = 2;

	public static final Color COLOR_BG = Color.BLACK;
	public static final Color COLOR_BORDER = new Color(60,60,60);
	public static final Color COLOR_PLAYER = new Color(0,170,220);
	public static final Color COLOR_PLAYER_HEAD = new Color(120,230,255);
	public static final Color COLOR_AI = new Color(220,100,0);
	public static final Color COLOR_AI_HEAD = new Color(255,190,90);
	public static final Color COLOR_TEXT = Color.WHITE;

	public enum State {READY, PLAYING, ROUND_OVER, OVER}


	private Grid grid;
	private Bike player;
	private Bike ai;
	private Dir wantedPlayerDir;

	private State state;
	private int stateTick;
	private int moveTick;
	private int moveInterval;

	private int round;
	private int scorePlayer;
	private int scoreAi;
	private int roundWinner;
	private boolean paused;


	public static void main(String[] args) {
		GameTron main = new GameTron();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Tron");
		s.setWidth(COLS*CELL + GAP*2);
		s.setHeight(ROWS*CELL + TOP + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(Color.BLACK);
	}

	protected void initialize1() {
		grid = new Grid(COLS, ROWS);
		player = new Bike();
		ai = new Bike();

		scorePlayer = 0;
		scoreAi = 0;
		round = 1;
		paused = false;

		startRound();
		addDraw(new GameDrawing());
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(handleRestartExit()) return;
		if(state!=State.OVER && k.in().p()) paused = !paused;
		if(paused) return;

		stateTick++;

		switch(state) {
			case READY: if(stateTick>READY_TICKS) setState(State.PLAYING); break;
			case PLAYING: playing(); break;
			case ROUND_OVER: if(stateTick>ROUND_OVER_TICKS) afterRound(); break;
			default: break;
		}
	}

	private void playing() {
		readPlayerInput();

		moveTick++;
		if(moveTick<moveInterval) return;
		moveTick = 0;
		moveBikes();
	}

	private void readPlayerInput() {
		Keyboard k = keyboard();
		if(k.in().up()) wantedPlayerDir = Dir.NORTH;
		else if(k.in().down()) wantedPlayerDir = Dir.SOUTH;
		else if(k.in().left()) wantedPlayerDir = Dir.WEST;
		else if(k.in().right()) wantedPlayerDir = Dir.EAST;
	}

	private void moveBikes() {
		applyPlayerDir();

		int pCol = player.getCol(), pRow = player.getRow();
		int aCol = ai.getCol(), aRow = ai.getRow();

		grid.setWall(pCol, pRow, OWNER_PLAYER);
		grid.setWall(aCol, aRow, OWNER_AI);

		decideAiDir();

		int pNextCol = player.peekNextCol(), pNextRow = player.peekNextRow();
		int aNextCol = ai.peekNextCol(), aNextRow = ai.peekNextRow();

		boolean headOn = pNextCol==aNextCol && pNextRow==aNextRow;
		boolean playerCrash = !grid.inside(pNextCol, pNextRow) || grid.isWall(pNextCol, pNextRow);
		boolean aiCrash = !grid.inside(aNextCol, aNextRow) || grid.isWall(aNextCol, aNextRow);

		if(headOn || (playerCrash && aiCrash)) {
			endRound(0);
			return;
		}
		if(playerCrash) {
			endRound(2);
			return;
		}
		if(aiCrash) {
			endRound(1);
			return;
		}

		player.move();
		ai.move();
	}

	private void applyPlayerDir() {
		if(wantedPlayerDir!=null && wantedPlayerDir!=player.getDir().opposite())
			player.turn(wantedPlayerDir);
	}


	/*
	 * AI
	 */

	private void decideAiDir() {
		List<Dir> candidates = new ArrayList<>();
		for(Dir d : Dir.values()) {
			if(d==ai.getDir().opposite()) continue;
			int col = ai.getCol()+d.getDx();
			int row = ai.getRow()+d.getDy();
			if(!grid.inside(col, row) || grid.isWall(col, row)) continue;
			candidates.add(d);
		}
		if(candidates.isEmpty()) return;
		if(candidates.size()==1) {
			ai.turn(candidates.get(0));
			return;
		}
		if(chance(AI_RANDOM_CHANCE)) {
			ai.turn(randomElement(candidates));
			return;
		}

		Dir best = candidates.get(0);
		int bestScore = -1;
		for(Dir d : candidates) {
			int col = ai.getCol()+d.getDx();
			int row = ai.getRow()+d.getDy();
			int score = grid.floodFill(col, row);
			if(score>bestScore) {
				bestScore = score;
				best = d;
			}
		}
		ai.turn(best);
	}


	/*
	 * ROUNDS
	 */

	private void setState(State state) {
		this.state = state;
		this.stateTick = 0;
	}

	private void startRound() {
		grid.reset();
		player.place(START_PLAYER_COL, START_ROW, Dir.EAST);
		ai.place(START_AI_COL, START_ROW, Dir.WEST);
		wantedPlayerDir = null;
		moveTick = 0;
		moveInterval = computeMoveInterval();
		setState(State.READY);
	}

	private int computeMoveInterval() {
		return Math.max(MIN_MOVE_INTERVAL, BASE_MOVE_INTERVAL-(round-1));
	}

	private void endRound(int winner) {
		roundWinner = winner;
		if(winner==1) scorePlayer++;
		else if(winner==2) scoreAi++;
		setState(State.ROUND_OVER);
	}

	private void afterRound() {
		if(scorePlayer>=SCORE_TO_WIN || scoreAi>=SCORE_TO_WIN) {
			setState(State.OVER);
			return;
		}
		round++;
		startRound();
	}


	/*
	 * DRAWING
	 */

	public class GameDrawing extends Drawing1 {

		public GameDrawing() {
			super();
			initOrigin().setXY(GAP, TOP);
		}

		protected void draw() {
			drawBackground();
			drawTrails();
			if(state!=State.OVER) drawBikes();
			drawHud();
			drawMessage();
		}

		private void drawBackground() {
			fillRect(COLOR_BG, p(0,0), COLS*CELL, ROWS*CELL);
			drawRect(COLOR_BORDER, p(0,0), COLS*CELL, ROWS*CELL);
		}

		private void drawTrails() {
			for(int col=0;col<COLS;col++)
				for(int row=0;row<ROWS;row++) {
					int owner = grid.ownerAt(col, row);
					if(owner==Grid.EMPTY) continue;
					fillRect(owner==OWNER_PLAYER ? COLOR_PLAYER : COLOR_AI, p(col*CELL, row*CELL), CELL, CELL);
				}
		}

		private void drawBikes() {
			fillRect(COLOR_PLAYER_HEAD, p(player.getCol()*CELL, player.getRow()*CELL), CELL, CELL);
			fillRect(COLOR_AI_HEAD, p(ai.getCol()*CELL, ai.getRow()*CELL), CELL, CELL);
		}

		private void drawHud() {
			drawString(COLOR_PLAYER_HEAD, fontBold(16), p(4, -TOP+30), "JOUEUR  "+scorePlayer);
			drawString(COLOR_AI_HEAD, fontBold(16), p(COLS*CELL-170, -TOP+30), "ORDINATEUR  "+scoreAi);
			drawStringC(COLOR_TEXT, fontPlain(11), p(COLS*CELL/2, ROWS*CELL+20), "FLECHES diriger   P pause   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			Point0 pc = p(COLS*CELL/2, ROWS*CELL/2);
			if(paused) drawStringC(COLOR_TEXT, fontBold(24), pc, "PAUSE");
			else if(state==State.READY) drawStringC(COLOR_TEXT, fontBold(22), pc, "MANCHE "+round);
			else if(state==State.ROUND_OVER) drawStringC(COLOR_TEXT, fontBold(22), pc, roundMessage());
			else if(state==State.OVER) drawStringC(scorePlayer>scoreAi ? COLOR_PLAYER_HEAD : COLOR_AI_HEAD, fontBold(26), pc,
					scorePlayer>scoreAi ? "VICTOIRE !" : "DEFAITE");
		}

		private String roundMessage() {
			if(roundWinner==0) return "MATCH NUL";
			return roundWinner==1 ? "MANCHE GAGNEE" : "MANCHE PERDUE";
		}
	}
}
