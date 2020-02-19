package view;

import controller.CurrentTime;

public class TableView {
    
	private static final String frameName = "Chess";
	private static final int sizeX = 1000, sizeY = 750;
	
    public TableView(CurrentTime time) {
		@SuppressWarnings("unused")
		StartMenuFrame startMenuFrame = new StartMenuFrame(frameName, sizeX, sizeY, time);
    }
    
}
