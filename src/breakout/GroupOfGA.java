package breakout;

import java.util.Random;

import org.ietf.jgss.Oid;

import utils.Commons;

public class GroupOfGA implements Comparable<GroupOfGA>{

	private double MUTATION_RATE = Commons.MUTATION_RATE;
	private double INITIAL_MUTATION_PERCENTAGE = Commons.INITIAL_MUTATION_PERCENTAGE;
	private double SELECTION_PERCENTAGE = Commons.SELECTION_PERCENTAGE;
	private double average = 0;
	
	
	int nSeeds;
	
	Random r = new Random();
	
	BreakoutGeneticAlgorithm[] gaList;
	
	public GroupOfGA(int nSeeds, double mutationRate, double mutationChangePercentage, double selectionPercentage) {
		this.nSeeds = nSeeds;
		gaList = new BreakoutGeneticAlgorithm[nSeeds];
		this.INITIAL_MUTATION_PERCENTAGE = mutationChangePercentage != 0 ? mutationChangePercentage : Commons.INITIAL_MUTATION_PERCENTAGE;
		this.MUTATION_RATE = mutationRate != 0 ? mutationRate : Commons.MUTATION_RATE;
		this.SELECTION_PERCENTAGE = selectionPercentage != 0 ? selectionPercentage : Commons.SELECTION_PERCENTAGE;
		initializeGeneticAlgoirthms();
	}

	public double getMUTATION_RATE() {
		return MUTATION_RATE;
	}

	public double getSELECTION_PERCENTAGE() {
		return SELECTION_PERCENTAGE;
	}

	public double getMUTATIONCHANGEPERCENTAGE() {
		return INITIAL_MUTATION_PERCENTAGE;
	}
	
	private void initializeGeneticAlgoirthms() {
		// TODO Auto-generated method stub
		for(int i = 0; i < gaList.length; i++) {
			//gaList[i] = new BreakoutGeneticAlgorithm(r.nextInt(1111), MUTATION_RATE, INITIAL_MUTATION_PERCENTAGE, SELECTION_PERCENTAGE);
		}
	}

	public double getResult() {
		double sum = 0;
		for(int i = 0; i < gaList.length; i++) {
			sum += gaList[i].getChampion().getFitness();
		}
		average = sum/nSeeds;
		return average;
	}

	@Override
	public String toString() {
		return "GroupOfGA (" + average + ") [\n MUTATION_RATE=" + MUTATION_RATE + "\n MUTATIONCHANGEPERCENTAGE=" + INITIAL_MUTATION_PERCENTAGE + "\n SELECTION_PERCENTAGE=" + SELECTION_PERCENTAGE + "\n]";
	}

	@Override
	public int compareTo(GroupOfGA o) {
		return Double.compare(getResult(), o.getResult());
	}

	
	
	
}
