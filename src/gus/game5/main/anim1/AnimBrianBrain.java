package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;

public class AnimBrianBrain extends Game2 {

	public static final int CELL = 6;
	public static final int GW = 140;
	public static final int GH = 100;
	public static final int SIZE = GW*GH;
	public static final double DENSITY = 0.2;

	public static final int OFF = 0;
	public static final int DYING = 1;
	public static final int ON = 2;

	public static void main(String[] args) {
		AnimBrianBrain main = new AnimBrianBrain();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Brian's Brain animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(50);
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
		private final int[] palette = new int[3];
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		public Automaton() {
			palette[OFF] = Color.BLACK.getRGB();
			palette[DYING] = new Color(255,120,0).getRGB();
			palette[ON] = new Color(255,255,80).getRGB();
			for(int c=0;c<SIZE;c++) state[c] = Math.random()<DENSITY ? ON : OFF;
		}

		public void goNext() {
			for(int x=0;x<GW;x++)
			for(int y=0;y<GH;y++) {
				int c = y*GW+x;
				int current = state[c];
				int result;

				if(current==ON) {
					result = DYING;
				} else if(current==DYING) {
					result = OFF;
				} else {
					result = countOnNeighbours(x, y)==2 ? ON : OFF;
				}

				next[c] = result;
			}

			System.arraycopy(next, 0, state, 0, SIZE);
		}

		public void goBack() {
		}

		private int countOnNeighbours(int x, int y) {
			int count = 0;
			for(int dx=-1;dx<=1;dx++)
			for(int dy=-1;dy<=1;dy++) {
				if(dx==0 && dy==0) continue;
				int nx = (x+dx+GW)%GW;
				int ny = (y+dy+GH)%GH;
				if(state[ny*GW+nx]==ON) count++;
			}
			return count;
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) pixels[c] = palette[state[c]];
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}
}
