package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.TableModel;

public class SavegameController {
	
	/*
	 * save game format:
	 * first player name - name1
	 * second player name - name2
	 * current player turn - X
	 * piece 1 (type, locationX, locationY, owner)
	 * piece 2
	 * .
	 * .
	 * .
	 * piece n
	 */
	
	public static final String filePath = "saves";
	private static final String saveExtension = ".save";
	
	private static int getSaveFileNumber() {
		File[] files = new File(filePath).listFiles();
		if (files == null)
			return 1;
		else {
			int numberOfFiles = 1;
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile())
					numberOfFiles++;
			return numberOfFiles;
		}
	}
	
	public static void save(String player1Name, String player2Name, int turn, TableModel table) {
		try {
			final String savegameName = "save" + getSaveFileNumber() + saveExtension;
			final String savegamePath = filePath + File.separator + savegameName;
			File saveFile = new File(savegamePath);
			saveFile.getParentFile().mkdirs();
			saveFile.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(savegamePath, true));
			writer.write(player1Name + "\n" + player2Name + "\n" + turn + "\n");
			writer.write(table.rawDataToString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
