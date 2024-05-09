package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.control.ShapeControlBorderRebound;
import gus.game5.core.util.UtilRandom;

public class AnimBallBattle extends Game2 {
	
	public static void main(String[] args) {
		AnimBallBattle main = new AnimBallBattle();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Ball battle animation");
		s.setWidth(1400);
		s.setHeight(760);
		s.setSleep(10);
	}
	
	public static final int RADIUS = 8;
	public static final double SPEED_MIN = 0.5;
	public static final double SPEED_MAX = 3;
	public static final int NUMBER = 300;
	
	public static final Color[] COLORS = new Color[] {
		Color.WHITE, 
		Color.ORANGE, 
		Color.GREEN.darker()
	};
	
	private ShapeList<Ball> balls;
	private ShapeControlBorderRebound control;
	
	protected void initialize1() {
		balls = newShapeList();
		control = new ShapeControlBorderRebound(this);
		
		for(int i=0;i<NUMBER;i++) {
			balls.add(new Ball(1));
			balls.add(new Ball(2));
		}
	}
	
	
	private class Ball extends ShapeRound {
		private int team; //1, 2, 0
		
		public Ball(int team) {
			super(UtilRandom.randomPoint2(AnimBallBattle.this), RADIUS);
			getAnchor().setDerived(UtilRandom.randomDouble(SPEED_MIN, SPEED_MAX), Angle.random());
			this.team = team;
		}
		
		protected void drawShape() {
			fillRoundC(COLORS[team], RADIUS);
			drawRoundC(Color.BLACK, RADIUS);
		}
		
		public void faceTeam(int teamOut) {
			if(teamOut==0 || teamOut==team) return;
			this.team = team==0 ? teamOut : 0;
		}
		public int getTeam() {
			return team;
		}
	}
	

	protected void after() {
		balls.control(control);
		
		for(Ball ball1 : balls) for(Ball ball2 : balls) {
			if(ball1!=ball2 && ball1.intersects(ball2)) {
				int team1 = ball1.getTeam();
				int team2 = ball2.getTeam();
				
				ball1.faceTeam(team2);
				ball2.faceTeam(team1);
			}
		}
	}
}
