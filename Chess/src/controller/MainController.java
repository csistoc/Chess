package controller;

import java.util.Scanner;

import view.InterfaceMessages;
import view.ConsoleInterface;

public class MainController {
	
	public static boolean welcomeMenu(String input, boolean start_game) {
		switch (input) {
			case "1":
				start_game = true;
				return true;
			case "2":
				System.out.print(InterfaceMessages.workInProgress());
				return false;
			case "3":
				System.out.print(InterfaceMessages.workInProgress());
				return false;
			case "4":
				return true;
			default:
				System.out.print(InterfaceMessages.wrongInput());
				return false;
		}
	}
	
	public static void run() {
		Scanner keyboard = new Scanner(System.in);
		String input = new String();	
		boolean welcome_menu_input = false;
		boolean start_game = false;
		while (!welcome_menu_input) {
			System.out.print(InterfaceMessages.welcomeMenu());
			input = keyboard.nextLine();
			welcome_menu_input = welcomeMenu(input, start_game);
		}
		if (start_game) {
			ConsoleInterface I = new ConsoleInterface();
			while(true) {
				ConsoleInterfaceController.showCurrentStage(I);
				System.out.print(InterfaceMessages.enterInput());
				input = keyboard.nextLine();
				
			}
		}
		keyboard.close();
	}

	public static void main(String[] args) {
		run();
	}

}
