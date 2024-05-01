package breakout;

import utils.Commons;

import java.util.Arrays;

public class TestValues {

	GroupOfGA best;
	int nTests;
	int nSeeds;
	
	GroupOfGA[] arr;

	
	public TestValues(int nTests, int nSeeds) {
		this.nTests = nTests;
		this.nSeeds = nSeeds;
		this.arr = new GroupOfGA[nTests];
		best = new GroupOfGA(nSeeds, Commons.MUTATION_RATE, Commons.MUTATION_PERCENTAGE, Commons.SELECTION_PERCENTAGE);
		initializeGroupsOfGAs();
	}

	private void initializeGroupsOfGAs() {
		// TODO Auto-generated method stub
		for(int i = 1; i < nTests+1; i++) {
			System.out.println("########################################################################################");
			System.out.println("Best until now:");
			System.out.println(best);
			System.out.println("########################################################################################");
			System.out.println("Test " + i + "\n");
			

			double MUTATION_RATE_range = ((2 * Math.random()) - 1) * 0.1;
			double MUTATIONCHANGEPERCENTAGE_range = ((2 * Math.random()) - 1) * 0.1;
			double MUTATIONMAGNITUDE_range = ((2 * Math.random()) - 1) * 1;
			double SELECTION_PERCENTAGE_range = ((2 * Math.random()) - 1) *  0.1;
			

			double MUTATION_RATE =  0.05 + 0.4 * Math.random();//Math.abs(Commons.MUTATION_RATE + ((2 * Math.random()) - 1) * 0.2) ;
			double MUTATIONCHANGEPERCENTAGE = 0.2 + 0.8 * Math.random();
			double MUTATIONMAGNITUDE = 1 * Math.random();//(n/(n-i) * Math.random());//Math.abs(Commons.MUTATIONMAGNITUDE + ((2 * Math.random()) - 1) * 0.2);
			double SELECTION_PERCENTAGE =  0.1 + 0.4 * Math.random();//Math.abs(Commons.SELECTION_PERCENTAGE + ((2 * Math.random()) - 1) * 0.1);
			
			if(best.getResult() > 100000) {
				System.out.println("yuppi!");
				MUTATION_RATE =  Math.min(Math.abs(best.getMUTATION_RATE() + MUTATION_RATE_range), .6) ;
				MUTATIONCHANGEPERCENTAGE = Math.min(Math.max(best.getMUTATIONCHANGEPERCENTAGE() + MUTATIONCHANGEPERCENTAGE_range, .2), 1);
				SELECTION_PERCENTAGE = Math.min(Math.max(Math.abs(best.getSELECTION_PERCENTAGE() + SELECTION_PERCENTAGE_range), .05), 1);
				
			}

			System.out.println("mutationRate: " + MUTATION_RATE);
			System.out.println("mutationChangePercentage: " + MUTATIONCHANGEPERCENTAGE);
			System.out.println("mutationMagnitude: " + MUTATIONMAGNITUDE);
			System.out.println("selectionPercentage: " + SELECTION_PERCENTAGE);
			GroupOfGA NEW = new GroupOfGA(nSeeds, MUTATIONCHANGEPERCENTAGE,MUTATIONMAGNITUDE, SELECTION_PERCENTAGE);
			//GroupOfGA NEW = new GroupOfGA(0, 0, 0, MUTATIONMAGNITUDE, 0);
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
