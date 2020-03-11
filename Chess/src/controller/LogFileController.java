package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFileController {
	
	private static final String logFileName = "log.txt";
	private static BufferedWriter writer;
	private static CurrentTime time = new CurrentTime();
	private static final String upperLimitStackTrace = "actionPerformed";
	private static final String firstAvoidStackTrace = "getStackTrace";
	private static final String secondAvoidStackTrace = "writeToFile";
	
	public static void initLogFile() {
		try {
			File file = new File(logFileName);
			if (!file.createNewFile()) {
				file.delete();
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(logFileName, true));
		} catch (IOException e) {
			closeWriter();
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(String text) {
		try {
			writer.write("[" + time.getCurrentTime() + "] ");
			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
				if (upperLimitStackTrace.equals(ste.getMethodName()))
					break;
				if (!firstAvoidStackTrace.equals(ste.getMethodName()) && !secondAvoidStackTrace.equals(ste.getMethodName()))
					writer.write(ste.getMethodName() + " : ");
			}
			writer.write(text + "\n");
		} catch (Exception e) {
			closeWriter();
			e.printStackTrace();
		}
	}
	
	public static void closeWriter() {
		try {
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
