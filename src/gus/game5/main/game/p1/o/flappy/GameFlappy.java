package gus.game5.main.game.p1.o.flappy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;

public class GameFlappy extends Game1 {

	public static final long SLEEP = 16;

	public static final int WIDTH = 420;
	public static final int HEIGHT = 620;
	public static final int INFO_H = 42;
	public static final int GROUND_H = 46;
	public static final int GRASS_H = 12;
	public static final int GROUND_Y = HEIGHT - INFO_H - GROUND_H;
	public static final double GROUND_TILE = 34;

	public static final double BIRD_X = 130;
	public static final double BIRD_RX = 12;
	public static final double BIRD_RY = 10;
	public static final double BIRD_R = 12;
	public static final double GRAVITY = 0.45;
	public static final double FLAP_SPEED = -8.2;
	public static final double MAX_FALL = 11;
	public static final double TILT_RATE = 7;
	public static final double TILT_UP = -28;
	public static final double TILT_DOWN = 75;
	public static final double HOVER_Y = GROUND_Y*0.45;
	public static final double HOVER_AMP = 7;
	public static final double HOVER_RATE = 0.09;

	public static final double SCROLL = 2.4;
	public static final double PIPE_W = 62;
	public static final double PIPE_LIP_H = 20;
	public static final double PIPE_GAP = 150;
	public static final double GAP_MARGIN = 60;
	public static final int PIPE_COUNT = 3;
	public static final double PIPE_SPACING = 200;
	public static final double PIPE_PERIOD = PIPE_COUNT*PIPE_SPACING;
	public static final double PIPE_START = WIDTH + 60;

	public static final int CLOUD_COUNT = 3;
	public static final double CLOUD_W = 96;
	public static final double CLOUD_SPACING = 190;
	public static final double CLOUD_PERIOD = CLOUD_COUNT*CLOUD_SPACING;
	public static final double CLOUD_SPEED = SCROLL*0.3;
	public static final double CLOUD_TOP = 50;
	public static final double CLOUD_BOTTOM = 220;
	public static final double CLOUD_START = 40;

	public static final Color COLOR_SKY = new Color(110,197,207);
	public static final Color COLOR_HAZE_1 = new Color(127,205,207);
	public static final Color COLOR_HAZE_2 = new Color(144,213,208);
	public static final Color COLOR_HAZE_3 = new Color(161,221,209);
	public static final Color COLOR_CLOUD = new Color(238,248,248);
	public static final Color COLOR_PIPE = new Color(94,176,62);
	public static final Color COLOR_PIPE_LIP = new Color(112,193,75);
	public static final Color COLOR_PIPE_DARK = new Color(57,124,40);
	public static final Color COLOR_PIPE_LIGHT = new Color(155,214,110);
	public static final Color COLOR_GRASS = new Color(118,189,72);
	public static final Color COLOR_GRASS_DARK = new Color(80,145,50);
	public static final Color COLOR_GROUND = new Color(222,198,130);
	public static final Color COLOR_GROUND_DARK = new Color(203,175,105);
	public static final Color COLOR_BIRD = new Color(249,207,67);
	public static final Color COLOR_BIRD_LIGHT = new Color(253,233,150);
	public static final Color COLOR_BIRD_DARK = new Color(222,154,36);
	public static final Color COLOR_BEAK = new Color(242,110,48);
	public static final Color COLOR_INFO = new Color(28,38,46);
	public static final Color COLOR_INFO_TEXT = new Color(168,184,196);
	public static final Color COLOR_TEXT = new Color(255,255,255);
	public static final Color COLOR_SHADOW = new Color(0,0,0,120);
	public static final Color COLOR_VEIL = new Color(0,0,0,140);


	private Bird bird;
	private List<Pipe> pipes;
	private List<Cloud> clouds;

	private double groundOffset;
	private int score;
	private int best;

	private boolean started;
	private boolean paused;
	private boolean over;


	public static void main(String[] args) {
		GameFlappy main = new GameFlappy();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Flappy Bird");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_SKY);
	}

	protected void initialize1() {
		bird = new Bird(HOVER_Y);

		pipes = new ArrayList<>();
		for(int i=0;i<PIPE_COUNT;i++)
			pipes.add(new Pipe(PIPE_START + i*PIPE_SPACING, Pipe.randomGapTop()));

		clouds = new ArrayList<>();
		for(int i=0;i<CLOUD_COUNT;i++)
			clouds.add(new Cloud(CLOUD_START + i*CLOUD_SPACING, Cloud.randomY()));

		groundOffset = 0;
		score = 0;
		started = false;
		paused = false;
		over = false;

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
		if(started && !over && k.out().p()) paused = !paused;
		if(paused || over) return;

		scrollDecor();

		boolean flap = k.in().space() || k.in().up() || mouse().button1().justPressed();
		if(!started) {
			bird.hover(getCount());
			if(flap) {
				started = true;
				bird.flap();
			}
			return;
		}

		if(flap) bird.flap();
		bird.fall();
		movePipes();
		checkCollisions();
	}

	private void scrollDecor() {
		groundOffset = (groundOffset + SCROLL)%GROUND_TILE;
		for(Cloud cloud : clouds) cloud.move(CLOUD_SPEED, CLOUD_PERIOD);
	}

	private void movePipes() {
		for(Pipe pipe : pipes) {
			pipe.move(SCROLL);
			if(pipe.checkPassed(bird.getLeft())) {
				score++;
				if(score>best) best = score;
			}
			if(pipe.isOut()) pipe.recycle(PIPE_PERIOD);
		}
	}

	private void checkCollisions() {
		if(bird.getBottom()>=GROUND_Y) {
			bird.restOnGround();
			die();
			return;
		}
		if(bird.getTop()<=0) bird.restOnCeiling();
		for(Pipe pipe : pipes) if(pipe.hits(bird)) {
			die();
			return;
		}
	}

	private void die() {
		over = true;
	}


	/*
	 * DRAWING
	 */

	public class GameDrawing extends Drawing1 {

		protected void draw() {
			drawSky();
			drawClouds();
			drawPipes();
			drawGround();
			drawBird();
			drawInfo();
			drawScore();
			drawMessage();
		}

		private void drawSky() {
			fillRect(COLOR_SKY, p(0, 0), WIDTH, GROUND_Y);
			fillRect(COLOR_HAZE_1, p(0, GROUND_Y-90), WIDTH, 30);
			fillRect(COLOR_HAZE_2, p(0, GROUND_Y-60), WIDTH, 30);
			fillRect(COLOR_HAZE_3, p(0, GROUND_Y-30), WIDTH, 30);
		}

		private void drawClouds() {
			for(Cloud cloud : clouds) {
				double x = cloud.getX();
				double y = cloud.getY();
				fillRect(COLOR_CLOUD, p(x+10, y), 68, 18);
				fillRoundC(COLOR_CLOUD, p(x+26, y), 26);
				fillRoundC(COLOR_CLOUD, p(x+56, y-8), 20);
				fillRoundC(COLOR_CLOUD, p(x+76, y+2), 16);
			}
		}

		private void drawPipes() {
			for(Pipe pipe : pipes) {
				double x = pipe.getX();
				drawPipeBody(x, 0, pipe.getGapTop()-PIPE_LIP_H);
				drawPipeBody(x, pipe.getGapBottom()+PIPE_LIP_H, GROUND_Y);
				drawPipeLip(x, pipe.getGapTop()-PIPE_LIP_H);
				drawPipeLip(x, pipe.getGapBottom());
			}
		}

		private void drawPipeBody(double x, double top, double bottom) {
			double h = bottom - top;
			if(h<=0) return;
			fillRect(COLOR_PIPE, p(x, top), PIPE_W, h);
			fillRect(COLOR_PIPE_LIGHT, p(x+8, top), 9, h);
			fillRect(COLOR_PIPE_DARK, p(x+PIPE_W-12, top), 12, h);
			drawRect(COLOR_PIPE_DARK, p(x, top), PIPE_W, h);
		}

		private void drawPipeLip(double x, double top) {
			fillRect(COLOR_PIPE_LIP, p(x, top), PIPE_W, PIPE_LIP_H);
			fillRect(COLOR_PIPE_LIGHT, p(x+8, top), 10, PIPE_LIP_H);
			fillRect(COLOR_PIPE_DARK, p(x+PIPE_W-13, top), 13, PIPE_LIP_H);
			fillRect(COLOR_PIPE_DARK, p(x, top), PIPE_W, 3);
			fillRect(COLOR_PIPE_DARK, p(x, top+PIPE_LIP_H-3), PIPE_W, 3);
			drawRect(COLOR_PIPE_DARK, p(x, top), PIPE_W, PIPE_LIP_H);
		}

		private void drawGround() {
			fillRect(COLOR_GRASS, p(0, GROUND_Y), WIDTH, GRASS_H);
			fillRect(COLOR_GRASS_DARK, p(0, GROUND_Y+GRASS_H-3), WIDTH, 3);
			fillRect(COLOR_GROUND, p(0, GROUND_Y+GRASS_H), WIDTH, GROUND_H-GRASS_H);
			for(double x=-groundOffset;x<WIDTH;x+=GROUND_TILE)
				fillRect(COLOR_GROUND_DARK, p(x, GROUND_Y+GRASS_H), GROUND_TILE*0.5, GROUND_H-GRASS_H);
		}

		private void drawBird() {
			double a = Math.toRadians(bird.tiltDeg());
			double cos = Math.cos(a);
			double sin = Math.sin(a);
			Point1 center = p(BIRD_X, bird.getY());

			fillPoly(COLOR_BIRD_DARK, at(-13,-4,cos,sin), at(-2,-2,cos,sin), at(-2,5,cos,sin), at(-13,7,cos,sin));
			fillPoly(COLOR_BEAK, at(6,-4,cos,sin), at(19,1,cos,sin), at(6,5,cos,sin));
			fillRoundC(COLOR_BIRD, center, BIRD_R);
			drawRoundC(COLOR_BIRD_DARK, center, BIRD_R);
			fillPoly(COLOR_BIRD_LIGHT, at(-7,0,cos,sin), at(2,-1,cos,sin), at(0,6,cos,sin), at(-8,6,cos,sin));
			fillRoundC(COLOR_TEXT, at(4,-5,cos,sin), 4.5);
			fillRoundC(Color.BLACK, at(5.5,-5,cos,sin), 2);
		}

		private Point1 at(double dx, double dy, double cos, double sin) {
			return p(BIRD_X + dx*cos - dy*sin, bird.getY() + dx*sin + dy*cos);
		}

		private void drawInfo() {
			fillRect(COLOR_INFO, p(0, GROUND_Y+GROUND_H), WIDTH, INFO_H);
			drawString(COLOR_INFO_TEXT, fontPlain(11), p(12, GROUND_Y+GROUND_H+17),
					"ESPACE / HAUT / CLIC  battre des ailes    P  pause");
			drawString(COLOR_INFO_TEXT, fontPlain(11), p(12, GROUND_Y+GROUND_H+33),
					"F1  rejouer    F2  quitter    MEILLEUR  "+best);
		}

		private void drawScore() {
			if(!started || over || paused) return;
			drawStringC(COLOR_SHADOW, fontBold(44), p(WIDTH*0.5+3, 75), ""+score);
			drawStringC(COLOR_TEXT, fontBold(44), p(WIDTH*0.5, 72), ""+score);
		}

		private void drawMessage() {
			if(!started) {
				fillRect(COLOR_VEIL, p(0, 122), WIDTH, 86);
				drawTitle("FLAPPY BIRD", 150);
				drawStringC(COLOR_TEXT, fontBold(15), p(WIDTH*0.5, 188), "ESPACE, HAUT ou CLIC pour commencer");
				return;
			}
			if(paused) {
				fillRect(COLOR_VEIL, p(0, 0), WIDTH, GROUND_Y+GROUND_H);
				drawTitle("PAUSE", GROUND_Y*0.5);
				return;
			}
			if(!over) return;
			fillRect(COLOR_VEIL, p(0, 0), WIDTH, GROUND_Y+GROUND_H);
			drawTitle("GAME OVER", GROUND_Y*0.5-50);
			drawStringC(COLOR_TEXT, fontBold(20), p(WIDTH*0.5, GROUND_Y*0.5), "SCORE   "+score);
			drawStringC(COLOR_TEXT, fontBold(20), p(WIDTH*0.5, GROUND_Y*0.5+30), "MEILLEUR   "+best);
			drawStringC(COLOR_TEXT, fontPlain(14), p(WIDTH*0.5, GROUND_Y*0.5+70), "F1 pour rejouer");
		}

		private void drawTitle(String text, double y) {
			drawStringC(COLOR_SHADOW, fontBold(34), p(WIDTH*0.5+3, y+3), text);
			drawStringC(COLOR_TEXT, fontBold(34), p(WIDTH*0.5, y), text);
		}
	}
}
