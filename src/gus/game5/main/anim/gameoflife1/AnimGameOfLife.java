package gus.game5.main.anim.gameoflife1;

import java.awt.Color;

import gus.game5.core.alter.AlterDilate;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class AnimGameOfLife extends Game1 {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final double RANDOM_RATE = 0.5;
	public static final char ALIVE = 'O';

	public static final Color COLOR_POINT = Color.BLUE;
	public static final Color COLOR_BACKGROUND = Color.BLACK;
	public static final Color COLOR_BORDER = Color.LIGHT_GRAY;
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_SELECTED = new Color(255,200,60);

	public static final Config[] CONFIGS = buildConfigs();

	private int selected = CONFIGS.length-1;
	private int running = CONFIGS.length-1;

	private int cell;
	private int gridX;
	private int gridY;
	private boolean[][] state;

	public static void main(String[] args) {
		AnimGameOfLife main = new AnimGameOfLife();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Game of Life");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(50);
		s.setBackground(COLOR_BORDER);
	}

	protected void initialize1() {
		running = selected;
		Config config = CONFIGS[running];
		cell = config.getCell();
		gridX = WIDTH/cell;
		gridY = HEIGHT/cell;
		state = config.isRandom() ? randomState() : patternState(config);
		addDraw(new GameDrawing());
		addDraw(new MenuDrawing());
	}

	protected void turn() {
		state = computeState();

		Keyboard k = keyboard();
		if(k.out().up()) selected = (selected+CONFIGS.length-1)%CONFIGS.length;
		else if(k.out().down()) selected = (selected+1)%CONFIGS.length;

		if(k.F1()) restart();
		if(k.F2()) exit();
	}

	public class GameDrawing extends Drawing1 {
		public GameDrawing() {
			setColor(COLOR_POINT);
			addAlter(new AlterDilate(()->cell));
		}
		protected void draw() {
			fillRect(COLOR_BACKGROUND, gridX, gridY);
			for(int i=0;i<gridX;i++)
			for(int j=0;j<gridY;j++)
			if(state[i][j]) fillSquare(p(i, j), 1);
		}
	}

	public class MenuDrawing extends Drawing1 {
		protected void draw() {
			drawString(COLOR_LABEL, fontPlain(13), p(12, 22),
					"EN COURS : "+CONFIGS[running].getName());
			drawString(COLOR_SELECTED, fontBold(13), p(12, 40),
					"CHOIX "+(selected+1)+"/"+CONFIGS.length+" : "+CONFIGS[selected].getName());
			drawString(COLOR_LABEL, fontPlain(11), p(12, HEIGHT-16),
					"HAUT/BAS choisir   F1 lancer la configuration choisie   F2 quitter");
		}
	}

	private boolean[][] randomState() {
		boolean[][] b = new boolean[gridX][gridY];
		for(int i=0;i<gridX;i++)
		for(int j=0;j<gridY;j++)
		b[i][j] = Math.random()<RANDOM_RATE;
		return b;
	}

	private boolean[][] patternState(Config config) {
		boolean[][] b = new boolean[gridX][gridY];
		String[] rows = config.getRows();
		int i0 = (gridX-config.getWidth())/2;
		int j0 = (gridY-rows.length)/2;
		for(int j=0;j<rows.length;j++) {
			String row = rows[j];
			for(int i=0;i<row.length();i++)
			if(row.charAt(i)==ALIVE) setAlive(b, i0+i, j0+j);
		}
		return b;
	}

	private void setAlive(boolean[][] b, int i, int j) {
		if(i<0 || i>=gridX || j<0 || j>=gridY) return;
		b[i][j] = true;
	}

	private boolean[][] computeState() {
		boolean[][] b1 = new boolean[gridX][gridY];
		for (int i = 0; i < gridX; i++)
		for (int j = 0; j < gridY; j++)
		b1[i][j] = findNextValue(i, j);
		return b1;
	}

	private boolean findNextValue(int i, int j) {
		int n = aliveNeighbours(i, j);
		if (state[i][j]) return n == 2 || n == 3;
		return n == 3;
	}

	private int aliveNeighbours(int i, int j) {
		int i1 = i > 0 ? i - 1 : gridX - 1;
		int i2 = i < gridX - 1 ? i + 1 : 0;
		int j1 = j > 0 ? j - 1 : gridY - 1;
		int j2 = j < gridY - 1 ? j + 1 : 0;

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

	private static Config[] buildConfigs() {
		return new Config[] {
			new Config("Block (nature morte)", 20,
					"OO",
					"OO"),
			new Config("Beehive (nature morte)", 20,
					".OO.",
					"O..O",
					".OO."),
			new Config("Loaf (nature morte)", 20,
					".OO.",
					"O..O",
					".O.O",
					"..O."),
			new Config("Boat (nature morte)", 20,
					"OO.",
					"O.O",
					".O."),
			new Config("Tub (nature morte)", 20,
					".O.",
					"O.O",
					".O."),
			new Config("Blinker (oscillateur)", 20,
					"OOO"),
			new Config("Toad (oscillateur)", 20,
					".OOO",
					"OOO."),
			new Config("Beacon (oscillateur)", 20,
					"OO..",
					"OO..",
					"..OO",
					"..OO"),
			new Config("Pulsar (oscillateur)", 10,
					"..OOO...OOO..",
					".............",
					"O....O.O....O",
					"O....O.O....O",
					"O....O.O....O",
					"..OOO...OOO..",
					".............",
					"..OOO...OOO..",
					"O....O.O....O",
					"O....O.O....O",
					"O....O.O....O",
					".............",
					"..OOO...OOO.."),
			new Config("Pentadecathlon (oscillateur)", 10,
					"..O....O..",
					"OO.OOOO.OO",
					"..O....O.."),
			new Config("Glider (vaisseau)", 8,
					".O.",
					"..O",
					"OOO"),
			new Config("LWSS (vaisseau leger)", 8,
					".O..O",
					"O....",
					"O...O",
					"OOOO."),
			new Config("MWSS (vaisseau moyen)", 8,
					"...O..",
					".O...O",
					"O.....",
					"O....O",
					"OOOOO."),
			new Config("HWSS (vaisseau lourd)", 8,
					"...OO..",
					".O....O",
					"O......",
					"O.....O",
					"OOOOOO."),
			new Config("Gosper Glider Gun (canon a planeurs)", 4,
					"........................O...........",
					"......................O.O...........",
					"............OO......OO............OO",
					"...........O...O....OO............OO",
					"OO........O.....O...OO..............",
					"OO........O...O.OO....O.O...........",
					"..........O.....O.......O...........",
					"...........O...O....................",
					"............OO......................"),
			new Config("R-pentomino (methuselah)", 4,
					".OO",
					"OO.",
					".O."),
			new Config("Diehard (methuselah)", 4,
					"......O.",
					"OO......",
					".O...OOO"),
			new Config("Acorn (methuselah)", 4,
					".O.....",
					"...O...",
					"OO..OOO"),
			new Config("Aleatoire", 2)
		};
	}

	public static class Config {

		private final String name;
		private final int cell;
		private final String[] rows;

		public Config(String name, int cell, String... rows) {
			this.name = name;
			this.cell = cell;
			this.rows = rows;
		}

		public String getName() {
			return name;
		}

		public int getCell() {
			return cell;
		}

		public String[] getRows() {
			return rows;
		}

		public boolean isRandom() {
			return rows.length==0;
		}

		public int getWidth() {
			int w = 0;
			for(String row : rows) w = Math.max(w, row.length());
			return w;
		}
	}
}
