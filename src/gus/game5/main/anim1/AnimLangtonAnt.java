package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.alter.AlterDilate;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;

public class AnimLangtonAnt extends Game1 {
	
	public static final int NORTH  = 0;
	public static final int EAST  = 1;
	public static final int SOUTH  = 2;
	public static final int WEST  = 3;
	
	public static final int ON = 1;
	public static final int OFF = -1;
	
	public static final int X = 150;
	public static final int Y = 150;
	public static final int GAP = 10;
	public static final int RATIO = 4;
	
	public static final Color COLOR_ON = Color.YELLOW;
	public static final Color COLOR_OFF = Color.BLACK;
	public static final Color COLOR_BORDER = Color.LIGHT_GRAY;
	public static final Color COLOR_ANT = Color.CYAN;
	
	private int[][] state;
	private int i1;
	private int j1;
	private int dir;
	
	
	public static void main(String[] args) {
		AnimLangtonAnt main = new AnimLangtonAnt();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Langton ant");
		s.setWidth(X*RATIO);
		s.setHeight(Y*RATIO);
		s.setSleep(0);
		s.setBackground(COLOR_BORDER);
	}
	
	
	protected void initialize1() {
		state = new int[X][Y];
		for(int i=0;i<X;i++)
		for(int j=0;j<Y;j++)
			state[i][j] = OFF;
		
		i1 = (int) X/2;
		j1 = (int) Y/2;
		dir = NORTH;
		
		addDraw(new GameDrawing());
		newDrawingText(Color.WHITE, new Point1(30, 30), ()->"turn: "+getCount());
	}

	protected void turn() {
		Keyboard k = keyboard();
		
		if(k.F1())		restart();
		if(k.F2())		exit();
		
		if(antInsideBoard()) moveAnt();
		else stop();
	}
	
	private void moveAnt() {
		dir -= state[i1][j1];
		if(dir==4) dir = 0;
		else if(dir==-1) dir = 3;
		
		state[i1][j1] *= -1;
		
		if(dir==NORTH) j1--;
		else if(dir==EAST) i1++;
		else if(dir==SOUTH) j1++;
		else if(dir==WEST) i1--;
	}
	
	private boolean antInsideBoard() {
		return i1>=0 && i1<X && j1>=0 && j1<Y;
	}
	

	public class GameDrawing extends Drawing1 {
		public GameDrawing() {
			addAlter(new AlterDilate(()->RATIO));
		}
		protected void draw() {
			fillRect(COLOR_OFF, X, Y);
			for(int i=0;i<X;i++)
			for(int j=0;j<Y;j++)
			if(state[i][j]==ON) fillSquare(COLOR_ON, p(i, j), 1);
			
			drawSquare(COLOR_ANT, p(i1, j1), 1);
		}
	}
}
