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
		
		private BreakoutNeuralNetwork champion;
		
		public int totalGeracoes = 0;

		private static int k_tournament = 6;

	    GeneticAlgorithm(){
	        generatePopulation();
	        champion = search();
	        System.out.println(champion);
	    }
	    
	    public BreakoutNeuralNetwork getChampion() {
			return champion;
	    }


	    private void generatePopulation() {
	        for(int i = 0; i < POPULATION_SIZE; i++){
	            population[i] = new BreakoutNeuralNetwork();;
	        }
	    }
	    
	    private void getBest(BreakoutNeuralNetwork nn) {
    		if(nn.getFitness() > champion.getFitness()) {
    			champion = new BreakoutNeuralNetwork(nn.getNeuralNetwork());
    			System.out.println("Best: \n" + champion);
    			System.out.println(".");
    		}
	    }
	    
	    private BreakoutNeuralNetwork getBestCopy() {
	    	BreakoutNeuralNetwork bb = new BreakoutNeuralNetwork(champion.getNeuralNetwork());
	    	return bb;
	    }
	    
	    private void printPopulation() {
	    	for(int i = 0; i <population.length; i++) 
	    		System.out.print(population[i].getFitness() + "  ");
	    	System.out.println("");
	    }

	    private BreakoutNeuralNetwork search() {
	    	champion = population[0];
			for (int i = 0; i < NUM_GENERATIONS; i++) {
				BreakoutNeuralNetwork[] newGeneration = new BreakoutNeuralNetwork[POPULATION_SIZE];
				Arrays.sort(population);
				System.out.println("Gen: " + i);
		
				getBest(population[0]);
				
				//printPopulation();
				
				int start = ((int) (POPULATION_SIZE * SELECTION_PERCENTAGE/2))*2;
				// Ensure 'start' is within bounds and adjust if necessary
				start = Math.max(2, Math.min(start, POPULATION_SIZE - 2)); // Ensures we have space for at least one pair of children
								
				for (int j = 0; j < POPULATION_SIZE - 1; j += 2) {
					
					if(j < start) {
						newGeneration[j] = population[j];
						newGeneration[j+1] = population[j+1];
					} else {
						BreakoutNeuralNetwork parent1 = selectParent();
						BreakoutNeuralNetwork parent2 = selectParent();
						BreakoutNeuralNetwork[] children = crossover(parent1, parent2);
						
						newGeneration[j] = mutate(children[0]);
						newGeneration[j + 1]  = mutate(children[1]);
					}
					
				}
				
				population = newGeneration;

			}
			return champion;
		}
	    
	
	    private BreakoutNeuralNetwork mutate(BreakoutNeuralNetwork individual) {
	        double[] genes = individual.getNeuralNetwork();
	        for (int i = 0; i < genes.length; i++) {
	            if (Math.random() < MUTATION_RATE) {
	                // Adjust the gene by a small, random amount
	                genes[i] += (Math.random() * 2 - 1) * MUTATIOMAGNITUDE;
	            }
	        }
	        BreakoutNeuralNetwork newIndividual = new BreakoutNeuralNetwork(genes);
	        if (Commons.MUTATEGETELITE) {
	        	return individual.getFitness() > newIndividual.getFitness() ? individual : newIndividual;
	        } else {
	        	return newIndividual;
	        }
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
