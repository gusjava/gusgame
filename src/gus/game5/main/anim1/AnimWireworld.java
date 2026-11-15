package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;

public class AnimWireworld extends Game2 {

	public static final int CELL = 8;
	public static final int GW = 100;
	public static final int GH = 75;
	public static final int SIZE = GW*GH;

	public static final int EMPTY = 0;
	public static final int WIRE = 1;
	public static final int HEAD = 2;
	public static final int TAIL = 3;

	public static void main(String[] args) {
		AnimWireworld main = new AnimWireworld();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Wireworld animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(60);
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
		private final int[] palette = new int[4];
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		public Automaton() {
			palette[EMPTY] = Color.BLACK.getRGB();
			palette[WIRE] = new Color(120,40,20).getRGB();
			palette[HEAD] = new Color(60,140,255).getRGB();
			palette[TAIL] = new Color(170,210,255).getRGB();
			buildCircuit();
		}

		private void wire(int x, int y) {
			state[y*GW+x] = WIRE;
		}

		private void head(int x, int y) {
			state[y*GW+x] = HEAD;
		}

		private void tail(int x, int y) {
			state[y*GW+x] = TAIL;
		}

		private void hline(int x1, int x2, int y) {
			for(int x=x1;x<=x2;x++) wire(x, y);
		}

		private void vline(int x, int y1, int y2) {
			for(int y=y1;y<=y2;y++) wire(x, y);
		}

		private void buildCircuit() {
			hline(10, 26, 30);
			hline(10, 26, 42);
			vline(10, 30, 42);
			vline(26, 30, 42);
			tail(17, 30);
			head(18, 30);

			int mainY = 36;
			hline(26, 85, mainY);

			vline(40, mainY, 56);
			hline(40, 60, 56);

			vline(60, 16, mainY);
			hline(40, 60, 16);

			vline(85, mainY, 52);
			hline(65, 85, 52);
			vline(65, 52, 66);
			hline(65, 90, 66);
		}

		public void goNext() {
			for(int x=0;x<GW;x++)
			for(int y=0;y<GH;y++) {
				int c = y*GW+x;
				int current = state[c];
				int result;

				if(current==EMPTY) {
					result = EMPTY;
				} else if(current==HEAD) {
					result = TAIL;
				} else if(current==TAIL) {
					result = WIRE;
				} else {
					int headNeighbours = countHeadNeighbours(x, y);
					result = (headNeighbours==1 || headNeighbours==2) ? HEAD : WIRE;
				}

				next[c] = result;
			}

			System.arraycopy(next, 0, state, 0, SIZE);
		}

		public void goBack() {
		}

		private int countHeadNeighbours(int x, int y) {
			int count = 0;
			for(int dx=-1;dx<=1;dx++)
			for(int dy=-1;dy<=1;dy++) {
				if(dx==0 && dy==0) continue;
				int nx = x+dx, ny = y+dy;
				if(nx<0 || nx>=GW || ny<0 || ny>=GH) continue;
				if(state[ny*GW+nx]==HEAD) count++;
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
