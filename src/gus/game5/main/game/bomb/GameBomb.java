package gus.game5.main.game.bomb;

import java.awt.Color;

import gus.game5.core.drawing.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilRandom;

public class GameBomb extends Game1 {
	
	public static final int BOMB_RATE = 40;
	public static final int BOMB_INITRADIUS = 5;
	public static final double BOMB_MIN_GROWTH = 0.2;
	public static final double BOMB_MAX_GROWTH = 0.7;
	public static final double BOMB_MAX_ENERGY = 100;
	public static final int BOMB_DAMAGE = 1;

	public static final int BONUS_RATE = 600;
	public static final int BONUS_RADIUS = 20;
	public static final double BONUS_MIN_GROWTH = 0.2;
	public static final double BONUS_MAX_GROWTH = 0.7;
	public static final double BONUS_MAX_ENERGY = 100;
	public static final int BONUS_LIFE = 3;
	
	public static final int SHOT_DURATION = 30;
	public static final int SHOT_RADIUS = 15;
	public static final int SHOT_STARS = 8;
	
	public static final int FREEZE_ENERGY = 250;
	public static final double FREEZE_IMPACT = 3;
	
	
	public static final int MAX_LIFE = 20;
	
	
	public static void main(String[] args) {
		GameBomb main = new GameBomb();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Game bomb");
		s.setWidth(1500);
		s.setHeight(780);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	private ShapeList<Bomb> bombList;
	private ShapeList<Bonus> bonusList;
	private ShapeList<Shot> shotList;
	
	private int life;
	private int killedNb;
	private int shotNb;
	private int energy;
	private boolean gameOver;
	
	protected void initialize1() {
		life = MAX_LIFE;
		killedNb = 0;
		shotNb = 0;
		energy = 0;
		gameOver = false;
		
		bombList = newShapeList();
		bonusList = newShapeList();
		shotList = newShapeList();
		
		DrawingText lifeText = newDrawingText(p1(30, 40), ()->"life: "+life);
		lifeText.setFontBold(20);
		
		DrawingText killedText = newDrawingText(p1(30, 70), ()->"killed: "+killedNb);
		killedText.setFontBold(20);
		
		DrawingText shotText = newDrawingText(p1(30, 100), ()->"shot: "+shotNb);
		shotText.setFontBold(20);
		
		DrawingText freezeText = newDrawingText(Color.CYAN, p1(800, 40), ()->"freeze: "+energy/FREEZE_ENERGY);
		freezeText.setFontBold(20);
		
		DrawingText gameOverText = newDrawingText(Color.RED, p1(300, 200), "GAME OVER");
		gameOverText.setFontBold(70);
		gameOverText.setDrawable(()->gameOver);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1())	restart();
		if(k.in().F2())	exit();
		
		if(gameOver) return;
		if(k.in().space()) freeze();
		
		if(mouse().button1().justPressed()) shoot();
		shoot2();
		
		if(UtilRandom.chance(BOMB_RATE)) createBomb();
		if(UtilRandom.chance(BONUS_RATE)) createBonus();
		
		goNext();
		clean();
		
		energy++;
		if(life<=0) {
			life = 0;
			gameOver = true;
		}
	}
	
	private void freeze() {
		if(energy<FREEZE_ENERGY) return;
		energy -= FREEZE_ENERGY;
		bombList.forEach(Bomb::freeze);
		bonusList.forEach(Bonus::freeze);
	}
	
	private void shoot() {
		shotNb++;
		Point0 p = mouse().pointCurrent();
		
		shotList.add(new Shot(p));
		bombList.removeIf(bomb->bomb.target(p));
		bonusList.removeIf(bonus->bonus.target(p));
	}
	
	private void shoot2() {
		Point0 p = mouse().pointCurrent();
		
		boolean found1 = bombList.removeIf(bomb->bomb.target(p));
		boolean found2 = bonusList.removeIf(bonus->bonus.target(p));
		
		if(found1 || found2) {
			shotNb++;
			shotList.add(new Shot(p));
		}
	}
	
	private void createBomb() {
		bombList.add(new Bomb(UtilRandom.randomPoint1(this)));
	}
	
	private void createBonus() {
		bonusList.add(new Bonus(UtilRandom.randomPoint1(this)));
	}
	
	
	private class Bomb extends ShapeRound {
		private double energy;
		private double growth;
		private boolean freezed = false;

		public Bomb(Point0 p0) {
			super(p0, BOMB_INITRADIUS, AnchorType.CENTER);
			energy = 0;
			growth = UtilRandom.randomDouble(BOMB_MIN_GROWTH, BOMB_MAX_GROWTH);
			setColor(Color.BLACK);
		}
		
		protected void draw() {
			if(isBurning()) {
				fillRoundC(new Color(255,204,204), getRadius());
				drawRoundC(Color.RED, getRadius());
			}
			else {
				fillRoundC(getRadius());
				drawRoundC(freezed ? Color.CYAN : Color.BLACK, getRadius());
			}
		}
		
		public void goNext() {
			energy += growth;
			setRadius(BOMB_INITRADIUS + energy);
			int v = (int) (255.0*Math.min(energy/BOMB_MAX_ENERGY, 1));
			setColor(new Color(v,v,v));
			if(isOver()) life -= BOMB_DAMAGE;
		}
		
		public boolean isBurning() {
			return energy>=BOMB_MAX_ENERGY-3;
		}
		
		public boolean isOver() {
			return energy>=BOMB_MAX_ENERGY;
		}
		
		public void freeze() {
			freezed = true;
			growth /= FREEZE_IMPACT;
		}
		
		public boolean target(Point0 p) {
			if(!contains(p)) return false;
			killedNb++;
			return true;
		}
	}
	
	
	private class Bonus extends ShapeRound {
 		private double energy;
		private double growth;
		private boolean freezed = false;

		public Bonus(Point0 p0) {
			super(p0, BONUS_RADIUS, AnchorType.CENTER);
			energy = 0;
			growth = UtilRandom.randomDouble(BONUS_MIN_GROWTH, BONUS_MAX_GROWTH);
			setColor(Color.YELLOW);
		}
		
		protected void draw() {
			fillRoundC(getRadius());
			drawRoundC(freezed ? Color.CYAN : Color.ORANGE, getRadius());
		}
		
		public void goNext() {
			energy += growth;
		}
		
		public boolean isOver() {
			return energy>=BONUS_MAX_ENERGY;
		}
		
		public void freeze() {
			freezed = true;
			growth /= FREEZE_IMPACT;
		}
		
		public boolean target(Point0 p) {
			if(!contains(p)) return false;
			life += BONUS_LIFE;
			return true;
		}
	}
	
	private class Shot extends ShapeRound {
		private double duration;

		public Shot(Point0 p0) {
			super(p0, SHOT_RADIUS, AnchorType.CENTER);
			duration = SHOT_DURATION;
			setColor(Color.GRAY);
		}
		
		protected void draw() {
			drawStarC(getRadius(), SHOT_STARS);
		}
		
		public void goNext() {
			duration--;
		}
		
		public boolean isOver() {
			return duration<=0;
		}
	}
}
