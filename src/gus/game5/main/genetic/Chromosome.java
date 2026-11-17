package gus.game5.main.genetic;

public interface Chromosome<C extends Chromosome<C>> {

	C crossover(C other);

	void mutate(double mutationRate);

	C copy();

}
