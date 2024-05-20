package gus.game5.core.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

import gus.game5.core.clean.Clean;
import gus.game5.core.clean.CleanList;
import gus.game5.core.draw.Draw;
import gus.game5.core.draw.DrawList;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.drawing.text.DrawingTextC;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.dyn.DynList;
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

	protected <E extends ShapeCell> ShapeBoard<E> newShapeBoard(double cellSize, int x, int y, ShapeCellBuilder<E> builder) {
		ShapeBoard<E> shapeBoard = new ShapeBoard<>(cellSize, x, y, builder);
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
	 * RESOURCE
	 */
	
	public String resourceAt(String fileName) {
		return UtilResource.resourceAt(getClass(), fileName);
	}
	
	public ImageIcon iconAt(String fileName) {
		return UtilImage.readIcon(resourceAt(fileName));
	}
}
