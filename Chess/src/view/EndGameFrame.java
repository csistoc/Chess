package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EndGameFrame extends JFrame {

	private static final long serialVersionUID = 4072571665731980379L;
	private static final String newGameBtnName = "New game";
	private static final String exitGameBtnName = "Exit game";
	
	public EndGameFrame(String frameName, int sizeX, int sizeY, ChessGameFrame chessGameFrame) {
		super(frameName);
		JButton newGameBtn = new JButton(newGameBtnName);
		newGameBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			chessGameFrame.setVisible(true);
				setVisible(false);
    		}
		});
		JButton exitGameBtn = new JButton(exitGameBtnName);
		exitGameBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.exit(0);
    		}
		});
		JPanel endGamePanel = new JPanel();
		endGamePanel.add(newGameBtn, BorderLayout.CENTER);
		endGamePanel.add(exitGameBtn, BorderLayout.CENTER);
		add(endGamePanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(false);
	}
}
