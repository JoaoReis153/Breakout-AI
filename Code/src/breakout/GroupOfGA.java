package breakout;

import java.util.Random;

import org.ietf.jgss.Oid;

import utils.Commons;

public class GroupOfGA implements Comparable<GroupOfGA>{

	private double MUTATION_RATE = Commons.MUTATION_RATE;
	private double MUTATIONMAGNITUDE = Commons.MUTATIONMAGNITUDE;
	private double SELECTION_PERCENTAGE = Commons.SELECTION_PERCENTAGE;
	private double INITIALDIVERSITY = Commons.INITIALDIVERSITY;
	private double average = 0;
	
	
	int n = Commons.N_SEEDS;
	
	Random r = new Random(Commons.DIFFERENTSEEDS_RANDOMSEEDGENERATOR);
	
	GeneticAlgorithm[] gaList = new GeneticAlgorithm[n];
	
	public GroupOfGA(double initialDiversity, double mutationRate, double mutationMagnitude, double selectionPercentage) {
		this.MUTATION_RATE = mutationRate != 0 ? mutationRate : Commons.MUTATION_RATE;
		this.MUTATIONMAGNITUDE = mutationMagnitude != 0 ? mutationMagnitude : Commons.MUTATIONMAGNITUDE;
		this.SELECTION_PERCENTAGE = selectionPercentage != 0 ? selectionPercentage : Commons.SELECTION_PERCENTAGE;
    	this.INITIALDIVERSITY = initialDiversity != 0 ? initialDiversity : Commons.INITIALDIVERSITY;
		initializeGeneticAlgoirthms();
	}

	public double getMUTATION_RATE() {
		return MUTATION_RATE;
	}

	public double getMUTATIONMAGNITUDE() {
		return MUTATIONMAGNITUDE;
	}

	public double getSELECTION_PERCENTAGE() {
		return SELECTION_PERCENTAGE;
	}

	public double getINITIALDIVERSITY() {
		return INITIALDIVERSITY;
	}

	private void initializeGeneticAlgoirthms() {
		// TODO Auto-generated method stub
		for(int i = 0; i < gaList.length; i++) {
			//int s = (int) (i * Math.random() * 100000);
			int s = r.nextInt(10000);
			gaList[i] = new GeneticAlgorithm(s, INITIALDIVERSITY, MUTATION_RATE, MUTATIONMAGNITUDE, SELECTION_PERCENTAGE);
		}
	}

	public double getResult() {
		double sum = 0;
		for(int i = 0; i < gaList.length; i++) {
			sum += gaList[i].getChampion().getFitness();
		}
		average = sum/n;
		return average;
	}

	@Override
	public String toString() {
		return "GroupOfGA (" + average + ") [\n INITIALDIVERSITY=" + INITIALDIVERSITY + "\n MUTATION_RATE=" + MUTATION_RATE + "\n MUTATIONMAGNITUDE=" + MUTATIONMAGNITUDE
				+ "\n SELECTION_PERCENTAGE=" + SELECTION_PERCENTAGE + "\n]";
	}

	@Override
	public int compareTo(GroupOfGA o) {
		return Double.compare(getResult(), o.getResult());
	}
	
	
	
}
