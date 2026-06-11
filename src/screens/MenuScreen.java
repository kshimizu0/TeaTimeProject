package screens;


//import java.awt.Point;
import core.DrawingSurface;
import processing.core.PImage;

/**
 * Represents the main menu screen of the game
 * 
 * Displays the game title/logo and allows the player to start the game by clicking a button
 * 
 * @author Cindy Cheng, Keira Shimizu, Vashini Raja
 * @version 5-25-26
 */
public class MenuScreen extends Screen {
	
	private PImage logo;

	/**
	 * Constructs the MenuScreen with a start button.
	 * 
	 * @param surface the main drawing surface
	 */
	public MenuScreen(DrawingSurface surface) {
		super(800, 600, surface);
	}
	
	/**
	 * loads the logo screen
	 */
	public void setup() {
		logo = surface.loadImage("teaTime.png");
		logo.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	/**
	 * Draws the menu screen, including background and UI elements.
	 */
	public void draw() {
		surface.background(247,216,245);
//		surface.background(235,250,240);
		surface.image(logo, 0, 0);
		
	}

	/**
	 * Handles mouse press events.
	 * If the button is clicked, switches to the game screen.
	 */
	public void mousePressed() {

	}
	
	/**
	 * handles the key press events.
	 * If any key is pressed, it switches to the game screen.
	 */
	public void keyPressed() {
		surface.switchScreen(ScreenSwitcher.INSTRUCTIONS_SCREEN);
	}
	
	/**
	 * Handles mouse movement events.
	 * Intended for hover effects on UI elements.
	 */
	public void mouseMoved() {
		
	}
	

}

