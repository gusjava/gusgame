package gus.game5.main.game.p1.o.frogger;

import java.awt.Color;

public class Lane {

	public static final double BLINK_FROM = 0.62;
	public static final double SUNK_FROM = 0.80;

	private LaneType type;
	private int row;
	private double speed;
	private double length;
	private int count;
	private double period;
	private Color color;

	private double offset;
	private double divePeriod;
	private double diveTime;

	public Lane(LaneType type, int row, double speed, double length, int count, double period, Color color) {
		this.type = type;
		this.row = row;
		this.speed = speed;
		this.length = length;
		this.count = count;
		this.period = period;
		this.color = color;
		this.offset = 0;
		this.divePeriod = 0;
		this.diveTime = 0;
	}

	public static double wrap(double value, double modulo) {
		double r = value%modulo;
		return r<0 ? r+modulo : r;
	}

	/*
	 * STATE
	 */

	public LaneType getType() {return type;}
	public int getRow() {return row;}
	public double getSpeed() {return speed;}
	public double getLength() {return length;}
	public int getCount() {return count;}
	public double getPeriod() {return period;}
	public Color getColor() {return color;}

	public boolean isCarrier() {
		return type==LaneType.LOG || type==LaneType.TURTLE;
	}

	public void setDive(double divePeriod, double diveTime) {
		this.divePeriod = divePeriod;
		this.diveTime = diveTime;
	}

	public void update(double dt) {
		offset = wrap(offset + speed*dt, period);
		if(divePeriod>0) diveTime = wrap(diveTime + dt, divePeriod);
	}

	public double position(int index) {
		return wrap(offset + index*period/count, period) - length;
	}

	/*
	 * TURTLES
	 */

	public double divePhase(int index) {
		if(divePeriod<=0) return 0;
		return wrap(diveTime + index*divePeriod*0.37 + row*divePeriod*0.13, divePeriod)/divePeriod;
	}

	public boolean isSunk(int index) {
		return divePeriod>0 && divePhase(index)>=SUNK_FROM;
	}

	public boolean isBlinking(int index) {
		if(divePeriod<=0) return false;
		double phase = divePhase(index);
		return phase>=BLINK_FROM && phase<SUNK_FROM;
	}

	/*
	 * HIT TESTS
	 */

	public boolean overlaps(double left, double right) {
		for(int i=0;i<count;i++) {
			double x = position(i);
			if(right>x && left<x+length) return true;
		}
		return false;
	}

	public int carrierAt(double x) {
		for(int i=0;i<count;i++) {
			if(isSunk(i)) continue;
			double start = position(i);
			if(x>=start && x<=start+length) return i;
		}
		return -1;
	}
}
