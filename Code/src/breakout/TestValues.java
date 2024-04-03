package breakout;

import java.util.Random;

import utils.Commons;

public class TestValues {
	
	double bestAverage = 0;
	GroupOfGA best = null;

	int n = 4;
	long s = 1000L;
	
	
	Random r = new Random(s);
	
	
	public TestValues(int n) {
		this.n = n;
		initializeGroupsOfGAs();
	}

	private void initializeGroupsOfGAs() {
		// TODO Auto-generated method stub
		for(int i = 0; i < n; i++) {
			System.out.println("########################################################################################");
			System.out.println("Test " + i + "\n");
			double INITIALDIVERSITY = Math.random();
			double MUTATION_RATE = Math.random();
			double MUTATIONMAGNITUDE = Math.random();
			double SELECTION_PERCENTAGE = Math.random();
			System.out.println("initialDiversity: " + INITIALDIVERSITY);
			System.out.println("mutationRate: " + MUTATION_RATE);
			System.out.println("mutationMagnitude: " + MUTATIONMAGNITUDE);
			System.out.println("selectionPercentage: " + SELECTION_PERCENTAGE);
			GroupOfGA NEW = new GroupOfGA(INITIALDIVERSITY, MUTATION_RATE, MUTATIONMAGNITUDE, SELECTION_PERCENTAGE);
			if(NEW.getResult() > bestAverage) {
				best = NEW;
				bestAverage = best.getResult();
			}
		}
	}

	public GroupOfGA getBest() {
		return best;
	}
	
	
	
}
