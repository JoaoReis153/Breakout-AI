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

		private int seed = Commons.SEED;
		
		public int interval = 0;
		
		private static int k_tournament = 6;

	    GeneticAlgorithm(){
	        generatePopulation();
	        champion = search();
	        System.out.println(champion);
	    }
	    
	    GeneticAlgorithm(int seed){
	    	this.seed = seed;
	        generatePopulation();
	        champion = search();
	        System.out.println("Best: " + champion.getFitness());
	    }
	    
	    public BreakoutNeuralNetwork getChampion() {
			return champion;
	    }


	    private void generatePopulation() {
			System.out.println("\n\n\n---\n+ Seed: " + seed + "\n-");
	        for(int i = 0; i < POPULATION_SIZE; i++){
	        	population[i] = new BreakoutNeuralNetwork(seed);
	        }
	    }
	    
	    private void getBest(BreakoutNeuralNetwork nn, int gen) {
    		if(nn.getFitness() > champion.getFitness()) {
    			interval = 0;
    			champion = new BreakoutNeuralNetwork(nn.getNeuralNetwork(), seed);
    			if(Commons.SHOWNEWBEST)
    				System.out.println(champion);
    		} else {
    			interval++;
    		}
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
				
				if(Commons.SHOWGENERATION)
					System.out.println("Gen: " + i);
		
				getBest(population[0], i);
			
				if(Commons.BREAKIFNOTHINGININTERVAL && interval == Commons.BREAKINTERVAL) {  
					System.out.println("Gen: " + i);
					break;
				}
				
				if(Commons.SHOWPOPULATION)
					printPopulation(); 
			
				int start = (int) (POPULATION_SIZE * SELECTION_PERCENTAGE);
				
				start = Math.max(2, Math.min(start, POPULATION_SIZE - 2)); 
				
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
	                genes[i] += (Math.random() * 2 - 1) * MUTATIOMAGNITUDE;
	            }
	        }
	        BreakoutNeuralNetwork newIndividual = new BreakoutNeuralNetwork(genes, seed);
	        
        	return newIndividual;
	    }


	    private BreakoutNeuralNetwork selectParent() {
	        BreakoutNeuralNetwork best = null;
	        for (int i = 0; i < k_tournament; i++) {
	            int randomIndex = (int) (Math.random() * POPULATION_SIZE);
	            BreakoutNeuralNetwork contender = population[randomIndex];
	            if (best == null || contender.getFitness() > best.getFitness()) {
	                best = contender;
	            }
	        }
	        return best;
	    }


		private BreakoutNeuralNetwork[] crossover(BreakoutNeuralNetwork parent1, BreakoutNeuralNetwork parent2) {
		    double[] genes1 = parent1.getNeuralNetwork();
		    double[] genes2 = parent2.getNeuralNetwork();
		    double[] child1 = new double[genes1.length];
		    double[] child2 = new double[genes2.length];
		    
		    int crossoverPoint1 = (int) (Math.random() * genes1.length);
		    
		    /*
		    for(int i = 0; i < genes1.length; i++) {
		    	child1[i] = (i < crossoverPoint1) ? genes1[i] : genes2[i];
		        child2[i] = (i < crossoverPoint1) ? genes2[i] : genes1[i];
		    }
		    
		     */
		    
		    int crossoverPoint2 = (int) ((Math.random() * (genes1.length - crossoverPoint1)) + crossoverPoint1);
		    for (int i = 0; i < genes1.length; i++) {
		    	 if (i > crossoverPoint1 && i < crossoverPoint2) {
		    	        child1[i] = genes2[i];
		    	        child2[i] = genes1[i];
		    	    } else {
		    	        child1[i] = genes1[i];
		    	        child2[i] = genes2[i];
		    	    }
		    }
		    
		    BreakoutNeuralNetwork offspring1 = new BreakoutNeuralNetwork(child1, seed);
		    BreakoutNeuralNetwork offspring2 = new BreakoutNeuralNetwork(child2, seed);
		    return new BreakoutNeuralNetwork[]{offspring1, offspring2};
		}




}
