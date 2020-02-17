package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFileController {
	
	private static final String logFileName = "logs.txt";
	
	public static void writeToFile(String text) {
		try {
			File file = new File(logFileName);
			file.mkdirs();
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File getFile() {
		File outFile = new File(logFileName);
		return outFile;
	}
}
