package view;

import java.awt.BorderLayout;
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

public class StartMenuFrame extends JFrame {

	private static final long serialVersionUID = -7597230372365270806L;
	private static final String newGameBtnName = "New game";
	private static final String loadGameBtnName = "Load game";
	private static final String exitGameBtnName = "Exit game";
	private static final String aboutBtnName = "About";
	
	public StartMenuFrame(String frameName, int sizeX, int sizeY, ChessGameFrame gameFrame) {
		super(frameName);
		add(createMainPanel(sizeX, sizeY, gameFrame), BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(true);
	}
	
	private JPanel createMainPanel(int sizeX, int sizeY, ChessGameFrame gameFrame) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 1));
		mainPanel.setPreferredSize(new Dimension(sizeX, sizeY));
		JButton newGameBtn = new JButton(newGameBtnName);
		newGameBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
				gameFrame.setVisible(true);
				setVisible(false);
    		}
		});
		JButton loadGameBtn = new JButton(loadGameBtnName);
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
    			    	System.out.println("An error occurred.");
    			    	ex.printStackTrace();
    			}
    			infoBox(aboutTextField, aboutBtnName);
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
		mainPanel.add(aboutBtn);
		mainPanel.add(exitGameBtn);
		return mainPanel;
	}
	
	private static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
