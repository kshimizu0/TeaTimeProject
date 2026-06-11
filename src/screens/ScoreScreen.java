package screens;

import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.Concoction;
import gameElements.Straw;
import jay.jaysound.JayLayer;
import processing.core.PImage;

/**
 * represents the screen where the user will poke the straw and receive their score
 * @author Cindy Cheng, Keira Shimizu
 * @version 5-25-26
 */
public class ScoreScreen extends Screen{
	private ArrayList<PImage> images;
	private PImage image;
	private PImage[] drinkParts, fiveStars;
	private GameScreen gameScreen;
	private Concoction finishedDrink;
	private Straw straw;
	private int step, drawCount, score, dingCount;
	private boolean soundPlayed, drumPlayed;
	
	private static final int STARTUP = 1, TIMER_GOING = 2, END = 3;
	private int phase;
	private long startTime;
	
	private JayLayer effectsPlayer;
	private String[] soundEffects;
	
	
	/**
	 * constructs the scoring screen
	 * @param surface the main drawing surface
	 */
	public ScoreScreen(DrawingSurface surface, GameScreen gameScreen) {
		super(1200, 600, surface);
		this.gameScreen = gameScreen;
		images = new ArrayList<PImage>();
		step = 0; //poking the straw
		dingCount = 0;
		phase = STARTUP;
		display();
		soundPlayed = false;
		
	}
	
	/**
	 * initializes the images of the screen
	 */
	public void setup() {
		images.add(surface.loadImage("scoreScreen/spotlight.png"));
		images.add(surface.loadImage("scoreScreen/fullStar.png"));
		images.add(surface.loadImage("scoreScreen/halfStar.png"));
		images.add(surface.loadImage("scoreScreen/noStar.png"));
		images.add(surface.loadImage("scoreScreen/pokingScreen.png"));
		image = images.get(4);
		
		soundEffects = new String[] {"fahh.mp3","effect1.mp3","drumroll.mp3", "ding.mp3","OMGsoundfx.mp3","ohHeckNah.mp3", "itsalright.mp3"};
		effectsPlayer = new JayLayer("audio/","audio/",true);
		
		for(int i = 0; i < soundEffects.length; i++) {
			effectsPlayer.addSoundEffect(soundEffects[i]);
		}
		
		
	}
	
	/**
	 * draws the screen, straw, and the drink
	 */
	public void draw() {
		if(straw.isPoked() && (straw.getPhysics().getY() >= 142 && !finishedDrink.getTea().getFlavor().equals("strawberry") || straw.getPhysics().getY() >= 112 && finishedDrink.getTea().getFlavor().equals("strawberry"))) {
			image = images.get(0);
			if (!drumPlayed) {
				effectsPlayer.playSoundEffect(2);
				drumPlayed = true;
			}
			surface.image(image, 0, 0, 1200, 600);
		}
		
		surface.image(image, 0, 0, 1200, 600);
		//if image is blank screen w straw
		surface.image(drinkParts[2], 360, 68, 480, 480);
		surface.image(drinkParts[1], 360, 68, 480, 480);
		straw.draw(surface);
		
		if (phase == STARTUP) {
		} else if (phase == TIMER_GOING) {
			if (System.currentTimeMillis() - startTime > 4000) {
				phase = END;
			}
		} else {
			drawCount++;
			int translateX = 30;
			if (dingCount < 41 && dingCount % 10 == 0) {
				effectsPlayer.playSoundEffect(3);
			}
			surface.image(fiveStars[0], 310 + translateX, 60, 150, 150);
			if (drawCount >= 10) {
				surface.image(fiveStars[1], 400 + translateX, 30, 150, 150);
			}
			if (drawCount >= 20) {
				surface.image(fiveStars[2], 500 + translateX, 10, 150, 150);	
			}
			if (drawCount >= 30) {
				surface.image(fiveStars[3], 600 + translateX, 30, 150, 150);
			}
			if (drawCount >= 40) {
				surface.image(fiveStars[4], 690 + translateX, 60, 150, 150);
				if (!soundPlayed) {
					if (score >= 80) {
						effectsPlayer.playSoundEffect(1);
					} else if (score <= 30) {
						effectsPlayer.playSoundEffect(0);
					} else if(score > 30 && score <= 50) {
						effectsPlayer.playSoundEffect(5);
					} else if(score > 50 && score <= 70) {
						effectsPlayer.playSoundEffect(4);
					} else if (score > 70 && score < 80) {
						effectsPlayer.playSoundEffect(6);
					}
					
					soundPlayed = true;
				}
				surface.textSize(24);
				surface.text(">> press any key to continue", 490, 555);
			}
			step = 2;
			dingCount++;
			}
	}

	/**
	 * Called when this screen is switched to.
	 */
	public void switchedTo() {
		finishedDrink = this.gameScreen.getConcoction();
		if (finishedDrink.getTea().getFlavor().equals("strawberry")) 
			straw = new Straw(true); //change the color later
		else
			straw = new Straw(false);
		drawCount = 0;
		setTeaImgs();
		phase = STARTUP;
		step = 0;
		soundPlayed = false;
		image = images.get(4);
		drumPlayed = false;
		dingCount = 0;
	}
	
	private void setTeaImgs() {
		drinkParts = this.gameScreen.getTeaImgs();
	}
	
	/**
	 * Handles mouse press events
	 */
	public void mousePressed() {
		if (step == 0) {
			straw.poke();
			finishedDrink.setStrawPos(straw.getX());
			phase = TIMER_GOING;
			startTime = System.currentTimeMillis();
			step = 1;
			score = finishedDrink.score();
			
			fiveStars = new PImage[5];
			
			for (int i = 0; i < 5; i++) {
				if (finishedDrink.getStars()[i].equals("full star")) {
					fiveStars[i] = images.get(1);
				} else if (finishedDrink.getStars()[i].equals("half star")) {
					fiveStars[i] = images.get(2);
				} else if (finishedDrink.getStars()[i].equals("no star")) {
					fiveStars[i] = images.get(3);
				} else {
					System.out.println("wrong stars");
				}
			}
		}
	}
	
	/**
	 * Handles key press events
	 */
	public void keyPressed() {
		if (step == 2 && soundPlayed) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			gameScreen.serve();
			gameScreen.setLevel(7);
		}
	}
	

}
