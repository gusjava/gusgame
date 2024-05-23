package gus.game5.core.shape.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.features.g.GPoint0;
import gus.game5.core.line.HasSlope;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilString;

public abstract class GraphPoint0 extends GraphObject implements GPoint0, HasSlope {
	
	
	public GraphPoint0() {
		super();
	}
	
	public GraphPoint0(Color color) {
		super(color);
	}
	
	/*
	 * DISPLAY MODE
	 */

	public static final int DISPLAY_MODE_NO = 0;
	public static final int DISPLAY_MODE_CROSS = 1;
	public static final int DISPLAY_MODE_ARROW = 2;
	
	protected int displayMode = DISPLAY_MODE_CROSS;
	
	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}
	
	public int getDisplayMode() {
		return displayMode;
	}
	
	/*
	 * COLOR PROJECTION
	 */
	
	private Color colorProjX;
	private Color colorProjY;
	
	public void setColorProjXY(Color colorProj) {
		setColorProjX(colorProj);
		setColorProjY(colorProj);
	}
	
	public void setColorProjX(Color colorProjX) {
		this.colorProjX = colorProjX;
	}
	
	public void setColorProjY(Color colorProjY) {
		this.colorProjY = colorProjY;
	}
	
	public Color getColorProjX() {
		return colorProjX;
	}
	
	public Color getColorProjY() {
		return colorProjY;
	}
	
	/*
	 * DRAW OBJECT
	 */

	protected void drawObject(ShapeGraph graph) {
		Point0 p = getPoint();
		
		if(p==null) return;
		if(!graph.includes(p)) return;
		
		Color c = getColor();
		if(c==null) c = graph.getColor();

		
		if(colorProjX!=null) {
			graph.drawLine(colorProjX, p, p.pSetY(0));
		}
		if(colorProjY!=null) {
			graph.drawLine(colorProjY, p, p.pSetX(0));
		}
		
		if(displayMode==DISPLAY_MODE_CROSS) {
			graph.drawCross(c, p);
		}
		else if(displayMode==DISPLAY_MODE_ARROW) {
			graph.drawArrow(c, p);
		}
		
		List<String> desc = buildDescription();
		if(!desc.isEmpty()) {
			String s = UtilString.join(desc, " ");
			graph.drawString(c, p, s, 10, 5);
		}
	}
	
	private List<String> buildDescription() {
		List<String> infos = new ArrayList<>();
		if(isDisplayName()) infos.add(getName());
		if(isDisplayInfo()) infos.add("("+UtilDisplay.dec2s(getX())+","+UtilDisplay.dec2s(getY())+")");
		return infos;
	}
	
	/*
	 * X Y
	 */
	
	public double getX() {
		return getPoint().getX();
	}
	
	public double getY() {
		return getPoint().getY();
	}
	
	/*
	 * POINT
	 */
	
	public abstract Point0 getPoint();
	
	public Point0 g() {
		return getPoint();
	}
	
	/*
	 * SLOPE
	 */
	
	public Double getSlope() {
		return getPoint().getSlope();
	}
	
	public Double getSlopeInv() {
		return getPoint().getSlopeInv();
	}
}
