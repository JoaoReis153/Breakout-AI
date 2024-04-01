package breakout;

public class TestDifferentSeeds {

	private static int[] seeds = {2, 22, 223, 2234, 22345, 223456};
	GeneticAlgorithm[] gaList = new GeneticAlgorithm[seeds.length];
	
	public TestDifferentSeeds() {
		initializeGeneticAlgoirthms();
	}

	private void initializeGeneticAlgoirthms() {
		// TODO Auto-generated method stub
		for(int i = 0; i < gaList.length; i++) {
			//int s = (int) (i * Math.random() * 100000);
			int s = seeds[i];
			gaList[i] = new GeneticAlgorithm(s);
		}
	}

	public double getResult() {
		double sum = 0;
		for(int i = 0; i < gaList.length; i++) {
			sum += gaList[i].getChampion().getFitness();
		}
		
		return sum/seeds.length;
	}
	
	
	
}
