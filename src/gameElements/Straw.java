package gameElements;

import kshimizu.shapes.*;
import processing.core.PApplet;

/**
 * represents a straw object in game 
 * @author Cindy Cheng, Keira Shimizu
 * @version 5-25-26
 */
public class Straw {
	private boolean poked;
	private PhysicsStraw p;
	private double x;
	
	/**
	 * creates a straw object 
	 * @param c the color of the straw
	 */
	public Straw(boolean isStrawberry) {
		p = new PhysicsStraw(new Rectangle(600,10,30,150));
		if (isStrawberry)
			p.isStrawberry();
		p.setFillColor(230, 149, 173);
		x = 0;
	}
	
	/**
	 * draws the straw on the window
	 * 
	 * @param surface the PApplet used to draw with
	 */
	public void draw(PApplet surface) {
		if(!poked) {
			p.shift();
		}else {
			p.pokeAction();
		}
		p.draw(surface);
		
	}
	
	/**
	 * sets the state of poke to true
	 */
	public void poke() {
		poked = true;
		setX(p.getX());
	}
	
	/**
	 * retrieves the state of poke
	 * @return true if the straw is poked
	 */
	public boolean isPoked() {
		return poked;
	}
	
	public PhysicsStraw getPhysics() {
		return p;
	}
	
	private void setX(double xPos) {
		x = xPos;
	}
	
	public double getX() {
		return x;
	}

}
