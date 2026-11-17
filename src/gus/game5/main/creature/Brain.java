package gus.game5.main.creature;

public class Brain {

	private final Genome genome;
	private final double[] learned = new double[Genome.TOTAL_SIZE];

	private double[] lastDecisionInputs;
	private int lastDecisionIndex = -1;

	public Brain(Genome genome) {
		this.genome = genome;
	}

	public Genome getGenome() {
		return genome;
	}

	/*
	 * PERCEIVE
	 */

	public double[] perceive(double[] inputs) {
		return NeuroMath.computeLayer(genome.getGenes(), learned, Genome.PERCEPTION_OFFSET, Genome.PERCEPTION_INPUTS, Genome.PERCEPTION_OUTPUTS, inputs);
	}

	/*
	 * EVALUATE DANGER OPPORTUNITY
	 */

	public double evaluateDangerOpportunity(double[] inputs) {
		return NeuroMath.computeLayer(genome.getGenes(), learned, Genome.DANGER_OFFSET, Genome.DANGER_INPUTS, Genome.DANGER_OUTPUTS, inputs)[0];
	}

	/*
	 * COMMUNICATE
	 */

	public Signal communicate(double[] inputs) {
		double[] logits = NeuroMath.computeLayer(genome.getGenes(), learned, Genome.COMM_OFFSET, Genome.COMM_INPUTS, Genome.COMM_OUTPUTS, inputs);
		return Signal.values()[NeuroMath.argmax(logits)];
	}

	/*
	 * DECIDE
	 */

	public CreatureAction decide(double[] inputs) {
		double[] logits = NeuroMath.computeLayer(genome.getGenes(), learned, Genome.DECISION_OFFSET, Genome.DECISION_INPUTS, Genome.DECISION_OUTPUTS, inputs);
		int index = NeuroMath.argmax(logits);
		lastDecisionInputs = inputs.clone();
		lastDecisionIndex = index;
		return CreatureAction.values()[index];
	}

	/*
	 * OVERRIDE LAST DECISION
	 */

	public void overrideLastDecision(CreatureAction action) {
		lastDecisionIndex = action.ordinal();
	}

	/*
	 * LEARN FROM REWARD
	 */

	public void learnFromReward(double reward) {
		if(lastDecisionInputs==null) return;
		NeuroMath.adjustLayer(learned, Genome.DECISION_OFFSET, Genome.DECISION_INPUTS, Genome.DECISION_OUTPUTS, lastDecisionInputs, lastDecisionIndex, reward, CreatureConst.LEARNING_RATE, CreatureConst.LEARNED_CLAMP);
	}
}
