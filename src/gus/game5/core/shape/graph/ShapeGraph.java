package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.dyn.DynList;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.Shape0;

public class ShapeGraph extends Shape0 {
	
	public static final int AXIS_DISPLAY_LEVEL_NO = 0;
	public static final int AXIS_DISPLAY_LEVEL_LINE = 1;
	public static final int AXIS_DISPLAY_LEVEL_NOTCH = 2;
	public static final int AXIS_DISPLAY_LEVEL_FIGURE = 3;
	
	// ANCHOR P0

	public ShapeGraph(Point0 anchor, double width, double height, AnchorType type) {
		super(anchor, width, height, type);
	}

	public ShapeGraph(Point0 anchor, double width, double height) {
		super(anchor, width, height);
	}
	
	// ANCHOR P2

	public ShapeGraph(Point2 anchor, double width, double height, AnchorType type) {
		super(anchor, width, height, type);
	}

	public ShapeGraph(Point2 anchor, double width, double height) {
		super(anchor, width, height);
	}
	
	
	private double xMin = -10;
	private double xMax = 10;
	private double xStep = 50;
	
	private double yMin = -10;
	private double yMax = 10;
	private double yStep = 50;
	
	private double notchLength = 3;
	
	private Color axisColor = Color.LIGHT_GRAY;
	private int axisDisplayLevel = AXIS_DISPLAY_LEVEL_FIGURE;
	
	private DynList<GraphObject> objects = new DynList<>(); 
	
	/*
	 * X MIN
	 */
	
	public double getXMin() {
		return xMin;
	}
	
	public void setXMin(double xMin) {
		this.xMin = xMin;
	}
	
	/*
	 * X MAX
	 */
	
	public double getXMax() {
		return xMax;
	}
	
	public void setXMax(double xMax) {
		this.xMax = xMax;
	}
	
	/*
	 * X STEP
	 */
	
	public double getXStep() {
		return xStep;
	}
	
	public void setXStep(double xStep) {
		this.xStep = xStep;
	}
	
	/*
	 * Y MIN
	 */
	
	public double getYMin() {
		return yMin;
	}
	
	public void setYMin(double yMin) {
		this.yMin = yMin;
	}
	
	/*
	 * Y MAX
	 */
	
	public double getYMax() {
		return yMax;
	}
	
	public void setYMax(double yMax) {
		this.yMax = yMax;
	}
	
	/*
	 * Y STEP
	 */
	
	public double getYStep() {
		return yStep;
	}
	
	public void setYStep(double yStep) {
		this.yStep = yStep;
	}
	
	/*
	 * NOTCH LENGTH
	 */
	
	public double getNotchLength() {
		return notchLength;
	}
	
	public void setNotchLength(double notchLength) {
		this.notchLength = notchLength;
	}
	
	
	/*
	 * DRAW SHAPE
	 */

	protected void drawShape() {
		drawAxisX();
		drawAxisY();
		for(GraphObject object : objects) {
			object.drawObject(this);
		}
	}
	
	/*
	 * ADD
	 */
	
	public void add(GraphObject object) {
		objects.add(object);
	}
	
	public void add(String name, GraphObject object) {
		objects.add(object);
		object.setName(name);
	}
	
	/*
	 * AXIS X
	 */
	
	protected void drawAxisX() {
		if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NO) {
			super.drawLine(axisColor, p(xMin*xStep,0), p(xMax*xStep,0));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_LINE) {
				drawNotchX1();
				drawNotchX2();
			}
		}
	}
	
	protected void drawNotchX1() {
		int k = 0;
		while(k<xMax) {
			k++;
			double kx = k*xStep;
			super.drawLine(axisColor, p(kx,-notchLength), p(kx,notchLength));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NOTCH)
			super.drawStringC(axisColor, p(kx, 15), ""+k);
		}
	}
	
	protected void drawNotchX2() {
		int k = 0;
		while(k>xMin) {
			k--;
			double kx = k*xStep;
			super.drawLine(axisColor, p(kx,-notchLength), p(kx,notchLength));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NOTCH)
			super.drawStringC(axisColor, p(kx, 15), ""+k);
		}
	}
	
	/*
	 * AXIS Y
	 */
	
	protected void drawAxisY() {
		if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NO) {
			super.drawLine(axisColor, p(0,yMin*yStep), p(0,yMax*yStep));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_LINE) {
				drawNotchY1();
				drawNotchY2();
			}
		}
	}
	
	protected void drawNotchY1() {
		int k = 0;
		while(k<yMax) {
			k++;
			double ky = -k*yStep;
			super.drawLine(axisColor, p(-notchLength, ky), p(notchLength, ky));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NOTCH)
			super.drawStringC(axisColor, p(-15, ky), ""+k);
		}
	}
	
	protected void drawNotchY2() {
		int k = 0;
		while(k>yMin) {
			k--;
			double ky = -k*yStep;
			super.drawLine(axisColor, p(-notchLength, ky), p(notchLength, ky));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NOTCH)
			super.drawStringC(axisColor, p(-15, ky), ""+k);
		}
	}
	
	/*
	 * STEP
	 */
	
	public void setStep(double step) {
		xStep = step;
		yStep = step;
	}
	
	public Point0 pMult(Point0 p) {
		return p.pMult(xStep, -yStep);
	}
	
	public Point0 pMult(double x, double y) {
		return new Point1(x * xStep, -y * yStep);
	}
	
	public boolean includes(Point0 p) {
		double x = p.getX();
		double y = p.getY();
		return x>=xMin && x<=xMax && y>=yMin && y<=yMax;
	}
	
	/*
	 * GO NEXT / GO BACK
	 */

	public void goNext() {
		super.goNext();
		objects.goNext();
	}
	
	public void goBack() {
		super.goBack();
		objects.goBack();
	}
	
	/*
	 * DRAW OVAL
	 */
	
	public void drawOvalC(Color color, Point0 pc, double width, double height) {
		super.drawOvalC(color, pMult(pc), width*xStep, height*yStep);
	}
	
	/*
	 * DRAW LINE
	 */
	
	public void drawLine(Color color, Point0 p1, Point0 p2) {
		super.drawLine(color, pMult(p1), pMult(p2));
	}
	
	/*
	 * DRAW ARROW
	 */
	
	public void drawArrow(Color color, Point0 p2) {
		drawArrow(color, p(0,0), pMult(p2), 5);
	}
	
	public void drawArrow(Color color, Point0 p2, double r_) {
		super.drawArrow(color, p(0,0), pMult(p2), r_);
	}
	
	/*
	 * DRAW CROSS
	 */
	
	public void drawCross(Color color, Point0 p) {
		drawCross(color, p, 5);
	}
	
	public void drawCross(Color color, Point0 p, double r_) {
		Point0 p_ = pMult(p);
		super.drawLine(color, p_.pAdd(r_, 0), p_.pAdd(-r_, 0));
		super.drawLine(color, p_.pAdd(0, r_), p_.pAdd(0, -r_));
	}
	
	/*
	 * DRAW CROSS (ANGLE)
	 */
	
	public void drawCross(Color color, Point0 p, Angle a) {
		drawCross(color, p, a, 5);
	}
	
	public void drawCross(Color color, Point0 p, Angle a, double r_) {
		Point0 p_ = pMult(p);
		Angle a_ = a.sym90();
		Angle a90_ = a_.add90();
		super.drawLine(color, p_.pAdd(r_, a_), p_.pAdd(-r_, a_));
		super.drawLine(color, p_.pAdd(r_, a90_), p_.pAdd(-r_, a90_));
	}
	
	/*
	 * DRAW NOTCH X
	 */
	
	public void drawNotchX(Color color, Point0 p) {
		drawNotchX(color, p, 5);
	}
	
	public void drawNotchX(Color color, Point0 p, double r_) {
		Point0 p_ = pMult(p);
		super.drawLine(color, p_.pAdd(r_, 0), p_.pAdd(-r_, 0));
	}
	
	/*
	 * DRAW NOTCH Y
	 */
	
	public void drawNotchY(Color color, Point0 p) {
		drawNotchY(color, p, 5);
	}
	
	public void drawNotchY(Color color, Point0 p, double r_) {
		Point0 p_ = pMult(p);
		super.drawLine(color, p_.pAdd(0, r_), p_.pAdd(0, -r_));
	}
	
	/*
	 * DRAW NOTCH (ANGLE)
	 */
	
	public void drawNotch(Color color, Point0 p, Angle a) {
		drawNotch(color, p, a, 5);
	}
	
	public void drawNotch(Color color, Point0 p, Angle a, double r_) {
		Point0 p_ = pMult(p);
		Angle a_ = a.sym90();
		super.drawLine(color, p_.pAdd(r_, a_), p_.pAdd(-r_, a_));
	}
	
	
	/*
	 * DRAW STRING
	 */
	
	public void drawString(Color color, Point0 p, String text) {
		super.drawString(color, pMult(p), text);
	}
	
	public void drawString(Color color, Point0 p, String text, double dx_, double dy_) {
		super.drawString(color, pMult(p).pAdd(dx_,dy_), text);
	}
}
