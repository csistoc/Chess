package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import controller.CurrentTime;
import model.TableModel;

public class ChessGameFrame extends JFrame {
	
	private static final long serialVersionUID = 3145336724843258698L;
	private Boolean[] debug;
    
    public ChessGameFrame(String frameName, int sizeX, int sizeY, TableModel table, CurrentTime time) {
    	super(frameName);
    	debug = new Boolean[1];
    	debug[0] = false;
    	ChessGameTextAreaPanel textAreaPanel = new ChessGameTextAreaPanel(frameName, sizeX, sizeY, table, time, this, debug);
    	ChessGameMainPanel mainPanel = new ChessGameMainPanel(frameName, this, sizeX, sizeY, table, textAreaPanel.getTextArea(), time, debug[0]);
    	ChessGameSeparatorPanel separatorPanel = new ChessGameSeparatorPanel();
    	add(mainPanel, BorderLayout.WEST);
    	add(separatorPanel);
    	add(textAreaPanel, BorderLayout.EAST);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(true);
    }

}
