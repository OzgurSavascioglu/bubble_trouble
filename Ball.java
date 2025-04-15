//Ozgur Savascioglu, 2022400366, 16 April 2023
//This is the class for the Ball object. This class also includes the methods that calculates ball-wall, ball-arrow and ball-player interactions. 
//Ball class has 2 static variables (activeBalls and deletedBalls). The first one is used to store the active balls in the game and the second one stores the deleted balls.
//Ball class has 8 instance variables (xPosition, yPosition, initialXDirection, ballLevel, radius, xVelocity, yVelocity, and maxHeight). 
//All these instance variables are private and can be reached and changed by using respective getter and setter functions.
//There are two constructors for Ball class, second one is used to create balls after arrow hit
//drawBalls() method is used to calculate the ball movements and checks ball-wall, ball-arrow and ball-player interactions by calling relevant methods.
//wallHitCheck() method is used to check if the ball hits a wall and returns the hit direction if the ball hits the wall
//bounce() method returns the new velocity if the ball hits a wall
//playerHitCheck() is used to check if the ball hits the player and calls the endScreen() if there is a hit.
//arrowHitCheck() is used to check if the arrow hits the player, add the ball to deletedBalls list and calls the ballSplit() or endScreen() if necessary.
//splitBall() method is used to create new balls after hit occurs. 

import java.util.ArrayList;

public class Ball {
	//class constants
	private static final double PERIOD_OF_BALL=15000;
	private static final double HEIGHT_MULTIPLIER=1.75;
	private static final double RADIUS_MULTIPLIER=2.0;
	private static final double PLAYER_HEIGHT_SCALEY_RATE= Environment.getyScaleFinal()/8.0;
	private static final double MIN_POSSIBLE_HEIGHT=PLAYER_HEIGHT_SCALEY_RATE*1.4;
	private static final double MIN_POSSIBLE_RADIUS=Environment.getyScaleFinal()*0.0175;
	private static final double GRAVITY=(0.000003*Environment.getyScaleFinal());
	private static final double V_BALL=Environment.getxScaleFinal()/PERIOD_OF_BALL;
	 
	//static variables
	private static ArrayList <Ball> activeBalls = new ArrayList <Ball>();//stores the active balls in the game
	private static ArrayList <Ball> deletedBalls = new ArrayList <Ball>();//stores the deleted balls
	 
	//instance variables
	private double xPosition, yPosition;//current x and y positions of the ball
	private int initialXDirection;//creation x direction of the ball
	private int ballLevel;//level of the ball between 0 and 2
	private double radius;// radius of the ball
	private double maxHeight;// radius of the ball
	private double xVelocity, yVelocity;//current x and y velocities of the ball

	//constructor
	public Ball(double xPosition, double yPosition, int xDirection, int ballLevel) {
		this.setxPosition(xPosition);//sets the xPosition
		this.setyPosition(yPosition);//sets the yPosition
		this.setInitialXDirection(xDirection);//sets the initialXDirection
	    this.setBallLevel(ballLevel);//sets the ballLevel
	    this.setRadius();//calculates and sets the radius of the ball
	    this.setMaxHeight();//calculates and sets the max height of the ball
		this.setxVelocity(V_BALL * xDirection);//calculates and sets the x velocity
		this.setyVelocity(0);//sets the initial y velocity as 0
	    activeBalls.add(this);//add the ball to active balls arraylist
	}
	
	//second constructor
	public Ball(double xPosition, double yPosition, double yVelocity, int xDirection, int ballLevel) {
		this(xPosition, yPosition,  xDirection, ballLevel);
		this.setyVelocity(yVelocity);//sets the initial y velocity as the y velocity of the original ball
	}
	 
	//class methods
	 
	 //method that creates initial balls when a new game starts
	 public static void createBalls() {
		new Ball(Environment.getxScaleFinal()/4, MIN_POSSIBLE_HEIGHT, 1, 0);//creates initial ball 1
		new Ball(Environment.getxScaleFinal()/3, MIN_POSSIBLE_HEIGHT*Math.pow(HEIGHT_MULTIPLIER,1), -1, 1);//creates initial ball 2
		new Ball(Environment.getxScaleFinal()/4, MIN_POSSIBLE_HEIGHT*Math.pow(HEIGHT_MULTIPLIER,2), 1, 2);//creates initial ball 3
	 }//end of thecreateBalls method
	
	//method that calculates the ball movements/hits etc. and draw balls
	public static void drawBalls() {
		
		//check and remove the destroyed balls in the previous screen
		if(getDeletedBalls().size()>0) {//checks the size of the deleted balls list
			for(int i=0;i<getDeletedBalls().size();i++) {//loop 1 begins
				getActiveBalls().remove(getDeletedBalls().get(i));
			}//loop ends
			getDeletedBalls().clear();
		}//end(check and remove the destroyed balls in the previous screen)
		
		for(int i=0;i<activeBalls.size();i++) {//loop 2 begins - gets the active balls one by one
			
           double x = getActiveBalls().get(i).getxPosition();//get current x position
           double y = getActiveBalls().get(i).getyPosition();//get current y position
           
           if(getActiveBalls().get(i).playerHitCheck()) {//check if the ball hits the player
        	   //getActiveBalls().get(i).playerHit=true;// set playerHit as true - this value is to be used in the next call of this method
        	   Ball.clearBalls();//clears all the balls
        	   Environment.getBubbleTrouble().endScreen();//calls end screen with the default end status(lost)
           }//if ends
           
           getActiveBalls().get(i).setxPosition(getActiveBalls().get(i).getxPosition()+getActiveBalls().get(i).getxVelocity()*(Environment.PAUSE_DURATION));//sets next x position
           getActiveBalls().get(i).setyPosition(getActiveBalls().get(i).getyPosition()+getActiveBalls().get(i).getyVelocity()*(Environment.PAUSE_DURATION));//sets next y position
           
           getActiveBalls().get(i).setyVelocity(getActiveBalls().get(i).getyVelocity()+(-GRAVITY)*(Environment.PAUSE_DURATION));//sets next y velocity
           
           StdDraw.picture(x,y, "ball.png", 2*getActiveBalls().get(i).getRadius(), 2*getActiveBalls().get(i).getRadius());//draw the ball with current x and y positions
           
           if(getActiveBalls().get(i).wallHitCheck()!=-1) {//check if the ball hits any wall
        	   getActiveBalls().get(i).bounce(getActiveBalls().get(i).wallHitCheck());//calls the bounce method to set the velocity of the ball after wall hit
           }//end (check if the ball hits any wall now)
           
           if(Arrow.getMyActiveArrow()!=null) {//if 1 starts - check if there is an active arrow in the game
        	   
        	   if(getActiveBalls().get(i).arrowHitCheck(Arrow.getMyActiveArrow().getxArrow(),Arrow.getMyActiveArrow().getyArrow())!=false){//if 2 starts - check if the ball is hit by the arrow
        		   Arrow.deleteArrow();//deletes the arrow
        		   
        		   if(getActiveBalls().get(i).getBallLevel()!=0) {//if 3 starts - checks the ball level
        			   getActiveBalls().get(i).splitBall(); //if level!=0 splits the ball to two
        		   }//if 3 ends
        		   
        		   else if(getActiveBalls().get(i).getBallLevel()==0) {//else if starts 
        			   getDeletedBalls().add(getActiveBalls().get(i));//if level==0 add the ball to the deleted balls array (the ball will be deleted in the beginning of the next call of this method)
        			   
        			   if(getActiveBalls().size()==1) {//if 4 starts - checks if it is the last active ball
        				   Environment.getBubbleTrouble().setEndState(true);//set ends state as won
        				   Ball.clearBalls();//clears all the balls
        				   Environment.getBubbleTrouble().endScreen();//calls end screen
        			   }//if 4 ends
        			   
        		   }//else if ends
        		   
        	   }//if 2 ends
        	   
           }//if 1 ends
           
		}//loop 2 ends - gets the active balls one by one
		
	}//end of the draw balls method
	
	//method to split balls after arrow hit
	public void splitBall() {
		
		new Ball(this.getxPosition(), this.getyPosition(),Math.abs(this.getyVelocity()), 1, (this.getBallLevel()-1));//sets the position and direction of the ball 1
		new Ball(this.getxPosition(), this.getyPosition(), Math.abs(this.getyVelocity()), -1, (this.getBallLevel()-1));//sets the position and direction of the ball 2
		
		getDeletedBalls().add(this);//add the ball to the deleted balls list (the ball will be deleted in the beginning of the next call of this method)
	}//end of splitBall method

	//method to clear activeBalss arraylist
	public static void clearBalls() {
		activeBalls.clear();
	}//end of clearBalls method
	
	//method that returns the hit direction if the ball hits the wall, else return -1
	public int wallHitCheck() {
		int returnValue=-1;//"No hit" case return value
		if(this.getxPosition()+(this.getxVelocity()*Environment.PAUSE_DURATION)+this.getRadius()>=Environment.getxScaleFinal())//checks if the ball hits the right wall
			returnValue=0;
		else if(this.getyPosition()+(this.getyVelocity()*Environment.PAUSE_DURATION)+this.getRadius()>=Environment.getyScaleFinal())//checks if the ball hits the top wall
			returnValue=1;
		else if(this.getxPosition()+(this.getxVelocity()*Environment.PAUSE_DURATION)-this.getRadius()<=0)//checks if the ball hits the left wall
			returnValue=2;
		else if(this.getyPosition()+(this.getyVelocity()*Environment.PAUSE_DURATION)-this.getRadius()<=0)//checks if the ball hits the bottom wall
			returnValue=3;
		return returnValue;//returns the hit direction if the ball hits the wall, else return -1
	}
	
	//method that changes the velocity of the ball after the wall hit
	public void bounce(int hitDirection) {
		switch(hitDirection) {
			case 0://checks if the ball hits the right wall
			this.setxVelocity(-this.getxVelocity());
			break;
			case 1://checks if the ball hits the top wall
			//this.setyVelocity(-this.getyVelocity());
			break;
			case 2://checks if the ball hits the left wall
			this.setxVelocity(-this.getxVelocity());
			break;
			case 3:{//checks if the ball hits the bottom wall
			this.setyVelocity(-this.getyVelocity());
			if(Math.abs(this.getyVelocity())>Math.abs(this.maxyVelocityAfterWallHit()))//checks if the value is over the max possible y velocity
				this.setyVelocity(this.maxyVelocityAfterWallHit());//set the value to max possible y
			break;
			}
		}
	}//end of the bounce method 
	
	//this method returns true if the ball hits the player, else returns false
	public boolean playerHitCheck() {
		
		boolean returnValue=false;//"No hit" case return value
		double xDifLeft, xDifRight, yDifTop;//variables to store x and y axis distance
		double hitValue, hitValue2;//boundary variables
		double playerPosition= Environment.getBubbleTrouble().getGamer().getxPlayer();//Get player position
		xDifLeft=Math.abs(this.getxPosition()-(playerPosition-Player.getPlayerWidth()/2));//calculates the x axis difference between the center of the ball and left side of the player
		xDifRight=Math.abs(this.getxPosition()-(playerPosition+Player.getPlayerWidth()/2));//calculates the x axis difference between the center of the ball and right side of the player
		yDifTop=Math.abs(this.getyPosition()-(Player.getPlayerHeight()));//calculates the y axis difference between the center of the ball and top side of the player
		
		if(xDifLeft<this.getRadius()) {//checks if it is possible to hit the ball from the left side with the current x-axis value 
			double boundary=Math.abs(Math.pow(this.getRadius(),2)- Math.pow(xDifLeft,2));//calculate the boundary y-axis difference value
			hitValue=this.getyPosition()-Math.sqrt(boundary);//calculate the boundary y value of the ball to hit the player
			
			if(hitValue<=Player.getPlayerHeight())//checks the height of the player
				returnValue=true;//"Hit" case return value
			
		}
		
		else if(xDifRight<this.getRadius()) {//checks if it is possible to hit the ball from the right side with the current x-axis value
			double boundary=Math.abs(Math.pow(this.getRadius(),2)- Math.pow(xDifRight,2));//calculate the boundary y-axis difference value
			hitValue=this.getyPosition()-Math.sqrt(boundary);//calculate the boundary y value of the ball to hit the player
			
			if(hitValue<=Player.getPlayerHeight())//checks the height of the player
				returnValue=true;//"Hit" case return value
			
		}
		
		else if(yDifTop<this.getRadius()) {//checks if it is possible to hit the ball with the current y-axis value
			double boundary=Math.abs(Math.pow(this.getRadius(),2)- Math.pow(yDifTop,2));//calculate the boundary y-axis difference value
			hitValue=this.getxPosition()-Math.sqrt(boundary);//calculate the left boundary x value of the ball to hit the player
			hitValue2=this.getxPosition()+Math.sqrt(boundary);//calculate the right boundary x value of the ball to hit the player
			
			if(hitValue<=playerPosition+Player.getPlayerWidth()/2 && playerPosition-Player.getPlayerWidth()/2<=hitValue2)//checks the x position of the player
				returnValue=true;//"Hit" case return value
		}
		
		else if(this.getyPosition()<=Player.getPlayerHeight()) {//this loop is to check the boundary condition that the ball is inside of the player rectangle and does not touch any of the borders
			if(this.getxPosition()<=playerPosition+Player.getPlayerWidth()/2 && playerPosition-Player.getPlayerWidth()/2<=this.getxPosition())// checks the x position
				returnValue=true;//"Hit" case return value
		}
		
		return returnValue;//returns true if the ball hits the player, else returns false
		
	}//end of the playerHitCheck method
		
	//this method returns true if the ball is hit by an arrow, else returns false
	public boolean arrowHitCheck(double xArrow, double yArrow) {
		
		boolean returnValue=false;//"No hit" case return value
		double xDif, lowerDistanceCheck;//Variables to calculate the x-axis difference and lowest height for arrow to hit the ball
		xDif=Math.abs(this.getxPosition()-xArrow);//calculate the x-axis difference
		
		if(xDif<this.getRadius()) {//checks if it is possible to hit the ball with the current x-axis value
			double boundary=Math.abs(Math.pow(this.getRadius(),2)- Math.pow(xDif,2));//calculate the boundary y-axis difference value
			lowerDistanceCheck=this.getyPosition()-Math.sqrt(boundary);//calculate the lowest height to hit the arrow
			
			if(lowerDistanceCheck<=yArrow)//checks the y position of the arrow
				returnValue=true;//"Hit" case return value
			
		}
		
		return returnValue;//returns true if the ball is hit by an arrow, else returns false
	}
	
	//this method is used the change the y velocity after hit to the bottom wall to prevent the ball from exceeding max height
	public double maxyVelocityAfterWallHit() {
		return Math.sqrt(this.getMaxHeight()*2*GRAVITY);//calculates and returns the max possible y velocity after hit
	}//end of the maxyVelocityAfterWallHit method
	
	//setters and getters
	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	private void setRadius() {
		this.radius=MIN_POSSIBLE_RADIUS*Math.pow(RADIUS_MULTIPLIER,ballLevel);
	}
	
	private void setMaxHeight() {
		this.maxHeight=MIN_POSSIBLE_HEIGHT*Math.pow(HEIGHT_MULTIPLIER,ballLevel);
	}

	public double getMaxHeight() {
		return maxHeight;
	}

	private double getRadius() {
		return this.radius;
	}

	public int getInitialXDirection() {
		return initialXDirection;
	}

	public void setInitialXDirection(int initialXDirection) {
		this.initialXDirection = initialXDirection;
	}

	public void setBallLevel(int ballLevel) {
		this.ballLevel = ballLevel;
	}
	
	public int getBallLevel() {
		return ballLevel;
	}
			
	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public static double getHeightMultiplier() {
		return HEIGHT_MULTIPLIER;
	}

	public static double getMinPossibleHeight() {
		return MIN_POSSIBLE_HEIGHT;
	}
		
	public static void setActiveBalls(ArrayList<Ball> activeBalls) {
		Ball.activeBalls = activeBalls;
	}
	
	public static ArrayList<Ball> getActiveBalls() {
		return activeBalls;
	}

	public static void setDeletedBalls(ArrayList <Ball> deletedBalls) {
		Ball.deletedBalls = deletedBalls;
	}
	
	public static ArrayList <Ball> getDeletedBalls() {
		return deletedBalls;
	}

}
