package controller;

import model.TableModel;
import view.TableView;

public class MainController {
	
	public static void run() {
		TableModel table = new TableModel();
    	CurrentTime time = new CurrentTime();
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	@SuppressWarnings("unused")
        		TableView view = new TableView(table, time);
            }
        });
	}

	public static void main(String[] args) {
		run();
	}

}
