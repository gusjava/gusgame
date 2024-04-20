package gus.game5.core.util;

public class UtilAngle {
	
	public static final double DEG_TO_RAD = Math.PI/180;
	public static final double RAD_TO_DEG = 180/Math.PI;

	
	public static double computeFromXY(double x, double y) {
		if(x==0 && y==0) throw new RuntimeException("Failed to build angle at (0,0)");
		return Math.atan2(y, x);
	}
	
	public static double degToRad(double value) {
		return value*DEG_TO_RAD;
	}
	
	public static double radToDeg(double value) {
		return value*RAD_TO_DEG;
	}
}
