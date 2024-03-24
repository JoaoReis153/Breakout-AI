package breakout;

import utils.Commons;

public class Main {

	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		BreakoutNeuralNetwork nn = ga.getBestNeuralNetwork();
		
		//BreakoutNeuralNetwork n = new BreakoutNeuralNetwork();
		Breakout a = new Breakout(nn, Commons.SEED);
		
	}
}
