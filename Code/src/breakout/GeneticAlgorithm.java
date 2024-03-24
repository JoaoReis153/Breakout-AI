package breakout;

import java.util.Arrays;

import utils.Commons;

public class GeneticAlgorithm {

		private static int POPULATION_SIZE = Commons.POPULATION_SIZE;
		private static int NUM_GENERATIONS = Commons.NUM_GENERATIONS;
		private static double MUTATION_RATE = Commons.MUTATION_RATE;
		private static double MUTATIOMAGNITUDE = Commons.MUTATIOMAGNITUDE;
		private static double SELECTION_PERCENTAGE = Commons.SELECTION_PERCENTAGE;
		private BreakoutNeuralNetwork[] population = new BreakoutNeuralNetwork[POPULATION_SIZE];
		
		private BreakoutNeuralNetwork bestNeuralNetwork;
		
		public int totalGeracoes = 0;

		private static int k_tournament = 6;

	    GeneticAlgorithm(){
	        generatePopulation();
	        bestNeuralNetwork = search();
	        System.out.println(bestNeuralNetwork);
	    }
	    
	    public BreakoutNeuralNetwork getBestNeuralNetwork() {
			return bestNeuralNetwork;
	    }


	    private void generatePopulation() {
	        for(int i = 0; i < POPULATION_SIZE; i++){
	            population[i] = new BreakoutNeuralNetwork();;
	        }
	    }
	    
	    private void getBest(BreakoutNeuralNetwork nn) {
    		if(nn.getFitness() > bestNeuralNetwork.getFitness()) {
    			bestNeuralNetwork = new BreakoutNeuralNetwork(nn.getNeuralNetwork());
    			System.out.println("Best: " + bestNeuralNetwork.getFitness());
    		}
	    }
	    
	    private BreakoutNeuralNetwork getBestCopy() {
	    	BreakoutNeuralNetwork bb = new BreakoutNeuralNetwork(bestNeuralNetwork.getNeuralNetwork());
	    	return bb;
	    }
	    
	    private void printPopulation() {
	    	for(int i = 0; i <population.length; i++) 
	    		System.out.print(population[i].getFitness() + "  ");
	    	System.out.println("");
	    }

	    private BreakoutNeuralNetwork search() {
	    	bestNeuralNetwork = population[0];
			for (int i = 0; i < NUM_GENERATIONS; i++) {
				BreakoutNeuralNetwork[] newGeneration = new BreakoutNeuralNetwork[POPULATION_SIZE];
				Arrays.sort(population);
				System.out.println("\nGen: " + i);
		
				getBest(population[0]);
				
				printPopulation();
					
				
				for (int j = 2; j < POPULATION_SIZE ; j+=2) {
					BreakoutNeuralNetwork parent1 = selectParent();
					BreakoutNeuralNetwork parent2 = selectParent();
					BreakoutNeuralNetwork[] children = crossover(parent1, parent2);
					newGeneration[j] = children[0];
					newGeneration[j + 1] = children[1];
					
					newGeneration[j] = mutate(newGeneration[j]);
					newGeneration[j + 1]  = mutate(newGeneration[j + 1]);

					
				}
			
				newGeneration[0] = getBestCopy();
				newGeneration[1] = mutate(getBestCopy());
				population = newGeneration;

			}
			return bestNeuralNetwork;
		}
	    
	
	    private BreakoutNeuralNetwork mutate(BreakoutNeuralNetwork individual) {
	        double[] genes = individual.getNeuralNetwork();
	        for (int i = 0; i < genes.length; i++) {
	            if (Math.random() < MUTATION_RATE) {
	                // Adjust the gene by a small, random amount
	                genes[i] += (Math.random() * 2 - 1) * MUTATIOMAGNITUDE;
	            }
	        }
	        individual.initializeNetwork(genes);
	        return individual;
	    }


		private BreakoutNeuralNetwork selectParent() {
			BreakoutNeuralNetwork parent1 = population[(int) (Math.random() * POPULATION_SIZE * SELECTION_PERCENTAGE)];
			BreakoutNeuralNetwork parent2 = population[(int) (Math.random() * POPULATION_SIZE * SELECTION_PERCENTAGE)];
			
			if (parent1.getFitness() > parent2.getFitness()) return parent1;
			return parent2;
		}

		private BreakoutNeuralNetwork[] crossover(BreakoutNeuralNetwork parent1, BreakoutNeuralNetwork parent2) {
		    double[] genes1 = parent1.getNeuralNetwork();
		    double[] genes2 = parent2.getNeuralNetwork();
		    double[] child1 = new double[genes1.length];
		    double[] child2 = new double[genes2.length];
		    
		    int crossoverPoint = (int) (Math.random() * genes1.length);
		    for (int i = 0; i < genes1.length; i++) {
		        child1[i] = (i < crossoverPoint) ? genes1[i] : genes2[i];
		        child2[i] = (i < crossoverPoint) ? genes2[i] : genes1[i];
		    }
		    
		    BreakoutNeuralNetwork offspring1 = new BreakoutNeuralNetwork(child1);
		    BreakoutNeuralNetwork offspring2 = new BreakoutNeuralNetwork(child2);
		    return new BreakoutNeuralNetwork[]{offspring1, offspring2};
		}




}
