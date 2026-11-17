package gus.game5.main.anim1;

import gus.game5.core.util.UtilRandom;
import gus.game5.main.genetic.Chromosome;

public class QueensChromosome implements Chromosome<QueensChromosome> {

	private final int[] genes;

	public QueensChromosome(int n) {
		this(randomGenes(n));
	}

	private QueensChromosome(int[] genes) {
		this.genes = genes;
	}

	private static int[] randomGenes(int n) {
		int[] genes = new int[n];
		for(int i=0;i<n;i++) genes[i] = UtilRandom.randomInt(n);
		return genes;
	}

	public int size() {
		return genes.length;
	}

	public int row(int column) {
		return genes[column];
	}

	public QueensChromosome crossover(QueensChromosome other) {
		int[] childGenes = new int[genes.length];
		for(int i=0;i<genes.length;i++) childGenes[i] = UtilRandom.randomBoolean() ? genes[i] : other.genes[i];
		return new QueensChromosome(childGenes);
	}

	public void mutate(double mutationRate) {
		for(int i=0;i<genes.length;i++) {
			if(UtilRandom.randomDouble(1.0)<mutationRate) genes[i] = UtilRandom.randomInt(genes.length);
		}
	}

	public QueensChromosome copy() {
		return new QueensChromosome(genes.clone());
	}

}
