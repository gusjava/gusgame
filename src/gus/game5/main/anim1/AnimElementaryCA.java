package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Iterator;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimElementaryCA extends Game2 {

	public static void main(String[] args) {
		AnimElementaryCA main = new AnimElementaryCA();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Elementary cellular automaton animation");
		s.setSize(900, 720);
		s.setSleep(40);
		s.setBackground(Color.BLACK);
	}

	public static final double CELL = 5;
	public static final int[] RULES = {30, 90, 110, 184, 122, 169};
	public static final int CYCLE_GENERATIONS = 260;
	public static final Color ALIVE_COLOR = new Color(90,220,140);

	protected void initialize1() {
		Automaton automaton = new Automaton();
		addDraw(automaton);
		addDyn(automaton);
	}

	/*
	 * AUTOMATON
	 */

	private class Automaton extends Drawing1 implements Dyn {
		private final int width;
		private final int height;
		private int rule;
		private int generation;
		private boolean[] current;
		private final ArrayDeque<boolean[]> rows = new ArrayDeque<>();

		public Automaton() {
			width = (int) (gameWidth()/CELL);
			height = (int) (gameHeight()/CELL);
			newRule();
		}

		private void newRule() {
			rule = RULES[UtilRandom.randomInt(RULES.length)];
			generation = 0;
			rows.clear();

			current = new boolean[width];
			current[width/2] = true;
			rows.addLast(current);
		}

		public void goNext() {
			generation++;
			if(generation>=CYCLE_GENERATIONS) {
				newRule();
				return;
			}

			boolean[] next = new boolean[width];
			for(int i=0; i<width; i++) {
				boolean left = current[(i-1+width)%width];
				boolean center = current[i];
				boolean right = current[(i+1)%width];
				int pattern = (left?4:0)+(center?2:0)+(right?1:0);
				next[i] = ((rule>>pattern)&1)==1;
			}

			current = next;
			rows.addLast(current);
			if(rows.size()>height) rows.removeFirst();
		}

		public void goBack() {
		}

		protected void draw() {
			int rowIndex = 0;
			Iterator<boolean[]> it = rows.iterator();
			while(it.hasNext()) {
				boolean[] row = it.next();
				for(int i=0; i<width; i++)
					if(row[i])
						fillRect(ALIVE_COLOR, p(i*CELL, rowIndex*CELL), CELL, CELL);
				rowIndex++;
			}
		}
	}
}
