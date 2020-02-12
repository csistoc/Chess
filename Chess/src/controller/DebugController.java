package controller;

import model.TableModel;

public class DebugController {
	
	public static String print(TableModel table) {
		String output = new String();
		for (int i = 0; i < table.getPieces().size(); i++)
			output += "Type: " + table.getPieces().get(i).getType() +
					"\n" + table.getPieces().get(i).getLocation() +
					"\nOwner: " + table.getPieces().get(i).getOwner() + "\n-------------------------\n";
		return output;
	}
	
}
