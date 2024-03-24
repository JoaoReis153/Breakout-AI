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
	public static final int PERIOD = 5;
    
	public static final int BREAKOUT_STATE_SIZE = 7;
	public static final int BREAKOUT_HIDDEN_LAYERS = 4;
	public static final int BREAKOUT_NUM_ACTIONS = 2;
	public static final int BREAKOUT_NETWORK_SIZE = BREAKOUT_STATE_SIZE * BREAKOUT_HIDDEN_LAYERS + BREAKOUT_HIDDEN_LAYERS + BREAKOUT_HIDDEN_LAYERS * BREAKOUT_NUM_ACTIONS + BREAKOUT_NUM_ACTIONS;

	public static final int SEED = 1415123;
	
	
	//GA Breakoout
	public static final int POPULATION_SIZE = 100;
	public static final int NUM_GENERATIONS = 100;
	
	public static final double INITIALDIVERSITY = 87;
	public static final double MUTATION_RATE = .2;
	public static final double MUTATIOMAGNITUDE = 20;
	public static final double SELECTION_PERCENTAGE = 0.2;

}
