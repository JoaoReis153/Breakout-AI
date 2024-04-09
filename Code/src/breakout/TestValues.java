package breakout;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import utils.Commons;

public class TestValues {

	GroupOfGA best = null;
	int n = Commons.N_VALUES_TO_TEST;
	
	GroupOfGA[] arr = new GroupOfGA[n];

	
	public TestValues() {
		best = new GroupOfGA(Commons.INITIALDIVERSITY, Commons.MUTATION_RATE, Commons.MUTATIONMAGNITUDE, Commons.SELECTION_PERCENTAGE);
		initializeGroupsOfGAs();
	}

	private void initializeGroupsOfGAs() {
		// TODO Auto-generated method stub
		for(int i = 1; i < n+1; i++) {
			System.out.println("########################################################################################");
			System.out.println("Best until now:");
			System.out.println(best);
			System.out.println("########################################################################################");
			System.out.println("Test " + i + "\n");
			
			double INITIALDIVERSITY = 1 + Math.random();
			double MUTATION_RATE =  0.01 + 0.1 * Math.random();
			double MUTATIONMAGNITUDE = Math.random();//(n/(n-i) * Math.random());
			double SELECTION_PERCENTAGE =  0.2 + 0.3 * Math.random();

			//double INITIALDIVERSITY = Math.abs(best.getINITIALDIVERSITY() + ((2 * Math.random()) - 1) * .2);
			//double MUTATION_RATE =  Math.min(Math.abs(best.getMUTATION_RATE() + ((2 * Math.random()) - 1) * .2), 1) ;
			//double MUTATIONMAGNITUDE = Math.abs(best.getMUTATIONMAGNITUDE() + ((2 * Math.random()) - 1) * .2);
			//double SELECTION_PERCENTAGE = Math.min(Math.abs(best.getSELECTION_PERCENTAGE()+ ((2 * Math.random()) - 1) * .2), 1);
			
			System.out.println("initialDiversity: " + INITIALDIVERSITY);
			System.out.println("mutationRate: " + MUTATION_RATE);
			System.out.println("mutationMagnitude: " + MUTATIONMAGNITUDE);
			System.out.println("selectionPercentage: " + SELECTION_PERCENTAGE);
			GroupOfGA NEW = new GroupOfGA(INITIALDIVERSITY, MUTATION_RATE, MUTATIONMAGNITUDE, SELECTION_PERCENTAGE);
			if(NEW.getResult() > best.getResult()) 
				best = NEW;
			
			arr[i] = NEW;
		}
		System.out.println("\n\n\n\n");
		Arrays.sort(arr);
		for(GroupOfGA a : arr) {			
			System.out.println("------------------------------------");
			System.out.println(a);
		}
	}

	public GroupOfGA getBest() {
		return best;
	}
	

	
	
}
