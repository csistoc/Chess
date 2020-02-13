package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.CurrentTime;

public class ChessGameTextAreaPanel extends JPanel {
	
	private static final long serialVersionUID = -3197877034264165550L;
	private static final String debugBtnName = "Debug";
	private static final String mainMenuBtnName = "Main menu";
	private static final String exitBtnName = "Exit";
	private JTextArea textArea = new JTextArea(20, 15);
	
	public ChessGameTextAreaPanel(CurrentTime time, ChessGameFrame chessGameFrame, StartMenuFrame startMenuFrame, Boolean[] debug) {
		super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.append("[" + time.getCurrentTime() + "] Player 1 turn's\n");
        textArea.setEditable(false);
        JButton debugBtn = new JButton(debugBtnName);
        debugBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (debug[0])
    				debug[0] = false;
    			else debug[0] = true;
    		}
        });
        JButton mainMenuBtn = new JButton(mainMenuBtnName);
        mainMenuBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			startMenuFrame.setVisible(true);
    			chessGameFrame.setVisible(false);
    		}
        });
        JButton exitBtn = new JButton(exitBtnName);
        mainMenuBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.exit(0);
    		}
        });
        add(debugBtn);
        add(mainMenuBtn);
        add(exitBtn);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
	
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
}
