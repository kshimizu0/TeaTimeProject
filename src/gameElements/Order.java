package gameElements;

import java.util.ArrayList;

/**
 * Generates a random drink for every customer 
 * 
 * @author Varshini Raja
 * @version 5-25-26
 */

public class Order {
	private Drink order;
	private Tea tea;
	
	private int toppingChecker;
	
	private ArrayList<String> teaFlavors;
	private ArrayList<Topping> numToppings;
	
	/**
	 * Constructs a new randomly generated order
	 * Each attribute is a random value
	 */
	
	public Order() {
		teaFlavors = new ArrayList<String>();
		numToppings = new ArrayList<Topping>();
		
		tea = new Tea(randomTeaFlavor());
		int numScoops = randomNumScoops();
		
		toppingChecker = numToppings();
//		while (toppingChecker > 0) {
//			numToppings.add(randomTopping());
//		}
		if (toppingChecker > 1) {
			numScoops++;
		}
		
		while (toppingChecker > 0) {
			Topping t = randomTopping();
			boolean newTopping = false;
			while (!newTopping) {
				int count = 0;
				for (Topping e : numToppings) {
					//System.out.println(e.getName() + " " + t.getName());
					if (e.getName().equals(t.getName())) {
						count++;
					}
				}
				if (count == 0) {
					newTopping = true;
				} else {
					t = randomTopping();
				}
			}
			numToppings.add(t);
			toppingChecker--;
		}
		
		
		order = new Drink(randomIceLevel(), randomSugarLevel(), randomTeaLevel(), numScoops, tea, numToppings);
		//System.out.println(order.printToppings());
	}
	
	/**
	 * Generates a random ice level between 10% and 50%, by increments of 10
	 * 
	 * @return the ice level
	 */
	
	public int randomIceLevel() {
		int icelevel;
		icelevel = ((int)(Math.random() * 5) + 1) * 10;
		
		return icelevel;
	}
	
	/**
	 * Generates a random ice level between 0% and 100%, by increments of 25
	 * 
	 * @return the sugar level
	 */
	public int randomSugarLevel() {
		int sugarLevel;
		sugarLevel = (int)(Math.random() * 5) * 25;
		
		return sugarLevel;
	}
	
	/**
	 * Generates a random tea level between 150 and 250
	 * 
	 * @return the tea level
	 */
	public int randomTeaLevel() {
		int teaLevel;
		int min = 150;
		int max = 240;
		teaLevel = (int)(Math.random() * ((max - min) + 1)) + min;
		
		return teaLevel;
	}

	/**
	 * Generates a random integer of topping scoops between 1 and 3
	 * 
	 * @returns the number of scoops
	 */
	public int randomNumScoops() {
		int numScoops;
		int minScoops = 1;
		int maxScoops = 2;
		numScoops = (int)(Math.random() * ((maxScoops - minScoops) + 1)) + minScoops;
		
		return numScoops;
	}
	
	/**
	 * Randomly selects a tea flavor from the list of available flavors
	 * 
	 * @return selected tea flavor
	 */
	public String randomTeaFlavor() {
		teaFlavors.add("passionFruit");
		teaFlavors.add("brownsugar");
		teaFlavors.add("strawberry");
		
		int drinkIndex;
		drinkIndex = (int)(Math.random() * ((0 + 3) * 1)) + 0;
		
		return teaFlavors.get(drinkIndex);	
		
	}
	
	/**
	 * Randomly selects a topping from the list of available toppings
	 * 
	 * @return selected topping
	 */
	public Topping randomTopping() {
		Topping pearls = new Topping("pearls");
		Topping popping = new Topping("popping");
		Topping lychee = new Topping("lychee");
		
		Topping[] toppings = {pearls, popping, lychee};

		int randomIndex = (int)(Math.random() * toppings.length);

		Topping randomTopping = toppings[randomIndex];
		
		return randomTopping;
		
	}
	
	/**
	 * Determines the number of toppings the order will have, either 1 or 2
	 * 
	 * @return the number of toppings
	 */
	public int numToppings() {
		int numToppings;
		numToppings = (int)(Math.random() * 2) + 1;
		
		return numToppings;
	}

	/**
	 * Creates a Drink object based on the randomly generated order details
	 * 
	 * @return completed drink for the order
	 */
	public Drink getDrink() {	
		return order;
	}
	

}
