package gameElements;

import java.util.ArrayList;

/**
 * Represents a Drink that the player makes in the game
 * 
 * @author Cindy Cheng
 * @version 5-25-26
 * 
 */

public class Concoction extends Drink{
	private Drink correctDrink;
	private String drinkState;
	private double strawPos;
	
	/**
	 * Creates an empty drink for the player to start with
	 * @param correctDrink the drink the customer has ordered that the player will try to match
	 */
	public Concoction(Drink correctDrink) {
		super(0, 0, 0, 0, null, new ArrayList<Topping>());
		drinkState = "empty";
		this.correctDrink = correctDrink;
	}
	
	/*
	 * enter the measured values
	 */
	/**
	 * Calculates how high the tea level is based on how long the player presses the tea button
	 * @param startTime the instant the player starts clicking the button
	 * @param endTime the instant the player stops clicking the button
	 * @return the height of the tea in the cup
	 */
	public double fillHeight(double time) {
		int cupWidthInPixels = 150;
		int waterStreamCrossSection = 20; //in pixels, the width of waterStream
		double totalTime = time;
		double volume = (totalTime * waterStreamCrossSection) / 2;
		return volume / cupWidthInPixels;
	}
	
	/**
	 * Returns the current state of the drink, which could be "empty", "building", or "done"
	 * @return the current drink state as a string
	 */
	public String getDrinkState() {
		return drinkState;
	}
	
	/**
	 * Sets the state of the drink to a new state
	 * @param drinkState the new state of the drink
	 * @pre new drinkState should be "empty", "building", or "done"
	 */
	public void setDrinkState(String drinkState) {
		this.drinkState = drinkState;
	}
	
	/**
	 * Resets all values of the drink
	 */
	public void reset() {
		drinkState = "empty";
		setIceLevel(0);
		setSugarLevel(0);
		setTea(null);
		setTeaLevel(0);
		setNumScoops(0);
		setToppings(new ArrayList<Topping>());
		setResetCount(0);
		
	}
	
	/**
	 * Finishes the drink and sets the drink state to "done"
	 */
	public void finishDrink() {
		drinkState = "done";
	}
	
	/**
	 * adds the flavor of topping to the drink the user made
	 */
	public void setToppings(ArrayList<Topping> toppings) {
		for(Topping e : toppings) {
			addTopping(e);
		}
	}
	
	/*
	 * things to do:
	 * fix the comparing teaLevel so that it gives
	 * points based on how close the tea height is to
	 * correct one
	 */
	/**
	 * Calculates the score of the player-made drink based on how accurate it is to the correct drink
	 * @return a percent out of 100 that reflects the player's drink
	 */
	public int score() {
		double score = 0, toppingScore = 0;
		double total = correctDrink.getToppings().size() + 10;
		int resets = getResetCount();
//		System.out.println("drink toppings: " + printToppings());
//		System.out.println("order toppings: " + correctDrink.printToppings());
		for (Topping t : correctDrink.getToppings()) {
			for (Topping t2 : getToppings()) {
				if (t.equals(t2)) {
					score++;
					toppingScore++;
				}
			}
		}
		System.out.println("drink toppings: " + printToppings());
		System.out.println("order toppings: " + correctDrink.printToppings());
		System.out.println("toppingScore: " + toppingScore);
		if (correctDrink.getToppings().size() != toppingScore || getToppings().size() != toppingScore) {
			score -= 3;
		}
		if (correctDrink.getToppings().size() != getToppings().size()) {
			score -= Math.abs(correctDrink.getToppings().size() - getToppings().size());
		}
		if (getIceLevel() == correctDrink.getIceLevel())
			score++;
//		System.out.println("drink ice: " + getIceLevel());
//		System.out.println("order ice: " + correctDrink.getIceLevel());
		if (getSugarLevel() == correctDrink.getSugarLevel()) 
			score++;
//		System.out.println("drink sugar: " + getSugarLevel());
//		System.out.println("order sugar: " + correctDrink.getSugarLevel());
		if (getTea().equals(correctDrink.getTea()))
			score++;
		else
			score -= 3;
//		System.out.println("drink tea: " + getTea().getFlavor());
//		System.out.println("order tea: " + correctDrink.getTea().getFlavor());
//		System.out.println("drink numScoops: " + getNumScoops());
//		System.out.println("order numScoops: " + correctDrink.getNumScoops());
		if (getNumScoops() == correctDrink.getNumScoops()) 
			score++;
		if (Math.abs(getTeaLevel() - correctDrink.getTeaLevel()) < 3)
			score += 3;
		else if (Math.abs(getTeaLevel() - correctDrink.getTeaLevel()) < 5)
			score += 2;
		else if (Math.abs(getTeaLevel() - correctDrink.getTeaLevel()) < 10) 
			score += 1;
		else if (getTeaLevel() < 30)
			score -= 3;
//		System.out.println("drink tea level: " + getTeaLevel());
//		System.out.println("order tea level: " + correctDrink.getTeaLevel());
		if (Math.abs((strawPos + 15) - 600) <= 3)
			score += 3;
		else if ((Math.abs(strawPos + 15) - 600) <= 5)
			score += 2;
		else if (strawPos < 555 || strawPos > 615) 
			score -= 1;
		else if (strawPos < 535 || strawPos > 635) 
			score -= 3;
//		else if ((Math.abs(strawPos + 15) - 600) <= 5)
//			score += 2;
//		else if (Math.abs((strawPos + 15) - 600) < 10) 
//			score += 1;
		while (resets > 0) {
			//System.out.println("reset");
			score -= 2;
			resets--;
			//setResetCount(resets);
		}
		System.out.println("strawPos: " + strawPos + 15);
		System.out.println("score: " + score);
		System.out.println("total: " + total);
		System.out.println("percent: " + (int) ((score / total) * 100));
		
		if ((int) ((score / total) * 100) <= 0) {
			return 0;
		}
		return (int) ((score / total) * 100);
	}
	
	/**
	 * returns each star of the five this drink got based on the score
	 * @return an Array of strings size 5 that holds each of the stars
	 */
	public String[] getStars() {
		String[] stars = new String[5];
		double percent = score();
		int index = 0;
		while (percent > 19) {
			percent -= 20;
			stars[index] = "full star";
			index++;
		}
		if (percent >= 10) {
			stars[index] = "half star";
			index++;
		}
		while (index < 5) {
			stars[index] = "no star";
			index++;
		}
		return stars;
	}
	
	/**
	 * sets the x position of the straw 
	 * @strawPos the x position of the straw
	 */
	public void setStrawPos(double strawPos) {
		this.strawPos = strawPos;
	}
	
	
}
