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

public class AnimLenia extends Game2 {

	public static final int CELL = 5;
	public static final int GW = 150;
	public static final int GH = 150;
	public static final int SIZE = GW*GH;

	public static final int KERNEL_RADIUS = 13;
	public static final double GROWTH_MU = 0.15;
	public static final double GROWTH_SIGMA = 0.015;
	public static final double DT = 0.1;

	public static final int SEED_COUNT = 6;
	public static final int SEED_RADIUS = 10;

	public static void main(String[] args) {
		AnimLenia main = new AnimLenia();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Lenia animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(40);
		s.setBackground(Color.BLACK);
	}

	protected void initialize1() {
		Lenia lenia = new Lenia();
		addDraw(lenia);
		addDyn(lenia);
	}

	/*
	 * LENIA
	 */

	private class Lenia extends Drawing1 implements Dyn {
		private final double[] value = new double[SIZE];
		private final double[] next = new double[SIZE];
		private final int[] kernelDx;
		private final int[] kernelDy;
		private final double[] kernelWeight;
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		public Lenia() {
			List<int[]> offsets = new ArrayList<>();
			List<Double> weights = new ArrayList<>();
			double sum = 0;

			for(int dx=-KERNEL_RADIUS;dx<=KERNEL_RADIUS;dx++)
			for(int dy=-KERNEL_RADIUS;dy<=KERNEL_RADIUS;dy++) {
				double dist = Math.sqrt(dx*dx+dy*dy);
				if(dist==0 || dist>KERNEL_RADIUS) continue;
				double weight = 1-dist/KERNEL_RADIUS;
				offsets.add(new int[] {dx,dy});
				weights.add(weight);
				sum += weight;
			}

			kernelDx = new int[offsets.size()];
			kernelDy = new int[offsets.size()];
			kernelWeight = new double[offsets.size()];
			for(int i=0;i<offsets.size();i++) {
				kernelDx[i] = offsets.get(i)[0];
				kernelDy[i] = offsets.get(i)[1];
				kernelWeight[i] = weights.get(i)/sum;
			}

			for(int s=0;s<SEED_COUNT;s++) seed(UtilRandom.randomInt(GW), UtilRandom.randomInt(GH));
		}

		private void seed(int cx, int cy) {
			for(int dx=-SEED_RADIUS;dx<=SEED_RADIUS;dx++)
			for(int dy=-SEED_RADIUS;dy<=SEED_RADIUS;dy++) {
				if(dx*dx+dy*dy>SEED_RADIUS*SEED_RADIUS) continue;
				int x = (cx+dx+GW)%GW;
				int y = (cy+dy+GH)%GH;
				value[y*GW+x] = UtilRandom.randomDouble(1.0);
			}
		}

		public void goNext() {
			for(int x=0;x<GW;x++)
			for(int y=0;y<GH;y++) {
				double u = 0;
				for(int k=0;k<kernelDx.length;k++) {
					int nx = (x+kernelDx[k]+GW)%GW;
					int ny = (y+kernelDy[k]+GH)%GH;
					u += value[ny*GW+nx]*kernelWeight[k];
				}

				double growth = 2*Math.exp(-(u-GROWTH_MU)*(u-GROWTH_MU)/(2*GROWTH_SIGMA*GROWTH_SIGMA))-1;
				double v = value[y*GW+x]+DT*growth;
				next[y*GW+x] = v<0 ? 0 : (v>1 ? 1 : v);
			}

			System.arraycopy(next, 0, value, 0, SIZE);
		}

		public void goBack() {
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) {
				float v = (float) value[c];
				pixels[c] = Color.HSBtoRGB(0.58f-0.5f*v, 0.75f, 0.1f+0.9f*v);
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}
}
