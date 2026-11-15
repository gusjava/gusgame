package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;

public class AnimSandpile extends Game2 {

	public static final int CELL = 5;
	public static final int GW = 140;
	public static final int GH = 100;
	public static final int SIZE = GW*GH;
	public static final int TOPPLE_THRESHOLD = 4;

	public static void main(String[] args) {
		AnimSandpile main = new AnimSandpile();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Sandpile animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL+20);
		s.setSleep(5);
	}

	private Automaton automaton;

	protected void initialize1() {
		automaton = new Automaton();
		addDraw(automaton);
		addDyn(automaton);

		newDrawingText(Color.WHITE, p1(10, GH*CELL+15), ()-> "Grains ajoutes : "+automaton.getGrainsAdded());
	}

	/*
	 * AUTOMATON
	 */

	private class Automaton extends Drawing1 implements Dyn {
		private final int[] grains = new int[SIZE];
		private final int[] palette = new int[TOPPLE_THRESHOLD];
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];
		private int grainsAdded = 0;
		private int dropCursor = 0;

		public Automaton() {
			palette[0] = Color.BLACK.getRGB();
			palette[1] = new Color(60,90,170).getRGB();
			palette[2] = new Color(120,170,230).getRGB();
			palette[3] = new Color(220,230,255).getRGB();
		}

		public int getGrainsAdded() {
			return grainsAdded;
		}

		public void goNext() {
			int cx, cy;
			if(dropCursor%3==0) {
				cx = GW/2;
				cy = GH/2;
			} else {
				cx = (int) (Math.random()*GW);
				cy = (int) (Math.random()*GH);
			}
			dropCursor++;

			grains[cy*GW+cx]++;
			grainsAdded++;
			topple();
		}

		public void goBack() {
		}

		private void topple() {
			boolean unstable = true;
			while(unstable) {
				unstable = false;
				for(int x=0;x<GW;x++)
				for(int y=0;y<GH;y++) {
					int c = y*GW+x;
					if(grains[c]>=TOPPLE_THRESHOLD) {
						unstable = true;
						grains[c] -= TOPPLE_THRESHOLD;
						addGrain(x-1, y);
						addGrain(x+1, y);
						addGrain(x, y-1);
						addGrain(x, y+1);
					}
				}
			}
		}

		private void addGrain(int x, int y) {
			if(x<0 || x>=GW || y<0 || y>=GH) return;
			grains[y*GW+x]++;
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) {
				int v = grains[c];
				pixels[c] = palette[v<TOPPLE_THRESHOLD ? v : TOPPLE_THRESHOLD-1];
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), GW*CELL, GH*CELL, img);
		}
	}
}
