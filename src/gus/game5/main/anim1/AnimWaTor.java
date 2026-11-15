package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.UtilRandom;

public class AnimWaTor extends Game1 {

	public static final long SLEEP = 40;
	public static final int CELL = 4;
	public static final int GW = 200;
	public static final int GH = 145;
	public static final int SIZE = GW*GH;

	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;

	public static final double FISH_RATE = 0.26;
	public static final double SHARK_RATE = 0.05;

	public static final int FISH_BREED = 3;
	public static final int SHARK_BREED = 12;
	public static final int SHARK_ENERGY = 6;

	public static final int EXTINCTION_DELAY = 60;

	public static final Color COLOR_SEA = new Color(6,20,46);
	public static final Color COLOR_FISH = new Color(90,220,140);
	public static final Color COLOR_SHARK = new Color(240,80,80);
	public static final Color COLOR_TEXT = new Color(190,210,235);
	public static final Color COLOR_BAND = new Color(6,20,46,200);
	public static final int BAND_HEIGHT = 26;

	public static void main(String[] args) {
		AnimWaTor main = new AnimWaTor();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Wa-Tor");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_SEA);
	}

	/*
	 * DATA
	 */

	private int[] kind;
	private int[] breed;
	private int[] energy;
	private boolean[] done;
	private int[] order;
	private int[] neighbours;
	private int fishCount;
	private int sharkCount;
	private int chronon;
	private int extinctSince;

	protected void initialize1() {
		kind = new int[SIZE];
		breed = new int[SIZE];
		energy = new int[SIZE];
		done = new boolean[SIZE];
		order = new int[SIZE];
		neighbours = new int[4];
		chronon = 0;
		extinctSince = 0;

		for(int c=0;c<SIZE;c++) {
			order[c] = c;
			double r = UtilRandom.randomDouble(1.0);
			if(r<SHARK_RATE) {
				kind[c] = SHARK;
				energy[c] = SHARK_ENERGY;
				breed[c] = UtilRandom.randomInt(SHARK_BREED);
			}
			else if(r<SHARK_RATE+FISH_RATE) {
				kind[c] = FISH;
				breed[c] = UtilRandom.randomInt(FISH_BREED);
			}
		}

		countPopulations();

		addDraw(new Sea());
		addDraw(new Hud());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();

		if(fishCount==0 || sharkCount==0) {
			extinctSince++;
			if(extinctSince>EXTINCTION_DELAY) restart();
			return;
		}

		chronon++;
		shuffleOrder();
		Arrays.fill(done, false);

		for(int i=0;i<SIZE;i++) {
			int c = order[i];
			if(done[c]) continue;
			if(kind[c]==FISH) moveFish(c);
			else if(kind[c]==SHARK) moveShark(c);
		}

		countPopulations();
	}

	private void shuffleOrder() {
		for(int i=SIZE-1;i>0;i--) {
			int j = UtilRandom.randomInt(i+1);
			int tmp = order[i];
			order[i] = order[j];
			order[j] = tmp;
		}
	}

	private void moveFish(int c) {
		done[c] = true;
		breed[c]++;

		int target = pickNeighbour(c, EMPTY);
		if(target<0) return;

		moveTo(c, target);
		if(breed[target]>=FISH_BREED) {
			breed[target] = 0;
			kind[c] = FISH;
			breed[c] = 0;
		}
	}

	private void moveShark(int c) {
		done[c] = true;
		breed[c]++;
		energy[c]--;

		if(energy[c]<=0) {
			clear(c);
			return;
		}

		int target = pickNeighbour(c, FISH);
		if(target>=0) energy[c] = SHARK_ENERGY;
		else target = pickNeighbour(c, EMPTY);
		if(target<0) return;

		moveTo(c, target);
		if(breed[target]>=SHARK_BREED) {
			breed[target] = 0;
			kind[c] = SHARK;
			breed[c] = 0;
			energy[c] = SHARK_ENERGY;
		}
	}

	private void moveTo(int from, int to) {
		kind[to] = kind[from];
		breed[to] = breed[from];
		energy[to] = energy[from];
		done[to] = true;
		clear(from);
	}

	private void clear(int c) {
		kind[c] = EMPTY;
		breed[c] = 0;
		energy[c] = 0;
	}

	private int pickNeighbour(int c, int wanted) {
		int x = c%GW;
		int y = c/GW;
		int count = 0;

		int west = y*GW+(x>0 ? x-1 : GW-1);
		int east = y*GW+(x<GW-1 ? x+1 : 0);
		int north = (y>0 ? y-1 : GH-1)*GW+x;
		int south = (y<GH-1 ? y+1 : 0)*GW+x;

		if(kind[west]==wanted) neighbours[count++] = west;
		if(kind[east]==wanted) neighbours[count++] = east;
		if(kind[north]==wanted) neighbours[count++] = north;
		if(kind[south]==wanted) neighbours[count++] = south;

		if(count==0) return -1;
		return neighbours[UtilRandom.randomInt(count)];
	}

	private void countPopulations() {
		int fish = 0;
		int sharks = 0;
		for(int c=0;c<SIZE;c++) {
			if(kind[c]==FISH) fish++;
			else if(kind[c]==SHARK) sharks++;
		}
		fishCount = fish;
		sharkCount = sharks;
	}

	/*
	 * SEA
	 */

	private class Sea extends Drawing1 {

		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];
		private final int rgbSea = COLOR_SEA.getRGB();
		private final int rgbFish = COLOR_FISH.getRGB();
		private final int rgbShark = COLOR_SHARK.getRGB();

		protected void draw() {
			for(int c=0;c<SIZE;c++) {
				pixels[c] = kind[c]==FISH ? rgbFish : kind[c]==SHARK ? rgbShark : rgbSea;
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {

		protected void draw() {
			fillRect(COLOR_BAND, p(0,0), gameWidth(), BAND_HEIGHT);
			drawString(COLOR_TEXT, fontPlain(13), p(12, 18), "Chronon " + chronon
					+ "    poissons " + fishCount + "    requins " + sharkCount);
		}
	}
}
