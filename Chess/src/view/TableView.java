package view;

import controller.CurrentTime;
import model.TableModel;

public class TableView {
    
	private static final String frameName = "Chess";
	private static final int sizeX = 1000, sizeY = 750;
	private static StartMenuFrame startMenuFrame;
	private static ChessGameFrame chessGameFrame;
	private static EndGameFrame endGameFrame;
	
    private static void createAndShowGUI() {
    	TableModel table = new TableModel(8, 8);
    	CurrentTime time = new CurrentTime();
    	chessGameFrame = new ChessGameFrame(frameName, sizeX, sizeY, table, startMenuFrame, endGameFrame, time);
    	startMenuFrame = new StartMenuFrame(frameName, sizeX, sizeY, chessGameFrame);
    	endGameFrame = new EndGameFrame(frameName, sizeX, sizeY, chessGameFrame);
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
