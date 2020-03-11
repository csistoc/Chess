package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.ChessFrameController;
import controller.CurrentTime;
import controller.DBController;
import controller.LogFileController;
import controller.PieceController;
import controller.SavegameController;
import controller.TableController;
import model.PieceModel;
import model.Player;
import model.TableModel;

public class ChessGameFrame extends JFrame {
	
	private static final long serialVersionUID = 3145336724843258698L;
	private String frameName, firstPlayerName, secondPlayerName;
	private int sizeX, sizeY, playerTurn;
    
    public ChessGameFrame(String frameName, String firstPlayerName, String secondPlayerName, int sizeX, int sizeY, TableModel table, 
    		int playerTurn, CurrentTime time) {
    	super(frameName);
    	init(frameName, firstPlayerName, secondPlayerName, sizeX, sizeY, playerTurn);
    	ChessGameTextAreaPanel textAreaPanel = new ChessGameTextAreaPanel(table, time, this);
    	ChessGameMainPanel mainPanel = new ChessGameMainPanel(this, table, textAreaPanel.getTextArea(), time);
    	ChessGameSeparatorPanel separatorPanel = new ChessGameSeparatorPanel();
    	add(mainPanel, BorderLayout.WEST);
    	add(separatorPanel);
    	add(textAreaPanel, BorderLayout.EAST);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(true);
    }
    
    private void init(String frameName, String firstPlayerName, String secondPlayerName, int sizeX, int sizeY, int playerTurn) {
    	this.frameName = frameName;
    	this.firstPlayerName = firstPlayerName;
    	this.secondPlayerName = secondPlayerName;
    	this.sizeX = sizeX;
    	this.sizeY = sizeY;
    	this.playerTurn = playerTurn;
    }
    
    public class ChessGameTextAreaPanel extends JPanel {
    	
    	private static final long serialVersionUID = -3197877034264165550L;
    	private static final String viewLogFileBtnName = "View logs file";
    	private static final String savegameBtnName = "Save game";
    	private static final String mainMenuBtnName = "Main menu";
    	private static final String exitBtnName = "Exit";
    	private JTextArea textArea = new JTextArea(20, 15);
    	public static final String savegameTrigger = "savegame";
    	
    	public ChessGameTextAreaPanel(TableModel table, CurrentTime time, ChessGameFrame chessGameFrame) {
    		super();
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder(new EmptyBorder(5, 5, 5, 5));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.append("[" + time.getCurrentTime() + "] " + firstPlayerName + " turn's\n");
            textArea.setEditable(false);
            textArea.setOpaque(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setOpaque(false);
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
            viewLogsFileBtn.setVisible(false);
            JButton savegameBtn = new JButton(savegameBtnName);
            savegameBtn.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			SavegameController.save(firstPlayerName, secondPlayerName, playerTurn, table);
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
            add(scrollPane);
            add(savegameBtn);
            add(mainMenuBtn);
            add(exitBtn);
    	}
    	
    	public JTextArea getTextArea() {
    		return textArea;
    	}
    	
    	public void setTextArea(JTextArea textArea) {
    		this.textArea = textArea;
    	}
    }

    public class ChessGameMainPanel extends JPanel {
    	
    	private static final long serialVersionUID = 4739413986670004870L;
    	private static final String blank = " ";
        private final Color backgroundColor = Color.black;
        private final Color player1Color = Color.white, player2Color = Color.darkGray, highlightColor = Color.blue;
        private static final int gridX = 8, gridY = 8;
        private int firstButtonX = -1, firstButtonY = -1, secondButtonX = -1, secondButtonY = -1;
        private GridLayout tableLayout = new GridLayout(gridX, gridY);
    	private JButton[][] buttonPieces = new JButton[gridX][gridY];
        
    	public ChessGameMainPanel(ChessGameFrame chessGameFrame, TableModel table, JTextArea textArea, CurrentTime time) {
    		super();
    		setBorder(new EmptyBorder(5, 5, 5, 5));
    		ActionListener actionListener = new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				if (firstButtonX == -1 && firstButtonY == -1) {
    					JButton selectedBtn = (JButton) e.getSource();
    					int[] aux = ChessFrameController.selectButtonCoord(buttonPieces, selectedBtn);
    					if (TableController.isKingInCheckMate(table, playerTurn)) {
    						ChessFrameController.endGame(ChessFrameController.getCurrentPlayer(playerTurn - 1, firstPlayerName, secondPlayerName), chessGameFrame, frameName, 
    								firstPlayerName, secondPlayerName, sizeX, sizeY, table, time);
    						DBController.insertData(ChessFrameController.getCurrentPlayer(playerTurn - 1, firstPlayerName, secondPlayerName), playerTurn - 1);
    					}
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
    					int[] aux = ChessFrameController.selectButtonCoord(buttonPieces, selectedBtn);
    					secondButtonX = aux[0];
    					secondButtonY = aux[1];
    					if ((secondButtonX != -1 && secondButtonY != -1)) {
    						boolean moveIsValid = PieceController.isGeneralMoveValid(table, firstButtonX, firstButtonY, secondButtonX, secondButtonY);
    						if (moveIsValid) {
    							TableModel tempTable = new TableModel(table);
    							TableController.makeMove(tempTable, firstButtonX, firstButtonY, secondButtonX, secondButtonY);
    							if (TableController.tick(tempTable, playerTurn, textArea, time)) {
    								ChessFrameController.makeMove(buttonPieces[firstButtonX][firstButtonY], buttonPieces[secondButtonX][secondButtonY], blank);
    								TableController.makeMove(table, firstButtonX, firstButtonY, secondButtonX, secondButtonY);
    								playerTurn++;
    							}
    							buttonPieces[firstButtonX][firstButtonY].setBackground(backgroundColor);
    							firstButtonX = firstButtonY = -1;
    							textArea.append("[" + time.getCurrentTime() + "] " 
    									+ ChessFrameController.getCurrentPlayer((playerTurn % 2), firstPlayerName, secondPlayerName) + " turn's\n");
    						}
    						else textArea.append("[" + time.getCurrentTime() + "] Wrong move. Please try again\n");
    					}
    				}
    			}
    		};
    		initBtns(table, actionListener);
    		for (int i = 0; i < buttonPieces.length; i++)
    			for (int j = 0; j < buttonPieces[0].length; j++)
                	add(buttonPieces[i][j]);
    		setLayout(tableLayout);
    		setPreferredSize(new Dimension(sizeX, sizeY));
    	}
    	
    	private void initBtns(TableModel table, ActionListener actionListener) {
    		for (PieceModel currPiece : table.getPieces()) {
    			int i = currPiece.getLocation().getX();
    			int j = currPiece.getLocation().getY();
    			String tempButtonName = String.valueOf(currPiece.getType());
        		if (tempButtonName == "BLANK") 
        			buttonPieces[i][j] = new JButton(blank);
        		else 
        			buttonPieces[i][j] = new JButton(tempButtonName);
        		buttonPieces[i][j].setBackground(backgroundColor);
        		if (currPiece.getOwner() == Player.PLAYER1)
        			buttonPieces[i][j].setForeground(player1Color);
        		else
        			buttonPieces[i][j].setForeground(player2Color);
        		buttonPieces[i][j].addActionListener(actionListener);
    		}
    	}
    }

    public class ChessGameSeparatorPanel extends JPanel {
    	
    	private static final long serialVersionUID = 4584765638472939490L;

    	public ChessGameSeparatorPanel() {
    		super();
            add(new JSeparator(SwingConstants.HORIZONTAL));
    	}
    }
}
