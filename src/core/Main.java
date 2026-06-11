package core;

import processing.core.PApplet;

/**
 * Main class runs the program
 * @author Cindy Cheng, Keira Shimizu, Varshini Raja
 * @version 5-25-26
 */
public class Main {

	/**
	 * The main method that the program runs
	 */
	public static void main(String args[]) {
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		drawing.windowResizable(false);
		}
	
}
