package gus.game5.core.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

import gus.game5.core.angle.Angle;
import gus.game5.core.clean.Clean;
import gus.game5.core.clean.CleanList;
import gus.game5.core.draw.Draw;
import gus.game5.core.draw.DrawList;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.drawing.text.DrawingTextC;
import gus.game5.core.drawing.text.DrawingTextP;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.dyn.DynGAngle;
import gus.game5.core.dyn.DynGDouble;
import gus.game5.core.dyn.DynGInt;
import gus.game5.core.dyn.DynGLong;
import gus.game5.core.dyn.DynList;
import gus.game5.core.dyn.DynTimer;
import gus.game5.core.features.g.G;
import gus.game5.core.map.GameMap;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.Shape;
import gus.game5.core.shape.Shape0.AnchorType;
import gus.game5.core.shape.ShapeImg;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRect;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.shape.board.ShapeCellBuilder;
import gus.game5.core.shape.board.ShapeCellIntBuilder;
import gus.game5.core.shape.graph.ShapeGraph;
import gus.game5.core.util.UtilImage;
import gus.game5.core.util.UtilResource;

public abstract class Game1 extends Game {
	
	protected abstract void initialize1();

	protected DrawList<Draw> drawList;
	protected DynList<Dyn> dynList;
	protected CleanList<Clean> cleanList;
	
	protected void initialize() {
		drawList = new DrawList<>();
		dynList = new DynList<>();
		cleanList = new CleanList<>();
		initialize1();
	}

	protected Draw buildDraw() {
		return drawList;
	}
	
	protected void addDraw(Draw draw) {
		drawList.add(draw);
	}
	
	protected void addDyn(Dyn dyn) {
		dynList.add(dyn);
	}
	
	protected void addClean(Clean clean) {
		cleanList.add(clean);
	}
	
	protected void clearAll() {
		drawList.clear();
		dynList.clear();
		cleanList.clear();
	}
	
	protected void goNext() {
		dynList.goNext();
	}
	
	protected void goBack() {
		dynList.goBack();
	}
	
	protected void clean() {
		cleanList.clean();
	}
	
	
	
	/*
	 * NEW SHAPE BOARD
	 */

	protected <E extends ShapeCell> ShapeBoard<E> newShapeBoard(double cellSize, int[][] data, ShapeCellIntBuilder<E> builder) {
		return newShapeBoard(cellSize, data, (i,j)->builder.build(i, j, data[i][j]));
	}

	protected <E extends ShapeCell> ShapeBoard<E> newShapeBoard(double cellSize, int[][] data, ShapeCellBuilder<E> builder) {
		int x = data.length;
		if(x==0) throw new RuntimeException("Invalid data size for ShapeBoard");
		int y = data[0].length;
		if(y==0) throw new RuntimeException("Invalid data size for ShapeBoard");
		return newShapeBoard(cellSize, x, y, builder);
	}

	protected <E extends ShapeCell> ShapeBoard<E> newShapeBoard(double cellSize, int x, int y, ShapeCellBuilder<E> builder) {
		ShapeBoard<E> shapeBoard = new ShapeBoard<>(cellSize, x, y, builder);
		addDraw(shapeBoard);
		addDyn(shapeBoard);
		return shapeBoard;
	}

	protected <E extends ShapeCell> ShapeBoard<E> newShapeBoard(double cellWidth, double cellHeight, int x, int y, ShapeCellBuilder<E> builder) {
		ShapeBoard<E> shapeBoard = new ShapeBoard<>(cellWidth, cellHeight, x, y, builder);
		addDraw(shapeBoard);
		addDyn(shapeBoard);
		return shapeBoard;
	}
	
	protected <E extends ShapeCell> ShapeBoard<E> newShapeBoard(double cellSize, int x, ShapeCellBuilder<E> builder) {
		ShapeBoard<E> shapeBoard = new ShapeBoard<>(cellSize, x, builder);
		addDraw(shapeBoard);
		addDyn(shapeBoard);
		return shapeBoard;
	}
	
	protected <E extends ShapeCell> ShapeBoard<E> newShapeBoard(double cellWidth, double cellHeight, int x, ShapeCellBuilder<E> builder) {
		ShapeBoard<E> shapeBoard = new ShapeBoard<>(cellWidth, cellHeight, x, builder);
		addDraw(shapeBoard);
		addDyn(shapeBoard);
		return shapeBoard;
	}
	
	/*
	 * NEW SHAPE LIST
	 */
	
	protected <E extends Shape> ShapeList<E> newShapeList() {
		ShapeList<E> shapeList = new ShapeList<>();
		addDraw(shapeList);
		addDyn(shapeList);
		addClean(shapeList);
		return shapeList;
	}
	
	@SuppressWarnings("unchecked")
	protected <E extends Shape> ShapeList<E> newShapeList(E... shapes) {
		ShapeList<E> shapeList = new ShapeList<>(shapes);
		addDraw(shapeList);
		addDyn(shapeList);
		addClean(shapeList);
		return shapeList;
	}
	
	protected <E extends Shape> ShapeList<E> newShapeList(List<E> shapes) {
		ShapeList<E> shapeList = new ShapeList<>(shapes);
		addDraw(shapeList);
		addDyn(shapeList);
		addClean(shapeList);
		return shapeList;
	}
	
	/*
	 * NEW SHAPE
	 */
	
	protected <E extends Shape> E newShape(E shape) {
		addDraw(shape);
		addDyn(shape);
		return shape;
	}
	
	/*
	 * NEW SHAPE ROUND
	 */
	
	protected ShapeRound newShapeRound(Point0 anchor, double radius, Color color, AnchorType type) {
		return newShape(new ShapeRound(anchor, radius, color, type));
	}
	
	protected ShapeRound newShapeRound(Point0 anchor, double radius, AnchorType type) {
		return newShape(new ShapeRound(anchor, radius, type));
	}
	
	protected ShapeRound newShapeRound(Point2 anchor, double radius, Color color, AnchorType type) {
		return newShape(new ShapeRound(anchor, radius, color, type));
	}
	
	protected ShapeRound newShapeRound(Point2 anchor, double radius, AnchorType type) {
		return newShape(new ShapeRound(anchor, radius, type));
	}
	
	protected ShapeRound newShapeRound(Point0 anchor, double radius, Color color) {
		return newShape(new ShapeRound(anchor, radius, color));
	}
	
	protected ShapeRound newShapeRound(Point0 anchor, double radius) {
		return newShape(new ShapeRound(anchor, radius));
	}
	
	protected ShapeRound newShapeRound(Point2 anchor, double radius, Color color) {
		return newShape(new ShapeRound(anchor, radius, color));
	}
	
	protected ShapeRound newShapeRound(Point2 anchor, double radius) {
		return newShape(new ShapeRound(anchor, radius));
	}
	
	/*
	 * NEW SHAPE RECT
	 */
	
	protected ShapeRect newShapeRect(Point0 anchor, double width, double height, Color color, AnchorType type) {
		return newShape(new ShapeRect(anchor, width, height, color, type));
	}
	
	protected ShapeRect newShapeRect(Point0 anchor, double width, double height, AnchorType type) {
		return newShape(new ShapeRect(anchor, width, height, type));
	}
	
	protected ShapeRect newShapeRect(Point2 anchor, double width, double height, Color color, AnchorType type) {
		return newShape(new ShapeRect(anchor, width, height, color, type));
	}
	
	protected ShapeRect newShapeRect(Point2 anchor, double width, double height, AnchorType type) {
		return newShape(new ShapeRect(anchor, width, height, type));
	}
	
	protected ShapeRect newShapeRect(Point0 anchor, double width, double height, Color color) {
		return newShape(new ShapeRect(anchor, width, height, color));
	}
	
	protected ShapeRect newShapeRect(Point0 anchor, double width, double height) {
		return newShape(new ShapeRect(anchor, width, height));
	}
	
	protected ShapeRect newShapeRect(Point2 anchor, double width, double height, Color color) {
		return newShape(new ShapeRect(anchor, width, height, color));
	}
	
	protected ShapeRect newShapeRect(Point2 anchor, double width, double height) {
		return newShape(new ShapeRect(anchor, width, height));
	}
	
	/*
	 * NEW SHAPE IMG
	 */
	
	protected ShapeImg newShapeImg(Point0 anchor, double width, double height, AnchorType type, BufferedImage img) {
		return newShape(new ShapeImg(anchor, width, height, type, img));
	}
	
	protected ShapeImg newShapeImg(Point0 anchor, double width, double height, BufferedImage img) {
		return newShape(new ShapeImg(anchor, width, height, img));
	}
	
	protected ShapeImg newShapeImg(Point0 anchor, AnchorType type, BufferedImage img) {
		return newShape(new ShapeImg(anchor, type, img));
	}
	
	protected ShapeImg newShapeImg(Point0 anchor, BufferedImage img) {
		return newShape(new ShapeImg(anchor, img));
	}
	
	protected ShapeImg newShapeImg(Point2 anchor, double width, double height, AnchorType type, BufferedImage img) {
		return newShape(new ShapeImg(anchor, width, height, type, img));
	}
	
	protected ShapeImg newShapeImg(Point2 anchor, double width, double height, BufferedImage img) {
		return newShape(new ShapeImg(anchor, width, height, img));
	}
	
	protected ShapeImg newShapeImg(Point2 anchor, AnchorType type, BufferedImage img) {
		return newShape(new ShapeImg(anchor, type, img));
	}
	
	protected ShapeImg newShapeImg(Point2 anchor, BufferedImage img) {
		return newShape(new ShapeImg(anchor, img));
	}
	
	/*
	 * NEW GAME MAP
	 */
	
	protected GameMap newGameMap(int width, int height) {
		GameMap gameMap = new GameMap(this, width, height);
		addDraw(gameMap);
		addDyn(gameMap);
		addClean(gameMap);
		return gameMap;
	}
	
	/*
	 * NEW DRAWING TEXT
	 */
	
	protected DrawingText newDrawingText(Color color, Point1 origin, G<String> gString) {
		DrawingText draw = new DrawingText(color, origin, gString);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingText(Color color, Point1 origin, String text) {
		DrawingText draw = new DrawingText(color, origin, text);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingText(Point1 origin, G<String> gString) {
		DrawingText draw = new DrawingText(origin, gString);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingText(Point1 origin, String text) {
		DrawingText draw = new DrawingText(origin, text);
		addDraw(draw);
		return draw;
	}
	
	/*
	 * NEW DRAWING TEXT C
	 */
	
	protected DrawingText newDrawingTextC(Color color, Point1 origin, G<String> gString) {
		DrawingText draw = new DrawingTextC(color, origin, gString);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingTextC(Color color, Point1 origin, String text) {
		DrawingText draw = new DrawingTextC(color, origin, text);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingTextC(Point1 origin, G<String> gString) {
		DrawingText draw = new DrawingTextC(origin, gString);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingTextC(Point1 origin, String text) {
		DrawingText draw = new DrawingTextC(origin, text);
		addDraw(draw);
		return draw;
	}
	
	/*
	 * NEW DRAWING TEXT P
	 */
	
	protected DrawingText newDrawingTextP(Color color, Point1 origin, G<String> gString, double fW, double fH) {
		DrawingText draw = new DrawingTextP(color, origin, gString, fW, fH);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingTextP(Color color, Point1 origin, String text, double fW, double fH) {
		DrawingText draw = new DrawingTextP(color, origin, text, fW, fH);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingTextP(Point1 origin, G<String> gString, double fW, double fH) {
		DrawingText draw = new DrawingTextP(origin, gString, fW, fH);
		addDraw(draw);
		return draw;
	}
	
	protected DrawingText newDrawingTextP(Point1 origin, String text, double fW, double fH) {
		DrawingText draw = new DrawingTextP(origin, text, fW, fH);
		addDraw(draw);
		return draw;
	}
	
	/*
	 * NEW SHAPE GRAPH
	 */
	
	protected ShapeGraph newShapeGraph(Point0 anchor, double width, double height) {
		ShapeGraph graph = new ShapeGraph(anchor, width, height);
		newShape(graph);
		return graph;
	}
	
	/*
	 * NEW DYN GANGLE
	 */
	
	protected DynGAngle newDynGAngle(Angle angle, DynGDouble derived) {
		DynGAngle dyn = new DynGAngle(angle, derived);
		addDyn(dyn);
		return dyn;
	}
	
	protected DynGAngle newDynGAngle(Angle angle, double derived) {
		DynGAngle dyn = new DynGAngle(angle, derived);
		addDyn(dyn);
		return dyn;
	}
	
	protected DynGAngle newDynGAngle(Angle angle) {
		DynGAngle dyn = new DynGAngle(angle);
		addDyn(dyn);
		return dyn;
	}
	
	/*
	 * NEW DYN GDOUBLE
	 */
	
	protected DynGDouble newDynGDouble(double val, DynGDouble derived) {
		DynGDouble dyn = new DynGDouble(val, derived);
		addDyn(dyn);
		return dyn;
	}
	
	protected DynGDouble newDynGDouble(double val, double derived) {
		DynGDouble dyn = new DynGDouble(val, derived);
		addDyn(dyn);
		return dyn;
	}
	
	protected DynGDouble newDynGDouble(double val) {
		DynGDouble dyn = new DynGDouble(val);
		addDyn(dyn);
		return dyn;
	}
	
	/*
	 * NEW DYN GINT
	 */
	
	protected DynGInt newDynGInt(int val, DynGInt derived) {
		DynGInt dyn = new DynGInt(val, derived);
		addDyn(dyn);
		return dyn;
	}
	
	protected DynGInt newDynGInt(int val, int derived) {
		DynGInt dyn = new DynGInt(val, derived);
		addDyn(dyn);
		return dyn;
	}
	
	protected DynGInt newDynGInt(int val) {
		DynGInt dyn = new DynGInt(val);
		addDyn(dyn);
		return dyn;
	}
	
	/*
	 * NEW DYN GLONG
	 */
	
	protected DynGLong newDynGLong(long val, DynGLong derived) {
		DynGLong dyn = new DynGLong(val, derived);
		addDyn(dyn);
		return dyn;
	}
	
	protected DynGLong newDynGLong(long val, long derived) {
		DynGLong dyn = new DynGLong(val, derived);
		addDyn(dyn);
		return dyn;
	}
	
	protected DynGLong newDynGLong(long val) {
		DynGLong dyn = new DynGLong(val);
		addDyn(dyn);
		return dyn;
	}
	
	/*
	 * NEW DYN POINT2
	 */
	
	protected Point2 newDynPoint2(double x, double y) {
		Point2 dyn = p2(x, y);
		addDyn(dyn);
		return dyn;
	}
	
	protected Point2 newDynPoint2(double x, double y, double dx, double dy) {
		Point2 dyn = p2(x, y, dx, dy);
		addDyn(dyn);
		return dyn;
	}
	
	/*
	 * NEW DYN TIMER
	 */
	
	protected DynTimer newDynTimer() {
		DynTimer dyn = new DynTimer();
		addDyn(dyn);
		return dyn;
	}
	
	/*
	 * RESOURCE
	 */
	
	public String resourceAt(String fileName) {
		return UtilResource.resourceAt(getClass(), fileName);
	}
	
	public ImageIcon iconAt(String fileName) {
		return UtilImage.readIcon(resourceAt(fileName));
	}
}
