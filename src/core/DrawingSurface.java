package core;


import java.awt.Point;
import java.util.ArrayList;

import processing.core.PApplet;
import screens.*;

/**
 * DrawingSurface class starts the program
 * It uses the processing library
 * It manages switching between screens, scaling the screen
 * It takes in user input 
 * Actively draws on the screen that's active
 * 
 * @author Cindy Cheng, Keira Shimizu, Vashini Raja
 * @version 5-25-2026
 */

public class DrawingSurface extends PApplet implements ScreenSwitcher {

	public static final int MENU_SCREEN = 0; // menu screen index
	public static final int GAME_SCREEN = 1; // game screen index
	public static final int SCORE_SCREEN = 2;
	public static final int INSTRUCTIONS_SCREEN = 3;
	public static final int WIN_SCREEN = 4;
	
	private static final int DRAWING_WIDTH = 1200, DRAWING_HEIGHT = 600;
	public float ratioX, ratioY; // scaling ratios 
	
	private Screen activeScreen; // currently active screen
	private ArrayList<Screen> screens; // list of all screens 

	/**
	 * Creates the DrawingSurface
	 * Initializes the screens
	 * tracks keys
	 */
	public DrawingSurface() {
		
		screens = new ArrayList<Screen>();
		
		MenuScreen screen1 = new MenuScreen(this);
		screens.add(screen1);
		
		GameScreen screen2 = new GameScreen(this);
		screens.add(screen2);
		
		ScoreScreen screen3 = new ScoreScreen(this, screen2);
		screens.add(screen3);
		
		InstructionsScreen screen4 = new InstructionsScreen(this);
		screens.add(screen4);
		
		WinScreen screen5 = new WinScreen(this);
		screens.add(screen5);
		
		activeScreen = screens.get(MENU_SCREEN);
	}
	
	/**
	 * Sets the window size 
	 */
	
	public void settings() {
		setSize(DRAWING_WIDTH, DRAWING_HEIGHT);
	}
	
	/**
	 * Calls setup on all screens 
	 */
	
	public void setup() {
		for (Screen s : screens)
			s.setup();
	}
	
	/**
	 * Main drawing loop method
	 * Scales and implements drawing to the active screen
	 */
	
	public void draw() {
		ratioX = (float)width/activeScreen.SCREEN_WIDTH;
		ratioY = (float)height/activeScreen.SCREEN_HEIGHT;

		push();
		
		scale(ratioX, ratioY);
		
		activeScreen.draw();
		
		pop();
		
		
	}
	
	/**
	 * Handles the key pressing 
	 * Prevents duplicate keys in storage
	 * Prevents ESC key from closing the program
	 */
	public void keyPressed() {
		if (key == ESC)  // This prevents a processing program from closing on escape key
			key = 0;
		
		activeScreen.keyPressed();
	}

	/**
	 * Implements mouse pressed event to the active screen
	 */
	public void mousePressed() {
		activeScreen.mousePressed();
	}
	
	/**
	 * Implements mouse released event to the active screen
	 */
	public void mouseReleased() {
		activeScreen.mouseReleased();
	}
	
	/**
	 * Converts drawing coordinates to screen coordinates using scaling ratios.
	 * 
	 * @param drawing the point in drawing coordinates
	 * @return the corresponding point in screen coordinates
	 */
	public Point drawingCoordinatesToScreenCoordinates(Point drawing) {
		return new Point((int)(drawing.getX()*ratioX), (int)(drawing.getY()*ratioY));
	}
	
	/**
	 * Converts screen coordinates to drawing coordinates using scaling ratios.
	 * 
	 * @param screen the point in screen coordinates
	 * @return the corresponding point in drawing coordinates
	 */
	public Point screenCoordinatesToDrawingCoordinates(Point screen) {
		return new Point((int)(screen.getX()/ratioX) , (int)(screen.getY()/ratioY));
	}

	/**
	 * Restarts the game. (not implemented rn)
	 */
	public void restartGame() {
		
	}
	
	/**
	 * Switches the active screen.
	 * 
	 * @param i the index of the screen to switch to
	 */
	@Override
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
		activeScreen.switchedTo();
	}


}
