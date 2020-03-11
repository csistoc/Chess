package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadgameController {
	
	private static final String filePath = SavegameController.filePath;
	
	private static String getSaveInfo() {
		File[] files = new File(filePath).listFiles();
		if (files == null)
			return "There are no save games.\n";
		else {
			String output = new String();
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile()) {
					try {
						int lines = 0;
						Scanner reader = new Scanner(files[i]);
						while (reader.hasNextLine()) 
							if (lines < 3) {
								String data = reader.nextLine();
								output += data + " ";
								lines++;
							}
						else {
							reader.close();
							break;
						}
						reader.close();
						output += "\n";
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			return output;
		}
	}
	
	private static String getSaveInfoWithFormat() {
		String output = new String();
		String segments[] = getSaveInfo().split(" ");
		for (int i = 0; i < segments.length - 3; i+=3)
			output += segments[i] + " vs " + segments[i + 1] + " on turn " + segments[i + 2];
		return output;
	}
	
	public static ArrayList<String> getSaveInfoAsList() {
		ArrayList<String> output = new ArrayList<String>();
		String segments[] = getSaveInfoWithFormat().split("\n");
		for (int i = 0; i < segments.length; i++)
			output.add(segments[i]);
		return output;
	}
	
	private static String[] getPlayerNamesAndTurn(String input) {
		/*
		 * input format: 
		 * 		Player1 vs Player2 on turn 0
		 */
		String[] output = new String[3];
		String[] segments = input.split(" ");
		output[0] = segments[0];
		output[1] = segments[2];
		output[2] = segments[5];
		return output;
	}
	
	private static String getSaveFileName(String player1Name, String player2Name, String turn) {
		File[] files = new File(filePath).listFiles();
		if (files == null)
			return "[Error] There are no save games with current parameters.";
		else {
			String output = new String();
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile()) {
					try {
						boolean found = true;
						Scanner reader = new Scanner(files[i]);
						reader.hasNextLine();
						if (!reader.nextLine().equals(player1Name))
							found = false;
						reader.hasNextLine();
						if(!reader.nextLine().equals(player2Name))
							found = false;
						reader.hasNextLine();
						if (!reader.nextLine().equals(turn))
							found = false;
						reader.close();
						if (found) {
							output = files[i].getName();
							break;
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			return output;
		}
	}
	
	public static ArrayList<String> loadGameFile(String loadgameBtnName) {
		String[] playerNamesAndTurn = getPlayerNamesAndTurn(loadgameBtnName);
		String saveFileName = getSaveFileName(playerNamesAndTurn[0], playerNamesAndTurn[1], playerNamesAndTurn[2]);
		File saveFile = new File(filePath + File.separator + saveFileName);
		ArrayList<String> output = new ArrayList<String>();
		try {
			Scanner reader = new Scanner(saveFile);
			while (reader.hasNextLine())
				output.add(reader.nextLine());
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static String getPlayerName(ArrayList<String> loadGameFileContent, int choosePlayer) {
		if (choosePlayer < 0 || choosePlayer > 1)
			return null;
		else return loadGameFileContent.get(choosePlayer);
	}
	
	public static int getTableTurn(ArrayList<String> loadGameFileContent) {
		return Integer.parseInt(loadGameFileContent.get(2));
	}
	
	public static ArrayList<String> getTableRawData(ArrayList<String> loadGameFileContent) {
		ArrayList<String> output = new ArrayList<String>();
		for (int i = 3; i < loadGameFileContent.size(); i++) {
			String[] segments = loadGameFileContent.get(i).split(" ");
			for (int j = 0; j < segments.length; j++)
				output.add(segments[j]);
		}
		return output;
	}
}
