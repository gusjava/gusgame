package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.control.ShapeControl;
import gus.game5.core.shape.control.ShapeControlBorderRebound;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilRandom;

public class AnimEvolvingCreatures extends Game1 {

	public static final int GAME_WIDTH = 1000;
	public static final int GAME_HEIGHT = 700;
	public static final long GAME_SPEED = 10;

	public static final int INIT_COUNT = 30;
	public static final double MAX_ENERGY = 100;
	public static final double SPAWN_THRESHOLD = 70;
	public static final int SPAWN_CHANCE = 40;
	public static final double MUTATION_RATE = 0.10;

	public static final double SPEED_MIN = 0.3;
	public static final double SPEED_MAX = 3.0;
	public static final double PERCEPTION_MIN = 20;
	public static final double PERCEPTION_MAX = 150;
	public static final double SIZE_MIN = 0.5;
	public static final double SIZE_MAX = 3.0;
	public static final double ENERGY_COST_SPEED = 0.012;
	public static final double ENERGY_COST_SIZE = 0.008;

	public static final int FOOD_RATE = 8;
	public static final double FOOD_RADIUS = 2;
	public static final double FOOD_ENERGY = 30;
	public static final Color FOOD_COLOR = Color.WHITE;

	public static void main(String[] args) {
		AnimEvolvingCreatures main = new AnimEvolvingCreatures();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Evolving creatures");
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(GAME_SPEED);
		s.setBackground(Color.BLACK);
	}

	/*
	 * DATA
	 */

	private ShapeList<EvoFood> foodList;
	private ShapeList<Creature> creatureList;
	private ShapeControl borderRebound;

	protected void initialize1() {
		foodList = newShapeList();
		creatureList = newShapeList();
		borderRebound = new ShapeControlBorderRebound(this);

		for(int i=0;i<INIT_COUNT;i++) {
			creatureList.add(new Creature(UtilRandom.randomPoint2(this),
					UtilRandom.randomDouble(SPEED_MIN, SPEED_MAX),
					UtilRandom.randomDouble(PERCEPTION_MIN, PERCEPTION_MAX),
					UtilRandom.randomDouble(SIZE_MIN, SIZE_MAX)));
		}

		newDrawingText(Color.WHITE, p1(10,15), ()-> "Creatures : "+creatureList.size()+"   Nourriture : "+foodList.size());
		newDrawingText(Color.WHITE, p1(10,30), ()-> "Vitesse moy. : "+avg(Creature::getSpeedGene));
		newDrawingText(Color.WHITE, p1(10,45), ()-> "Perception moy. : "+avg(Creature::getPerceptionGene));
		newDrawingText(Color.WHITE, p1(10,60), ()-> "Taille moy. : "+avg(Creature::getSizeGene));
	}

	private String avg(java.util.function.ToDoubleFunction<Creature> f) {
		if(creatureList.isEmpty()) return "-";
		double sum = 0;
		for(Creature c : creatureList) sum += f.applyAsDouble(c);
		return UtilDisplay.dec2(sum/creatureList.size());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();

		steerCreatures();
		processFoodEating();
		processCreatureActivity();
		processCreatureSpawning();
		processFoodGeneration();
	}

	private void steerCreatures() {
		for(Creature creature : creatureList) {
			EvoFood target = foodList.findNearest(creature.getAnchor());
			if(target==null) continue;
			double d = target.getAnchor().dist(creature.getAnchor());
			if(d>creature.getPerceptionGene()) continue;

			double dx = target.getAnchor().getX()-creature.getAnchor().getX();
			double dy = target.getAnchor().getY()-creature.getAnchor().getY();
			creature.getAnchor().initDerived().setAngle(Angle.angleXY(dx,dy));
		}
	}

	private void processFoodEating() {
		for(EvoFood food : foodList) {
			List<Creature> eaters = creatureList.findAllInter(food);
			if(eaters.isEmpty()) continue;

			double gain = food.getEnergy()/eaters.size();
			eaters.forEach(c->c.gainEnergy(gain));
			food.setOver(true);
		}
		foodList.clean();
	}

	private void processCreatureActivity() {
		creatureList.goNext();
		creatureList.clean();
		creatureList.control(borderRebound);
	}

	private void processCreatureSpawning() {
		List<Creature> spawns = new ArrayList<>();
		for(Creature creature : creatureList) {
			Creature spawn = creature.trySpawn();
			if(spawn!=null) spawns.add(spawn);
		}
		creatureList.addAll(spawns);
	}

	private void processFoodGeneration() {
		if(getCount()%FOOD_RATE==0) {
			foodList.add(new EvoFood(UtilRandom.randomPoint1(this)));
		}
	}

	/*
	 * EVO FOOD
	 */

	private static class EvoFood extends ShapeRound {

		private boolean over = false;

		public EvoFood(Point0 anchor) {
			super(anchor, FOOD_RADIUS);
			setColor(FOOD_COLOR);
		}

		public double getEnergy() {
			return FOOD_ENERGY;
		}

		public void setOver(boolean over) {
			this.over = over;
		}

		public boolean isOver() {
			return over;
		}
	}

	/*
	 * CREATURE
	 */

	private class Creature extends ShapeRound {

		private final double speedGene;
		private final double perceptionGene;
		private final double sizeGene;
		private double energy;
		private boolean dead = false;

		public Creature(Point0 anchor, double speedGene, double perceptionGene, double sizeGene) {
			super(anchor, 3+2*sizeGene);
			this.speedGene = speedGene;
			this.perceptionGene = perceptionGene;
			this.sizeGene = sizeGene;
			this.energy = MAX_ENERGY/2;
			getAnchor().initDerived().setXY(speedGene, Angle.random());
			setColor(new Color(60+(int)(sizeGene*40), 200, 120+(int)(speedGene*40)));
		}

		public double getSpeedGene() {return speedGene;}
		public double getPerceptionGene() {return perceptionGene;}
		public double getSizeGene() {return sizeGene;}

		public boolean isOver() {
			return dead;
		}

		public void goNext() {
			super.goNext();
			double cost = ENERGY_COST_SPEED*speedGene*speedGene+ENERGY_COST_SIZE*sizeGene;
			energy -= cost;
			if(energy<=0) {
				energy = 0;
				dead = true;
			}
		}

		public void gainEnergy(double gain) {
			energy = Math.min(MAX_ENERGY, energy+gain);
		}

		public Creature trySpawn() {
			if(energy<SPAWN_THRESHOLD) return null;
			if(!UtilRandom.chance(SPAWN_CHANCE)) return null;

			energy /= 2;
			Creature child = new Creature(getAnchor().p1(),
					mutate(speedGene, SPEED_MIN, SPEED_MAX),
					mutate(perceptionGene, PERCEPTION_MIN, PERCEPTION_MAX),
					mutate(sizeGene, SIZE_MIN, SIZE_MAX));
			child.energy = energy;
			return child;
		}

		private double mutate(double gene, double min, double max) {
			double mutated = gene*(1+UtilRandom.randomDouble(-MUTATION_RATE, MUTATION_RATE));
			return Math.max(min, Math.min(max, mutated));
		}
	}
}
