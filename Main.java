//Ozgur Savascioglu, 2022400366, 16 April 2023
//Environment.setCanvas() creates the canvas
//Environment.getBubbleTrouble().gameplay() starts the Bubble Trouble game

public class Main {
	
	public static void main (String[] args){
		
		StdDraw.enableDoubleBuffering();//for faster animations
		Environment.setCanvas();// sets the canvas 
		Environment.getBubbleTrouble().gameplay();//starts new game
	}

}
