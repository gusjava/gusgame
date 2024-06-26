package gus.game5.main.test3;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.dyn.DynGAngle;
import gus.game5.core.dyn.DynGDouble;
import gus.game5.core.function.Function;
import gus.game5.core.function.FunctionSin;
import gus.game5.core.function.FunctionUScale;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0Dda;
import gus.game5.core.point.point0.Point0Dxh;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.graph.GraphCircle1;
import gus.game5.core.shape.graph.GraphFunction1;
import gus.game5.core.shape.graph.GraphLine1;
import gus.game5.core.shape.graph.GraphLine2;
import gus.game5.core.shape.graph.GraphPoint1;
import gus.game5.core.shape.graph.GraphPolynom1;
import gus.game5.core.shape.graph.GraphSegment1;
import gus.game5.core.shape.graph.GraphTangent2;
import gus.game5.core.shape.graph.GraphTriangle1;
import gus.game5.core.shape.graph.GraphYLine1;
import gus.game5.core.shape.graph.ShapeGraph;

public class MainGraph1 extends Game2 {
	
	public static final int GRAPH_WIDTH = 700;
	public static final int GRAPH_HEIGHT = 700;

	public static void main(String[] args) {
		MainGraph1 main = new MainGraph1();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Graph");
		s.setBackground(Color.WHITE);
		s.setSize(GRAPH_WIDTH+20, GRAPH_HEIGHT+20);
	}

	protected void initialize1() {
		ShapeGraph graph = newShapeGraph(gameCenter(), GRAPH_WIDTH, GRAPH_HEIGHT);
		graph.setStep(30);
		
		// initialisation des �l�ments dynamiques
		
		DynGDouble x1 = newDynGDouble(-3, 0.01);
		DynGDouble x2 = newDynGDouble(-5, 0.005);
		DynGAngle a1 = newDynGAngle(Angle.ANGLE120, 0.01);
		Point2 pp1 = newDynPoint2(2, 2, 0, 0.01);
		
		// graph points
		
		GraphPoint1 p1 = new GraphPoint1(Color.RED, pp1);
		GraphPoint1 p2 = new GraphPoint1(4,3);
		GraphPoint1 p3 = new GraphPoint1(Color.GREEN.darker(), new Point0Dda(2.5, a1));
		
		p3.setDisplayMode(GraphPoint1.DISPLAY_MODE_ARROW);
		
		// graph lines
		
		GraphLine2 l1 = new GraphLine2(Color.ORANGE, p1, p2);
		
		GraphLine1 l2 = new GraphLine1(Color.BLUE, 1);
		GraphLine1 l3 = new GraphLine1(Color.BLUE, 1.5, 1);
		GraphYLine1 l4 = new GraphYLine1(Color.BLUE, -2.5);
		
		// graph functions
		
		Function sin = new FunctionUScale(new FunctionSin(), 4, Math.PI/2, 1.5, 5);
		
		GraphPolynom1 y1 = new GraphPolynom1(Color.GRAY, -2, 0, 0.5);
		GraphFunction1 y2 = new GraphFunction1(Color.GRAY, sin);
		GraphTangent2 t1 = new GraphTangent2(Color.GRAY, y1.getFunction(), x1);
		
		GraphPoint1 p4 = new GraphPoint1(Color.RED, new Point0Dxh(y1.getFunction(), x1));
		GraphPoint1 p5 = new GraphPoint1(Color.BLACK, new Point0Dxh(sin, x2));
		
		p1.setColorProjXY(Color.LIGHT_GRAY);
		p4.setColorProjXY(Color.LIGHT_GRAY);
		p5.setColorProjXY(Color.LIGHT_GRAY);
		
		// graph forms
		
		GraphCircle1 c1 = new GraphCircle1(Color.RED.darker(), p1(-4,2), 1.2);
		GraphTriangle1 c2 = new GraphTriangle1(Color.RED.darker(), p1(-3,-5), p1(-2,-2.5), p1(1,-4));
		GraphSegment1 c3 = new GraphSegment1(Color.RED.darker(), p1(-8,-1), p1(-4, 1));

		c1.setDisplayCenter(true);
		c2.setDisplayCenter(true);
		c3.setDisplayCenter(true);
		
		graph.add("P1", p1);
		graph.add("P2", p2);
		graph.add("P3", p3);
		graph.add("P4", p4);
		graph.add("P5", p5);
		
		graph.add("L1", l1);
		graph.add("L2", l2);
		graph.add("L3", l3);
		graph.add("L4", l4);

		graph.add("Y1", y1);
		graph.add("Y2", y2);
		graph.add("T1", t1);
		
		graph.add("C1", c1);
		graph.add("C2", c2);
		graph.add("C3", c3);
	}
}
