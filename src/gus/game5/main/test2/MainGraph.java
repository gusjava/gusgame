package gus.game5.main.test2;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0Dda;
import gus.game5.core.point.point0.Point0Dxh;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.graph.GraphLine1;
import gus.game5.core.shape.graph.GraphLine2;
import gus.game5.core.shape.graph.GraphObject;
import gus.game5.core.shape.graph.GraphPoint1;
import gus.game5.core.shape.graph.GraphPolynom1;
import gus.game5.core.shape.graph.GraphTangent2;
import gus.game5.core.shape.graph.GraphYLine1;
import gus.game5.core.shape.graph.ShapeGraph;

public class MainGraph extends Game1 {
	
	public static final int GRAPH_WIDTH = 700;
	public static final int GRAPH_HEIGHT = 700;

	public static void main(String[] args) {
		MainGraph main = new MainGraph();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Graph");
		s.setBackground(Color.WHITE);
		s.setSize(GRAPH_WIDTH+20, GRAPH_HEIGHT+20);
	}
	
	private ShapeGraph graph;
	private double x1;
	private Angle a1;

	protected void turn() {
		goNext();
		x1 += 0.01;
		a1 = a1.add(0.01);
	}

	protected void initialize1() {
		graph = new ShapeGraph(gameCenter(), GRAPH_WIDTH, GRAPH_HEIGHT);
		graph.setStep(30);
		newShape(graph);
		
		x1 = -1;
		a1 = Angle.ANGLE120;
		
		Point2 pp1 = p2(2,2);
		pp1.setDerived(0, 0.01);
		addDyn(pp1);
		
		Point0Dda pp3 = new Point0Dda(2.5, ()->a1);
		
		GraphPoint1 p1 = new GraphPoint1(Color.RED, pp1);
		GraphPoint1 p2 = new GraphPoint1(4,3);
		GraphPoint1 p3 = new GraphPoint1(Color.GREEN.darker(), pp3);
		
		p3.setDisplayMode(GraphPoint1.DISPLAY_MODE_ARROW);
		
		GraphLine2 l1 = new GraphLine2(Color.ORANGE, p1::getPoint, p2::getPoint);
		
		GraphLine1 l2 = new GraphLine1(Color.BLUE, 1);
		GraphLine1 l3 = new GraphLine1(Color.BLUE, 1.5, 1);
		GraphYLine1 l4 = new GraphYLine1(Color.BLUE, -2.5);
		
		GraphPolynom1 c1 = new GraphPolynom1(Color.GRAY, -2, 0, 0.5);
		GraphTangent2 t1 = new GraphTangent2(Color.GRAY, c1.getFunction(), ()->x1);
		GraphPoint1 p4 = new GraphPoint1(Color.RED, new Point0Dxh(c1.getFunction(), ()->x1));
		
		graph.add("P1", p1);
		graph.add("P2", p2);
		graph.add("P3", p3);
		
		graph.add("L1", l1);
		graph.add("L2", l2);
		graph.add("L3", l3);
		graph.add("L4", l4);

		graph.add("A", c1);
		graph.add("T", t1);
		graph.add("P4", p4);
	}
}
