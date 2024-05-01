package breakout;

public class Main {

	public static void main(String[] args) {

		BreakoutGeneticAlgorithm ga = new BreakoutGeneticAlgorithm(1);
		BreakoutNeuralNetwork nn = ga.getChampion();
		new Breakout(nn, ga.getSeed());


	}
}
