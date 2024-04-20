package gus.game5.core.angle;

import java.util.HashMap;
import java.util.Map;

import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilAngle;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilRandom;

public class Angle {
	
	public static boolean CACHED = false;
	
	private static Map<String,Angle> cache = new HashMap<>();
	
	public static Angle angleRad(double val) {
		val = val%PI2;
		if(CACHED) {
			String key = UtilDisplay.dec2(val);
			if(!cache.containsKey(key)) cache.put(key, new Angle(val));
			return cache.get(key);
		}
		return new Angle(val);
	}
	
	public static Angle angleXY(double x, double y) {
		return angleRad(UtilAngle.computeFromXY(x, y));
	}
	
	

	public static final double PI = Math.PI;
	public static final double PI2 = Math.PI*2;
	
	public static final Angle ANGLE345 = angleRad(PI*23/12);
	public static final Angle ANGLE330 = angleRad(PI*22/12);
	public static final Angle ANGLE315 = angleRad(PI*21/12);
	public static final Angle ANGLE300 = angleRad(PI*20/12);
	public static final Angle ANGLE285 = angleRad(PI*19/12);
	public static final Angle ANGLE270 = angleRad(PI*18/12);
	public static final Angle ANGLE255 = angleRad(PI*17/12);
	public static final Angle ANGLE240 = angleRad(PI*16/12);
	public static final Angle ANGLE225 = angleRad(PI*15/12);
	public static final Angle ANGLE210 = angleRad(PI*14/12);
	public static final Angle ANGLE195 = angleRad(PI*13/12);
	public static final Angle ANGLE180 = angleRad(PI);
	public static final Angle ANGLE165 = angleRad(PI*11/12);
	public static final Angle ANGLE150 = angleRad(PI*10/12);
	public static final Angle ANGLE135 = angleRad(PI*9/12);
	public static final Angle ANGLE120 = angleRad(PI*8/12);
	public static final Angle ANGLE105 = angleRad(PI*7/12);
	public static final Angle ANGLE90 = angleRad(PI*6/12);
	public static final Angle ANGLE75 = angleRad(PI*5/12);
	public static final Angle ANGLE60 = angleRad(PI*4/12);
	public static final Angle ANGLE45 = angleRad(PI*3/12);
	public static final Angle ANGLE30 = angleRad(PI*2/12);
	public static final Angle ANGLE15 = angleRad(PI*1/12);
	public static final Angle ANGLE0 = angleRad(0);
	
	
	public static double degToRad(double value) {
		return UtilAngle.degToRad(value);
	}
	
	public static double radToDeg(double value) {
		return UtilAngle.radToDeg(value);
	}
	
	public static Angle angleDeg(double deg) {
		return angleRad(degToRad(deg));
	}
	
	public static Angle anglePart(int nb) {
		return nb!=0 ? angleRad(PI2/nb) : null;
	}
	
	/*
	 * RANDOM
	 */
	
	public static Angle random() {
		return randomRad(PI2);
	}
	
	public static Angle randomRad(double limit) {
		return angleRad(UtilRandom.randomDouble(limit));
	}
	
	public static Angle randomDeg(double limit) {
		return randomRad(degToRad(limit));
	}
	
	

	private final double value;
	
	private Angle(double value) {
		this.value = value;
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
	 * COS
	 */
	
	private boolean cosCached = false;
	private double cos = 0;
	
	public double cos() {
		if(!cosCached) {
			cos = Math.cos(value);
			cosCached = true;
		}
		return cos;
	}
	
	/*
	 * SIN
	 */

	private boolean sinCached = false;
	private double sin = 0;
	
	public double sin() {
		if(!sinCached) {
			sin = Math.sin(value);
			sinCached = true;
		}
		return sin;
	}
	
	/*
	 * TAN
	 */

	private boolean tanCached = false;
	private double tan = 0;
	
	public double tan() {
		if(!tanCached) {
			tan = Math.tan(value);
			tanCached = true;
		}
		return tan;
	}
	
	/*
	 * ADD
	 */
	
	public Angle add(double val) {
		return angleRad(value+val);
	}
	
	public Angle add(Angle a) {
		return add(a.value);
	}
	
	public Angle addDeg(double val) {
		return add(degToRad(val));
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
		return angleRad(value-val);
	}
	
	public Angle sub(Angle a) {
		return sub(a.value);
	}
	
	public Angle subDeg(double val) {
		return sub(degToRad(val));
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
	 * SYM
	 */
	
	public Angle sym(double val) {
		return angleRad(2*val-value);
	}
	
	public Angle sym(Angle a) {
		return sym(a.value);
	}
	
	public Angle symDeg(double val) {
		return sym(degToRad(val));
	}

	
	public Angle sym0() {
		return sym(ANGLE0);
	}
	
	public Angle sym15() {
		return sym(ANGLE15);
	}
	
	public Angle sym30() {
		return sym(ANGLE30);
	}
	
	public Angle sym45() {
		return sym(ANGLE45);
	}
	
	public Angle sym60() {
		return sym(ANGLE60);
	}
	
	public Angle sym75() {
		return sym(ANGLE75);
	}
	
	public Angle sym90() {
		return sym(ANGLE90);
	}
	
	public Angle sym105() {
		return sym(ANGLE105);
	}
	
	public Angle sym120() {
		return sym(ANGLE120);
	}
	
	public Angle sym135() {
		return sym(ANGLE135);
	}
	
	public Angle sym150() {
		return sym(ANGLE150);
	}
	
	public Angle sym165() {
		return sym(ANGLE165);
	}
	
	public Angle sym180() {
		return sym(ANGLE180);
	}
	
	public Angle sym195() {
		return sym(ANGLE195);
	}
	
	public Angle sym210() {
		return sym(ANGLE210);
	}
	
	public Angle sym225() {
		return sym(ANGLE225);
	}
	
	public Angle sym240() {
		return sym(ANGLE240);
	}
	
	public Angle sym255() {
		return sym(ANGLE255);
	}
	
	public Angle sym270() {
		return sym(ANGLE270);
	}
	
	public Angle sym285() {
		return sym(ANGLE285);
	}
	
	public Angle sym300() {
		return sym(ANGLE300);
	}
	
	public Angle sym315() {
		return sym(ANGLE315);
	}
	
	public Angle sym330() {
		return sym(ANGLE330);
	}
	
	public Angle sym345() {
		return sym(ANGLE345);
	}
	
	/*
	 * APPROACH
	 */
	
	public Angle approach(Angle target, double d) {
		//TODO debug
		if(target.value-value<PI) return add(d);
		return sub(d);
	}
	
	public Angle approach(Angle target, Angle a) {
		return approach(target, a.value);
	}
	
	public Angle approachDeg(Angle target, double val) {
		return approach(target, degToRad(val));
	}

	
	/*
	 * MULT
	 */
	
	public Angle mult(double val) {
		return angleRad(value*val);
	}
	
	/*
	 * DIV
	 */
	
	public Angle div(double val) {
		if(val==0) return null;
		return angleRad(value/val);
	}
	
	/*
	 * INV
	 */
	
	public Angle inv() {
		return angleRad(value*-1);
	}
	
	/*
	 * SUPPL
	 */
	
	public Angle suppl() {
		return angleRad(PI-value);
	}
	
	/*
	 * COMPL
	 */
	
	public Angle compl() {
		return angleRad(PI/2-value);
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
	
	public Point1 point() {
		return new Point1(this);
	}
	
}
