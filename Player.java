//This is the class for the Player object. 
//Ball class has a single instance variable (xPlayer). 
//All instance variables are private and can be reached and changed by using respective getter and setter functions.
//fireArrow() method is used to fire an arrow if there is no active arrow in the game
//drawPlayer() method is used to draw the player
//movePlayer() method is used to calculate new x position of the player

public class Player {
	//class constants
	private static final double PERIOD_OF_PLAYER=6000;
	private static final double PLAYER_HEIGHT_SCALEY_RATE= 1.0/8.0;
	private static final double PLAYER_HEIGHT= PLAYER_HEIGHT_SCALEY_RATE*Environment.getyScaleFinal();
	private static final double PLAYER_HEIGHT_WIDTH_RATE=37.0/27.0;
	private static final double PLAYER_WIDTH=PLAYER_HEIGHT/PLAYER_HEIGHT_WIDTH_RATE;
	private static final double V_PLAYER=Environment.getxScaleFinal()/PERIOD_OF_PLAYER;
	 
	//instance variables
	private double xPlayer;// x position of the player
	 
	//constructor
	public Player() {
		setxPlayer(Environment.getxScaleFinal()/2);//set the initial x position of the player as the middle of the canvas
	}//end of the constructor
	 
	 //method to fire arrows
	public void fireArrow() {
		if(Arrow.getArrowCounter()==0)//checks if there is an active arrow
			new Arrow(getxPlayer());// if there is no active arrow calls the arrow constructor with x position of the player
	}//end of the fireArrow() method
	 
	//method to draw the player
	public void drawPlayer(){
		StdDraw.picture(xPlayer,PLAYER_HEIGHT/2, "player_back.png", PLAYER_WIDTH,PLAYER_HEIGHT);//draws the player
	}//end of drawPlayer() method
	
	//method to move the player
	public void movePlayer(int directionInput){
		if(directionInput==1 && (xPlayer+Environment.PAUSE_DURATION*V_PLAYER<Environment.getxScaleFinal()-(getPlayerWidth()/2)))//checks if the player hits the right wall
			setxPlayer(getxPlayer()+Environment.PAUSE_DURATION*V_PLAYER);//moves the player to the right
		if(directionInput==-1 && (xPlayer+Environment.PAUSE_DURATION*V_PLAYER>Environment.getxScaleInitial()+(getPlayerWidth()/2)))//checks if the player hits the left wall
			setxPlayer(getxPlayer()-Environment.PAUSE_DURATION*V_PLAYER);//moves the player to the left
	}//end of the movePlayer method
 
	//setters and getters
	public double getxPlayer() {
		return xPlayer;
	}

	public void setxPlayer(double xPlayer) {
		this.xPlayer = xPlayer;
	}

	public static double getPlayerHeight() {
		return PLAYER_HEIGHT;
	}

	public static double getPlayerWidth() {
		return PLAYER_WIDTH;
	}
	
}
