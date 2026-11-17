package gus.game5.main.creature;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.util.UtilRandom;

public class GameCreatureSociety extends Game1 {

	protected void initSettings(Settings s) {
		s.setTitle("Societe de creatures");
		s.setWidth(CreatureConst.GAME_WIDTH);
		s.setHeight(CreatureConst.GAME_HEIGHT);
		s.setSleep(CreatureConst.GAME_SPEED);
		s.setBackground(Color.BLACK);
	}

	private ShapeList<Food> foodList;
	private ShapeList<Creature> creatureList;
	private boolean[] colorTaken;
	private int birthsCount = 0;
	private int deathsCount = 0;

	protected void initialize1() {
		foodList = newShapeList();
		creatureList = newShapeList();
		addDraw(this::drawAffinityLinks);

		colorTaken = new boolean[CreatureConst.PALETTE.length];
		for(int i=0;i<CreatureConst.INITIAL_POPULATION;i++) {
			Point2 pos = UtilRandom.randomPoint2(this);
			Creature creature = new Creature(pos, Genome.random(), CreatureConst.PALETTE[i], i);
			colorTaken[i] = true;
			creatureList.add(creature);
		}

		for(int i=0;i<CreatureConst.FOOD_INITIAL;i++) {
			foodList.add(new Food(UtilRandom.randomPoint1(this)));
		}

		newDrawingText(Color.WHITE, p1(10,15), ()-> "Population : " + creatureList.size() + "/" + CreatureConst.MAX_POPULATION);
		newDrawingText(Color.WHITE, p1(10,30), ()-> "Naissances : " + birthsCount);
		newDrawingText(Color.WHITE, p1(10,45), ()-> "Morts : " + deathsCount);
		newDrawingText(Color.WHITE, p1(10,60), ()-> "Nourriture : " + foodList.size());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();

		tickCooldowns();
		processSensingAndDecision();
		processFoodEating();
		processSocialInteractions();
		processAffinityDecay();
		processMovement();
		processDeaths();
		processReproduction();
		processFoodGeneration();
	}

	private void tickCooldowns() {
		for(Creature c : creatureList) {
			c.tickCooldown();
		}
	}

	private void processSensingAndDecision() {
		for(Creature c : creatureList) {
			if(!c.isOver()) {
				c.senseAndDecide(this, foodList, creatureList);
			}
		}
	}

	private void processFoodEating() {
		for(Creature c : creatureList) {
			if(c.isOver()) continue;

			List<Food> eaten = foodList.findAllInter(c);
			if(eaten.isEmpty()) continue;

			boolean ateSomething = false;
			for(Food food : eaten) {
				if(food.isOver()) continue;
				c.gainEnergy(CreatureConst.FOOD_ENERGY);
				food.setEaten(true);
				ateSomething = true;
			}
			if(ateSomething && c.getCurrentAction()==CreatureAction.APPROACH) {
				c.rewardDecision(1);
			}
		}
		foodList.clean();
	}

	private void processSocialInteractions() {
		int n = creatureList.size();
		for(int i=0;i<n;i++) {
			Creature a = creatureList.get(i);
			if(a.isOver()) continue;

			for(int j=i+1;j<n;j++) {
				Creature b = creatureList.get(j);
				if(b.isOver()) continue;

				double dist = a.getAnchor().dist(b.getAnchor());
				if(dist>CreatureConst.INTERACT_RADIUS) continue;

				boolean aCooperates = a.getCurrentAction()==CreatureAction.COOPERATE;
				boolean bCooperates = b.getCurrentAction()==CreatureAction.COOPERATE;
				if(aCooperates && bCooperates) {
					a.adjustAffinity(b, CreatureConst.AFFINITY_COOPERATE_GAIN);
					b.adjustAffinity(a, CreatureConst.AFFINITY_COOPERATE_GAIN);
					shareEnergy(a, b);
					a.rewardDecision(1);
					b.rewardDecision(1);
					continue;
				}

				boolean aAttacks = a.getCurrentAction()==CreatureAction.ATTACK && a.getLastSalient()==b && a.canAttack();
				boolean bAttacks = b.getCurrentAction()==CreatureAction.ATTACK && b.getLastSalient()==a && b.canAttack();
				if(aAttacks) {
					b.loseHealth(CreatureConst.CONFLICT_DAMAGE);
					a.adjustAffinity(b, -CreatureConst.AFFINITY_CONFLICT_LOSS);
					b.adjustAffinity(a, -CreatureConst.AFFINITY_CONFLICT_LOSS);
					a.rewardDecision(CreatureConst.ATTACK_SUCCESS_REWARD);
					b.rewardDecision(-1);
					a.startAttackCooldown();
				}
				if(bAttacks) {
					a.loseHealth(CreatureConst.CONFLICT_DAMAGE);
					b.adjustAffinity(a, -CreatureConst.AFFINITY_CONFLICT_LOSS);
					a.adjustAffinity(b, -CreatureConst.AFFINITY_CONFLICT_LOSS);
					b.rewardDecision(CreatureConst.ATTACK_SUCCESS_REWARD);
					a.rewardDecision(-1);
					b.startAttackCooldown();
				}
			}
		}
	}

	private void shareEnergy(Creature a, Creature b) {
		double transfer = (a.getEnergy()-b.getEnergy())*CreatureConst.COOPERATE_SHARE_RATIO;
		if(transfer>0) {
			a.loseEnergy(transfer);
			b.gainEnergy(transfer);
		}
		else if(transfer<0) {
			b.loseEnergy(-transfer);
			a.gainEnergy(-transfer);
		}
	}

	private void processAffinityDecay() {
		for(Creature c : creatureList) {
			c.decayAffinity();
		}
	}

	private void processMovement() {
		for(Creature c : creatureList) {
			if(!c.isOver()) c.applyMovement();
		}
		creatureList.goNext();
		for(Creature c : creatureList) {
			c.clampToBounds(this);
		}
	}

	private void processDeaths() {
		for(Creature c : creatureList) {
			if(!c.isOver()) continue;

			colorTaken[c.getPaletteIndex()] = false;
			deathsCount++;
			for(Creature other : creatureList) {
				if(other!=c) other.forgetCreature(c);
			}
		}
		creatureList.clean();
	}

	private void processReproduction() {
		if(creatureList.size()>=CreatureConst.MAX_POPULATION) return;

		int n = creatureList.size();
		for(int i=0;i<n;i++) {
			Creature a = creatureList.get(i);
			if(!a.canReproduce() || a.getEnergy()<CreatureConst.REPRO_ENERGY_THRESHOLD) continue;

			for(int j=i+1;j<n;j++) {
				Creature b = creatureList.get(j);
				if(!b.canReproduce() || b.getEnergy()<CreatureConst.REPRO_ENERGY_THRESHOLD) continue;
				if(a.getAffinity(b)<CreatureConst.REPRO_AFFINITY_THRESHOLD) continue;

				spawnChild(a, b);
				return;
			}
		}
	}

	private void spawnChild(Creature a, Creature b) {
		int colorIndex = findFreeColorIndex();
		if(colorIndex<0) return;

		Genome childGenome = a.getGenome().crossover(b.getGenome());
		childGenome.mutate(CreatureConst.MUTATION_RATE);

		Point2 position = a.getAnchor().pAdd(b.getAnchor()).p2();
		position.divXY(2);
		position.addXY(UtilRandom.randomDouble(-10,10), UtilRandom.randomDouble(-10,10));

		Creature child = new Creature(position, childGenome, CreatureConst.PALETTE[colorIndex], colorIndex);
		colorTaken[colorIndex] = true;
		creatureList.add(child);

		a.loseEnergy(CreatureConst.REPRO_ENERGY_COST);
		b.loseEnergy(CreatureConst.REPRO_ENERGY_COST);
		a.startReproCooldown();
		b.startReproCooldown();
		birthsCount++;
	}

	private int findFreeColorIndex() {
		for(int i=0;i<colorTaken.length;i++) {
			if(!colorTaken[i]) return i;
		}
		return -1;
	}

	private void processFoodGeneration() {
		if(foodList.size()>=CreatureConst.FOOD_MAX) return;
		if(getCount()%CreatureConst.FOOD_RATE==0) {
			foodList.add(new Food(UtilRandom.randomPoint1(this)));
		}
	}

	private void drawAffinityLinks(Graphics2D g2) {
		int n = creatureList.size();
		for(int i=0;i<n;i++) {
			Creature a = creatureList.get(i);
			for(int j=i+1;j<n;j++) {
				Creature b = creatureList.get(j);
				double value = a.getAffinity(b);
				if(Math.abs(value)<0.05) continue;

				Color color = value>0
						? new Color(60,220,100, alphaFor(value))
						: new Color(220,60,60, alphaFor(value));
				g2.setColor(color);

				Point2 pa = a.getAnchor();
				Point2 pb = b.getAnchor();
				g2.drawLine((int) pa.getX(), (int) pa.getY(), (int) pb.getX(), (int) pb.getY());
			}
		}
	}

	private int alphaFor(double value) {
		return (int) Math.min(255, 60+Math.abs(value)*180);
	}

	public static void main(String[] args) {
		GameCreatureSociety main = new GameCreatureSociety();
		main.displayInWindows();
		main.start();
	}
}
