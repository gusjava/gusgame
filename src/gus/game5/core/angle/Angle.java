package gus.game5.core.angle;

import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilAngle;
import gus.game5.core.util.UtilRandom;

public class Angle {
	
	public static final Angle ANGLE360 = new Angle(Math.PI*2);
	public static final Angle ANGLE345 = new Angle(Math.PI*23/12);
	public static final Angle ANGLE330 = new Angle(Math.PI*22/12);
	public static final Angle ANGLE315 = new Angle(Math.PI*21/12);
	public static final Angle ANGLE300 = new Angle(Math.PI*20/12);
	public static final Angle ANGLE285 = new Angle(Math.PI*19/12);
	public static final Angle ANGLE270 = new Angle(Math.PI*18/12);
	public static final Angle ANGLE255 = new Angle(Math.PI*17/12);
	public static final Angle ANGLE240 = new Angle(Math.PI*16/12);
	public static final Angle ANGLE225 = new Angle(Math.PI*15/12);
	public static final Angle ANGLE210 = new Angle(Math.PI*14/12);
	public static final Angle ANGLE195 = new Angle(Math.PI*13/12);
	public static final Angle ANGLE180 = new Angle(Math.PI);
	public static final Angle ANGLE165 = new Angle(Math.PI*11/12);
	public static final Angle ANGLE150 = new Angle(Math.PI*10/12);
	public static final Angle ANGLE135 = new Angle(Math.PI*9/12);
	public static final Angle ANGLE120 = new Angle(Math.PI*8/12);
	public static final Angle ANGLE105 = new Angle(Math.PI*7/12);
	public static final Angle ANGLE90 = new Angle(Math.PI*6/12);
	public static final Angle ANGLE75 = new Angle(Math.PI*5/12);
	public static final Angle ANGLE60 = new Angle(Math.PI*4/12);
	public static final Angle ANGLE45 = new Angle(Math.PI*3/12);
	public static final Angle ANGLE30 = new Angle(Math.PI*2/12);
	public static final Angle ANGLE15 = new Angle(Math.PI*1/12);
	
	
	public static double degToRad(double value) {
		return UtilAngle.degToRad(value);
	}
	
	public static double radToDeg(double value) {
		return UtilAngle.radToDeg(value);
	}
	
	public static double radMod(double value) {
		return UtilAngle.radMod(value);
	}
	
	public static Angle angleRad(double rad) {
		return new Angle(rad);
	}
	
	public static Angle angleDeg(double deg) {
		return new Angle(degToRad(deg));
	}
	
	public static Angle anglePart(int nb) {
		return nb!=0 ? ANGLE360.div(nb) : null;
	}
	
	/*
	 * RANDOM
	 */
	
	public static Angle random() {
		return randomRad(Math.PI*2);
	}
	
	public static Angle randomRad(double limit) {
		return new Angle(UtilRandom.randomDouble(limit));
	}
	
	public static Angle randomDeg(double limit) {
		return randomRad(degToRad(limit));
	}
	
	

	private final double value;
	
	public Angle() {
		value = 0;
	}
	
	public Angle(double value) {
		this.value = value;
	}
	
	public Angle(double x, double y) {
		this.value = UtilAngle.computeFromXY(x, y);
	}
	
	/*
	 * VALUE
	 */
	
	public double getValueRad() {
		return value;
	}
	
	public double getValueDeg() {
		return radToDeg(value);
	}
	
	/*
	 * VALUE MOD
	 */
	
	public double getValueRadMod() {
		return radMod(value);
	}
	
	public double getValueDegMod() {
		return radToDeg(radMod(value));
	}
	
	/*
	 * COS SIN TAN
	 */
	
	public double cos() {
		return Math.cos(value);
	}
	
	public double sin() {
		return Math.sin(value);
	}
	
	public double tan() {
		return Math.tan(value);
	}
	
	/*
	 * ADD
	 */
	
	public Angle add(double val) {
		return new Angle(value+val);
	}
	
	public Angle add(Angle a) {
		return new Angle(value+a.value);
	}
	
	public Angle addDeg(double val) {
		return new Angle(value+degToRad(val));
	}
	

	
	public Angle add15() {
		return add(ANGLE15);
	}
	
	public Angle add30() {
		return add(ANGLE30);
	}
	
	public Angle add45() {
		return add(ANGLE45);
	}
	
	public Angle add60() {
		return add(ANGLE60);
	}
	
	public Angle add75() {
		return add(ANGLE75);
	}
	
	public Angle add90() {
		return add(ANGLE90);
	}
	
	public Angle add105() {
		return add(ANGLE105);
	}
	
	public Angle add120() {
		return add(ANGLE120);
	}
	
	public Angle add135() {
		return add(ANGLE135);
	}
	
	public Angle add150() {
		return add(ANGLE150);
	}
	
	public Angle add165() {
		return add(ANGLE165);
	}
	
	public Angle add180() {
		return add(ANGLE180);
	}
	
	public Angle add195() {
		return add(ANGLE195);
	}
	
	public Angle add210() {
		return add(ANGLE210);
	}
	
	public Angle add225() {
		return add(ANGLE225);
	}
	
	public Angle add240() {
		return add(ANGLE240);
	}
	
	public Angle add255() {
		return add(ANGLE255);
	}
	
	public Angle add270() {
		return add(ANGLE270);
	}
	
	public Angle add285() {
		return add(ANGLE285);
	}
	
	public Angle add300() {
		return add(ANGLE300);
	}
	
	public Angle add315() {
		return add(ANGLE315);
	}
	
	public Angle add330() {
		return add(ANGLE330);
	}
	
	public Angle add345() {
		return add(ANGLE345);
	}
	

	
	/*
	 * SUB
	 */
	
	public Angle sub(double val) {
		return new Angle(value-val);
	}
	
	public Angle sub(Angle a) {
		return new Angle(value-a.value);
	}
	
	public Angle subDeg(double val) {
		return new Angle(value-degToRad(val));
	}
	
	
	public Angle sub15() {
		return sub(ANGLE15);
	}
	
	public Angle sub30() {
		return sub(ANGLE30);
	}
	
	public Angle sub45() {
		return sub(ANGLE45);
	}
	
	public Angle sub60() {
		return sub(ANGLE60);
	}
	
	public Angle sub75() {
		return sub(ANGLE75);
	}
	
	public Angle sub90() {
		return sub(ANGLE90);
	}
	
	public Angle sub105() {
		return sub(ANGLE105);
	}
	
	public Angle sub120() {
		return sub(ANGLE120);
	}
	
	public Angle sub135() {
		return sub(ANGLE135);
	}
	
	public Angle sub150() {
		return sub(ANGLE150);
	}
	
	public Angle sub165() {
		return sub(ANGLE165);
	}
	
	public Angle sub180() {
		return sub(ANGLE180);
	}
	
	public Angle sub195() {
		return sub(ANGLE195);
	}
	
	public Angle sub210() {
		return sub(ANGLE210);
	}
	
	public Angle sub225() {
		return sub(ANGLE225);
	}
	
	public Angle sub240() {
		return sub(ANGLE240);
	}
	
	public Angle sub255() {
		return sub(ANGLE255);
	}
	
	public Angle sub270() {
		return sub(ANGLE270);
	}
	
	public Angle sub285() {
		return sub(ANGLE285);
	}
	
	public Angle sub300() {
		return sub(ANGLE300);
	}
	
	public Angle sub315() {
		return sub(ANGLE315);
	}
	
	public Angle sub330() {
		return sub(ANGLE330);
	}
	
	public Angle sub345() {
		return sub(ANGLE345);
	}
	
	/*
	 * MULT
	 */
	
	public Angle mult(double val) {
		return new Angle(value*val);
	}
	
	/*
	 * DIV
	 */
	
	public Angle div(double val) {
		if(val==0) return null;
		return new Angle(value/val);
	}
	
	/*
	 * INV
	 */
	
	public Angle inv() {
		return new Angle(value*-1);
	}
	
	/*
	 * SUPPL
	 */
	
	public Angle suppl() {
		return new Angle(Math.PI-value);
	}
	
	/*
	 * COMPL
	 */
	
	public Angle compl() {
		return new Angle(Math.PI/2-value);
	}
	
	/*
	 * ROTATION
	 */
	
	public double rotationX(double x, double y) {
		return x*cos()-y*sin();
	}
	
	public double rotationY(double x, double y) {
		return x*sin()+y*cos();
	}
	
	/*
	 * POINT
	 */
	
	public Point1 pointAt(double dist) {
		return new Point1(dist, this);
	}
	
}
