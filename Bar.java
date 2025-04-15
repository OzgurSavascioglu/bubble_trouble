//Ozgur Savascioglu, 2022400366, 16 April 2023
//This is the class for the Bar object. 
//Bar class has 2 instance variables (remainingTime and startTime). 
//All instance variables are private and can be reached and changed by using respective getter and setter functions.
//drawBar() method is used to calculate the bar size and color on a given time and draw the bar.
//There are two versions of this method, the second one(with double parameter) is used to draw the bar at the endScreen
//calculateTime() method is used to calculate the time passed from the start of the game.
//calculateRemainingTime() method is used to calculate the time remaining.

import java.awt.Color;

public class Bar {
	//class constants
	private static final double HEIGHT_TOTAL=-1.0;//sets the highest position of the bar background on canvas scale
	private static final double HEIGHT_BAR=0.5;//sets the height of the bar
	private static final double RED_START= 225, RED_END=225, GREEN_START= 225, GREEN_END=0; //declares and sets the boundary RGB values
	
	//instance variables
	private double startTime;
	private double remainingTime;
	
	//constructor
	public Bar() {
		startTime=System.currentTimeMillis();	
	}//end of the constructor
	
	//method to draw the bar
	public void drawBar() {
		
		int red, green;//variables to store red and green values in a specific moment
		double barScale;//variable to store the bar scale in a given time
		Color color;//variable to store the color in a given time
		
		setRemainingTime(this.calculateRemainingTime());//calculates the remaining time
		
		if(getRemainingTime()<0)//checks if the remaining time is less than zero
			setRemainingTime(0);//if it is less than 0, makes remaining time=0
		
		red=(int)((getRemainingTime()/Environment.getMaxTime())*(RED_START-RED_END)+RED_END);//calculates the red value in a given time
		green= (int)((getRemainingTime()/Environment.getMaxTime())*(GREEN_START-GREEN_END)+GREEN_END);//calculates the green value in a given time
		barScale=(getRemainingTime()/Environment.getMaxTime())*(Environment.getxScaleFinal());//calculates the bar scale value in a given time
		color = new Color(red,green,0);//sets the color value in a given time
		StdDraw.setPenColor(color);//set the pen color with the color value
		
		if(barScale>0)//checks if the bar scale is greater than 0
			StdDraw.filledRectangle(barScale/2, HEIGHT_TOTAL/2, barScale/2, HEIGHT_BAR/2);//draws the bar
		
	}//end of the drawBar method
	
	//method to draw the bar at the end screen
	public void drawBar(double remainingTime) {
		
		int red, green;//variables to store red and green values in a specific moment
		double barScale;//variable to store the bar scale in a given time
		Color color;//variable to store the color in a given time
		
		setRemainingTime(remainingTime);//calculates the remaining time
		
		if(getRemainingTime()<0)//checks if the remaining time is less than zero
			setRemainingTime(0);//if it is less than 0, makes remaining time=0
		
		red=(int)((getRemainingTime()/Environment.getMaxTime())*(RED_START-RED_END)+RED_END);//calculates the red value in a given time
		green= (int)((getRemainingTime()/Environment.getMaxTime())*(GREEN_START-GREEN_END)+GREEN_END);//calculates the green value in a given time
		barScale=(getRemainingTime()/Environment.getMaxTime())*(Environment.getxScaleFinal());//calculates the bar scale value in a given time
		color = new Color(red,green,0);//sets the color value in a given time
		StdDraw.setPenColor(color);//set the pen color with the color value
		
		if(barScale>0)//checks if the bar scale is greater than 0
			StdDraw.filledRectangle(barScale/2, HEIGHT_TOTAL/2, barScale/2, HEIGHT_BAR/2);//draws the bar
		
	}//end of the drawBar method
	
	//method that calculates the time passed
	public double calculateTime() {
		return System.currentTimeMillis()-startTime;
	}//end of the calculateTime method
	
	//method to calculate remaining game time
	public double calculateRemainingTime() {
		return Environment.getMaxTime()- this.calculateTime();//calculates and returns the remaining time
	}//end of the calculateRemainingTime()

	//getters and setters
	public double getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(double remainingTime) {
		this.remainingTime = remainingTime;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}
	
}
