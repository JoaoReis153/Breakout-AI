package breakout;

import utils.Commons;

public class Main {

	public static void main(String[] args) {
		
		//BreakoutNeuralNetwork n = new BreakoutNeuralNetwork();
		if(Commons.TESTDIFFERENTVALUES) {
			System.out.println("Testing different values...\n\n");
			TestValues test = new TestValues(15);
			System.out.println("\n\nBest values: ");
			System.out.println(test.getBest());
		}
 		
		if(Commons.TESTDIFFERENTSEEDS) {
			GroupOfGA tdiffSeeds = new GroupOfGA(0,0,0,0);
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
