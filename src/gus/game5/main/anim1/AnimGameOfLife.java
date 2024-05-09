package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.alter.AlterDilate;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class AnimGameOfLife extends Game1 {
	
	public static final int X = 400;
	public static final int Y = 300;
	public static final int GAP = 10;
	public static final int RATIO = 2;
	
	public static final Color COLOR_POINT = Color.BLUE;
	public static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final Color COLOR_BORDER = Color.LIGHT_GRAY;

	private boolean[][] state;
	
	public static void main(String[] args) {
		AnimGameOfLife main = new AnimGameOfLife();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Game of Life");
		s.setWidth(X*RATIO);
		s.setHeight(Y*RATIO);
		s.setSleep(50);
		s.setBackground(COLOR_BORDER);
	}
	
	protected void initialize1() {
		state = initialRandomState();
		addDraw(new GameDrawing());
	}

	protected void turn() {
		state = computeState();
		
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();
	}

	public class GameDrawing extends Drawing1 {
		public GameDrawing() {
			setColor(COLOR_POINT);
			addAlter(new AlterDilate(()->RATIO));
		}
		protected void draw() {
			fillRect(COLOR_BACKGROUND, X, Y);
			for(int i=0;i<X;i++)
			for(int j=0;j<Y;j++)
			if(state[i][j]) fillSquare(p(i, j), 1);
		}
	}
	
	private boolean[][] initialRandomState() {
		boolean[][] b = new boolean[X][Y];
		for(int i=0;i<X;i++)
		for(int j=0;j<Y;j++)
		b[i][j] = Math.random()<0.5;
		return b;
	}
	
	private boolean[][] computeState() {
		boolean[][] b1 = new boolean[X][Y];
		for (int i = 0; i < X; i++)
		for (int j = 0; j < Y; j++)
		b1[i][j] = findNextValue(i, j);
		return b1;
	}

	private boolean findNextValue(int i, int j) {
		int n = aliveNeighbours(i, j);
		if (state[i][j]) return n == 2 || n == 3;
		return n == 3;
	}

	private int aliveNeighbours(int i, int j) {
		int i1 = i > 0 ? i - 1 : X - 1;
		int i2 = i < X - 1 ? i + 1 : 0;
		int j1 = j > 0 ? j - 1 : Y - 1;
		int j2 = j < Y - 1 ? j + 1 : 0;

		int n = 0;

		if (state[i1][j1]) n++;
		if (state[i1][j]) n++;
		if (state[i1][j2]) n++;
		if (state[i][j1]) n++;
		if (state[i][j2]) n++;
		if (state[i2][j1]) n++;
		if (state[i2][j]) n++;
		if (state[i2][j2]) n++;

		return n;
	}
}
