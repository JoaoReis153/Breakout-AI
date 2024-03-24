package breakout;

import java.util.Arrays;

import utils.Commons;
import utils.GameController;

public class BreakoutNeuralNetwork implements GameController, Comparable<BreakoutNeuralNetwork> {
    private int inputDim = Commons.BREAKOUT_STATE_SIZE;
    private int hiddenDim = Commons.BREAKOUT_HIDDEN_LAYERS;
    private int outputDim = Commons.BREAKOUT_NUM_ACTIONS;
    private double[][] hiddenWeights;
    private double[] hiddenBiases;
    private double[][] outputWeights;
    private double[] outputBiases;
    
    private double hiddenWeightsMultiplier = 1;
    private double hiddenBiasesMultiplier = 1;
    private double outputWeightsMultipler = 1;
    private double outputBiasesMultiplier = 1;
    
    private double INITIALDIVERSITY = Commons.INITIALDIVERSITY;
   
    public BreakoutNeuralNetwork() {
        initializeParameters();
    }
    
    

	public BreakoutNeuralNetwork(double[] values) {
        int maxSize = (inputDim * hiddenDim + hiddenDim + outputDim * hiddenDim + outputDim) ;
        if(values.length == maxSize) {
        	initializeNetwork(values);        	
        }
        else {
        	throw new IllegalArgumentException("Demasiado grande");
        }
    }
    
    private void initializeNetwork(double[] values) {
    	// Initialize network with specified set of values
    	// Implementation omitted for brevity
    	hiddenWeights = new double[inputDim][hiddenDim];
    	hiddenBiases = new double[hiddenDim];
    	outputWeights = new double[hiddenDim][outputDim];
    	outputBiases = new double[outputDim];
    	
    	int index = 0;
    	for (int i = 0; i < inputDim; i++) {
    		for (int j = 0; j < hiddenDim; j++) {
    			hiddenWeights[i][j] = values[index++];
    		}
    	}
    	
    	for (int i = 0; i < hiddenDim; i++) {
    		hiddenBiases[i] = values[index++];
    	}
    	for (int i = 0; i < hiddenDim; i++) {
    		for (int j = 0; j < outputDim; j++) {
    			outputWeights[i][j] = values[index++];
    		}
    	}
    	
    	for (int i = 0; i < outputDim; i++) {
    		outputBiases[i] = values[index++];
    	}  	
    }
     
    private void initializeParameters() {
			hiddenWeights = new double[inputDim][hiddenDim];
			hiddenBiases = new double[hiddenDim];
			outputWeights = new double[hiddenDim][outputDim];
			outputBiases = new double[outputDim];
			
			// Initialize weights with random values in the specified range
			for (int i = 0; i < inputDim; i++) {
				for (int j = 0; j < hiddenDim; j++) {
					hiddenWeights[i][j] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
				}
			}
			
			// Initialize hidden biases with random values in the specified range
			for (int i = 0; i < hiddenDim; i++) {
				hiddenBiases[i] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
				for (int j = 0; j < outputDim; j++) {
					outputWeights[i][j] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
				}
			}
			
			// Initialize output biases with random values in the specified range
			for (int i = 0; i < outputDim; i++) {
				outputBiases[i] = ((Math.random() * 2) - 1) * INITIALDIVERSITY;
			}
}
       
    public double[] forward(int[] values) {
    	double[] hiddenLayer = new double[hiddenDim];
		double[] inputValues = normalize(values);
	
    	for(int i = 0; i < hiddenDim; i++) {
    		for(int j = 0; j < inputDim; j++) {
    			//System.out.println("Weights: " + hiddenWeights[j][i]);
    			hiddenLayer[i] += hiddenWeights[j][i] * inputValues[j] ;
    		}
    		//System.out.println("Hidden layer: " + hiddenLayer[i]);
    		//System.out.println("Hidden biases: " + hiddenBiases[i]);
    		hiddenLayer[i] = sigmoid(hiddenLayer[i] + hiddenBiases[i]);
    		//System.out.println("After calculus hidden layer: " + hiddenLayer[i]);
    	}
    	
    	double[] output = new double[Commons.BREAKOUT_NUM_ACTIONS];
    	for(int i = 0; i < outputDim; i++) {
            for(int j = 0; j < hiddenDim; j++) {
                output[i] += outputWeights[j][i] * hiddenLayer[j]; 
            }
    		output[i] = sigmoid(output[i] + outputBiases[i]);
    	}
    	
    	
    	
    	return output;
    }
    
    @Override
	public int nextMove(int[] inputValues) {
		double[] output = forward(inputValues);
		//System.out.println("Output: " + output[0] + " : " + output[1]);
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
    	/*
    	double sum = 0; 
    	for( int i = 0; i< values.length; i++) sum+= values[i];
    	System.out.println("result_ " + sum);
    	for(int i = 0; i < values.length; i++) {
    		result[i] = values[i] / (double) sum;
    		System.out.print(".." + result[i]);
    	}
    	*/
    	
    	
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
    	return result;
    }
        
    public double getFitness() {
    	BreakoutBoard bb = new BreakoutBoard(this, false, Commons.SEED);
    	bb.runSimulation();
    	return bb.getFitness();
    }
    
    public double[] getNeuralNetwork() {
    	
        int size = inputDim * hiddenDim + hiddenDim + hiddenDim * outputDim + outputDim;
        double[] networkParams = new double[size];
        
        int index = 0;
        
        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                networkParams[index++] = hiddenWeights[i][j];
            }
        }
        
        for (int i = 0; i < hiddenDim; i++) {
            networkParams[index++] = hiddenBiases[i];
        }
        
        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                networkParams[index++] = outputWeights[i][j];
            }
        }
        
        for (int i = 0; i < outputDim; i++) {
        	networkParams[index++] = outputBiases[i];
    	}
        
        
        return networkParams;
    }
  
	public void loadNeuralNetwork(double[] values) {

		if (values == null || values.length != (inputDim * hiddenDim + hiddenDim + hiddenDim * outputDim + outputDim)) {
	        throw new IllegalArgumentException("Array inválido.");
	    }
	    
	    int index = 0;
	    
	    // Unpack hidden layer weights
	    for (int i = 0; i < inputDim; i++) {
	        for (int j = 0; j < hiddenDim; j++) {
	            hiddenWeights[i][j] = values[index++];
	        }
	    }
	    
	    // Unpack hidden layer biases
	    for (int i = 0; i < hiddenDim; i++) {
	        hiddenBiases[i] = values[index++];
	    }
	    
	    // Unpack output layer weights
	    for (int i = 0; i < hiddenDim; i++) {
	        for (int j = 0; j < outputDim; j++) {
	            outputWeights[i][j] = values[index++];
	        }
	    }
	    
	    // Unpack output layer bias
	    for (int i = 0; i < outputDim; i++) {
    	   outputBiases[i] = values[index++];
	    }
	    
	 
		
	}
	 
	@Override
    public String toString() {
    		String result = "----------------\n";
    		result+="Ftn: " + getFitness() + "\n";
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
            
            return result;
    }

	@Override
	public int compareTo(BreakoutNeuralNetwork o) {
		return Double.compare(o.getFitness(), getFitness());
	}


}
