package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controller.CurrentTime;
import controller.LoadgameController;
import model.TableModel;

public class LoadgameFrame extends JFrame {

	private static final long serialVersionUID = -3690809905258290658L;
	private static final String backBtnName = "Back";
	
	public LoadgameFrame(String frameName, int sizeX, int sizeY, CurrentTime time) {
		super(frameName);
		setPreferredSize(new Dimension(sizeX, sizeY));
		add(new JScrollPane(createMainPanel(frameName, sizeX, sizeY, this, time)), BorderLayout.CENTER);
		JButton backBtn = new JButton(backBtnName);
		backBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			@SuppressWarnings("unused")
				StartMenuFrame startMenuFrame = new StartMenuFrame(frameName, sizeX, sizeY, time);
    			dispose();
    		}
		});
		add(backBtn, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(true);
	}
	
	private JPanel createMainPanel(String frameName, int sizeX, int sizeY, LoadgameFrame loadgameFrame, CurrentTime time) {
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(50, 50, 5, 50));
		ArrayList<JButton> loadBtn = new ArrayList<JButton>();
		ArrayList<String> loadBtnName = LoadgameController.getSaveInfoAsList();
		mainPanel.setLayout(new GridLayout(loadBtnName.size() + 2, 1));
		for (int i = 0; i < loadBtnName.size(); i++) {
			loadBtn.add(new JButton(loadBtnName.get(i)));
			loadBtn.get(i).addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			String buttonText = ((JButton) e.getSource()).getText();
	    			try {
	    				ArrayList<String> loadGameFileContent = LoadgameController.loadGameFile(buttonText);
	    				String firstPlayerName = LoadgameController.getPlayerName(loadGameFileContent, 0);
	    				String secondPlayerName = LoadgameController.getPlayerName(loadGameFileContent, 1);
	    				int playerTurn = LoadgameController.getTableTurn(loadGameFileContent);
	    				ArrayList<String> tableRawData = LoadgameController.getTableRawData(loadGameFileContent);
	    				TableModel table = new TableModel(tableRawData);
	    				@SuppressWarnings("unused")
	    				ChessGameFrame newChessGameFrame = new ChessGameFrame(frameName, firstPlayerName, secondPlayerName, sizeY, sizeY, table, playerTurn, time);
	    				loadgameFrame.dispose();
	    			} catch (NullPointerException e1) {
	    				e1.printStackTrace();
	    			}
	    		}
			});
			mainPanel.add(loadBtn.get(i));
		}
		return mainPanel;
	}
}
