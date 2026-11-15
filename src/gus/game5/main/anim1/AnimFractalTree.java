package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.util.UtilColor;

public class AnimFractalTree extends Game2 {

	public static void main(String[] args) {
		AnimFractalTree main = new AnimFractalTree();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Fractal tree animation");
		s.setSize(900, 800);
		s.setSleep(10);
		s.setBackground(new Color(10,15,25));
	}

	public static final int MAX_DEPTH = 8;
	public static final double TRUNK_LENGTH = 130;
	public static final Angle BRANCH_ANGLE = Angle.angleDeg(24);
	public static final double LENGTH_FACTOR_LEFT = 0.72;
	public static final double LENGTH_FACTOR_RIGHT = 0.78;
	public static final double SWAY_AMPLITUDE = 0.12;
	public static final double SWAY_SPEED = 0.015;
	public static final double SWAY_PHASE_STEP = 0.5;
	public static final double MAX_THICKNESS = 10;
	public static final double LEAF_RADIUS = 5;
	public static final Color TRUNK_COLOR = new Color(101,67,33);
	public static final Color LEAF_COLOR = new Color(50,160,60);

	protected void initialize1() {
		addDraw(new Tree(p1(gameWidth()/2, gameHeight()-40)));
	}

	/*
	 * TREE
	 */

	private class Tree extends Drawing1 {
		public Tree(Point0 base) {
			super(base);
		}

		protected void draw() {
			drawBranch(p(0,0), Angle.angleDeg(270), TRUNK_LENGTH, MAX_DEPTH);
		}

		private void drawBranch(Point0 start, Angle angle, double length, int depth) {
			if(depth==0) {
				fillRoundC(LEAF_COLOR, start, LEAF_RADIUS);
				return;
			}

			Point0 end = start.pAdd(length, angle);
			double thickness = 1+(MAX_THICKNESS-1)*depth/(double) MAX_DEPTH;
			Color color = UtilColor.blend(LEAF_COLOR, TRUNK_COLOR, depth/(double) MAX_DEPTH);
			drawThickLine(color, start, end, thickness);

			double sway = SWAY_AMPLITUDE*Math.sin(getCount()*SWAY_SPEED+depth*SWAY_PHASE_STEP);
			Angle swayAngle = Angle.angleRad(sway);

			drawBranch(end, angle.sub(BRANCH_ANGLE).add(swayAngle), length*LENGTH_FACTOR_LEFT, depth-1);
			drawBranch(end, angle.add(BRANCH_ANGLE).add(swayAngle), length*LENGTH_FACTOR_RIGHT, depth-1);
		}

	}
}
