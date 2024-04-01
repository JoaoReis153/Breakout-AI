package breakout;

import utils.Commons;

public class Main {

	public static void main(String[] args) {
		
		//BreakoutNeuralNetwork n = new BreakoutNeuralNetwork();
		if(Commons.TESTDIFFERENTSEEDS) {
			TestDifferentSeeds tdiffSeeds = new TestDifferentSeeds();
			System.out.println();
			System.out.println();
			System.out.println("Average: ");
			System.out.println("- " + tdiffSeeds.getResult());
		}
		
		
		if(Commons.DEFAULT) {			
			GeneticAlgorithm ga = new GeneticAlgorithm();
			BreakoutNeuralNetwork nn = ga.getChampion();
			Breakout a = new Breakout(nn, nn.getSeed());
		}
		
	}
}
