package gus.game5.main.game.p1.c.pipemania;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.util.Dir;
import gus.game5.core.util.UtilGrid;
import gus.game5.core.util.UtilRandom;

public class GamePipeMania extends Game1 {

	public static final long SLEEP = 16;
	public static final long TICKS_PER_SECOND = 1000/SLEEP;

	public static final int ROWS = 8;
	public static final int COLS = 10;
	public static final int CELL = 48;
	public static final int GAP = 16;
	public static final int TOP = 90;
	public static final int BOTTOM = 40;

	public static final int NEXT_COUNT = 5;
	public static final int START_COUNTDOWN = (int) (4*TICKS_PER_SECOND);
	public static final int START_INTERVAL = 24;
	public static final int MIN_INTERVAL = 8;
	public static final int SPEEDUP_EVERY = 6;

	public static final int[] START_CELL = {0,0};

	public static final Color COLOR_BG = new Color(10,10,16);
	public static final Color COLOR_GRID = new Color(60,60,70);
	public static final Color COLOR_TILE = new Color(30,30,38);
	public static final Color COLOR_PIPE = new Color(150,150,165);
	public static final Color COLOR_PIPE_EDGE = new Color(100,100,115);
	public static final Color COLOR_FLOWED = new Color(60,190,230);
	public static final Color COLOR_SOURCE = new Color(230,150,40);
	public static final Color COLOR_DRAIN = new Color(90,200,110);
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);

	public enum State {BUILDING, FLOWING, WON, LOST}


	public static void main(String[] args) {
		GamePipeMania main = new GamePipeMania();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Pipe Mania");
		s.setWidth(COLS*CELL + GAP*2);
		s.setHeight(ROWS*CELL + TOP + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private int[] endCell;
	private PipeShape[][] grid;
	private Deque<PipeShape> queue;
	private Set<Long> flowed;

	private State state;
	private int countdown;
	private int flowInterval;
	private int flowTimer;
	private int[] flowCell;
	private Dir flowIncoming;
	private int flowedCount;

	protected void initialize1() {
		endCell = new int[] {ROWS-1, COLS-1};
		grid = new PipeShape[ROWS][COLS];

		queue = new ArrayDeque<>();
		for(int i=0;i<NEXT_COUNT;i++) queue.addLast(randomShape());

		flowed = new HashSet<>();
		flowedCount = 0;

		countdown = START_COUNTDOWN;
		flowInterval = START_INTERVAL;
		setState(State.BUILDING);

		addDraw(new GameDrawing());
	}

	private PipeShape randomShape() {
		return UtilRandom.randomElement(PipeShape.values());
	}

	/*
	 * TURN
	 */

	protected void turn() {
		if(handleRestartExit()) return;

		switch(state) {
			case BUILDING: building(); break;
			case FLOWING: flowing(); break;
			default: break;
		}

		handlePlacement();
	}

	private void building() {
		countdown--;
		if(countdown<=0) startFlowing();
	}

	private void startFlowing() {
		setState(State.FLOWING);
		flowCell = new int[] {START_CELL[0]+Dir.SOUTH.getDy(), START_CELL[1]+Dir.SOUTH.getDx()};
		flowIncoming = Dir.NORTH;
		flowTimer = flowInterval;
	}

	private void flowing() {
		flowTimer--;
		if(flowTimer>0) return;
		flowTimer = flowInterval;
		advanceFlow();
	}

	private void advanceFlow() {
		if(flowCell[0]<0 || flowCell[0]>=ROWS || flowCell[1]<0 || flowCell[1]>=COLS) {
			setState(State.LOST);
			return;
		}
		if(same(flowCell, endCell)) {
			setState(State.WON);
			return;
		}

		PipeShape shape = grid[flowCell[0]][flowCell[1]];
		if(shape==null || !shape.hasOpening(flowIncoming)) {
			setState(State.LOST);
			return;
		}

		flowed.add(key(flowCell));
		flowedCount++;
		if(flowedCount%SPEEDUP_EVERY==0) flowInterval = Math.max(MIN_INTERVAL, flowInterval-2);

		Dir outgoing = shape.otherOpening(flowIncoming);
		flowCell = new int[] {flowCell[0]+outgoing.getDy(), flowCell[1]+outgoing.getDx()};
		flowIncoming = outgoing.opposite();
	}

	private void handlePlacement() {
		if(state==State.WON || state==State.LOST) return;
		if(!mouse().button1().justPressed()) return;

		int[] cell = cellAt(mouse().button1().justPressedPoint());
		if(cell==null) return;
		if(same(cell, START_CELL) || same(cell, endCell)) return;
		if(grid[cell[0]][cell[1]]!=null) return;

		grid[cell[0]][cell[1]] = queue.pollFirst();
		queue.addLast(randomShape());
	}

	private int[] cellAt(Point0 p) {
		return UtilGrid.cellAt(p, GAP, TOP, CELL, ROWS, COLS);
	}

	private boolean same(int[] a, int[] b) {
		return UtilGrid.same(a, b);
	}

	private long key(int[] cell) {
		return cell[0]*1000L + cell[1];
	}

	private void setState(State s) {
		state = s;
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
			drawGrid();
			drawPipes();
			drawStartEnd();
			drawQueue();
			drawHud();
			drawMessage();
		}

		private void drawGrid() {
			for(int r=0;r<ROWS;r++)
			for(int c=0;c<COLS;c++) {
				double x = c*CELL;
				double y = r*CELL;
				fillRect(COLOR_TILE, p(x+1,y+1), CELL-2, CELL-2);
				drawRect(COLOR_GRID, p(x,y), CELL, CELL);
			}
		}

		private void drawPipes() {
			for(int r=0;r<ROWS;r++)
			for(int c=0;c<COLS;c++) {
				PipeShape shape = grid[r][c];
				if(shape==null) continue;
				boolean isFlowed = flowed.contains(key(new int[] {r,c}));
				drawPipe(r, c, shape, isFlowed);
			}
		}

		private void drawPipe(int r, int c, PipeShape shape, boolean isFlowed) {
			Point0 center = cellCenter(r, c);
			Color body = isFlowed ? COLOR_FLOWED : COLOR_PIPE;
			drawPipeArm(center, shape.getA(), body);
			drawPipeArm(center, shape.getB(), body);
			fillRoundC(body, center, CELL*0.18);
		}

		private void drawPipeArm(Point0 center, Dir dir, Color color) {
			Point0 edge = p(center.getX()+dir.getDx()*CELL*0.5, center.getY()+dir.getDy()*CELL*0.5);
			drawThickLine(color, center, edge, CELL*0.3);
		}

		private void drawStartEnd() {
			Point0 startCenter = cellCenter(START_CELL[0], START_CELL[1]);
			fillRoundC(COLOR_SOURCE, startCenter, CELL*0.3);
			drawPipeArm(startCenter, Dir.SOUTH, COLOR_SOURCE);

			Point0 endCenter = cellCenter(endCell[0], endCell[1]);
			fillRoundC(COLOR_DRAIN, endCenter, CELL*0.3);
		}

		private Point0 cellCenter(int r, int c) {
			return p(c*CELL+CELL*0.5, r*CELL+CELL*0.5);
		}

		private void drawQueue() {
			drawString(COLOR_LABEL, fontPlain(12), p(2, -TOP+22), "PROCHAINS TUYAUX");
			int i = 0;
			for(PipeShape shape : queue) {
				double x = 10 + i*42;
				double y = -TOP+34;
				fillRect(COLOR_TILE, p(x,y), 36, 36);
				drawRect(COLOR_GRID, p(x,y), 36, 36);
				Point0 center = p(x+18, y+18);
				drawPipeArm(center, shape.getA(), COLOR_PIPE);
				drawPipeArm(center, shape.getB(), COLOR_PIPE);
				i++;
			}
		}

		private void drawHud() {
			drawString(COLOR_TEXT, fontBold(16), p(2, -TOP+78), "FLUX  "+flowedCount);

			String message;
			if(state==State.BUILDING) message = "Flux dans "+(countdown/TICKS_PER_SECOND+1)+"s";
			else message = "Intervalle  "+flowInterval;
			drawStringC(COLOR_LABEL, fontPlain(13), p(COLS*CELL-80, -TOP+78), message);

			double y = ROWS*CELL + BOTTOM*0.55;
			drawStringC(COLOR_LABEL, fontPlain(11), p(COLS*CELL/2.0, y),
					"Cliquer une case vide pour y poser le prochain tuyau   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			double w = COLS*CELL;
			double h = ROWS*CELL;
			if(state==State.WON) {
				fillRect(COLOR_VEIL, p(0,0), w, h);
				drawStringC(COLOR_DRAIN, fontBold(26), p(w/2, h/2-14), "CIRCUIT RACCORDE");
				drawStringC(COLOR_TEXT, fontPlain(14), p(w/2, h/2+16), "Flux  "+flowedCount+"  -  F1 pour rejouer");
				return;
			}
			if(state==State.LOST) {
				fillRect(COLOR_VEIL, p(0,0), w, h);
				drawStringC(new Color(220,80,80), fontBold(26), p(w/2, h/2-14), "FUITE");
				drawStringC(COLOR_TEXT, fontPlain(14), p(w/2, h/2+16), "Flux  "+flowedCount+"  -  F1 pour rejouer");
			}
		}
	}
}
