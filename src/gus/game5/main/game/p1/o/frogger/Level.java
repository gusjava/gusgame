package gus.game5.main.game.p1.o.frogger;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Level {

	public static final double SPEED_STEP = 0.12;
	public static final double SPEED_MAX = 2.2;
	public static final double DIVE_MAX = 1.6;

	public static final Color COLOR_CAR_A = new Color(240,230,120);
	public static final Color COLOR_CAR_B = new Color(120,210,240);
	public static final Color COLOR_CAR_C = new Color(230,120,200);
	public static final Color COLOR_RACER = new Color(240,80,70);
	public static final Color COLOR_TRUCK = new Color(235,235,240);
	public static final Color COLOR_LOG = new Color(140,86,46);
	public static final Color COLOR_TURTLE = new Color(70,180,120);

	public static double factor(int level) {
		return Math.min(1 + SPEED_STEP*(level-1), SPEED_MAX);
	}

	public static List<Lane> build(int level) {
		double f = factor(level);
		double d = Math.min(f, DIVE_MAX);
		List<Lane> lanes = new ArrayList<Lane>();

		lanes.add(log(1, 1.3*f, 4, 3, 18));
		lanes.add(turtles(2, -1.9*f, 2, 4, 17, 5.5/d));
		lanes.add(log(3, 3.0*f, 6, 2, 21));
		lanes.add(log(4, 1.8*f, 3, 3, 17));
		lanes.add(turtles(5, -1.6*f, 3, 3, 17, 6.5/d));

		lanes.add(vehicle(LaneType.TRUCK, 7, 1.4*f, 2, 2, 17, COLOR_TRUCK));
		lanes.add(vehicle(LaneType.CAR, 8, -4.5*f, 1, 2, 19, COLOR_RACER));
		lanes.add(vehicle(LaneType.CAR, 9, 2.2*f, 1, 3, 15, COLOR_CAR_C));
		lanes.add(vehicle(LaneType.CAR, 10, -2.6*f, 1, 3, 15, COLOR_CAR_B));
		lanes.add(vehicle(LaneType.CAR, 11, 1.8*f, 1, 3, 15, COLOR_CAR_A));

		return lanes;
	}

	private static Lane vehicle(LaneType type, int row, double speed, double length, int count, double period, Color color) {
		Lane lane = new Lane(type, row, speed, length, count, period, color);
		lane.update(row*0.7);
		return lane;
	}

	private static Lane log(int row, double speed, double length, int count, double period) {
		Lane lane = new Lane(LaneType.LOG, row, speed, length, count, period, COLOR_LOG);
		lane.update(row*0.7);
		return lane;
	}

	private static Lane turtles(int row, double speed, double length, int count, double period, double divePeriod) {
		Lane lane = new Lane(LaneType.TURTLE, row, speed, length, count, period, COLOR_TURTLE);
		lane.setDive(divePeriod, 0);
		lane.update(row*0.7);
		return lane;
	}
}
