package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.control.ShapeControlBorderRebound;
import gus.game5.core.util.UtilRandom;

public class AnimEpidemic extends Game2 {

	public static final int RADIUS = 5;
	public static final double SPEED_MIN = 0.6;
	public static final double SPEED_MAX = 1.8;
	public static final int COUNT = 400;
	public static final int INITIAL_INFECTED = 2;

	public static final double INFECTION_CHANCE = 0.30;
	public static final int RECOVERY_TICKS = 300;

	public static final int STATE_S = 0;
	public static final int STATE_I = 1;
	public static final int STATE_R = 2;

	public static final Color[] COLORS = new Color[] {
		new Color(90,170,255),
		new Color(255,70,70),
		new Color(150,150,150)
	};

	public static void main(String[] args) {
		AnimEpidemic main = new AnimEpidemic();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Epidemic (SIR)");
		s.setWidth(1000);
		s.setHeight(700);
		s.setSleep(10);
		s.setBackground(new Color(15,15,20));
	}

	private ShapeList<Person> people;
	private ShapeControlBorderRebound control;

	protected void initialize1() {
		people = newShapeList();
		control = new ShapeControlBorderRebound(this);

		for(int i=0;i<COUNT;i++) {
			people.add(new Person());
		}
		for(int i=0;i<INITIAL_INFECTED;i++) {
			people.get(i).setState(STATE_I);
			people.get(i).moveTo(gameCenter());
		}

		newDrawingText(COLORS[STATE_S], p1(10,15), ()-> "Sains : "+people.sumInt(p->p.getState()==STATE_S?1:0));
		newDrawingText(COLORS[STATE_I], p1(10,30), ()-> "Infectes : "+people.sumInt(p->p.getState()==STATE_I?1:0));
		newDrawingText(COLORS[STATE_R], p1(10,45), ()-> "Gueris : "+people.sumInt(p->p.getState()==STATE_R?1:0));
	}

	protected void after() {
		people.control(control);

		for(Person p1 : people) for(Person p2 : people) {
			if(p1!=p2 && p1.getState()==STATE_S && p2.getState()==STATE_I && p1.intersects(p2)) {
				if(Math.random()<INFECTION_CHANCE) p1.setState(STATE_I);
			}
		}
	}

	/*
	 * PERSON
	 */

	private class Person extends ShapeRound {

		private int state = STATE_S;
		private int infectedSince;

		public Person() {
			super(UtilRandom.randomPoint2(AnimEpidemic.this), RADIUS);
			double speed = UtilRandom.randomDouble(SPEED_MIN, SPEED_MAX);
			getAnchor().setDerived(speed, Angle.random());
		}

		protected void drawShape() {
			fillRoundC(COLORS[state], RADIUS);
		}

		public void moveTo(Point0 p) {
			getAnchor().setXY(p);
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
			infectedSince = 0;
		}

		public void goNext() {
			super.goNext();
			if(state==STATE_I) {
				infectedSince++;
				if(infectedSince>=RECOVERY_TICKS) state = STATE_R;
			}
		}
	}
}
