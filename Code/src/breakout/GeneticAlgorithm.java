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
						if(Commons.MULTIPLEPOINTCROSSOVER) children = multipleCrossover(parent1, parent2);
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
	        if (Math.random() < MUTATION_RATE) {
		        for (int i = 0; i < genes.length; i++) {
		                genes[i] += (Math.random() * 2 - 1) * MUTATIONMAGNITUDE;
		            }
	        }
	        BreakoutNeuralNetwork newIndividual = new BreakoutNeuralNetwork(genes, seed, INITIALDIVERSITY);
	        
        	return newIndividual;
	    }


	    private BreakoutNeuralNetwork selectParent() {
	        BreakoutNeuralNetwork best = null;
	        for (int i = 0; i < Commons.K_TOURNAMENT; i++) {
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
		    
		    
		    //for(int i = 0; i < genes1.length; i++) {
		    //	child1[i] = (i < crossoverPoint1) ? genes1[i] : genes2[i];
		    //    child2[i] = (i < crossoverPoint1) ? genes2[i] : genes1[i];
		    //}
		    
		     
		    
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
		    
		    BreakoutNeuralNetwork offspring1 = new BreakoutNeuralNetwork(child1, seed, INITIALDIVERSITY);
		    BreakoutNeuralNetwork offspring2 = new BreakoutNeuralNetwork(child2, seed, INITIALDIVERSITY);
		    return new BreakoutNeuralNetwork[]{offspring1, offspring2};
		}

		
	    private BreakoutNeuralNetwork[] multipleCrossover(BreakoutNeuralNetwork parent1, BreakoutNeuralNetwork parent2) {
	        double[] genes1 = parent1.getNeuralNetwork();
	        double[] genes2 = parent2.getNeuralNetwork();
	        double[] child1 = new double[genes1.length];
	        double[] child2 = new double[genes2.length];
	        
	        // Generate k random crossover points
	        int[] crossoverPoints = new int[kSelectionPoints + 1];
	        crossoverPoints[0] = 0; // The first segment always starts from 0
	        crossoverPoints[kSelectionPoints] = genes1.length; // The last segment always ends at the length of genes
	        
	        // Generate k-1 unique crossover points
	        for (int i = 1; i < kSelectionPoints; i++) {
	            int randomPoint = (int) (Math.random() * genes1.length);
	            boolean isUnique = true;
	            for (int j = 0; j < i; j++) {
	                if (randomPoint == crossoverPoints[j]) {
	                    isUnique = false;
	                    break;
	                }
	            }
	            if (isUnique) {
	                crossoverPoints[i] = randomPoint;
	            } else {
	                i--; // Retry generating a new point
	            }
	        }
	        
	        // Sort crossover points in ascending order
	        Arrays.sort(crossoverPoints);
	        
	        // Perform crossover
	        for (int i = 0; i < kSelectionPoints; i++) {
	            if (i % 2 == 0) {
	                for (int j = crossoverPoints[i]; j < crossoverPoints[i + 1]; j++) {
	                    child1[j] = genes1[j];
	                    child2[j] = genes2[j];
	                }
	            } else {
	                for (int j = crossoverPoints[i]; j < crossoverPoints[i + 1]; j++) {
	                    child1[j] = genes2[j];
	                    child2[j] = genes1[j];
	                }
	            }
	        }
	        
	        // Create offspring neural networks
	        BreakoutNeuralNetwork offspring1 = new BreakoutNeuralNetwork(child1, seed, INITIALDIVERSITY);
	        BreakoutNeuralNetwork offspring2 = new BreakoutNeuralNetwork(child2, seed, INITIALDIVERSITY);
	        
	        return new BreakoutNeuralNetwork[]{offspring1, offspring2};
	    }





}
