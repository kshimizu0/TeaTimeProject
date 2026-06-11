package gameElements;

import java.awt.Color;

/**
 * Represents a tea flavor in the game
 * 
 * @author Cindy Cheng
 * @version 5-25-26
 * 
 */
public class Tea {
	
	private Color color;
	private String flavor;
	
	/**
	 * Creates a new Tea object and sets the flavor
	 * @param flavor the flavor of the tea
	 */
	public Tea(String flavor) {
		this.flavor = flavor;
		if (flavor.equals("passionFruit"))
			color = new Color(255, 247, 189);
		else if (flavor.equals("brownsugar"))
			color = new Color(216, 181, 128);
		else if (flavor.equals("strawberry"))
			color = new Color(255, 218, 232);
		else
			color = new Color(0);
	}
	
	/**
	 * Creates a new Tea object with a flavor and color
	 * @param flavor the flavor of the tea
	 * @param color the color of the tea
	 */
	public Tea(String flavor, Color color) {
		this.flavor = flavor;
		this.color = color;
	}
	
	/**
	 * Returns the flavor of the tea
	 * @return the flavor of the tea
	 */
	public String getFlavor() {
		return flavor;
	}
	
	/**
	 * Sets the flavor of the tea to a new flavor
	 * @param flavor the new flavor of the tea
	 */
	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}
	
	/**
	 * Returns the color of this tea
	 * @param flavor the new flavor of the tea
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Sets the color of the tea to a new color in terms of rgb
	 * @param r the red value of the new color
	 * @param g the green value of the new color
	 * @param b the blue value of the new color
	 */
	public void setColor(int r, int g, int b) {
		color = new Color(r, g, b);
	}
	
	/**
	 * Checks to see if this tea is equal to another tea
	 * @param other the other tea to be checked against
	 * @return true if this tea is equal to the other; false otherwise
	 */
	public boolean equals(Tea other) {
		if (flavor.equals(other.getFlavor())) 
			return true;
		return false;
	}
	

}
