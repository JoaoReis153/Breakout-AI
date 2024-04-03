package breakout;

import java.util.Arrays;

import utils.Commons;
import utils.GameController;

public class BreakoutNeuralNetwork implements GameController, Comparable<BreakoutNeuralNetwork> {

	private int inputDim = Commons.BREAKOUT_STATE_SIZE;
    private int hiddenDim = Commons.BREAKOUT_HIDDEN_LAYER;
    private int outputDim = Commons.BREAKOUT_NUM_ACTIONS;
    private int secondHiddenDim = Commons.BREAKOUT_SECOND_HIDDEN_LAYER; // Define the size of the second hidden layer
    private double[][] secondHiddenWeights;
    private double[] secondHiddenBiases;
    private double[][] hiddenWeights;
    private double[] hiddenBiases;
    private double[][] outputWeights;
    private double[] outputBiases;
     
    private double INITIALDIVERSITY = Commons.INITIALDIVERSITY;//1
    
    private int seed = Commons.SEED; 
    /*
    public BreakoutNeuralNetwork() {
        initializeParameters();
    }*/
    
    public BreakoutNeuralNetwork(int seed, double initialDiversity) {
    	this.INITIALDIVERSITY = initialDiversity != 0 ? initialDiversity : Commons.INITIALDIVERSITY;
    	this.seed = seed;
        initializeParameters();
    }
    
    public BreakoutNeuralNetwork(double[] values, int seed, double initialDiversity) {
    	this.INITIALDIVERSITY = initialDiversity != 0 ? initialDiversity : Commons.INITIALDIVERSITY;
    	this.seed = seed;
        int maxSize = Commons.BREAKOUT_NETWORK_SIZE;
        if (values.length == maxSize) {
            initializeNetwork(values);
        } else {
            throw new IllegalArgumentException("Incorrect size of input values array");
        }
    }

    
    
    
    
	public void initializeNetwork(double[] values) {
	    // Initialize network with specified set of values
	    hiddenWeights = new double[inputDim][hiddenDim];
	    hiddenBiases = new double[hiddenDim];
	    secondHiddenWeights = new double[hiddenDim][secondHiddenDim]; // Add this line for the second hidden layer
	    secondHiddenBiases = new double[secondHiddenDim];             // Add this line for the second hidden layer
	    outputWeights = new double[secondHiddenDim][outputDim];       // Note the change here from hiddenDim to secondHiddenDim
	    outputBiases = new double[outputDim];
	    
	    int index = 0;
	    // Initialize first hidden layer weights
	    for (int i = 0; i < inputDim; i++) {
	        for (int j = 0; j < hiddenDim; j++) {
	            hiddenWeights[i][j] = values[index++];
	        }
	    }
	    
	    // Initialize first hidden layer biases
	    for (int i = 0; i < hiddenDim; i++) {
	        hiddenBiases[i] = values[index++];
	    }
	    
	    // Initialize second hidden layer weights
	    for (int i = 0; i < hiddenDim; i++) {
	        for (int j = 0; j < secondHiddenDim; j++) {
	            secondHiddenWeights[i][j] = values[index++];
	        }
	    }
	    
	    // Initialize second hidden layer biases
	    for (int i = 0; i < secondHiddenDim; i++) {
	        secondHiddenBiases[i] = values[index++];
	    }
	    
	    // Initialize output layer weights
	    for (int i = 0; i < secondHiddenDim; i++) {
	        for (int j = 0; j < outputDim; j++) {
	            outputWeights[i][j] = values[index++];
	        }
	    }
	    
	    // Initialize output layer biases
	    for (int i = 0; i < outputDim; i++) {
	        outputBiases[i] = values[index++];
	    }  	
	}

     
	    private void initializeParameters() {
	        // First hidden layer initialization
	        hiddenWeights = new double[inputDim][hiddenDim];
	        hiddenBiases = new double[hiddenDim];
	        for (int i = 0; i < inputDim; i++) {
	            for (int j = 0; j < hiddenDim; j++) {
	                hiddenWeights[i][j] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
	            }
	        }
	        for (int i = 0; i < hiddenDim; i++) {
	            hiddenBiases[i] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
	        }
	
	        // Second hidden layer initialization
	        secondHiddenWeights = new double[hiddenDim][secondHiddenDim];
	        secondHiddenBiases = new double[secondHiddenDim];
	        for (int i = 0; i < hiddenDim; i++) {
	            for (int j = 0; j < secondHiddenDim; j++) {
	                secondHiddenWeights[i][j] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
	            }
	        }
	        for (int i = 0; i < secondHiddenDim; i++) {
	            secondHiddenBiases[i] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
	        }
	
	        // Output layer initialization: now connects from the second hidden layer
	        outputWeights = new double[secondHiddenDim][outputDim];
	        outputBiases = new double[outputDim];
	        for (int i = 0; i < secondHiddenDim; i++) {
	            for (int j = 0; j < outputDim; j++) {
	                outputWeights[i][j] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
	            }
	        }
	        for (int i = 0; i < outputDim; i++) {
	            outputBiases[i] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
	        }
	    }

	    public int getSeed() {
	    	return seed;
	    }

			//initializeNetwork(stringToArray(a1799999));
       
	    public double[] forward(int[] values) {
	        double[] inputValues = normalize(values);

	        // First hidden layer
	        double[] hiddenLayer = new double[hiddenDim];
	        for (int i = 0; i < hiddenDim; i++) {
	            for (int j = 0; j < inputDim; j++) {
	                hiddenLayer[i] += hiddenWeights[j][i] * inputValues[j];
	            }
	            hiddenLayer[i] = sigmoid(hiddenLayer[i] + hiddenBiases[i]);
	        }

	        // Second hidden layer
	        double[] secondHiddenLayer = new double[secondHiddenDim];
	        for (int i = 0; i < secondHiddenDim; i++) {
	            for (int j = 0; j < hiddenDim; j++) {
	                secondHiddenLayer[i] += secondHiddenWeights[j][i] * hiddenLayer[j];
	            }
	            secondHiddenLayer[i] = sigmoid(secondHiddenLayer[i] + secondHiddenBiases[i]);
	        }

	        // Output layer
	        double[] output = new double[outputDim];
	        for (int i = 0; i < outputDim; i++) {
	            for (int j = 0; j < secondHiddenDim; j++) {
	                output[i] += outputWeights[j][i] * secondHiddenLayer[j];
	            }
	            output[i] = sigmoid(output[i] + outputBiases[i]);
	        }

	        return output;
	    }

    
    @Override
	public int nextMove(int[] inputValues) {
		double[] output = forward(inputValues);
		if(output[0] > output[1]) 
			return 1;
		return 2;
	}
    
    private double tanh(double x) {
        return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
    }

    private double sigmoid(double x) {
    	return 1/(1+Math.exp(-x));
    }
  
    private double[] normalize(int[] values) {
    	double[] result = new double[values.length];
    	
    	double total = 0;
    	for(double k : values) total += k;
    	total/=values.length;
    	for(int i = 0; i < result.length; i++) result[i] = values[i]/total;
    	/*
     	//0 and 1 are balls coordinates
    	result[0] = values[0] / (double) Commons.WIDTH;
    	result[1] = values[1] / (double) Commons.HEIGHT;
    	//2 is its direction (-1 ou 1)
    	result[2] = values[2] * 0.5;
    	//3 and 4 are the paddle coordinates
    	result[3] = values[3] / (double) Commons.WIDTH;
    	result[4] = values[4] / (double) Commons.HEIGHT;
    	//5 and 6 are the bricks coordinates
    	result[5] = values[5] / (double) Commons.WIDTH;
    	result[6] = values[6] / (double) Commons.HEIGHT;
    	*/
    	return result;
    }
        
    public double getFitness() {
    	BreakoutBoard bb = new BreakoutBoard(this, false, seed);
    	bb.runSimulation();
    	return bb.getFitness();
    }
    
    public double[] getNeuralNetwork() {
        int size = (inputDim * hiddenDim) + hiddenDim +
                   (hiddenDim * secondHiddenDim) + secondHiddenDim +
                   (secondHiddenDim * outputDim) + outputDim;
        double[] networkParams = new double[size];
        
        int index = 0;
        // First hidden layer weights and biases
        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                networkParams[index++] = hiddenWeights[i][j];
            }
        }
        for (int i = 0; i < hiddenDim; i++) {
            networkParams[index++] = hiddenBiases[i];
        }

        // Second hidden layer weights and biases
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < secondHiddenDim; j++) {
                networkParams[index++] = secondHiddenWeights[i][j];
            }
        }
        for (int i = 0; i < secondHiddenDim; i++) {
            networkParams[index++] = secondHiddenBiases[i];
        }

        // Output layer weights and biases
        for (int i = 0; i < secondHiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                networkParams[index++] = outputWeights[i][j];
            }
        }
        for (int i = 0; i < outputDim; i++) {
            networkParams[index++] = outputBiases[i];
        }
        
        return networkParams;
    }

  
	 
	@Override
    public String toString() {
    		String result = "";
    		result+="Ftn: " + getFitness() + "\n";
           /*
    		String acc = "";
            for (int input = 0; input < inputDim; input++) {
                for (int i = 0; i < hiddenDim; i++) {
                    acc += hiddenWeights[input][i] + "  ";
                }
            } 
            result += "hiddenWeights: " + acc +  "\n"; 
            acc = "";
            for (int i = 0; i < hiddenDim; i++) {
            	acc += hiddenBiases[i] + "  ";
            }
            
            result += "hiddenBiases: " + acc +  "\n"; 
            acc = "";
            
            for (int hiddenw = 0; hiddenw < hiddenDim; hiddenw++) {
                for (int i = 0; i < outputDim; i++) {
                    acc += outputWeights[hiddenw][i] + "  ";
                }
            }
            
            result += "outputWeights: " + acc +  "\n"; 
            acc = "";
            
            for (int i = 0; i < outputDim; i++) {
            	acc += outputBiases[i] +  "  ";
            }
            
            result += "outputBiases: " + acc +  "\n"; 
            */
            return result;
    }

	@Override
	public int compareTo(BreakoutNeuralNetwork o) {
		return Double.compare(o.getFitness(), getFitness());
	}
	
	public double[] stringToArray(String a) {
		String[] b = a.split("  ");
		double[] values = new double[b.length];
		for(int i = 0; i < b.length; i++) {
			values[i] = Double.valueOf(b[i]);
		}
		return values;
	}


}
