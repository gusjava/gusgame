package gus.game5.main.game.p1.o.brickblast;

import static gus.game5.core.util.UtilGui.action;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.dyn.DynTimer;
import gus.game5.core.features.g.G;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.point.point2.Point2D0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRect;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilRandom;

public class GameBrickBlast extends Game1 {

	public static final String TITLE = "Brick Blast";

	public static final int GAME_WIDTH = 900;
	public static final int GAME_HEIGHT = 700;
	public static final int HUD_HEIGHT = 46;

	public static final int COLS = 11;
	public static final double BRICK_W = 72;
	public static final double BRICK_H = 26;
	public static final double BRICK_GAP = 5;
	public static final double BRICK_X0 = (GAME_WIDTH - COLS*BRICK_W)/2;
	public static final double BRICK_Y0 = HUD_HEIGHT + 26;

	public static final double PADDLE_W = 112;
	public static final double PADDLE_H = 14;
	public static final double PADDLE_Y = GAME_HEIGHT - 46;
	public static final double PADDLE_WIDE = 1.7;
	public static final double BALL_R = 8;
	public static final double BALL_SPEED = 5.5;
	public static final double BALL_SPEED_MAX = 8.5;
	public static final double BALL_ANGLE_MAX = Math.toRadians(62);
	public static final double SLOW_FACTOR = 0.62;

	public static final int LIVES = 3;
	public static final int BALLS_MAX = 7;
	public static final int DROP_CHANCE = 6;
	public static final int EFFECT_TICKS = 600;
	public static final int CLEARED_TICKS = 130;

	public static final int SOLID = 9;

	public static final int BONUS_MULTI = 0;
	public static final int BONUS_WIDE = 1;
	public static final int BONUS_SLOW = 2;
	public static final int BONUS_LIFE = 3;

	public static final String[] BONUS_LABEL = {"M", "W", "S", "+1"};
	public static final int[] BONUS_WEIGHTS = {30, 28, 24, 18};

	public static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 12);

	public static final Color BG = new Color(16, 18, 34);
	public static final Color INK = new Color(236, 240, 241);
	public static final Color DIM = new Color(120, 128, 160);
	public static final Color BALL_C = new Color(255, 205, 80);
	public static final Color PADDLE_C = new Color(93, 173, 226);
	public static final Color SOLID_C = new Color(127, 140, 141);

	public static final Color[] BRICK_C = {
		new Color(46, 204, 113),
		new Color(241, 196, 15),
		new Color(231, 76, 60)
	};

	public static final Color[] BONUS_C = {
		new Color(155, 89, 182),
		new Color(93, 173, 226),
		new Color(26, 188, 156),
		new Color(231, 76, 60)
	};

	public static final int[][][] LEVELS = {
		{
			{1,1,1,1,1,1,1,1,1,1,1},
			{2,2,2,2,2,2,2,2,2,2,2},
			{1,1,1,1,1,1,1,1,1,1,1}
		},
		{
			{0,0,0,0,0,3,0,0,0,0,0},
			{0,0,0,0,2,2,2,0,0,0,0},
			{0,0,0,1,1,2,1,1,0,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,1,1,1,1,1,1,1,1,1,0}
		},
		{
			{1,0,1,0,1,0,1,0,1,0,1},
			{0,2,0,2,0,2,0,2,0,2,0},
			{1,0,1,0,9,0,9,0,1,0,1},
			{0,2,0,2,0,2,0,2,0,2,0},
			{3,0,3,0,3,0,3,0,3,0,3}
		},
		{
			{9,1,1,1,1,1,1,1,1,1,9},
			{9,2,2,2,2,2,2,2,2,2,9},
			{9,3,3,3,0,0,0,3,3,3,9},
			{0,2,2,2,2,2,2,2,2,2,0},
			{0,0,1,1,1,1,1,1,1,0,0}
		},
		{
			{0,0,2,2,0,0,0,2,2,0,0},
			{0,0,2,2,0,0,0,2,2,0,0},
			{0,0,0,0,0,3,0,0,0,0,0},
			{1,0,0,0,0,0,0,0,0,0,1},
			{0,1,3,3,3,3,3,3,3,1,0}
		}
	};

	public static void main(String[] args) {
		GameBrickBlast main = new GameBrickBlast();
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
		s.setBackground(BG);
		s.setFont(FONT);
	}

	/*
	 * DATA
	 */

	private ShapeList<Brick> brickList;
	private ShapeList<Spark> sparkList;
	private ShapeList<Bonus> bonusList;
	private ShapeList<Ball> ballList;
	private Paddle paddle;
	private DynTimer clearedTimer;

	private int score;
	private int lives;
	private int level;
	private int wideTicks;
	private int slowTicks;
	private boolean launched;
	private boolean cleared;
	private boolean gameOver;

	protected void initialize1() {
		score = 0;
		lives = LIVES;
		level = 1;
		gameOver = false;

		addDraw(new Board());
		brickList = newShapeList();
		sparkList = newShapeList();
		bonusList = newShapeList();
		ballList = newShapeList();
		paddle = newShape(new Paddle());
		clearedTimer = newDynTimer();

		buildHud();
		startLevel();
	}

	private void buildHud() {
		hudText(p1(20, 30), INK, 17, ()->"SCORE  "+score);
		hudText(p1(250, 30), INK, 17, ()->"LIVES  "+lives);
		hudText(p1(420, 30), INK, 17, ()->"LEVEL  "+level);
		hudText(p1(590, 30), DIM, 15, this::effectText);
		hudText(p1(20, GAME_HEIGHT-14), DIM, 12,
				()->"mouse: move    click or space: launch    F1: new game    F2: exit");

		DrawingText hint = newDrawingTextC(DIM, p1(GAME_WIDTH/2, PADDLE_Y-70), "CLICK OR PRESS SPACE TO LAUNCH");
		hint.setFontBold(18);
		hint.setDrawable(()->!launched && !cleared && !gameOver);

		DrawingText message = newDrawingTextC(INK, p1(GAME_WIDTH/2, GAME_HEIGHT/2-60), this::messageText);
		message.setFontBold(44);
		message.setDrawable(()->cleared || gameOver);
	}

	private DrawingText hudText(Point1 origin, Color color, int size, G<String> gString) {
		DrawingText text = newDrawingText(color, origin, gString);
		text.setFontBold(size);
		return text;
	}

	private String effectText() {
		if(wideTicks>0 && slowTicks>0) return "WIDE + SLOW";
		if(wideTicks>0) return "WIDE";
		if(slowTicks>0) return "SLOW";
		return "";
	}

	private String messageText() {
		if(gameOver) return "GAME OVER";
		if(cleared) return "LEVEL "+level+" CLEARED";
		return "";
	}

	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(handleRestartExit()) return;

		if(gameOver) {
			goNext();
			clean();
			return;
		}

		if(wideTicks>0) wideTicks--;
		if(slowTicks>0) slowTicks--;
		paddle.setWidth(wideTicks>0 ? PADDLE_W*PADDLE_WIDE : PADDLE_W);

		if(!cleared && !launched) {
			holdBall();
			if(k.in().space() || mouse().button1().justPressed()) launch();
		}

		goNext();

		if(!cleared) {
			for(Ball ball : ballList) collide(ball);
			catchBonus();
		}

		clean();

		if(!cleared) {
			if(ballList.isEmpty()) loseLife();
			else if(brickList.none(Brick::isBreakable)) clearLevel();
		}
	}

	/*
	 * LEVEL
	 */

	private void startLevel() {
		brickList.clear();
		sparkList.clear();
		bonusList.clear();
		ballList.clear();

		wideTicks = 0;
		slowTicks = 0;
		cleared = false;

		buildBricks(levelPattern());
		newBall();
	}

	private void clearLevel() {
		cleared = true;
		ballList.clear();
		bonusList.clear();
		clearedTimer.start(CLEARED_TICKS, this::nextLevel);
	}

	private void nextLevel() {
		level++;
		startLevel();
	}

	private int[][] levelPattern() {
		if(level<=LEVELS.length) return LEVELS[level-1];
		return randomPattern();
	}

	private int[][] randomPattern() {
		int rows = Math.min(3 + level/2, 7);
		int[][] data = new int[rows][COLS];
		boolean breakable = false;

		for(int row=0;row<rows;row++) {
			for(int col=0;col<COLS;col++) {
				if(UtilRandom.chance(5)) continue;
				if(UtilRandom.chance(14)) {data[row][col] = SOLID; continue;}
				data[row][col] = UtilRandom.randomInt(1, 4);
				breakable = true;
			}
		}

		if(!breakable) data[rows-1][COLS/2] = 1;
		return data;
	}

	private void buildBricks(int[][] data) {
		for(int row=0;row<data.length;row++) {
			for(int col=0;col<data[row].length;col++) {
				int hp = data[row][col];
				if(hp>0) brickList.add(new Brick(col, row, hp));
			}
		}
	}

	/*
	 * BALL
	 */

	private void newBall() {
		ballList.add(new Ball(paddleX(), ballRestY(), 0, 0));
		launched = false;
	}

	private void holdBall() {
		for(Ball ball : ballList) ball.place(paddleX(), ballRestY());
	}

	private void launch() {
		double angle = UtilRandom.randomDouble(-0.45, 0.45);
		for(Ball ball : ballList) ball.aim(angle, ballSpeed());
		launched = true;
	}

	private void loseLife() {
		lives--;
		wideTicks = 0;
		slowTicks = 0;
		bonusList.clear();
		if(lives<=0) gameOver = true;
		else newBall();
	}

	private double ballSpeed() {
		return Math.min(BALL_SPEED + (level-1)*0.22, BALL_SPEED_MAX);
	}

	private double paddleX() {
		return paddle.getAnchor().getX();
	}

	private double ballRestY() {
		return PADDLE_Y - PADDLE_H/2 - BALL_R - 2;
	}

	/*
	 * COLLISION
	 */

	private void collide(Ball ball) {
		Point2 a = ball.getAnchor();

		if(a.getX()-BALL_R < 0) {a.setX(BALL_R); ball.pushX(1);}
		if(a.getX()+BALL_R > GAME_WIDTH) {a.setX(GAME_WIDTH-BALL_R); ball.pushX(-1);}
		if(a.getY()-BALL_R < HUD_HEIGHT) {a.setY(HUD_HEIGHT+BALL_R); ball.pushY(1);}
		if(a.getY()-BALL_R > GAME_HEIGHT) {ball.lose(); return;}

		if(ball.goingDown() && paddle.intersects(ball)) {
			double offset = (a.getX()-paddleX())/(paddle.getWidth()/2);
			if(offset<-1) offset = -1;
			if(offset>1) offset = 1;
			ball.aim(offset*BALL_ANGLE_MAX, ballSpeed());
			a.setY(ballRestY());
			return;
		}

		List<Brick> hits = brickList.findAllInter(ball);
		if(hits.isEmpty()) return;

		bounceOn(ball, hits.get(0));
		for(Brick brick : hits) hitBrick(brick);
	}

	private void bounceOn(Ball ball, Brick brick) {
		Point2 a = ball.getAnchor();
		double overlapX = Math.min(a.getX()+BALL_R-brick.getP0().getX(), brick.getP1().getX()-a.getX()+BALL_R);
		double overlapY = Math.min(a.getY()+BALL_R-brick.getP0().getY(), brick.getP1().getY()-a.getY()+BALL_R);

		if(overlapX<overlapY) ball.flipX();
		else ball.flipY();
	}

	private void hitBrick(Brick brick) {
		if(brick.isOver()) return;

		if(!brick.isBreakable()) {
			spawnSparks(brick.getPc(), SOLID_C, 3);
			return;
		}

		Color color = brick.brickColor();
		boolean broken = brick.hit();
		score += broken ? 25 : 10;
		spawnSparks(brick.getPc(), color, broken ? 12 : 4);

		if(broken && UtilRandom.chance(DROP_CHANCE)) {
			bonusList.add(new Bonus(brick.getPc(), UtilRandom.chances(BONUS_WEIGHTS)));
		}
	}

	private void spawnSparks(Point0 p, Color color, int count) {
		for(int i=0;i<count;i++) sparkList.add(new Spark(p, color));
	}

	/*
	 * BONUS
	 */

	private void catchBonus() {
		for(Bonus bonus : bonusList.findAllInter(paddle)) {
			bonus.take();
			applyBonus(bonus.getType());
		}
	}

	private void applyBonus(int type) {
		switch(type) {
		case BONUS_MULTI: splitBalls(); break;
		case BONUS_WIDE: wideTicks = EFFECT_TICKS; break;
		case BONUS_SLOW: slowTicks = EFFECT_TICKS; break;
		case BONUS_LIFE: lives++; break;
		}
		score += 50;
	}

	private void splitBalls() {
		if(!launched) return;

		List<Ball> current = ballList.findAll(ball->!ball.isOver());
		for(Ball ball : current) {
			if(ballList.size()>=BALLS_MAX) return;
			ballList.add(ball.copyRotated(0.42));
			if(ballList.size()>=BALLS_MAX) return;
			ballList.add(ball.copyRotated(-0.42));
		}
	}

	/*
	 * SHAPES
	 */

	private class Board extends Drawing1 {
		public Board() {
			super(0, 0);
		}

		protected void draw() {
			drawLine(DIM, p(0, HUD_HEIGHT), p(GAME_WIDTH, HUD_HEIGHT));
		}
	}

	private class Paddle extends ShapeRect {
		public Paddle() {
			super(new Point2D0(GameBrickBlast.this::paddlePoint), PADDLE_W, PADDLE_H);
		}

		protected void drawShape() {
			fillRectC(PADDLE_C, getWidth(), getHeight());
			fillRectC(PADDLE_C.brighter(), p(0, -getHeight()/2+3), getWidth()-10, 4);
		}
	}

	private Point0 paddlePoint() {
		double half = paddle==null ? PADDLE_W/2 : paddle.getWidth()/2;
		double x = mouse().point().getX();
		if(x<half) x = half;
		if(x>GAME_WIDTH-half) x = GAME_WIDTH-half;
		return p1(x, PADDLE_Y);
	}

	private class Ball extends ShapeRound {
		private double vx;
		private double vy;
		private boolean lost;

		public Ball(double x, double y, double vx, double vy) {
			super(new Point2(x, y), BALL_R, BALL_C);
			this.vx = vx;
			this.vy = vy;
		}

		public void goNext() {
			double factor = slowTicks>0 ? SLOW_FACTOR : 1;
			getAnchor().setDerived(vx*factor, vy*factor);
			super.goNext();
		}

		protected void drawShape() {
			fillRoundC(buildCircularGradient(BALL_R, Color.WHITE, BALL_C), BALL_R);
		}

		public void place(double x, double y) {
			getAnchor().setXY(x, y);
			vx = 0;
			vy = 0;
		}

		public void aim(double angle, double speed) {
			vx = speed*Math.sin(angle);
			vy = -speed*Math.cos(angle);
		}

		public Ball copyRotated(double angle) {
			Point2 a = getAnchor();
			double cos = Math.cos(angle);
			double sin = Math.sin(angle);
			return new Ball(a.getX(), a.getY(), vx*cos-vy*sin, vx*sin+vy*cos);
		}

		public void flipX() {vx = -vx;}

		public void flipY() {vy = -vy;}

		public void pushX(int dir) {vx = dir*Math.abs(vx);}

		public void pushY(int dir) {vy = dir*Math.abs(vy);}

		public boolean goingDown() {return vy>0;}

		public void lose() {lost = true;}

		public boolean isOver() {return lost;}
	}

	private class Brick extends ShapeRect {
		private int hp;
		private boolean solid;
		private boolean broken;

		public Brick(int col, int row, int hp) {
			super(brickAnchor(col, row), BRICK_W-BRICK_GAP, BRICK_H);
			this.hp = hp;
			this.solid = hp==SOLID;
		}

		protected void drawShape() {
			Color color = brickColor();
			fillRectC(color, getWidth(), getHeight());
			fillRectC(color.brighter(), p(0, -getHeight()/2+3), getWidth()-8, 4);
			drawRectC(BG, getWidth(), getHeight());
		}

		public Color brickColor() {
			if(solid) return SOLID_C;
			return BRICK_C[Math.max(1, Math.min(hp, BRICK_C.length))-1];
		}

		public boolean hit() {
			hp--;
			if(hp<=0) broken = true;
			return broken;
		}

		public boolean isBreakable() {return !solid;}

		public boolean isOver() {return broken;}
	}

	private Point1 brickAnchor(int col, int row) {
		return p1(BRICK_X0 + col*BRICK_W + BRICK_W/2,
				BRICK_Y0 + row*(BRICK_H+BRICK_GAP) + BRICK_H/2);
	}

	private class Bonus extends ShapeRect {
		private int type;
		private boolean taken;

		public Bonus(Point0 p, int type) {
			super(new Point2(p.getX(), p.getY()), 30, 20);
			this.type = type;
			getAnchor().setDerived(0, 2.3);
			setFontBold(13);
		}

		protected void drawShape() {
			fillRectC(BONUS_C[type], getWidth(), getHeight());
			drawRectC(BG, getWidth(), getHeight());
			drawStringC(BG, BONUS_LABEL[type]);
		}

		public int getType() {return type;}

		public void take() {taken = true;}

		public boolean isOver() {
			return taken || getAnchor().getY()>GAME_HEIGHT+20;
		}
	}

	private class Spark extends ShapeRound {
		private Color color;
		private int life;

		public Spark(Point0 p, Color color) {
			super(new Point2(p.getX(), p.getY()), UtilRandom.randomDouble(2, 4.5), color);
			this.color = color;
			this.life = UtilRandom.randomInt(16, 34);
			getAnchor().setDerived(UtilRandom.randomDouble(-2.4, 2.4), UtilRandom.randomDouble(-2.8, 1.2));
		}

		public void goNext() {
			super.goNext();
			life--;
			Point2 derived = getAnchor().getDerived();
			derived.setY(derived.getY()+0.14);
		}

		protected void drawShape() {
			int alpha = Math.min(255, life*10);
			fillRoundC(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha), getRadius());
		}

		public boolean isOver() {return life<=0;}
	}
}
