package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.line.HasSlope;
import gus.game5.core.point.point0.Point0;

public abstract class GraphSegment0 extends GraphObject implements HasSlope {
	
	public GraphSegment0(Color color) {
		super(color);
	}
	
	public GraphSegment0() {
		super();
	}
	
	/*
	 * DISPLAY CENTER
	 */
	
	protected boolean displayCenter = false;
	
	public void setDisplayCenter(boolean displayCenter) {
		this.displayCenter = displayCenter;
	}
	
	public boolean isDisplayCenter() {
		return displayCenter;
	}
	
	/*
	 * DISPLAY ENDS
	 */
	
	protected boolean displayEnds = false;
	
	public void setDisplayEnds(boolean displayEnds) {
		this.displayEnds = displayEnds;
	}
	
	public boolean isDisplayEnds() {
		return displayEnds;
	}
	
	/*
	 * COLOR PROJECTION
	 */
	
	private Color colorProjP1x;
	private Color colorProjP2x;
	private Color colorProjP12x;
	private Color colorProjP1y;
	private Color colorProjP2y;
	private Color colorProjP12y;
	
	public Color getColorProjP1x() {
		return colorProjP1x;
	}
	public Color getColorProjP2x() {
		return colorProjP2x;
	}
	public Color getColorProjP12x() {
		return colorProjP12x;
	}
	public Color getColorProjP1y() {
		return colorProjP1y;
	}
	public Color getColorProjP2y() {
		return colorProjP2y;
	}
	public Color getColorProjP12y() {
		return colorProjP12y;
	}

	public void setColorProjP1x(Color colorProjP1x) {
		this.colorProjP1x = colorProjP1x;
	}
	public void setColorProjP2x(Color colorProjP2x) {
		this.colorProjP2x = colorProjP2x;
	}
	public void setColorProjP12x(Color colorProjP12x) {
		this.colorProjP12x = colorProjP12x;
	}
	public void setColorProjP1y(Color colorProjP1y) {
		this.colorProjP1y = colorProjP1y;
	}
	public void setColorProjP2y(Color colorProjP2y) {
		this.colorProjP2y = colorProjP2y;
	}
	public void setColorProjP12y(Color colorProjP12y) {
		this.colorProjP12y = colorProjP12y;
	}
	
	public void setColorProj(Color colorProj) {
		setColorProjX(colorProj);
		setColorProjY(colorProj);
	}
	
	public void setColorProjX(Color colorProj) {
		setColorProjP1x(colorProj);
		setColorProjP2x(colorProj);
		setColorProjP12x(colorProj);
	}
	
	public void setColorProjY(Color colorProj) {
		setColorProjP1y(colorProj);
		setColorProjP2y(colorProj);
		setColorProjP12y(colorProj);
	}
	
	public void setColorProjP1(Color colorProj) {
		setColorProjP1x(colorProj);
		setColorProjP1y(colorProj);
	}
	
	public void setColorProjP2(Color colorProj) {
		setColorProjP2x(colorProj);
		setColorProjP2y(colorProj);
	}
	
	public void setColorProjP12(Color colorProj) {
		setColorProjP12x(colorProj);
		setColorProjP12y(colorProj);
	}

	/*
	 * DRAW OBJECT
	 */
	
	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		Point0 p1 = getPoint1();
		Point0 p2 = getPoint2();
		Point0 p12 = getPoint12();
		
		double xMin = graph.getXMin();
		double yMin = graph.getYMin();
		double xMax = graph.getXMax();
		double yMax = graph.getYMax();
		
		boolean p1Inside = p1.xBetween(xMin, xMax) && p1.yBetween(yMin, yMax);
		boolean p2Inside = p2.xBetween(xMin, xMax) && p2.yBetween(yMin, yMax);
		
		if(p1Inside && p2Inside) {
			if(colorProjP1x!=null) {
				graph.drawLine(colorProjP1x, p1, p1.pSetY(0));
			}
			if(colorProjP1y!=null) {
				graph.drawLine(colorProjP1y, p1, p1.pSetX(0));
			}
			if(colorProjP2x!=null) {
				graph.drawLine(colorProjP2x, p2, p2.pSetY(0));
			}
			if(colorProjP2y!=null) {
				graph.drawLine(colorProjP2y, p2, p2.pSetX(0));
			}
			if(colorProjP12x!=null) {
				graph.drawLine(colorProjP12x, p12, p12.pSetY(0));
			}
			if(colorProjP12y!=null) {
				graph.drawLine(colorProjP12y, p12, p12.pSetX(0));
			}
			
			if(displayCenter) {
				graph.drawNotch(c, p12, getAngle90());
			}
			
			if(displayEnds) {
				graph.drawNotch(c, getPoint1(), getAngle90());
				graph.drawNotch(c, getPoint2(), getAngle90());
			}
			
			graph.drawLine(c, p1, p2);
		}
	}
	
	/*
	 * POINTS
	 */
	
	public abstract Point0 getPoint1();
	public abstract Point0 getPoint2();
	
	public Point0 getPoint12() {
		return avg(getPoint1(), getPoint2());
	}
	
	public Point0 getVector() {
		return getPoint2().pSub(getPoint1());
	}
	
	public Point0 pointAtRatio(double ratio) {
		return getPoint1().pAdd(getVector().pMult(ratio));
	}
	
	/*
	 * ANGLE
	 */
	
	public Angle getAngle() {
		return getVector().angle();
	}
	
	public Angle getAngle90() {
		return getVector().angle().add90();
	}
	
	/*
	 * LENGTH
	 */
	
	public double getLength() {
		return getVector().dist();
	}
	
	/*
	 * SLOPE
	 */
	
	public Double getSlope() {
		return getVector().getSlope();
	}
	
	public Double getSlopeInv() {
		return getVector().getSlopeInv();
	}
}
