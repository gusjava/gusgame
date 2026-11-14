package gus.game5.main.game.p1.c.sokoban;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;

public class GameSokoban extends Game1 {

	public static final long SLEEP = 16;
	public static final int CLEAR_TICKS = 90;

	public static final int CELL = 36;
	public static final int GAP = 16;
	public static final int TOP = 50;
	public static final int BOTTOM = 40;

	public static final Color COLOR_BG = new Color(10,10,16);
	public static final Color COLOR_WALL = new Color(90,70,60);
	public static final Color COLOR_WALL_EDGE = new Color(130,105,90);
	public static final Color COLOR_FLOOR = new Color(40,34,30);
	public static final Color COLOR_TARGET = new Color(230,190,60);
	public static final Color COLOR_BOX = new Color(170,120,70);
	public static final Color COLOR_BOX_EDGE = new Color(120,80,45);
	public static final Color COLOR_BOX_ON_TARGET = new Color(90,200,110);
	public static final Color COLOR_BOX_ON_TARGET_EDGE = new Color(50,150,75);
	public static final Color COLOR_PLAYER = new Color(70,150,230);
	public static final Color COLOR_PLAYER_FACE = Color.WHITE;
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);

	public enum State {PLAYING, CLEARED, FINISHED}


	private Level level;
	private int levelIndex;
	private Dir facing;
	private State state;
	private int stateTick;
	private boolean paused;


	public static void main(String[] args) {
		GameSokoban main = new GameSokoban();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Sokoban");
		s.setWidth(Level.MAX_COLS*CELL + GAP*2);
		s.setHeight(Level.MAX_ROWS*CELL + TOP + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	protected void initialize1() {
		levelIndex = 0;
		level = new Level(levelIndex);
		facing = Dir.EAST;
		paused = false;
		setState(State.PLAYING);
		addDraw(new GameDrawing());
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) {
			restart();
			return;
		}
		if(k.F2()) exit();
		if(k.in().p()) paused = !paused;
		if(paused) return;

		switch(state) {
			case PLAYING: playing(k); break;
			case CLEARED: stateTick++; if(stateTick>CLEAR_TICKS) nextLevel(); break;
			default: break;
		}
	}

	private void playing(Keyboard k) {
		if(k.in().u()) level.undo();
		else if(k.in().r()) level = new Level(levelIndex);
		else handleMove(k);

		if(level.isSolved()) setState(State.CLEARED);
	}

	private void handleMove(Keyboard k) {
		Dir dir = readDir(k);
		if(dir==null) return;
		facing = dir;
		level.move(dir);
	}

	private Dir readDir(Keyboard k) {
		if(k.in().up()) return Dir.NORTH;
		if(k.in().down()) return Dir.SOUTH;
		if(k.in().left()) return Dir.WEST;
		if(k.in().right()) return Dir.EAST;
		return null;
	}

	private void setState(State s) {
		state = s;
		stateTick = 0;
	}

	private void nextLevel() {
		levelIndex++;
		if(levelIndex>=Level.COUNT) {
			setState(State.FINISHED);
			return;
		}
		level = new Level(levelIndex);
		setState(State.PLAYING);
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
			drawBoard();
			drawHud();
			drawMessage();
		}

		private void drawBoard() {
			for(int row=0;row<level.getRows();row++)
				for(int col=0;col<level.getCols();col++)
					drawCell(col, row);
			for(int row=0;row<level.getRows();row++)
				for(int col=0;col<level.getCols();col++)
					if(level.isBox(col, row)) drawBox(col, row);
			drawPlayer();
		}

		private void drawCell(int col, int row) {
			double x = col*CELL;
			double y = row*CELL;
			if(level.isWall(col, row)) {
				drawWall(col, row, x, y);
				return;
			}
			fillRect(COLOR_FLOOR, p(x, y), CELL, CELL);
			if(level.isTarget(col, row))
				fillSquareC(COLOR_TARGET, p(x+CELL*0.5, y+CELL*0.5), CELL*0.16);
		}

		private void drawWall(int col, int row, double x, double y) {
			fillRect(COLOR_WALL, p(x, y), CELL, CELL);
			if(!level.isWall(col, row-1)) drawLine(COLOR_WALL_EDGE, p(x, y), p(x+CELL, y));
			if(!level.isWall(col, row+1)) drawLine(COLOR_WALL_EDGE, p(x, y+CELL), p(x+CELL, y+CELL));
			if(!level.isWall(col-1, row)) drawLine(COLOR_WALL_EDGE, p(x, y), p(x, y+CELL));
			if(!level.isWall(col+1, row)) drawLine(COLOR_WALL_EDGE, p(x+CELL, y), p(x+CELL, y+CELL));
		}

		private void drawBox(int col, int row) {
			double x = col*CELL;
			double y = row*CELL;
			boolean onTarget = level.isTarget(col, row);
			Color body = onTarget ? COLOR_BOX_ON_TARGET : COLOR_BOX;
			Color edge = onTarget ? COLOR_BOX_ON_TARGET_EDGE : COLOR_BOX_EDGE;
			fillRect(body, p(x+CELL*0.08, y+CELL*0.08), CELL*0.84, CELL*0.84);
			drawRect(edge, p(x+CELL*0.08, y+CELL*0.08), CELL*0.84, CELL*0.84);
			drawLine(edge, p(x+CELL*0.08, y+CELL*0.08), p(x+CELL*0.92, y+CELL*0.92));
			drawLine(edge, p(x+CELL*0.92, y+CELL*0.08), p(x+CELL*0.08, y+CELL*0.92));
		}

		private void drawPlayer() {
			double x = level.getPlayerCol()*CELL;
			double y = level.getPlayerRow()*CELL;
			Point0 pc = p(x+CELL*0.5, y+CELL*0.5);
			fillRoundC(COLOR_PLAYER, pc, CELL*0.38);
			drawFace(x, y);
		}

		private void drawFace(double x, double y) {
			double fx = x+CELL*0.5 + facing.getDx()*CELL*0.16;
			double fy = y+CELL*0.5 + facing.getDy()*CELL*0.16;
			fillRoundC(COLOR_PLAYER_FACE, p(fx, fy), CELL*0.1);
		}

		private void drawHud() {
			drawString(COLOR_TEXT, fontBold(16), p(2, -TOP+28), "NIVEAU  "+Math.min(levelIndex+1, Level.COUNT)+"/"+Level.COUNT);
			drawString(COLOR_TEXT, fontBold(16), p(2, -TOP+48), "MOUVEMENTS  "+level.getMoves()+"   POUSSEES  "+level.getPushes());

			double y = level.getRows()*CELL + BOTTOM*0.55;
			drawStringC(COLOR_LABEL, fontPlain(11), p(Level.MAX_COLS*CELL/2.0, y),
					"Fleches deplacer   U annuler   R recommencer   P pause   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			double w = level.getCols()*CELL;
			double h = level.getRows()*CELL;
			if(paused) {
				fillRect(COLOR_VEIL, p(0, 0), w, h);
				drawStringC(COLOR_TEXT, fontBold(26), p(w/2, h/2), "PAUSE");
				return;
			}
			if(state==State.CLEARED) {
				fillRect(COLOR_VEIL, p(0, 0), w, h);
				drawStringC(COLOR_TEXT, fontBold(24), p(w/2, h/2), "NIVEAU "+(levelIndex+1)+" TERMINE");
				return;
			}
			if(state==State.FINISHED) {
				fillRect(COLOR_VEIL, p(0, 0), w, h);
				drawStringC(COLOR_TARGET, fontBold(26), p(w/2, h/2-14), "BRAVO");
				drawStringC(COLOR_TEXT, fontPlain(14), p(w/2, h/2+16), "Tous les niveaux resolus - F1 pour rejouer");
			}
		}
	}
}
