package gus.game5.main.genetic;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilRandom;

public class Population<C extends Chromosome<C>> {

	/*
	 * PARAMETERS
	 */

	private final FitnessFunction<C> fitnessFunction;
	private final double mutationRate;
	private final int tournamentSize;
	private final int elitismCount;

	/*
	 * STATE
	 */

	private List<C> chromosomes;
	private double[] fitness;
	private int bestIndex;
	private int generation;

	public Population(List<C> initialChromosomes, FitnessFunction<C> fitnessFunction,
			double mutationRate, int tournamentSize, int elitismCount) {
		this.chromosomes = initialChromosomes;
		this.fitnessFunction = fitnessFunction;
		this.mutationRate = mutationRate;
		this.tournamentSize = tournamentSize;
		this.elitismCount = elitismCount;
		this.fitness = new double[chromosomes.size()];
		evaluate();
	}

	/*
	 * EVALUATE (lower fitness is better)
	 */

	private void evaluate() {
		bestIndex = 0;
		for(int i=0;i<chromosomes.size();i++) {
			fitness[i] = fitnessFunction.evaluate(chromosomes.get(i));
			if(fitness[i]<fitness[bestIndex]) bestIndex = i;
		}
	}

	/*
	 * NEXT GENERATION
	 */

	public void nextGeneration() {
		int size = chromosomes.size();
		List<C> next = new ArrayList<>(size);

		for(Integer eliteIndex : eliteOrder()) {
			if(next.size()>=elitismCount) break;
			next.add(chromosomes.get(eliteIndex).copy());
		}

		while(next.size()<size) {
			C parent1 = chromosomes.get(tournament());
			C parent2 = chromosomes.get(tournament());
			C child = parent1.crossover(parent2);
			child.mutate(mutationRate);
			next.add(child);
		}

		chromosomes = next;
		fitness = new double[size];
		evaluate();
		generation++;
	}

	private List<Integer> eliteOrder() {
		List<Integer> order = new ArrayList<>();
		for(int i=0;i<chromosomes.size();i++) order.add(i);
		order.sort((a,b)->Double.compare(fitness[a], fitness[b]));
		return order;
	}

	private int tournament() {
		int best = UtilRandom.randomInt(chromosomes.size());
		for(int t=1;t<tournamentSize;t++) {
			int candidate = UtilRandom.randomInt(chromosomes.size());
			if(fitness[candidate]<fitness[best]) best = candidate;
		}
		return best;
	}

	/*
	 * ACCESS
	 */

	public C getBest() {
		return chromosomes.get(bestIndex);
	}

	public double getBestFitness() {
		return fitness[bestIndex];
	}

	public int getGeneration() {
		return generation;
	}

}
