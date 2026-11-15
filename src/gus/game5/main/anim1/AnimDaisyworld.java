package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilRandom;

public class AnimDaisyworld extends Game1 {

	public static final long SLEEP = 20;
	public static final int CELL = 5;
	public static final int GW = 160;
	public static final int GH = 120;
	public static final int SIZE = GW*GH;

	public static final int BARE = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;

	public static final double ALBEDO_BARE = 0.5;
	public static final double ALBEDO_BLACK = 0.25;
	public static final double ALBEDO_WHITE = 0.75;

	public static final double TEMP_OPTIMAL = 22.5;
	public static final double TEMP_RANGE = 17.5;
	public static final double TEMP_K1 = 40;
	public static final double TEMP_K2 = 30;
	public static final double RAMP_PERIOD = 6000;

	public static final double DEATH_RATE = 0.02;
	public static final double SEED_RATE = 0.30;

	public static final Color COLOR_BARE = new Color(90,70,45);
	public static final Color COLOR_BLACK = new Color(20,20,20);
	public static final Color COLOR_WHITE = new Color(235,235,230);

	public static void main(String[] args) {
		AnimDaisyworld main = new AnimDaisyworld();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Daisyworld");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL+60);
		s.setSleep(SLEEP);
		s.setBackground(Color.BLACK);
	}

	/*
	 * DATA
	 */

	private int[] state;
	private double luminosity;
	private double globalTemp;
	private double meanAlbedo;

	protected void initialize1() {
		state = new int[SIZE];
		for(int c=0;c<SIZE;c++) {
			double r = Math.random();
			state[c] = r<0.2 ? BLACK : r<0.4 ? WHITE : BARE;
		}

		addDraw(new Field());
		newDrawingText(Color.WHITE, p1(10, GH*CELL+20), ()-> "Luminosite : "+UtilDisplay.dec2(luminosity)+"   Temperature globale : "+UtilDisplay.dec2(globalTemp));
		newDrawingText(Color.WHITE, p1(10, GH*CELL+40), this::ratioText);
	}

	private String ratioText() {
		int black = 0, white = 0;
		for(int c=0;c<SIZE;c++) {
			if(state[c]==BLACK) black++;
			else if(state[c]==WHITE) white++;
		}
		return "Marguerites noires : "+black+"   blanches : "+white+"   nues : "+(SIZE-black-white);
	}

	protected void turn() {
		handleRestartExit();

		luminosity = 1.0+0.6*Math.sin(getCount()/RAMP_PERIOD*2*Math.PI);
		computeClimate();
		updatePatches();
	}

	private void computeClimate() {
		double sumAlbedo = 0;
		for(int c=0;c<SIZE;c++) sumAlbedo += albedoOf(state[c]);
		meanAlbedo = sumAlbedo/SIZE;
		globalTemp = TEMP_K1*luminosity-TEMP_K2*meanAlbedo;
	}

	private double albedoOf(int s) {
		if(s==BLACK) return ALBEDO_BLACK;
		if(s==WHITE) return ALBEDO_WHITE;
		return ALBEDO_BARE;
	}

	private double growth(double temp) {
		double d = (temp-TEMP_OPTIMAL)/TEMP_RANGE;
		return Math.max(0, 1-d*d);
	}

	private void updatePatches() {
		int[] next = state.clone();

		for(int i=0;i<GW;i++)
		for(int j=0;j<GH;j++) {
			int c = j*GW+i;
			int s = state[c];

			if(s!=BARE) {
				if(Math.random()<DEATH_RATE) next[c] = BARE;
				continue;
			}

			double localTemp = globalTemp+TEMP_K2*(meanAlbedo-ALBEDO_BARE);
			double g = growth(localTemp);
			if(g<=0) continue;

			int blackNb = 0, whiteNb = 0, total = 0;
			for(int dy=-1;dy<=1;dy++)
			for(int dx=-1;dx<=1;dx++) {
				if(dx==0 && dy==0) continue;
				int ni = ((i+dx)%GW+GW)%GW;
				int nj = ((j+dy)%GH+GH)%GH;
				int nc = nj*GW+ni;
				total++;
				if(state[nc]==BLACK) blackNb++;
				else if(state[nc]==WHITE) whiteNb++;
			}

			double pBlack = g*SEED_RATE*blackNb/total;
			double pWhite = g*SEED_RATE*whiteNb/total;
			double roll = Math.random();
			if(roll<pBlack) next[c] = BLACK;
			else if(roll<pBlack+pWhite) next[c] = WHITE;
		}

		state = next;
	}

	/*
	 * FIELD
	 */

	private class Field extends Drawing1 {

		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		protected void draw() {
			int bare = rgb(COLOR_BARE);
			int black = rgb(COLOR_BLACK);
			int white = rgb(COLOR_WHITE);
			for(int c=0;c<SIZE;c++) {
				pixels[c] = state[c]==BLACK ? black : state[c]==WHITE ? white : bare;
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), GW*CELL, GH*CELL, img);
		}

		private int rgb(Color color) {
			return (color.getRed()<<16)|(color.getGreen()<<8)|color.getBlue();
		}
	}
}
