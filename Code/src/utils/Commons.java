package utils;

public interface Commons {
    
	public static final int WIDTH = 300;
	public static final int HEIGHT = 400;
	public static final int BOTTOM_EDGE = 390;
	public static final  int N_OF_BRICKS = 1;//30;
	public static final int INIT_PADDLE_X = 200;
	public static final int INIT_PADDLE_Y = 360;
	public static final int INIT_BALL_X = 230;
	public static final int INIT_BALL_Y = 355;    
	public static final int PERIOD = 2;
    
	public static final int BREAKOUT_STATE_SIZE = 7;
	public static final int BREAKOUT_HIDDEN_LAYER = 5;
	//public static final int BREAKOUT_SECOND_HIDDEN_LAYER = 5;
	public static final int BREAKOUT_NUM_ACTIONS = 2;

	public static final int BREAKOUT_NETWORK_SIZE = (BREAKOUT_STATE_SIZE * BREAKOUT_HIDDEN_LAYER) + BREAKOUT_HIDDEN_LAYER + 
	                                       (BREAKOUT_HIDDEN_LAYER * BREAKOUT_NUM_ACTIONS) + BREAKOUT_NUM_ACTIONS;

	
	
	//GA Breakoout
	public static final int POPULATION_SIZE = 100;
	public static final int NUM_GENERATIONS = 1000;
	
	//public static final boolean MUTATEGETELITE = false;
	public static final double INITIALDIVERSITY = 2.65;
	public static final double MUTATION_RATE = .14;
	public static final double MUTATIONMAGNITUDE = .317;
	public static final double SELECTION_PERCENTAGE = 0.20;
	
	//TESTING
		//Crossover
		public static final boolean MULTIPLEPOINTCROSSOVER = false; //if false é o normal
		public static final int N_CROSSOVERPOINTS = 4; 
		//Select Parent
		public static final int K_TOURNAMENT = 5;
	
	
	//DEBUG STUFF
	public static final boolean TESTDIFFERENTVALUES = false;
	public static final int N_VALUES_TO_TEST = 50; 
	
	public static final boolean TESTDIFFERENTSEEDS = false;
	public static final long DIFFERENTSEEDS_RANDOMSEEDGENERATOR = 444444L;
	public static final int N_SEEDS = 10;
	
	public static final boolean DEFAULT = true;
	public static final int SEED = 1;
	//
	
	
	public static final boolean SHOWPOPULATION = false;
	public static final boolean SHOWEVERY_TEN = false;
	public static final boolean SHOWGENERATION = false;
	
	public static final boolean SHOWNEWBEST = true;
	public static final boolean PLAYNEWBEST = false;

	public static final boolean BREAKIFNOTHINGININTERVAL = false;
	public static int BREAKINTERVAL = 15;
}
