package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimReactionDiffusion extends Game2 {

	public static void main(String[] args) {
		AnimReactionDiffusion main = new AnimReactionDiffusion();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Reaction-diffusion animation");
		s.setSize(880, 720);
		s.setSleep(30);
		s.setBackground(Color.BLACK);
	}

	public static final int GRID_W = 110;
	public static final int GRID_H = 90;
	public static final double CELL = 8;
	public static final double DIFF_A = 1.0;
	public static final double DIFF_B = 0.5;
	public static final double FEED = 0.0545;
	public static final double KILL = 0.062;
	public static final int STEPS_PER_FRAME = 10;
	public static final int SEED_COUNT = 4;
	public static final int SEED_RADIUS = 4;

	protected void initialize1() {
		ReactionDiffusion reactionDiffusion = new ReactionDiffusion();
		addDraw(reactionDiffusion);
		addDyn(reactionDiffusion);
	}

	/*
	 * REACTION DIFFUSION
	 */

	private class ReactionDiffusion extends Drawing1 implements Dyn {
		private double[][] a;
		private double[][] b;
		private double[][] nextA;
		private double[][] nextB;

		public ReactionDiffusion() {
			a = new double[GRID_W][GRID_H];
			b = new double[GRID_W][GRID_H];
			nextA = new double[GRID_W][GRID_H];
			nextB = new double[GRID_W][GRID_H];

			for(int i=0; i<GRID_W; i++)
				for(int j=0; j<GRID_H; j++)
					a[i][j] = 1;

			for(int s=0; s<SEED_COUNT; s++) {
				int cx = UtilRandom.randomInt(GRID_W);
				int cy = UtilRandom.randomInt(GRID_H);
				seed(cx, cy);
			}
		}

		private void seed(int cx, int cy) {
			for(int i=-SEED_RADIUS; i<=SEED_RADIUS; i++)
				for(int j=-SEED_RADIUS; j<=SEED_RADIUS; j++) {
					int x = wrap(cx+i, GRID_W);
					int y = wrap(cy+j, GRID_H);
					a[x][y] = 0.5;
					b[x][y] = 0.25;
				}
		}

		private int wrap(int v, int limit) {
			return ((v%limit)+limit)%limit;
		}

		public void goNext() {
			for(int s=0; s<STEPS_PER_FRAME; s++) step();
		}

		public void goBack() {
		}

		private void step() {
			for(int i=0; i<GRID_W; i++)
				for(int j=0; j<GRID_H; j++) {
					double av = a[i][j];
					double bv = b[i][j];
					double reaction = av*bv*bv;

					double newA = av + DIFF_A*laplacian(a, i, j) - reaction + FEED*(1-av);
					double newB = bv + DIFF_B*laplacian(b, i, j) + reaction - (KILL+FEED)*bv;

					nextA[i][j] = clamp01(newA);
					nextB[i][j] = clamp01(newB);
				}

			double[][] swapA = a;
			a = nextA;
			nextA = swapA;

			double[][] swapB = b;
			b = nextB;
			nextB = swapB;
		}

		private double clamp01(double v) {
			return v<0 ? 0 : (v>1 ? 1 : v);
		}

		private double laplacian(double[][] grid, int i, int j) {
			int im = wrap(i-1, GRID_W);
			int ip = wrap(i+1, GRID_W);
			int jm = wrap(j-1, GRID_H);
			int jp = wrap(j+1, GRID_H);

			double orthoSum = grid[im][j]+grid[ip][j]+grid[i][jm]+grid[i][jp];
			double diagSum = grid[im][jm]+grid[im][jp]+grid[ip][jm]+grid[ip][jp];

			return orthoSum*0.2+diagSum*0.05-grid[i][j];
		}

		protected void draw() {
			for(int i=0; i<GRID_W; i++)
				for(int j=0; j<GRID_H; j++) {
					double v = clamp01(b[i][j]*1.4);
					float hue = 0.62f-0.55f*(float) v;
					float brightness = 0.12f+0.88f*(float) v;
					Color color = Color.getHSBColor(hue, 0.85f, brightness);
					fillRect(color, p(i*CELL, j*CELL), CELL, CELL);
				}
		}
	}
}
