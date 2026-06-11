package gameElements;

import jay.jaysound.JayLayer;
import kshimizu.shapes.Shape;
import processing.core.PApplet;

/**
 * Represents the tea falling from the dispenser as the user
 * presses the button
 * 
 * @author Cindy Cheng
 * @version 5/25/26
 */
public class PhysicsTea{
	
	private double dropV, dropHeight;
	private double fillV, fillHeight;
	private int x, y;
	private Tea tea;
	private boolean mouseIsPressed, stopDrawing, mouseReleased, teaFallen, start;
	
	private JayLayer effectsPlayer;
	
	/**
	 * creates an object of type PhysicsTea
	 * @param tea the tea that the user chose
	 */
	public PhysicsTea(Tea tea) {
		dropV = 7;
		dropHeight = 0;
		fillHeight = 0;
		fillV = 8;
		mouseIsPressed = false;
		mouseReleased = false;
		teaFallen = false;
		stopDrawing = false;
		this.tea = tea;
		x = 870;
		y = 100;
	}
	
	/**
	 * loads the song into the player
	 */
	public void setup() {
		effectsPlayer = new JayLayer("audio/", "audio/", false);
		effectsPlayer.addSoundEffect("pourSound.mp3");
	}
	
	/**
	 * causes the tea stream to fall while the user presses the dispenser,
	 * the stream is cut off once the user stops pressing the button
	 */
	public void act() {
		if (mouseIsPressed) {
			if(!start)
			if (dropHeight < 320) {
				dropHeight += dropV;
			} else {
				fillHeight += fillV;
			}
		} else {
			if (y < 420) {
				y += dropV;
				if (y + dropHeight > 420) {
					dropHeight -= dropV;
				}
			}
			else {
				dropHeight = 0;
				stopDrawing = true;
				teaFallen = true;
				
			}
		}
	}
	
	
	public void setStart(boolean value) {
		start = value;
	}
	
	/**
	 * draws the tea stream onto the screen
	 * @param surface the PApplet used to draw with
	 */
	public void draw(PApplet surface) {
		if (mouseReleased && teaFallen || y >= 420) {
		} else {
			surface.push();
			surface.noStroke();

			// main stream
			surface.fill(tea.getColor().getRGB(), 180);
			surface.rect((float)x, (float)y, (float)14, (float)dropHeight, 10);

			// side liquid strands
			surface.fill(tea.getColor().getRGB(), 120);

			surface.rect((float)x - 4,
					(float)y + 10,
					(float)4,
					(float)(dropHeight - 20),
					10);

			surface.rect((float)x + 14,
					(float)y + 20,
					(float)3,
					(float)(dropHeight - 35),
					10);

			// droplets
			if (dropHeight > 40) {

				surface.fill(tea.getColor().getRGB(), 150);

				surface.ellipse(
						(float)x + 7,
						(float)(y + dropHeight),
						10,
						14);

				surface.ellipse(
						(float)x + 2,
						(float)(y + dropHeight - 18),
						6,
						8);
			}

			surface.pop();
		}
	}

	/**
	 * draws smoother tea fill inside the cup
	 * 
	 * @param surface the PApplet used to draw with
	 * @param height the tea fill height
	 * @param cupX x-position of cup fill
	 * @param cupBottom bottom y-position of cup
	 * @param cupWidth width of tea fill
	 */
	public void drawCupFill(PApplet surface, double height, int cupX, int cupBottom, int cupWidth) {
		surface.push();
		surface.noStroke();
		surface.fill(tea.getColor().getRGB(), 190);
		//surface.fill(tea.getColor().getRGB());
		surface.rect((float)cupX, (float)(cupBottom - height), (float)cupWidth, (float)height, 0, 0, 18, 18);
		surface.fill(255, 255, 255, 40);

		for (int i = 0; i < cupWidth; i += 18) {
			float waveY = (float)(cupBottom - height) + (float)(Math.sin((surface.frameCount + i) * 0.08) * 3);
			surface.ellipse(cupX + i, waveY, 18, 5);
		}

		surface.pop();
	}
	
	/**
	 * sets the state of whether or not the user is pressing the mouse
	 * @param mousePressed whether or not the user is holding the button
	 */
	public void setState(boolean mousePressed) {
		mouseIsPressed = mousePressed;
	}
	
	/**
	 * sets the state of whether or not the user has released the mouse
	 * @param mouseReleased whether or not the user has released the mouse
	 */
	public void setMouseReleased(boolean mouseReleased) {
		this.mouseReleased = mouseReleased;
	}
	
	/**
	 * returns whether the user is pressing the mouse or not
	 * @return true if the mouse is pressed; false otherwise
	 */
	public boolean getState() {
		return mouseIsPressed;
	}
	
	/**
	 * gets whether the tea has stopped drawing when it has fallen to the bottom of the cup
	 * @return true if the tea should not be drawn anymore; false otherwise
	 */
	public boolean stopDrawing() {
		return stopDrawing;
	}
	
	/**
	 * gets the y position of the tea stream
	 * @return y the y position of the tea stream
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * gets the height of the tea stream
	 * @return dropHeight the height of the tea stream
	 */
	public double getDropHeight() {
		return dropHeight;
	}
}