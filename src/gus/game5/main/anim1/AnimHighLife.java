package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;

public class AnimHighLife extends Game2 {

	public static final int CELL = 6;
	public static final int GW = 140;
	public static final int GH = 100;
	public static final int SIZE = GW*GH;
	public static final double DENSITY = 0.25;

	private static final boolean[][] REPLICATOR = {
		{false,false,true,true,true},
		{false,true,false,false,true},
		{true,false,false,false,true},
		{true,false,false,true,false},
		{true,true,true,false,false},
	};

	public static void main(String[] args) {
		AnimHighLife main = new AnimHighLife();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("HighLife animation");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(40);
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
		private final boolean[] state = new boolean[SIZE];
		private final boolean[] next = new boolean[SIZE];
		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];
		private final int colorOn = Color.WHITE.getRGB();
		private final int colorOff = Color.BLACK.getRGB();

		public Automaton() {
			for(int c=0;c<SIZE;c++) state[c] = Math.random()<DENSITY;
			stampReplicator(20, 15);
			stampReplicator(100, 70);
			stampReplicator(25, 75);
		}

		private void stampReplicator(int ox, int oy) {
			for(int j=0;j<REPLICATOR.length;j++)
			for(int i=0;i<REPLICATOR[j].length;i++) {
				state[(oy+j)*GW+(ox+i)] = REPLICATOR[j][i];
			}
		}

		public void goNext() {
			for(int x=0;x<GW;x++)
			for(int y=0;y<GH;y++) {
				int c = y*GW+x;
				int aliveNeighbours = countAliveNeighbours(x, y);
				boolean alive = state[c];

				if(alive) {
					next[c] = aliveNeighbours==2 || aliveNeighbours==3;
				} else {
					next[c] = aliveNeighbours==3 || aliveNeighbours==6;
				}
			}

			System.arraycopy(next, 0, state, 0, SIZE);
		}

		public void goBack() {
		}

		private int countAliveNeighbours(int x, int y) {
			int count = 0;
			for(int dx=-1;dx<=1;dx++)
			for(int dy=-1;dy<=1;dy++) {
				if(dx==0 && dy==0) continue;
				int nx = (x+dx+GW)%GW;
				int ny = (y+dy+GH)%GH;
				if(state[ny*GW+nx]) count++;
			}
			return count;
		}

		protected void draw() {
			for(int c=0;c<SIZE;c++) pixels[c] = state[c] ? colorOn : colorOff;
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}
}
