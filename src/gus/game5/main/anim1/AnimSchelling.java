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

public class AnimSchelling extends Game2 {

	public static final int CELL = 4;
	public static final int GW = 200;
	public static final int GH = 145;
	public static final int SIZE = GW*GH;

	public static final int EMPTY = 0;
	public static final int TYPE_A = 1;
	public static final int TYPE_B = 2;

	public static final double RATE_A = 0.45;
	public static final double RATE_B = 0.45;

	public static final double SATISFACTION_THRESHOLD = 0.35;
	public static final int MOVES_PER_FRAME = 1500;

	public static final Color COLOR_EMPTY = new Color(20,20,24);
	public static final Color COLOR_A = new Color(240,150,60);
	public static final Color COLOR_B = new Color(80,170,235);
	public static final Color COLOR_TEXT = new Color(220,220,230);
	public static final Color COLOR_BAND = new Color(20,20,24);
	public static final int BAND_HEIGHT = 26;

	public static void main(String[] args) {
		AnimSchelling main = new AnimSchelling();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Schelling segregation animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(30);
	}

	protected void initialize1() {
		City city = new City();
		addDraw(city);
		addDraw(new Hud(city));
		addDyn(city);
	}

	/*
	 * CITY
	 */

	private class City extends Drawing1 implements Dyn {
		private final int[] state = new int[SIZE];
		private final List<Integer> emptyCells = new ArrayList<>();
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];
		private final int rgbEmpty = COLOR_EMPTY.getRGB();
		private final int rgbA = COLOR_A.getRGB();
		private final int rgbB = COLOR_B.getRGB();
		private double segregationIndex;

		public City() {
			for(int c=0;c<SIZE;c++) {
				double r = UtilRandom.randomDouble(1.0);
				if(r<RATE_A) state[c] = TYPE_A;
				else if(r<RATE_A+RATE_B) state[c] = TYPE_B;
				else emptyCells.add(c);
			}
		}

		public void goNext() {
			for(int i=0;i<MOVES_PER_FRAME;i++) moveOne();
			segregationIndex = computeSegregationIndex();
		}

		public void goBack() {
		}

		private void moveOne() {
			int c = UtilRandom.randomInt(SIZE);
			int type = state[c];
			if(type==EMPTY) return;
			if(satisfaction(c, type)>=SATISFACTION_THRESHOLD) return;

			int index = UtilRandom.randomInt(emptyCells.size());
			int target = emptyCells.get(index);
			emptyCells.set(index, c);
			state[target] = type;
			state[c] = EMPTY;
		}

		private double satisfaction(int c, int type) {
			int x = c%GW;
			int y = c/GW;
			int same = 0;
			int occupied = 0;

			for(int dx=-1;dx<=1;dx++)
			for(int dy=-1;dy<=1;dy++) {
				if(dx==0 && dy==0) continue;
				int wx = (x+dx+GW)%GW;
				int wy = (y+dy+GH)%GH;
				int neighbour = state[wy*GW+wx];
				if(neighbour==EMPTY) continue;
				occupied++;
				if(neighbour==type) same++;
			}

			return occupied==0 ? 1 : same/(double) occupied;
		}

		private double computeSegregationIndex() {
			double sum = 0;
			int counted = 0;

			for(int c=0;c<SIZE;c++) {
				int type = state[c];
				if(type==EMPTY) continue;
				sum += satisfaction(c, type);
				counted++;
			}

			return counted==0 ? 0 : sum/counted;
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) {
				int s = state[c];
				pixels[c] = s==TYPE_A ? rgbA : s==TYPE_B ? rgbB : rgbEmpty;
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}

		public double getSegregationIndex() {
			return segregationIndex;
		}
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {
		private final City city;

		public Hud(City city) {
			this.city = city;
		}

		protected void draw() {
			fillRect(COLOR_BAND, p(0,0), gameWidth(), BAND_HEIGHT);
			drawString(COLOR_TEXT, fontPlain(13), p(12, 18),
					"Indice de segregation moyen : " + Math.round(city.getSegregationIndex()*100) + " %");
		}
	}
}
