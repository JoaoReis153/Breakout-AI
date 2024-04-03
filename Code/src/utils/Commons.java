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
	public static final int PERIOD = 1;
    
	public static final int BREAKOUT_STATE_SIZE = 7;
	public static final int BREAKOUT_HIDDEN_LAYER = 5;
	public static final int BREAKOUT_SECOND_HIDDEN_LAYER = 4;
	public static final int BREAKOUT_NUM_ACTIONS = 2;

	public static final int BREAKOUT_NETWORK_SIZE = (BREAKOUT_STATE_SIZE * BREAKOUT_HIDDEN_LAYER) + BREAKOUT_HIDDEN_LAYER + 
	                                       (BREAKOUT_HIDDEN_LAYER * BREAKOUT_SECOND_HIDDEN_LAYER) + BREAKOUT_SECOND_HIDDEN_LAYER + 
	                                       (BREAKOUT_SECOND_HIDDEN_LAYER * BREAKOUT_NUM_ACTIONS) + BREAKOUT_NUM_ACTIONS;

	
	
	//GA Breakoout
	public static final int POPULATION_SIZE = 100;
	public static final int NUM_GENERATIONS = 100;
	
	//public static final boolean MUTATEGETELITE = false;
	public static final double INITIALDIVERSITY = Math.sqrt(8.0 / BREAKOUT_STATE_SIZE);
	public static final double MUTATION_RATE = .3;
	public static final double MUTATIONMAGNITUDE = .3;
	public static final double SELECTION_PERCENTAGE = 0.2;
	
	
	//DEBUG STUFF
	public static final boolean TESTDIFFERENTVALUES = true;
	public static final boolean TESTDIFFERENTSEEDS = false;
	public static final boolean DEFAULT = false;
	public static final int SEED = 6;
	
	public static final boolean SHOWPOPULATION = false;
	public static final boolean SHOWEVERY_TEN = false;
	public static final boolean SHOWGENERATION = false;
	
	public static final boolean SHOWNEWBEST = true;

	public static final boolean BREAKIFNOTHINGININTERVAL = true;
	public static int BREAKINTERVAL = 30;
}
