package view;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.CurrentTime;
import controller.LogFileController;
import model.TableModel;

public class ChessGameTextAreaPanel extends JPanel {
	
	private static final long serialVersionUID = -3197877034264165550L;
	private static final String viewLogFileBtnName = "View logs file";
	private static final String mainMenuBtnName = "Main menu";
	private static final String exitBtnName = "Exit";
	private JTextArea textArea = new JTextArea(20, 15);
	
	public ChessGameTextAreaPanel(String frameName, int sizeX, int sizeY, TableModel table, CurrentTime time, ChessGameFrame chessGameFrame) {
		super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.append("[" + time.getCurrentTime() + "] Player 1 turn's\n");
        textArea.setEditable(false);
        JButton viewLogsFileBtn = new JButton(viewLogFileBtnName);
        viewLogsFileBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (Desktop.isDesktopSupported()) {
    			        try {
							Desktop.getDesktop().open(LogFileController.getFile());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
    			}
    		}
        });
        JButton mainMenuBtn = new JButton(mainMenuBtnName);
        mainMenuBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			@SuppressWarnings("unused")
				StartMenuFrame startMenuFrame = new StartMenuFrame(frameName, sizeX, sizeY, time);
    			chessGameFrame.dispose();
    		}
        });
        JButton exitBtn = new JButton(exitBtnName);
        exitBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.exit(0);
    		}
        });
        add(viewLogsFileBtn);
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
