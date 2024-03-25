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
     
    private double INITIALDIVERSITY = Commons.INITIALDIVERSITY;//1
   
    String a1799999 = "-1.5357924113913106  0.7906783373361628  0.3726787237320426  1.4434773659332518  3.584350916649724  1.4214869460867765  -1.0555896549999353  -1.9036478085881037  1.6026648516703361  -0.04147636624634343  -0.18361641396029238  -1.5939782359285386  0.9882400518369407  -0.1907427145228574  1.5070963771677461  -1.1244050102501493  1.4717466650529283  -0.8663454055871149  0.7944367887949407  -3.3998833283329577  -1.1013502181746695  1.587672381406325  -0.8090767200507535  0.8917010954312323  -0.698593167453371  -2.9437918528050346  1.3576895041182069  1.2824813254004899  0.2612972895796919  -0.0768997781295086  -1.839613529381018  1.2757886473206876  3.1980549551552113  0.13658680121845357  0.5285848334309898  -0.5200908622406266  0.8662831511149042  -2.1327326209693944  1.8612661413245575  -0.8407339044991909  0.4537675052278739  -0.10129902374752564  0.3559792254608669  0.38888878689698725  -1.6691244366508802  -1.138589909954573  -0.12000341840758351  -0.909794630790927  -2.0463250337065872  0.9481267613601491  2.556624519217134  2.8902324804675206";

    
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
    
    public void initializeNetwork(double[] values) {
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
			initializeNetwork(stringToArray(a1799999));
}
       
    public double[] forward(int[] values) {
    	double[] inputValues = normalize(values);

    	double[] hiddenLayer = new double[hiddenDim];
	
    	for(int i = 0; i < hiddenDim; i++) {
    		for(int j = 0; j < inputDim; j++) {
    			hiddenLayer[i] += hiddenWeights[j][i] * inputValues[j] ;
    		}
    		hiddenLayer[i] = sigmoid(hiddenLayer[i] + hiddenBiases[i]);
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
	
	public double[] stringToArray(String a) {
		String[] b = a.split("  ");
		double[] values = new double[b.length];
		for(int i = 0; i < b.length; i++) {
			values[i] = Double.valueOf(b[i]);
		}
		return values;
	}


}
