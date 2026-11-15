package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimCyclicCA extends Game2 {

	public static final int CELL = 6;
	public static final int GW = 130;
	public static final int GH = 95;
	public static final int SIZE = GW*GH;
	public static final int STATES = 5;
	public static final int THRESHOLD = 3;

	public static void main(String[] args) {
		AnimCyclicCA main = new AnimCyclicCA();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Cyclic cellular automaton animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(30);
	}

	protected void initialize1() {
		Automaton automaton = new Automaton();
		addDraw(automaton);
		addDyn(automaton);
	}

	/*
	 * AUTOMATON
	 */

	private class Automaton extends Drawing1 implements Dyn {
		private final int[] state = new int[SIZE];
		private final int[] next = new int[SIZE];
		private final int[] palette = new int[STATES];
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		public Automaton() {
			for(int i=0;i<STATES;i++) palette[i] = Color.getHSBColor(i/(float) STATES, 1, 1).getRGB();
			for(int c=0;c<SIZE;c++) state[c] = UtilRandom.randomInt(STATES);
		}

		public void goNext() {
			for(int x=0;x<GW;x++)
			for(int y=0;y<GH;y++) {
				int c = y*GW+x;
				int current = state[c];
				int target = (current+1)%STATES;
				int count = 0;

				count += countIfTarget(x-1, y-1, target);
				count += countIfTarget(x, y-1, target);
				count += countIfTarget(x+1, y-1, target);
				count += countIfTarget(x-1, y, target);
				count += countIfTarget(x+1, y, target);
				count += countIfTarget(x-1, y+1, target);
				count += countIfTarget(x, y+1, target);
				count += countIfTarget(x+1, y+1, target);

				next[c] = count>=THRESHOLD ? target : current;
			}

			System.arraycopy(next, 0, state, 0, SIZE);
		}

		public void goBack() {
		}

		private int countIfTarget(int x, int y, int target) {
			int wx = (x+GW)%GW;
			int wy = (y+GH)%GH;
			return state[wy*GW+wx]==target ? 1 : 0;
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) pixels[c] = palette[state[c]];
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}
}
