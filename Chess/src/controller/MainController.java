package controller;

import view.TableView;

public class MainController {
	
	public static void run() {
    	CurrentTime time = new CurrentTime();
    	LogFileController.initLogFile();
    	DBController.init();
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	@SuppressWarnings("unused")
        		TableView view = new TableView(time);
            }
        });
    	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
    	    public void run() {
    	        LogFileController.closeWriter();
    	    	DBController.close();
    	    }
    	}));
	}

	public static void main(String[] args) {
		run();
	}

}
