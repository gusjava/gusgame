package gus.game5.main.game.p1.space;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.Shape0;

public class Town extends Shape0 {
	public static double RADIUS1 = 16;
	public static double RADIUS2 = 6;
	public static Color COLOR_SELECTED = Color.BLUE;
	
	private int level = 0;
	private boolean selected = false;

	public Town(int x, int y) {
		super(new Point2(x,y), RADIUS1*2, RADIUS1*2);
	}
	
	public void goNext() {
		super.goNext();
	}
	
	public void upgrade() {
		level++;
	}
	
	public void downgrade() {
		if(level>0)
		level--;
	}
	
	protected void drawShape() {
		fillRoundC(selected ? COLOR_SELECTED : Color.BLACK, RADIUS2);
		drawRoundC(selected ? COLOR_SELECTED : Color.GRAY, RADIUS1);
		fillRoundC(Color.WHITE, RADIUS2-2);
		
		Angle a = Angle.angleRad(count*0.1);
		
		for(int i=0;i<level;i++) {
			fillRoundC(selected ? COLOR_SELECTED : Color.GRAY, a.pointAt(RADIUS1), RADIUS2);
			a = a.add(Math.PI*2 / level);
		}
	}
	
	/*
	 * SELECTED
	 */
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void unselect() {
		setSelected(false);
	}
	
	public void select() {
		setSelected(true);
	}
}
