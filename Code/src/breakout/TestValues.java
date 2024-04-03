package breakout;

import java.util.Random;

import utils.Commons;

public class TestValues {
	
	double bestAverage = 0;
	GroupOfGA best = null;

	int n = 10;
	long s = 10L;
	
	public TestValues(int n) {
		this.n = n;
		initializeGroupsOfGAs();
	}

	private void initializeGroupsOfGAs() {
		// TODO Auto-generated method stub
		for(int i = 0; i < n; i++) {
			System.out.println("########################################################################################");
			System.out.println("Test " + i + "\n");
			double INITIALDIVERSITY = Math.abs(Commons.INITIALDIVERSITY + ((2 * Math.random()) - 1) * 0.5) ;
			double MUTATION_RATE =  Math.abs(Commons.MUTATION_RATE + ((2 * Math.random()) - 1) * 0.5) ;
			double MUTATIONMAGNITUDE =  Math.abs(Commons.MUTATIONMAGNITUDE + ((2 * Math.random()) - 1) * 0.5);
			double SELECTION_PERCENTAGE =  Math.abs(Commons.SELECTION_PERCENTAGE + ((2 * Math.random()) - 1) * 0.5);
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
