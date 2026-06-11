package screens;

/**
 * Interface that allows switching between different game screens
 * 
 * Implementing classes can change the active screen using already defined screens
 * It can change the active screen using already defined screens
 * 
 * @author Cindy Cheng, Keira Shimizu, Vashini Raja
 * @version 5-25-26
 */
public interface ScreenSwitcher {
	public static final int MENU_SCREEN = 0;
	public static final int GAME_SCREEN = 1;
	public static final int SCORE_SCREEN = 2;
	public static final int INSTRUCTIONS_SCREEN = 3;
	public static final int WIN_SCREEN = 4;
	
	/**
	 * Switches the currently active screen
	 * 
	 * @param i the screen index to switch to MENU_SCREEN or GAME_SCREEN
	 */
	public void switchScreen(int i);
}
