package gus.game5.core.drawing;

import java.awt.Graphics2D;

import gus.game5.core.draw.Draw;
import gus.game5.core.features.g.GBool;

public abstract class Drawing0 extends G2Holder implements Draw {
	
	protected abstract void draw();
	
	public void draw(Graphics2D g2) {
		setG2(g2);
		if(drawable()) draw();
	}
	
	/*
	 * DRAWABLE
	 */

	private GBool drawable;
	
	public void setDrawable(GBool drawable) {
		this.drawable = drawable;
	}
	
	public void setDrawable(boolean value) {
		this.drawable = ()->value;
	}
	
	public GBool getDrawable() {
		return drawable;
	}
	
	public boolean drawable() {
		return drawable==null || drawable.gBool();
	}
}

