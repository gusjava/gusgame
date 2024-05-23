package gus.game5.main.test3;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.dyn.DynGAngle;
import gus.game5.core.features.g.GPoint0;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.shape.graph.GraphLine1;
import gus.game5.core.shape.graph.GraphPoint2;
import gus.game5.core.shape.graph.GraphSegment1;
import gus.game5.core.shape.graph.GraphSegment2;
import gus.game5.core.shape.graph.GraphULine2;
import gus.game5.core.shape.graph.ShapeGraph;
import gus.game5.core.util.UtilLine;

public class MainGraph2Segment extends Game2 {
	
	public static final int GRAPH_WIDTH = 700;
	public static final int GRAPH_HEIGHT = 700;

	public static void main(String[] args) {
		MainGraph2Segment main = new MainGraph2Segment();
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
		
		GraphSegment1 s1 = new GraphSegment1(Color.RED.darker(), p1(-6,1), p1(2, 3));
		s1.setColorProj(Color.LIGHT_GRAY);
		s1.setDisplayCenter(true);
		s1.setDisplayEnds(true);

		GraphLine1 s1l1 = new GraphLine1(Color.BLUE, s1.getPoint1(), s1.getSlopeInv());
		GraphLine1 s1l2 = new GraphLine1(Color.BLUE, s1.getPoint2(), s1.getSlopeInv());
		GraphLine1 s1l12 = new GraphLine1(Color.BLUE, s1.getPoint12(), s1.getSlopeInv());
		

		graph.add("L1", s1l1);
		graph.add("L2", s1l2);
		graph.add("L12", s1l12);
		graph.add("S1", s1);
		
		DynGAngle a1 = newDynGAngle(Angle.ANGLE90, 0.01);
		GPoint0 gPoint1 = ()->p1(4,-3).pRotate(p1(4,0), a1.g());
		GPoint0 gPoint2 = ()->p1(4,4).pRotate(p1(4,0), a1.g());
		
		GraphSegment2 s2 = new GraphSegment2(Color.RED.darker(), gPoint1, gPoint2);
		s2.setColorProj(Color.LIGHT_GRAY);
		s2.setDisplayCenter(true);
		s2.setDisplayEnds(true);

		GraphULine2 s2l1 = new GraphULine2(Color.BLUE, s2::getPoint1, s2::getSlopeInv);
		GraphULine2 s2l2 = new GraphULine2(Color.BLUE, s2::getPoint2, s2::getSlopeInv);
		GraphULine2 s2l12 = new GraphULine2(Color.BLUE, s2::getPoint12, s2::getSlopeInv);
		
		GraphPoint2 p1 = new GraphPoint2(()->s2.pointAtRatio(1.5));
		GraphPoint2 p2 = new GraphPoint2(()->UtilLine.pointAt(s1l12, s2l1));
		
		p2.setColorProjXY(Color.LIGHT_GRAY);
		
		graph.add("L1", s2l1);
		graph.add("L2", s2l2);
		graph.add("L12", s2l12);
		graph.add("S2", s2);
		graph.add("P1", p1);
		graph.add("P2", p2);
		
	}
}
