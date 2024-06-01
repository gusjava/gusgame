package gus.game5.core.drawing;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.font.FontRenderContext;
import java.awt.geom.Arc2D;
import java.awt.image.RenderedImage;

import gus.game5.core.alter.Alter;
import gus.game5.core.alter.AlterList;
import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.util.UtilDistance;
import gus.game5.core.util.UtilImage;

public class G2Holder {

	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}
	
	/*
	 * ALTER
	 */
	
	private AlterList alterList = new AlterList();
	
	public void addAlter(Alter alter) {
		alterList.add(alter);
	}
	
	public void removeAlter(Alter alter) {
		alterList.remove(alter);
	}
	
	protected Point0 alterPoint(Point0 p) {
		return alterList.alterPoint(p);
	}
	
	protected double alterDistance(double dist) {
		return alterList.alterDistance(dist);
	}
	
	
	/*
	 * G2
	 */
	
	private Graphics2D g2;
	
	protected Graphics2D g2() {
		return g2;
	}
	
	/*
	 * G2 : COLOR
	 */
	
	protected void g2_setColor(Color color) {
		g2.setColor(color);
	}
	
	protected Color g2_getColor() {
		return g2.getColor();
	}
	
	/*
	 * G2 : PAINT
	 */
	
	protected void g2_setPaint(Paint paint) {
		g2.setPaint(paint);
	}
	
	protected Paint g2_getPaint() {
		return g2.getPaint();
	}
	
	/*
	 * G2 : FONT
	 */
	
	protected void g2_setFont(Font font) {
		g2.setFont(font);
	}
	
	protected Font g2_getFont() {
		return g2.getFont();
	}
	
	/*
	 * G2 : COMPOSITE
	 */
	
	protected void g2_setComposite(Composite composite) {
		g2.setComposite(composite);
	}
	
	protected Composite g2_getComposite() {
		return g2.getComposite();
	}
	
	/*
	 * G2 : POLY
	 */
	
	protected void g2_fillPoly(Point0... pp) {
		g2.fillPolygon(buildPolygon(pp));
	}
	
	protected void g2_drawPoly(Point0... pp) {
		g2.drawPolygon(buildPolygon(pp));
	}
	
	private Polygon buildPolygon(Point0... pp) {
		int nb = pp.length;
		int[] xpoints = new int[nb];
		int[] ypoints = new int[nb];
		for(int i=0;i<nb;i++) {
			Point0 p = alterPoint(pp[i]);
			xpoints[i] = (int) p.getX();
			ypoints[i] = (int) p.getY();
		}
		return new Polygon(xpoints, ypoints, nb);
	}
	
	/*
	 * G2 : LINE
	 */
	
	protected void g2_drawLine(Point0 p1, Point0 p2) {
		p1 = alterPoint(p1);
		p2 = alterPoint(p2);
		
		if(p1!=null && p2!=null)
		g2.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
	}
	
	/*
	 * G2 : ARROW
	 */
	
	protected void g2_drawArrow(Point0 p1, Point0 p2) {
		g2_drawArrow(p1, p2, 8);
	}
	
	protected void g2_drawArrow(Point0 p1, Point0 p2, double r) {
		p1 = alterPoint(p1);
		p2 = alterPoint(p2);
		
		int x1s = (int) p1.getX();
		int y1s = (int) p1.getY();
		int x2s = (int) p2.getX();
		int y2s = (int) p2.getY();
		
		g2.drawLine(x1s, y1s, x2s, y2s);
		
		if(x1s==x2s && y1s==y2s) return;
		double d = UtilDistance.dist(x1s, y1s, x2s, y2s);
		double k = r/d;
		
		int xa = (int) (x2s + k*(x1s-x2s+y1s-y2s));
		int ya = (int) (y2s + k*(x2s-x1s+y1s-y2s));
		
		int xb = (int) (x2s + k*(x1s-x2s+y2s-y1s));
		int yb = (int) (y2s + k*(x1s-x2s+y1s-y2s));
		
		g2.drawLine(x2s, y2s, xa, ya);
		g2.drawLine(x2s, y2s, xb, yb);
	}
	
	/*
	 * G2 : STRING
	 */
	
	protected void g2_drawString(Point0 p, String text) {
		p = alterPoint(p);
		g2.drawString(text, (int) p.getX(), (int) p.getY());
	}
	
	/*
	 * G2 : FONT METRICS
	 */
	
	protected FontMetrics getFontMetrics() {
		return g2.getFontMetrics();
	}
	
	protected FontMetrics getFontMetrics(Font font) {
		return g2.getFontMetrics(font);
	}
	
	/*
	 * G2 : FONT RENDER CONTEXT
	 */
	
	protected FontRenderContext getFontRenderContext() {
		return g2.getFontRenderContext();
	}
	
	/*
	 * G2 IMAGE
	 */
	
	protected void g2_paintImage(Point0 p0, double w, double h, Image img) {
		p0 = alterPoint(p0);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (p0.getX());
		int x2 = (int) (p0.getX()+w);
		int y1 = (int) (p0.getY());
		int y2 = (int) (p0.getY()+h);
		UtilImage.paint(g2, Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h), img);
	}
	
	protected void g2_paintImageC(Point0 pc, double w, double h, Image img) {
		pc = alterPoint(pc);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (pc.getX()-w/2);
		int x2 = (int) (pc.getX()+w/2);
		int y1 = (int) (pc.getY()-h/2);
		int y2 = (int) (pc.getY()+h/2);
		UtilImage.paint(g2, Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h), img);
	}
	
	/*
	 * G2 RENDERED IMAGE
	 */
	
	protected void g2_paintRenderedImage(Point0 p0, double w, double h, RenderedImage img) {
		p0 = alterPoint(p0);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (p0.getX());
		int x2 = (int) (p0.getX()+w);
		int y1 = (int) (p0.getY());
		int y2 = (int) (p0.getY()+h);
		UtilImage.paint(g2, Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h), img);
	}
	
	protected void g2_paintRenderedImageC(Point0 pc, double w, double h, RenderedImage img) {
		pc = alterPoint(pc);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (pc.getX()-w/2);
		int x2 = (int) (pc.getX()+w/2);
		int y1 = (int) (pc.getY()-h/2);
		int y2 = (int) (pc.getY()+h/2);
		UtilImage.paint(g2, Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h), img);
	}
	
	
	/*
	 * G2 : FILL RECT
	 */
	
	protected void g2_fillRect(Point0 p0, double w, double h) {
		p0 = alterPoint(p0);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (p0.getX());
		int x2 = (int) (p0.getX()+w);
		int y1 = (int) (p0.getY());
		int y2 = (int) (p0.getY()+h);
		g2.fillRect(Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h));
	}
	
	protected void g2_fillRectC(Point0 pc, double w, double h) {
		pc = alterPoint(pc);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (pc.getX()-w/2);
		int x2 = (int) (pc.getX()+w/2);
		int y1 = (int) (pc.getY()-h/2);
		int y2 = (int) (pc.getY()+h/2);
		g2.fillRect(Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h));
	}
	
	protected void g2_fillRectDebug(Point0 pc, double w, double h) {
		g2.fillRect((int) pc.getX(), (int) pc.getY(), (int) w, (int) h);
	}
	
	/*
	 * G2 : FILL OVAL
	 */
	
	protected void g2_fillOval(Point0 p0, double w, double h) {
		p0 = alterPoint(p0);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (p0.getX());
		int x2 = (int) (p0.getX()+w);
		int y1 = (int) (p0.getY());
		int y2 = (int) (p0.getY()+h);
		g2.fillOval(Math.min(x1,x2), Math.min(y1,y2), Math.abs(x2-x1), Math.abs(y2-y1));
	}
	
	protected void g2_fillOvalC(Point0 pc, double w, double h) {
		pc = alterPoint(pc);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (pc.getX()-w/2);
		int x2 = (int) (pc.getX()+w/2);
		int y1 = (int) (pc.getY()-h/2);
		int y2 = (int) (pc.getY()+h/2);
		
		g2.fillOval(Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h));
	}
	
	protected void g2_fillOvalDebug(Point0 pc, double w, double h) {
		g2.fillOval((int) pc.getX(), (int) pc.getY(), (int) w, (int) h);
	}
	
	/*
	 * G2 : FILL ARC C
	 */
	
	protected void g2_fillArcC(Point0 pc, double w, double h, Angle angle1, Angle angle2) {
		pc = alterPoint(pc);
		w = alterDistance(w);
		h = alterDistance(h);

		double x1 = pc.getX()-w/2;
		double x2 = pc.getX()+w/2;
		double y1 = pc.getY()-h/2;
		double y2 = pc.getY()+h/2;
		
		Arc2D arc = new Arc2D.Double(
				Math.min(x1,x2), 
				Math.min(y1,y2), 
				Math.abs((int) w), 
				Math.abs((int) h),
				-angle1.getValueDeg(),
				-angle2.getValueDeg(),
				Arc2D.PIE);
		
		g2.fill(arc);
	}
	
	/*
	 * G2 : DRAW RECT
	 */
	
	protected void g2_drawRect(Point0 p0, double w, double h) {
		p0 = alterPoint(p0);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (p0.getX());
		int x2 = (int) (p0.getX()+w);
		int y1 = (int) (p0.getY());
		int y2 = (int) (p0.getY()+h);
		g2.drawRect(Math.min(x1,x2), Math.min(y1,y2), Math.abs(x2-x1), Math.abs(y2-y1));
	}
	
	protected void g2_drawRectC(Point0 pc, double w, double h) {
		pc = alterPoint(pc);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (pc.getX()-w/2);
		int x2 = (int) (pc.getX()+w/2);
		int y1 = (int) (pc.getY()-h/2);
		int y2 = (int) (pc.getY()+h/2);
		g2.drawRect(Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h));
	}
	
	/*
	 * G2 : DRAW OVAL
	 */
	
	protected void g2_drawOval(Point0 p0, double w, double h) {
		p0 = alterPoint(p0);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (p0.getX());
		int x2 = (int) (p0.getX()+w);
		int y1 = (int) (p0.getY());
		int y2 = (int) (p0.getY()+h);
		g2.drawOval(Math.min(x1,x2), Math.min(y1,y2), Math.abs(x2-x1), Math.abs(y2-y1));
	}
	
	protected void g2_drawOvalC(Point0 pc, double w, double h) {
		pc = alterPoint(pc);
		w = alterDistance(w);
		h = alterDistance(h);
		
		int x1 = (int) (pc.getX()-w/2);
		int x2 = (int) (pc.getX()+w/2);
		int y1 = (int) (pc.getY()-h/2);
		int y2 = (int) (pc.getY()+h/2);
		g2.drawOval(Math.min(x1,x2), Math.min(y1,y2), Math.abs((int) w), Math.abs((int) h));
	}
	
	/*
	 * G2 : DRAW ARC C
	 */
	
	protected void g2_drawArcC(Point0 pc, double w, double h, Angle angle1, Angle angle2) {
		pc = alterPoint(pc);
		w = alterDistance(w);
		h = alterDistance(h);

		double x1 = pc.getX()-w/2;
		double x2 = pc.getX()+w/2;
		double y1 = pc.getY()-h/2;
		double y2 = pc.getY()+h/2;
		
		Arc2D arc = new Arc2D.Double(
				Math.min(x1,x2), 
				Math.min(y1,y2), 
				Math.abs((int) w), 
				Math.abs((int) h),
				-angle1.getValueDeg(),
				-angle2.getValueDeg(),
				Arc2D.OPEN);
		
		g2.draw(arc);
	}
}

