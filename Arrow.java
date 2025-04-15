//Ozgur Savascioglu, 2022400366, 16 April 2023
//This is the class for the Arrow object. 
//Ball class has two static variables (arrowCounter,myActiveArrow). The first one counts the number of arrows and the second one stores the active arrow in the game.
//Ball class has 2 instance variables (xArrow and yArrow). 
//All these instance variables are private and can be reached and changed by using respective getter and setter functions.
//This class has also a no-argument constructor, which is used to create the initial activeArrow
//drawArrow() method is used to draw the arrow, this method calls deleteArrow() and moveArrow functions depending on the position of the arrow.
//There are two versions of this method, the second one(with boolean parameter) is used to draw the arrow at the endScreen
//moveArrow() method is used to calculate new y position of the top of the arrow
//deleteArrow() method is used to delete the arrow and reset the counter

public class Arrow {
	//class constants
	private static final double PERIOD_OF_ARROW=1500;
	private static final double V_ARROW=Environment.getyScaleFinal()/PERIOD_OF_ARROW;
	
	//static variables
	private static int arrowCounter=0;//counts the number of active arrows
	private static Arrow myActiveArrow = new Arrow();//stores the active arrow
	
	//instance variables
	private double xArrow, yArrow;//x coordinate of the arrow and y coordinate of the top of the arrow

	//no-argument constructor
	public Arrow(){
	}//end of no-argument constructor
	
	//constructor
	public Arrow(double xCoordinate){
		if(getArrowCounter()==0) {//start of if
			this.setxArrow(xCoordinate);//x coordinate of the arrow
			this.setyArrow(0.0);//y coordinate of the top of the arrow
			setMyActiveArrow(this);//sets the activeArrow to this
			setArrowCounter(getArrowCounter()+1);//increases the arrowCounter 
		}//end of if
	}//end of the constructor
	
	//this method is used to draw the arrow, this method calls deleteArrow() and moveArrow functions depending on the position of the arrow.
	public void drawArrow(){
		StdDraw.picture(getxArrow(),-Environment.getyScaleFinal()/2+getyArrow(), "arrow.png");//draws the arrow to the screen
		if(this.getyArrow()>=Environment.getyScaleFinal())//check if the arrow leaves the screen
			deleteArrow();//calls the delete method
		else 
			this.moveArrow();//calls the move method
	}//end of the drawArrow() method
	
	public void drawArrow(boolean endScreen) {
		StdDraw.picture(getxArrow(),-Environment.getyScaleFinal()/2+getyArrow(), "arrow.png");//draws the arrow to the screen
	}//end of the drawArrow() method
	
	//this is the method that calculates the next position of the arrow
	private void moveArrow() {
		this.setyArrow(this.getyArrow()+getvArrow()*(Environment.PAUSE_DURATION));//calculates the next yPosition of the arrow
	}//end of the moveArrow() method

	//this is the method that deletes the arrow
	public static void deleteArrow() {
		setMyActiveArrow(null);//set the active arrow as null
		setArrowCounter(getArrowCounter()-1);//reset the arrowCounter
	}//end of the deleteArrow() method

	//setters and getters
	public double getxArrow() {
		return xArrow;
	}

	public void setxArrow(double xArrow) {
		this.xArrow = xArrow;
	}

	public double getyArrow() {
		return yArrow;
	}

	public void setyArrow(double yArrow) {
		this.yArrow = yArrow;
	}

	public static Arrow getMyActiveArrow() {
		return myActiveArrow;
	}

	public static void setMyActiveArrow(Arrow myActiveArrow) {
		Arrow.myActiveArrow = myActiveArrow;
	}

	public static int getArrowCounter() {
		return arrowCounter;
	}

	public static void setArrowCounter(int arrowCounter) {
		Arrow.arrowCounter = arrowCounter;
	}

	public static double getvArrow() {
		return V_ARROW;
	}
	
}
