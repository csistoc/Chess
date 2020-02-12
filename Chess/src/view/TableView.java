package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import controller.CurrentTime;
import controller.PieceController;
import controller.TableController;
import model.Player;
import model.TableModel;

public class TableView {
    static final int size_x = 1000, size_y = 750;
    static final int gridX = 8, gridY = 8;
    static final String windowName = "Chess", blank = " ";
    static final Color backgroundColor = Color.black;
    static final Color player1Color = Color.white, player2Color = Color.darkGray, highlightColor = Color.blue;
    GridLayout tableLayout = new GridLayout(gridX, gridY);
    JButton[][] buttonPieces = new JButton[gridX][gridY];
    JFrame frame;
    TableModel table;
    int firstButtonX = -1, firstButtonY = -1, secondButtonX = -1, secondButtonY = -1;
    boolean debug = false;
    int playerTurn = 0;
    JTextArea textArea;
    
    public TableView() {
    	CurrentTime time = new CurrentTime();
        this.frame = new JFrame(windowName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        table = new TableModel(gridX, gridY);
        if (debug) 
        	System.out.println("[tableView]\n" + table);
        
        ActionListener newGameAction = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TableView table = new TableView();
        		closeCurrentFrame();
        	}
        };
        JButton newGameBtn = new JButton("New game");
        newGameBtn.addActionListener(newGameAction);
        
        ActionListener exitGameAction = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        };
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(exitGameAction);
        
        textArea = new JTextArea(20, 15);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.append("[" + time.getCurrentTime() + "] Player 1 turn's\n");
        
        
        for (int i = 0; i < buttonPieces.length; i++)
        	for (int j = 0; j < buttonPieces[0].length; j++)
        {
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
        	buttonPieces[i][j].addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			if (firstButtonX == -1 && firstButtonY == -1) {
        				JButton selectedBtn = (JButton) e.getSource();
        				int[] aux = selectFirstButtonCoord(buttonPieces, selectedBtn);
        				
        				if (TableController.isKingInCheckMate(table, playerTurn, debug)) {
        					closeCurrentFrame();
        					Frame endGameFrame = new Frame(windowName);
        					JPanel endGamePanel = new JPanel();
        					endGamePanel.add(newGameBtn, BorderLayout.CENTER);
        					endGamePanel.add(exitBtn, BorderLayout.CENTER);
        					endGameFrame.add(endGamePanel);
        					endGameFrame.pack();
        					endGameFrame.setVisible(true);
        				}
        				
        				if (debug)
        					System.out.println("[TableView] selectedBtn: " + table.getPieces(aux[0], aux[1]));
        				
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
        				int[] aux = selectSecondButtonCoord(buttonPieces, selectedBtn);
        				
        				
        				secondButtonX = aux[0];
        				secondButtonY = aux[1];
        				
        				if ((secondButtonX != -1 && secondButtonY != -1)) {
        					boolean moveIsValid = PieceController.isGeneralMoveValid(table, firstButtonX, firstButtonY, secondButtonX, secondButtonY, debug);
        					if (debug) {
        						System.out.println("[tableView] First btn: " + firstButtonX + " " + firstButtonY + " Second btn: " + secondButtonX + " " + secondButtonY);
        						System.out.println("[tableView] Start: " + table.getPieces(firstButtonX, firstButtonY));
        						System.out.println("[tableView] Finish: " + table.getPieces(secondButtonX, secondButtonY));
        						System.out.println("[tableView] Boolean: " + moveIsValid);
        					}
        					if(moveIsValid) {
        						TableModel tempTable = new TableModel(table);
        						TableController.makeMove(tempTable, firstButtonX, firstButtonY, secondButtonX, secondButtonY, debug);
        						if (debug) {
            						System.out.println("[tableView] Start: " + table.getPieces(firstButtonX, firstButtonY));
            						System.out.println("[tableView] Finish: " + table.getPieces(secondButtonX, secondButtonY));
        						}
        						if (TableController.tick(tempTable, playerTurn, textArea, time, debug)) {
        							makeMove(buttonPieces[firstButtonX][firstButtonY], buttonPieces[secondButtonX][secondButtonY]);
        							TableController.makeMove(table, firstButtonX, firstButtonY, secondButtonX, secondButtonY, debug);
        							playerTurn++;
        						}
        						buttonPieces[firstButtonX][firstButtonY].setBackground(backgroundColor);
        						firstButtonX = firstButtonY = -1;
        						textArea.append("[" + time.getCurrentTime() + "] Player " + ((playerTurn % 2) + 1) + " turn's\n");
        					}
        					else textArea.append("[" + time.getCurrentTime() + "] Wrong move. Please try again\n");
        					if (debug)
        						System.out.println("[tableView]\n" + table);
        				}
        			}
        			
        		}
        	});
        }
        
        JPanel tablePanel = new JPanel();
        
        tablePanel.setLayout(tableLayout);
        tablePanel.setPreferredSize(new Dimension(size_x, size_y));
        
        for (int i = 0; i < buttonPieces.length; i++)
        	for (int j = 0; j < buttonPieces[0].length; j++)
        		tablePanel.add(buttonPieces[i][j]);
        
        frame.add(tablePanel, BorderLayout.WEST);
        
        JPanel separatorPanel = new JPanel();
        separatorPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        frame.add(separatorPanel);
        
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.PAGE_AXIS));
        textArea.setEditable(false);
        JButton debugBtn = new JButton("Debug");
        debugBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (debug)
    				debug = false;
    			else debug = true;
    		}
        });
        textAreaPanel.add(newGameBtn);
        textAreaPanel.add(debugBtn);
        
        textAreaPanel.add(exitBtn);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textAreaPanel.add(scrollPane);
        
        frame.add(textAreaPanel, BorderLayout.EAST);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void closeCurrentFrame() {
    	frame.dispose();
    }
    
    private static boolean checkBtnIsBlank(JButton button, String blankName) {
    	return button.getText().equals(blankName);
    }
    
    private static int[] selectFirstButtonCoord(JButton[][] buttons, JButton selectedBtn) {
    	int[] output = new int[2];
    	for (int i = 0; i < buttons.length; i++)
			for (int j = 0; j < buttons[0].length; j++) {
				if (buttons[i][j] == selectedBtn) {
					output[0] = i;
					output[1] = j;
					break;
				}
			}
    	return output;
    }
    
    private static int[] selectSecondButtonCoord(JButton[][] buttons, JButton selectedBtn) {
    	int[] output = new int[2];
		for (int i = 0; i < buttons.length; i++)
			for (int j = 0; j < buttons[0].length; j++) {
				if (buttons[i][j] == selectedBtn) {
					output[0] = i;
					output[1] = j;
					break;
				}
			}
    	return output;
    }
    
    private static JButton[] swapBtns(JButton btn1, JButton btn2) {
    	JButton[] output = new JButton[2];
    	output[0] = btn1;
    	output[1] = btn2;
    	String auxString = output[0].getText();
		Color auxColor = output[0].getForeground();
		output[0].setText(output[1].getText());
		output[0].setForeground(output[1].getForeground());
		output[1].setText(auxString);
		output[1].setForeground(auxColor);
    	return output;
    }
    
    private static void reverseTable(JButton[][] buttons) {
    	for (int i = 0; i < buttons.length / 2; i++)
			for (int j = 0; j < buttons[0].length; j++) {
				JButton[] aux = swapBtns(buttons[i][j], buttons[buttons.length - 1 - i][j]);
				buttons[i][j] = aux[0];
				buttons[buttons.length - 1 - i][j] = aux[1];
			}
    }
    
    private static void makeMove(JButton btn1, JButton btn2) {
    	//JButton[] output = new JButton[2];
    	//output[0] = btn1;
    	//output[1] = btn2;
    	if (btn2.getText().equals(blank))
    		swapBtns(btn1, btn2);
    	else {
    		btn2.setText(btn1.getText());
    		btn2.setForeground(btn1.getForeground());
    		btn1.setText(blank);
    	}
    	//return output;
    }
	
    private static void createAndShowGUI() {
    	TableView frame = new TableView();
    }
    
    public JButton[][] getBtns() {
    	return this.buttonPieces;
    }
    
    public void setBtns(JButton[][] btns) {
    	for(int i = 0; i < this.buttonPieces.length; i++)
    		for(int j = 0; j < this.buttonPieces[0].length; j++)
    			this.buttonPieces[i][j] = btns[i][j];
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
