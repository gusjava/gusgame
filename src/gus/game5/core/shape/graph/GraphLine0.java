package gus.game5.core.shape.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public abstract class GraphLine0 extends GraphObject {
	
	public abstract double getA();
	public abstract double getB();
	
	public GraphLine0() {
		super();
	}
	
	public GraphLine0(Color color, String name) {
		super(color, name);
	}
	
	public GraphLine0(String name) {
		super(name);
	}

	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		double a = getA();
		double b = getB();
		
		double xMin = graph.getXMin();
		double yMin = graph.getYMin();
		double xMax = graph.getXMax();
		double yMax = graph.getYMax();
		
		double yForXMin = a * xMin + b;
		double yForXMax = a * xMax + b;
		
		double xForYMin = (yMin - b) / a;
		double xForYMax = (yMax - b) / a;
		
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
			Point0 p1 = graph.pMult(pp.get(0));
			Point0 p2 = graph.pMult(pp.get(1));
			graph.drawLine(c, p1, p2);
		}
	}

}
