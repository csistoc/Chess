package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.CurrentTime;
import controller.DBController;

public class LeaderboardFrame extends JFrame {

	private static final long serialVersionUID = 3867660495363672296L;
	private static final String backBtnName = "Back";
	
	public LeaderboardFrame (String frameName, int sizeX, int sizeY, CurrentTime time) {
		super(frameName);
		setPreferredSize(new Dimension(sizeX, sizeY));
		add(createLeaderboardPanel(frameName, sizeX, sizeY, time));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(true);
	}
	
	private JPanel createLeaderboardPanel(String frameName, int sizeX, int sizeY, CurrentTime time) {
		JPanel leaderboardPanel = new JPanel();
		leaderboardPanel.setLayout(new GridLayout(2, 1));
		JTextArea lTextArea = new JTextArea(100, 100);
		lTextArea.setLineWrap(true);
		lTextArea.setWrapStyleWord(true);
		lTextArea.setEditable(false);
		lTextArea.append(DBController.show());
		JScrollPane scrollPane = new JScrollPane(lTextArea);
		JButton backBtn = new JButton(backBtnName);
		backBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			@SuppressWarnings("unused")
				StartMenuFrame startMenuFrame = new StartMenuFrame(frameName, sizeX, sizeY, time);
    			dispose();
    		}
		});
		leaderboardPanel.add(scrollPane);
		leaderboardPanel.add(backBtn);
		return leaderboardPanel;
	}
}
