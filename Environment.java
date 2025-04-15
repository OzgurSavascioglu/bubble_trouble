//Ozgur Savascioglu, 2022400366, 16 April 2023
//This is the class for the Environment object. This class also includes the methods that for main game animation. 
//Environment class has 3 static variables (bubbleTrouble, gameBar, and gamer).
//Environment class has a single instance variable (endState). 
//All instance variables are private and can be reached and changed by using respective getter and setter functions.
//No-argument Environment() constructor sets the static environment object of the class: bubbleTrouble
//setCanvas() create the initial game canvas.
//gameplay method includes main game animation loop
//endScreen method includes endScreen game animation loop
//displayEndScreen method is used to draw endScreen objects to screen
//displayBackground method is used to draw the background image
//displayBar Background method is used to draw the bar background image

import java.awt.Font;
import java.awt.event.KeyEvent;

public class Environment {
	
	//class constants
	private static final double X_SCALE_INITIAL=0.0, X_SCALE_FINAL=16.0, Y_SCALE_INITIAL=-1.0, Y_SCALE_FINAL=9.0;
	private static final double MAX_TIME= 40000.0;
	static final int CANVAS_WIDTH=800, CANVAS_HEIGHT=500;
	static final int PAUSE_DURATION = 20;
	
	//static variables
	private static Environment bubbleTrouble = new Environment();//Stores the game environment
	private static Bar gameBar;//Stores the game bar
	private static Player gamer;//Stores the player
	
	//instance variables
	private boolean endState=false;//stores the end game status: winning or loosing
	
	//constructor
	public Environment() {
		setGameBar(new Bar());//creates a new game bar
		setGamer(new Player());//creates a new player
		Ball.createBalls();//creates the initial balls
		setBubbleTrouble(this);//set bubbleTrouble(static environment object of the class) as this environment
	}//end of the constructor
	
	//method to create the initial game canvas
	public static void setCanvas() {
		StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);// setting the size of the canvas
		StdDraw.setXscale(X_SCALE_INITIAL, X_SCALE_FINAL);// setting the X and Y scales of the canvas
		StdDraw.setYscale(Y_SCALE_INITIAL, Y_SCALE_FINAL);// setting the X and Y scales of the canvas
	}
	
	//this is the method for playing a new game and drawing objects to the canvas
	public void gameplay() {
		
		while(true) {//animation loop begins
			
			StdDraw.clear();//clears the canvas
			
			Environment.displayBackground();//draws the game background
			
			if(Arrow.getArrowCounter()==1)//checks if there exists an active arrow in the game
				Arrow.getMyActiveArrow().drawArrow();//draws the active arrow
			
			Environment.displayBarBackground();//draws the bar background
			getGameBar().drawBar();//draws the gameBar
			getGamer().drawPlayer();//draws the player
			Ball.drawBalls();//draws the active balls
			StdDraw.show();//shows the drawings on the screen
			
			if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))// Move the player to the left
				getGamer().movePlayer(-1);//calls the move player method
			if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))// Move the player to the right
				getGamer().movePlayer(1);//calls the move player method
			if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE))// Fire an arrow
				getGamer().fireArrow();//calls the fire arrow method
			
			StdDraw.pause(PAUSE_DURATION);
			
			if(gameBar.getRemainingTime()<=0) {//checks if the game time has ended
				Ball.clearBalls();//clears active balls
				this.endScreen();//calls end screen
				break;//breaks the animation loop	
			}
			
		}//animation loop ends
	}//end of the gameplay method
	
	//this method is called to draw the objects after game ends
	public void endScreen() {
		
		while(true) {//animation loop begins
			
			StdDraw.clear();//clears the canvas
			
			Environment.displayBackground();//draws the game background
			
			if(Arrow.getArrowCounter()==1)//checks if there exists an active arrow in the game
				Arrow.getMyActiveArrow().drawArrow(true);//draws the active arrow
			
			Environment.displayBarBackground();//draws the bar background
			getGameBar().drawBar(getGameBar().getRemainingTime());//draws the gameBar
			getGamer().drawPlayer();//draws the player
			displayEndScreen();//draws the endScreen
			
			StdDraw.show();//shows the drawings on the screen
			StdDraw.pause(PAUSE_DURATION);
			
			if(StdDraw.isKeyPressed(KeyEvent.VK_Y)) {// Starts a new game
				if(Arrow.getArrowCounter()==1)
					Arrow.deleteArrow();//deleted the existing arrow
				setBubbleTrouble(new Environment());//Replace bubbleTrouble with a new environment
				getBubbleTrouble().gameplay();//Call the gameplay method
			}
			
			else if (StdDraw.isKeyPressed(KeyEvent.VK_N)) {// Quit game
				System.exit(1);// ends the game and terminates the code
			}
			
		}//animation loop ends
		
	}//end of the endScreen method
	
	//this method is called to display the end screen rectangle and texts 
	public static void displayEndScreen() {
		
		StdDraw.picture(X_SCALE_FINAL/2,Y_SCALE_FINAL/2.18, "game_screen.png",X_SCALE_FINAL/3.8,Y_SCALE_FINAL/4,0.0);
		
		StdDraw.setPenRadius(0.01);//sets the pen radius
		StdDraw.setPenColor(StdDraw.BLACK);//sets the pen color
		Font font = new Font("Helvetica", Font.BOLD, 30);//font settings for the end state message
		StdDraw.setFont(font);//sets the font to the end state settings
		
		if(bubbleTrouble.getEndState()==false)//checks if the end state false
			StdDraw.text(getxScaleFinal()/2, getyScaleFinal()/2.0, "Game Over!");//draws the "game over" message
		
		else if(bubbleTrouble.getEndState()==true)//checks if the end state true
			StdDraw.text(getxScaleFinal()/2, getyScaleFinal()/2.0, "You Won!");//draws the "you won" message
		
		font = new Font("Helvetica", Font.ITALIC, 15);//font settings for the end game options
		StdDraw.setFont(font);//sets the font to the end game options
		
		StdDraw.text(getxScaleFinal()/2, getyScaleFinal()/2.3, "To Replay Click “Y”");//draws replay text
		StdDraw.text(getxScaleFinal()/2, getyScaleFinal()/2.6, "To Quit Click “N”");//draws quit text
		
	}//end of the displayEndScreen method
	
	//method to display the game background
	public static void displayBackground() {
		StdDraw.picture(X_SCALE_FINAL/2,Y_SCALE_FINAL/2, "background.png",X_SCALE_FINAL,Y_SCALE_FINAL,0.0);//draws the game background image
	}// end of the displayBackground method
	
	//method to display the bar background
	public static void displayBarBackground() {
		StdDraw.picture(X_SCALE_FINAL/2,Y_SCALE_INITIAL/2, "bar.png",X_SCALE_FINAL,1);//draws the bar background image
	}//end of the displayBarBackground method

	//getters and setters
	public static double getxScaleInitial() {
		return X_SCALE_INITIAL;
	}

	public static double getxScaleFinal() {
		return X_SCALE_FINAL;
	}

	public static double getyScaleInitial() {
		return Y_SCALE_INITIAL;
	}

	public static double getyScaleFinal() {
		return Y_SCALE_FINAL;
	}

	public static double getMaxTime() {
		return MAX_TIME;
	}
	
	public static Environment getBubbleTrouble() {
		return bubbleTrouble;
	}
	
	public static void setBubbleTrouble(Environment myEnvironment) {
		Environment.bubbleTrouble = myEnvironment;
	}

	public static Bar getGameBar() {
		return gameBar;
	}

	public static void setGameBar(Bar gameBar) {
		Environment.gameBar = gameBar;
	}

	public boolean getEndState() {
		return endState;
	}

	public void setEndState(boolean endState) {
		this.endState = endState;
	}

	public Player getGamer() {
		return gamer;
	}

	public void setGamer(Player gamer) {
		Environment.gamer = gamer;
	}
	
}
