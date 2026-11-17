package gus.game5.main.creature;

public class NeuroMath {

	/*
	 * LAYER SIZE
	 */

	public static int layerSize(int inputs, int outputs) {
		return outputs*(inputs+1);
	}

	/*
	 * COMPUTE LAYER
	 */

	public static double[] computeLayer(double[] inherited, double[] learned, int offset, int inputs, int outputs, double[] in) {
		double[] out = new double[outputs];
		for(int o=0;o<outputs;o++) {
			int base = offset + o*(inputs+1);
			double sum = inherited[base+inputs] + learned[base+inputs];
			for(int i=0;i<inputs;i++) {
				sum += (inherited[base+i]+learned[base+i]) * in[i];
			}
			out[o] = Math.tanh(sum);
		}
		return out;
	}

	/*
	 * ADJUST LAYER
	 */

	public static void adjustLayer(double[] learned, int offset, int inputs, int outputs, double[] in, int chosen, double reward, double learningRate, double clamp) {
		int base = offset + chosen*(inputs+1);
		for(int i=0;i<inputs;i++) {
			learned[base+i] = clampValue(learned[base+i] + learningRate*reward*in[i], clamp);
		}
		learned[base+inputs] = clampValue(learned[base+inputs] + learningRate*reward, clamp);
	}

	/*
	 * SIGMOID
	 */

	public static double sigmoid(double x) {
		return 1/(1+Math.exp(-x));
	}

	/*
	 * ARGMAX
	 */

	public static int argmax(double[] values) {
		int best = 0;
		for(int i=1;i<values.length;i++) {
			if(values[i]>values[best]) best = i;
		}
		return best;
	}

	/*
	 * CLAMP VALUE
	 */

	private static double clampValue(double value, double clamp) {
		if(value>clamp) return clamp;
		if(value<-clamp) return -clamp;
		return value;
	}
}
