package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import controller.ChessGameFrameController;
import controller.CurrentTime;
import controller.LogFileController;
import controller.PieceController;
import controller.TableController;
import model.Player;
import model.TableModel;

public class ChessGameMainPanel extends JPanel {
	
	private static final long serialVersionUID = 4739413986670004870L;
	private static final String blank = " ";
    private static final Color backgroundColor = Color.black;
    private static final Color player1Color = Color.white, player2Color = Color.darkGray, highlightColor = Color.blue;
    private static final int gridX = 8, gridY = 8;
    private int playerTurn = 0;
    private int firstButtonX = -1, firstButtonY = -1, secondButtonX = -1, secondButtonY = -1;
    private GridLayout tableLayout = new GridLayout(gridX, gridY);
	private JButton[][] buttonPieces = new JButton[gridX][gridY];
    
	public ChessGameMainPanel(String frameName, ChessGameFrame chessGameFrame, int sizeX, int sizeY, TableModel table, 
			JTextArea textArea, CurrentTime time, Boolean debug) {
		super();
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (firstButtonX == -1 && firstButtonY == -1) {
					JButton selectedBtn = (JButton) e.getSource();
					int[] aux = ChessGameFrameController.selectButtonCoord(buttonPieces, selectedBtn);
					if (TableController.isKingInCheckMate(table, playerTurn, debug))
						endGame(ChessGameFrameController.getCurrentPlayer(playerTurn - 1), chessGameFrame, frameName, sizeX, sizeY, table, time);
					if (debug)
						LogFileController.writeToFile("[TableView] selectedBtn: " + table.getPieces(aux[0], aux[1]) + "\n");
					if (playerTurn % 2 == 0 && table.getPieces(aux[0], aux[1]).getOwner() == Player.PLAYER1) {
						firstButtonX = aux[0];
						firstButtonY = aux[1];
						selectedBtn.setBackground(highlightColor);
					}
					else if (playerTurn % 2 == 1 && table.getPieces(aux[0], aux[1]).getOwner() == Player.PLAYER2) {
						firstButtonX = aux[0];
						firstButtonY = aux[1];
						selectedBtn.setBackground(highlightColor);
					}
					else textArea.append("[" + time.getCurrentTime() + "] Please choose another piece, wrong owner\n");
				}
				else {
					secondButtonX = secondButtonY = -1;
					JButton selectedBtn = (JButton) e.getSource();
					int[] aux = ChessGameFrameController.selectButtonCoord(buttonPieces, selectedBtn);
					secondButtonX = aux[0];
					secondButtonY = aux[1];
					if ((secondButtonX != -1 && secondButtonY != -1)) {
						boolean moveIsValid = PieceController.isGeneralMoveValid(table, firstButtonX, firstButtonY, secondButtonX, secondButtonY, debug);
						if (debug)
							LogFileController.writeToFile("[tableView] First btn: " + firstButtonX + " " + firstButtonY + " Second btn: " + secondButtonX + " " + secondButtonY 
									+ "\n" + "[tableView] Start: " + table.getPieces(firstButtonX, firstButtonY)
									+ "\n" + "[tableView] Finish: " + table.getPieces(secondButtonX, secondButtonY)
									+ "\n" + "[tableView] Boolean: " + moveIsValid);
						if (moveIsValid) {
							TableModel tempTable = new TableModel(table);
							TableController.makeMove(tempTable, firstButtonX, firstButtonY, secondButtonX, secondButtonY, debug);
							if (debug)
								LogFileController.writeToFile("[tableView] Start: " + table.getPieces(firstButtonX, firstButtonY) 
									+ "\n" + "[tableView] Finish: " + table.getPieces(secondButtonX, secondButtonY));
							if (TableController.tick(tempTable, playerTurn, textArea, time, debug)) {
								ChessGameFrameController.makeMove(buttonPieces[firstButtonX][firstButtonY], buttonPieces[secondButtonX][secondButtonY], blank);
								TableController.makeMove(table, firstButtonX, firstButtonY, secondButtonX, secondButtonY, debug);
								playerTurn++;
							}
							buttonPieces[firstButtonX][firstButtonY].setBackground(backgroundColor);
							firstButtonX = firstButtonY = -1;
							textArea.append("[" + time.getCurrentTime() + "] Player " + ((playerTurn % 2) + 1) + " turn's\n");
						}
						else textArea.append("[" + time.getCurrentTime() + "] Wrong move. Please try again\n");
						if (debug)
							LogFileController.writeToFile("[tableView]\n" + table + "\n");
					}
				}
			}
		};
		for (int i = 0; i < buttonPieces.length; i++)
        	for (int j = 0; j < buttonPieces[0].length; j++) {
        		String tempButtonName = String.valueOf(table.getPieces().get(i * gridX + j).getType());
        		if (tempButtonName == "BLANK") 
        			buttonPieces[i][j] = new JButton(blank);
        		else 
        			buttonPieces[i][j] = new JButton(tempButtonName);
        		buttonPieces[i][j].setBackground(backgroundColor);
        		if (table.getPieces().get(i * gridX + j).getOwner() == Player.PLAYER1)
        			buttonPieces[i][j].setForeground(player1Color);
        		else
        			buttonPieces[i][j].setForeground(player2Color);
        		buttonPieces[i][j].addActionListener(actionListener);
        	}
		for (int i = 0; i < buttonPieces.length; i++)
			for (int j = 0; j < buttonPieces[0].length; j++)
            	add(buttonPieces[i][j]);
		setLayout(tableLayout);
		setPreferredSize(new Dimension(sizeX, sizeY));
	}
	
	private static void endGame(String winPlayer, ChessGameFrame chessGameFrame, String frameName, int sizeX, int sizeY, TableModel table, CurrentTime time) {
		String text = winPlayer + " has won. Would you like to start another game?";
		Object[] options = {"Yes",
				"Return to main menu", 
                "Exit"};
		int choice = JOptionPane.showOptionDialog(chessGameFrame, 
				text,
				"Check mate Question",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, 
				options, 
				options[0]); 
		if (choice == JOptionPane.YES_OPTION) {
			TableModel newTable = new TableModel();
			@SuppressWarnings("unused")
			ChessGameFrame newChessGameFrame = new ChessGameFrame(frameName, sizeY, sizeY, newTable, time);
			chessGameFrame.dispose();
		}
		else if(choice == JOptionPane.NO_OPTION) {
			@SuppressWarnings("unused")
			StartMenuFrame startMenuFrame = new StartMenuFrame(frameName, sizeX, sizeY, table, time);
			chessGameFrame.dispose();
		}
		else System.exit(0);
	}
}
