package gameElements;

//import java.awt.Color;
import java.util.ArrayList;

import kshimizu.shapes.Rectangle;
import kshimizu.shapes.Shape;
//import processing.core.PApplet;

/**
 * Represents an ice cube with physics
 * 
 * @author Cindy Cheng
 * @version 5/25/26
 */
public class PhysicsIce extends PhysicsShape{
	
	private double iceVy;
	private boolean isFloating, isStacked;
	private int iceCheckCount;
	
	/**
	 * Creates a new ice cube with physics
	 * @param s the shape of the ice cube
	 */
	public PhysicsIce(Shape s) {
		super(s);
		isFloating = false;
		iceCheckCount = 0;
		iceVy = 0.01;
	}
	
	/**
	 * Causes ice to behave logically
	 * stacks on top of each other until two ice cubes, accelerates and falls
	 * @param others the other ice cubes that it is being checked against
	 */
	public void actIce(ArrayList<PhysicsIce> others, ArrayList<PhysicsShape> physics) {

		getShape().translate(0, iceVy);
		
		if (!isFloating) {
			fall();
			for (PhysicsShape ice : others) {
				if (!getShape().equals(ice.getShape())) {

					Rectangle rect = (Rectangle)getShape();
					Rectangle rect1 = (Rectangle)ice.getShape();
				
					double y1min = rect.getY();
					double y1max = rect.getY()+rect.getHeight();
					
					double y2min = rect1.getY();
					double y2max = rect1.getY()+rect1.getHeight();
					
					boolean ok = (y1min <= y2max && y1max >= y2min);
					if(ok) {
						if (iceCheckCount < 1) {
							getShape().translate(0, -iceVy);
						}
						iceVy = 0;
						iceCheckCount++;
					}
				}
			}
			
			for(PhysicsShape e : physics) {
				if(getShape().isTouching(e.getShape())) {
					act(e);
				}
			}
		}

		}
	
	/**
	 * makes the ice float
	 * @param go whether or not the ice is currently floating up
	 */
	public void floatIce(boolean go, double height) {
		if (go) {
//			isFloating = true;
//			if (other.getX() == getX()) {
//				//System.out.println("stacked");
//				isStacked = true;
//				if((420 - height) < (other.getY())) {
//					//System.out.println("after getting through other ice");
//					if (other.getIsStacked()) {
//						if ((420 - height) <  (other.getY() - 35)) {
//							iceVy = -2;
//							getShape().translate(0, iceVy);
//						} else {
//							iceVy = 0;
//							getShape().translate(0, iceVy);
//						}
//					} else {
//						iceVy = -2;
//						getShape().translate(0, iceVy);
//					}
//				} else {
//					iceVy = 0;
//					getShape().translate(0, iceVy);
//				}
//			} else {
//				if (!isStacked && (420 - height) < getY()) {
//					iceVy = -2;
//					getShape().translate(0, iceVy);
//				}
//			}
			//iceVy = -1.5;
//			iceVy = -3;
			//getShape().translate(0, iceVy);
			getShape().setPosition(getX(), 420 - height);
		} else {
			iceVy = 0;
		}
	}
	
	/**
	 * hides the ice graphics
	 */
	public void resetGraphics() {
		getShape().setX(-100);
	}
	
	/**
	 * retrieves the y velocity of the ice
	 * @return the value of the y velocity of the ice
	 */
	public double getIceVy() {
		return iceVy;
	}
	
	/**
	 * speeds up the movement of the object
	 * @param ax the acceleration of the x component
	 * @param ay the acceleration of the y component
	 */
	public void accelerateIce(double ay) {
		iceVy+=ay;
	}
	
	public boolean getIsStacked() {
		return isStacked;
	}
}
