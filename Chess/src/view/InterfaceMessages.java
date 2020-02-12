package view;

public class InterfaceMessages {
	
	private static final String start_menu_options = "1.New game\n2.Load game(in progress)\n3.Scoreboard(in progress)\n4.Quit\n";
	
	public static String welcomeMenu() {
		return "Welcome\nPlease choose the number corresponding with one of the following options you would like to do:\n" + start_menu_options 
				+ "[Input]: ";
	}
	
	public static String workInProgress() {
		return "Work in progress. Please choose another option\n\n";
	}
	
	public static String wrongInput() {
		return "Wrong input. Please try again\n\n";
	}
	
	public static String enterInput() {
		return "[Input]: ";
	}

}
