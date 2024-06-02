package gus.game5.main.game.p1.o.missile;

import java.awt.Color;

import gus.game5.core.point.point1.Point1;

public class Enemy extends Element {
	public final static int SIZE = 5;

	private int gain;
	private int damage;
	private int duplicationRate;
	
	public Enemy(Point1 start, Point1 target, Color color, double speed, double blastSize, int gain, int damage, int duplicationRate) {
		super(start, target, color, SIZE, speed, blastSize);
		this.gain = gain;
		this.damage = damage;
		this.duplicationRate = duplicationRate;
	}

	public int getGain() {
		return gain;
	}
	public int getDamage() {
		return damage;
	}
	public int getDuplicationRate() {
		return duplicationRate;
	}
}
