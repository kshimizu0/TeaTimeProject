package gameElements;

import java.util.ArrayList;

import processing.core.PImage;

/**
 * Represents a drink object in the game
 * 
 * @author Cindy Cheng
 * @version 5-25-26
 * 
 */
public class Drink {
	private int iceLevel, sugarLevel, numScoops, resetCount;
	private Tea tea;
	private ArrayList<Topping> toppings;
	private double teaLevel;
	
	private final static int DRINK_BASE_PRICE = 10;
	private final static int TOPPINGS_PRICE = 3;
	
	/**
	 * Creates a drink with the settings given in the parameter
	 * @param iceLevel the ice level of the drink
	 * @param sugarLevel the sugar level of the drink
	 * @param teaLevel the amount of tea in the drink
	 * @param toppingScoops the amount of scoops each topping needs
	 * @param tea the tea flavor of the drink
	 * @param toppings a list of the toppings in the drink
	 */
	public Drink(int iceLevel, int sugarLevel, int teaLevel, 
			int toppingScoops, Tea tea, ArrayList<Topping> toppings) {
		this.iceLevel = iceLevel;
		this.sugarLevel = sugarLevel;
		this.tea = tea;
		this.teaLevel = teaLevel;
		this.numScoops = toppingScoops;
		this.toppings = toppings;
	}
	
	/**
	 * Returns the ice level of the drink
	 * @return the ice level of the drink
	 */
	public int getIceLevel() {
		return iceLevel;
	}
	
	/**
	 * Sets the ice level to a new amount
	 * @param iceLevel the new ice level of the drink
	 */
	public void setIceLevel(int iceLevel) {
		this.iceLevel = iceLevel;
	}
	
	/**
	 * Returns the sugar level of the drink
	 * @return the sugar level of the drink
	 */
	public int getSugarLevel() {
		return sugarLevel;
	}
	
	/**
	 * Sets the sugar level to a new amount
	 * @param sugarLevel the new sugar level of the drink
	 */
	public void setSugarLevel(int sugarLevel) {
		this.sugarLevel = sugarLevel;
	}
	
	/**
	 * Calculates the price of the drink
	 * @return the price of the drink
	 */
	public int getPrice() {
		return (getNumToppings() * TOPPINGS_PRICE) + DRINK_BASE_PRICE;
	}
	
	/**
	 * Returns the Tea that the drink has
	 * @return the Tea that the drink has
	 */
	public Tea getTea() {
		return tea;
	}
	
	/**
	 * Sets the tea to a new tea
	 * @param tea the new tea the drink will have
	 */
	public void setTea(Tea tea) {
		this.tea = tea;
	}
	
	/**
	 * Returns the number of toppings the drink has
	 * @return the number of toppings the drink has
	 */
	public int getNumToppings() {
		return toppings.size();
	}
	
	/**
	 * Adds a new topping to the list of toppings if it does not already have that topping 
	 * @param topping the new topping to be added to the list
	 */
	public void addTopping(Topping topping) {
		for (Topping t : toppings) {
			if (t.equals(topping)) 
				return;
		}
		toppings.add(topping);
	}
	
	/**
	 * Returns a list of the toppings the drink has
	 * @return a list of the toppings the drink has
	 */
	public ArrayList<Topping> getToppings() {
		return toppings;
	}
	
	/**
	 * Sets the list of toppings to a new list
	 * @param toppings the new list of toppings the drink has
	 */
	public void setToppings(ArrayList<Topping> toppings) {
		this.toppings = toppings;
	}
	
	/**
	 * Sets the tea level to a new amount
	 * @param teaLevel the new tea level the drink will have
	 */
	public void setTeaLevel(double teaLevel) {
		this.teaLevel = teaLevel;
	}
	
	/**
	 * Returns the amount of tea the drink has
	 * @return the amount of tea the drink has
	 */
	public double getTeaLevel() {
		return teaLevel;
	}
	
	/**
	 * Sets the number of scoops for every topping to a new number
	 * @param numScoops the new number of scoops every topping needs
	 */
	public void setNumScoops(int numScoops) {
		this.numScoops = numScoops;
	}
	
	/**
	 * returns the number of scoops every topping needs
	 * @return the number of scoops every topping needs
	 */
	public int getNumScoops() {
		return numScoops;
	}
	
	/**
	 * returns the amount of times the drink was reset
	 * @return the amount of times the drink was reset
	 */
	public int getResetCount() {
		return resetCount;
	}
	
	/**
	 * sets the reset count to a new number
	 * @param resetCount the new resetCount;
	 */
	public void setResetCount(int resetCount) {
		this.resetCount = resetCount;
	}
	
	/**
	 * Checks if this Drink's contents are equal to another drink
	 * @param other the other Drink to be checked against
	 * @return true if the drink has identical contents to the other drink; false otherwise
	 */
	public boolean equals(Drink other) {
		if (iceLevel == other.getIceLevel() && 
				sugarLevel == other.getSugarLevel() &&
				tea.equals(other.getTea()) &&
				teaLevel == other.getTeaLevel() &&
				numScoops == other.getNumScoops()) {
			int count = 0;
			for (Topping t : other.getToppings()) {
				for (Topping t2 : toppings) {
					if (t.equals(t2)) {
						count++;
					}
				}
			}
			if (count == toppings.size()) 
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if this Drink is the favorite drink of the customer
	 * @param other the other Drink to be checked against
	 * @return true if the drink has the same toppings and tea flavor as the other drink; false otherwise
	 */
	public boolean checkFavoriteDrink(Drink other) {
		if (tea.equals(other.getTea())) {
			int count = 0;
			for (Topping t : other.getToppings()) {
				for (Topping t2 : toppings) {
					if (t.equals(t2)) {
						count++;
					}
				}
			}
			if (count == toppings.size()) 
				return true;
		}
		return false;
	}
	
	/**
	 * Returns the toppings of the drink as a string
	 * @return a string of the names of the toppings
	 */
	public String printToppings() {
		if (toppings.size() == 2) {
			return toppings.get(0).getName() + ", " + toppings.get(1).getName();
		} else {
			return toppings.get(0).getName();
		}
	}
	
	
	/**
	 * 
	 * Finds the correct tea image and toppings image based on drink
	 * @param teaImgs an ArrayList of the different images for each type of tea and topping
	 */
	public PImage[] getDrinkImage(ArrayList<PImage> teaImgs) {
		PImage[] drinkImage = new PImage[3];
		if (getTea().getFlavor().equals("passionFruit")) {
			drinkImage[0] = teaImgs.get(1);
			drinkImage[2] = teaImgs.get(10);
		}
		else if (getTea().getFlavor().equals("brownsugar")) {
			drinkImage[0] = teaImgs.get(0);
			drinkImage[2] = teaImgs.get(9);
		}
		else if (getTea().getFlavor().equals("strawberry")){
			drinkImage[0] = teaImgs.get(2);
			drinkImage[2] = teaImgs.get(11);
		}
		else {
			System.out.println("tea missing");
			System.out.println(getTea().getFlavor());
		}
		if (getToppings().size() == 2) {
			if ((getToppings().get(0).getName().equals("pearls") || getToppings().get(1).getName().equals("pearls"))
					&& (getToppings().get(0).getName().equals("popping") || getToppings().get(1).getName().equals("popping"))) {
				drinkImage[1] = teaImgs.get(6);
			} else if ((getToppings().get(0).getName().equals("lychee") || getToppings().get(1).getName().equals("lychee"))
					&& (getToppings().get(0).getName().equals("popping") || getToppings().get(1).getName().equals("popping"))) {
				drinkImage[1] = teaImgs.get(8);
			} else if ((getToppings().get(0).getName().equals("pearls") || getToppings().get(1).getName().equals("pearls"))
					&& (getToppings().get(0).getName().equals("lychee") || getToppings().get(1).getName().equals("lychee"))) {
				drinkImage[1] = teaImgs.get(7);
			} else {
				System.out.println("wrong toppings");
			}
		} else if (getToppings().size() == 1){
			if (getToppings().get(0).getName().equals("pearls"))
				drinkImage[1] = teaImgs.get(3);
			else if (getToppings().get(0).getName().equals("popping"))
				drinkImage[1] = teaImgs.get(4);
			else if (getToppings().get(0).getName().equals("lychee"))
				drinkImage[1] = teaImgs.get(5);
			else
				System.out.println("wrong topping");
			
		} else if (getToppings().size() == 3) {
			drinkImage[1] = teaImgs.get(12);
		}
		else {
			System.out.println("too little or too many toppings");
		}
		return drinkImage;
	}
	
}
