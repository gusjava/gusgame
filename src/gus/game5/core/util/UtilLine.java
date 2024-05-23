package gus.game5.core.util;

import gus.game5.core.line.Line;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class UtilLine {

	public static Point0 pointAt(Line line1, Line line2) {
		Double slope1 = line1.getSlope();
		Double slope2 = line2.getSlope();
		
		double val1 = line1.getVal0();
		double val2 = line2.getVal0();
		
		if(slope1==null && slope2==null) return null;
		
		if(slope1==null) {
			double x = val1;
			double y = val2 + slope2 * x;
			return new Point1(x,y);
		}
		if(slope2==null) {
			double x = val2;
			double y = val1 + slope1 * x;
			return new Point1(x,y);
		}
		if(slope1.equals(slope2)) return null;

		double x = (val2 - val1)/(slope1 - slope2);
		double y = val1 + slope1 * x;
		return new Point1(x,y);
	}
}
