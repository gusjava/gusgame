package gus.game5.main.game.p1.o.blockrun;

import static gus.game5.core.util.UtilGui.action;

import java.awt.Color;
import java.awt.Font;

import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.point.point2.Point2D0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRect;
import gus.game5.core.shape.ShapeSquare;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilRandom;

public class GameBlockRun extends Game1 {
	
	public static final String TITLE = "Block Run";
	
	public static final int GAME_HEIGHT = 700;
	public static final int GAME_WIDTH = 1100;
	public static final int INIT_SIZE = 10;
	public static final int MIN_SIZE = 4;
	
	public static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 12);
	
	public static final int[][] CONFIG = new int[][] {
		{15, 5, 5},
		{15, 5, 5}, //green, red, blue
		{18, 5, 5},
		{18, 6, 5},
		{21, 6, 5},
		{21, 7, 5},
		{21, 7, 4},
		{24, 7, 4},
		{24, 8, 4},
		{27, 8, 4},
		{27, 9, 4},
		{30, 9, 4},
		{30, 10, 4},
		{35, 10, 4},
		{35, 12, 4},
		{40, 12, 4},
		{40, 15, 4}
	};
	
	public static void main(String[] args) {
		GameBlockRun main = new GameBlockRun();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(FONT);
	}
	
	/*
	 * DATA
	 */
	
	private Block block;
	private ShapeList<Bacteria> bacteriaList;
	private DrawingText gameOverDisplay;
	private boolean gameOver;
	private double rate;
	private int score;
	private int level;
	
	
	protected void initialize1() {
		gameOver = false;
		rate = 8;
		score = 0;
		level = 0;
		bacteriaList = newShapeList();
		block = new Block();
		newShape(block);
		
		newDrawingText(p1(10,20), ()->"rate: "+UtilDisplay.dec2(rate));
		newDrawingText(p1(10,40), ()->"score: "+score);
		newDrawingText(p1(10,60), ()->"level: "+level);
		newDrawingText(p1(10,80), ()->"time: "+getCount());
		
		gameOverDisplay = newDrawingTextC(Color.BLACK, gameCenter(), "GAME OVER");
		gameOverDisplay.setFont(FONT);
		gameOverDisplay.setFontBold(50);
		
		block.setDrawable(()->!gameOver);
		gameOverDisplay.setDrawable(()->gameOver);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(gameOver) return;
		
		if(getCount()%1000==0) {
			if(level<CONFIG.length-1) level++;
		}
		
		rate *= 0.9999;
		if(UtilRandom.chance(rate)) {
			int type = UtilRandom.chances(CONFIG[level]);
			int v = new int[] {1,0,-1}[type];
			
			double x = gameWidth() + 50;
			double y = UtilRandom.randomDouble(gameHeight());
			double w = UtilRandom.randomDouble(20, 60);
			
			Bacteria bacteria = new Bacteria(p2(x,y,-4,0), w, w, v);
			bacteriaList.add(bacteria);
		}
		
		for(Bacteria bacteria : bacteriaList.findAllInter(block)) {
			int value = bacteria.takeValue();
			if(value==0) {
				gameOver = true;
				break;
			}
			block.changeSize(value);
		}
		
		goNext();
		clean();
	}
	
	
	private class Block extends ShapeSquare {
		private int size;

		public Block() {
			super(new Point2D0(mouse()::point), INIT_SIZE*2);
			size = INIT_SIZE;
		}
		
		protected void drawShape() {
			fillSquareC(Color.GRAY, getLength());
			drawSquareC(Color.BLACK, getLength());
		}
		
		public void changeSize(int d) {
			size += d;
			if(size<MIN_SIZE) size = MIN_SIZE;
			setLength(size*2);
			
			if(d<0) score++;
		}
	}
	
	private class Bacteria extends ShapeRect {
		private int value;
		private boolean taken = false;
		
		public Bacteria(Point2 anchor, double width, double height, int value) {
			super(anchor, width, height);
			this.value = value;
		}
		
		protected void drawShape() {
			Color c = value<0 ? Color.BLUE : value==0 ? Color.RED : Color.GREEN;
			fillRectC(c, getWidth(), getHeight());
			drawRectC(Color.BLACK, getWidth(), getHeight());
		}
		
		public boolean isOver() {
			return getAnchor().getX()<-100 || taken;
		}
		
		public int takeValue() {
			if(value!=0) taken = true;
			return value;
		}
	}
}
