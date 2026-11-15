package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimLangtonLoops extends Game2 {

	public static final int CELL = 4;
	public static final int GW = 200;
	public static final int GH = 145;
	public static final int SIZE = GW*GH;

	public static final int LOOP_SIZE = 6;
	public static final int GAP = 2;
	public static final int STEP = LOOP_SIZE+GAP;
	public static final int REPLICATE_PERIOD = 40;
	public static final int MAX_LOOPS = 500;

	public static final int[] DX = {0, 1, 0, -1};
	public static final int[] DY = {-1, 0, 1, 0};

	public static void main(String[] args) {
		AnimLangtonLoops main = new AnimLangtonLoops();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Langton loops animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(60);
		s.setBackground(Color.BLACK);
	}

	protected void initialize1() {
		Colony colony = new Colony();
		addDraw(colony);
		addDyn(colony);
	}

	/*
	 * LOOP
	 */

	private static class Loop {
		int cx;
		int cy;
		int dirIndex;
		int timer;
		int generation;

		Loop(int cx, int cy, int generation) {
			this.cx = cx;
			this.cy = cy;
			this.generation = generation;
			this.dirIndex = 0;
			this.timer = UtilRandom.randomInt(REPLICATE_PERIOD);
		}
	}

	/*
	 * COLONY
	 */

	private class Colony extends Drawing1 implements Dyn {
		private final int[] grid = new int[SIZE];
		private final List<Loop> loops = new ArrayList<>();
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		public Colony() {
			Loop seed = new Loop(GW/2, GH/2, 0);
			seed.timer = 0;
			carve(seed);
			loops.add(seed);
		}

		public void goNext() {
			if(loops.size()>=MAX_LOOPS) return;

			List<Loop> snapshot = new ArrayList<>(loops);
			for(Loop loop : snapshot) {
				loop.timer++;
				if(loop.timer<REPLICATE_PERIOD) continue;

				loop.timer = 0;
				tryReplicate(loop);
				loop.dirIndex = (loop.dirIndex+1)%4;
			}
		}

		public void goBack() {
		}

		private void tryReplicate(Loop parent) {
			int dx = DX[parent.dirIndex];
			int dy = DY[parent.dirIndex];
			int childCx = parent.cx+dx*STEP;
			int childCy = parent.cy+dy*STEP;

			if(!fitsFree(childCx, childCy)) return;

			Loop child = new Loop(childCx, childCy, parent.generation+1);
			child.timer = 0;
			carve(child);
			loops.add(child);
		}

		private boolean fitsFree(int cx, int cy) {
			int half = LOOP_SIZE/2;
			int x0 = cx-half-1;
			int y0 = cy-half-1;
			int x1 = cx+half+1;
			int y1 = cy+half+1;

			if(x0<0 || y0<0 || x1>=GW || y1>=GH) return false;

			for(int x=x0;x<=x1;x++)
			for(int y=y0;y<=y1;y++)
				if(grid[y*GW+x]!=0) return false;

			return true;
		}

		private void carve(Loop loop) {
			int half = LOOP_SIZE/2;
			int x0 = loop.cx-half;
			int y0 = loop.cy-half;
			int x1 = loop.cx+half;
			int y1 = loop.cy+half;

			for(int x=x0;x<=x1;x++)
			for(int y=y0;y<=y1;y++) {
				boolean border = x==x0 || x==x1 || y==y0 || y==y1;
				if(border) grid[y*GW+x] = 1+(loop.generation%6);
			}
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) {
				int v = grid[c];
				pixels[c] = v==0 ? 0 : Color.HSBtoRGB(v/7f, 0.8f, 1f);
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}
}
