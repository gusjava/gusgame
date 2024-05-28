package gus.game5.main.l1.bloon;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeList;
import gus.game5.main.l1.bloon.missile.Missile;
import gus.game5.main.l1.bloon.round.Round;
import gus.game5.main.l1.bloon.tower.Tower;
import gus.game5.main.l1.bloon.tower.Tower1;
import gus.game5.main.l1.bloon.type.Bloon;

import static gus.game5.core.util.UtilGui.*;

public class GameBloon extends Game1 {
	
	public static void main(String[] args) {
		GameBloon main = new GameBloon();
		main.displayInWindows();
		main.start();
	}
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Bloon");
		s.setWidth(1000);
		s.setHeight(700);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	// DATA
	
	private ShapeList<Bloon> bloons;
	private ShapeList<Tower> towers;
	private ShapeList<Missile> missiles;
	private List<Point1> path;
	private double life;
	private Round round;
	
	private int step;
	private long step1Start;
	private long step1Stop;
	
	private double[] roundParams = new double[] {80, 100, 120, 150, 200};
	private double ratio = 0.7;
	
	public List<Point1> getPath() {
		return path;
	}
	
	public double getLife() {
		return life;
	}
	
	public void setLife(double life) {
		this.life = life;
	}
	
	public ShapeList<Missile> getMissiles() {
		return missiles;
	}
	
	public ShapeList<Bloon> getBloons() {
		return bloons;
	}
	
	protected void initialize1() {
		
		life = 200;
		step = 0;
		
		path = new ArrayList<>();
		path.add(p1(0, 50));
		path.add(p1(50, 50));
		path.add(p1(80, 90));
		path.add(p1(120, 500));
		path.add(p1(700, 500));
		path.add(p1(600, 470));
		path.add(p1(200, 600));
		path.add(p1(400, 200));
		path.add(p1(600, 300));
		path.add(p1(750, 300));
		path.add(p1(900, 200));
		path.add(p1(1000, 460));
		
		bloons = newShapeList();
		towers = newShapeList();
		missiles = newShapeList();
		
		addDraw(new PathDraw());
		
		DrawingText dt = newDrawingText(Color.GREEN, p1(800, 100), ()->"Life = "+life);
		dt.setFont(30);

		dt = newDrawingText(Color.BLACK, p1(600, 100), ()->"Time = "+getStep1Duration());
		dt.setDrawable(()->getStep1Duration()>0);
		dt.setFont(30);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(isGameOver()) return;
		
		if(step==0) handleStep0();
		else handleStep1();
	}
	
	private void handleStep0() {
		if(mouse().button1().justPressed()) {
			Tower tower1 = new Tower1(this, mouse().pointCurrent());
			towers.add(tower1);

			for(int i=0;i<roundParams.length;i++)
				roundParams[i] *= ratio;
			round = new Round(this, 1000, roundParams);
			step1Start = getCount();
			step = 1;
		}
	}
			
	private void handleStep1() {
		if(!isStep1DurationOver())
		round.generate();
		
		for(Tower tower : towers) {
			for(Bloon bloon : bloons) {
				double distance = bloon.getAnchor().dist(tower.getAnchor());
				if(distance<=tower.getRange()) {
					tower.targetBloon(bloon);
				}
			}
		}
		
		for(Missile missile : missiles) {
			for(Bloon bloon : bloons) {
				if(missile.intersects(bloon)) {
					missile.explode();
					bloon.damage(1);
				}
			}
		}

		goNext();
		clean();
		
		if(isStep1DurationOver() && bloons.isEmpty()) {
			step = 0;
			step1Stop = getCount();
		}
	}
	
	public void handleGameOver() {
		life = 0;
		DrawingText dt = newDrawingTextC(Color.RED, gameCenter(), "Game Over");
		dt.setFont(50);
	}
	
	private boolean isGameOver() {
		return life<=0;
	}
	
	private class PathDraw extends Drawing1 {
		protected void draw() {
			for(int i=1;i<path.size();i++) {
				drawLine(path.get(i-1), path.get(i)); 
			}
		}
	}
	
	private long getStep1Duration() {
		return step1Start>0 ? getCount()-step1Start : 0;
	}
	
	private boolean isStep1DurationOver() {
		return getStep1Duration() >= round.getDuration();
	}
}
