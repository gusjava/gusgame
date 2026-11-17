package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;
import gus.game5.main.genetic.Population;

public class AnimQueensGA extends Game1 {

	public static final int GAME_WIDTH = 760;
	public static final int GAME_HEIGHT = 760;
	public static final long SLEEP = 20;

	public static final int N_MIN = 8;
	public static final int N_MAX = 12;

	public static final int POP_SIZE = 150;
	public static final double MUTATION_RATE = 0.15;
	public static final int TOURNAMENT_SIZE = 5;
	public static final int ELITISM_COUNT = 4;

	public static final double CELL_SIZE = 48;
	public static final double BOARD_TOP = 110;
	public static final int SOLVED_PAUSE_FRAMES = 90;
	public static final int STAGNATION_LIMIT = 120;

	public static final Color COLOR_BG = new Color(25, 28, 32);
	public static final Color COLOR_CELL_LIGHT = new Color(235, 225, 200);
	public static final Color COLOR_CELL_DARK = new Color(90, 65, 50);
	public static final Color COLOR_QUEEN = new Color(220, 40, 40);
	public static final Color COLOR_TEXT = Color.WHITE;
	public static final Color COLOR_SOLVED = new Color(120, 230, 140);

	public static void main(String[] args) {
		AnimQueensGA main = new AnimQueensGA();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("N-Reines par algorithme genetique");
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private int n;
	private double boardSize;
	private double boardX;
	private Population<QueensChromosome> population;
	private int solvedPause;
	private double bestEverFitness;
	private int totalGenerations;
	private int lastImprovementGeneration;

	protected void initialize1() {
		n = UtilRandom.randomInt(N_MIN, N_MAX+1);
		boardSize = n*CELL_SIZE;
		boardX = (GAME_WIDTH-boardSize)/2;
		solvedPause = 0;
		totalGenerations = 0;
		lastImprovementGeneration = 0;

		population = newPopulation();
		bestEverFitness = population.getBestFitness();

		addDraw(new Board());

		newDrawingText(COLOR_TEXT, p1(20, 30), ()-> "N-Reines par algorithme genetique - N = "+n);
		newDrawingText(COLOR_TEXT, p1(20, 55), ()-> "Generation "+totalGenerations
				+"   Conflits (meilleur individu) : "+(int) population.getBestFitness());
		newDrawingText(COLOR_SOLVED, p1(20, 78), ()-> population.getBestFitness()==0 ? "SOLUTION TROUVEE" : "");
		newDrawingText(Color.LIGHT_GRAY, p1(20, GAME_HEIGHT-20), "F1 nouvelle partie   F2 quitter");
	}

	private Population<QueensChromosome> newPopulation() {
		List<QueensChromosome> initial = new ArrayList<>();
		for(int i=0;i<POP_SIZE;i++) initial.add(new QueensChromosome(n));
		return new Population<>(initial, this::evaluateFitness, MUTATION_RATE, TOURNAMENT_SIZE, ELITISM_COUNT);
	}

	private double evaluateFitness(QueensChromosome chromosome) {
		int size = chromosome.size();
		int conflicts = 0;
		for(int i=0;i<size;i++)
		for(int j=i+1;j<size;j++) {
			int diff = chromosome.row(i)-chromosome.row(j);
			if(diff==0 || Math.abs(diff)==j-i) conflicts++;
		}
		return conflicts;
	}

	protected void turn() {
		if(handleRestartExit()) return;

		if(population.getBestFitness()==0) {
			solvedPause++;
			if(solvedPause>=SOLVED_PAUSE_FRAMES) restart();
			return;
		}

		population.nextGeneration();
		totalGenerations++;

		if(population.getBestFitness()<bestEverFitness) {
			bestEverFitness = population.getBestFitness();
			lastImprovementGeneration = totalGenerations;
		}
		else if(totalGenerations-lastImprovementGeneration>=STAGNATION_LIMIT) {
			population = newPopulation();
			bestEverFitness = population.getBestFitness();
			lastImprovementGeneration = totalGenerations;
		}
	}

	/*
	 * BOARD
	 */

	private class Board extends Drawing1 {

		protected void draw() {
			for(int col=0;col<n;col++)
			for(int row=0;row<n;row++) {
				Color cellColor = (col+row)%2==0 ? COLOR_CELL_LIGHT : COLOR_CELL_DARK;
				fillRect(cellColor, p(boardX+col*CELL_SIZE, BOARD_TOP+row*CELL_SIZE), CELL_SIZE, CELL_SIZE);
			}

			QueensChromosome best = population.getBest();
			for(int col=0;col<n;col++) {
				Point1 center = p(boardX+col*CELL_SIZE+CELL_SIZE/2, BOARD_TOP+best.row(col)*CELL_SIZE+CELL_SIZE/2);
				drawStringC(COLOR_QUEEN, fontBold((int) (CELL_SIZE*0.6)), center, "♛");
			}
		}
	}

}
