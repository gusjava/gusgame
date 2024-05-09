package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.Font;

import gus.game5.core.alter.AlterDilate;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.UtilMaze;
import gus.game5.core.util.maze.MazeSolver;
import gus.game5.core.util.maze.MazeSolverBFS;
import gus.game5.core.util.maze.MazeSolverDFS;

public class AnimMaze extends Game1 {
	
	public static final int NB = 51;
	public static final int CELL_SIZE = 10;
	public static final int BOARD_SIZE = CELL_SIZE * NB;

	public static void main(String[] args) {
		AnimMaze main = new AnimMaze();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Maze", 
			action("Restart (F1)", this::restart),
			action("Exit (F2)", this::exit),
			null,
			action("Solve with BFS (F3)", this::solveBFS),
			action("Solve with DFS (F4)", this::solveDFS)
		);
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Path searching inside Maze");
		s.setSize(BOARD_SIZE);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	
	private boolean[][] data;
	private int[] start;
	private int[] end;
	private MazeSolver solver;
	
	protected void initialize1() {
		data = UtilMaze.generate(NB);
		start = UtilMaze.pickRandom(data);
		end = UtilMaze.pickRandom(data);
		solver = null;
		
		addDraw(new GameDrawing());
	}

	protected void turn() {
		Keyboard k = keyboard();
		
		if(k.in().F1())		restart();
		if(k.in().F2())		exit();
		if(k.in().F3())		solveBFS();
		if(k.in().F4())		solveDFS();
		
		if(solver!=null && !solver.isComplete()) 
			solver.handleStep();
	}
	
	/*
	 * SOLVE
	 */
	
	private void solveBFS() {
		try {
			solver = new MazeSolverBFS(data, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void solveDFS() {
		try {
			solver = new MazeSolverDFS(data, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class GameDrawing extends Drawing1 {
		public GameDrawing() {
			addAlter(new AlterDilate(()->CELL_SIZE));
		}
		protected void draw() {
			if(solver!=null) drawSolving();
			drawMaze();
			drawStart();
			drawEnd();
		}
		
		private void drawSolving() {
			boolean[][] state = solver.getState();
			int[] current = solver.getCurrent();
			int[][] path = solver.getPath();
			
			for(int i=0;i<NB;i++)
			for(int j=0;j<NB;j++) {
				if(!state[i][j]) fillSquare(Color.GRAY, p(i, j), 1);
			}
			if(path!=null) {
				for(int i=0;i<path.length;i++) {
					fillSquare(Color.YELLOW, p(path[i][0], path[i][1]), 1);
				}
			}
			if(current!=null)
			fillSquare(Color.YELLOW, p(current[0], current[1]), 1);
		}
		
		private void drawMaze() {
			for(int i=0;i<NB;i++)
			for(int j=0;j<NB;j++) {
				if(!data[i][j]) fillSquare(Color.BLACK, p(i, j), 1);
			}
		}
		
		private void drawStart() {
			fillSquare(Color.GREEN, p(start[0], start[1]), 1);
		}
		
		private void drawEnd() {
			fillSquare(Color.RED, p(end[0], end[1]), 1);
		}
	}
}
