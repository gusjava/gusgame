package gus.game5.main.game.minesweeper;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;

public class GameMinesweeper extends Game1 {
	
	/*
	 * CONSTANTS
	 */
	
	public static final String LEVEL1 = "Beginner";
	public static final int X1 = 9;
	public static final int Y1 = 9;
	public static final int NB1 = 10;

	public static final String LEVEL2 = "Intermediate";
	public static final int X2 = 16;
	public static final int Y2 = 16;
	public static final int NB2 = 40;

	public static final String LEVEL3 = "Expert";
	public static final int X3 = 30;
	public static final int Y3 = 16;
	public static final int NB3 = 99;
	
	public static final int GAP = 10;
	public static final int CELL = 25;
	public static final int FONT_SIZE = 18;
	
	public static final Color COLOR_BORDER = new Color(250,250,250);
	public static final Color COLOR_REVEALED = Color.LIGHT_GRAY;
	public static final Color COLOR_HIDDEN = Color.GRAY;
	public static final Color COLOR_MINE = Color.BLACK;
	public static final Color COLOR_DEATH = Color.YELLOW;
	public static final Color COLOR_LOST = Color.RED;
	public static final Color COLOR_WON = Color.ORANGE;
	
	public static final int STATE_START = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_LOST = 2;
	public static final int STATE_WON = 3;

	/*
	 * VARIABLES
	 */
	
	
	private GameDrawing drawing;
	private Data data;
	private Analyzer analyzer;
	private int state = STATE_START;
//	private String level = LEVEL3;
	private int x = X3;
	private int y = Y3;
	private int nb = NB3;
	
	
	public static void main(String[] args) {
		GameMinesweeper main = new GameMinesweeper();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - Mine sweeper");
		s.setWidth(X3*CELL + GAP*2);
		s.setHeight(Y3*CELL + GAP*2);
		s.setSleep(50);
		s.setBackground(COLOR_BORDER);
	}
	
	protected void initialize1() {
		drawing = new GameDrawing();
		addDraw(drawing);
		
		data = new Data(x,y,nb);
		analyzer = new Analyzer(data);
		state = STATE_START;
	}

	protected void turn() {
		Keyboard k = keyboard();
		Mouse m = mouse();
		
		if(k.in().F1())		restartAsBeginner();
		if(k.in().F2())		restartAsIntermediate();
		if(k.in().F3())		restartAsExpert();
		if(k.in().F4())		exit();
		
		if(k.in().num0())	analyzer.setDepth(0);
		if(k.in().num1())	analyzer.setDepth(1);
		if(k.in().num2())	analyzer.setDepth(2);
		if(k.in().num3())	analyzer.setDepth(3);
		if(k.in().num4())	analyzer.setDepth(4);
		if(k.in().num5())	analyzer.setDepth(5);
		if(k.in().num6())	analyzer.setDepth(6);
		if(k.in().num7())	analyzer.setDepth(7);
		
		if(gameOver()) return;
		
		if(m.button1().justPressed() || k.in().space() || k.out().space()) {
			int i = getSelectedI();
			int j = getSelectedJ();
			
			if(state==STATE_START) {
				int attempt = 0;
				while(attempt<100 && !handleFirstClick(i,j)) {
					resetData();
					attempt++;
				}
				state = STATE_RUNNING;
			}
			else handleClick(i,j);
		}
	}

	
	public class GameDrawing extends Drawing1 {
		
		public GameDrawing() {
			super();
			initOrigin().setXY(GAP, GAP);
		}

		protected void draw() {
			fillRect(COLOR_HIDDEN, x*CELL, y*CELL);
			
			for(int i=0;i<x;i++)
			for(int j=0;j<y;j++) {
				int c = data.getMineC(i, j);
				if(c>=0 || data.isDead(i,j)) {
					Color bgCell = getBackgroundColor(i,j);
					fillSquare(bgCell, p(i*CELL, j*CELL), CELL);
				}
				drawSquare(COLOR_BORDER, p(i*CELL, j*CELL), CELL);
				
				if(c>0) drawString(Utils.colorFor(c), fontBold(FONT_SIZE), p(i*CELL+7,j*CELL+CELL-5),""+c);
			}
			
			if(gameOver()) {
				for(int i=0;i<x;i++)
				for(int j=0;j<y;j++) {
					if(data.isMineCell(i,j)) {
						Color mineColor = data.isDead(i,j) ? COLOR_DEATH : COLOR_MINE;
						fillRoundC(mineColor, cellCenter(i,j), (CELL/2)-2);
						if(data.isDead(i,j)) drawStarC(Color.RED, cellCenter(i,j), (CELL/2)-2, 12);
					}
				}
				if(gameLost()) drawString(COLOR_LOST, fontBold(50), p(100, 100), "You lost (-_-;)");
				if(gameWon()) drawString(COLOR_WON, fontBold(50), p(100, 100), "You won (^o^)/");
			}
			else {
				if(analyzer.hasDepth1()) {
					for(int i=0;i<x;i++)
					for(int j=0;j<y;j++) {
//						int c = data.getHiddenC(i, j);
//						int d = data.getAnalyzedCode(i, j);
//						if(c>0) drawString(i*CELL+2,j*CELL+CELL-2,""+c+","+d, Color.BLACK, font(8));
						
						Color analyzeColor = analyzer.getColorHidden(i, j);
						if(analyzer.isHiddenMine(i,j)) fillRoundC(analyzeColor, cellCenter(i,j), (CELL/2)-4);
						else if(analyzer.isHiddenSafe(i, j)) drawStarC(analyzeColor, cellCenter(i,j), (CELL/2)-4, 8);
					}
				}
			}
		}
		
		public Point0 cellCenter(int i, int j) {
			return p((i+0.5)*CELL, (j+0.5)*CELL);
		}
	}
	
	private Color getBackgroundColor(int i, int j) {
		Color color = analyzer.getColorRevealed(i,j);
		return color!=null ? color : COLOR_REVEALED;
	}
	
	private void restartAsBeginner() {
//		level = LEVEL1;
		x = X1;
		y = Y1;
		nb = NB1;
		restart();
	}
	
	private void restartAsIntermediate() {
//		level = LEVEL2;
		x = X2;
		y = Y2;
		nb = NB2;
		restart();
	}
	
	private void restartAsExpert() {
//		level = LEVEL3;
		x = X3;
		y = Y3;
		nb = NB3;
		restart();
	}
	
	
	private void resetData() {
		data = new Data(x,y,nb);
		analyzer = new Analyzer(data);
	}
	
	
	private boolean handleFirstClick(int i, int j) {
		if(data.isMineCell(i,j)) return false;
		analyze(i,j);
		return data.hasRevealedMany(); 
	}
	
	private void handleClick(int i, int j) {
		if(data.isMineCell(i,j)) {
			data.setDead(i,j);
			state = STATE_LOST;
		}
		else {
			analyze(i,j);
		}
	}
	
	
	private void analyze(int i, int j) {
		data.revealCell(i,j);
		if(data.hasRevealedAll()) state = STATE_WON;
		else analyzer.analyzeGrid();
	}
	
	
	private boolean gameOver() {
		return gameWon() || gameLost();
	}
	
	private boolean gameWon() {
		return state==STATE_WON;
	}
	
	private boolean gameLost() {
		return state==STATE_LOST;
	}
	
	private int getSelectedI() {
		return (int) ((mouse().pointCurrent().getX()-GAP)/CELL);
	}
	
	private int getSelectedJ() {
		return (int) ((mouse().pointCurrent().getY()-GAP)/CELL);
	}
	
}
