package gus.game5.core.shape.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.line.Line;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public abstract class GraphULine0 extends GraphObject implements Line {
	
	public GraphULine0() {
		super();
	}
	
	public GraphULine0(Color color) {
		super(color);
	}

	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		double xMin = graph.getXMin();
		double yMin = graph.getYMin();
		double xMax = graph.getXMax();
		double yMax = graph.getYMax();
		
		double val0 = getVal0();
		Double slope = getSlope();
		
		if(slope==null) {
			// droite verticale
			double x0 = val0;
			if(x0>=xMin && x0<=xMax) {
				Point0 p1 = p(x0, yMin);
				Point0 p2 = p(x0, yMax);
				graph.drawLine(c, p1, p2);
			}
		}
		else if(slope==0.0){
			// droite horizontale
			double y0 = val0;
			if(y0>=yMin && y0<=yMax) {
				Point0 p1 = p(xMin, y0);
				Point0 p2 = p(xMax, y0);
				graph.drawLine(c, p1, p2);
			}
		}
		else {
			// droite oblique
			double a1 = slope;
			double a0 = val0;
			
			double yForXMin = a1 * xMin + a0;
			double yForXMax = a1 * xMax + a0;
			
			double xForYMin = (yMin - a0) / a1;
			double xForYMax = (yMax - a0) / a1;
			
			List<Point0> pp = new ArrayList<>();
			
			if(yForXMin >= yMin && yForXMin < yMax) {
				pp.add(new Point1(xMin, yForXMin));
			}
			if(yForXMax >= yMin && yForXMax < yMax) {
				pp.add(new Point1(xMax, yForXMax));
			}
			
			if(xForYMin > xMin && xForYMin <= xMax) {
				pp.add(new Point1(xForYMin, yMin));
			}
			if(xForYMax > xMin && xForYMax <= xMax) {
				pp.add(new Point1(xForYMax, yMax));
			}
			
			if(pp.size()==2) {
				Point0 p1 = pp.get(0);
				Point0 p2 = pp.get(1);
				graph.drawLine(c, p1, p2);
			}
		}
	}
	
	/*
	 * TO STRING
	 */
	
	public String toString() {
		return getSlope()+"|"+getVal0();
	}
}
