package gus.game5.main.game.p1.c.pacman;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.main.game.p1.c.pacman.Ghost.Kind;

public class GamePacman extends Game1 {

	public static final long SLEEP = 12;
	public static final int TPS = 80;

	public static final int GAP = 10;
	public static final int TOP = 44;
	public static final int BOTTOM = 36;

	public static final int SPEED_PACMAN = 2;
	public static final int SPEED_GHOST = 2;
	public static final int SPEED_FRIGHT = 1;
	public static final int SPEED_EYES = 4;
	public static final int GHOST_SLOW_RATE = 8;

	public static final int SCORE_PELLET = 10;
	public static final int SCORE_POWER = 50;
	public static final int SCORE_GHOST = 200;
	public static final int BONUS_LIFE = 10000;
	public static final int LIVES = 3;

	public static final int[] MODES = {7,20,7,20,5,20,5,-1};
	public static final int FRIGHT_MAX = 7;
	public static final int FRIGHT_MIN = 2;
	public static final int FRIGHT_WARNING = 2;
	
	public static final int[] FRUIT_TRIGGERS = {70, 170};
	public static final int FRUIT_SHOW = 9;
	public static final int FRUIT_SCORE_SHOW = 2;
	public static final int FRUIT_HISTORY = 7;

	public static final Color COLOR_WALL = new Color(20,20,130);
	public static final Color COLOR_WALL_EDGE = new Color(70,70,240);
	public static final Color COLOR_DOOR = new Color(255,184,255);
	public static final Color COLOR_PELLET = new Color(255,206,170);
	public static final Color COLOR_PACMAN = new Color(255,230,0);
	public static final Color COLOR_FRIGHT = new Color(33,33,222);
	public static final Color COLOR_FRIGHT_FACE = new Color(255,184,174);
	public static final Color COLOR_EYE = new Color(33,33,222);
	public static final Color COLOR_TEXT = Color.WHITE;
	public static final Color COLOR_FRUIT_SCORE = new Color(255,184,255);
	public static final Color COLOR_STEM = new Color(0,190,0);
	public static final Color COLOR_CHERRY = new Color(222,0,0);
	public static final Color COLOR_APPLE = new Color(210,0,40);
	public static final Color COLOR_ORANGE = new Color(255,160,0);
	public static final Color COLOR_MELON = new Color(120,230,80);
	public static final Color COLOR_MELON_LINE = new Color(30,140,40);
	public static final Color COLOR_GALBOSS = new Color(60,200,255);
	public static final Color COLOR_BELL = new Color(255,220,60);
	public static final Color COLOR_KEY = new Color(190,220,255);

	public enum State {READY, PLAYING, DYING, CLEARED, OVER}


	private Maze maze;
	private Pacman pacman;
	private Ghost[] ghosts;

	private State state;
	private int tick;
	private int stateTick;
	private int modeIndex;
	private int modeTick;
	private int frightTick;
	private int ghostChain;
	private int fruitTick;
	private int fruitsShown;
	private int fruitScoreTick;
	private int fruitScore;

	private int score;
	private int lives;
	private int level;
	private int nextBonus;
	private boolean paused;


	public static void main(String[] args) {
		GamePacman main = new GamePacman();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Pacman");
		s.setWidth(Maze.COLS*Maze.CELL + GAP*2);
		s.setHeight(Maze.ROWS*Maze.CELL + TOP + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(Color.BLACK);
	}

	protected void initialize1() {
		maze = new Maze();
		pacman = new Pacman(maze);
		ghosts = new Ghost[] {
			new Ghost(maze, Kind.BLINKY, Maze.HOME_COL),
			new Ghost(maze, Kind.PINKY, Maze.HOME_COL),
			new Ghost(maze, Kind.INKY, Maze.HOME_COL-2),
			new Ghost(maze, Kind.CLYDE, Maze.HOME_COL+2)};

		score = 0;
		lives = LIVES;
		level = 1;
		nextBonus = BONUS_LIFE;
		tick = 0;
		paused = false;

		startLevel();
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
		if(k.in().space()) paused = !paused;
		if(paused) return;

		tick++;
		stateTick++;

		switch(state) {
			case READY: if(stateTick>TPS*2) setState(State.PLAYING); break;
			case PLAYING: playing(); break;
			case DYING: if(stateTick>TPS*2) afterDeath(); break;
			case CLEARED: if(stateTick>TPS*2) nextLevel(); break;
			default: break;
		}
	}

	private void playing() {
		readDirection();
		pacman.update(SPEED_PACMAN);
		eatPellet();

		if(maze.getPelletsLeft()==0) {
			setState(State.CLEARED);
			return;
		}

		updateFruit();
		updateMode();
		updateGhosts();
		checkContacts();
	}

	private void readDirection() {
		Keyboard k = keyboard();
		if(k.up()) pacman.setWanted(Dir.NORTH);
		else if(k.down()) pacman.setWanted(Dir.SOUTH);
		else if(k.left()) pacman.setWanted(Dir.WEST);
		else if(k.right()) pacman.setWanted(Dir.EAST);
	}

	private void eatPellet() {
		if(!pacman.isCentered()) return;
		char c = maze.eat(pacman.getCol(), pacman.getRow());
		if(c==Maze.PELLET) addScore(SCORE_PELLET);
		else if(c==Maze.POWER) {
			addScore(SCORE_POWER);
			startFright();
		}
	}

	private void updateFruit() {
		if(fruitScoreTick>0) fruitScoreTick--;
		if(fruitTick>0) {
			fruitTick--;
			if(fruitTick>0 && onFruit()) eatFruit();
			return;
		}
		if(fruitsShown>=FRUIT_TRIGGERS.length) return;
		if(maze.getPelletsEaten()<FRUIT_TRIGGERS[fruitsShown]) return;
		fruitsShown++;
		fruitTick = FRUIT_SHOW*TPS;
	}
	
	private boolean onFruit() {
		return pacman.getCol()==Maze.PACMAN_COL && pacman.getRow()==Maze.PACMAN_ROW;
	}
	
	private void eatFruit() {
		fruitScore = Fruit.forLevel(level).getScore();
		addScore(fruitScore);
		fruitTick = 0;
		fruitScoreTick = FRUIT_SCORE_SHOW*TPS;
	}
	
	private void startFright() {
		frightTick = Math.max(FRIGHT_MIN, FRIGHT_MAX-level)*TPS;
		ghostChain = 0;
		for(Ghost g : ghosts) g.frighten();
	}

	private void updateMode() {
		if(frightTick>0) {
			frightTick--;
			if(frightTick==0) for(Ghost g : ghosts) g.calm();
			return;
		}
		if(MODES[modeIndex]<0) return;

		modeTick++;
		if(modeTick<MODES[modeIndex]*TPS) return;

		modeTick = 0;
		modeIndex++;
		for(Ghost g : ghosts) g.turnBack();
	}

	private boolean isScatter() {
		return MODES[modeIndex]>=0 && modeIndex%2==0;
	}

	private void updateGhosts() {
		boolean scatter = isScatter();
		for(Ghost g : ghosts) {
			if(g.isEyes()) g.update(SPEED_EYES, scatter, pacman, ghosts[0]);
			else if(g.isFrightened()) g.update(SPEED_FRIGHT, scatter, pacman, ghosts[0]);
			else if(tick%GHOST_SLOW_RATE!=0) g.update(SPEED_GHOST, scatter, pacman, ghosts[0]);
		}
	}

	private void checkContacts() {
		for(Ghost g : ghosts) {
			if(!g.isCatchable() || !touching(g)) continue;
			if(g.isFrightened()) {
				ghostChain++;
				addScore(SCORE_GHOST*(1<<(ghostChain-1)));
				g.eaten();
			}
			else {
				setState(State.DYING);
				return;
			}
		}
	}

	private boolean touching(Ghost g) {
		return Math.abs(g.getX()-pacman.getX())<Maze.CELL*0.6
			&& Math.abs(g.getY()-pacman.getY())<Maze.CELL*0.6;
	}

	private void addScore(int points) {
		score += points;
		if(score<nextBonus) return;
		lives++;
		nextBonus += BONUS_LIFE;
	}


	/*
	 * STATES
	 */

	private void setState(State state) {
		this.state = state;
		this.stateTick = 0;
	}

	private void startLevel() {
		maze.reset();
		resetActors();
		fruitsShown = 0;
		setState(State.READY);
	}

	private void resetActors() {
		pacman.place(Maze.PACMAN_COL, Maze.PACMAN_ROW, Dir.WEST);
		pacman.setWanted(null);
		ghosts[0].reset(true, 0);
		ghosts[1].reset(false, TPS*2);
		ghosts[2].reset(false, TPS*5);
		ghosts[3].reset(false, TPS*8);
		modeIndex = 0;
		modeTick = 0;
		frightTick = 0;
		ghostChain = 0;
		fruitTick = 0;
		fruitScoreTick = 0;
	}

	private void afterDeath() {
		lives--;
		if(lives<=0) {
			setState(State.OVER);
			return;
		}
		resetActors();
		setState(State.READY);
	}

	private void nextLevel() {
		level++;
		startLevel();
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
			drawMaze();
			drawItems();
			drawFruit();
			if(state!=State.OVER) drawPacman();
			if(state!=State.DYING && state!=State.OVER) drawGhosts();
			drawHud();
			drawMessage();
		}

		private void drawMaze() {
			for(int row=0;row<Maze.ROWS;row++)
				for(int col=0;col<Maze.COLS;col++) {
					int x = col*Maze.CELL;
					int y = row*Maze.CELL;
					if(maze.isWall(col, row)) drawWall(col, row, x, y);
					else if(maze.isDoor(col, row))
						fillRect(COLOR_DOOR, p(x, y+Maze.CELL*0.4), Maze.CELL, Maze.CELL*0.2);
				}
		}

		private void drawWall(int col, int row, int x, int y) {
			fillRect(COLOR_WALL, p(x, y), Maze.CELL, Maze.CELL);
			if(!wallAt(col, row-1)) drawLine(COLOR_WALL_EDGE, p(x, y), p(x+Maze.CELL, y));
			if(!wallAt(col, row+1)) drawLine(COLOR_WALL_EDGE, p(x, y+Maze.CELL), p(x+Maze.CELL, y+Maze.CELL));
			if(!wallAt(col-1, row)) drawLine(COLOR_WALL_EDGE, p(x, y), p(x, y+Maze.CELL));
			if(!wallAt(col+1, row)) drawLine(COLOR_WALL_EDGE, p(x+Maze.CELL, y), p(x+Maze.CELL, y+Maze.CELL));
		}
		
		private boolean wallAt(int col, int row) {
			if(row<0 || row>=Maze.ROWS) return false;
			return maze.isWall(col, row);
		}

		private void drawItems() {
			boolean blink = (tick/14)%2==0;
			for(int row=0;row<Maze.ROWS;row++)
				for(int col=0;col<Maze.COLS;col++) {
					char c = maze.at(col, row);
					Point0 pc = p(Maze.centerX(col), Maze.centerY(row));
					if(c==Maze.PELLET) fillSquareC(COLOR_PELLET, pc, Maze.CELL*0.2);
					else if(c==Maze.POWER && blink) fillRoundC(COLOR_PELLET, pc, Maze.CELL*0.35);
				}
		}

		private void drawPacman() {
			Point0 pc = p(pacman.getX(), pacman.getY());
			double radius = Maze.CELL*0.45;
			double open = mouthOpening();
			fillArcC(COLOR_PACMAN, pc, radius,
					Angle.angleDeg(baseAngle()+open), Angle.angleDeg(360-2*open));
		}

		private double mouthOpening() {
			if(state==State.DYING) return 10 + 170*Math.min(1, stateTick/(TPS*1.6));
			if(state==State.READY) return 35;
			return 4 + 36*Math.abs(Math.sin(tick*0.17));
		}

		private double baseAngle() {
			switch(pacman.getDir()) {
				case EAST: return 0;
				case SOUTH: return 90;
				case WEST: return 180;
				default: return 270;
			}
		}

		private void drawGhosts() {
			for(Ghost g : ghosts) drawGhost(g);
		}

		private void drawGhost(Ghost g) {
			double radius = Maze.CELL*0.45;
			int x = g.getX();
			int y = g.getY();
			if(!g.isEyes()) {
				Color body = ghostColor(g);
				fillArcC(body, p(x, y), radius, Angle.angleDeg(180), Angle.angleDeg(180));
				fillRect(body, p(x-radius, y), radius*2, radius*0.7);
				drawSkirt(body, x, y, radius);
			}
			if(g.isFrightened()) drawScaredFace(g, x, y, radius);
			else drawEyes(g, x, y, radius);
		}

		private Color ghostColor(Ghost g) {
			if(!g.isFrightened()) return g.getColor();
			if(frightTick<FRIGHT_WARNING*TPS && (tick/10)%2==0) return Color.WHITE;
			return COLOR_FRIGHT;
		}

		private void drawSkirt(Color body, int x, int y, double radius) {
			double top = y + radius*0.6;
			double bot = y + radius;
			double mid = bot - radius*0.4;
			double w = radius*2/3;
			fillPoly(body,
				p(x-radius, top),
				p(x-radius, bot),
				p(x-radius+w*0.5, mid),
				p(x-radius+w, bot),
				p(x-radius+w*1.5, mid),
				p(x-radius+w*2, bot),
				p(x-radius+w*2.5, mid),
				p(x+radius, bot),
				p(x+radius, top));
		}

		private void drawEyes(Ghost g, int x, int y, double radius) {
			double gap = radius*0.4;
			double top = y - radius*0.15;
			double px = g.getDir().getDx()*radius*0.14;
			double py = g.getDir().getDy()*radius*0.18;
			fillOvalC(Color.WHITE, p(x-gap, top), radius*0.5, radius*0.62);
			fillOvalC(Color.WHITE, p(x+gap, top), radius*0.5, radius*0.62);
			fillRoundC(COLOR_EYE, p(x-gap+px, top+py), radius*0.16);
			fillRoundC(COLOR_EYE, p(x+gap+px, top+py), radius*0.16);
		}

		private void drawScaredFace(Ghost g, int x, int y, double radius) {
			Color face = ghostColor(g)==Color.WHITE ? Color.RED : COLOR_FRIGHT_FACE;
			fillRoundC(face, p(x-radius*0.35, y-radius*0.2), radius*0.13);
			fillRoundC(face, p(x+radius*0.35, y-radius*0.2), radius*0.13);
			double step = radius*0.35;
			double base = y + radius*0.35;
			for(int i=0;i<4;i++)
				drawLine(face,
					p(x-radius*0.7+step*i, i%2==0 ? base : base-radius*0.22),
					p(x-radius*0.7+step*(i+1), i%2==0 ? base-radius*0.22 : base));
		}

		private void drawHud() {
			drawString(COLOR_TEXT, fontBold(16), p(2, -TOP+26), "SCORE  "+score);
			drawString(COLOR_TEXT, fontBold(16), p(Maze.COLS*Maze.CELL-110, -TOP+26), "LEVEL  "+level);
			
			double y = Maze.ROWS*Maze.CELL + BOTTOM*0.45;
			for(int i=0;i<lives-1;i++)
				fillArcC(COLOR_PACMAN, p(14+i*26, y), Maze.CELL*0.42, 
						Angle.angleDeg(30), Angle.angleDeg(300));
			
			drawStringC(COLOR_TEXT, fontPlain(11), p(Maze.COLS*Maze.CELL/2, y+4), 
					"F1 rejouer   F2 quitter   ESPACE pause");
			
			drawFruitHistory(y);
		}
		
		private void drawFruitHistory(double y) {
			int count = Math.min(level, FRUIT_HISTORY);
			for(int i=0;i<count;i++)
				drawFruitShape(Fruit.forLevel(level-i), 
						Maze.COLS*Maze.CELL-14-i*24, y, Maze.CELL);
		}
		
		private void drawFruit() {
			double x = Maze.centerX(Maze.PACMAN_COL);
			double y = Maze.centerY(Maze.PACMAN_ROW);
			if(fruitScoreTick>0) {
				drawStringC(COLOR_FRUIT_SCORE, fontBold(15), p(x, y+5), ""+fruitScore);
				return;
			}
			if(fruitTick>0) drawFruitShape(Fruit.forLevel(level), x, y, Maze.CELL*1.1);
		}
		
		private void drawFruitShape(Fruit fruit, double x, double y, double size) {
			double r = size*0.34;
			switch(fruit) {
				case CHERRY: drawCherry(x, y, r); break;
				case STRAWBERRY: drawStrawberry(x, y, r); break;
				case ORANGE: drawRoundFruit(COLOR_ORANGE, x, y, r); break;
				case APPLE: drawRoundFruit(COLOR_APPLE, x, y, r); break;
				case MELON: drawMelon(x, y, r); break;
				case GALBOSS: drawGalboss(x, y, r); break;
				case BELL: drawBell(x, y, r); break;
				default: drawKey(x, y, r); break;
			}
		}
		
		private void drawCherry(double x, double y, double r) {
			drawThickLine(COLOR_STEM, p(x+r*0.3, y-r), p(x-r*0.7, y-r*0.1), 2);
			drawThickLine(COLOR_STEM, p(x+r*0.3, y-r), p(x+r*0.75, y-r*0.1), 2);
			fillRoundC(COLOR_CHERRY, p(x-r*0.68, y+r*0.42), r*0.52);
			fillRoundC(COLOR_CHERRY, p(x+r*0.72, y+r*0.42), r*0.52);
		}
		
		private void drawStrawberry(double x, double y, double r) {
			fillPoly(COLOR_CHERRY, p(x-r*0.85, y-r*0.2), p(x+r*0.85, y-r*0.2), p(x, y+r*1.1));
			fillRoundC(COLOR_CHERRY, p(x, y-r*0.2), r*0.85);
			fillRect(COLOR_STEM, p(x-r*0.9, y-r*1.05), r*1.8, r*0.4);
			drawThickLine(COLOR_STEM, p(x, y-r*1.05), p(x, y-r*1.4), 2);
		}
		
		private void drawRoundFruit(Color color, double x, double y, double r) {
			fillRoundC(color, p(x, y+r*0.2), r*0.88);
			drawThickLine(COLOR_STEM, p(x, y-r*0.6), p(x, y-r*1.2), 2);
			fillPoly(COLOR_STEM, p(x, y-r*0.75), p(x+r*1.05, y-r*1.0), p(x+r*0.25, y-r*1.3));
		}
		
		private void drawMelon(double x, double y, double r) {
			fillRoundC(COLOR_MELON, p(x, y+r*0.15), r*0.92);
			drawThickLine(COLOR_STEM, p(x, y-r*0.7), p(x, y-r*1.25), 2);
			for(int i=-1;i<=1;i++)
				drawLine(COLOR_MELON_LINE, p(x+i*r*0.42, y-r*0.5), p(x+i*r*0.42, y+r*0.95));
		}
		
		private void drawGalboss(double x, double y, double r) {
			fillPoly(COLOR_GALBOSS, p(x, y-r*1.1), p(x+r, y+r*0.75), p(x, y+r*0.15), p(x-r, y+r*0.75));
			fillRoundC(COLOR_PELLET, p(x, y-r*0.15), r*0.28);
		}
		
		private void drawBell(double x, double y, double r) {
			fillArcC(COLOR_BELL, p(x, y+r*0.45), r*0.95, Angle.angleDeg(180), Angle.angleDeg(180));
			fillRect(COLOR_BELL, p(x-r*0.95, y+r*0.45), r*1.9, r*0.28);
			fillRoundC(COLOR_BELL, p(x, y+r*0.85), r*0.2);
		}
		
		private void drawKey(double x, double y, double r) {
			fillRoundC(COLOR_KEY, p(x, y-r*0.6), r*0.42);
			fillRect(COLOR_KEY, p(x-r*0.13, y-r*0.45), r*0.26, r*1.5);
			fillRect(COLOR_KEY, p(x-r*0.13, y+r*0.45), r*0.75, r*0.22);
			fillRect(COLOR_KEY, p(x-r*0.13, y+r*0.9), r*0.55, r*0.22);
		}
		
		private void drawMessage() {
			Point0 pc = p(Maze.COLS*Maze.CELL/2, Maze.centerY(Maze.PACMAN_ROW-3));
			if(paused) drawStringC(COLOR_TEXT, fontBold(24), pc, "PAUSE");
			else if(state==State.READY) drawStringC(COLOR_PACMAN, fontBold(24), pc, "READY!");
			else if(state==State.OVER) drawStringC(Color.RED, fontBold(26), pc, "GAME OVER");
			else if(state==State.CLEARED) drawStringC(COLOR_TEXT, fontBold(22), pc, "LEVEL "+level+" CLEAR");
		}
	}
}
