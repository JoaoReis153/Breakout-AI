package breakout;

import java.util.Arrays;

import utils.Commons;

public class GeneticAlgorithm {

		private static int POPULATION_SIZE = Commons.POPULATION_SIZE;
		private static int NUM_GENERATIONS = Commons.NUM_GENERATIONS;
		
		private double MUTATION_RATE = Commons.MUTATION_RATE;
		private double MUTATIONMAGNITUDE = Commons.MUTATIONMAGNITUDE;
		private double SELECTION_PERCENTAGE = Commons.SELECTION_PERCENTAGE;
		private double INITIALDIVERSITY = Commons.INITIALDIVERSITY;
		
		private int kSelectionPoints = Commons.N_CROSSOVERPOINTS;
		
		private BreakoutNeuralNetwork[] population = new BreakoutNeuralNetwork[POPULATION_SIZE];
		
		private BreakoutNeuralNetwork champion;
		
		public int totalGeracoes = 0;

		private int seed = Commons.SEED;
		
		public int interval = 0;
		

	    GeneticAlgorithm(){
	        generatePopulation();
	        champion = search();
	        System.out.println(champion);
	    }
	    
	    GeneticAlgorithm(int seed, double initialDiversity, double mutationRate, double mutationMagnitude, double selectionPercentage, int kSelectionPoints){
	    	this.kSelectionPoints = kSelectionPoints;
	    	this.MUTATION_RATE = mutationRate != 0 ? mutationRate : Commons.MUTATION_RATE;
			this.MUTATIONMAGNITUDE = mutationMagnitude != 0 ? mutationMagnitude : Commons.MUTATIONMAGNITUDE;
			this.SELECTION_PERCENTAGE = selectionPercentage != 0 ? selectionPercentage : Commons.SELECTION_PERCENTAGE;
	    	this.INITIALDIVERSITY = initialDiversity != 0 ? initialDiversity : Commons.INITIALDIVERSITY;
	    	this.seed = seed;
	        generatePopulation();
	        champion = search();
	        //System.out.println("Best: " + champion.getFitness());
	    }
	    
	    public BreakoutNeuralNetwork getChampion() {
			return champion;
	    }


	    private void generatePopulation() {
			System.out.println("\n\n---\n+ Seed: " + seed + "\n-");
	        for(int i = 0; i < POPULATION_SIZE; i++){
	        	population[i] = new BreakoutNeuralNetwork(seed, INITIALDIVERSITY);
	        }
	    }
	    
	    private void getBest(BreakoutNeuralNetwork nn, int gen) {
    		if(nn.getFitness() > champion.getFitness()) {
    			interval = 0;
    			champion = new BreakoutNeuralNetwork(nn.getNeuralNetwork(), seed, INITIALDIVERSITY);
    			if(Commons.SHOWNEWBEST)
    				System.out.println(champion);
    			if(Commons.PLAYNEWBEST) {     				
    				Breakout game = new Breakout(champion, seed);
    			}
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
				
				if(Commons.SHOWEVERY_TEN) 
					if(i % 10 == 0) {						
						Breakout game = new Breakout(champion, seed);
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
						BreakoutNeuralNetwork[] children = null;
						
						//Crossover
						if(Commons.MULTIPLEPOINTCROSSOVER) children = crossover(parent1, parent2);
						else children = crossover(parent1, parent2); 
						
						
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
	                genes[i] += (Math.random() * 2 - 1) * MUTATIONMAGNITUDE;
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
		    
		    BreakoutNeuralNetwork offspring1 = new BreakoutNeuralNetwork(child1, seed, INITIALDIVERSITY);
		    BreakoutNeuralNetwork offspring2 = new BreakoutNeuralNetwork(child2, seed, INITIALDIVERSITY);
		    return new BreakoutNeuralNetwork[]{offspring1, offspring2};
		}




}