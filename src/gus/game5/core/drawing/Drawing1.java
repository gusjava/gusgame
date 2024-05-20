package gus.game5.core.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.RenderedImage;

import gus.game5.core.alter.AlterAdd;
import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point0.Point0Avg;
import gus.game5.core.point.point0.Point0Sum;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point1.Point1List;

public abstract class Drawing1 extends Drawing0 {
	
	public Drawing1() {
		super();
	}
	
	public Drawing1(Point0 p) {
		super();
		initOrigin().setXY(p);
	}
	
	public Drawing1(double x, double y) {
		super();
		initOrigin().setXY(x, y);
	}
	
	/*
	 * ORIGIN
	 */
	
	private Point1 origin;
	
	public Point1 getOrigin() {
		return origin;
	}
	
	public Point1 initOrigin() {
		if(origin==null) setOrigin(new Point1());
		return origin;
	}
	
	public void setOrigin(Point1 origin) {
		this.origin = origin;
		addAlter(new AlterAdd(origin));
	}
	
	public void setOrigin(Point0 origin) {
		setOrigin(new Point1(origin));
	}
	
	/*
	 * COLOR
	 */
	
	public Color color = Color.BLACK;
	
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	
	public Color c(int r, int g, int b) {
		return new Color(r, g, b);
	}
	
	/*
	 * POINT
	 */
	
	public static Point1 p(double x, double y) {
		return new Point1(x, y);
	}
	
	public static Point1 avg(Point0... points) {
		return new Point0Avg(points).p1();
	}
	
	public static Point1 sum(Point0... points) {
		return new Point0Sum(points).p1();
	}
	
	/*
	 * STRUCTURE
	 */
	
	public Point1List structure = new Point1List();
	
	public Point1List getStructure() {return structure;}
	
	public Point1 addStruct(Point0 p0) {
		Point1 p = new Point1(p0);
		structure.add(p);
		return p;
	}
	
	public Point1 addStruct(double x, double y) {
		Point1 p = new Point1(x,y);
		structure.add(p);
		return p;
	}
	
	/*
	 * FONT
	 */
	
	public Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setFont(int size) {
		setFont(font(size));
	}
	
	public void setFontBold() {
		setFont(fontBold());
	}
	
	public void setFontBold(int size) {
		setFont(fontBold(size));
	}
	
	public void setFontItalic() {
		setFont(fontItalic());
	}
	
	public void setFontItalic(int size) {
		setFont(fontItalic(size));
	}
	
	public void setFontPlain() {
		setFont(fontPlain());
	}
	
	public void setFontPlain(int size) {
		setFont(fontPlain(size));
	}
	
	public Font getFont() {
		return font;
	}
	
	/*
	 * FONT SIZE
	 */
	
	public Font font(int size) {
		return font.deriveFont((float) size);
	}
	
	/*
	 * FONT BOLD
	 */
	
	public Font fontBold() {
		return font.deriveFont(Font.BOLD);
	}
	public Font fontBold(String name) {
		return new Font(name, Font.BOLD, font.getSize());
	}
	public Font fontBold(int size) {
		return font.deriveFont(Font.BOLD).deriveFont((float) size);
	}
	public Font fontBold(String name, int size) {
		return new Font(name, Font.BOLD, size);
	}
	
	/*
	 * FONT ITALIC
	 */
	
	public Font fontItalic() {
		return font.deriveFont(Font.ITALIC);
	}
	public Font fontItalic(String name) {
		return new Font(name, Font.ITALIC, font.getSize());
	}
	public Font fontItalic(int size) {
		return font.deriveFont(Font.ITALIC).deriveFont((float) size);
	}
	public Font fontItalic(String name, int size) {
		return new Font(name, Font.ITALIC, size);
	}
	
	/*
	 * FONT PLAIN
	 */
	
	public Font fontPlain() {
		return font.deriveFont(Font.PLAIN);
	}
	public Font fontPlain(String name) {
		return new Font(name, Font.PLAIN, font.getSize());
	}
	public Font fontPlain(int size) {
		return font.deriveFont(Font.PLAIN).deriveFont((float) size);
	}
	public Font fontPlain(String name, int size) {
		return new Font(name, Font.PLAIN, size);
	}

	
	/*
	 * DRAW
	 * 
	 * - drawPoly
	 * - drawRect
	 * - drawRectC
	 * - drawOval
	 * - drawOvalC
	 * - drawSquare
	 * - drawSquareC
	 * - drawRound
	 * - drawRoundC
	 * - drawArcC
	 */
	
	/*
	 * DRAW POLY
	 */
	
	public void drawPoly(Color color, Point0... pp) {
		g2_setColor(color);
		g2_drawPoly(pp);
	}
	
	public void drawPoly(Point0... pp) {
		drawPoly(color, pp);
	}
	
	/*
	 * DRAW RECT
	 */
	
	public void drawRect(Color color, Point0 p0, double width, double height) {
		g2_setColor(color);
		g2_drawRect(p0, width, height);
	}
	
	public void drawRect(Color color, double width, double height) {
		drawRect(color, p(0,0), width, height);
	}
	
	public void drawRect(Point0 p0, double width, double height) {
		drawRect(color, p0, width, height);
	}
	
	public void drawRect(double width, double height) {
		drawRect(color, p(0,0), width, height);
	}
	
	/*
	 * DRAW RECT C
	 */
	
	public void drawRectC(Color color, Point0 pc, double width, double height) {
		g2_setColor(color);
		g2_drawRectC(pc, width, height);
	}
	
	public void drawRectC(Color color, double width, double height) {
		drawRectC(color, p(0,0), width, height);
	}
	
	public void drawRectC(Point0 pc, double width, double height) {
		drawRectC(color, pc, width, height);
	}
	
	public void drawRectC(double width, double height) {
		drawRectC(color, p(0,0), width, height);
	}
	
	/*
	 * DRAW POINT
	 */
	
	public void drawPoint(Color color, Point0 p) {
		drawSquare(color, p, 1);
	}
	
	public void drawPoint(Point0 p) {
		drawPoint(color, p);
	}
	
	/*
	 * DRAW SQUARE
	 */
	
	public void drawSquare(Color color, Point0 p0, double c) {
		drawRect(color, p0, c, c);
	}
	
	public void drawSquare(Color color, double c) {
		drawSquare(color, p(0, 0), c);
	}
	
	public void drawSquare(Point0 p0, double c) {
		drawSquare(color, p0, c);
	}
	
	public void drawSquare(double c) {
		drawSquare(color, p(0, 0), c);
	}
	
	/*
	 * DRAW SQUARE C
	 */
	
	public void drawSquareC(Color color, Point0 pc, double c) {
		drawRectC(color, pc, c, c);
	}
	
	public void drawSquareC(Color color, double c) {
		drawSquareC(color, p(0, 0), c);
	}
	
	public void drawSquareC(Point0 pc, double c) {
		drawSquareC(color, pc, c);
	}
	
	public void drawSquareC(double c) {
		drawSquareC(color, p(0, 0), c);
	}
	
	/*
	 * DRAW OVAL
	 */
	
	public void drawOval(Color color, Point0 p0, double width, double height) {
		g2_setColor(color);
		g2_drawOval(p0, width, height);
	}
	
	public void drawOval(Color color, double width, double height) {
		drawOval(color, p(0,0), width, height);
	}
	
	public void drawOval(Point0 p0, double width, double height) {
		drawOval(color, p0, width, height);
	}
	
	public void drawOval(double width, double height) {
		drawOval(color, p(0,0), width, height);
	}
	
	/*
	 * DRAW OVAL C
	 */
	
	public void drawOvalC(Color color, Point0 pc, double width, double height) {
		g2_setColor(color);
		g2_drawOvalC(pc, width, height);
	}
	
	public void drawOvalC(Color color, double width, double height) {
		drawOvalC(color, p(0,0), width, height);
	}
	
	public void drawOvalC(Point0 pc, double width, double height) {
		drawOvalC(color, pc, width, height);
	}
	
	public void drawOvalC(double width, double height) {
		drawOvalC(color, p(0,0), width, height);
	}
	
	/*
	 * DRAW ROUND
	 */
	
	public void drawRound(Color color, Point0 p0, double radius) {
		drawOval(color, p0, radius*2, radius*2);
	}
	
	public void drawRound(Point0 p0, double radius) {
		drawRound(color, p0, radius);
	}
	
	public void drawRound(Color color, double radius) {
		drawRound(color, p(0, 0), radius);
	}
	
	public void drawRound(double radius) {
		drawRound(color, radius);
	}
	
	/*
	 * DRAW ROUND C
	 */
	
	public void drawRoundC(Color color, Point0 pc, double radius) {
		drawOvalC(color, pc, radius*2, radius*2);
	}
	
	public void drawRoundC(Point0 pc, double radius) {
		drawRoundC(color, pc, radius);
	}
	
	public void drawRoundC(Color color, double radius) {
		drawRoundC(color, p(0, 0), radius);
	}
	
	public void drawRoundC(double radius) {
		drawRoundC(color, radius);
	}
	
	
	
	/*
	 * FILL
	 * 
	 * - fillPoly
	 * - fillRect
	 * - fillRectC
	 * - fillOval
	 * - fillOvalC
	 * - fillSquare
	 * - fillSquareC
	 * - fillRound
	 * - fillRoundC
	 * - fillArcC
	 */
	
	
	/*
	 * FILL POLY
	 */
	
	public void fillPoly(Color color, Point0... pp) {
		g2_setColor(color);
		g2_fillPoly(pp);
	}
	
	public void fillPoly(Point0... pp) {
		fillPoly(color, pp);
	}
	
	/*
	 * PAINT IMAGE
	 */

	public void paintImage(Point0 p0, double width, double height, Image img) {
		g2_paintImage(p0, width, height, img);
	}

	public void paintImage(Point0 p0, double size, Image img) {
		g2_paintImage(p0, size, size, img);
	}
	
	public void paintImage(double width, double height, Image img) {
		paintImage(p(0,0), width, height, img);
	}
	
	public void paintImage(double size, Image img) {
		paintImage(p(0,0), size, size, img);
	}
	
	/*
	 * PAINT IMAGE C
	 */
	
	public void paintImageC(Point0 pc, double width, double height, Image img) {
		g2_paintImageC(pc, width, height, img);
	}
	
	public void paintImageC(Point0 pc, double size, Image img) {
		g2_paintImageC(pc, size, size, img);
	}
	
	public void paintImageC(double width, double height, Image img) {
		paintImageC(p(0,0), width, height, img);
	}
	
	public void paintImageC(double size, Image img) {
		paintImageC(p(0,0), size, size, img);
	}
	
	/*
	 * PAINT RENDERED IMAGE
	 */

	public void paintRenderedImage(Point0 p0, double width, double height, RenderedImage img) {
		g2_paintRenderedImage(p0, width, height, img);
	}

	public void paintRenderedImage(Point0 p0, double size, RenderedImage img) {
		g2_paintRenderedImage(p0, size, size, img);
	}
	
	public void paintRenderedImage(double width, double height, RenderedImage img) {
		paintRenderedImage(p(0,0), width, height, img);
	}
	
	public void paintRenderedImage(double size, RenderedImage img) {
		paintRenderedImage(p(0,0), size, size, img);
	}
	
	/*
	 * PAINT RENDERED IMAGE C
	 */
	
	public void paintRenderedImageC(Point0 pc, double width, double height, RenderedImage img) {
		g2_paintRenderedImageC(pc, width, height, img);
	}
	
	public void paintRenderedImageC(Point0 pc, double size, RenderedImage img) {
		g2_paintRenderedImageC(pc, size, size, img);
	}
	
	public void paintRenderedImageC(double width, double height, RenderedImage img) {
		paintRenderedImageC(p(0,0), width, height, img);
	}
	
	public void paintRenderedImageC(double size, RenderedImage img) {
		paintRenderedImageC(p(0,0), size, size, img);
	}
	
	
	/*
	 * FILL RECT
	 */
	
	public void fillRect(Color color, Point0 p0, double width, double height) {
		g2_setColor(color);
		g2_fillRect(p0, width, height);
	}
	
	public void fillRect(Point0 p0, double width, double height) {
		fillRect(color, p0, width, height);
	}
	
	public void fillRect(Color color, double width, double height) {
		fillRect(color, p(0,0), width, height);
	}
	
	public void fillRect(double width, double height) {
		fillRect(color, p(0,0), width, height);
	}
	
	/*
	 * FILL RECT C
	 */
	
	public void fillRectC(Color color, Point0 pc, double width, double height) {
		g2_setColor(color);
		g2_fillRectC(pc, width, height);
	}
	
	public void fillRectC(Point0 pc, double width, double height) {
		fillRectC(color, pc, width, height);
	}
	
	public void fillRectC(Color color, double width, double height) {
		fillRectC(color, p(0,0), width, height);
	}
	
	public void fillRectC(double width, double height) {
		fillRectC(color, p(0,0), width, height);
	}
	
	/*
	 * FILL OVAL
	 */
	
	public void fillOval(Color color, Point0 p0, double width, double height) {
		g2_setColor(color);
		g2_fillOval(p0, width, height);
	}
	
	public void fillOval(Point0 p0, double width, double height) {
		fillOval(color, p0, width, height);
	}
	
	public void fillOval(Color color, double width, double height) {
		fillOval(color, p(0,0), width, height);
	}
	
	public void fillOval(double width, double height) {
		fillOval(color, p(0,0), width, height);
	}
	
	/*
	 * FILL OVAL C
	 */
	
	public void fillOvalC(Color color, Point0 pc, double width, double height) {
		g2_setColor(color);
		g2_fillOvalC(pc, width, height);
	}
	
	public void fillOvalC(Point0 pc, double width, double height) {
		fillOvalC(color, pc, width, height);
	}
	
	public void fillOvalC(Color color, double width, double height) {
		fillOvalC(color, p(0,0), width, height);
	}
	
	public void fillOvalC(double width, double height) {
		fillOvalC(color, p(0,0), width, height);
	}
	
	/*
	 * FILL SQUARE
	 */
	
	public void fillSquare(Color color, Point0 p0, double c) {
		fillRect(color, p0, c, c);
	}
	
	public void fillSquare(Point0 p0, double c) {
		fillSquare(color, p0, c);
	}
	
	public void fillSquare(Color color, double c) {
		fillSquare(color, p(0, 0), c);
	}
	
	public void fillSquare(double c) {
		fillSquare(color, c);
	}
	
	/*
	 * FILL SQUARE C
	 */
	
	public void fillSquareC(Color color, Point0 pc, double c) {
		fillRectC(color, pc, c, c);
	}
	
	public void fillSquareC(Point0 pc, double c) {
		fillSquareC(color, pc, c);
	}
	
	public void fillSquareC(Color color, double c) {
		fillSquareC(color, p(0, 0), c);
	}
	
	public void fillSquareC(double c) {
		fillSquareC(color, c);
	}
	
	/*
	 * FILL ROUND
	 */
	
	public void fillRound(Color color, Point0 p0, double radius) {
		g2_setColor(color);
		g2_fillOval(p0, radius*2, radius*2);
	}
	
	public void fillRound(Point0 p0, double radius) {
		fillRound(color, p0, radius);
	}
	
	public void fillRound(Color color, double radius) {
		fillRound(color, p(0,0), radius);
	}
	
	public void fillRound(double radius) {
		fillRound(color, p(0,0), radius);
	}
	
	/*
	 * FILL ROUND C
	 */
	
	public void fillRoundC(Paint paint, Point0 pc, double radius) {
		g2_setPaint(paint);
		g2_fillOvalC(pc, radius*2, radius*2);
	}
	
	public void fillRoundC(Paint paint, double radius) {
		fillRoundC(paint, p(0,0), radius);
	}
	
	public void fillRoundC(Color color, Point0 pc, double radius) {
		g2_setColor(color);
		g2_fillOvalC(pc, radius*2, radius*2);
	}
	
	public void fillRoundC(Color color, double radius) {
		fillRoundC(color, p(0,0), radius);
	}
	
	public void fillRoundC(Point0 pc, double radius) {
		fillRoundC(color, pc, radius);
	}
	
	public void fillRoundC(double radius) {
		fillRoundC(color, p(0,0), radius);
	}
	
	/*
	 * FILL ARC C
	 */
	
	public void fillArcC(Paint paint, Point0 pc, double radius, Angle angle1, Angle angle2) {
		g2_setPaint(paint);
		g2_fillArcC(pc, radius*2, radius*2, angle1, angle2);
	}
	
	/*
	 * DRAW LINE
	 */
	
	public void drawLine(Color color, Point0 p1, Point0 p2) {
		g2_setColor(color);
		g2_drawLine(p1, p2);
	}
	
	public void drawLine(Point0 p1, Point0 p2) {
		drawLine(color, p1, p2);
	}
	
	public void drawLine(Color color, Point0 p2) {
		drawLine(color, p(0,0), p2);
	}
	
	public void drawLine(Point0 p2) {
		drawLine(color, p2);
	}
	
	/*
	 * DRAW ARROW
	 */
	
	public void drawArrow(Color color, Point0 p1, Point0 p2) {
		g2_setColor(color);
		g2_drawArrow(p1, p2);
	}
	
	public void drawArrow(Point0 p1, Point0 p2) {
		drawArrow(color, p1, p2);
	}
	
	public void drawArrow(Color color, Point0 p2) {
		drawArrow(color, p(0,0), p2);
	}
	
	public void drawArrow(Point0 p2) {
		drawArrow(color, p2);
	}
	
	/*
	 * DRAW ARROW (R)
	 */
	
	public void drawArrow(Color color, Point0 p1, Point0 p2, double r) {
		g2_setColor(color);
		g2_drawArrow(p1, p2, r);
	}
	
	public void drawArrow(Point0 p1, Point0 p2, double r) {
		drawArrow(color, p1, p2, r);
	}
	
	public void drawArrow(Color color, Point0 p2, double r) {
		drawArrow(color, p(0,0), p2, r);
	}
	
	public void drawArrow(Point0 p2, double r) {
		drawArrow(color, p2, r);
	}
	
	/*
	 * DRAW THICK LINE
	 * TODO mieux gérer l'épaisseur du trait avec les arrondis
	 */
	
	public void drawThickLine(Color color, Point0 p1, Point0 p2, double thickness) {
		Angle a = p2.pSub(p1).angle();
		Angle a1 = a.add90();
		double d = thickness*0.5;
		
		Point0 p1a = p1.pAdd(d, a1);
		Point0 p1b = p1.pAdd(-d, a1);
		
		Point0 p2a = p2.pAdd(d, a1);
		Point0 p2b = p2.pAdd(-d, a1);
		
		g2_setColor(color);
		g2_fillPoly(p1a, p1b, p2b, p2a);
	}
	
	public void drawThickLine(Point0 p1, Point0 p2, double thickness) {
		drawThickLine(color, p1, p2, thickness);
	}
	
	public void drawThickLine(Color color, Point0 p2, double thickness) {
		drawThickLine(color, p(0,0), p2, thickness);
	}
	
	public void drawThickLine(Point0 p2, double thickness) {
		drawThickLine(color, p2, thickness);
	}
	
	/*
	 * DRAW STAR
	 */
	
	public void drawStar(Color color, Point0 p0, double radius, int number) {
		drawStarC(color, p0.pAdd(radius), radius, number);
	}
	
	public void drawStar(Point0 pc, double radius, int number) {
		drawStar(color, pc, radius, number);
	}
	
	public void drawStar(Color color, double radius, int number) {
		drawStar(color, p(0, 0), radius, number);
	}
	
	public void drawStar(double radius, int number) {
		drawStar(color, radius, number);
	}
	
	/*
	 * DRAW STAR C
	 */
	
	public void drawStarC(Color color, Point0 pc, double radius, int number) {
		Angle angle0 = Angle.anglePart(number);
		if(angle0==null) return;
		for(int i=0;i<number;i++) {
			Point1 p = pc.pAdd(radius, angle0.mult(i));
			drawLine(color, pc, p);
		}
	}
	
	public void drawStarC(Point0 pc, double radius, int number) {
		drawStarC(color, pc, radius, number);
	}
	
	public void drawStarC(Color color, double radius, int number) {
		drawStarC(color, p(0, 0), radius, number);
	}
	
	public void drawStarC(double radius, int number) {
		drawStarC(color, radius, number);
	}
	
	/*
	 * DRAW STRING
	 */
	
	public void drawString(Color color, Font font, Point0 p, String text) {
		g2_setColor(color);
		g2_setFont(font);
		g2_drawString(p, text);
	}
	
	public void drawString(Color color, Point0 p, String text) {
		drawString(color, font, p, text);
	}
	
	public void drawString(Font font, Point0 p, String text) {
		drawString(color, font, p, text);
	}
	
	public void drawString(Point0 p, String text) {
		drawString(color, font, p, text);
	}
	
	public void drawString(String text) {
		drawString(p(0,0), text);
	}
	
	/*
	 * DRAW STRING C
	 */
	
	public void drawStringC(Color color, Font font, Point0 p, String text) {
		g2_setColor(color);
		g2_setFont(font);
		
		FontMetrics fm = getFontMetrics();
		int width = fm.stringWidth(text);
		int height = fm.getAscent();
		
		Point0 p1 = p.pAdd(-width*0.5, height*0.5);
		g2_drawString(p1, text);
		
	}
	
	public void drawStringC(Color color, Point0 p, String text) {
		drawStringC(color, font, p, text);
	}
	
	public void drawStringC(Font font, Point0 p, String text) {
		drawStringC(color, font, p, text);
	}
	
	public void drawStringC(Point0 p, String text) {
		drawStringC(color, font, p, text);
	}
	
	public void drawStringC(String text) {
		drawStringC(p(0,0), text);
	}
	
	/*
	 * BUILD CIRCULAR GRADIENT
	 */
	

	
	public Paint buildCircularGradient(double radius, Color color1, Color color2) {
		return buildCircularGradient(p(0,0), radius, color1, color2);
	}
	
	public Paint buildCircularGradient(Point0 pc, double radius, Color color1, Color color2) {
		pc = alterPoint(pc);
		radius = alterDistance(radius);
		
		Point2D p1 = new Point2D.Double(pc.getX(), pc.getY());
		return new RadialGradientPaint(p1, (float) radius, new float[] {0,1}, new Color[] {color1, color2});
	}
}
