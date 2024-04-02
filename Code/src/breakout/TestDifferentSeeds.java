package breakout;

import java.util.Random;

public class TestDifferentSeeds {

	int n = 6;
	long s = 1000L;
	
	Random r = new Random(s);
	
	GeneticAlgorithm[] gaList = new GeneticAlgorithm[n];
	
	public TestDifferentSeeds() {
		initializeGeneticAlgoirthms();
	}

	private void initializeGeneticAlgoirthms() {
		// TODO Auto-generated method stub
		for(int i = 0; i < gaList.length; i++) {
			//int s = (int) (i * Math.random() * 100000);
			int s = r.nextInt(10000);
			gaList[i] = new GeneticAlgorithm(s);
		}
	}

	public double getResult() {
		double sum = 0;
		for(int i = 0; i < gaList.length; i++) {
			sum += gaList[i].getChampion().getFitness();
		}
		
		return sum/n;
	}
	
	
	
}
