package gus.game5.main.game.p1.snake;


import static gus.game5.core.util.UtilRandom.randomInt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class GameSnake extends Game1 {

	public static final int X = 60;
	public static final int Y = 60;
	public static final int GAP = 10;
	public static final int RATIO = 8;
	public static final int INIT_LENGTH = 20;
	public static final int INIT_TAIL_X = 10;
	public static final int INIT_TAIL_Y = 10;
	public static final int GROWTH = 10;
	public static final long SLEEP = 8;
	
	public static final Color COLOR_SNAKE = Color.GREEN;
	public static final Color COLOR_FOOD = Color.MAGENTA;
	public static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final Color COLOR_BORDER = Color.LIGHT_GRAY;
	public static final Color COLOR_GAMEOVER = Color.GRAY;
	

	private List<Cell> snake;
	private Cell food;
	private Dir d;
	private int size = 0;
	private boolean gameOver;
	
	
	public static void main(String[] args) {
		GameSnake main = new GameSnake();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Snake");
		s.setWidth(X*RATIO + GAP*2);
		s.setHeight(Y*RATIO + GAP*2);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BORDER);
	}
	
	protected void initialize1() {
		d = Dir.SOUTH;
		size = INIT_LENGTH;
		gameOver = false;
		
		snake = new ArrayList<>();
		for(int i=0;i<INIT_LENGTH;i++)
		snake.add(new Cell(INIT_TAIL_X, INIT_TAIL_Y+i, d));

		food = newFood();
		
		GameDrawing drawing = new GameDrawing();
		
		addDraw(drawing);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(gameOver) return;
		
		if(d.isEast() || d.isWest()) {
			if(k.in().up()) d = Dir.NORTH;
			if(k.in().down()) d = Dir.SOUTH;
		}
		else if(d.isNorth() || d.isSouth()) {
			if(k.in().right()) d = Dir.EAST;
			if(k.in().left()) d = Dir.WEST;
		}
		
		if(gapLeft()==0) {
			Cell newHead = snake.get(snake.size()-1).buildNext(d);
			if(insideSnake(newHead) || outsideGame(newHead)) {
				gameOver = true;
				return;
			}
			snake.add(newHead);
			while(snake.size()>size) snake.remove(0);
			
			if(newHead.equals(food)) {
				food = newFood();
				size += GROWTH;
			}
		}
	}


	
	private Cell newFood() {
		Cell newFood = new Cell(randomInt(X), randomInt(Y));
		while(insideSnake(newFood))  newFood = new Cell(randomInt(X), randomInt(Y));
		return newFood;
	}
	
	private boolean insideSnake(Cell c) {
		for(int i=0;i<snake.size();i++)
			if(snake.get(i).equals(c)) return true;
		return false;
	}
	
	private boolean outsideGame(Cell c) {
		return c.getX()<0 || c.getX()>=X || c.getY()<0 || c.getY()>=Y;
	}
	
	private boolean isGrowing() {
		return snake.size()<size;
	}
	
	private int gapLeft() {
		return (int) getCount()%RATIO;
	}

	
	public enum Dir {
		NORTH,WEST,SOUTH,EAST;
		
		public boolean isNorth() {return this==NORTH;}
		public boolean isWest() {return this==WEST;}
		public boolean isSouth() {return this==SOUTH;}
		public boolean isEast() {return this==EAST;}
	}
	
	private class Cell {
		private int x;
		private int y;
		private Dir d;
		
		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Cell(int x, int y, Dir d) {
			this(x, y);
			this.d = d;
		}
		
		public boolean equals(Cell cell) {
			return cell!=null && x==cell.x && y==cell.y;
		}
		
		public Cell buildNext(Dir nextD) {
			int nextX = nextD.isWest() ? x-1 : nextD.isEast() ? x+1 : x;
			int nextY = nextD.isNorth() ? y-1 : nextD.isSouth() ? y+1 : y;
			return new Cell(nextX, nextY, nextD);
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public Dir getD() {
			return d;
		}
	}
	
	public class GameDrawing extends Drawing1 {
		
		public GameDrawing() {
			super();
			initOrigin().setXY(GAP, GAP);
			setColor(COLOR_SNAKE);
		}

		protected void draw() {
			fillRect(COLOR_BACKGROUND, X*RATIO, Y*RATIO);
			fillSquare(COLOR_FOOD, p(food.getX()*RATIO, food.getY()*RATIO), RATIO);
			
			if(gameOver) {
				setColor(COLOR_GAMEOVER);
				for(int i=0;i<snake.size();i++)
					drawBody(snake.get(i));
				drawString(font(30), p(20*RATIO, 20*RATIO), "Game Over");
				return;
			}
			
			int nb = snake.size();
			for(int i=0;i<nb;i++) {
				Cell c = snake.get(i);
				if(i==nb-1) drawHead(c);
				else if(i==0 && !isGrowing()) drawTail(c);
				else drawBody(c);
			}
		}
		
		private void drawBody(Cell c) {
			fillSquare(p(c.getX()*RATIO, c.getY()*RATIO), RATIO);
		}
		
		private void drawHead(Cell c) {
			int x = getHeadX(c);
			int y = getHeadY(c);
			int w = getHeadW(c);
			int h = getHeadH(c);
			fillRect(p(x, y), w, h);
		}
		
		private int getHeadX(Cell c) {
			switch(c.getD()) {
				case NORTH: return c.getX()*RATIO;
				case SOUTH: return c.getX()*RATIO;
				case WEST: return (c.getX()+1)*RATIO-gapLeft();
				case EAST: return c.getX()*RATIO;
				default: return c.getX()*RATIO;
			}
		}
		
		private int getHeadY(Cell c) {
			switch(c.getD()) {
				case NORTH: return (c.getY()+1)*RATIO-gapLeft();
				case SOUTH: return c.getY()*RATIO;
				case WEST: return c.getY()*RATIO;
				case EAST: return c.getY()*RATIO;
				default: return c.getY()*RATIO;
			}
		}
		
		private int getHeadW(Cell c) {
			switch(c.getD()) {
				case NORTH: return RATIO;
				case SOUTH: return RATIO;
				case WEST: return gapLeft();
				case EAST: return gapLeft();
				default: return RATIO;
			}
		}
		
		private int getHeadH(Cell c) {
			switch(c.getD()) {
				case NORTH: return gapLeft();
				case SOUTH: return gapLeft();
				case WEST: return RATIO;
				case EAST: return RATIO;
				default: return RATIO;
			}
		}
		
		
		
		
		private void drawTail(Cell c) {
			int x = getTailX(c);
			int y = getTailY(c);
			int w = getTailW(c);
			int h = getTailH(c);
			fillRect(p(x, y), w, h);
		}
		
		private int getTailX(Cell c) {
			switch(c.getD()) {
				case NORTH: return c.getX()*RATIO;
				case SOUTH: return c.getX()*RATIO;
				case WEST: return c.getX()*RATIO;
				case EAST: return c.getX()*RATIO+gapLeft();
				default: return c.getX()*RATIO;
			}
		}
		
		private int getTailY(Cell c) {
			switch(c.getD()) {
				case NORTH: return c.getY()*RATIO;
				case SOUTH: return c.getY()*RATIO+gapLeft();
				case WEST: return c.getY()*RATIO;
				case EAST: return c.getY()*RATIO;
				default: return c.getY()*RATIO;
			}
		}
		
		private int getTailW(Cell c) {
			switch(c.getD()) {
				case NORTH: return RATIO;
				case SOUTH: return RATIO;
				case WEST: return RATIO-gapLeft();
				case EAST: return RATIO-gapLeft();
				default: return RATIO;
			}
		}
		
		private int getTailH(Cell c) {
			switch(c.getD()) {
				case NORTH: return RATIO-gapLeft();
				case SOUTH: return RATIO-gapLeft();
				case WEST: return RATIO;
				case EAST: return RATIO;
				default: return RATIO;
			}
		}
	}
}
