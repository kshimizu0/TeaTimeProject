package screens;

import core.DrawingSurface;

/**
 * Abstract base class for all game screens
 * 
 * Defines the structure and methods that every screen has to support
 * 
 * @author Cindy Cheng, Keira Shimizu, Vashini Raja
 * @version 5-25-26
 */
public abstract class Screen {

	public final int SCREEN_WIDTH, SCREEN_HEIGHT;
	protected final DrawingSurface surface;
	private boolean displayed;
	
	/**
	 * Constructs a Screen with a given size and drawing surface
	 * 
	 * @param width the width of the screen
	 * @param height the height of the screen
	 * @param surface the main drawing surface
	 */
	public Screen(int width, int height, DrawingSurface surface) {
		this.SCREEN_WIDTH = width;
		this.SCREEN_HEIGHT = height;
		this.surface = surface;
	}
	
	/**
	 * Updates screen logic
	 * Called every frame before drawing
	 */
	
	/**
	 * Initializes screen elements
	 * Called once when the screen is first created
	 */
	public void setup() {
		
	}
	
	/**
	 * Draws all visual elements for this screen
	 * Called continuously each frame
	 */
	public void draw() {
		
	}
	
	/**
	 * Handles mouse press events
	 */
	public void mousePressed() {
		
	}
	
	/**
	 * Handles mouse release events
	 */
	public void mouseReleased() {
		
	}
	
	/**
	 * called when the key is pressed
	 */
	public void keyPressed() {
		
	}
	
	/**
	 * Called when this screen is switched to.
	 */
	public void switchedTo() {
		
	}
	
	public boolean isDisplayed() {
		return displayed;
	}
	
	public void display() {
		displayed = true;
	}
	
	
	
}
