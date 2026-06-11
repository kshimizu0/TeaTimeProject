package gameElements;
import java.awt.Color;

import kshimizu.shapes.*;
import processing.core.PApplet;

/**
 * Represents a shape with physics
 * 
 * @author Cindy Cheng, Keira Shimizu
 * @version 5/25/26
 */
public class PhysicsShape {
	
	private Shape s;
	private double vy, vx;
	private boolean touch;
	
	/**
	 * creates an object of type PhysicsShape
	 * @param s the shape that will be able to create a PhysicsShape
	 */
	public PhysicsShape(Shape s) {
		this.s = s;
		this.vx = 5;
		this.vy = 0.01;
	}
	
	/**
	 * checks if a rectangle is touching another
	 * @param other the rectangle being checked against
	 * @return true if the rectangles are touching
	 */
	public boolean isRectangleTouching(Shape other) {

		Rectangle rect = (Rectangle)s;
		Rectangle rect1 = (Rectangle)other;
		
		double x1min = rect.getX();
		double x1max = rect.getX()+rect.getWidth();
		double y1min = rect.getY();
		double y1max = rect.getY()+rect.getHeight();
		
		double x2min = rect1.getX();
		double x2max = rect1.getX()+rect1.getWidth();
		double y2min = rect1.getY();
		double y2max = rect1.getY()+rect1.getHeight();
		
		return (y1min <= y2max && y1max >= y2min && x1min <= x2max && x1max >= x2min);
	}
	
	public void setTouch(boolean state) {
		touch = state;
	}
	
	public boolean getTouch() {
		return touch;
	}
	
	/**
	 * Causes objects to bounce off each other when they hit
	 * @param other the PhysicShape that is being checked against
	 */
	public void act(PhysicsShape other) {
		Rectangle rect1 = (Rectangle) s;
		Rectangle rect2 = (Rectangle) other.getShape();

		if(isRectangleTouching(rect2)) {
			if(rect1.getY() + rect1.getHeight() > rect2.getY() && rect1.getY() < rect2.getY()) {
				if(other.vy == 0 || other.touch) {
					s.translate(0, -(rect1.getY() + rect1.getHeight() - rect2.getY()));
					vy = 0;
					touch = true;	
				}
				
			}else if(rect2.getY() + rect2.getHeight() > rect1.getY() && rect2.getY() < rect1.getY()) {
				if(vy == 0  || touch){
					other.getShape().translate(0, -(rect2.getY() + rect2.getHeight() - rect1.getY()));
					other.vy = 0;
					other.touch = true;
				}
			}
			
		}else {
			touch = false;
		}
		
	}
	
	/**
	 * acts gravity on the shape
	 */
	public void fall() {
		if(!touch && vy != 0) {
			s.translate(0, vy);
			accelerate(0, 1.5);
		}else {
			vy = 0;
		}
		
	}
	
	/**
	 * Draws the shape
	 * @param surface the drawing utility to be used 
	 */
	public void draw(PApplet surface) {
		s.draw(surface);
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
	 * checks if the coordinates of a point is inside
	 * @param x the x-coordinate that is being checked
	 * @param y the y-coordinate that is being checked
	 * @return true if the point is inside
	 */
	public boolean isPointInside(double x, double y) {
		return s.isPointInside(x, y);
	}
	
	/**
	 * speeds up the movement of the object
	 * @param ax the acceleration of the x component
	 * @param ay the acceleration of the y component
	 */
	public void accelerate(double ax, double ay) {
		vx+=ax;
		vy+=ay;
		
		if(vy > 15) {
			vy = 15;
		}
	}
	
	/**
	 * changes the velocity of the x and y components
	 * @param vx the new x veloicty
	 * @param vy the new y velocity
	 */
	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}
	
	/**
	 * changes the position of the object
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 */
	public void setPosition(double x, double y) {
		s.translate(x-s.getX(), y-s.getY());
	}

	/**
	 * retrieve the boundaries of a circle as a rectangle
	 * @return a PhysicsShape object that acts as the boundaries of a circle
	 */
	public PhysicsShape getBounds() {
		Circle c = (Circle)s;
		double radius = c.getDiameter()/2;
		Rectangle boundsOfCircle = new Rectangle(s.getX()-radius,s.getY()-radius,2*radius,2*radius); 
		PhysicsShape bounds = new PhysicsShape(boundsOfCircle);
		return bounds;
	}
	
	/**
	 * retrieves the boundaries of a square as a circle
	 * @return a PhysicsShape object that acts as the boundaries of a rectangle 
	 */
	public PhysicsShape getCircle() {
		double radius = s.getPerimeter()/8;
		Circle circleFromSquare = new Circle(s.getX()+radius,s.getY()+radius, radius);
		PhysicsShape shape = new PhysicsShape(circleFromSquare);
		return shape;
	}
	
	/**
	 * retrieve the x component of the velocity
	 * @return the x component of the velocity
	 */
	public double getVx() {
		return vx;
	}
	
	/**
	 * retrieve the y component of the velocity
	 * @return the y component of the velocity
	 */
	public double getVy() {
		return vy;
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
	
	/**
	 * retrieves the shape of the physicsShape
	 * @return the shape the object that has motion is
	 */
	public Shape getShape() {
		return s;
	}
	
	/**
	 * Makes the physicsShape stay inside the cup
	 */
	public void stayInCup() {
		Rectangle rect = (Rectangle)s;
		if(s.getY() + rect.getHeight() >= 420) {
			s.translate(0, 420 - (rect.getY() + rect.getHeight()));
			vy = 0;
			touch = true;
		}
	}
	
	/**
	 * Sets the shape to a new shape
	 * @param s the new shape it is set to
	 */
	public void setShape(Shape s) {
		this.s = s;
	}
}
