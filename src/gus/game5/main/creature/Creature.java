package gus.game5.main.creature;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;

public class Creature extends ShapeRound {

	public Creature(Point2 anchor, Genome genome, Color identityColor, int paletteIndex) {
		super(anchor, CreatureConst.CREATURE_RADIUS, identityColor);
		this.genome = genome;
		this.brain = new Brain(genome);
		this.paletteIndex = paletteIndex;
		this.energy = CreatureConst.MAX_ENERGY*CreatureConst.INITIAL_ENERGY_RATIO;
		this.health = CreatureConst.MAX_HEALTH;
		this.heading = Angle.random();
	}

	/*
	 * GENOME / BRAIN
	 */

	private final Genome genome;
	private final Brain brain;

	public Genome getGenome() {
		return genome;
	}

	/*
	 * PALETTE INDEX
	 */

	private final int paletteIndex;

	public int getPaletteIndex() {
		return paletteIndex;
	}

	/*
	 * ENERGY
	 */

	private double energy;

	public double getEnergy() {
		return energy;
	}

	public void gainEnergy(double gain) {
		if(gain<=0) return;
		energy = Math.min(CreatureConst.MAX_ENERGY, energy+gain);
	}

	public void loseEnergy(double loss) {
		if(loss<=0) return;
		energy = Math.max(0, energy-loss);
		if(energy<=0) dead = true;
	}

	/*
	 * HEALTH
	 */

	private double health;

	public double getHealth() {
		return health;
	}

	public void loseHealth(double damage) {
		if(damage<=0) return;
		health = Math.max(0, health-damage);
		if(health<=0) dead = true;
	}

	/*
	 * DEAD / OVER
	 */

	private boolean dead = false;

	public boolean isOver() {
		return dead;
	}

	/*
	 * HEADING
	 */

	private Angle heading;

	/*
	 * ACTION / SIGNAL
	 */

	private CreatureAction currentAction = CreatureAction.REST;
	private Signal currentSignal = Signal.CALM;
	private Creature lastSalient;

	public CreatureAction getCurrentAction() {
		return currentAction;
	}

	public Signal getCurrentSignal() {
		return currentSignal;
	}

	public Creature getLastSalient() {
		return lastSalient;
	}

	/*
	 * CURRENT SPEED
	 */

	public double currentSpeed() {
		Point2 derived = getAnchor().getDerived();
		return derived!=null ? derived.dist() : 0;
	}

	/*
	 * AFFINITY
	 */

	private Map<Creature,Double> affinity = new HashMap<>();

	public double getAffinity(Creature other) {
		Double value = affinity.get(other);
		return value!=null ? value : 0;
	}

	public void adjustAffinity(Creature other, double delta) {
		double value = getAffinity(other) + delta;
		if(value>1) value = 1;
		if(value<-1) value = -1;
		affinity.put(other, value);
	}

	public void decayAffinity() {
		for(Map.Entry<Creature,Double> entry : affinity.entrySet()) {
			double value = entry.getValue();
			if(value>0) entry.setValue(Math.max(0, value-CreatureConst.AFFINITY_DECAY));
			else if(value<0) entry.setValue(Math.min(0, value+CreatureConst.AFFINITY_DECAY));
		}
	}

	public void forgetCreature(Creature other) {
		affinity.remove(other);
	}

	/*
	 * REPRODUCTION COOLDOWN
	 */

	private int reproCooldown = 0;

	public boolean canReproduce() {
		return reproCooldown<=0;
	}

	public void startReproCooldown() {
		reproCooldown = CreatureConst.REPRO_COOLDOWN;
	}

	public void tickCooldown() {
		if(reproCooldown>0) reproCooldown--;
		if(attackCooldown>0) attackCooldown--;
	}

	/*
	 * ATTACK COOLDOWN
	 */

	private int attackCooldown = 0;

	public boolean canAttack() {
		return attackCooldown<=0;
	}

	public void startAttackCooldown() {
		attackCooldown = CreatureConst.ATTACK_COOLDOWN;
	}

	/*
	 * REWARD
	 */

	public void rewardDecision(double reward) {
		brain.learnFromReward(reward);
	}

	/*
	 * SENSE AND DECIDE
	 */

	public void senseAndDecide(Game game, ShapeList<Food> foods, ShapeList<Creature> all) {
		Food nearestFood = foods.findNearest(getAnchor());
		double foodDistNorm = 1;
		double foodCos = 0;
		double foodSin = 0;
		if(nearestFood!=null) {
			Point1 delta = nearestFood.getAnchor().pSub(getAnchor());
			double dist = delta.dist();
			foodDistNorm = Math.min(1, dist/CreatureConst.SENSE_RADIUS);
			if(dist>0) {
				foodCos = delta.getX()/dist;
				foodSin = delta.getY()/dist;
			}
		}

		double wallSignalRaw = computeWallSignal(game);

		int visibleCount = 0;
		double nearestNeighborDist = -1;

		Creature salient = null;
		double salientScore = 0;
		double salientAffinity = 0;

		double weightedSignalSum = 0;
		double weightAbsSum = 0;

		for(Creature other : all) {
			if(other==this || other.isOver()) continue;
			double dist = getAnchor().dist(other.getAnchor());
			if(dist>CreatureConst.SENSE_RADIUS) continue;

			visibleCount++;
			if(nearestNeighborDist<0 || dist<nearestNeighborDist) {
				nearestNeighborDist = dist;
			}

			double closeness = 1 - Math.min(1, dist/CreatureConst.SENSE_RADIUS);
			double agitation = Math.min(1, other.currentSpeed()/CreatureConst.MAX_SPEED);
			double affinityValue = getAffinity(other);

			double score = brain.evaluateDangerOpportunity(new double[] {closeness, agitation, affinityValue});
			if(salient==null || Math.abs(score)>Math.abs(salientScore)) {
				salient = other;
				salientScore = score;
				salientAffinity = affinityValue;
			}

			double signalValue = signalToValue(other.getCurrentSignal());
			weightedSignalSum += affinityValue*signalValue;
			weightAbsSum += Math.abs(affinityValue);
		}

		double neighborDensityNorm = Math.min(1, visibleCount/(double) (CreatureConst.MAX_POPULATION-1));
		double nearestNeighborDistNorm = nearestNeighborDist<0 ? 1 : Math.min(1, nearestNeighborDist/CreatureConst.SENSE_RADIUS);

		double[] perception = brain.perceive(new double[] {foodDistNorm, foodCos, foodSin, wallSignalRaw, neighborDensityNorm, nearestNeighborDistNorm});
		double foodSignal = perception[0];
		double wallSignal = perception[1];
		double socialPressure = perception[2];

		double energyRatio = energy/CreatureConst.MAX_ENERGY;
		double healthRatio = health/CreatureConst.MAX_HEALTH;

		currentSignal = brain.communicate(new double[] {energyRatio, healthRatio, socialPressure, salientScore});

		double receivedSignalScore = weightAbsSum>0 ? weightedSignalSum/weightAbsSum : 0;

		double[] decisionInputs = new double[] {
			energyRatio, healthRatio, socialPressure, foodSignal, wallSignal, salientScore, receivedSignalScore, salientAffinity
		};
		currentAction = brain.decide(decisionInputs);
		if(energyRatio<CreatureConst.HUNGER_OVERRIDE_RATIO && nearestFood!=null) {
			currentAction = CreatureAction.APPROACH;
			brain.overrideLastDecision(currentAction);
		}
		lastSalient = salient;

		Angle desired = heading;
		if(currentAction==CreatureAction.FLEE && salient!=null) {
			desired = angleAway(salient.getAnchor());
		}
		else {
			Point0 target = computeTarget(nearestFood, salient);
			if(target!=null) {
				desired = angleToward(target);
			}
		}
		heading = turnToward(heading, desired, genome.getAgility());
	}

	private static final double MIN_DIRECTION_DIST = 1e-6;

	private Angle angleToward(Point0 target) {
		Point1 delta = target.pSub(getAnchor());
		if(delta.dist()<MIN_DIRECTION_DIST) return heading;
		return angleOfVector(delta.getX(), delta.getY());
	}

	private Angle angleAway(Point0 source) {
		Point1 delta = getAnchor().pSub(source);
		if(delta.dist()<MIN_DIRECTION_DIST) return heading;
		return angleOfVector(delta.getX(), delta.getY());
	}

	private static Angle angleOfVector(double x, double y) {
		return Angle.angleRad(Math.atan2(y, x));
	}

	private double signalToValue(Signal signal) {
		switch(signal) {
			case HOSTILE: return 1;
			case ALERT: return 0;
			default: return -1;
		}
	}

	private Point0 computeTarget(Food nearestFood, Creature salient) {
		switch(currentAction) {
			case APPROACH:
				if(nearestFood!=null) return nearestFood.getAnchor();
				return salient!=null ? salient.getAnchor() : null;
			case COOPERATE:
			case ATTACK:
				return salient!=null ? salient.getAnchor() : null;
			default:
				return null;
		}
	}

	private double computeWallSignal(Game game) {
		double x = getAnchor().getX();
		double y = getAnchor().getY();
		double left = x;
		double right = game.gameWidth()-x;
		double top = y;
		double bottom = game.gameHeight()-y;
		double minDist = Math.min(Math.min(left, right), Math.min(top, bottom));
		return 1 - Math.min(1, Math.max(0, minDist)/CreatureConst.WALL_SENSE_DIST);
	}

	private Angle turnToward(Angle current, Angle target, double maxStep) {
		double diff = (target.getValueRad()-current.getValueRad()) % Angle.PI2;
		if(diff<-Angle.PI) diff += Angle.PI2;
		if(diff>Angle.PI) diff -= Angle.PI2;
		if(diff>maxStep) diff = maxStep;
		if(diff<-maxStep) diff = -maxStep;
		return current.add(diff);
	}

	/*
	 * MOVEMENT
	 */

	public void applyMovement() {
		double speed = currentAction==CreatureAction.REST ? 0 : genome.getSpeed();
		getAnchor().setDerived(speed, heading);
		loseEnergy(CreatureConst.ENERGY_LOSS_IDLE + (speed>0 ? CreatureConst.ENERGY_LOSS_MOVE_FACTOR*speed : 0));
	}

	public void clampToBounds(Game game) {
		double margin = getRadius();
		Point2 a = getAnchor();
		double w = game.gameWidth();
		double h = game.gameHeight();
		if(a.getX()<margin) a.setX(margin);
		if(a.getX()>w-margin) a.setX(w-margin);
		if(a.getY()<margin) a.setY(margin);
		if(a.getY()>h-margin) a.setY(h-margin);
	}

	/*
	 * DRAW SHAPE
	 */

	protected void drawShape() {
		double r = getRadius();
		fillRoundC(getColor(), r);
		drawRoundC(Color.WHITE, r);
		drawHeadingTick(r);
		drawBar(r, 0, energyColor(ratio(energy, CreatureConst.MAX_ENERGY)), ratio(energy, CreatureConst.MAX_ENERGY));
		drawBar(r, 7, Color.WHITE, ratio(health, CreatureConst.MAX_HEALTH));
		drawActionLabel(r);
	}

	private void drawHeadingTick(double r) {
		double hx = heading.cos();
		double hy = heading.sin();
		drawLine(Color.WHITE, p(hx*r*0.4, hy*r*0.4), p(hx*r*1.6, hy*r*1.6));
	}

	private double ratio(double value, double max) {
		return Math.max(0, Math.min(1, value/max));
	}

	private void drawBar(double r, double yOffset, Color color, double ratio) {
		double barWidth = 26;
		double barHeight = 4;
		Point1 topLeft = p(-barWidth/2, r+8+yOffset);
		drawRect(Color.DARK_GRAY, topLeft, barWidth, barHeight);
		fillRect(color, topLeft, barWidth*ratio, barHeight);
	}

	private Color energyColor(double ratio) {
		int red = (int) (255*(1-ratio));
		int green = (int) (255*ratio);
		return new Color(red, green, 40);
	}

	private void drawActionLabel(double r) {
		fillRectC(new Color(0,0,0,170), p(0,-r-14), 18, 12);
		drawStringC(Color.WHITE, p(0,-r-14), actionLetters());
	}

	private String actionLetters() {
		switch(currentAction) {
			case APPROACH: return "Ap";
			case FLEE: return "Fl";
			case COOPERATE: return "Co";
			case ATTACK: return "At";
			default: return "Re";
		}
	}
}
