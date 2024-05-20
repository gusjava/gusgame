package gus.game5.main.test2;

import java.awt.Color;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.graph.GraphLine1;
import gus.game5.core.shape.graph.GraphLine2;
import gus.game5.core.shape.graph.GraphPoint1;
import gus.game5.core.shape.graph.GraphPoint2;
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
		
		Point2 pp1 = p2(2,2);
		pp1.setDerived(0, 0.01);
		
		GraphPoint2 p1 = new GraphPoint2(Color.RED, "P1", pp1);
		GraphPoint2 p2 = new GraphPoint2("P2",4,3);
		GraphLine2 l3 = new GraphLine2(Color.ORANGE, "L3", p1, p2);
		
		GraphPoint1 p3 = new GraphPoint1("P3",-2,1);
		GraphLine1 l1 = new GraphLine1(Color.BLUE, "L1", 1);
		GraphLine1 l2 = new GraphLine1(Color.BLUE, "L2", 1, 1.5);
		
		graph.addObject(p1);
		graph.addObject(p2);
		graph.addObject(p3);
		graph.addObject(l1);
		graph.addObject(l2);
		graph.addObject(l3);
	}
}
