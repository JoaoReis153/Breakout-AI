package breakout;

import utils.Commons;

import java.util.Random;

public class GroupOfGA implements Comparable<GroupOfGA> {

    private double MUTATION_RATE = Commons.MUTATION_RATE;
    private double MUTATIONMAGNITUDE = Commons.MUTATIONMAGNITUDE;
    private double MUTATION_CHANGE_PERCENTAGE = Commons.MUTATION_CHANGE_PERCENTAGE;
    private double SELECTION_PERCENTAGE = Commons.SELECTION_PERCENTAGE;
    private double average = 0;


    int n = Commons.N_SEEDS;

    Random r = new Random(Commons.INIITIAL_SEED);

    GeneticAlgorithm[] gaList = new GeneticAlgorithm[n];

    public GroupOfGA(double mutationRate, double mutationChangePercentage, double mutationMagnitude, double selectionPercentage) {
        this.MUTATION_CHANGE_PERCENTAGE = mutationChangePercentage != 0 ? mutationChangePercentage : Commons.MUTATION_CHANGE_PERCENTAGE;
        this.MUTATION_RATE = mutationRate != 0 ? mutationRate : Commons.MUTATION_RATE;
        this.MUTATIONMAGNITUDE = mutationMagnitude != 0 ? mutationMagnitude : Commons.MUTATIONMAGNITUDE;
        this.SELECTION_PERCENTAGE = selectionPercentage != 0 ? selectionPercentage : Commons.SELECTION_PERCENTAGE;
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

    public double getMUTATIONCHANGEPERCENTAGE() {
        return MUTATION_CHANGE_PERCENTAGE;
    }

    private void initializeGeneticAlgoirthms() {
        // TODO Auto-generated method stub
        for (int i = 0; i < gaList.length; i++) {
            gaList[i] = new GeneticAlgorithm(r.nextInt(1111), MUTATION_RATE, MUTATION_CHANGE_PERCENTAGE, MUTATIONMAGNITUDE, SELECTION_PERCENTAGE);
        }
    }

    public double getResult() {
        double sum = 0;
        for (int i = 0; i < gaList.length; i++) {
            sum += gaList[i].getChampion().getFitness();
        }
        average = sum / n;
        return average;
    }

    @Override
    public String toString() {
        return "GroupOfGA (" + average + ") [\n MUTATION_RATE=" + MUTATION_RATE + "\n MUTATIONCHANGEPERCENTAGE=" + MUTATION_CHANGE_PERCENTAGE + "\n MUTATIONMAGNITUDE=" + MUTATIONMAGNITUDE
                + "\n SELECTION_PERCENTAGE=" + SELECTION_PERCENTAGE + "\n]";
    }

    @Override
    public int compareTo(GroupOfGA o) {
        return Double.compare(getResult(), o.getResult());
    }


}
