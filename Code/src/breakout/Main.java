package breakout;

import utils.Commons;

public class Main {

	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		BreakoutNeuralNetwork nn = ga.getChampion();
		
		//BreakoutNeuralNetwork n = new BreakoutNeuralNetwork();
		Breakout a = new Breakout(nn, Commons.SEED);
		
		
		//ConstantsGetter cgetter = new ConstantsGetter(100);
		//System.out.println("getter.getValue());
	}
}
