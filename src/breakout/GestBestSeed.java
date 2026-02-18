package breakout;

public class GestBestSeed {

    int bestSeed = 0;
    BreakoutNeuralNetwork bestNN;

    int n;

    GestBestSeed(int n) {
        GeneticAlgorithm ga = new GeneticAlgorithm(0);
        bestNN = ga.getChampion();
        this.n = n;
        gestBest();
    }

    private void gestBest() {

        for(int i = 1; i < n; i++) {
            int a = ((int) (Math.random() * 1000 * i));
            GeneticAlgorithm ga = new GeneticAlgorithm(a);
            if(ga.getChampion().getFitness() > bestNN.getFitness()) {
                bestSeed = a;
                bestNN = ga.getChampion();
            }
            System.out.println("------------------");
            System.out.println("Seed: " + ga.getChampion().getSeed());
            System.out.println("Best seed: " + bestSeed);

        }

    }

    public BreakoutNeuralNetwork getBestNN() {
        System.out.println(bestNN.getFitness());
        return bestNN;
    }


}
