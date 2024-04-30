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
	
	protected Color color = Color.BLACK;
	
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	
	protected Color c(int r, int g, int b) {
		return new Color(r, g, b);
	}
	
	/*
	 * POINT 0
	 */
	
	protected Point0 p(double x, double y) {
		return new Point1(x, y);
	}
	
	protected Point0 avg(Point0... points) {
		return new Point0Avg(points);
	}
	
	protected Point0 sum(Point0... points) {
		return new Point0Sum(points).p1();
	}
	
	/*
	 * STRUCTURE
	 */
	
	protected Point1List structure = new Point1List();
	
	public Point1List getStructure() {return structure;}
	
	protected Point1 addStruct(Point0 p0) {
		Point1 p = new Point1(p0);
		structure.add(p);
		return p;
	}
	
	protected Point1 addStruct(double x, double y) {
		Point1 p = new Point1(x,y);
		structure.add(p);
		return p;
	}
	
	/*
	 * FONT
	 */
	
	protected Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
	
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
	
	protected void drawPoly(Color color, Point0... pp) {
		g2_setColor(color);
		g2_drawPoly(pp);
	}
	
	protected void drawPoly(Point0... pp) {
		drawPoly(color, pp);
	}
	
	/*
	 * DRAW RECT
	 */
	
	protected void drawRect(Color color, Point0 p0, double width, double height) {
		g2_setColor(color);
		g2_drawRect(p0, width, height);
	}
	
	protected void drawRect(Color color, double width, double height) {
		drawRect(color, p(0,0), width, height);
	}
	
	protected void drawRect(Point0 p0, double width, double height) {
		drawRect(color, p0, width, height);
	}
	
	protected void drawRect(double width, double height) {
		drawRect(color, p(0,0), width, height);
	}
	
	/*
	 * DRAW RECT C
	 */
	
	protected void drawRectC(Color color, Point0 pc, double width, double height) {
		g2_setColor(color);
		g2_drawRectC(pc, width, height);
	}
	
	protected void drawRectC(Color color, double width, double height) {
		drawRectC(color, p(0,0), width, height);
	}
	
	protected void drawRectC(Point0 pc, double width, double height) {
		drawRectC(color, pc, width, height);
	}
	
	protected void drawRectC(double width, double height) {
		drawRectC(color, p(0,0), width, height);
	}
	
	/*
	 * DRAW POINT
	 */
	
	protected void drawPoint(Color color, Point0 p) {
		drawSquare(color, p, 1);
	}
	
	protected void drawPoint(Point0 p) {
		drawPoint(color, p);
	}
	
	/*
	 * DRAW SQUARE
	 */
	
	protected void drawSquare(Color color, Point0 p0, double c) {
		drawRect(color, p0, c, c);
	}
	
	protected void drawSquare(Color color, double c) {
		drawSquare(color, p(0, 0), c);
	}
	
	protected void drawSquare(Point0 p0, double c) {
		drawSquare(color, p0, c);
	}
	
	protected void drawSquare(double c) {
		drawSquare(color, p(0, 0), c);
	}
	
	/*
	 * DRAW SQUARE C
	 */
	
	protected void drawSquareC(Color color, Point0 pc, double c) {
		drawRectC(color, pc, c, c);
	}
	
	protected void drawSquareC(Color color, double c) {
		drawSquareC(color, p(0, 0), c);
	}
	
	protected void drawSquareC(Point0 pc, double c) {
		drawSquareC(color, pc, c);
	}
	
	protected void drawSquareC(double c) {
		drawSquareC(color, p(0, 0), c);
	}
	
	/*
	 * DRAW OVAL
	 */
	
	protected void drawOval(Color color, Point0 p0, double width, double height) {
		g2_setColor(color);
		g2_drawOval(p0, width, height);
	}
	
	protected void drawOval(Color color, double width, double height) {
		drawOval(color, p(0,0), width, height);
	}
	
	protected void drawOval(Point0 p0, double width, double height) {
		drawOval(color, p0, width, height);
	}
	
	protected void drawOval(double width, double height) {
		drawOval(color, p(0,0), width, height);
	}
	
	/*
	 * DRAW OVAL C
	 */
	
	protected void drawOvalC(Color color, Point0 pc, double width, double height) {
		g2_setColor(color);
		g2_drawOvalC(pc, width, height);
	}
	
	protected void drawOvalC(Color color, double width, double height) {
		drawOvalC(color, p(0,0), width, height);
	}
	
	protected void drawOvalC(Point0 pc, double width, double height) {
		drawOvalC(color, pc, width, height);
	}
	
	protected void drawOvalC(double width, double height) {
		drawOvalC(color, p(0,0), width, height);
	}
	
	/*
	 * DRAW ROUND
	 */
	
	protected void drawRound(Color color, Point0 p0, double radius) {
		drawOval(color, p0, radius*2, radius*2);
	}
	
	protected void drawRound(Point0 p0, double radius) {
		drawRound(color, p0, radius);
	}
	
	protected void drawRound(Color color, double radius) {
		drawRound(color, p(0, 0), radius);
	}
	
	protected void drawRound(double radius) {
		drawRound(color, radius);
	}
	
	/*
	 * DRAW ROUND C
	 */
	
	protected void drawRoundC(Color color, Point0 pc, double radius) {
		drawOvalC(color, pc, radius*2, radius*2);
	}
	
	protected void drawRoundC(Point0 pc, double radius) {
		drawRoundC(color, pc, radius);
	}
	
	protected void drawRoundC(Color color, double radius) {
		drawRoundC(color, p(0, 0), radius);
	}
	
	protected void drawRoundC(double radius) {
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
	
	protected void fillPoly(Color color, Point0... pp) {
		g2_setColor(color);
		g2_fillPoly(pp);
	}
	
	protected void fillPoly(Point0... pp) {
		fillPoly(color, pp);
	}
	
	/*
	 * PAINT IMAGE
	 */

	protected void paintImage(Point0 p0, double width, double height, Image img) {
		g2_paintImage(p0, width, height, img);
	}
	
	protected void paintImage(double width, double height, Image img) {
		paintImage(p(0,0), width, height, img);
	}
	
	/*
	 * PAINT IMAGE C
	 */
	
	protected void paintImageC(Point0 pc, double width, double height, Image img) {
		g2_paintImageC(pc, width, height, img);
	}
	
	protected void paintImageC(double width, double height, Image img) {
		paintImageC(p(0,0), width, height, img);
	}
	
	/*
	 * PAINT RENDERED IMAGE
	 */

	protected void paintRenderedImage(Point0 p0, double width, double height, RenderedImage img) {
		g2_paintRenderedImage(p0, width, height, img);
	}
	
	protected void paintRenderedImage(double width, double height, RenderedImage img) {
		paintRenderedImage(p(0,0), width, height, img);
	}
	
	/*
	 * PAINT RENDERED IMAGE C
	 */
	
	protected void paintRenderedImageC(Point0 pc, double width, double height, RenderedImage img) {
		g2_paintRenderedImageC(pc, width, height, img);
	}
	
	protected void paintRenderedImageC(double width, double height, RenderedImage img) {
		paintRenderedImageC(p(0,0), width, height, img);
	}
	
	
	/*
	 * FILL RECT
	 */
	
	protected void fillRect(Color color, Point0 p0, double width, double height) {
		g2_setColor(color);
		g2_fillRect(p0, width, height);
	}
	
	protected void fillRect(Point0 p0, double width, double height) {
		fillRect(color, p0, width, height);
	}
	
	protected void fillRect(Color color, double width, double height) {
		fillRect(color, p(0,0), width, height);
	}
	
	protected void fillRect(double width, double height) {
		fillRect(color, p(0,0), width, height);
	}
	
	/*
	 * FILL RECT C
	 */
	
	protected void fillRectC(Color color, Point0 pc, double width, double height) {
		g2_setColor(color);
		g2_fillRectC(pc, width, height);
	}
	
	protected void fillRectC(Point0 pc, double width, double height) {
		fillRectC(color, pc, width, height);
	}
	
	protected void fillRectC(Color color, double width, double height) {
		fillRectC(color, p(0,0), width, height);
	}
	
	protected void fillRectC(double width, double height) {
		fillRectC(color, p(0,0), width, height);
	}
	
	/*
	 * FILL OVAL
	 */
	
	protected void fillOval(Color color, Point0 p0, double width, double height) {
		g2_setColor(color);
		g2_fillOval(p0, width, height);
	}
	
	protected void fillOval(Point0 p0, double width, double height) {
		fillOval(color, p0, width, height);
	}
	
	protected void fillOval(Color color, double width, double height) {
		fillOval(color, p(0,0), width, height);
	}
	
	protected void fillOval(double width, double height) {
		fillOval(color, p(0,0), width, height);
	}
	
	/*
	 * FILL OVAL C
	 */
	
	protected void fillOvalC(Color color, Point0 pc, double width, double height) {
		g2_setColor(color);
		g2_fillOvalC(pc, width, height);
	}
	
	protected void fillOvalC(Point0 pc, double width, double height) {
		fillOvalC(color, pc, width, height);
	}
	
	protected void fillOvalC(Color color, double width, double height) {
		fillOvalC(color, p(0,0), width, height);
	}
	
	protected void fillOvalC(double width, double height) {
		fillOvalC(color, p(0,0), width, height);
	}
	
	/*
	 * FILL SQUARE
	 */
	
	protected void fillSquare(Color color, Point0 p0, double c) {
		fillRect(color, p0, c, c);
	}
	
	protected void fillSquare(Point0 p0, double c) {
		fillSquare(color, p0, c);
	}
	
	protected void fillSquare(Color color, double c) {
		fillSquare(color, p(0, 0), c);
	}
	
	protected void fillSquare(double c) {
		fillSquare(color, c);
	}
	
	/*
	 * FILL SQUARE C
	 */
	
	protected void fillSquareC(Color color, Point0 pc, double c) {
		fillRectC(color, pc, c, c);
	}
	
	protected void fillSquareC(Point0 pc, double c) {
		fillSquareC(color, pc, c);
	}
	
	protected void fillSquareC(Color color, double c) {
		fillSquareC(color, p(0, 0), c);
	}
	
	protected void fillSquareC(double c) {
		fillSquareC(color, c);
	}
	
	/*
	 * FILL ROUND
	 */
	
	protected void fillRound(Color color, Point0 p0, double radius) {
		g2_setColor(color);
		g2_fillOval(p0, radius*2, radius*2);
	}
	
	protected void fillRound(Point0 p0, double radius) {
		fillRound(color, p0, radius);
	}
	
	protected void fillRound(Color color, double radius) {
		fillRound(color, p(0,0), radius);
	}
	
	protected void fillRound(double radius) {
		fillRound(color, p(0,0), radius);
	}
	
	/*
	 * FILL ROUND C
	 */
	
	protected void fillRoundC(Paint paint, Point0 pc, double radius) {
		g2_setPaint(paint);
		g2_fillOvalC(pc, radius*2, radius*2);
	}
	
	protected void fillRoundC(Paint paint, double radius) {
		fillRoundC(paint, p(0,0), radius);
	}
	
	protected void fillRoundC(Color color, Point0 pc, double radius) {
		g2_setColor(color);
		g2_fillOvalC(pc, radius*2, radius*2);
	}
	
	protected void fillRoundC(Color color, double radius) {
		fillRoundC(color, p(0,0), radius);
	}
	
	protected void fillRoundC(Point0 pc, double radius) {
		fillRoundC(color, pc, radius);
	}
	
	protected void fillRoundC(double radius) {
		fillRoundC(color, p(0,0), radius);
	}
	
	/*
	 * FILL ARC C
	 */
	
	protected void fillArcC(Paint paint, Point0 pc, double radius, Angle angle1, Angle angle2) {
		g2_setPaint(paint);
		g2_fillArcC(pc, radius*2, radius*2, angle1, angle2);
	}
	
	/*
	 * DRAW LINE
	 */
	
	protected void drawLine(Color color, Point0 p1, Point0 p2) {
		g2_setColor(color);
		g2_drawLine(p1, p2);
	}
	
	protected void drawLine(Point0 p1, Point0 p2) {
		drawLine(color, p1, p2);
	}
	
	protected void drawLine(Color color, Point0 p2) {
		drawLine(color, p(0,0), p2);
	}
	
	protected void drawLine(Point0 p2) {
		drawLine(color, p2);
	}
	
	/*
	 * DRAW THICK LINE
	 * TODO mieux gérer l'épaisseur du trait avec les arrondis
	 */
	
	protected void drawThickLine(Color color, Point0 p1, Point0 p2, double thickness) {
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
	
	protected void drawThickLine(Point0 p1, Point0 p2, double thickness) {
		drawThickLine(color, p1, p2, thickness);
	}
	
	protected void drawThickLine(Color color, Point0 p2, double thickness) {
		drawThickLine(color, p(0,0), p2, thickness);
	}
	
	protected void drawThickLine(Point0 p2, double thickness) {
		drawThickLine(color, p2, thickness);
	}
	
	/*
	 * DRAW STAR
	 */
	
	protected void drawStar(Color color, Point0 p0, double radius, int number) {
		drawStarC(color, p0.pAdd(radius), radius, number);
	}
	
	protected void drawStar(Point0 pc, double radius, int number) {
		drawStar(color, pc, radius, number);
	}
	
	protected void drawStar(Color color, double radius, int number) {
		drawStar(color, p(0, 0), radius, number);
	}
	
	protected void drawStar(double radius, int number) {
		drawStar(color, radius, number);
	}
	
	/*
	 * DRAW STAR C
	 */
	
	protected void drawStarC(Color color, Point0 pc, double radius, int number) {
		Angle angle0 = Angle.anglePart(number);
		if(angle0==null) return;
		for(int i=0;i<number;i++) {
			Point1 p = pc.pAdd(radius, angle0.mult(i));
			drawLine(color, pc, p);
		}
	}
	
	protected void drawStarC(Point0 pc, double radius, int number) {
		drawStarC(color, pc, radius, number);
	}
	
	protected void drawStarC(Color color, double radius, int number) {
		drawStarC(color, p(0, 0), radius, number);
	}
	
	protected void drawStarC(double radius, int number) {
		drawStarC(color, radius, number);
	}
	
	/*
	 * DRAW STRING
	 */
	
	protected void drawString(Color color, Font font, Point0 p, String text) {
		g2_setColor(color);
		g2_setFont(font);
		g2_drawString(p, text);
	}
	
	protected void drawString(Color color, Point0 p, String text) {
		drawString(color, font, p, text);
	}
	
	protected void drawString(Font font, Point0 p, String text) {
		drawString(color, font, p, text);
	}
	
	protected void drawString(Point0 p, String text) {
		drawString(color, font, p, text);
	}
	
	protected void drawString(String text) {
		drawString(p(0,0), text);
	}
	
	/*
	 * DRAW STRING C
	 */
	
	protected void drawStringC(Color color, Font font, Point0 p, String text) {
		g2_setColor(color);
		g2_setFont(font);
		
		FontMetrics fm = getFontMetrics();
		int width = fm.stringWidth(text);
		int height = fm.getAscent();
		
//		GlyphVector gv = font.createGlyphVector(getFontRenderContext(), text);
//		Rectangle rect = gv.getPixelBounds(null, 0, 0);
//		int height = rect.height;
//		int width = rect.width;
		
		Point0 p1 = p.pAdd(-width*0.5, height*0.5);
		g2_drawString(p1, text);
		
	}
	
	protected void drawStringC(Color color, Point0 p, String text) {
		drawStringC(color, font, p, text);
	}
	
	protected void drawStringC(Font font, Point0 p, String text) {
		drawStringC(color, font, p, text);
	}
	
	protected void drawStringC(Point0 p, String text) {
		drawStringC(color, font, p, text);
	}
	
	protected void drawStringC(String text) {
		drawStringC(p(0,0), text);
	}
	
	/*
	 * BUILD CIRCULAR GRADIENT
	 */
	

	
	protected Paint buildCircularGradient(double radius, Color color1, Color color2) {
		return buildCircularGradient(p(0,0), radius, color1, color2);
	}
	
	protected Paint buildCircularGradient(Point0 pc, double radius, Color color1, Color color2) {
		pc = alterPoint(pc);
		radius = alterDistance(radius);
		
		Point2D p1 = new Point2D.Double(pc.getX(), pc.getY());
		return new RadialGradientPaint(p1, (float) radius, new float[] {0,1}, new Color[] {color1, color2});
	}
}
