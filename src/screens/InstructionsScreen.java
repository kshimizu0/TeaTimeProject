package screens;

import core.DrawingSurface;
import processing.core.PImage;

/**
 * Represents the screen where the instructions for the game show
 * 
 * @author Cindy Cheng, Keira Shimizu
 * @version 5-25-26
 */
public class InstructionsScreen extends Screen{

	private Button next;
	private PImage[] instructions;
	private int index;
	
	/**
	 * Constructs a screen that the user can click through for instructions
	 * @param surface the main DrawingSurface used
	 */
	public InstructionsScreen(DrawingSurface surface) {
		super(1200, 600, surface);
		instructions = new PImage[4];
		next = new Button(1086, 265, 92, 80, "");
		index = 0;
	}
	
	/**
	 * initializes the images of the screen
	 */
	public void setup() {
		instructions[0] = surface.loadImage("instructions-2/1.png");
		instructions[1] = surface.loadImage("instructions-2/2.png");
		instructions[2] = surface.loadImage("instructions-2/3.png");
		instructions[3] = surface.loadImage("instructions-2/4.png");
	}
	
	/**
	 * draws the instructions onto the screen
	 */
	public void draw() {
		surface.strokeWeight(2);
		next.draw(surface);
		surface.image(instructions[index], 0, 0, 1200, 600);
	}
	
	/**
	 * handles mouse pressed events
	 */
	public void mousePressed() {
		if (next.isClicked(surface.mouseX, surface.mouseY)) {
			if (index == 3) {
				surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			} else {
				index++;
			}
		}
	}
	
	public void switchedTo() {
		index = 0;
	}

}
