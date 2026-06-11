package screens;

import core.DrawingSurface;
import processing.core.PImage;

/**
 * Represents the screen that appears when the player wins the game
 * 
 * @author Cindy Cheng, Keira Shimizu, Varshini Raja
 * @version 5-25-2026
 */
public class WinScreen extends Screen {

	private PImage winScreen;
	
	/**
	 * Constructs the WinScreen with the designated DrawingSurface 
	 * @param surface the main DrawingSurface to be used
	 */
	public WinScreen(DrawingSurface surface) {
		super(1200, 600, surface);
	}
	
	/**
	 * Loads the images
	 */
	public void setup() {
		winScreen = surface.loadImage("winScreen.png");
	}
	
	/**
	 * Draws the screen
	 */
	public void draw() {
		surface.image(winScreen, 0, 0, 1200, 600);
	}

}
