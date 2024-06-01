package gus.game5.main.game.p1.missile;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.util.UtilRandom;

public class GameMissile extends Game1 {
	
	
	public static final double[] ENEMY_SPEEDS = new double[] {
			1.3,	1.8,	1.0
	};
	public static final double[] ENEMY_BLASTSIZES = new double[] {
			50, 	80, 	20
	};
	public static final Color[] ENEMY_COLORS = new Color[] {
			Color.CYAN, Color.BLUE, Color.GREEN
	};
	public static final int[] ENEMY_GAINS = new int[] {
			0, 		3, 		0
	};
	public static final int[] ENEMY_DAMAGES = new int[] {
			4, 		10, 	1
	};
	public static final int[] ENEMY_DUPLICATIONRATES = new int[] {
			1300, 	0, 	300
	};
	
	public static final double MISSILE0_SPEED = 10;
	public static final double MISSILE0_BLASTSIZE = 50;
	public static final Color MISSILE0_COLOR = Color.ORANGE;
	
	public static final double MISSILE1_SPEED = 14;
	public static final double MISSILE1_BLASTSIZE = 250;
	public static final Color MISSILE1_COLOR = Color.RED;
	

	public static final int MISSILE1_RATE = 10;
	public static final int MISSILE_STOCK_INIT = 20;
	
	public static final double BLAST_INCR = 0.9;
	public static final int BLAST_STAR_NB = 24;
	
	public static final double AREA_PERCENT = 0.5;
	public static final double BASE_HEIGHT = 50;
	
	public static final int LIFE_INCR_RATE = 750;
	public static final int LIFE_MAX = 100;
	
	
	private ShapeList<Missile> missiles;
	private ShapeList<Enemy> enemies;
	private ShapeList<Blast> blasts;
	
	private Point1 missileStart;
	private int missileStock = 0;
	private int life = 0;
	private int enemyFreq = 0;
	private int missileThrown = 0;
	
	
	protected void initSettings(Settings s) {
		s.setTitle("Game missiles");
		s.setWidth(800);
		s.setHeight(800);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	
	protected void initialize1() {
		addDraw(new BaseDraw());
		
		blasts = newShapeList();
		enemies = newShapeList();
		missiles = newShapeList();
		
		missileStart = new Point2(gameWidth()/2,gameHeight()-BASE_HEIGHT);
		missileStock = MISSILE_STOCK_INIT;
		life = LIFE_MAX;
		enemyFreq = 0;
		missileThrown = 0;
	}

	
	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(isGameOver()) return;
		
		if(mouse().button1().justPressed()) 
			throwMissile();
		
		if(getCount()%LIFE_INCR_RATE==0 && life<LIFE_MAX) {
			changeLife(1);
		}
		
		enemyFreq = enemyFreq();
		if(UtilRandom.chance(enemyFreq))
			enemies.add(createNewEnemy());

		List<Blast> newBlasts = new ArrayList<>();
		List<Enemy> newEnemies = new ArrayList<>();
		
		{
			Iterator<Missile> it = missiles.iterator();
			while(it.hasNext()) {
				Missile missile = it.next();
				if(missile.targetReached()) {
					it.remove();
					newBlasts.add(new Blast(missile, BLAST_INCR, BLAST_STAR_NB));
				}
			}
		}
		{
			Iterator<Enemy> it = enemies.iterator();
			while(it.hasNext()) {
				Enemy enemy = it.next();
				if(enemy.targetReached()) {
					it.remove();
					newBlasts.add(new Blast(enemy, BLAST_INCR, BLAST_STAR_NB));
					changeLife(-enemy.getDamage());
				}
				else if(UtilRandom.chance(enemy.getDuplicationRate())) {
					newEnemies.add(duplicateEnemy(enemy));
				}
			}
		}
		{
			Iterator<Blast> it = blasts.iterator();
			while(it.hasNext()) {
				Blast blast = it.next();
				if(blast.over()) {
					it.remove();
				}
				else {
					Iterator<Enemy> it1 = enemies.iterator();
					while(it1.hasNext()) {
						Enemy enemy = it1.next();
						if(blast.intersects(enemy)) {
							it1.remove();
							newBlasts.add(new Blast(enemy, BLAST_INCR, BLAST_STAR_NB));
							missileStock += enemy.getGain();
						}
					}
				}
			}
		}
		
		blasts.addAll(newBlasts);
		enemies.addAll(newEnemies);
		
		blasts.goNext();
		missiles.goNext();
		enemies.goNext();
	}
	
	
	private void throwMissile() {
		if(missileStock<=0) return;
		
		missileThrown++;
		missileStock--;
		
		int type = missileThrown%MISSILE1_RATE==0 ? 1 : 0;
		Color color = type==0 ? MISSILE0_COLOR : MISSILE1_COLOR;
		double speed = type==0 ? MISSILE0_SPEED : MISSILE1_SPEED;
		double blastSize = type==0 ? MISSILE0_BLASTSIZE : MISSILE1_BLASTSIZE;
		
		missiles.add(new Missile(
				missileStart, 
				mouse().pointCurrent(), 
				color, 
				speed,
				blastSize));
	}
	
	
	private Enemy createNewEnemy() {
		int type = UtilRandom.randomInt(3);
		Point1 start = enemyRandomStart();
		Point1 target = enemyRandomTarget();
		
		Color color = ENEMY_COLORS[type];
		double speed = ENEMY_SPEEDS[type];
		double blastSize = ENEMY_BLASTSIZES[type];
		int gain = ENEMY_GAINS[type];
		int damage = ENEMY_DAMAGES[type];
		int duplicationRate = ENEMY_DUPLICATIONRATES[type];
		
		return new Enemy(start, target, color, speed, blastSize, gain, damage, duplicationRate);
	}
	
	private Enemy duplicateEnemy(Enemy enemy) {
		Point1 start = new Point1(enemy.getAnchor());
		Point1 target = enemyRandomTarget();
		
		Color color = enemy.getColor();
		double speed = enemy.getSpeed();
		double blastSize = enemy.getBlastSize();
		int gain = enemy.getGain();
		int damage = enemy.getDamage();
		int duplicationRate = enemy.getDuplicationRate();
		
		return new Enemy(start, target, color, speed, blastSize, gain, damage, duplicationRate);
	}
	
	
	
	private int enemyFreq() {
		double r = ((double) getCount()%500)/500.0;
		return (int) ((1-r)*100+20);
	}
	
	private Point1 enemyRandomStart() {
		return new Point1(UtilRandom.randomDouble(gameWidth()), 0);
	}
	
	private Point1 enemyRandomTarget() {
		return new Point1(gameWidth()*(1-AREA_PERCENT)/2 + UtilRandom.randomDouble(gameWidth()*AREA_PERCENT), gameHeight()-BASE_HEIGHT);
	}
	
	
	private void changeLife(int v) {
		life += v;
		if(life<0) life = 0;
		if(life>LIFE_MAX) life = LIFE_MAX;
	}
	
	
	private boolean isGameOver() {
		return life<=0;
	}
	
	
	
	
	private class BaseDraw extends Drawing1 {

		protected void draw() {
//			drawString(10, 15, "freq: "+enemyFreq);
			
			fillRect(Color.DARK_GRAY, p(0, gameHeight()-BASE_HEIGHT), gameWidth(), BASE_HEIGHT);
			drawString(Color.WHITE, p(10, gameHeight()-BASE_HEIGHT+20), "missiles: "+missileStock);
			drawString(Color.YELLOW, p(10, gameHeight()-BASE_HEIGHT+35), "life: "+life);
			
			if(isGameOver()) {
				drawString(Color.RED, font(40), p(200, gameHeight()/2), "Game Over");
			}
			
			double x = gameWidth()/2-3;
			double y = gameHeight()-BASE_HEIGHT-20;
			double w = 6;
			double h = 20;
			
			Point1 p1 = new Point1(x,y);
			Point1 p2 = new Point1(x+w,y);
			Point1 p3 = new Point1(x+w,y+h);
			Point1 p4 = new Point1(x,y+h);
			
			Point0 pm = mouse().pointCurrent();
			if(pm!=null) {
				Point1 pr = new Point1(x+w/2, y+h);
				Angle angle = pm.pSub(pr).angle().add90();
				p1.rotate(pr, angle);
				p2.rotate(pr, angle);
				p3.rotate(pr, angle);
				p4.rotate(pr, angle);
			}
			
			fillPoly(Color.RED, p1, p2, p3, p4);
			
			for(Missile missile : missiles)  {
				drawLine(missile.getColor(), missile.getStart(), missile.getPc());
			}
		}
	}
	
	
	
	

	
	
	public static void main(String[] args) {
		GameMissile main = new GameMissile();
		main.displayInWindows();
		main.start();
	}
}
