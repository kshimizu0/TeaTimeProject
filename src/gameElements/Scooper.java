package gameElements;
import java.util.ArrayList;

import jay.jaysound.JayLayer;
import kshimizu.shapes.*;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a Scooper object
 * @author Keira Shimizu
 * @version 5-25-26
 */
public class Scooper {
	
	private Topping toppingsHeld;	
	private PImage image;
	private PApplet surface;

	private int x, y, width, height;
	private int step, numScoops;
	private int count;
	
	private boolean dropping;
	
	private ArrayList<Boolean> settled;
	
	private ArrayList<PhysicsShape> physics;
	private ArrayList<Topping> toppingArray;
	private ArrayList<PImage> scooperImages;
	private int[] toppingScoopCounter;
	
	private JayLayer effectsPlayer;
	
	/**
	 * Creates a Scooper object
	 * @param x the x-coordinate of the scooper
	 * @param y the y-coordinate of the scooper
	 */
	public Scooper(PApplet surface) {
		
		this.surface = surface;
		toppingArray = new ArrayList<Topping>();
		physics = new ArrayList<PhysicsShape>();
		scooperImages = new ArrayList<PImage>();
		settled = new ArrayList<Boolean>();
		
		toppingScoopCounter = new int[3]; // 0 is BOBA 1 is LYCHEE 2 is POPPING

		step = 1;
		x = 1050;
		y = 420;
		width = 170; 
		height = 170;
		numScoops = 0;
		
	}
	
	/**
	 * initializes the images
	 */
	public void setup() {
		scooperImages.add(surface.loadImage("scooperImgs/scooperSet.png"));
		scooperImages.add(surface.loadImage("scooperImgs/scooperSelected.png"));
		scooperImages.add(surface.loadImage("scooperImgs/scooperIn.png"));
		scooperImages.add(surface.loadImage("scooperImgs/scooperBoba.png"));
		scooperImages.add(surface.loadImage("scooperImgs/scooperLychee.png"));
		scooperImages.add(surface.loadImage("scooperImgs/scooperPopping.png"));
		
		image = scooperImages.get(0);
		
		effectsPlayer = new JayLayer("audio/","audio/",false);
		effectsPlayer.addSoundEffect("ploppingSound.mp3");
		
	}
	
	/**
	 * draws the scooper object and toppings on the window
	 */
	public void draw() {
		surface.image(image, x, y, width, height);
		
		if(step == 4) {	
			dropping = true;
			if(toppingsHeld.getName().equals("pearls")) {
				image = scooperImages.get(3);
			}else if(toppingsHeld.getName().equals("lychee")){
				image = scooperImages.get(4);
			}else {
				image = scooperImages.get(5);
			}		
			x = 760;
			y = -50;
			width = 330;
			height = 300;
			
			//start not touching
			for(int i = numScoops*6; i < (numScoops+1)*6; i++) {
				if(i < physics.size())
					physics.get(i).setTouch(false);
			}
			
			//falling action, staggered
			if(numScoops*6 < physics.size()) {
				physics.get(numScoops*6).fall();
			}
			
			for(int a = numScoops*6 +1; a < (numScoops+1) * 6; a++) {
				if(a < physics.size() && physics.get(a-1).getY() > 120) {
					physics.get(a).fall();
				}
			}
			
			//stay in bounds
			for(int i = numScoops*6; i < (numScoops+1)*6; i++) {
				physics.get(i).stayInCup();
			}
			
			//physics work
			for(int i = numScoops*6; i < (numScoops+1) * 6-1; i++) {
				for(int j = i+1; j < physics.size(); j++) {		
					physics.get(i).act(physics.get(j));
				}
			}
				
			//checks if topping is a circle to match coordinates
			for(int i = numScoops*6; i < (numScoops+1) * 6; i++) {
				if(i < toppingArray.size()) {
					if(toppingArray.get(i).getPhysicsShape().getShape() instanceof Circle){
						Circle c = (Circle)toppingArray.get(i).getPhysicsShape().getShape();
						toppingArray.get(i).getPhysicsShape().setPosition(physics.get(i).getX() + c.getDiameter()/2, physics.get(i).getY() + c.getDiameter()/2);
					}
				}
			}
			
			//draw original toppings
			count = 0;
			count = 0;
			for(int i = numScoops*6; i < (numScoops+1) *6; i++) {
				if(physics.get(i).getVy() == 0) {
					count++;
				}
			}
			
			//plays the sound
			for(int i = numScoops*6; i < (numScoops + 1)*6; i++) {
				if(physics.get(i).getVy() == 0 && !settled.get(i)) {
					playSoundEffect();
					settled.set(i, true);
				}
			}
			
			if(count == 6) {
				reset();
				numScoops++;
			}
			count = 0;
		}else {
			dropping = false;
		}
		for(Topping e : toppingArray) {
			e.getPhysicsShape().draw(surface);
			surface.image(image, x, y, width, height);
			
		}
	}
	
	/**
	 * changes the x coordinate of the physics shapes to be where the mouse is clicked
	 */
	public void changeXCoordinate() {
		
		for(int i = numScoops * 6; i < (numScoops+1)*6; i++){
			double xPos;
			if(toppingArray.get(i).getPhysicsShape().getShape() instanceof Circle) {
				xPos = 820 + Math.random()*100;
				if(i >= 1) {
					double temp = physics.get(i-1).getX();
					while(Math.abs(temp - xPos) <= 15) {
						xPos = 820 + Math.random()*100;
					}
				}
			}else {
				xPos = 825 + Math.random()*85;
				if(i >= 1) {
					double temp = physics.get(i-1).getX();
					while(Math.abs(temp - xPos) <= 20) {
						xPos = 825 + Math.random()*85;
					}
				}
			}
			physics.get(i).setPosition(xPos, 100);
		}
	}
	
	/**
	 * plays the sound effect
	 */
	public void playSoundEffect(){
		effectsPlayer.playSoundEffect(0);
	}
	
	/**
	 * retrieves the current topping held
	 * @return the name of the topping held
	 */
	public String getToppingHeld() {
		return toppingsHeld.getName();
	}
	
	/**
	 * retrieves the current step the scooper is at
	 * @return the step the scooper's at
	 */
	public int getStep() {
		return step;
	}
	
	/**
	 * retrieves the amount of scoops the user made
	 * @return the number of scoops the user made
	 */
	public int getNumScoops() {
		return numScoops;
	}
	
	/**
	 * sets the step to the next one
	 */
	public void setStep(int level) {
		step = level;
	}
	
	/**
	 * resets the scooper to its original place, on the counter
	 */
	private void reset() {
		image = scooperImages.get(1);
		x = 1050;
		y = 420;
		width = 170; 
		height = 170;
		step = 2;
	}
	
	/**
	 * the entire drink is discarded, so it's empty
	 */
	public void discard() {
		image = scooperImages.get(0);
		x = 1050;
		y = 420;
		width = 170; 
		height = 170;
		step = 1;
	}
	
	/**
	 * resets the number of scoops
	 */
	public void resetDrink() {
		numScoops = 0;
		physics.clear();
		toppingArray.clear();
		settled.clear();
		discard();
	}
	
	/**
	 * grabs the scooper from the counter, shows that it is active and can be used
	 */
	public void grab() {
		if(step == 1) {
			image =  scooperImages.get(1); 
			step = 2;
		}
	}
	
	/**
	 * chooses an object to grab
	 * @param topping the topping that is being scooped
	 */
	public void scoop(Topping topping) { 
		if(step == 2) {
			width = 170;
			height = 170;
			image = scooperImages.get(2);
			y = 400;
			String name = topping.getName();
			if(name.equals("pearls")) {
				toppingsHeld = new Topping("pearls");
				for(int i = 0; i < 6; i++){
					Circle c = new Circle(-100, 100, 30);
					toppingArray.add(new Topping("pearls", c));
					toppingScoopCounter[0]++;
				}
				x = 615;
			}
			else if(name.equals("lychee")) {
				toppingsHeld = new Topping("lychee");
				for(int i = 0; i < 6; i++) {
					Rectangle r = new Rectangle(-100, 100, 40, 15);
					toppingArray.add(new Topping("lychee", r));
					toppingScoopCounter[1]++;
				}
				x = 765;
			}
			else {
				toppingsHeld = new Topping("popping");
				for(int i = 0; i < 6; i++) {
					Circle c = new Circle(-100, 100, 30);
					toppingArray.add(new Topping("popping", c));
					toppingScoopCounter[2]++;
				}
				x = 915;

			}

			if(toppingsHeld!= null && (toppingsHeld.getName().equals("pearls") || toppingsHeld.getName().equals("popping"))) {
				for(int i = numScoops*6; i < (numScoops+1)*6; i++) {
					physics.add(toppingArray.get(i).getPhysicsShape().getBounds());
					settled.add(false);
				}
			}else {
				for(int i = numScoops*6; i < (numScoops+1)*6; i++) {
					physics.add(toppingArray.get(i).getPhysicsShape());
					settled.add(false);
				}
			}
			step = 3;
		}
	}
	
	/**
	 * retrieves the data collection of scoops per topping
	 * @return the array of scoop count for each topping
	 */
	public int[] getToppingScoopCount() {
		return toppingScoopCounter;
	}
	
	
	/**
	 * sets the topping the user is scooping
	 * @param topping the topping the user is scooping
	 */
	public void setTopping(Topping topping) {
		toppingsHeld = topping;
	}
	
	/**
	 * retrieves the objects that has physics
	 * @return the arraylist of physics shapes
	 */
	public ArrayList<PhysicsShape> getPhysicsShapes(){
		return physics;
	}
	
	/**
	 * retrieves the toppings
	 * @return the arraylist of toppings
	 */
	public ArrayList<Topping> getToppingArray(){
		return toppingArray;
	}
	
	/**
	 * retrieves the state of the topping falling
	 * @return true if the toppings are falling
	 */
	public boolean isDropping() {
		return dropping;
	}

}
