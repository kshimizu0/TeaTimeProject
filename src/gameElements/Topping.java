package gameElements;

import java.awt.Color;

import kshimizu.shapes.*;

/**
 * Represents a topping in the game
 * 
 * @author Keira Shimizu
 * @version 5-25-26
 * 
 */
public class Topping {
	private PhysicsShape shape; 
	private String name;
	private int price;
	private Color color;
	private Color strokeColor;
	
	/**
	 * creates a new object of type Topping
	 * @param name the flavor of the topping
	 */
	public Topping(String name) {
		this.name = name;
		if (name.equals("pearls")) {
			shape = new PhysicsShape(new Circle());
			price = 50;
			color = new Color(67,50,34);
		}
		else if (name.equals("popping")) {
			shape = new PhysicsShape(new Circle());
			price = 75;
			color = new Color(255,200,233);
			
		} else if (name.equals("lychee")) {
			shape = new PhysicsShape(new Rectangle());
			price = 75;
			color = new Color(255,245,232);
		}
			
	}
	
	/**
	 * creates a new object of type Topping
	 * @param name the flavor of the topping
	 * @param s the shape of the topping, round or rectangular
	 */
	public Topping(String name, Shape s) {
		this.name = name;
		if (name.equals("pearls")) {
			shape = new PhysicsShape(s);
			color = new Color(67,50,34);
			strokeColor = new Color(0,0,0);
			price = 5;
		}
		else if (name.equals("popping")) {
			shape = new PhysicsShape(s);
			color = new Color(255,200,233);
			strokeColor = new Color(180,101,147);
			price = 7;
			
		} else if (name.equals("lychee")) {
			shape = new PhysicsShape(s);
			color = new Color(255,245,232);
			strokeColor = new Color(236,230,189);
			price = 7;
		}
		s.setFillColor(color);
		s.setStrokeColor(strokeColor);
	}
	
	/**
	 * retrieves the flavor of the topping
	 * @return the name of the topping
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * retrieves the physics shape of the topping
	 * @return the physics shape of the topping
	 */
	public PhysicsShape getPhysicsShape() {
		return shape;
	}
	
	/**
	 * retrieves the price of the topping
	 * @return the price of the topping
	 */
	public int getToppingPrice() {
		return price;
	}
	
	/**
	 * checks if this topping is equal to another
	 * @param other
	 * @return true if the topping is the same
	 */
	public boolean equals(Topping other) {
		if(other.name.equals(this.name))
			return true;
		return false;
	}
}
