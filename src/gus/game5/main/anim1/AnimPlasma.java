package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class AnimPlasma extends Game1 {

	public static final long SLEEP = 30;
	public static final int CELL = 12;
	public static final double TIME_STEP = 0.04;

	public static void main(String[] args) {
		AnimPlasma main = new AnimPlasma();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Plasma");
		s.setWidth(900);
		s.setHeight(700);
		s.setSleep(SLEEP);
		s.setBackground(Color.BLACK);
	}

	/*
	 * DATA
	 */

	private double t;

	protected void initialize1() {
		t = 0;
		addDraw(new Field());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();
		t += TIME_STEP;
	}

	/*
	 * FIELD
	 */

	private class Field extends Drawing1 {

		protected void draw() {
			double cx = gameWidth()/2.0;
			double cy = gameHeight()/2.0;

			for(int gx=0;gx<gameWidth();gx+=CELL)
			for(int gy=0;gy<gameHeight();gy+=CELL) {
				double dx = gx-cx;
				double dy = gy-cy;
				double dist = Math.sqrt(dx*dx+dy*dy);

				double v = Math.sin(gx*0.018+t)
						+ Math.sin(gy*0.022+t*1.3)
						+ Math.sin((gx+gy)*0.014+t*0.7)
						+ Math.sin(dist*0.03-t*1.4);
				double hue = (v/4+1)/2;

				fillRect(Color.getHSBColor((float) hue, 1f, 1f), p(gx, gy), CELL, CELL);
			}
		}
	}
}
