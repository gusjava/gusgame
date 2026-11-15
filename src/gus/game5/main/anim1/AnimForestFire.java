package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimForestFire extends Game2 {

	public static final int CELL = 4;
	public static final int GW = 200;
	public static final int GH = 145;
	public static final int SIZE = GW*GH;

	public static final int EMPTY = 0;
	public static final int TREE = 1;
	public static final int FIRE = 2;

	public static final double GROWTH_RATE = 0.01;
	public static final double LIGHTNING_RATE = 0.00005;
	public static final double INITIAL_TREE_RATE = 0.5;

	public static final Color COLOR_EMPTY = new Color(28,22,18);
	public static final Color COLOR_TREE = new Color(40,150,60);
	public static final Color COLOR_FIRE = new Color(235,90,30);

	public static void main(String[] args) {
		AnimForestFire main = new AnimForestFire();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Forest fire animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(40);
	}

	protected void initialize1() {
		Forest forest = new Forest();
		addDraw(forest);
		addDyn(forest);
	}

	/*
	 * FOREST
	 */

	private class Forest extends Drawing1 implements Dyn {
		private final int[] state = new int[SIZE];
		private final int[] next = new int[SIZE];
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];
		private final int rgbEmpty = COLOR_EMPTY.getRGB();
		private final int rgbTree = COLOR_TREE.getRGB();
		private final int rgbFire = COLOR_FIRE.getRGB();

		public Forest() {
			for(int c=0;c<SIZE;c++) if(UtilRandom.randomDouble(1.0)<INITIAL_TREE_RATE) state[c] = TREE;
		}

		public void goNext() {
			for(int x=0;x<GW;x++)
			for(int y=0;y<GH;y++) {
				int c = y*GW+x;
				int current = state[c];

				if(current==FIRE) next[c] = EMPTY;
				else if(current==TREE) next[c] = hasFireNeighbour(x, y) || UtilRandom.randomDouble(1.0)<LIGHTNING_RATE ? FIRE : TREE;
				else next[c] = UtilRandom.randomDouble(1.0)<GROWTH_RATE ? TREE : EMPTY;
			}

			System.arraycopy(next, 0, state, 0, SIZE);
		}

		public void goBack() {
		}

		private boolean hasFireNeighbour(int x, int y) {
			for(int dx=-1;dx<=1;dx++)
			for(int dy=-1;dy<=1;dy++) {
				if(dx==0 && dy==0) continue;
				int wx = (x+dx+GW)%GW;
				int wy = (y+dy+GH)%GH;
				if(state[wy*GW+wx]==FIRE) return true;
			}
			return false;
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) {
				int s = state[c];
				pixels[c] = s==FIRE ? rgbFire : s==TREE ? rgbTree : rgbEmpty;
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}
}
