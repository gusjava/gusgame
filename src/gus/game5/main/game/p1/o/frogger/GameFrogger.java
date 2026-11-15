package gus.game5.main.game.p1.o.frogger;

import java.awt.Color;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class GameFrogger extends Game1 {

	public static final long SLEEP = 16;
	public static final double DT = SLEEP/1000.0;

	public static final int COLS = 13;
	public static final int ROWS = 13;
	public static final int CELL = 40;

	public static final int ROW_HOME = 0;
	public static final int RIVER_TOP = 1;
	public static final int RIVER_BOTTOM = 5;
	public static final int ROW_MEDIAN = 6;
	public static final int ROAD_TOP = 7;
	public static final int ROAD_BOTTOM = 11;
	public static final int ROW_START = 12;
	public static final int START_COL = 6;

	public static final int[] HOME_COLS = {0, 3, 6, 9, 12};

	public static final int BOARD_W = COLS*CELL;
	public static final int BOARD_H = ROWS*CELL;
	public static final int TOP = 46;
	public static final int BOTTOM = 64;

	public static final double TIME_LIMIT = 40;
	public static final int LIVES = 3;
	public static final double HIT_MARGIN = 0.18;

	public static final int DEATH_TICKS = 55;
	public static final int LEVEL_TICKS = 110;

	public static final int SCORE_ROW = 10;
	public static final int SCORE_HOME = 50;
	public static final int SCORE_TIME = 10;
	public static final int SCORE_LEVEL = 1000;

	public static final Color COLOR_WATER = new Color(22,42,118);
	public static final Color COLOR_WAVE = new Color(34,60,148);
	public static final Color COLOR_ROAD = new Color(32,32,38);
	public static final Color COLOR_MARK = new Color(96,96,108);
	public static final Color COLOR_SAFE = new Color(112,56,128);
	public static final Color COLOR_BANK = new Color(24,112,52);
	public static final Color COLOR_PAD = new Color(10,54,28);
	public static final Color COLOR_PAD_EDGE = new Color(52,168,86);
	public static final Color COLOR_BUSH = new Color(16,74,34);
	public static final Color COLOR_FROG = new Color(104,214,74);
	public static final Color COLOR_HOMED = new Color(70,170,60);
	public static final Color COLOR_TEXT = new Color(238,238,246);
	public static final Color COLOR_LABEL = new Color(150,152,178);
	public static final Color COLOR_DEAD = new Color(226,72,60);
	public static final Color COLOR_VEIL = new Color(0,0,0,170);

	private List<Lane> lanes;
	private Frog frog;
	private Homes homes;

	private int score;
	private int level;
	private int lives;
	private int furthest;
	private double timeLeft;

	private int deathTick;
	private int levelTick;
	private String deathReason;

	private boolean paused;
	private boolean over;


	public static void main(String[] args) {
		GameFrogger main = new GameFrogger();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Frogger");
		s.setWidth(BOARD_W);
		s.setHeight(TOP + BOARD_H + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(new Color(8,10,18));
	}

	protected void initialize1() {
		homes = new Homes(HOME_COLS);
		frog = new Frog(START_COL, ROW_START);
		score = 0;
		lives = LIVES;
		paused = false;
		over = false;
		startLevel(1);
		addDraw(new GameDrawing());
	}

	private void startLevel(int value) {
		level = value;
		lanes = Level.build(level);
		homes.clear();
		levelTick = 0;
		respawn();
	}

	private void respawn() {
		frog.place(START_COL, ROW_START);
		furthest = ROW_START;
		timeLeft = TIME_LIMIT;
		deathTick = 0;
		deathReason = null;
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(handleRestartExit()) return;
		if(!over && k.in().p()) paused = !paused;
		if(paused || over) return;

		for(Lane lane : lanes) lane.update(DT);
		frog.animate();

		if(levelTick>0) {
			levelTick--;
			if(levelTick==0) startLevel(level+1);
			return;
		}
		if(deathTick>0) {
			deathTick--;
			if(deathTick==0) {
				if(lives<=0) over = true;
				else respawn();
			}
			return;
		}

		control(k);
		if(deathTick>0 || levelTick>0) return;

		ride();
		if(deathTick>0) return;

		timeLeft -= DT;
		if(timeLeft<=0) die("TEMPS ECOULE");
	}

	private void control(Keyboard k) {
		if(k.in().left()) frog.moveSide(-1, COLS);
		else if(k.in().right()) frog.moveSide(1, COLS);
		else if(k.in().up()) hopUp();
		else if(k.in().down()) frog.moveVert(1, ROWS);
	}

	private void hopUp() {
		if(frog.getRow()-1==ROW_HOME) {
			enterHome();
			return;
		}
		if(!frog.moveVert(-1, ROWS)) return;
		if(frog.getRow()<furthest) {
			furthest = frog.getRow();
			score += SCORE_ROW;
		}
	}

	private void enterHome() {
		frog.moveVert(-1, ROWS);
		int slot = homes.slotAt(frog.getX());
		if(slot<0) {
			die("ECRASEE DANS LA BERGE");
			return;
		}
		if(homes.isFilled(slot)) {
			die("NENUPHAR DEJA OCCUPE");
			return;
		}
		homes.fill(slot);
		score += SCORE_HOME + (int)timeLeft*SCORE_TIME;
		if(homes.isComplete()) {
			score += SCORE_LEVEL;
			levelTick = LEVEL_TICKS;
		}
		respawn();
	}

	private void ride() {
		int row = frog.getRow();
		if(row>=RIVER_TOP && row<=RIVER_BOTTOM) {
			Lane lane = laneAt(row);
			if(lane==null) return;
			if(lane.carrierAt(frog.getCenter())<0) {
				die("NOYADE");
				return;
			}
			frog.drift(lane.getSpeed()*DT);
			if(frog.isOffBoard(COLS)) die("EMPORTEE PAR LE COURANT");
			return;
		}
		if(row>=ROAD_TOP && row<=ROAD_BOTTOM) {
			Lane lane = laneAt(row);
			if(lane==null) return;
			if(lane.overlaps(frog.getX()+HIT_MARGIN, frog.getX()+1-HIT_MARGIN)) die("ECRASEE");
		}
	}

	private Lane laneAt(int row) {
		for(Lane lane : lanes)
			if(lane.getRow()==row) return lane;
		return null;
	}

	private void die(String reason) {
		deathReason = reason;
		deathTick = DEATH_TICKS;
		lives--;
	}


	/*
	 * DRAWING
	 */

	public class GameDrawing extends Drawing1 {

		protected void draw() {
			drawBoard();
			drawLanes();
			drawPads();
			if(deathTick>0) drawSplat();
			else if(levelTick==0) drawFrogAt(px(frog.drawX()+0.5), py(frog.drawY()+0.5)-frog.lift()*CELL,
					CELL*(1+frog.lift()*0.6), COLOR_FROG);
			drawHeader();
			drawFooter();
			drawMessage();
		}

		private double px(double col) {
			return col*CELL;
		}

		private double py(double row) {
			return TOP + row*CELL;
		}

		private void drawBoard() {
			fillRect(COLOR_BANK, p(0, py(ROW_HOME)), BOARD_W, CELL);
			fillRect(COLOR_WATER, p(0, py(RIVER_TOP)), BOARD_W, CELL*(RIVER_BOTTOM-RIVER_TOP+1));
			for(int row=RIVER_TOP;row<=RIVER_BOTTOM;row++)
				drawLine(COLOR_WAVE, p(0, py(row)), p(BOARD_W, py(row)));
			fillRect(COLOR_SAFE, p(0, py(ROW_MEDIAN)), BOARD_W, CELL);
			fillRect(COLOR_ROAD, p(0, py(ROAD_TOP)), BOARD_W, CELL*(ROAD_BOTTOM-ROAD_TOP+1));
			for(int row=ROAD_TOP+1;row<=ROAD_BOTTOM;row++)
				for(int x=0;x<BOARD_W;x+=28)
					fillRect(COLOR_MARK, p(x, py(row)-1), 16, 2);
			fillRect(COLOR_SAFE, p(0, py(ROW_START)), BOARD_W, CELL);
		}

		private void drawPads() {
			for(int col=0;col<COLS;col++) {
				if(homes.slotAt(col)>=0) continue;
				fillRect(COLOR_BUSH, p(px(col)+2, py(ROW_HOME)+2), CELL-4, CELL-4);
				fillRoundC(COLOR_BANK.brighter(), p(px(col)+CELL/2.0, py(ROW_HOME)+CELL/2.0), CELL*0.18);
			}
			for(int i=0;i<homes.size();i++) {
				double x = px(homes.column(i));
				fillRect(COLOR_PAD, p(x+3, py(ROW_HOME)+3), CELL-6, CELL-6);
				drawRect(COLOR_PAD_EDGE, p(x+3, py(ROW_HOME)+3), CELL-6, CELL-6);
				if(homes.isFilled(i))
					drawFrogAt(x+CELL/2.0, py(ROW_HOME)+CELL/2.0, CELL*0.84, COLOR_HOMED);
			}
		}

		private void drawLanes() {
			for(Lane lane : lanes) {
				for(int i=0;i<lane.getCount();i++) {
					double x = px(lane.position(i));
					double y = py(lane.getRow());
					double w = lane.getLength()*CELL;
					if(x>BOARD_W || x+w<0) continue;
					switch(lane.getType()) {
						case LOG: drawLog(x, y, w, lane.getColor()); break;
						case TURTLE: drawTurtles(lane, i, x, y); break;
						case TRUCK: drawTruck(x, y, w, lane); break;
						default: drawCar(x, y, w, lane); break;
					}
				}
			}
		}

		private void drawLog(double x, double y, double w, Color color) {
			fillRect(color.darker(), p(x, y+5), w, CELL-10);
			fillRect(color, p(x, y+8), w, CELL-19);
			for(double g=x+10;g<x+w-6;g+=22)
				fillRect(color.darker(), p(g, y+13), 12, 2);
			fillRound(color.brighter(), p(x+3, y+CELL/2.0-6), 6);
			fillRound(color.brighter(), p(x+w-15, y+CELL/2.0-6), 6);
		}

		private void drawTurtles(Lane lane, int index, double x, double y) {
			boolean sunk = lane.isSunk(index);
			boolean dim = lane.isBlinking(index) && ((int)(lane.divePhase(index)*60))%2==0;
			int n = (int)lane.getLength();
			for(int t=0;t<n;t++) {
				double cx = x + t*CELL + CELL/2.0;
				double cy = y + CELL/2.0;
				if(sunk) {
					drawRoundC(COLOR_WAVE, p(cx, cy), CELL*0.26);
					drawRoundC(COLOR_WAVE, p(cx, cy), CELL*0.14);
					continue;
				}
				Color shell = dim ? lane.getColor().darker().darker() : lane.getColor();
				double dir = lane.getSpeed()>0 ? 1 : -1;
				fillRoundC(shell.darker(), p(cx+dir*CELL*0.33, cy), CELL*0.11);
				fillRoundC(shell.darker(), p(cx-dir*CELL*0.22, cy-CELL*0.28), CELL*0.09);
				fillRoundC(shell.darker(), p(cx-dir*CELL*0.22, cy+CELL*0.28), CELL*0.09);
				fillRoundC(shell, p(cx, cy), CELL*0.36);
				fillRoundC(shell.darker(), p(cx, cy), CELL*0.22);
				fillRoundC(shell.brighter(), p(cx, cy), CELL*0.09);
			}
		}

		private void drawCar(double x, double y, double w, Lane lane) {
			boolean toRight = lane.getSpeed()>0;
			Color color = lane.getColor();
			fillRect(Color.BLACK, p(x+4, y+6), w-8, CELL-12);
			fillRect(color, p(x+3, y+8), w-6, CELL-16);
			fillRect(color.darker(), p(x+3, y+CELL-12), w-6, 4);
			double gx = toRight ? x+w-14 : x+6;
			fillRect(new Color(40,48,70), p(gx, y+11), 8, CELL-22);
			double lx = toRight ? x+w-5 : x+2;
			fillRect(new Color(250,240,170), p(lx, y+12), 3, 6);
			fillRect(new Color(250,240,170), p(lx, y+CELL-18), 3, 6);
		}

		private void drawTruck(double x, double y, double w, Lane lane) {
			boolean toRight = lane.getSpeed()>0;
			Color color = lane.getColor();
			double cabW = CELL*0.6;
			double cabX = toRight ? x+w-cabW : x;
			double boxX = toRight ? x : x+cabW;
			fillRect(Color.BLACK, p(x+2, y+5), w-4, CELL-10);
			fillRect(color, p(boxX+2, y+7), w-cabW-4, CELL-14);
			fillRect(color.darker(), p(boxX+6, y+7), 2, CELL-14);
			fillRect(new Color(70,76,96), p(cabX+1, y+7), cabW-2, CELL-14);
			double gx = toRight ? cabX+cabW-9 : cabX+3;
			fillRect(new Color(150,200,230), p(gx, y+11), 6, CELL-22);
		}

		private void drawFrogAt(double cx, double cy, double s, Color body) {
			fillRoundC(body.darker(), p(cx-s*0.30, cy+s*0.22), s*0.14);
			fillRoundC(body.darker(), p(cx+s*0.30, cy+s*0.22), s*0.14);
			fillRoundC(body.darker(), p(cx-s*0.32, cy-s*0.14), s*0.12);
			fillRoundC(body.darker(), p(cx+s*0.32, cy-s*0.14), s*0.12);
			fillRoundC(body, p(cx, cy+s*0.04), s*0.30);
			fillRoundC(body.brighter(), p(cx, cy-s*0.20), s*0.17);
			fillRoundC(Color.WHITE, p(cx-s*0.12, cy-s*0.28), s*0.075);
			fillRoundC(Color.WHITE, p(cx+s*0.12, cy-s*0.28), s*0.075);
			fillRoundC(Color.BLACK, p(cx-s*0.12, cy-s*0.28), s*0.035);
			fillRoundC(Color.BLACK, p(cx+s*0.12, cy-s*0.28), s*0.035);
		}

		private void drawSplat() {
			double cx = px(frog.getX()+0.5);
			double cy = py(frog.getRow()+0.5);
			fillRoundC(COLOR_DEAD, p(cx, cy), CELL*0.22);
			for(int i=0;i<8;i++) {
				double a = Math.PI*2*i/8;
				drawLine(COLOR_DEAD, p(cx, cy), p(cx+Math.cos(a)*CELL*0.42, cy+Math.sin(a)*CELL*0.42));
			}
			drawStringC(COLOR_DEAD, fontBold(13), p(BOARD_W/2.0, py(ROW_MEDIAN)+26), deathReason);
		}

		private void drawHeader() {
			drawString(COLOR_LABEL, fontPlain(11), p(8, 16), "SCORE");
			drawString(COLOR_TEXT, fontBold(20), p(8, 38), ""+score);
			drawStringC(COLOR_LABEL, fontPlain(11), p(BOARD_W/2.0, 16), "NIVEAU");
			drawStringC(COLOR_TEXT, fontBold(20), p(BOARD_W/2.0, 38), ""+level);
			drawStringC(COLOR_LABEL, fontPlain(11), p(BOARD_W-48, 16), "NENUPHARS");
			drawStringC(COLOR_TEXT, fontBold(20), p(BOARD_W-48, 38), homes.countFilled()+"/"+homes.size());
		}

		private void drawFooter() {
			double y = TOP + BOARD_H;
			for(int i=0;i<lives;i++)
				drawFrogAt(16+i*24, y+20, 22, COLOR_FROG);
			double barX = 150;
			double barW = BOARD_W - barX - 10;
			double part = Math.max(0, Math.min(1, timeLeft/TIME_LIMIT));
			fillRect(new Color(50,50,62), p(barX, y+13), barW, 14);
			fillRect(part>0.3 ? new Color(90,210,120) : COLOR_DEAD, p(barX+1, y+14), (barW-2)*part, 12);
			drawRect(COLOR_LABEL, p(barX, y+13), barW, 14);
			drawString(COLOR_LABEL, fontPlain(10), p(barX, y+10), "TEMPS");
			drawStringC(COLOR_LABEL, fontPlain(11), p(BOARD_W/2.0, y+48),
					"FLECHES bondir   P pause   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(!over && !paused && levelTick==0) return;
			fillRect(COLOR_VEIL, p(0, TOP), BOARD_W, BOARD_H);
			double cy = TOP + BOARD_H/2.0;
			if(paused) {
				drawStringC(COLOR_TEXT, fontBold(30), p(BOARD_W/2.0, cy), "PAUSE");
				return;
			}
			if(over) {
				drawStringC(COLOR_DEAD, fontBold(30), p(BOARD_W/2.0, cy-14), "GAME OVER");
				drawStringC(COLOR_TEXT, fontPlain(15), p(BOARD_W/2.0, cy+22), "F1 pour rejouer");
				return;
			}
			drawStringC(COLOR_TEXT, fontBold(26), p(BOARD_W/2.0, cy-14), "NIVEAU "+level+" TERMINE");
			drawStringC(COLOR_TEXT, fontPlain(15), p(BOARD_W/2.0, cy+22), "La riviere accelere");
		}
	}
}
