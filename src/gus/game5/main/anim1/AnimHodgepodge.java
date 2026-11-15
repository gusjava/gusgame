package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimHodgepodge extends Game2 {

	public static final int CELL = 6;
	public static final int GW = 140;
	public static final int GH = 100;
	public static final int SIZE = GW*GH;

	public static final int K = 20;
	public static final int INFECTION_THRESHOLD = 3;
	public static final double INITIAL_DENSITY = 0.02;

	public static void main(String[] args) {
		AnimHodgepodge main = new AnimHodgepodge();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Hodgepodge machine animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(40);
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
		private final int[] age = new int[SIZE];
		private final int[] next = new int[SIZE];
		private final int[] palette = new int[K+1];
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		public Automaton() {
			for(int a=0;a<=K;a++) {
				float ratio = a/(float) K;
				int r = (int) (255*Math.min(1, ratio*2));
				int g = (int) (255*Math.max(0, 1-Math.abs(ratio*2-1)));
				int b = 0;
				palette[a] = new Color(r, g, b).getRGB();
			}
			for(int c=0;c<SIZE;c++) age[c] = Math.random()<INITIAL_DENSITY ? 1+UtilRandom.randomInt(K) : 0;
		}

		public void goNext() {
			for(int x=0;x<GW;x++)
			for(int y=0;y<GH;y++) {
				int c = y*GW+x;
				int current = age[c];
				int result;

				if(current==K) {
					result = 0;
				} else if(current>0) {
					result = current+1;
				} else {
					int sumAge = 0, neighbours = 0;
					for(int dx=-1;dx<=1;dx++)
					for(int dy=-1;dy<=1;dy++) {
						if(dx==0 && dy==0) continue;
						int nx = (x+dx+GW)%GW;
						int ny = (y+dy+GH)%GH;
						sumAge += age[ny*GW+nx];
						neighbours++;
					}
					result = (sumAge/neighbours)>INFECTION_THRESHOLD ? 1 : 0;
				}

				next[c] = result;
			}

			System.arraycopy(next, 0, age, 0, SIZE);
		}

		public void goBack() {
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) pixels[c] = palette[age[c]];
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}
}
