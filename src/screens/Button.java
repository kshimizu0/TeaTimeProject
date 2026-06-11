package screens;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;

/**
 * Represents a button you click in the user interface
 * 
 * @author Varshini Raja
 * @version 5-25-26
 */

public class Button extends Rectangle2D.Double {
	
	private Color strokeColor;
	private String text; // text displayed on button
	private boolean active; 
	
	/**
	 * Constructs a rectangular Button with position, size, and label text
	 * 
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param w the width of the button
	 * @param h the height of the button
	 * @param text the label displayed on the button
	 */
	public Button(double x, double y, double w, double h, String text) {
		super(x,y,w,h);
		this.text = text;
		active = true;
		strokeColor = Color.black;
	}
	
	
	/**
	 * Constructs a circular Button
	 * 
	 * @param x the x-coordinate of the circular button
	 * @param y the y-coordinate of the circular button
	 * @param radius the radius of the circular button
	 * @param text the label displayed on the button
	 */
	public Button(double x, double y, double radius, String text) {
		super(x - radius, y - radius, radius * 2, radius *2);
		this.text = text;
		active = true;
		strokeColor = Color.black;
	}
	
	/**
	 * Draws the button on the Processing surface passed in
	 * Has a border and centered text label
	 * 
	 * @param surface the PApplet used for rendering
	 */
	public void draw(PApplet surface) {
		surface.stroke(strokeColor.getRGB());
		surface.strokeWeight(8);
		surface.noFill();
		surface.rect((float)x, (float)y, (float)width, (float)height);
		
		surface.textSize(32);
		surface.textAlign(PApplet.CENTER);
		
		surface.text(text, (float)x+(float)width/2, (float)y+(float)height/2+surface.textAscent()/4);
		surface.noFill();
	}
	
	/**
	 * Draws a circular button on the Processing surface passed in
	 * Displays a circle and text label
	 * 
	 * @param surface the PApplet used for rendering
	 */
	public void drawCircle(PApplet surface) {
		surface.stroke(strokeColor.getRGB());
		surface.strokeWeight(3);
		surface.fill(200);
		
		surface.ellipse((float)x, (float)y, (float)width, (float)width);
		
		surface.fill(0);
		
		surface.textSize(20);
		surface.textAlign(PApplet.CENTER, PApplet.CENTER);
		
		surface.text(text, (float)x, (float)y);
		surface.noFill();
	}
	
	/**
	 * Determines whether the button has been clicked based on mouse coordinates
	 * 
	 * @param myX the x-coordinate of the mouse click
	 * @param myY the y-coordinate of the mouse click
	 * @return true if the click is within the button bounds, false otherwise
	 */
	public boolean isClicked(int myX, int myY) {
		return x <= myX && x+width >= myX && y <= myY && y+height >= myY;
	}
	
	/**
	 * Determines whether a circular button has been clicked based on mouse coordinates
	 * 
	 * @param mx the x-coordinates of the mouse click
	 * @param my the y-coordinates of the mouse click
	 * @return true if the click is inside the circle, false otherwise
	 */
	public boolean isCircleClicked(int mx, int my) {
		double dx = mx - x;
		double dy = my - y;
		
		double radius = width / 2;
		
		return dx * dx + dy * dy <= radius * radius;
	}
	
	/**
	 * Returns the text label of the button
	 * 
	 * @return the button name
	 */
	public String getName() {
		return text;
	}
	
	/**
	 * Returns whether the button is active
	 * 
	 * @return true if the button is active, false otherwise
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Sets whether the button is active 
	 * 
	 * @return state the new active state of the button
	 */
	public void setState(boolean state) {
		active = state;
	}
	
	/**
	 * Sets the stroke color to a new color based on RGB values
	 * 
	 * @param r the red value of the new color
	 * @param g the green value of the new color
	 * @param b the blue value of the new color
	 */
	public void setStroke(int r, int g, int b) {
		strokeColor = new Color(r, g, b);
	}
}
