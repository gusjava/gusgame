package gus.game5.main.test2;

import java.awt.Color;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.graph.GraphLine1;
import gus.game5.core.shape.graph.GraphLine2;
import gus.game5.core.shape.graph.GraphObject;
import gus.game5.core.shape.graph.GraphPoint1;
import gus.game5.core.shape.graph.GraphPoint2;
import gus.game5.core.shape.graph.GraphPolynom1;
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

	protected void turn() {
		graph.goNext();
	}

	protected void initialize1() {
		graph = new ShapeGraph(gameCenter(), GRAPH_WIDTH, GRAPH_HEIGHT);
		graph.setStep(30);
		newShape(graph);
		
		// point P1 (2,2) qui monte, rouge
		Point2 pp1 = p2(2,2);
		pp1.setDerived(0, 0.01);
		GraphPoint2 p1 = new GraphPoint2(Color.RED, "P1", pp1);
		
		GraphPoint2 p2 = new GraphPoint2("P2",4,3);
		GraphLine2 l1 = new GraphLine2(Color.ORANGE, "L1", p1, p2);
		
		GraphPoint1 p3 = new GraphPoint1(Color.GREEN, "P3",-2,1);
		p3.setDisplayMode(GraphPoint1.DISPLAY_MODE_ARROW);
		
		GraphLine1 l2 = new GraphLine1(Color.BLUE, "L2", 1);
		GraphLine1 l3 = new GraphLine1(Color.BLUE, "L3", 1, 1.5);
		GraphYLine1 l4 = new GraphYLine1(Color.BLUE, "L4", -2.5);
		
		GraphPolynom1 f1 = new GraphPolynom1(-2, 0, 0.5);
		
		add(p1);
		add(p2);
		add(p3);
		
		add(l1);
		add(l2);
		add(l3);
		add(l4);

		add(f1);
	}
	
	private void add(GraphObject obj) {
		graph.addObject(obj);
	}
}
