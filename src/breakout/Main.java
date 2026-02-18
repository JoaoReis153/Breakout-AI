package breakout;

import utils.Commons;

public class Main {

    public static void main(String[] args) {

        if (Commons.GETBESTSEED) {
            GestBestSeed a = new GestBestSeed(Commons.N_GETBEST);
            new Breakout(a.getBestNN(), a.getBestNN().getSeed());
        }

        //BreakoutNeuralNetwork n = new BreakoutNeuralNetwork();
        if (Commons.TESTDIFFERENTVALUES) {
            System.out.println("Testing different values...\n\n");
            TestValues test = new TestValues();
            System.out.println("\n\nBest average: " + test.getBest().getResult());
            System.out.println(test.getBest());
        }

        if (Commons.TESTDIFFERENTSEEDS) {
            GroupOfGA tdiffSeeds = new GroupOfGA(0, 0, 0, 0);
            System.out.println();
            System.out.println();
            System.out.println("Average: ");
            System.out.println("- " + tdiffSeeds.getResult());
        }


        if (Commons.DEFAULT) {
            GeneticAlgorithm ga = new GeneticAlgorithm();
            BreakoutNeuralNetwork nn = ga.getChampion();
            new Breakout(nn, nn.getSeed());
        }

    }
}
