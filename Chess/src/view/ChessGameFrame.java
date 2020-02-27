package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import controller.CurrentTime;
import model.TableModel;

public class ChessGameFrame extends JFrame {
	
	private static final long serialVersionUID = 3145336724843258698L;
    
    public ChessGameFrame(String frameName, String firstPlayerName, String secondPlayerName, int sizeX, int sizeY, TableModel table, CurrentTime time) {
    	super(frameName);
    	ChessGameTextAreaPanel textAreaPanel = new ChessGameTextAreaPanel(frameName, firstPlayerName, sizeX, sizeY, table, time, this);
    	ChessGameMainPanel mainPanel = new ChessGameMainPanel(frameName, this, firstPlayerName, secondPlayerName, sizeX, 
    			sizeY, table, textAreaPanel.getTextArea(), time);
    	ChessGameSeparatorPanel separatorPanel = new ChessGameSeparatorPanel();
    	add(mainPanel, BorderLayout.WEST);
    	add(separatorPanel);
    	add(textAreaPanel, BorderLayout.EAST);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(true);
    }

}
