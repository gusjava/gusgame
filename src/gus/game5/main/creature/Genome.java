package gus.game5.main.creature;

import gus.game5.core.util.UtilRandom;
import gus.game5.main.genetic.Chromosome;

public class Genome implements Chromosome<Genome> {

	/*
	 * MODULE DIMENSIONS
	 */

	public static final int PERCEPTION_INPUTS = 6;
	public static final int PERCEPTION_OUTPUTS = 3;
	public static final int DANGER_INPUTS = 3;
	public static final int DANGER_OUTPUTS = 1;
	public static final int COMM_INPUTS = 4;
	public static final int COMM_OUTPUTS = 3;
	public static final int DECISION_INPUTS = 8;
	public static final int DECISION_OUTPUTS = 5;

	/*
	 * GENE LAYOUT
	 */

	public static final int PERCEPTION_OFFSET = 0;
	public static final int DANGER_OFFSET = PERCEPTION_OFFSET + NeuroMath.layerSize(PERCEPTION_INPUTS, PERCEPTION_OUTPUTS);
	public static final int COMM_OFFSET = DANGER_OFFSET + NeuroMath.layerSize(DANGER_INPUTS, DANGER_OUTPUTS);
	public static final int DECISION_OFFSET = COMM_OFFSET + NeuroMath.layerSize(COMM_INPUTS, COMM_OUTPUTS);
	public static final int SPEED_INDEX = DECISION_OFFSET + NeuroMath.layerSize(DECISION_INPUTS, DECISION_OUTPUTS);
	public static final int AGILITY_INDEX = SPEED_INDEX + 1;
	public static final int TOTAL_SIZE = AGILITY_INDEX + 1;

	private final double[] genes;

	public Genome(double[] genes) {
		this.genes = genes;
	}

	/*
	 * RANDOM
	 */

	public static Genome random() {
		double[] g = new double[TOTAL_SIZE];
		for(int i=0;i<g.length;i++) {
			g[i] = UtilRandom.randomDouble(-1, 1);
		}
		return new Genome(g);
	}

	/*
	 * GENES
	 */

	public double[] getGenes() {
		return genes;
	}

	/*
	 * SPEED
	 */

	public double getSpeed() {
		return CreatureConst.MIN_SPEED + (CreatureConst.MAX_SPEED-CreatureConst.MIN_SPEED)*NeuroMath.sigmoid(genes[SPEED_INDEX]);
	}

	/*
	 * AGILITY
	 */

	public double getAgility() {
		return CreatureConst.MIN_AGILITY + (CreatureConst.MAX_AGILITY-CreatureConst.MIN_AGILITY)*NeuroMath.sigmoid(genes[AGILITY_INDEX]);
	}

	/*
	 * CROSSOVER
	 */

	public Genome crossover(Genome other) {
		double[] childGenes = new double[TOTAL_SIZE];
		for(int i=0;i<TOTAL_SIZE;i++) {
			childGenes[i] = UtilRandom.randomBoolean() ? genes[i] : other.genes[i];
		}
		return new Genome(childGenes);
	}

	/*
	 * MUTATE
	 */

	public void mutate(double mutationRate) {
		for(int i=0;i<genes.length;i++) {
			if(UtilRandom.randomDouble(1.0)<mutationRate) {
				genes[i] += UtilRandom.randomDouble(-CreatureConst.MUTATION_STRENGTH, CreatureConst.MUTATION_STRENGTH);
			}
		}
	}

	/*
	 * COPY
	 */

	public Genome copy() {
		return new Genome(genes.clone());
	}
}
