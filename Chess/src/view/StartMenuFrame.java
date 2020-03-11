package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.CurrentTime;
import model.TableModel;

public class StartMenuFrame extends JFrame {

	private static final long serialVersionUID = -7597230372365270806L;
	private static final String newGameBtnName = "New game";
	private static final String loadGameBtnName = "Load game";
	private static final String exitGameBtnName = "Exit game";
	private static final String aboutBtnName = "About";
	private static final String leaderboardBtnName = "Leaderboard";
	private static String firstPlayerName = new String();
	private static String secondPlayerName = new String();
	
	public StartMenuFrame(String frameName, int sizeX, int sizeY, CurrentTime time) {
		super(frameName);
		setPreferredSize(new Dimension(sizeX, sizeY));
		add(createMainPanel(this, frameName, sizeX, sizeY, time));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(true);
	}
	
	private JPanel createMainPanel(StartMenuFrame startMenuFrame, String frameName, int sizeX, int sizeY, CurrentTime time) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5, 1));
		mainPanel.setBorder(new EmptyBorder(50, 300, 50, 300));
		JButton newGameBtn = new JButton(newGameBtnName);
		newGameBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			getFirstPlayerName(startMenuFrame, frameName, sizeY, sizeY, time);
    		}
		});
		JButton loadGameBtn = new JButton(loadGameBtnName);
		loadGameBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			@SuppressWarnings("unused")
				LoadgameFrame loadgameFrame = new LoadgameFrame(frameName, sizeX, sizeY, time);
    			dispose();
    		}
		});
		JButton leaderboardBtn = new JButton(leaderboardBtnName);
		leaderboardBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			@SuppressWarnings("unused")
				LeaderboardFrame leaderboardFrame = new LeaderboardFrame(frameName, sizeY, sizeY, time);
    			dispose();
    		}
		});
		JButton aboutBtn = new JButton(aboutBtnName);
		aboutBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String aboutTextField = new String();
    			try {
    				File file = new File("About.txt");
    			    Scanner reader = new Scanner(file);
    			    while (reader.hasNextLine()) {
    			    	aboutTextField += reader.nextLine() + "\n";
    			    }
    			    reader.close();
    			    } catch (FileNotFoundException ex) {
    			    	ex.printStackTrace();
    			}
    			aboutInfoBox(aboutTextField, aboutBtnName);
    		}
		});
		JButton exitGameBtn = new JButton(exitGameBtnName);
		exitGameBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.exit(0);
    		}
		});
		mainPanel.add(newGameBtn);
		mainPanel.add(loadGameBtn);
		mainPanel.add(leaderboardBtn);
		mainPanel.add(aboutBtn);
		mainPanel.add(exitGameBtn);
		return mainPanel;
	}
	
	private static void getFirstPlayerName(StartMenuFrame startMenuFrame, String frameName, int sizeX, int sizeY, CurrentTime time) {
		String result = (String)JOptionPane.showInputDialog (
				startMenuFrame,
				"Enter first player name", 
				"Chess",            
				JOptionPane.PLAIN_MESSAGE,
				null,            
				null, 
				"Player1");
		firstPlayerName = result;
		getSecondPlayerName(startMenuFrame, frameName, sizeY, sizeY, time);
	}
	
	private static void getSecondPlayerName(StartMenuFrame startMenuFrame, String frameName, int sizeX, int sizeY, CurrentTime time) {
		String result = (String)JOptionPane.showInputDialog (
				startMenuFrame,
				"Enter second player name", 
				"Chess",            
				JOptionPane.PLAIN_MESSAGE,
				null,            
				null, 
				"Player2");
		secondPlayerName = result;
		TableModel table = new TableModel();
		@SuppressWarnings("unused")
		ChessGameFrame gameFrame = new ChessGameFrame(frameName, firstPlayerName, secondPlayerName, sizeY, sizeY, table, 0, time);
		startMenuFrame.dispose();
	}
	
	private static void aboutInfoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

	public static String getFirstPlayerName() {
		return firstPlayerName;
	}

	public static String getSecondPlayerName() {
		return secondPlayerName;
	}
}
