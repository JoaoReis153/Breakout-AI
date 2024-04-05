package breakout;

import java.util.Random;

import utils.Commons;

public class GroupOfGA {

	private double MUTATION_RATE = Commons.MUTATION_RATE;
	private double MUTATIONMAGNITUDE = Commons.MUTATIONMAGNITUDE;
	private double SELECTION_PERCENTAGE = Commons.SELECTION_PERCENTAGE;
	private double INITIALDIVERSITY = Commons.INITIALDIVERSITY;
	private int kSelectionPoints = Commons.N_CROSSOVERPOINTS;
	
	
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

	private void initializeGeneticAlgoirthms() {
		// TODO Auto-generated method stub
		for(int i = 0; i < gaList.length; i++) {
			//int s = (int) (i * Math.random() * 100000);
			int s = r.nextInt(10000);
			gaList[i] = new GeneticAlgorithm(s, INITIALDIVERSITY, MUTATION_RATE, MUTATIONMAGNITUDE, SELECTION_PERCENTAGE, kSelectionPoints);
		}
	}

	public double getResult() {
		double sum = 0;
		for(int i = 0; i < gaList.length; i++) {
			sum += gaList[i].getChampion().getFitness();
		}
		
		return sum/n;
	}

	@Override
	public String toString() {
		return "GroupOfGA [\nkSelectionPoints = " + kSelectionPoints + "\nINITIALDIVERSITY=" + INITIALDIVERSITY + "MUTATION_RATE=" + MUTATION_RATE + "\n MUTATIONMAGNITUDE=" + MUTATIONMAGNITUDE
				+ "\n SELECTION_PERCENTAGE=" + SELECTION_PERCENTAGE + "\n]";
	}
	
	
	
}
