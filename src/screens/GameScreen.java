package screens;


import java.awt.Color;
import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.*;
import kshimizu.shapes.*;
import processing.core.PFont;
import processing.core.PImage;
import jay.jaysound.JayLayer;

/**
 * Represents the main gameplay screen where the player creates drinks, interacts with tools, and serves customers
 * 
 * This screen handles the game logic like drink creation, timing, and customer interactions
 * 
 * @author Cindy Cheng, Keira Shimizu, Varshini Raja
 * @version 6-11-26
 */
public class GameScreen extends Screen {
	
	private ArrayList<Button> buttons;
	private ArrayList<PhysicsShape> bounds, physics;
	private ArrayList<PhysicsIce> iceCubes;
	private Customer[] customers;
	
	private ArrayList<PImage> teaImgs, sugarPetals;
	private PImage background, emptyCup, teaLine, coins, 
						coinBar, receipt, dispenser, counter, sugarClicked, bookmark, book;

	
	private Customer customer;
	private Order order;
	private Scooper scooper;
	private Concoction userDrink, finishedDrink;
	private PhysicsTea teaFall;
	private String customerSaying;
	
	private PFont milkChoco;
	
	private int level, phase, resetCount, numCoins, count, iceIndex;
	
	private long startTime, levelTime;
	private boolean teaClicked, iceFalling, playedIceSound, drawText, drawBook;
	
	private static final int STARTUP = 1, TIMER_GOING = 2, RESULTS = 3;

	private String[] soundEffects;
	private String[] songs;
	private JayLayer effectsPlayer;
	private JayLayer songPlayer;
	
	private int songCount;
	private int playlistCount;
	
	private int[] iceXCount;
	private boolean[] iceXMax;
	
	/**
	 * Constructs the GameScreen with a specified drawing surface and initializes the fields
	 * 
	 * @param surface the main drawing surface
	 */
	public GameScreen(DrawingSurface surface) {
		super(1200, 600, surface);
	
		teaImgs = new ArrayList<PImage>();
		sugarPetals = new ArrayList<PImage>();
		buttons = new ArrayList<Button>();
		scooper = new Scooper(surface);
		bounds = scooper.getPhysicsShapes();
		iceCubes =  new ArrayList<PhysicsIce>();
		level = 0;
		teaFall = new PhysicsTea(null);
		teaClicked = false;
		physics = new ArrayList<PhysicsShape>();
		customerSaying = "";
		sugarClicked = new PImage();
		
		customers = new Customer[9];
				
		//ice level buttons (0-4)
		buttons.add(new Button(639, 139, 55, 48, ""));
		buttons.add(new Button(639, 188, 55, 53.5, ""));
		buttons.add(new Button(639, 242, 55, 53, ""));
		buttons.add(new Button(639, 296, 55, 57, ""));
		buttons.add(new Button(639, 353, 55, 53, ""));
		
		//sugar level buttons (5-9)
		buttons.add(new Button(1120, 205, 25, "0"));
		buttons.add(new Button(1160, 240, 25, "25"));
		buttons.add(new Button(1145, 290, 25, "50"));
		buttons.add(new Button(1095, 290, 25, "75"));
		buttons.add(new Button(1080, 240, 25, "100"));
		
		//done/delete buttons (10-11)
		buttons.add(new Button(1100, 400, 35, "done"));
		buttons.add(new Button(1185, 400, 35, "delete"));
		
		//tea buttons (12-14)
		buttons.add(new Button(750, 15, 60, 60, ""));
		buttons.add(new Button(850, 15, 60, 60, ""));
		buttons.add(new Button(950, 15, 60, 60, ""));
		
		//topping buttons (15-17)
		buttons.add(new Button(630, 470, 110, 110, "pearls"));
		buttons.add(new Button(790, 470, 110, 110, "lychee"));
		buttons.add(new Button(950, 470, 110, 110, "popping"));
		
		//scooper button (18)
		buttons.add(new Button(1123, 518, 17, 54, ""));
		
		//drink button (19) (area where the user clicks to dump toppings if scooper is held
		buttons.add(new Button(780, 130, 200, 130, ""));
		
		//customer button (20) (a button in the area of the customer; user clicks it to give the drink to the customer)
		buttons.add(new Button(200, 250, 200, 200, ""));
		
		//cashier button (21)
		buttons.add(new Button(20, 400, 180, 160, "cash register"));
		
		//bookmark button (22)
		buttons.add(new Button(520, 0, 26, 36, ""));
		
		//book button (23)
		buttons.add(new Button(443, 20, 129, 150, ""));
		
		activateButtons();
		
		iceXCount = new int[5];
		iceXMax = new boolean[5];
		
		songCount = 0;
		
	}

	/**
	 * Initializes game objects and variables such as images
	 * Called once when the screen is first loaded
	 */
	public void setup() {
		scooper.setup();
		teaFall.setup();
		surface.background(255, 255, 255);  
		startTime = -1;
		phase = STARTUP;
		background = surface.loadImage("gameScreen/cafeScreenUPDATED.png");
		
		teaImgs.add(surface.loadImage("orderImgs/brownsugarTea.png"));
		teaImgs.add(surface.loadImage("orderImgs/passionTea.png"));
		teaImgs.add(surface.loadImage("orderImgs/strawberryTea.png"));
		teaImgs.add(surface.loadImage("orderImgs/boba.png"));
		teaImgs.add(surface.loadImage("orderImgs/popping.png"));
		teaImgs.add(surface.loadImage("orderImgs/lychee.png"));
		teaImgs.add(surface.loadImage("orderImgs/bobaWPopping.png"));
		teaImgs.add(surface.loadImage("orderImgs/bobaWLychee.png"));
		teaImgs.add(surface.loadImage("orderImgs/poppingWLychee.png"));
		teaImgs.add(surface.loadImage("orderImgs/brownsugarTeaNoStraw.png"));
		teaImgs.add(surface.loadImage("orderImgs/passionTeaNoStraw.png"));
		teaImgs.add(surface.loadImage("orderImgs/strawberryTeaNoStraw.png"));
		teaImgs.add(surface.loadImage("orderImgs/allToppings.png"));
		
		customers[0] = new Customer(surface, "customers/bunny.png");
		customers[1] = (new Customer(surface, "customers/capybara.png"));
		customers[2] = (new Customer(surface, "customers/cat.png"));
		customers[3] = (new Customer(surface, "customers/dog.png"));
		customers[4] = (new Customer(surface, "customers/duck.png"));
		customers[5] = (new Customer(surface, "customers/frog.png"));
		customers[6] = (new Customer(surface, "customers/hippo.png"));
		customers[7] = (new Customer(surface, "customers/shark.png"));
		customers[8] = (new Customer(surface, "customers/wolf.png"));
		
		sugarPetals.add(surface.loadImage("sugarPetals/0sugarLeaf.png"));
		sugarPetals.add(surface.loadImage("sugarPetals/25sugarLeaf.png"));
		sugarPetals.add(surface.loadImage("sugarPetals/50sugarLeaf.png"));
		sugarPetals.add(surface.loadImage("sugarPetals/75sugarLeaf.png"));
		sugarPetals.add(surface.loadImage("sugarPetals/100sugarLeaf.png"));
		
		coins = surface.loadImage("gameScreen/coins.png");
		
		//load audio files and add to the arrays
		effectsPlayer = new JayLayer("audio/","audio/",true);
		songPlayer = new JayLayer("audio/","audio/",true);
		
		soundEffects = new String[] {"effect4.mp3", "iceSound.mp3", "pourSound.mp3", "sugarFall.mp3", 
				"bark.mp3", "bunnySniff.mp3", "capybara.mp3", "howl.mp3", "meow.mp3", 
				"quack.mp3", "ribbit.mp3", "shark.mp3", "hippo.mp3","andhisnamewasJOHNCENAAA.mp3"};
		songs = new String[] {"UniverseOfLove.mp3", "hurt.mp3", "airplanethoughts.mp3", "Clair de Lune.mp3", "xo.mp3"
				, "it's you.mp3", "thecutestpair.mp3", "lovewithyou.mp3", 
				"whatifwe.mp3", "dearmydarling.mp3", "sogood.mp3","doubletake.mp3","infrunami.mp3","besideyou.mp3",
				"tiramisu.mp3","RUDE!.mp3", "gluesong.mp3", "thatsmygirl.mp3", "venus.mp3","yourockmyworld.mp3","sourgrapes.mp3",
				"highschoolboy.mp3", "stillwithyou.mp3", "moonstruck.mp3", "besttime.mp3", "justadreamSnippet.mp3", 
				"dandelions.mp3", "warrr.mp3", "lovelee.mp3", "buddahlovaz.mp3", "crush.mp3",
				};
		
		for(int i = 0; i < soundEffects.length; i++) {
			effectsPlayer.addSoundEffect(soundEffects[i]);
		}
		
		songPlayer.addPlayList();
		songPlayer.addSongs(playlistCount, songs);
		
		songPlayer.changePlayList(0);
	    songPlayer.nextSong();
		
	    songPlayer.setRandomizePlayOrder(false);
		
		spawnCustomer();
		userDrink = new Concoction(order.getDrink());
		finishedDrink = userDrink;
		
		emptyCup = surface.loadImage("gameScreen/emptyCup.png");
		receipt = surface.loadImage("gameScreen/receipt.png");
		teaLine = surface.loadImage("gameScreen/teaLine.png");
		coinBar = surface.loadImage("gameScreen/coinBar.png");
		dispenser = surface.loadImage("gameScreen/dispenser.png");
		counter = surface.loadImage("gameScreen/cafeScreenCounterUPDATE8.png");
		book = surface.loadImage("gameScreen/book.png");
		bookmark = surface.loadImage("gameScreen/bookmark.png");
		
		milkChoco = surface.createFont("Milk Choco.otf", 10);
		surface.textFont(milkChoco);
	}

	/**
	 * Main draw loop for the game screen
	 * Handles rendering and updating game elements each frame
	 */
	public void draw() {
		//System.out.println(buttons.get(23).isActive());
		// drawing stuff
		surface.image(background, 0, 0, 1200, 600);
		surface.image(customer.getImage(), 0, -20, 600, 600);
		if (!(customer.getType().equals("bunny") || customer.getType().equals("capybara"))) {
			surface.image(counter, 0, 0, 1200, 600);
		}
		
		//instructions book
		if (drawBook) {
			//System.out.println("draw book");
			surface.image(book, 397, 0, 220, 220);
		}
		else
			surface.image(bookmark, 460, 0, 150, 150);
		
//		buttons.get(22).draw(surface);
//		buttons.get(23).draw(surface);
		
		//order
		if (level >= 1) {
			surface.image(receipt, -40, 0, 300, 300);
			surface.fill(97, 63, 19);
			surface.textSize(20);
			surface.text("scoops x" + order.getDrink().getNumScoops() + "\nice: " + order.getDrink().getIceLevel() + "\nsugar: " + order.getDrink().getSugarLevel(), 70, 75);
			surface.image(order.getDrink().getDrinkImage(teaImgs)[0], 50, 155, 120, 120);
			surface.image(order.getDrink().getDrinkImage(teaImgs)[1], 50, 155, 120, 120);

		}
		//coinbar
		surface.push();
		surface.image(coinBar, 1020, -50, 200, 200);
		surface.textSize(24);
		surface.fill(255);
		surface.text(numCoins, 1130, 40);
		surface.pop();
		
		//textBox
		if (drawText && level < 6) {
			surface.stroke(99, 69, 0);
			surface.strokeWeight(5);
			surface.fill(255, 234, 189);
			surface.rect(420, 200, 130, 180);
			surface.textAlign(surface.CENTER, surface.CENTER);
			surface.fill(0);
			surface.textSize(14);
			surface.text(customerSaying, 425, 200, 120, 170);
		}
		
		// drawing the tea rising in cup
		if (phase == STARTUP) {
		}
		else if (phase == TIMER_GOING) {
			levelTime = System.currentTimeMillis() - startTime;
			if (levelTime < 2700) {
			//if (levelTime < 1600) {
			} 
			else {
				double height = userDrink.fillHeight(levelTime - 2700);
				//double height = userDrink.fillHeight(levelTime - 1600);
				if (height > 250)
					height = 250;
				teaFall.drawCupFill(surface, height, 795, 420, 180);
				//if (height > 75) {
				//if (height > 120) {
//					for (int i = 0; i < iceCubes.size() - 1; i++) {
//						for (int j = i + 1; j < iceCubes.size(); j++) {
//							iceCubes.get(i).floatIce(true, iceCubes.get(j), height);
//						}
//					}
						//boolean isStacked = false;
//					for (PhysicsIce e : iceCubes)
//						if((420 - height) < (e.getY()))
//						if(!(userDrink.fillHeight(levelTime - 1600) > e.getY()))
//						if(!(userDrink.fillHeight(levelTime - 2700) > e.getY())) {
//								System.out.println(i);
//								System.out.println(iceCubes.get(j).getX());
//								System.out.println(iceCubes.get(i).getX());
//								System.out.println("");
//								if (iceCubes.get(i).getX() == iceCubes.get(j).getX() && iceCubes.get(i).getY() != iceCubes.get(j).getY()) {
//									if ((420 + 35) - height < iceCubes.get(i).getY()) {
//										System.out.println("yes");
//										iceCubes.get(i).floatIce(true);
//										isStacked = true;
//									}
//								} 
//							} 
//							if (!isStacked)
//							e.floatIce(true, e, height);
//						}
					for (PhysicsIce e : iceCubes) {
						if (420 - height < e.getY())
							e.floatIce(true, height);
						else
							e.floatIce(false, 0);
					}
			}
				
				
		}
		else {
			double height = 0;
			if (level == 4) {
				level = 5;
				activateButtons();
				}
			if (levelTime >= 2700) {
			//if (levelTime >= 1600) {
				height = userDrink.fillHeight(levelTime - 2700);
				//height = userDrink.fillHeight(levelTime - 1600);
			}
			else {
				height = userDrink.fillHeight(levelTime);
			}
			if (height > 250)
				height = 250;
			if (teaFall.getY() + teaFall.getDropHeight() > 400) {
				teaFall.drawCupFill(surface, height, 795, 422, 180);
			}
			for (PhysicsIce e : iceCubes) {
				e.floatIce(false, 0);	
			}
			if (userDrink.getTeaLevel() == 0) {
				userDrink.setTeaLevel(height);
			}
		}

		//drawing the cup outline, scooper, toppings, tea stream, and ice cubes
		if (level >= 4 && level < 7) { //tea
			if (userDrink.getTea() != null)
				drawTeaFalling();
			if (teaFall.stopDrawing()) {
				for (int i = 12; i <= 14; i++) {
					buttons.get(i).setState(false);
				}
			}
			surface.image(dispenser, 0, 0, 1200, 600);
			surface.image(sugarClicked, 0, 0, 1200, 600);
		}	
		scooper.draw();
		
		if (level >= 3  && level < 7) { //ice
			buttons.get(iceIndex).draw(surface);
			drawIceFalling(userDrink.getIceLevel(), physics);
			scooper.discard();
		}
		
		if (level == 4) {
			drawTeaLine();
		}
		
		surface.image(emptyCup, 700, 100, 370, 370);
		
		if (level == 7) {
			surface.image(finishedDrink.getDrinkImage(teaImgs)[0], 230, 425, 150, 150);
			surface.image(finishedDrink.getDrinkImage(teaImgs)[1], 230, 425, 150, 150);
		}
		
		if(level == 8) {
			surface.image(finishedDrink.getDrinkImage(teaImgs)[0], 170, 325, 120, 120);
			surface.image(finishedDrink.getDrinkImage(teaImgs)[1], 170, 325, 120, 120);
			surface.image(coins, 0, -10, 600, 600);
			//draw their rating
			surface.stroke(99, 69, 0);
			surface.strokeWeight(5);
			surface.fill(255, 234, 189);
			surface.rect(420, 200, 130, 180);
			surface.textSize(14);
			surface.textAlign(surface.CENTER, surface.CENTER);
			surface.fill(0);
			surface.text(customer.getRating(finishedDrink.getStars()), 425, 200, 120, 170);
			
			//draw textbox
			activateButtons();
		}
		
		if(level == 9) {
			spawnCustomer();
			level = 0;
			activateButtons();
			buttons.get(21).setState(false);
		}
	}
	
	/**
	 * adds all the values of the drink's elements the user made so that it can be checked and scored
	 */
	public void updateDrink() {
		userDrink.setToppings(scooper.getToppingArray());
	}
	
	/**
	 * runs when the user presses the reset drink button; 
	 * resets the made drink to an empty drink
	 */
	public void reset() {
		userDrink.reset();
		level = 1;
		activateButtons();
		phase = STARTUP;
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setStroke(0, 0, 0);
		}
		teaClicked = false;
		scooper.resetDrink();
		teaFall.setStart(false);
		iceCubes = new ArrayList<PhysicsIce>();
		playedIceSound = false;
		teaFall = new PhysicsTea(null);
		count = 0;
		iceXCount = new int[5];
		iceXMax = new boolean[5];
	}
	
	/**
	 * runs when the user finishes the drink and clicks the done button
	 */
	public void done() {
		
		if (level == 5) {
			level = 6;
			activateButtons();
		} 
		
		updateDrink();
		userDrink.setResetCount(resetCount);
		surface.switchScreen(ScreenSwitcher.SCORE_SCREEN);
		scooper.resetDrink();
		finishedDrink = userDrink;
		userDrink = new Concoction(order.getDrink());
		resetCount = 0;
		drawText = false;	
		
	}
	
	/**
	 * serves the drink into the hand of the customer
	 * also resets the concoction
	 */
	public void serve() {
		reset();
		level = 7;
		activateButtons();
		
	}
	
	/**
	 * Spawns a new customer with a random drink order.
	 */
	public void spawnCustomer() {
		order = new Order();
		userDrink = new Concoction(order.getDrink());
		customer = Customer.randomCustomer(customers);
		level = 0;
		activateButtons();
		drawText = false;
	}
	
	/**
	 * checks to activate/deactivate buttons based on the level
	 * the user is on while drink making
	 */
	public void activateButtons() {
		if (level == 0) { //ready to take order, when customer spawns, customer open, bookmark open
			//System.out.println("bookmark open");
			for (int i = 0; i < buttons.size(); i++) {
				if (i != 20 && i != 22) 
					buttons.get(i).setState(false);
				else 
					buttons.get(i).setState(true);
			}
		} else if (level == 1) { //start of the process, toppings and reset open, bookmark open
			for (int i = 0; i < buttons.size(); i++) {
				if (i != 11 && (i <= 14 || i >= 20) && i != 22) {
					buttons.get(i).setState(false);
				} else {
					buttons.get(i).setState(true);
				}
				buttons.get(21).setState(false);
			}
		} else if (level == 2) { //ready to click ice, reset, toppings and ice open, bookmark open
			for (int i = 0; i < buttons.size(); i++) {
				if (i != 22 && i >= 5 && i != 11 && i <= 14 || i >= 20) {
					buttons.get(i).setState(false);
				} else{
					buttons.get(i).setState(true);
				}
				buttons.get(21).setState(false);
			}
		} else if (level == 3) { //ready to click sugar, reset and sugar open, bookmark open
			for (int i = 0; i < buttons.size(); i++) {
				if ((i <= 4 || i >= 10) && i != 11 && i != 22) {
					buttons.get(i).setState(false);
				} else{
					buttons.get(i).setState(true);
				}
			}
		} else if (level == 4) { //ready to click tea, reset and tea open, bookmark open
			for (int i = 0; i < buttons.size(); i++) {
				if ((i <= 10 || i >= 15) && i != 22) {
					buttons.get(i).setState(false);
				} else{
					buttons.get(i).setState(true);
				}
			}
		} else if (level == 5) { //ready to click done, done and reset open, bookmark open                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			for (int i = 0; i < buttons.size(); i++) {
				if ((i <= 9 || i >= 12) && i != 22) {
					buttons.get(i).setState(false);
				} else{
					buttons.get(i).setState(true);
				}
			}
		} else if (level == 7) { //after clicking done, customer open, bookmark open
			for (int i = 0; i < buttons.size(); i++) {
				if (i != 20 && i != 22) 
					buttons.get(i).setState(false);
				else 
					buttons.get(i).setState(true);
			}
		} else if (level == 8) { //after serving, only register open, bookmark open
			for (int i = 0; i < buttons.size(); i++) {
				if(i != 21 && i != 22) {
					buttons.get(i).setState(false);
				}else {
					buttons.get(i).setState(true);
				}
			}
		} else if(level == 9){ //after clicking register, bookmark open
			reset();
			buttons.get(21).setState(false);
		}
	}
	
	/**
	 * Handles mouse press events
	 */
	public void mousePressed() {
		
		if (buttons.get(23).isActive()) {
			//System.out.println("on");
			if (buttons.get(23).isClicked(surface.mouseX, surface.mouseY)) {
				surface.switchScreen(ScreenSwitcher.INSTRUCTIONS_SCREEN);
			} else {
				drawBook = false;
				buttons.get(23).setState(false);
				buttons.get(22).setState(true);
			}
		} else {
		
			if (!teaClicked) {
				teaFall.setStart(true);
				selectTea();
			}
			
			selectToppings();
			selectIce();
			selectSugar();
			grabScooper();
			dropToppings();
			
			for(Button e : buttons) {
				if(!e.getName().equals("cash register") && e.isActive()) {
					if (((Math.random() * 5) < 1) && drawText == false && customer.isFirstVisit() == false) {
						drawText = true;
						customerSaying = customer.speak();
					}
				}
	//			customerSaying = customer.speak(); //check textbox 
			}
			
			if (buttons.get(11).isActive() && buttons.get(11).isCircleClicked(surface.mouseX, surface.mouseY)) {
				reset();
				scooper.resetDrink();
				resetCount++;
			}
			else if (buttons.get(10).isActive() && buttons.get(10).isCircleClicked(surface.mouseX, surface.mouseY)) {
				done();
			}
			else if (buttons.get(20).isClicked(surface.mouseX,  surface.mouseY)) {
				if (customer.isFirstVisit()) {
					drawText = true;
					customerSaying = customer.speak();
				} effectsPlayer.playSoundEffect(customer.getSoundEffect());
				if (buttons.get(20).isActive()) {
					if(level == 7) {
						serve();
						level = 8;
					} else {
						level = 1;
						activateButtons();
					}
				}
				
			}
			
			else if(buttons.get(21).isClicked(surface.mouseX, surface.mouseY) && buttons.get(21).isActive()) {
				effectsPlayer.playSoundEffect(0);
				numCoins += finishedDrink.getPrice() + (int) ((finishedDrink.score() / 100.0) * 15);
				if (numCoins >= 500) {
					surface.switchScreen(ScreenSwitcher.WIN_SCREEN);
					songPlayer.stopSong();
					effectsPlayer.playSoundEffect(13);
				}
				level = 9;
			}
			else if (buttons.get(22).isClicked(surface.mouseX, surface.mouseY) && buttons.get(22).isActive()) {
				drawBook = true;
				//System.out.println("clicked");
				buttons.get(23).setState(true);
				buttons.get(22).setState(false);
			}
		}
	}
	
	/**
	 * Implements mouse released event to the active screen
	 */
	public void mouseReleased() {
		if (phase == TIMER_GOING) {
			phase = RESULTS;
			if (level == 4) {
				level = 5;
				activateButtons();
				teaClicked = true;
				
			}
		}
	}
	
	/**
	 * handles key pressed events
	 */
	public void keyPressed() {
		if(surface.key == 'n') {
			if(songCount == songs.length) {
				for(int i = 0; i < songs.length; i++) {
					int increment = (int) (Math.random() * (songs.length-1) + 1);
					int index = i + increment;
					if(index >= songs.length) {
						index -=  songs.length;
					}
					String temp = songs[i];
					songs[i] = songs[index];
					songs[index] = temp;
				}
				
				playlistCount++;
				songPlayer.addPlayList();
				songPlayer.addSongs(playlistCount, songs);
				songPlayer.changePlayList(playlistCount);
				songCount = 0;
			}
		    songPlayer.nextSong();
		    songCount++;
		} 
//		else if (surface.key == '`') {
//			spawnCustomer();
//		}
	}
	
	/**
	 * Draws the ice cubes falling down into the cup
	 * @param iceLevel the amount of ice that the user chose
	 */
	public void drawIceFalling(int iceLevel, ArrayList<PhysicsShape> physics) {
		
		if(count < iceCubes.size()) {
			if(!playedIceSound) {
				effectsPlayer.playSoundEffect(1);
				playedIceSound = true;
			}

			iceFalling = true;
			surface.fill(142, 225, 255);
			surface.stroke(240, 251, 255);

			if(iceCubes.size() != 0) {
				iceCubes.get(0).actIce(iceCubes, physics);
				iceCubes.get(0).stayInCup();
				iceCubes.get(0).draw(surface);
			}

			for (int i = 1; i < iceCubes.size(); i++) {	
				if (iceCubes.get(i - 1).getY() > 120) {
					iceCubes.get(i).actIce(iceCubes, physics);
					iceCubes.get(i).stayInCup();
					iceCubes.get(i).draw(surface);	
				}
			}
		}

			for(PhysicsShape ice : iceCubes) {
				if(ice.getVy() == 0) {
					count++;
				}else {
					count = 0;
				}
			}
			
			for(PhysicsShape ice : iceCubes) {
				ice.draw(surface);
			}
			
		int count1 = 0;
		for(PhysicsIce e : iceCubes) {
			if(e.getIceVy() == 0) {
				count1++;
			}else {
				count1 = 0;
			}
		}
		
		if(count1 == iceCubes.size()) {
			iceFalling = false;
		}
	}
	
	/**
	 * allows the user to grab the scooper and start scooping
	 */
	public void grabScooper() {
		if(buttons.get(18).isActive() && buttons.get(18).isClicked(surface.mouseX, surface.mouseY)) {
			scooper.grab();
		}
	}
	
	/**
	 * chooses the topping the user presses 
	 */
	public void selectToppings() {
		for(int i = 15; i <= 17; i++) {
			if(buttons.get(i).isActive() && buttons.get(i).isClicked(surface.mouseX, surface.mouseY) && scooper.getStep() == 2) {
				if(scooper.getNumScoops() < 5) {
					scooper.setTopping(new Topping(buttons.get(i).getName()));
					Topping t = new Topping(scooper.getToppingHeld());
					scooper.scoop(t);
					level = 2;
					activateButtons();
				}else {
					level = 2;
					activateButtons();
					for(int j = 15; j <= 19; j++) {
						buttons.get(j).setState(false);
					}
					scooper.discard();
					System.out.println("max number of scoops reached.");
					break;
				}
			}
		}
	}
	
	/**
	 * drops the toppings into the cup
	 */
	public void dropToppings() {
		if(buttons.get(19).isActive() && buttons.get(19).isClicked(surface.mouseX, surface.mouseY) && scooper.getStep() == 3) {
			scooper.changeXCoordinate();
			scooper.setStep(4);
		}
	}
	
	/**
	 * checks to see which ice level the user selected
	 */
	public void selectIce() {
		physics = scooper.getPhysicsShapes();
		int iceLevel = 0;
		int[] iceXVals = {795, 831, 867, 903, 939};
		for(int i = 0; i <= 4; i++) {
			if (buttons.get(i).isClicked(surface.mouseX, surface.mouseY) && buttons.get(i).isActive() && !scooper.isDropping() && (scooper.getStep() == 2  || scooper.getStep() == 1)) {
				iceLevel = (i + 1) * 10;
				userDrink.setIceLevel(iceLevel);
				level = 3;
				userDrink.setNumScoops(scooper.getNumScoops());
				activateButtons();
				iceIndex = i;
				buttons.get(i).setStroke(168, 221, 213);
			}
		}
		int iceCount = 0;
		if (iceLevel == 10) {
			iceCount = 6;
		} else if (iceLevel == 20) {
			iceCount = 7;
		} else if (iceLevel == 30) {
			iceCount = 8;
		} else if (iceLevel == 40) {
			iceCount = 9;
		} else if (iceLevel == 50) {
			iceCount = 10;
		}
		for(int i = 0; i < iceCount; i++) {
			int pos = (int)(Math.random() * iceXVals.length);
			PhysicsIce ice = new PhysicsIce(new Rectangle(iceXVals[pos], 100, 35, 35));
			
			if(pos == 0 && !iceXMax[0]) {
				songCount++;
				ice.getShape().setFillColor(new Color(240, 251, 255));
				ice.getShape().setStrokeColor(new Color(197, 229, 242));
				bounds.add(ice);
				iceCubes.add(ice);
				iceXCount[0]++;
				if(iceXCount[0] == 3){
					iceXMax[0] = true;
				}
			}else if(pos == 1 && !iceXMax[1]) {
				songCount++;
				ice.getShape().setFillColor(new Color(240, 251, 255));
				ice.getShape().setStrokeColor(new Color(197, 229, 242));
				bounds.add(ice);
				iceCubes.add(ice);
				iceXCount[1]++;
				if(iceXCount[1] == 3){
					iceXMax[1] = true;
				}
			}else if(pos == 2 && !iceXMax[2]) {
				songCount++;
				ice.getShape().setFillColor(new Color(240, 251, 255));
				ice.getShape().setStrokeColor(new Color(197, 229, 242));
				bounds.add(ice);
				iceCubes.add(ice);
				iceXCount[2]++;
				if(iceXCount[2] == 3){
					iceXMax[2] = true;
				}
			}else if(pos == 3 && !iceXMax[3]) {
				songCount++;
				ice.getShape().setFillColor(new Color(240, 251, 255));
				ice.getShape().setStrokeColor(new Color(197, 229, 242));
				bounds.add(ice);
				iceCubes.add(ice);
				iceXCount[3]++;
				if(iceXCount[3] == 3){
					iceXMax[3] = true;
				}
			}else if(pos == 4 && !iceXMax[4]) {
				songCount++;
				ice.getShape().setFillColor(new Color(240, 251, 255));
				ice.getShape().setStrokeColor(new Color(197, 229, 242));
				bounds.add(ice);
				iceCubes.add(ice);
				iceXCount[4]++;
				if(iceXCount[4] == 3){
					iceXMax[4] = true;
				}
			}
		}
	}
	
	/**
	 * Checks to see which tea the user selected and adds it to userDrink
	 */
	public void selectTea() {
		for(int i = 12; i <= 14; i++) {
			if (buttons.get(i).isClicked(surface.mouseX, surface.mouseY)) {
				if (buttons.get(i).isActive()) {
					effectsPlayer.playSoundEffect(2);
					if (i == 12) 
						userDrink.setTea(new Tea("passionFruit"));
					else if (i == 13)
						userDrink.setTea(new Tea("brownsugar"));
					else
						userDrink.setTea(new Tea("strawberry"));
					if (phase == STARTUP) {
						startTime = System.currentTimeMillis();
						phase = TIMER_GOING;
					}
					teaFall = new PhysicsTea(userDrink.getTea());
					teaFall.setup();
					teaFall.setStart(false);
					buttons.get(i).setStroke(177, 255, 224);
				}
			}
		}
	}
	
	/**
	 * Draws the tea falling down from the dispenser into the cup
	 */
	public void drawTeaFalling() {
		for (int i = 12; i <= 14; i++) {
			if (surface.mousePressed && buttons.get(i).isClicked(surface.mouseX, surface.mouseY) && buttons.get(i).isActive()) {
				teaFall.setState(true);
			}
		} if (!surface.mousePressed) {
			teaFall.setState(false);
		}
		teaFall.act();
		teaFall.draw(surface);
	}
	
	/**
	 * Checks to see which ice the user selects and enters it into the userDrink
	 */
	public void selectSugar() {
		for (int i = 5; i <= 9; i++) {
			if (buttons.get(i).isActive() && buttons.get(i).isCircleClicked(surface.mouseX, surface.mouseY) && !iceFalling) {
				effectsPlayer.playSoundEffect(3);
				userDrink.setSugarLevel((i - 5) * 25);
				sugarClicked = sugarPetals.get(i - 5);
				level = 4;
				activateButtons();
			}
		}
	}
	
	/**
	 * draws the line where the user is supposed to fill the drink to
	 */
	public void drawTeaLine() {
		surface.image(teaLine, 700, (float) ((420 - order.getDrink().getTeaLevel()) - 183.4), 370, 370);
	}
	
	/**
	 * returns the userDrink
	 * @return the userDrink
	 */
	public Concoction getConcoction() {
		return userDrink;
	}
	
	/**
	 * sets the level to another level
	 * @param level the level that the current one is being changed to
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * returns the tea images
	 */
	public PImage[] getTeaImgs() {
		return userDrink.getDrinkImage(teaImgs);
	}
	
	public void switchedTo() {
		drawBook = false;
		buttons.get(22).setState(true);
		buttons.get(23).setState(false);
	}
	
}