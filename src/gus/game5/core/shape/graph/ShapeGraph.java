package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.dyn.DynList;
import gus.game5.core.point.point0.Point0;
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
	
	private Color axisColor = Color.LIGHT_GRAY;
	private int axisDisplayLevel = AXIS_DISPLAY_LEVEL_FIGURE;
	
	private DynList<GraphObject> objects = new DynList<>(); 
	
	
	public double getXMin() {
		return xMin;
	}
	
	public double getXMax() {
		return xMax;
	}
	
	public double getXStep() {
		return xStep;
	}
	
	public double getYMin() {
		return yMin;
	}
	
	public double getYMax() {
		return yMax;
	}
	
	public double getYStep() {
		return yStep;
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
	 * ADD OBJECT
	 */
	
	public void addObject(GraphObject object) {
		objects.add(object);
	}
	
	/*
	 * AXIS X
	 */
	
	protected void drawAxisX() {
		if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NO) {
			drawLine(axisColor, p(xMin*xStep,0), p(xMax*xStep,0));
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
			drawLine(axisColor, p(kx,-5), p(kx,5));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NOTCH)
			drawStringC(axisColor, p(kx, 15), ""+k);
		}
	}
	
	protected void drawNotchX2() {
		int k = 0;
		while(k>xMin) {
			k--;
			double kx = k*xStep;
			drawLine(axisColor, p(kx,-5), p(kx,5));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NOTCH)
			drawStringC(axisColor, p(kx, 15), ""+k);
		}
	}
	
	/*
	 * AXIS Y
	 */
	
	protected void drawAxisY() {
		if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NO) {
			drawLine(axisColor, p(0,yMin*yStep), p(0,yMax*yStep));
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
			drawLine(axisColor, p(-5, ky), p(5, ky));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NOTCH)
			drawStringC(axisColor, p(-15, ky), ""+k);
		}
	}
	
	protected void drawNotchY2() {
		int k = 0;
		while(k>yMin) {
			k--;
			double ky = -k*yStep;
			drawLine(axisColor, p(-5, ky), p(5, ky));
			if(axisDisplayLevel>AXIS_DISPLAY_LEVEL_NOTCH)
			drawStringC(axisColor, p(-15, ky), ""+k);
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
}
