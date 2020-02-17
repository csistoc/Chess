package view;

import controller.CurrentTime;
import model.TableModel;

public class TableView {
    
	private static final String frameName = "Chess";
	private static final int sizeX = 1000, sizeY = 750;
	
    public TableView(TableModel table, CurrentTime time) {
		@SuppressWarnings("unused")
		StartMenuFrame startMenuFrame = new StartMenuFrame(frameName, sizeX, sizeY, table, time);
    }
    
}
