package gus.game5.main.genetic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeImg;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilImage;

public class GameImageEvolution extends Game1 {

	public static final int GAME_WIDTH = 820;
	public static final int GAME_HEIGHT = 460;
	public static final long SLEEP = 25;

	public static final int POP_SIZE = 60;
	public static final double MUTATION_RATE = 0.08;
	public static final int TOURNAMENT_SIZE = 4;
	public static final int ELITISM_COUNT = 2;

	public static final double PANEL_W = 220;
	public static final double PANEL_H = 170;
	public static final Point1 TARGET_CENTER = new Point1(150, 190);
	public static final Point1 BEST_CENTER = new Point1(410, 190);

	public static final double CHART_X0 = 560;
	public static final double CHART_Y0 = 90;
	public static final double CHART_W = 220;
	public static final double CHART_H = 190;

	public static void main(String[] args) {
		GameImageEvolution main = new GameImageEvolution();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Evolution d'image par algorithme genetique");
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(new Color(25, 28, 32));
	}

	/*
	 * DATA
	 */

	private BufferedImage targetImage;
	private int canvasWidth;
	private int canvasHeight;
	private int[] targetPixels;
	private BufferedImage scratch;

	private Population<ImageChromosome> population;
	private List<Double> fitnessHistory;
	private ShapeImg bestShape;
	private boolean paused;

	protected void initialize1() {
		targetImage = UtilImage.readImg(resourceAt("data/target.jpg"));
		canvasWidth = targetImage.getWidth();
		canvasHeight = targetImage.getHeight();
		targetPixels = targetImage.getRGB(0, 0, canvasWidth, canvasHeight, null, 0, canvasWidth);
		scratch = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);

		List<ImageChromosome> initial = new ArrayList<>();
		for(int i=0;i<POP_SIZE;i++) initial.add(new ImageChromosome(canvasWidth, canvasHeight));
		population = new Population<>(initial, this::evaluateFitness, MUTATION_RATE, TOURNAMENT_SIZE, ELITISM_COUNT);

		fitnessHistory = new ArrayList<>();
		fitnessHistory.add(population.getBestFitness());
		paused = false;

		newShapeImg(TARGET_CENTER, PANEL_W, PANEL_H, targetImage);
		bestShape = newShapeImg(BEST_CENTER, PANEL_W, PANEL_H, population.getBest().toImage());
		addDraw(new FitnessChart());

		newDrawingText(Color.WHITE, p1(20, 25), "Evolution d'image par algorithme genetique");
		newDrawingText(Color.LIGHT_GRAY, p1(TARGET_CENTER.getX()-PANEL_W/2, 285), "Image cible");
		newDrawingText(Color.LIGHT_GRAY, p1(BEST_CENTER.getX()-PANEL_W/2, 285),
				()-> "Meilleur individu - generation "+population.getGeneration());
		newDrawingText(Color.WHITE, p1(BEST_CENTER.getX()-PANEL_W/2, 305),
				()-> "Ecart pixels : "+UtilDisplay.dec2(population.getBestFitness()));
		newDrawingText(Color.YELLOW, p1(20, GAME_HEIGHT-40), ()-> paused ? "EN PAUSE" : "");
		newDrawingText(Color.LIGHT_GRAY, p1(20, GAME_HEIGHT-20),
				"ESPACE pause/reprise   F1 nouvelle population   F2 quitter");
	}

	private double evaluateFitness(ImageChromosome chromosome) {
		Graphics2D g2 = scratch.createGraphics();
		chromosome.render(g2);
		g2.dispose();

		int[] candidatePixels = scratch.getRGB(0, 0, canvasWidth, canvasHeight, null, 0, canvasWidth);
		long total = 0;
		for(int i=0;i<candidatePixels.length;i++) total += pixelDiff(targetPixels[i], candidatePixels[i]);
		return total;
	}

	private static long pixelDiff(int a, int b) {
		int dr = ((a>>16)&0xFF)-((b>>16)&0xFF);
		int dg = ((a>>8)&0xFF)-((b>>8)&0xFF);
		int db = (a&0xFF)-(b&0xFF);
		return (long) (dr*dr+dg*dg+db*db);
	}

	protected void turn() {
		if(handleRestartExit()) return;

		Keyboard k = keyboard();
		if(k.out().space()) paused = !paused;
		if(paused) return;

		population.nextGeneration();
		fitnessHistory.add(population.getBestFitness());
		bestShape.setImg(population.getBest().toImage());
	}

	/*
	 * FITNESS CHART
	 */

	private class FitnessChart extends Drawing1 {

		protected void draw() {
			drawRect(Color.GRAY, p(CHART_X0, CHART_Y0), CHART_W, CHART_H);
			drawString(Color.WHITE, p(CHART_X0, CHART_Y0-10), "Fitness par generation (plus haut = mieux)");

			int count = fitnessHistory.size();
			if(count<2) return;

			double worst = fitnessHistory.get(0);
			double maxValue = worst<=0 ? 1 : worst;

			Point1 previous = null;
			for(int i=0;i<count;i++) {
				double ratio = Math.min(1, fitnessHistory.get(i)/maxValue);
				double x = CHART_X0+CHART_W*i/(double) (count-1);
				double y = CHART_Y0+CHART_H*ratio;
				Point1 current = p(x, y);
				if(previous!=null) drawThickLine(Color.CYAN, previous, current, 2);
				previous = current;
			}
		}
	}

}
