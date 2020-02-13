package controller;

import java.io.FileWriter;
import java.io.IOException;

public class DebugFileController {
	
	private static final String debugFileName = "logs.txt";
	
	public static void writeToFile(String text) {
		try {
			FileWriter myWriter = new FileWriter(debugFileName);
		    myWriter.write(text);
		    myWriter.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
	}
}
