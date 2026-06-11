package gameElements;

import java.awt.Color;

import kshimizu.shapes.Rectangle;
import kshimizu.shapes.Shape;
import processing.core.PApplet;

/**
 * Represents a straw with physics
 * 
 * @author Cindy Cheng
 * @version 5/25/26
 */
public class PhysicsStraw {
	
	private double x, y;
	private double vx;
	private boolean isStrawberry;
	private Shape s;
	
	/**
	 * Constructs a straw with physics
	 * @param s the shape of the straw
	 */
	public PhysicsStraw(Shape s) {
		this.s = s;
		vx = 7;
		isStrawberry = false;
	}
	
	/**
	 * moves the straw back and forth horizontally
	 */
	public void shift() {
		if(s.getX() > 500 && s.getX() < 670) {
			s.translate(vx, 0);
		} else if (s.getX() <= 500) {
			vx = -vx;
			s.translate(vx, 0);
		} else if(s.getX() >= 670) {
			vx = -vx;
			s.translate(vx, 0);
		}
	}
	
	/**
	 * the action of the straw poking the cup
	 */
	public void pokeAction() {
		int vy1 = 3;
		vx = 0;
		int pokeHeight = 80;
		if (isStrawberry) {
			pokeHeight = 50;
		}
		if(s.getY() >= pokeHeight) {
			Rectangle r = (Rectangle) s;
			if (r.getHeight() <= 90) {
				vy1 = 0;
				
			} else {
				setHeight(r.getHeight() - vy1);
			}
		}
		s.translate(0, vy1);
	}

	/**
	 * sets the height of the straw to a new height
	 * @param height the new height of the straw
	 */
	private void setHeight(double height) {
		Rectangle r = (Rectangle) s;
		s = new Rectangle(s.getX(), s.getY(), r.getWidth(), height);
		s.setFillColor(r.getFillColor());
	}
	
	/**
	 * draws the straw onto the screen
	 * @param surface the PApplet used to draw
	 */
	public void draw(PApplet surface) {
		Rectangle r = (Rectangle) s;
		surface.push();
		surface.fill(r.getFillColor().getRGB());
		surface.noStroke();
		surface.rect((float)r.getX(), (float)r.getY(), (float)r.getWidth(), (float)r.getHeight(), (float)10, (float)10, (float)2, (float)2);
		surface.pop();
	}
	
	/**
	 * called if the drink is strawberry flavored
	 * sets isStrawberry to true
	 */
	public void isStrawberry() {
		isStrawberry = true;
	}
	
	/**
	 * Sets the fill color of the shape to a new color in RGB form
	 * @param r the red value of the color
	 * @param g the green value of the color
	 * @param b the blue value of the color
	 */
	public void setFillColor(int r, int g, int b) {
		s.setFillColor(new Color(r, g, b));
	}
	
	/**
	 * retrieves the x-coordinate of the shape
	 * @return the x-coordinate of the shape
	 */
	public double getX() {
		return s.getX();
	}
	
	/**
	 * retrieves the y-coordinate of the shape
	 * @return the y-coordinate of the shape
	 */
	public double getY() {
		return s.getY();
	}
}
