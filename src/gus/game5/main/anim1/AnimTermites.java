package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimTermites extends Game1 {

	public static final long SLEEP = 10;
	public static final int CELL = 4;
	public static final int GW = 220;
	public static final int GH = 165;
	public static final int SIZE = GW*GH;

	public static final double CHIP_DENSITY = 0.10;
	public static final int TERMITE_COUNT = 200;
	public static final double STEP = 1.0;
	public static final double WANDER = Math.toRadians(25);
	public static final double TURN_INERTIA = Math.toRadians(6);

	public static final Color COLOR_BG = new Color(18,14,10);
	public static final Color COLOR_CHIP = new Color(200,160,90);
	public static final Color COLOR_TERMITE = new Color(255,235,200);
	public static final Color COLOR_TERMITE_LOADED = new Color(255,120,80);

	public static void main(String[] args) {
		AnimTermites main = new AnimTermites();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Termites");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private boolean[] chip;
	private double[] termiteX;
	private double[] termiteY;
	private double[] termiteA;
	private boolean[] loaded;

	protected void initialize1() {
		chip = new boolean[SIZE];
		for(int c=0;c<SIZE;c++) chip[c] = Math.random()<CHIP_DENSITY;

		termiteX = new double[TERMITE_COUNT];
		termiteY = new double[TERMITE_COUNT];
		termiteA = new double[TERMITE_COUNT];
		loaded = new boolean[TERMITE_COUNT];

		for(int i=0;i<TERMITE_COUNT;i++) {
			termiteX[i] = UtilRandom.randomDouble(GW);
			termiteY[i] = UtilRandom.randomDouble(GH);
			termiteA[i] = UtilRandom.randomDouble(2*Math.PI);
		}

		addDraw(new Field());
		addDraw(new Termites());
	}

	protected void turn() {
		handleRestartExit();
		moveTermites();
	}

	private void moveTermites() {
		for(int i=0;i<TERMITE_COUNT;i++) {
			double a = termiteA[i]+UtilRandom.randomDouble(-TURN_INERTIA, TURN_INERTIA);
			if(Math.random()<0.05) a += UtilRandom.randomDouble(-WANDER, WANDER);

			double x = termiteX[i]+STEP*Math.cos(a);
			double y = termiteY[i]+STEP*Math.sin(a);

			if(x<0.5 || x>GW-1.5) {
				a = Math.PI-a;
				x = termiteX[i];
			}
			if(y<0.5 || y>GH-1.5) {
				a = -a;
				y = termiteY[i];
			}

			termiteX[i] = x;
			termiteY[i] = y;
			termiteA[i] = a;

			handleChip(i);
		}
	}

	private void handleChip(int i) {
		int cx = (int) termiteX[i];
		int cy = (int) termiteY[i];
		int c = cy*GW+cx;

		if(!loaded[i]) {
			if(chip[c]) {
				chip[c] = false;
				loaded[i] = true;
			}
			return;
		}

		if(!chip[c]) return;

		int drop = findFreeNeighbor(cx, cy);
		if(drop<0) return;

		chip[drop] = true;
		loaded[i] = false;
	}

	private int findFreeNeighbor(int cx, int cy) {
		int start = UtilRandom.randomInt(8);
		for(int k=0;k<8;k++) {
			int dir = (start+k)%8;
			int dx = DX[dir];
			int dy = DY[dir];
			int nx = cx+dx;
			int ny = cy+dy;
			if(nx<0 || nx>=GW || ny<0 || ny>=GH) continue;
			int c = ny*GW+nx;
			if(!chip[c]) return c;
		}
		return -1;
	}

	private static final int[] DX = {-1,0,1,-1,1,-1,0,1};
	private static final int[] DY = {-1,-1,-1,0,0,1,1,1};

	/*
	 * FIELD
	 */

	private class Field extends Drawing1 {

		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		protected void draw() {
			int bg = (COLOR_BG.getRed()<<16)|(COLOR_BG.getGreen()<<8)|COLOR_BG.getBlue();
			int chipColor = (COLOR_CHIP.getRed()<<16)|(COLOR_CHIP.getGreen()<<8)|COLOR_CHIP.getBlue();
			for(int c=0;c<SIZE;c++) pixels[c] = chip[c] ? chipColor : bg;
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}

	/*
	 * TERMITES
	 */

	private class Termites extends Drawing1 {

		protected void draw() {
			for(int i=0;i<TERMITE_COUNT;i++) {
				Color color = loaded[i] ? COLOR_TERMITE_LOADED : COLOR_TERMITE;
				fillRoundC(color, p(termiteX[i]*CELL, termiteY[i]*CELL), 1.4);
			}
		}
	}
}
