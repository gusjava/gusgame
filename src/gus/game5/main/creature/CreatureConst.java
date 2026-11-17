package gus.game5.main.creature;

import java.awt.Color;

public class CreatureConst {

	/*
	 * GAME
	 */

	public static final int GAME_WIDTH = 900;
	public static final int GAME_HEIGHT = 700;
	public static final long GAME_SPEED = 15;

	/*
	 * POPULATION
	 */

	public static final int MAX_POPULATION = 6;
	public static final int INITIAL_POPULATION = 6;

	/*
	 * SIZES AND RANGES
	 */

	public static final double CREATURE_RADIUS = 14;
	public static final double SENSE_RADIUS = 220;
	public static final double INTERACT_RADIUS = 50;
	public static final double WALL_SENSE_DIST = 90;

	/*
	 * ENERGY AND HEALTH
	 */

	public static final double MAX_ENERGY = 100;
	public static final double MAX_HEALTH = 100;
	public static final double ENERGY_LOSS_IDLE = 0.035;
	public static final double ENERGY_LOSS_MOVE_FACTOR = 0.018;

	/*
	 * FOOD
	 */

	public static final double FOOD_RADIUS = 6;
	public static final double FOOD_ENERGY = 40;
	public static final int FOOD_RATE = 90;
	public static final int FOOD_INITIAL = 6;
	public static final int FOOD_MAX = 12;
	public static final Color FOOD_COLOR = new Color(225, 225, 225);

	/*
	 * REPRODUCTION
	 */

	public static final double REPRO_ENERGY_THRESHOLD = 45;
	public static final double REPRO_ENERGY_COST = 20;
	public static final double REPRO_AFFINITY_THRESHOLD = 0.12;
	public static final int REPRO_COOLDOWN = 200;

	/*
	 * AFFINITY
	 */

	public static final double AFFINITY_COOPERATE_GAIN = 0.2;
	public static final double AFFINITY_CONFLICT_LOSS = 0.12;
	public static final double AFFINITY_DECAY = 0.0002;
	public static final double COOPERATE_SHARE_RATIO = 0.15;

	/*
	 * CONFLICT
	 */

	public static final double CONFLICT_DAMAGE = 7;
	public static final int ATTACK_COOLDOWN = 45;
	public static final double ATTACK_SUCCESS_REWARD = 0.3;
	public static final double HUNGER_OVERRIDE_RATIO = 0.4;

	/*
	 * INITIAL STATE
	 */

	public static final double INITIAL_ENERGY_RATIO = 0.85;

	/*
	 * MOVEMENT TRAITS
	 */

	public static final double MIN_SPEED = 0.6;
	public static final double MAX_SPEED = 2.6;
	public static final double MIN_AGILITY = Math.toRadians(3);
	public static final double MAX_AGILITY = Math.toRadians(18);

	/*
	 * GENETICS
	 */

	public static final double MUTATION_STRENGTH = 0.4;
	public static final double MUTATION_RATE = 0.12;

	/*
	 * LEARNING
	 */

	public static final double LEARNING_RATE = 0.05;
	public static final double LEARNED_CLAMP = 1.5;

	/*
	 * IDENTITY PALETTE
	 */

	public static final Color[] PALETTE = {
		new Color(220, 60, 60),
		new Color(60, 180, 80),
		new Color(70, 130, 220),
		new Color(230, 190, 50),
		new Color(190, 80, 210),
		new Color(60, 200, 200)
	};
}
