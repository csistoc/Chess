package controller;

public class TextFormatController {
	
	private static final int spacing = 20;
	
	public static String leaderboardTypeFormat(String field1, String field2) {
		String output = new String();
		while (output.length() + field1.length() < spacing)
			output += " ";
		output += field1;
		while (output.length() + field2.length() < 2 * spacing)
			output += " ";
		output += field2;
		output += "\n";
		return output;
	}
	
	public static String leaderboardFormat(String name, int value) {
		String output = new String();
		while (output.length() + name.length() < spacing)
			output += " ";
		output += name;
		while (output.length() + (int)(Math.log10(value)) + 1 < 2 * spacing)
			output += " ";
		output += value;
		output += "\n";
		return output;
	}
}
