package controller;

public class InputController {
	
	public static boolean checkMoveInput(String input) {
		boolean output = true;
		for (int i = 0; i < input.length() && output; i++)
			if (!Character.isDigit(input.charAt(i)))
				output = false;
		return output;
	}
	
}
